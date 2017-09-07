<%-- 
    Document   : form
    Created on : 12 nov. 2010, 23:24:04
    Author     : hugo
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <title>Se Connecter pour DSI DAYS</title>
        <style type="text/css">
            <%@include file="style.css"%>
		</style>
    </head>
    <body>
        <div id="main">

            <form id="connect" class="center" name="ConnectionForm" action="connection.do" method="post">
                <fieldset >
                    <legend>Se Connecter</legend>

                    <div><label for="1-user">User : </label><input value="root" type="text" name="1-user" /></div>
                    <div><label for="2-password">Password : </label><input value="847789bh+,;:!" type="text" name="2-password" /></div>
                    <div><label for="3-db">DB : </label><input value="depenses" type="text" name="3-db" /></div>
                    <div><label for="4-table">Table : </label><input value="list" type="text" name="4-table" /></div>

                    <div><input id="submit" type="submit" name="Valider" /></div>
                </fieldset>
            </form>

        </div>
    </body>
</html>
