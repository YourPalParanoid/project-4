import com.mysql.cj.jdbc.MysqlDataSource;
	

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Properties;

import javax.swing.JOptionPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class clientServlet extends HttpServlet {
	private Connection connection;
	private ResultSet lookupResults;
	private ResultSetMetaData metadata;
	private int mysqlUpdateValue;
	private int[] updateReturnValues;
	private Statement statement;
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        // boolean goodCred = false;
    	// String inUserName = request.getParameter("username");
        // String inPassword = request.getParameter("password");
        // String credQuery = "select * from usercredentials where login_username = ? and login_password = ?";
        
        String query = request.getParameter("query");
        String message = "";
        
        


        
        message += ("<h1 style =\"color: black;\">PLEASE PRINT SOMETHING</h1>");
        
        
        try {
        	getClientDBConnection();
        	        	
        	//need to figure out how to display this lookupresult
        	
        	if (query.toLowerCase().charAt(0) == 's')
        	{
        		// Selct query
        		lookupResults = statement.executeQuery(query);
        		metadata = lookupResults.getMetaData();
        		int count = metadata.getColumnCount();
        		
        		message += ("<tr>");
        		
        		for(int i = 1; i <= count; i++)
        		{
        			message += ("<th>" + metadata.getColumnName(i) + "</th>");
        		}
        		
        		message += ("</tr>");
                
        		
        		while (lookupResults.next())
        		{
        			message += ("<tr>");
        			for(int i = 1; i <= count; i++)
            		{
            			message += ("<td>" + lookupResults.getString(i) + "</td>");
            		}
        			message += ("</tr>");
        		}
        		
        	}
        	else
        	{
        		
        	}
        	
        	
        	
        } catch (SQLException e)
        {
        	
        	message += ("<tr><td><b>Error executing sql statement:</b><br>" + e.getMessage() + "</tr></td>");
        	
        }
        
        HttpSession session = request.getSession();
        session.setAttribute("message", message);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/front-end-pages/clientHome.jsp");
    	dispatcher.forward(request, response);
    }
    
    private void getClientDBConnection() {
    	Properties properties = new Properties();
    	FileInputStream in = null;
    	MysqlDataSource datasource = null;
    	
    	try {
    		in = new FileInputStream("/home/christopheralbear/Downloads/tomcat/apache-tomcat-11.0.23/webapps/ROOT/WEB-INF/conf/client.properties");
    		properties.load(in);
    		datasource = new MysqlDataSource();
    		datasource.setUrl(properties.getProperty("MYSQL_DB_URL"));
    		datasource.setUser(properties.getProperty("MYSQL_DB_USERNAME"));
    		datasource.setPassword(properties.getProperty("MYSQL_DB_PASSWORD"));
    		connection = datasource.getConnection();
    		statement = connection.createStatement();
    	} catch (SQLException e) {
    		e.printStackTrace();
    	} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}