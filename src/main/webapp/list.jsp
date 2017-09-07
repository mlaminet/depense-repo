<%--
    Document   : index
    Created on : 10 nov. 2010, 16:05:30
    Author     : hugo
--%>
<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="com.webapp.model.MySQLAccess"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>

        <title>Gérer ses dépences mensuellement</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <style type="text/css">
            <%@include file="style.css"%>
        </style>

    </head>
    <body>

        <div id="main">

            <div id="session">
                <h2>Session</h2>
                <ul>
                    <%
                            Enumeration e = request.getAttributeNames();
                            ArrayList<String> names = new ArrayList();
                            while (e.hasMoreElements()) {
                                names.add(e.nextElement().toString());
                            }

                            ServletContext context = getServletContext();
                            ArrayList<String> param = (ArrayList<String>) context.getAttribute("param");

                            out.println("<li> Database : " + param.get(2) + "</li>");
                            out.println("<li> Table : " + param.get(3) + "</li>");
                            out.println("<li><a href=\"/Manager/index\">Refresh</a></li>");

                    %>
                </ul>
            </div>
            <div class="clear"></div>
            <div id="list">
                <h2>Liste</h2>
                <table>
                    <thead>
                        <tr>

                            <%
                                    SimpleDateFormat sdfStr = new SimpleDateFormat("dd MMM ");

                                    if (names.contains("meta") && names.contains("data")) {
                                        ArrayList<String> meta = (ArrayList<String>) request.getAttribute("meta");
                                        ArrayList<String[]> data = (ArrayList<String[]>) request.getAttribute("data");

                                        //*//
                                        for (int k = 1; k < meta.size(); k++) {
                                            String first = meta.get(k).substring(0, 1).toUpperCase();
                                            if (k == 1) {
                                                out.println("<td class=\"first\"><a href=order.do?orderby=" + meta.get(k) + ">" + first + meta.get(k).substring(1) + "</a></td>");
                                            } else {
                                                out.println("<td><a href=order.do?orderby=" + meta.get(k) + ">" + first + meta.get(k).substring(1) + "</a></td>");
                                            }
                                        }
                            %>
                            <td>Modifier</td>
                            <td class="last">Supprimer</td>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                                                                for (int i = data.size() - 1; i >= 0; i--) {
                                                                    out.println("<tr>");
                                                                    for (int j = 1; j < meta.size(); j++) {
                                                                        if (j == 3) {
                                                                            StringTokenizer stk = new StringTokenizer(data.get(i)[j], "-");
                                                                            int nb = stk.countTokens();
                                                                            int[] toks = new int[nb];

                                                                            for (int k = 0; k < nb; k++) {
                                                                                String kk = stk.nextToken().trim();
                                                                                toks[k] = Integer.valueOf(kk);
                                                                                if (k == 1) {
                                                                                    toks[k]--;
                                                                                }
                                                                            }

                                                                            Date date = new Date(toks[0], toks[1], toks[2]);
                                                                            out.println("<td>" + sdfStr.format(date).toString() + "</td>");
                                                                        } else if (j == 2) {
                                                                            out.println("<td>" + data.get(i)[j] + " &#8364; </td>");
                                                                        } else if (j == 1) {
                                                                            out.println("<td class=\"first\">" + data.get(i)[j] + "</td>");
                                                                        } else {
                                                                            out.println("<td>" + data.get(i)[j] + "</td>");
                                                                        }
                                                                    }
                        %>
                    <td><a href="modifier.do?d=<%=data.get(i)[0]%>">Modifier</a></td>
                    <td class="last"><a href="supprimer.do?d=<%=data.get(i)[0]%>">Supprimer</a></td>
                    <%
                                    out.println("</tr>");
                                }
                            } //Fin if(names.containes(..)
                            //*/
