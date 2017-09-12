<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="formSpring" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="page" tagdir="/WEB-INF/tags/"%>

<page:mainTamplate>
    <jsp:attribute name="title">${action == 'add' ? 'Создать' : 'Посмотреть'} договор</jsp:attribute>

    <jsp:body> 
        <c:set var="tableHeadHeight" value="47"/>

        <formSpring:form id="mainForm" cssClass="form-horizontal" commandName="order" method="POST" action="/orders/order/${action}" role="main">
            <fieldset>
                <legend>${action == 'add' ? 'Создание' : 'Просмотр'} договора</legend>
                <div class="row">
                    <div class="form-group">
                        <formSpring:hidden path="id"/>
                        <//formSpring:hidden path="manager"/>
                        <div class='col-sm-4'>
                            <label class="col-sm-2 control-label" for="numberInput">Номер</label>
                            <div class="col-sm-10">
                                <formSpring:input id="numberInput" path="number" cssClass="form-control" autofocus="${action == 'add' ? 'true' : 'false'}"/>  
                            </div>
                        </div>
                        <div class='col-sm-3'>
                            <label class="col-sm-2 control-label" for="datePick">Дата</label>
                            <div class="col-sm-10">
                                <formSpring:input id="datePick" type="date" path="date" cssClass="form-control"/>  
                            </div>
                        </div>
                    </div>
                </div>
            </fieldset>
        </formSpring:form>

        <formSpring:form cssClass="form-horizontal" commandName="order" method="POST">
            <div class="row">
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="name">Клиент</label>
                    <div class="col-sm-8">    
                        <formSpring:input type="text" path="customer" cssClass="form-control" placeholder="Выберите клиента" required="true" disabled="true"/>
                    </div>
                    <div class="col-sm-2">
                        <button type="submit" class="btn btn-default" formaction="/customers/show_page/selectCustomer">Выбрать</button>
                    </div>
                </div>
            </div>
        </formSpring:form> 

        <div class="row">                    
            <div class="col-sm-5">
                <h4>Список оказанных услуг</h4>
                <form id="services" class="form">
                    <div class="panel panel-info">
                        <table class="table table-condensed table-bordered table-hover">
                            <thead>
                                <tr class="info" role="row" height="${tableHeadHeight}">
                                    <th width="45px">№</th>
                                    <th>Наименование</th>
                                    <th whidth="10%">Стоимость</th>
                                </tr>
                            </thead>
                            <tfoot>
                                <tr>
                                    <td colspan="2"><h4>Итого:</h4></td>
                                    <td></td>
                                </tr>
                            </tfoot>
                            <tbody>
                                <c:forEach items="${order.providedservicesList}" var="providedService" varStatus="servCount">
                                    <tr>
                                        <th class="info" style="padding: 5px; margin-left: 0px">
                                            <input type="radio" name="indexPrServ" id="serviceRadio${servCount.index}" value="${servCount.index}">
                                            ${servCount.count}
                                        </th>
                                        <td style="padding: 0; margin-left: 0px">
                                            <div class="radio" style="padding: 0;">
                                                <label style="padding: 5px; margin-left: 0px" class="radio" for="serviceRadio${servCount.index}">${providedService.service.name}</label>
                                            </div>
                                        </td>
                                        <td style="padding: 0; margin-left: 0px">
                                            <div class="radio" style="padding: 0;">
                                                <label style="padding: 5px; margin-left: 0px" class="radio" for="serviceRadio${servCount.index}">${providedService.service.cost}</label>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>                                    
                            </tbody>
                        </table>
                    </div>
                </form>
                <formSpring:form modelAttribute="order">
                    <div class="modal-footer">
                        <formSpring:button class="btn btn-primary" formaction = "/providedServices/show_page/selectServices">
                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Добавить
                        </formSpring:button>
                        <button form="services" class="btn btn-info" formaction="/orders/order/service_delete">
                            <span class="glyphicon glyphicon-minus" aria-hidden="true"></span> Удалить
                        </button>
                        <formSpring:button class="btn btn-default" formaction="/orders/order/all_services_delete">
                            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Очистить
                        </formSpring:button>    
                    </div>
                </formSpring:form>
            </div>
            <div class="col-sm-7"> 
                <h4>Список израсходованных материалов</h4>

                <form id="materials" class="form">
                    <div class="panel panel-info">
                        <table class="table table-condensed table-bordered table-hover">
                            <thead>
                                <tr class="info" role="row" height="${tableHeadHeight}">
                                    <th class="info" width="45px">№</th>
                                    <th >Наименование</th>
                                    <th width="10%">Ед. измерения</th>
                                    <th width="20%">Количество</th>
                                    <th width="15%">Стоимость</th>
                                </tr>
                            </thead>
                            <tfoot>
                                <tr>
                                    <td colspan="4"><h4>Итого:</h4></td>
                                    <td></td>
                                </tr>
                            </tfoot>
                            <tbody>
                                <c:forEach items="${order.usedmaterialsList}" var="usedMaterial" varStatus="matCount">
                                    <tr>
                                        <th class="info"  style="padding: 5px; margin-left: 0px">
                                            <input type="radio" name="indexUsMat" id="materialRadio${matCount.index}" value="${matCount.index}">
                                            ${matCount.count}</th>
                                        <//formSpring:hidden path="id"/>
                                        <td style="padding: 0; margin-left: 0px">
                                            <div class="radio" style="padding: 0;">
                                                <label style="padding: 5px; margin-left: 0px" class="radio" for="materialRadio${matCount.index}">${usedMaterial.material.name}</label>
                                            </div>
                                        </td>
                                        <td style="padding: 0; margin-left: 0px">
                                            <div class="radio" style="padding: 0;">
                                                <label style="padding: 5px; margin-left: 0px" class="radio" for="materialRadio${matCount.index}">${usedMaterial.material.unit}</label>
                                            </div>
                                        </td>
                                        <td style="padding: 0; margin-left: 0px">
                                            <div class="radio" style="padding: 0;">
                                                <label style="padding: 5px; margin-left: 0px" class="radio" for="materialRadio${matCount.index}">${usedMaterial.count}</label>
                                            </div>
                                        </td>
                                        <td style="padding: 0; margin-left: 0px">
                                            <div class="radio" style="padding: 0;">
                                                <label style="padding: 5px; margin-left: 0px" class="radio" for="materialRadio${matCount.index}">${usedMaterial.material.cost}</label>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>                                    
                            </tbody>                                        
                        </table>
                    </div>
                </form>
                <formSpring:form modelAttribute="order">
                    <div class="modal-footer">
                        <formSpring:button class="btn btn-primary" formaction = "/usedMaterials/show_page/selectMaterials">
                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Добавить
                        </formSpring:button>
                        <button form="materials" class="btn btn-info" type="submit" formaction="/orders/order/material_delete">
                            <span class="glyphicon glyphicon-minus" aria-hidden="true"></span> Удалить
                        </button>
                        <formSpring:button class="btn btn-default" formaction="/orders/order/all_materials_delete">
                            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Очистить
                        </formSpring:button>    
                    </div>
                </formSpring:form>
            </div>
        </div>
        <div class="modal-footer">
            <button form="mainForm" class="btn btn-primary">
                Сохранить
            </button>
            <a class="btn btn-default" href="/orders/showAllOrders">
                Отмена
            </a>
        </div>
    </jsp:body>
</page:mainTamplate>
