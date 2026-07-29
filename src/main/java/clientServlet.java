import com.mysql.cj.jdbc.MysqlDataSource;
	

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
        
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();


        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\">");
        out.println("<meta charset=\"utf-8\">");

        out.println("<head>");
        out.println("<title>Testing WebApp Package Structure </title>");
        out.println("</head>");

        out.println("<body>");
        out.println("<h1 style =\"color: black;\">PLEASE PRINT SOMETHING</h1>");
        
        
        try {
        	getClientDBConnection();
        	        	
        	//need to figure out how to display this lookupresult
        	System.out.println("hi");
        	
        	if (query.toLowerCase().charAt(0) == 's')
        	{
        		// Selct query
        		lookupResults = statement.executeQuery(query);
        		metadata = lookupResults.getMetaData();
        		int count = metadata.getColumnCount();
        		
        		out.println("<table>");
        		out.println("<tr>");
        		
        		for(int i = 1; i < count; i++)
        		{
        			out.println("<td>" + metadata.getColumnName(i) + "</td>");
        		}
        		
        		out.println("</tr>");
                
        		
        		while (lookupResults.next())
        		{
        			out.println("<tr>");
        			for(int i = 1; i < count; i++)
            		{
            			out.println("<td>" + lookupResults.getString(i) + "</td>");
            		}
        			out.println("</tr>");
        		}
        		out.println("</table>");
        		out.println("</body>");
                out.println("</html>");
                out.close();
        	}
        	else
        	{
        		// update query
        	}
        	
        	
        	
        } catch (SQLException e)
        {
        	message = "<tr><td><b>Error executing sql statement:</b><br>" + e.getMessage() + "</tr></td>";
        }
        
    	
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