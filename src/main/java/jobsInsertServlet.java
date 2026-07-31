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

public class jobsInsertServlet extends HttpServlet {
	private Connection connection;
	private ResultSet lookupResults;
	private ResultSetMetaData metadata;
	private int mysqlUpdateValue;
	private int[] updateReturnValues;
	private PreparedStatement pstatement;
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        // boolean goodCred = false;
    	// String inUserName = request.getParameter("username");
        // String inPassword = request.getParameter("password");
        // String credQuery = "select * from usercrError executing sql statement:
        
    	String jnum = request.getParameter("jnum");
        String jname = request.getParameter("jname");
        String numworkers = request.getParameter("numworkers");
        String city = request.getParameter("city");
        String credQuery = "insert into jobs VALUES (?, ?, ?, ?)";
        
        
        String message = "";
        
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        try {
        	getClientDBConnection();
        	
        	pstatement = connection.prepareStatement(credQuery);
        	pstatement.setString(1, jnum);
        	pstatement.setString(2, jname);
        	pstatement.setString(3, numworkers);
        	pstatement.setString(4, city);
        	        	
        	
        	
    		// update query
    		pstatement.executeUpdate();
    		
    		message += ("<td style=\"color: black\"> New Shipment Record (" + jnum + ", " + jname + ", " + numworkers + ", " + city + ") - successfully entered into database</td>");
    		
        } catch (SQLException e)
        {
        	message += ("<tr><td><b>Error executing sql statement:</b><br>" + e.getMessage() + "</tr></td>");
            
        }
        
        HttpSession session = request.getSession();
        session.setAttribute("message", message);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/front-end-pages/dataEntryHome.jsp");
    	 dispatcher.forward(request, response);
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