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
                                                    <label class="radio" for="materialRadio${material.id}">
                                                        ${material.name}</label></td>
                                                <td><label class="radio" for="materialRadio${material.id}">
                                                        ${material.unit}</label></td>
                                                <td><label class="radio" for="materialRadio${material.id}">
                                                        ${material.count}</label></td>
                                                <td><label class="radio" for="materialRadio${material.id}">
                                                        ${material.cost}</label></td>
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
                        <a class="btn btn-default" data-toggle="modal" href="#myModal">
                            <table width="100%">
                                <tr>
                                    <td align="left" width="20"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></td>
                                    <td align="center"> Добавить</td>
                                </tr>                        
                            </table>
                        </a>
                        <button tupe="submit" class="btn btn-default" value="edit">
                            <table width="100%">
                                <tr>
                                    <td align="left" width="20"><span class="glyphicon glyphicon-refresh" aria-hidden="true"></span></td>
                                    <td align="center"> Редактировать</td>
                                </tr> 
                            </table>
                        </button>
                        <button tupe="submit" class="btn btn-default" value="delete" formaction="/materials/deleteMaterial/materialId">
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
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">Добавление материала</h4>
                    </div
                    <formSpring:form modelAttribute="material" cssClass="form-horizontal" action="/materials/addMaterial">
                    <div class="modal-body">
                        <div class="control-group">
                            <label class="control-lable" for="name">Наименование материала</label>
                            <div class="controls">
                                <input type="text" class="input-xlarge" id="name">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-lable" for="unit">Единицы измерения</label>
                            <div class="controls">
                                <select id="unit">
                                    <c:forEach items="${units}" var="unit">
                                        <option value="${unit.id}">${unit.unit}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-lable" for="count">Количество</label>
                            <div class="controls">
                                <input type="text" class="input-xlarge" id="count">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-lable" for="cost">Цена одной единицы</label>
                            <div class="controls">
                                <input type="text" class="input-xlarge" id="cost">
                            </div>
                        </div>
                        <button type="subbmit" formtarget="_parent" formaction="/materials/addMaterial" class="btn btn-primary">Сохранить</button>
                        
                    </formSpring:form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                        <button type="subbmit" formtarget="_parent" formaction="/materials/addMaterial" class="btn btn-primary">Сохранить</button>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>        

</page:mainTamplate>
