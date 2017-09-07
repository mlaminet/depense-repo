/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.webapps;

import com.webapp.model.MySQLAccess;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
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
public class Desc extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        response.setContentType("text/html");
		PrintWriter out = response.getWriter();

        ServletContext context = getServletContext();
        ArrayList<String> param = (ArrayList<String>)context.getAttribute("param");

        MySQLAccess mysql = new MySQLAccess(param);

        Enumeration<String> e = request.getParameterNames();
        ArrayList<String> keys = new ArrayList<String>();
        ArrayList<String> values = new ArrayList<String>();

        int i = 0;
		while(e.hasMoreElements()){
                    keys.add(e.nextElement());
                }

        Collections.sort(keys);
        String id = "";
        for (int j = 0; j < keys.size(); j++) {
            String key = keys.get(j);
            if(!key.equals("Valider") && !key.equals("4-id")){
                values.add(request.getParameter(key));
                i++;
            }
        }

        if(mysql.insertDesc(values)){
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
