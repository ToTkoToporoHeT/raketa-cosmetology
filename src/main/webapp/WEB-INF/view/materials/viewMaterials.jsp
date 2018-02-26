<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="formSpring" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:mainTamplate>
    <jsp:attribute name="title">Материалы</jsp:attribute>
    <jsp:attribute name="currentPage">viewMaterials</jsp:attribute>

    <jsp:body>         
        <formSpring:form cssClass="form-horizontal" role="main" modelAttribute="material" method="post">
            <div class="row">
                <div class="col-sm-10">
                    <div class="form-group">
                        <div class="panel panel-info">
                            <div class="panel-heading">Список материалов</div> 
                            <div class="panel-body">
                                <div class="row">                                
                                    <div class="col-sm-12">
                                        <input id="search" type="text" class="form-control" placeholder="Искать...">
                                    </div>
                                </div><!-- /.row -->
                            </div>
                            <div class="controls table-fixedH">
                                <table id="table" class="table table-condensed table-hover" data-sorting="true">
                                    <thead>
                                        <tr class="info" role="row">
                                            <th data-classes="id">
                                                №</th>
                                            <th data-classes="name">
                                                Наименование</th>
                                            <th data-classes="unit" 
                                                data-breakpoints="xs sm">
                                                Ед. измерения</th>
                                            <th data-classes="count" 
                                                data-breakpoints="xs sm">
                                                Количество</th>
                                            <th data-classes="cost" 
                                                data-breakpoints="xs sm md">
                                                Стоимость</th>
                                        </tr>
                                    </thead> 
                                    <tbody>

                                        <c:forEach items="${materials}" var="material" varStatus="materialNumber">
                                            <tr class="coloring">
                                                <td>
                                                    <input type="radio" name="id" id="materialRadio${material.id}" value="${material.id}">
                                                    ${materialNumber.count}</td>
                                                <td>
                                                    ${material.name}
                                                </td>
                                                <td>
                                                    ${material.unit}
                                                </td>
                                                <td>
                                                    <fmt:formatNumber value="${material.count}"/>
                                                <td>
                                                    <fmt:formatNumber value="${material.cost}" minFractionDigits="4"/>
                                                </td>
                                            </tr>
                                        </c:forEach>

                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-2">
                    <div class="btn-group-vertical" role="group" aria-label="...">
                        <button tupe="submit" class="btn btn-default" formaction="/materials/material/show_page/add">
                            <span class="glyphicon glyphicon-plus"></span>
                            <span class="text">Добавить</span>
                        </button>
                        <button tupe="submit" class="btn btn-default" formaction="/materials/material/show_page/edit">
                            <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                            <span class="text">Редактировать</span>
                        </button>
                        <button tupe="button" class="btn btn-default" value="delete" formaction="/materials/material/delete">
                            <span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
                            <span class="text">Удалить</span>
                        </button>
                    </div>
                </div>
            </div>
        </formSpring:form> 
    </jsp:body>        

</page:mainTamplate>
