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
                                <table id="table" class="table table-condensed table-bordered table-hover">
                                    <thead>
                                        <tr class="info" role="row">
                                            <th class="info" width="5%" valign="middle">№</th>
                                            <th width="50%">Наименование</th>
                                            <th width="10%">Ед. измерения</th>
                                            <th width="20%">Количество</th>
                                            <th width="15%">Стоимость</th>
                                        </tr>
                                    </thead> 
                                    <tbody>

                                        <c:forEach items="${materials}" var="material" varStatus="materialNumber">
                                            <tr>
                                                <th class="info">
                                                    <input type="radio" name="id" id="materialRadio${material.id}" value="${material.id}">
                                                    ${materialNumber.count}</th>
                                                <td>
                                                    <div class="radio">
                                                        <label for="materialRadio${material.id}">
                                                            <text>${material.name}</text>
                                                        </label>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="radio">                                                        
                                                        <label for="materialRadio${material.id}">
                                                            <text>${material.unit}</text>
                                                        </label>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="radio">
                                                        <label for="materialRadio${material.id}">
                                                            <text><fmt:formatNumber value="${material.count}"/></text>
                                                        </label>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="radio">
                                                        <label for="materialRadio${material.id}">
                                                            <text><fmt:formatNumber value="${material.cost}" minFractionDigits="4"/></text>
                                                        </label>
                                                    </div>
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
                    <div class="btn-group btn-group-vertical" role="group" aria-label="...">
                        <button tupe="submit" class="btn btn-default" formaction="/materials/material/show_page/add">
                            <table width="100%">
                                <tr>
                                    <td align="left" width="20"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></td>
                                    <td align="center"> Добавить</td>
                                </tr>                        
                            </table>
                        </button>
                        <button tupe="submit" class="btn btn-default" formaction="/materials/material/show_page/edit">
                            <table width="100%">
                                <tr>
                                    <td align="left" width="20"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></td>
                                    <td align="center"> Редактировать</td>
                                </tr> 
                            </table>
                        </button>
                        <button tupe="button" class="btn btn-default" value="delete" formaction="/materials/material/delete">
                            <table width="100%">
                                <tr>
                                    <td align="left" width="20"><span class="glyphicon glyphicon-minus" aria-hidden="true"></span></td>
                                    <td align="center"> Удалить</td>
                                </tr>                        
                            </table>
                        </button>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="btn-group btn-group-vertical" data-toggle="buttons">
                    <label class="btn btn-primary active">
                        <input type="radio" name="options" id="option1" autocomplete="off" checked> <div class="col-lg-12">Radio 1</div>
                    </label>
                    <c:forEach items="${materials}" var="material" varStatus="materialNumber">
                        <label class="btn btn-primary">
                            <input type="radio" name="id" id="option${material.id}" autocomplete="off" value="${material.id}">
                            <div class="col-lg-5">                             
                                ${material.name}
                            </div>
                            <div class="col-lg-1">   
                                ${materialNumber.count}
                            </div>
                            <div class="col-lg-1">   
                                ${material.unit}
                            </div>
                            <div class="col-lg-2">   
                                <fmt:formatNumber value="${material.count}"/>
                            </div>
                            <div class="col-lg-2">   
                                <fmt:formatNumber value="${material.cost}" minFractionDigits="4"/>
                            </div>
                        </label>
                    </c:forEach>
                </div>
            </div>
        </formSpring:form> 
    </jsp:body>        

</page:mainTamplate>
