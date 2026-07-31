/* Name: Christopher Albear
Course: CNT 4714 – Summer 2026 – Project Three
Assignment title: A Three-Tier Distributed Web-Based Application
Date: Friday July 31, 2026
*/

import com.mysql.cj.jdbc.MysqlDataSource;
	

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

import javax.swing.JOptionPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class authenticationServlet extends HttpServlet {
	private Connection connection;
	private ResultSet lookupResults;
	private PreparedStatement pstatement;
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        boolean goodCred = false;
    	String inUserName = request.getParameter("username");
        String inPassword = request.getParameter("password");
        String credQuery = "select * from usercredentials where login_username = ? and login_password = ?";
        
        try {
        	getDBConnection();
        	
        	pstatement = connection.prepareStatement(credQuery);
        	pstatement.setString(1, inUserName);
        	pstatement.setString(2, inPassword);
        	
        	lookupResults = pstatement.executeQuery();
        	
        	if(lookupResults.next()) {	
        		goodCred = true;
        	}else {
        		goodCred = false;
        	}
        	
        	
        }catch (SQLException sqlexception)
        {
        	JOptionPane.showMessageDialog(null, "Error: SQLEXCEPTION", "MAJOR ERROR- ERROR", JOptionPane.ERROR_MESSAGE);
        }
        
        if(goodCred == true){
        	// found the user
    		switch (inUserName) {
    		case "root":
    			// redirect to root homepage
    			response.sendRedirect("/front-end-pages/rootHome.jsp");
    			break;
    		case "client":
    			response.sendRedirect("/front-end-pages/clientHome.jsp");
    			break;
    		case "dataentry":
    			response.sendRedirect("/front-end-pages/dataEntryHome.jsp");
    			break;
    		case "theaccountant":
    			response.sendRedirect("/front-end-pages/accountantHome.jsp");
    			break;
    		}
        }else{
        	response.sendRedirect("/front-end-pages/Error.jsp");
        }
        
    	
    }
    
    private void getDBConnection() {
    	Properties properties = new Properties();
    	FileInputStream in = null;
    	MysqlDataSource datasource = null;
    	
    	try {
    		in = new FileInputStream("/home/christopheralbear/Downloads/tomcat/apache-tomcat-11.0.23/webapps/ROOT/WEB-INF/conf/system-app.properties");
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