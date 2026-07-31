/* Name: Christopher Albear
Course: CNT 4714 – Summer 2026 – Project Three
Assignment title: A Three-Tier Distributed Web-Based Application
Date: Friday July 31, 2026
*/

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class devVerTestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
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
    }
}