<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="formSpring" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="page" tagdir="/WEB-INF/tags/"%>

<page:mainTamplate>
    <jsp:attribute name="currentPage">${action == 'add' ? 'addOrder' : ''}</jsp:attribute>
    <jsp:attribute name="title">Тест AJAX</jsp:attribute>

    <jsp:body> 
        <legend>Даты посещения 
            <a href="#" onclick="">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
            </a>
        </legend>
        <formSpring:form id="form" cssClass="form-horizontal" commandName="visitDate" method="POST">            
            <div class="input-group col-sm-3">
                <formSpring:input type="date" id="date1" 
                                  path="visit_date"  
                                  cssClass="form-control" 
                                  onblur=""/>  
                <span class="input-group-btn">
                    <button class="btn btn-default" 
                            onclick="" 
                            type="button">
                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                    </button>
                </span>
            </div>
        </formSpring:form>
    </jsp:body>
</page:mainTamplate>
