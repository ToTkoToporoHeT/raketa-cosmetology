<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="formSpring" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:mainTamplate>
    <jsp:attribute name="title">Материалы</jsp:attribute>
    <jsp:attribute name="currentPage">materialsTest</jsp:attribute>

    <jsp:body>
        <formSpring:form>
        <table class="table table-bordered table-hover">
            <caption>Таблица материалов</caption>
            <thead>
                <tr class="info">
                    <th class="info">№</th>
                    <th>Наименование</th>
                    <th>Ед. измер.</th>
                    <th>Кол-во</th>
                    <th>Стоимость</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${materials}" var="material" varStatus="materialsStatus">
                    <tr>
                        <th class="info" width="5%">${materialsStatus.count}</th>
                        <td width="50%">
                            <label class="radio">                                                        
                                <input type="radio" name="materialsRadio" id="materialRadio${material.id}" value="${material.id}">
                                ${material.name}
                            </label></td>
                        <td width="10%">
                            <label class="radio" for="materialRadio${material.id}">                                                        
                                <input type="radio" name="materialsRadio" id="materialRadio${material.id}" value="${material.id}">
                                ${material.unit}
                            </label>
                        </td>
                        <td width="20%">${material.count}</td>
                        <td width="15%">${material.cost}</td>               
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        </formSpring:form>

        <div class="controls">
            <c:forEach items="${materials}" var="material" varStatus="materialsStatus">


                <label class="radio">
                    <input type="radio" name="materialsRadio" id="materialRadio${materialsStatus.count}" value="${materialsStatus.count}">
                    <table class="table table-bordered table-hover">
                        <tr>
                            <td><th class="info" width="5%">${materialsStatus.count}</th>
                            <td width="50%">${material.name}</td>
                            <td width="10%">${material.unit}</td>
                            <td width="20%">${material.count}</td>
                            <td width="15%">${material.cost}</td></td>
                        </tr>
                    </table>                        
                </label>


            </c:forEach>
        </div>
    </jsp:body>
</page:mainTamplate>