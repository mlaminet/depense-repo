/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.webapps;

import com.webapp.model.MySQLAccess;
import java.io.IOException;
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
public class Connection extends HttpServlet {

    @Override
	public void doGet(	HttpServletRequest request,HttpServletResponse response)
	throws IOException, ServletException{

                //On récupère la liste des noms de paramètres
		Enumeration<String> e = request.getParameterNames();
                ArrayList<String> paramNames = new ArrayList<String>();
                ArrayList<String> param = new ArrayList<String>();

                int i = 0;
		while(e.hasMoreElements()){
                    paramNames.add(e.nextElement());
                }

                Collections.sort(paramNames);
                for (int j = 0; j < paramNames.size(); j++) {
                    String key = paramNames.get(j);
                    if(!key.equals("Valider")){
                        param.add(request.getParameter(key));
                        i++;
                    }
                }

                MySQLAccess mysql = new MySQLAccess(param);

                ServletContext context = getServletContext();

                context.setAttribute("param", param);

                request.setAttribute("param", param);
                request.getRequestDispatcher("refresh.jsp").forward(request, response);
	}

    @Override
	public void doPost(	HttpServletRequest request,
			HttpServletResponse response)
			throws IOException, ServletException{

		doGet(request, response);
	}

}
