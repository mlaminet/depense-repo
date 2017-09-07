/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.webapps;

import com.webapp.model.MySQLAccess;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hugo
 */
public class Index extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        response.setContentType("text/html");
		PrintWriter out = response.getWriter();

        ServletContext context = getServletContext();
        ArrayList<String> param = (ArrayList<String>)context.getAttribute("param");

        MySQLAccess mysql = new MySQLAccess(param);

        request.setAttribute("data", mysql.getListReponses());
        request.setAttribute("meta", mysql.getMetaDatas());
        request.setAttribute("mpm", mysql.getMpMounth());
        request.setAttribute("mpd", mysql.getMpDesc());
        request.setAttribute("mpmd", mysql.getMpDescPMounth());
        request.setAttribute("descs", mysql.getDescs());
        request.getRequestDispatcher("list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        doGet(request, response);
    }

}
