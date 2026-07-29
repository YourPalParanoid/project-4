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

public class rootServlet extends HttpServlet {
	private Connection connection;
	private ResultSet lookupResults;
	private PreparedStatement pstatement;
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        // boolean goodCred = false;
    	// String inUserName = request.getParameter("username");
        // String inPassword = request.getParameter("password");
        // String credQuery = "select * from usercredentials where login_username = ? and login_password = ?";
        
        String query = request.getParameter("cmd");
        
        try {
        	getRootDBConnection();
        	
        	pstatement = connection.prepareStatement(query);
        	lookupResults = pstatement.executeQuery();
        	
        	//need to figure out how to display this lookupresult
        	System.out.println("hi");
        	
        	response.setContentType("text/html");
            PrintWriter out = response.getWriter();


            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\">");
            out.println("<meta charset=\"utf-8\">");

            out.println("<head>");
            out.println("<title>Testing WebApp Package Structure </title>");
            out.println("</head>");

            out.println("<body>");
            out.println("<h1 style=\"color: black\"> HELLO!!!</h1>");
            out.println("</body>");
            out.println("</html>");
            out.close();
        	
        } catch (SQLException e)
        {
        	e.printStackTrace();
        }
        
    	
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