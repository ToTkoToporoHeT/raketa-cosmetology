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
        <formSpring:form cssClass="form-horizontal" commandName="order" method="POST" action="/orders/order/${action}" role="main">
            <fieldset>
                <legend>${action == 'add' ? 'Создание' : 'Просмотр'} договора</legend>
                <formSpring:hidden path="id"/>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="name">Клиент</label>
                    <div class="col-sm-8">    
                        <formSpring:input type="text" autofocus="${action == 'add' ? 'true' : ''}" path="customer" cssClass="form-control" placeholder="Выберите клиента" required="true" disabled="true"/>
                    </div>
                    <div class="col-sm-2">
                        <button type="submit" class="btn btn-default" formaction="/customers/show_page/selectCustomer">Выбрать</button>
                    </div>
                </div>
                <div class="row">                    
                    <div class="col-sm-5">
                        <div class="form-group">
                            <h4>Список оказанных услуг</h4>
                            <div class="panel panel-info">
                                <formSpring:form id="services" commandName="service" cssClass="form">
                                    <table class="table table-condensed table-bordered table-hover">
                                        <thead>
                                            <tr class="info" role="row" height="52">
                                                <th width="5%">№</th>
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
                                                        <formSpring:radiobutton path="providedservicesList[${servCount.index}].id" id="serviceRadio${providedService.id}"/>
                                                        ${servCount.count}
                                                    </th>
                                                    <td style="padding: 0; margin-left: 0px">
                                                        <div class="radio" style="padding: 0;">
                                                            <label style="padding: 5px; margin-left: 0px" class="radio" for="serviceRadio${providedService.id}">${providedService.serviceId.name}</label>
                                                        </div>
                                                    </td>
                                                    <td style="padding: 0; margin-left: 0px">
                                                        <div class="radio" style="padding: 0;">
                                                            <label style="padding: 5px; margin-left: 0px" class="radio" for="serviceRadio${providedService.id}">${providedService.serviceId.cost}</label>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>                                    
                                        </tbody>
                                    </table>
                                </formSpring:form>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <formSpring:button class="btn btn-primary" type="submit">
                                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Добавить
                            </formSpring:button>
                            <formSpring:button form="services" class="btn btn-info" formaction="/orders/order/service_delete">
                                <span class="glyphicon glyphicon-minus" aria-hidden="true"></span> Удалить
                            </formSpring:button>
                            <formSpring:button class="btn btn-default" type="submit">
                                <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Очистить
                            </formSpring:button>    
                        </div>
                    </div>
                    <div class="col-sm-7"> 
                        <div class="form-group">
                            <h4>Список израсходованных материалов</h4>
                            <div class="panel panel-info">
                                <form id="materials" class="form">
                                    <table class="table table-condensed table-bordered table-hover">
                                        <thead>
                                            <tr class="info" role="row">
                                                <th class="info" width="5%" valign="middle">№</th>
                                                <th width="50%">Наименование</th>
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
                                                            <label style="padding: 5px; margin-left: 0px" class="radio" for="materialRadio${matCount.index}">${usedMaterial.materialId.name}</label>
                                                        </div>
                                                    </td>
                                                    <td style="padding: 0; margin-left: 0px">
                                                        <div class="radio" style="padding: 0;">
                                                            <label style="padding: 5px; margin-left: 0px" class="radio" for="materialRadio${matCount.index}">${usedMaterial.materialId.unit}</label>
                                                        </div>
                                                    </td>
                                                    <td style="padding: 0; margin-left: 0px">
                                                        <div class="radio" style="padding: 0;">
                                                            <label style="padding: 5px; margin-left: 0px" class="radio" for="materialRadio${matCount.index}">${usedMaterial.count}</label>
                                                        </div>
                                                    </td>
                                                    <td style="padding: 0; margin-left: 0px">
                                                        <div class="radio" style="padding: 0;">
                                                            <label style="padding: 5px; margin-left: 0px" class="radio" for="materialRadio${matCount.index}">${usedMaterial.materialId.cost}</label>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>                                    
                                        </tbody>                                        
                                    </table>
                                </form>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <formSpring:button class="btn btn-primary" type="submit" formaction = "/usedMaterials/show_page/selectMaterials">
                                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Добавить
                            </formSpring:button>
                            <formSpring:button form="materials" class="btn btn-info" formaction="/orders/order/material_delete">
                                <span class="glyphicon glyphicon-minus" aria-hidden="true"></span> Удалить
                            </formSpring:button>
                            <formSpring:button class="btn btn-default" type="submit" formaction="/orders/order/all_materials_delete">
                                <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Очистить
                            </formSpring:button>    
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <formSpring:button class="btn btn-primary" type="submit">
                        Сохранить
                    </formSpring:button>
                    <a class="btn btn-default" href="/orders/showAllOrders">
                        Отмена
                    </a>
                </div>
            </fieldset>
        </formSpring:form>
    </jsp:body>
</page:mainTamplate>
