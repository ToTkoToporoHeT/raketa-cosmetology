<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="formSpring" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:mainTamplate>
    <jsp:attribute name="title">Материалы</jsp:attribute>
    <jsp:attribute name="currentPage">viewMaterials</jsp:attribute>

    <jsp:body>         
        <form class="form-horizontal" role="main">
            <div class="row">
                <div class="col-sm-10">
                    <div class="form-group">
                        <div class="panel panel-info">
                            <div class="panel-heading">Список материалов</div> 
                            <div class="panel-body">
                                <div class="row">                                
                                    <div class="col-sm-12">
                                        <div class="input-group">
                                            <input type="text" class="form-control" width="10%" placeholder="Искать...">
                                            <span class="input-group-btn">
                                                <button class="btn btn-default" type="button">Найти</button>
                                            </span>
                                        </div><!-- /input-group -->
                                    </div><!-- /.col-lg-6 -->
                                </div><!-- /.row -->
                            </div>
                            <table class="table table-condensed table-bordered table-hover ">
                                <thead>
                                    <tr class="info">
                                        <th class="info" width="5%" valign="middle">№</th>
                                        <th width="50%">Наименование</th>
                                        <th width="10%">Ед. измерения</th>
                                        <th width="20%">Количество</th>
                                        <th width="15%">Стоимость</th>
                                    </tr>
                                </thead> 
                                <div class="controls">
                                    <tbody>

                                        <c:forEach items="${materials}" var="material" varStatus="materialNumber">
                                            <tr>
                                                <th class="info">
                                                    <input type="radio" name="materialsRadio" id="materialRadio${material.id}" value="${material.id}">
                                                    ${materialNumber.count}</th>
                                                <td>
                                                    <div class="radio">
                                                        <label class="radio" for="materialRadio${material.id}">${material.name}</label>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="radio">
                                                        <label class="radio" for="materialRadio${material.id}">${material.unit}</label>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="radio">
                                                        <label class="radio" for="materialRadio${material.id}">${material.count}</label>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="radio">
                                                        <label class="radio" for="materialRadio${material.id}">${material.cost}</label>
                                                    </div>
                                                </td>
                                            </tr>
                                        </c:forEach>

                                    </tbody>
                                </div>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="col-sm-2">
                    <div class="btn-group btn-group-vertical" role="group" aria-label="...">
                        <a class="btn" href="/materials/material/show_page/add">
                            <table width="100%">
                                <tr>
                                    <td align="left" width="20"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></td>
                                    <td align="center"> Добавить</td>
                                </tr>                        
                            </table>
                        </a>
                        <button tupe="button" class="btn" data-target="/materials/material/show_page/edit">
                            <table width="100%">
                                <tr>
                                    <td align="left" width="20"><span class="glyphicon glyphicon-refresh" aria-hidden="true"></span></td>
                                    <td align="center"> Редактировать</td>
                                </tr> 
                            </table>
                        </button>
                        <button tupe="submit" class="btn" value="delete" formaction="/materials/deleteMaterial">
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
        </form> 
    </jsp:body>        

</page:mainTamplate>
