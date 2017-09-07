/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.webapps;

import com.webapp.model.MySQLAccess;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author hugo
 */
public class Supprimer extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        response.setContentType("text/html");
		PrintWriter out = response.getWriter();

        ServletContext context = getServletContext();
        ArrayList<String> param = (ArrayList<String>)context.getAttribute("param");

        MySQLAccess mysql = new MySQLAccess(param);

        if(mysql.delete(request.getParameter("d"))){
            request.getRequestDispatcher("refresh.jsp").forward(request, response);
        }else {
            out.println("Erreur SQL");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        doGet(request, response);
    }

}
