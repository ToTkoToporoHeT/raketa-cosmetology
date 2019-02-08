<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="formSpring" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="page" tagdir="/WEB-INF/tags/"%>

<page:mainTamplate>
    <jsp:attribute name="currentPage">${action == 'add' ? 'addOrder' : ''}</jsp:attribute>
    <jsp:attribute name="title">${action == 'add' ? 'Создать' : 'Посмотреть'} договор</jsp:attribute>

    <jsp:body> 
        <c:if test="${action == null || orders == null}">
            <c:redirect url="/orders/showAllOrders"/>
        </c:if>
        <!--Ссылки используемые для обработки событий на странице-->
        <c:url var="actionURL"          value="/orders/order/${action}"/>
        <c:url var="cancelURL"          value="/orders/order/cancel"/>
        <c:url var="printOrder"         value="/printer/order"/>

        <c:url var="changeCheckNumber"  value="/orders/order/changeCheckNumber"/>
        <c:url var="changeDate"         value="/orders/order/changeDate"/>
        <c:url var="chooseCustomer"     value="/orders/order/show_page/selectCustomer"/>
        <c:url var="changeVisitDate"    value="/orders/order/visit_date"/>

        <c:url var="selectService"      value="/providedServices/show_page/selectServices"/>
        <c:url var="removeService"      value="/orders/order/service_delete"/>
        <c:url var="clearTableServices" value="/orders/order/all_services_delete"/>

        <c:url var="selectMaterial"      value="/usedMaterials/show_page/selectMaterials"/>
        <c:url var="removeMaterial"      value="/orders/order/material_delete"/>
        <c:url var="clearTableMaterials" value="/orders/order/all_materials_delete"/>
        
        <fieldset>
            <legend>${action == 'add' ? 'Создание' : 'Просмотр'} договора</legend>
            <formSpring:form id="mainForm" cssClass="form-horizontal" commandName="orders" method="POST" action="${actionURL}" role="main">
                <div class="row">
                    <div class="form-group">
                        <formSpring:hidden path="id"/>
                        <formSpring:hidden path="manager.id"/>
                        <div id="numberdiv" class='col-sm-6 col-md-5 col-lg-4'>
                            <label class="col-sm-4 control-label" for="check_number">Номер чека</label>
                            <div id="numbertext" class="col-sm-8">
                                <formSpring:errors path="check_number" cssClass="label label-danger"/>
                                <formSpring:input id="check_number" path="check_number" cssClass="form-control" 
                                                  aria-describedby="inputError2Status" 
                                                  placeholder="Введите номер чека"
                                                  onblur="postCheckNumber('${changeCheckNumber}')"/>  
                            </div>
                        </div>
                        <div class='col-sm-5 col-md-4 col-lg-3'>
                            <label class="col-sm-2 control-label" for="datePick">Дата</label>
                            <div id="datatext" class="col-sm-10">
                                <formSpring:errors path="prepare_date" cssClass="label label-danger"/>
                                <formSpring:input id="datePick" type="date" path="prepare_date" cssClass="form-control" onblur="postDate('${changeDate}')"/>
                            </div>
                        </div>                        
                    </div>
                </div>
            </formSpring:form>
            <formSpring:form cssClass="form-horizontal" commandName="orders" method="POST">
                <div class="row">
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="name">Клиент</label>
                        <div class="col-sm-8"> 
                            <formSpring:errors path="customer" cssClass="label label-danger"/>
                            <formSpring:input type="text" path="customer" cssClass="form-control" placeholder="Выберите клиента" disabled="true"/>
                        </div>
                        <div class="col-sm-2">
                            <button type="submit" class="btn btn-default" formaction="${chooseCustomer}">Выбрать</button>
                        </div>
                    </div>
                </div>
            </formSpring:form> 
                <fieldset>
                    <legend>Даты посещения 
                        <a href="#" onclick="addVisitDate('${changeVisitDate}')">
                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                        </a>
                    </legend>                    
                    <formSpring:form cssClass="form-horizontal" commandName="orders" method="POST">
                        <div id="visitDates">
                            <c:set var="vDateListLength" value="${fn:length(orders.visitDatesList)}"/>
                            <c:forEach begin="1" end="${vDateListLength == 0 ? 0 : vDateListLength}" var="i">
                                <div class="visit-dates col-sm-4 col-md-3">
                                    <formSpring:hidden path="visitDatesList[${i - 1}].id"/>
                                    <formSpring:errors path="visitDatesList[${i - 1}].visit_date" cssClass="label label-danger"/>
                                    <div class="input-group" style="margin-bottom: 5px;">
                                        <formSpring:input type="date" 
                                                          path="visitDatesList[${i - 1}].visit_date"  
                                                          cssClass="form-control" 
                                                          onblur="editVisitDate('${changeVisitDate}', ${i - 1})"/>  
                                        <span class="input-group-btn">
                                            <button class="btn btn-default" 
                                                    onclick="deleteVisitDate('${changeVisitDate}', ${i - 1})" 
                                                    type="button">
                                                <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                                            </button>
                                        </span>
                                    </div>
                                    <formSpring:hidden path="visitDatesList[${i - 1}].order"/>
                                </div>
                            </c:forEach> 
                        </div>                    
                    </formSpring:form> 
                </fieldset>
            <div class="row">                    
                <div class="col-sm-5">
                    <h4>Список оказанных услуг</h4>
                    <formSpring:form id="services" cssClass="form" commandName="orders">
                        <div class="panel panel-info">
                            <div class="table-fixedH table-fixedH-small">
                                <table class="table table-condensed table-hover" data-sorting="true">
                                    <thead>
                                        <tr class="info" role="row">
                                            <th data-classes="id">
                                                №</th>
                                            <th data-classes="name">
                                                Наименование</th>
                                            <th data-classes="number">
                                                Тариф</th>
                                            <th data-classes="number">
                                                Стоимость</th>
                                        </tr>
                                    </thead>

                                    <c:set var="provadedServicesForURL" value=""/>
                                    <c:forEach items="${orders.providedservicesList}" var="providedService" varStatus="servCount">
                                        <tr class="coloring">
                                            <td>
                                                <input type="radio" name="indexPrServ" id="serviceRadio${servCount.index}" value="${servCount.index}">
                                                ${servCount.count}
                                            </td>
                                            <formSpring:hidden path="providedservicesList[${servCount.index}].id"/>
                                            <formSpring:hidden path="providedservicesList[${servCount.index}].rate"/>
                                            <formSpring:hidden path="providedservicesList[${servCount.index}].service.id"/>
                                            <td>
                                                ${providedService.service.name}
                                            </td>
                                            <td>
                                                <fmt:formatNumber value="${providedService.rate}"/>
                                            </td>                                            
                                            <td>
                                                <fmt:formatNumber value="${providedService.cost}" minFractionDigits="2"/>
                                            </td>
                                        </tr>
                                        <c:set var="provadedServicesForURL" 
                                               value="${provadedServicesForURL}service[]=${providedService.service.name}_${providedService.rate}_${providedService.cost}&"
                                               />
                                    </c:forEach>                                    

                                </table>
                            </div>
                            <table class="table table-condensed table-footer">
                                <tr>
                                    <c:set var="serviceSum" value="${0}"/>
                                    <c:forEach items="${orders.providedservicesList}" var="provService">
                                        <c:set var="serviceSum" value="${serviceSum + provService.cost * provService.rate}"/>
                                    </c:forEach>
                                    <td colspan="3">
                                        <h4>Итого:</h4>
                                    </td>
                                    <td class="number" style="width: 26%;">
                                        <h5><fmt:formatNumber value="${serviceSum}" minFractionDigits="2"/></h5>
                                    </td>
                                    <td width="17px"></td>
                                </tr>
                            </table>
                        </div>
                        <div class="modal-footer">
                            <formSpring:button class="btn btn-primary" formaction = "${selectService}">
                                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Добавить
                            </formSpring:button>
                            <button form="services" class="btn btn-info" onclick="return radioChecked('indexPrServ')" formaction="${removeService}">
                                <span class="glyphicon glyphicon-minus" aria-hidden="true"></span> Удалить
                            </button>
                            <formSpring:button class="btn btn-default" formaction="${clearTableServices}">
                                <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Очистить
                            </formSpring:button>    
                        </div>
                    </formSpring:form>
                </div>
                <div class="col-sm-7"> 
                    <h4>Список израсходованных материалов</h4>

                    <formSpring:form id="materials" cssClass="form" commandName="orders">
                        <div class="panel panel-info">
                            <div class="table-fixedH table-fixedH-small">
                                <table class="table table-condensed table-hover" data-sorting="true">
                                    <thead>
                                        <tr class="info" role="row">
                                            <th data-classes="id">
                                                №</th>
                                            <th data-classes="name">
                                                Наименование</th>
                                            <th data-classes="unit"
                                                data-breakpoints="xs sm">
                                                Ед. измерения</th>
                                            <th data-classes="number">
                                                Количество</th>
                                            <th data-classes="cost">
                                                Стоимость</th>
                                        </tr>
                                    </thead>                            
                                    <tbody>
                                        <c:set var="usedmaterialsStrForURL" value=""/>
                                        <c:forEach items="${orders.usedmaterialsList}" var="usedMaterial" varStatus="matCount">
                                            <tr class="coloring">
                                                <td>
                                                    <input type="radio" name="indexUsMat" id="materialRadio${matCount.index}" value="${matCount.index}">
                                                    ${matCount.count}
                                                </td>
                                                <formSpring:hidden path="usedmaterialsList[${matCount.index}].id"/>
                                                <formSpring:hidden path="usedmaterialsList[${matCount.index}].count"/>
                                                <formSpring:hidden path="usedmaterialsList[${matCount.index}].material.id"/>
                                                <td>
                                                    ${usedMaterial.material.name}
                                                </td>
                                                <td>
                                                    ${usedMaterial.material.unit}
                                                </td>
                                                <td>
                                                    ${usedMaterial.count}
                                                </td>
                                                <td>
                                                    <fmt:formatNumber value="${usedMaterial.cost * usedMaterial.count}" minFractionDigits="4"/>
                                                </td>
                                            </tr>
                                            <c:set var="usedmaterialsStrForURL" 
                                                   value="${usedmaterialsStrForURL}material[]=${usedMaterial.material.name}_${usedMaterial.material.unit}_${usedMaterial.count}_${usedMaterial.cost}&"
                                                   />
                                        </c:forEach>                                    
                                    </tbody>                                        
                                </table>
                            </div>
                            <table class="table table-condensed table-footer">
                                <tr>
                                    <c:set var="materialsSum" value="${0}"/>
                                    <c:forEach items="${orders.usedmaterialsList}" var="usedMaterial">
                                        <c:set var="materialsSum" value="${materialsSum + usedMaterial.cost * usedMaterial.count}"/>
                                    </c:forEach>
                                    <td colspan="3"><h4>Итого:</h4></td>
                                    <td width="15%"><h5><fmt:formatNumber value="${materialsSum}" minFractionDigits="2"/></h5></td>
                                    <td width="17px"></td>
                                </tr>
                            </table>
                        </div>
                        <div class="modal-footer">
                            <formSpring:button class="btn btn-primary" formaction = "${selectMaterial}"><!--//"/orders/order/show_page/select/selectMaterials">-->
                                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Добавить
                            </formSpring:button>
                            <button form="materials" class="btn btn-info" onclick="return radioChecked('indexUsMat')" type="submit" formaction="${removeMaterial}">
                                <span class="glyphicon glyphicon-minus" aria-hidden="true"></span> Удалить
                            </button>
                            <formSpring:button class="btn btn-default" formaction="${clearTableMaterials}">
                                <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Очистить
                            </formSpring:button>    
                        </div>
                    </formSpring:form>
                </div>
            </div>
            <div class="row modal-footer">
                <p class="col-lg-3 col-md-4 col-sm-5 col-xs-12 alert alert-info pull-left">
                    Сумма к оплате: <fmt:formatNumber value="${materialsSum + serviceSum}" minFractionDigits="2" maxFractionDigits="2"/>
                </p>
                <div class="checkbox" style="margin-top: 0px">
                    <label>
                        <input name="contract" type="checkbox" form="mainForm"/>
                        Оформить как договор
                    </label>
                </div>
                <button form="mainForm" class="btn btn-primary" type="submit"><!--onclick="return validateAllForm(this.form)"-->
                    Сохранить
                </button>
                <div class="btn-group">
                    <c:url
                        var="openInExcel"
                        value="/orders/order/openInExcel"
                        />
                    <%--value="http://192.168.1.16:8585/phpOrderPrinter/openOrderInExcel.php?staffFullName=${orders.manager}&number=${orders.check_number}&date=${orders.prepare_date}&clientFullName=${orders.customer}&address=${orders.customer.addressId}&${provadedServicesForURL}${usedmaterialsStrForURL}sum=${materialsSum + serviceSum}"--%> 
                    <%--formSpring:form id="print" action="${openInExcel}" method="GET" commandName="orders"--%>
                    <button type="submit" form="mainForm" formaction="${openInExcel}" class="btn btn-info">Открыть в MS Excel</button>
                    <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <span class="caret"></span>
                        <span class="sr-only">Toggle Dropdown</span>
                    </button>
                    <ul class="dropdown-menu">
                        <li><a href="${printOrder}">Напечатать</a>
                    </ul>
                    <!--/formSpring:form-->
                </div>              
                <a class="btn btn-default" onclick="return confirm('Вы дейстительно хотите отменить ${action == 'add' ? 'создание' : 'редактирование'} документа?')" href="${cancelURL}">
                    Отмена
                </a>
            </div>
        </fieldset>
    </jsp:body>
</page:mainTamplate>
