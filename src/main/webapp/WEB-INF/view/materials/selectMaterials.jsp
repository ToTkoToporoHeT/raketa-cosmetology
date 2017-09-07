<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="formSpring" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:mainTamplate>
    <jsp:attribute name="title">Выбор материалов</jsp:attribute>

    <jsp:body>         
        <formSpring:form cssClass="form-horizontal" role="main" modelAttribute="order" method="post" action="/usedMaterials">
            <div class="row">
                <div class="col-sm-10">
                    <div class="form-group">
                        <div class="panel panel-info">
                            <div class="panel-heading">Список материалов</div> 
                            <div class="panel-body">
                                <div class="row">                                
                                    <div class="col-sm-12">
                                        <input id="search" type="text" class="form-control" width="10%" placeholder="Искать...">
                                    </div>
                                </div><!-- /.row -->
                            </div>
                            <table id="table" class="table table-condensed table-bordered table-hover ">
                                <thead>
                                    <tr class="info" role="row">
                                        <th class="info" width="5%" valign="middle">№</th>
                                        <th width="50%">Наименование</th>
                                        <th width="10%">Единицы измерения</th>
                                        <th width="11%">Количество</th>
                                        <th width="11%">На складе</th>
                                        <th width="13%">Стоимость единицы</th>
                                    </tr>
                                </thead> 
                                <div class="controls">
                                    <tbody>
                                        <c:forEach items="${allMaterials.usedmaterialsList}" var="material" varStatus="materialNumber">
                                            <tr>
                                                <th class="info"  style="padding: 5px; margin-left: 0px">
                                                    <formSpring:checkbox value="${material.materialId.id}" path="usedmaterialsList[${materialNumber.index}].materialId.id" id="materialRadio${materialNumber.count}"/>
                                                ${materialNumber.count}</th>
                                            <td style="padding: 0; margin-left: 0px">
                                                <div class="checkbox" style="padding: 0;">
                                                    <formSpring:label style="padding: 5px; margin-left: 0px" class="checkbox form-control" for="materialRadio${materialNumber.count}" path="usedmaterialsList[${materialNumber.index}].materialId.name">${material.materialId.name}</formSpring:label>
                                                </div>
                                            </td>
                                            <td style="padding: 0; margin-left: 0px">
                                                <div class="checkbox" style="padding: 0;">
                                                    <label style="padding: 5px; margin-left: 0px" class="checkbox form-control" for="materialRadio${materialNumber.count}" >${material.materialId.unit}</label>
                                                </div>
                                            </td>
                                            <td style="padding: 0; margin-left: 0px">
                                                <div class="checkbox" style="padding: 0;">
                                                    <input type="number" style="padding: 5px; margin-left: 0px" class="checkbox form-control" for="materialRadio${materialNumber.count}" name="usedmaterialsList[${materialNumber.index}].materialId.count"/>
                                                </div>
                                            </td>
                                            <td style="padding: 0; margin-left: 0px">
                                                <div class="checkbox" style="padding: 0;">
                                                    <label style="padding: 5px; margin-left: 0px" class="checkbox form-control" for="materialRadio${materialNumber.count}" >${material.materialId.count}</label>
                                                </div>
                                            </td>
                                            <td style="padding: 0; margin-left: 0px">
                                                <div class="checkbox" style="padding: 0;">
                                                    <label style="padding: 5px; margin-left: 0px" class="checkbox form-control" for="materialRadio${materialNumber.count}" >${material.materialId.cost}</label>
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
                        <button tupe="submit" class="btn btn-default" formaction="/usedMaterials/create">
                            <table width="100%">
                                <tr>
                                    <td align="left" width="20"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span></td>
                                    <td align="center"> Выбрать</td>
                                </tr>                        
                            </table>
                        </button>
                        <button tupe="button" class="btn btn-default" value="back" formaction="/orders/order/show_page/add">
                            <table width="100%">
                                <tr>
                                    <td align="left" width="20"><span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span></td>
                                    <td align="center"> Назад</td>
                                </tr>                        
                            </table>
                        </button>
                    </div>
                </div>
            </div>
        </formSpring:form> 
    </jsp:body>        

</page:mainTamplate>
