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

public class rootServlet extends HttpServlet {
	private Connection connection;
	private ResultSet lookupResults;
	private Statement statement;
	private ResultSetMetaData metadata;
	private String message = "";
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        // boolean goodCred = false;
    	// String inUserName = request.getParameter("username");
        // String inPassword = request.getParameter("password");
        // String credQuery = "select * from usercredentials where login_username = ? and login_password = ?";
        
        String query = request.getParameter("query");
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        message = "";
        
        try {
        	getRootDBConnection();
        	        	
        	//need to figure out how to display this lookupresult
        	
        	if (query.toLowerCase().charAt(0) == 's')
        	{
        		// Selct query
        		lookupResults = statement.executeQuery(query);
        		metadata = lookupResults.getMetaData();
        		int count = metadata.getColumnCount();
        		
        		message +=("<tr>");
        		
        		for(int i = 1; i <= count; i++)
        		{
        			message +=("<td>" + metadata.getColumnName(i) + "</td>");
        		}
        		
        		message +=("</tr>");
                
        		
        		while (lookupResults.next())
        		{
        			message +=("<tr>");
        			for(int i = 1; i <= count; i++)
            		{
            			message +=("<td>" + lookupResults.getString(i) + "</td>");
            		}
        			message +=("</tr>");
        		}
                
        	}
        	else
        	{
        		int rows = statement.executeUpdate(query);
        		
        		message += "<tr><td>statement executed successfully</tr></td>";
        		message += "<tr><td>" + rows + "row(s) affected</tr></td>";
        	}
        	
        	
        	
        } catch (SQLException e)
        {
        	message +=("<tr><td><b>Error executing sql statement:</b><br>" + e.getMessage() + "</tr></td>");
        	}
        
        HttpSession session = request.getSession();
        session.setAttribute("message", message);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/front-end-pages/rootHome.jsp");
    	dispatcher.forward(request, response);
        
    	
    }
    
    private void getRootDBConnection() {
    	Properties properties = new Properties();
    	FileInputStream in = null;
    	MysqlDataSource datasource = null;
    	
    	try {
    		in = new FileInputStream("/home/christopheralbear/Downloads/tomcat/apache-tomcat-11.0.23/webapps/ROOT/WEB-INF/conf/root.properties");
    		properties.load(in);
    		datasource = new MysqlDataSource();
    		datasource.setUrl(properties.getProperty("MYSQL_DB_URL"));
    		datasource.setUser(properties.getProperty("MYSQL_DB_USERNAME"));
    		datasource.setPassword(properties.getProperty("MYSQL_DB_PASSWORD"));
    		connection = datasource.getConnection();
    		statement = connection.createStatement();
    	} catch (SQLException e) {
    		message +=(e);
    	} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
    		message +=(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			message +=(e);
		}
    	
    }
}