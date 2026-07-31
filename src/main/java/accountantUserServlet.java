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

public class accountantUserServlet extends HttpServlet {
	private Connection connection;
	private Statement statement;
	private int mysqlUpdateValue;
	private int[] updateReturnValues;
	private CallableStatement cs;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        // boolean goodCred = false;
    	// String inUserName = request.getParameter("username");
        // String inPassword = request.getParameter("password");
        // String credQuery = "select * from usercrError executing sql statement:
        
    	
    	ResultSet lookupResults;
    	ResultSetMetaData metadata;
    	String value = request.getParameter("radio");
    	String query = "";
        
        String message = "";
        
        String sumParts = "{call Get_The_Sum_Of_All_Parts_Weights()}";
        String maxSupp = "{call Get_The_Maximum_Status_Of_All_Suppliers()}";
        String totalShip = "{call Get_The_Total_Number_Of_Shipments()}";
        String getJob = "{call Get_The_Name_Of_The_Job_With_The_Most_Workers()}";
        String list = "{call List_The_Name_And_Status_Of_All_Suppliers()}";
        
        
        
        
         try {
        	getClientDBConnection();
        	
        	switch (value) 
        	{
        		case "1":
        			query = "select MAX(status) from suppliers";
        			// lookupResults = statement.executeQuery(query);
        			cs = connection.prepareCall(maxSupp);
        			lookupResults = cs.executeQuery();
        			lookupResults.next();
            		message += ("<tr>");
            		message += ("<td>Maximum_Status_Of_All_Suppliers</td>");
            		message += ("</tr>");
            		message += ("<tr>");
            		message += ("<td>"+ lookupResults.getInt(1) + "</td>");
            		message += ("</tr>");
            		break;
            		
        		case "2":
        			query = "select SUM(weight) from parts";
        			cs = connection.prepareCall(sumParts);
        			lookupResults = cs.executeQuery();
        			lookupResults.next();
            		message += ("<tr>");
            		message += ("<td>TOTAL_WEIGHT_OF_ALL_PARTS</td>");
            		message += ("</tr>");
            		message += ("<tr>");
            		message += ("<td>"+ lookupResults.getInt(1) + "</td>");
            		message += ("</tr>");
            		break;
            		
        		case "3":
        			query = "select COUNT(*) from shipments";
        			cs = connection.prepareCall(totalShip);
        			lookupResults = cs.executeQuery();
        			lookupResults.next();
            		message += ("<tr>");
            		message += ("<td>TOTAL_NUMBER_OF_SHIPMENTS</td>");
            		message += ("</tr>");
            		message += ("<tr>");
            		message += ("<td>"+ lookupResults.getInt(1) + "</td>");
            		message += ("</tr>");
            		break;
            		
        		case "4":
        			query = "select jname, jnum from jobs where numworkers = ( select MAX(numworkers) from jobs )";
        			cs = connection.prepareCall(getJob);
        			lookupResults = cs.executeQuery();
        			lookupResults.next();
            		message += ("<tr>");
            		message += ("<td>jname</td><td>jnum</td>");
            		message += ("</tr>");
            		message += ("<tr>");
            		message += ("<td>"+ lookupResults.getString(1) + "</td><td>" + lookupResults.getString(2) + "</td>");
            		message += ("</tr>");
            		break;
            		
        		case "5":
        			query = "select sname, status from suppliers";
        			cs = connection.prepareCall(list);
        			lookupResults = cs.executeQuery();
        			metadata = lookupResults.getMetaData();
            		int count = metadata.getColumnCount();
        			lookupResults.next();
            		message += ("<tr>");
            		
            		for(int i = 1; i <= count; i++)
            		{
            			message += ("<td>" + metadata.getColumnName(i) + "</td>");
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
        } catch (SQLException e)
        {
        	message += ("<tr><td><b>Error executing sql statement:</b><br>" + e.getMessage() + "</tr></td>");
        	 
        }
         HttpSession session = request.getSession();
         session.setAttribute("message", message);
         RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/front-end-pages/accountantHome.jsp");
     	 dispatcher.forward(request, response);
        
        
    }
    
    private void getClientDBConnection() {
    	Properties properties = new Properties();
    	FileInputStream in = null;
    	MysqlDataSource datasource = null;
    	
    	try {
    		in = new FileInputStream("/home/christopheralbear/Downloads/tomcat/apache-tomcat-11.0.23/webapps/ROOT/WEB-INF/conf/the-accountant.properties");
    		properties.load(in);
    		datasource = new MysqlDataSource();
    		datasource.setUrl(properties.getProperty("MYSQL_DB_URL"));
    		datasource.setUser(properties.getProperty("MYSQL_DB_USERNAME"));
    		datasource.setPassword(properties.getProperty("MYSQL_DB_PASSWORD"));
    		connection = datasource.getConnection();
    		
    		// statement = connection.createStatement();
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