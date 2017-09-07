<%-- 
    Document   : result
    Created on : 07.09.2017, 14:45:57
    Author     : dazz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <% String[] masStr = (String[])request.getAttribute("masStr");
        for (String st : masStr){
            out.println(st);
        }
        %>
    </body>
</html>
