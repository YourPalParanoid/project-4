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

public class accountantUserServlet extends HttpServlet {
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
        // String credQuery = "select * from usercrError executing sql statement:
        
    	String value = request.getParameter("radio");
    	System.out.println(value);
    	String query = "";
        
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
        	
        	switch (value) {
        		case "1":
        			query = "select MAX(status) from suppliers";
        			lookupResults = statement.executeQuery(query);
        			lookupResults.next();
        			out.println("<table>");
            		out.println("<tr>");
            		out.println("<td>Maximum_Status_Of_All_Suppliers</td>");
            		out.println("</tr>");
            		out.println("<tr>");
            		out.println("<td>"+ lookupResults.getInt(1) + "</td>");
            		out.println("</tr>");
            		out.println("</table>");
            		break;
        	}
    		
    		out.println("</body>");
            out.println("</html>");
            out.close();
    	
    		
    		out.println("</body>");
            out.println("</html>");
            out.close();
        	
        	
        } catch (SQLException e)
        {
        	out.println("<table>");
        	out.println("<tr><td><b>Error executing sql statement:</b><br>" + e.getMessage() + "</tr></td>");
        	message = "<tr><td><b>Error executing sql statement:</b><br>" + e.getMessage() + "</tr></td>";
        	out.println("</table>");
    		out.println("</body>");
            out.println("</html>");
            out.close();
        }
        
    	
    }
    
    private void getClientDBConnection() {
    	Properties properties = new Properties();
    	FileInputStream in = null;
    	MysqlDataSource datasource = null;
    	
    	try {
    		in = new FileInputStream("/home/christopheralbear/Downloads/tomcat/apache-tomcat-11.0.23/webapps/ROOT/WEB-INF/conf/data-entry.properties");
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