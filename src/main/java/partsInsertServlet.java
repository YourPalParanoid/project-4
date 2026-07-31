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

public class partsInsertServlet extends HttpServlet {
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
        
    	String pnum = request.getParameter("pnum");
        String pname = request.getParameter("pname");
        String color = request.getParameter("color");
        String weight = request.getParameter("weight");
        String city = request.getParameter("city");
        String credQuery = "insert into parts VALUES (?, ?, ?, ?, ?)";
        
        
        String message = "";
        
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();


        message +=("<!DOCTYPE html>");
        message +=("<html lang=\"en\">");
        message +=("<meta charset=\"utf-8\">");

        message +=("<head>");
        message +=("<title>Testing WebApp Package Structure </title>");
        message +=("</head>");

        message +=("<body>");
        message +=("<h1 style =\"color: black;\">PLEASE PRINT SOMETHING</h1>");
        
        
        try {
        	getClientDBConnection();
        	
        	pstatement = connection.prepareStatement(credQuery);
        	pstatement.setString(1, pnum);
        	pstatement.setString(2, pname);
        	pstatement.setString(3, color);
        	pstatement.setString(4, weight);
        	pstatement.setString(5, city);
        	
    		// update query
    		pstatement.executeUpdate();
    		
    		message +=("<td> New Shipment Record (" + pnum + ", " + pname + ", " + color + ", " + weight + ", " + city + ") - successfully entered into database</td>");
    		
        	
        	
        } catch (SQLException e)
        {
        	message +=("<tr><td><b>Error executing sql statement:</b><br>" + e.getMessage() + "</tr></td>");
        	
            
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