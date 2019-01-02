<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="formSpring" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:mainTamplate>
    <jsp:attribute name="currentPage">error 404</jsp:attribute>
    <jsp:attribute name="title">Произошла ошибка</jsp:attribute>
    
    <jsp:body>
        <h3>${error}</h3>
        <c:forEach items="${stackTrace}" var="stackTraceElement">
            <p>${stackTraceElement}</p>
        </c:forEach>
    </jsp:body>
</page:mainTamplate>
