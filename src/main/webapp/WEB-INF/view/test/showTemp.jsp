<%-- 
    Document   : showTemp
    Created on : 07.09.2017, 14:29:21
    Author     : dazz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h2>Выберите материалы:</h2>
        <form:form action="/test/result">
            <form:checkboxes items="${materialsNames}" path="masStr"/>
            <input type="submit" value="Отправить">
        </form:form>
    </body>
</html>