%>
                    </tbody>
                </table>
            </div>
            <div id="actions">
                <h2>Actions</h2>
                <form name="AddForm" action="add.do" method="get">
                    <fieldset>
                        <legend>Ajouter une entrée</legend>
                        <%
                                Date now = new Date();
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                ArrayList<String[]> str;
                                boolean hasList = false;

                                if (names.contains("liste")) {
                                    str = (ArrayList<String[]>) request.getAttribute("liste");
                        %>
                        <div><label for="1-description">Description : </label>
                            <select name="1-description">
                                <option value="<%= str.get(0)[1]%>"><%= str.get(0)[1]%></option>
                                <%
                                                                    if (names.contains("descs")) {
                                                                        ArrayList<String> descs = (ArrayList<String>) request.getAttribute("descs");
                                                                        for (int i = 0; i < descs.size(); i++) {
                                                                            if (!descs.get(i).equals(str.get(0)[1])) {
                                %>
                                <option value="<%= descs.get(i)%>"><%= descs.get(i)%></option>
                                <%
                                                                            }
                                                                        }
                                                                    }
                                %>
                            </select>
                        </div>
                        <div><label for="2-montant">Montant : </label><input value="<%= str.get(0)[2]%>" type="text" name="2-montant" /></div>
                        <div><label for="3-date">Date : </label><input value="<%= str.get(0)[3]%>" type="text" name="3-date" /></div>
                        <div><input value="<%= str.get(0)[0]%>" type="hidden" name="4-id"/></div>
                        <div class="f-right">
                            <input id="submit" type="submit" name="Valider" />
                        </div>
                        <div class="clear"></div>
                        <%
                                                        } else {
                        %>
                        <div><label for="1-description">Description : </label>
                            <select name="1-description">
                                <%
                                                                    if (names.contains("descs")) {
                                                                        ArrayList<String> descs = (ArrayList<String>) request.getAttribute("descs");
                                                                        for (int i = 0; i < descs.size(); i++) {
                                %>
                                <option value="<%= descs.get(i)%>"><%= descs.get(i)%></option>
                                <%
                                                                        }
                                                                    }
                                %>
                            </select>
                        </div>
                        <div><label for="2-montant">Montant : </label><input value="" type="text" name="2-montant" /></div>
                        <div><label for="3-date">Date : </label><input value="<%= sdf.format(now).toString()%>" type="text" name="3-date" /></div>
                        <div><input value="" type="hidden" name="4-id"/></div>
                        <div class="f-right">
                            <input id="submit" type="submit" name="Valider" />
                        </div>
                        <div class="clear"></div>
                        <% }%>
                    </fieldset>
                </form>

                <form name="DescForm" action="desc.do" method="get">
                    <fieldset>
                        <legend>Ajouter une description</legend>
                        <div><label for="description">Description : </label><input value="" type="text" name="description" /></div>
                        <input id="submit" type="submit" name="Valider" />
                    </fieldset>
                </form>
                <h2>Statistiques</h2>
                <div>
                    <fieldset>
                        <legend>Par mois</legend>
                        <%
                                SimpleDateFormat sdfM = new SimpleDateFormat("MMMM");
                                if (names.contains("mpm")) {
                                    ArrayList<String> mpm = (ArrayList<String>) request.getAttribute("mpm");
                                    for (int i = 0; i < mpm.size(); i += 2) {
                                        StringTokenizer stk = new StringTokenizer(mpm.get(i).toString(), "-");
                                        int nb = stk.countTokens();
                                        int[] toks = new int[nb];

                                        for (int k = 0; k < nb; k++) {
                                            String kk = stk.nextToken().trim();
                                            toks[k] = Integer.valueOf(kk);
                                            if (k == 1) {
                                                toks[k]--;
                                            }
                                        }
                                        Date date2 = new Date(toks[0], toks[1], 01);

                        %>
                        <h3><%=sdfM.format(date2).toString().substring(0, 1).toUpperCase() + sdfM.format(date2).toString().substring(1, sdfM.format(date2).toString().length()) + " " + String.valueOf(toks[0])%> : <span class="chiffre"><%= mpm.get(i + 1).toString().concat(" &#8364;")%></span></h3>
                        <%
                                    }
                                }
                        %>
                    </fieldset>
                    <fieldset class="colS">
                        <legend>Par description</legend>
                        <%
                                if (names.contains("mpd")) {
                                    ArrayList<String> mpd = (ArrayList<String>) request.getAttribute("mpd");
                                    for (int i = 0; i < mpd.size(); i += 2) {
                        %>
                        <div><%=mpd.get(i).toString()%> : <span class="chiffre"><%= mpd.get(i + 1).toString().concat(" &#8364;")%></span></div>
                        <%
                                    }
                                }
                        %>
                    </fieldset>
                    <fieldset class="colS">
                        <legend>Par mois et par description</legend>
                        <%
                                if (names.contains("mpmd")) {
                                    ArrayList<String[]> mpmd = (ArrayList<String[]>) request.getAttribute("mpmd");
                                    for (int i = 0; i < mpmd.size(); i++) {
                                        String curent = mpmd.get(i)[0].toString();
                                        String prev = "";
                                        if (i > 0) {
                                            prev = mpmd.get(i - 1)[0].toString();
                                        }
                                        StringTokenizer stk = new StringTokenizer(mpmd.get(i)[0].toString(), "-");
                                        int nb = stk.countTokens();
                                        int[] toks = new int[nb];

                                        for (int k = 0; k < nb; k++) {
                                            String kk = stk.nextToken().trim();
                                            toks[k] = Integer.valueOf(kk);
                                            if (k == 1) {
                                                toks[k]--;
                                            }
                                        }
                                        Date date2 = new Date(toks[0], toks[1], 01);


                                        if (i == 0) {
                        %>
                        <h3><%=sdfM.format(date2).toString().substring(0, 1).toUpperCase() + sdfM.format(date2).toString().substring(1, sdfM.format(date2).toString().length()) + " " + String.valueOf(toks[0])%> </h3>
                        <div class="in"><%=mpmd.get(i)[1].toString()%> : <span class="chiffre"><%= mpmd.get(i)[2].toString().concat(" &#8364;")%></span></div>
                        <%
                                                            } else {
                                                                if (curent.equals(prev)) {
                        %>
                        <div class="in"><%=mpmd.get(i)[1].toString()%> : <span class="chiffre"><%= mpmd.get(i)[2].toString().concat(" &#8364;")%></span></div>
                        <%
                                                                } else {
                        %>
                        <h3><%=sdfM.format(date2).toString().substring(0, 1).toUpperCase() + sdfM.format(date2).toString().substring(1, sdfM.format(date2).toString().length()) + " " + String.valueOf(toks[0])%> </h3>
                        <div class="in"><%=mpmd.get(i)[1].toString()%> : <span class="chiffre"><%= mpmd.get(i)[2].toString().concat(" &#8364;")%></span></div>
                        <%
                                            }
                                        }
                                    }
                                }
                        %>
                    </fieldset>
                </div>
            </div>
            <div class="clear"></div>
        </div>

    </body>
</html>
