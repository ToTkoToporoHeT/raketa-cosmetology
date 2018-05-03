<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="formSpring" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="page" tagdir="/WEB-INF/tags/"%>

<page:mainTamplate>
    <jsp:attribute name="currentPage">${action == 'add' ? 'addOrder' : ''}</jsp:attribute>
    <jsp:attribute name="title">${action == 'add' ? 'Создать' : 'Посмотреть'} договор</jsp:attribute>

    <jsp:body> 
        <script>
            function radioChecked(radioName)
            {
                var inp = document.getElementsByName(radioName);
                for (var i = 0; i < inp.length; i++) {
                    if (inp[i].type === "radio" && inp[i].checked) {
                        return true;
                    }
                }
                return false;
            }

            function showError(container, errorMessage) {
                container.className = 'error';
                var msgElem = document.createElement('span');
                msgElem.className = "error-message";
                msgElem.innerHTML = errorMessage;
                container.appendChild(msgElem);
            }

            function resetError(container) {
                container.className = '';
                if (container.lastChild.className == "error-message") {
                    container.removeChild(container.lastChild);
                }
            }

            function validateAllForm(form) {
                var elems = form.elements;
                var result = true;

                resetError(elems.number.parentNode);
                if (!elems.number.value) {
                    //showError(elems.number.parentNode, 'Введите номер договора!');
                    document.getElementById('numberdiv').setAttribute('class', 'form-group has-error has-feedback col-sm-5 col-md-4 col-lg-4');
                    result = false;
                }
                document.getElementById('numbertext').setAttribute('class', 'col-sm-10');

                resetError(elems.prepare_date.parentNode);
                if (!elems.prepare_date.value) {
                    showError(elems.prepare_date.parentNode, 'Введите дату!');
                    result = false;
                }
                document.getElementById('datatext').setAttribute('class', 'col-sm-10');

                return result;
            }
        </script>

        <formSpring:form id="mainForm" cssClass="form-horizontal" commandName="orders" method="POST" action="/orders/order/${action}" role="main">
            <fieldset>
                <legend>${action == 'add' ? 'Создание' : 'Просмотр'} договора</legend>
                <div class="row">
                    <div class="form-group">
                        <formSpring:hidden path="id"/>
                        <formSpring:hidden path="manager.id"/>
                        <div id="numberdiv" class='col-sm-5 col-md-4 col-lg-4'>
                            <label class="col-sm-2 control-label" for="numberInput">Номер</label>
                            <div id="numbertext" class="col-sm-10">
                                <formSpring:errors path="number" cssClass="label label-danger"/>
                                <formSpring:input id="numberInput" path="number" cssClass="form-control" autofocus="${action == 'add' ? 'true' : 'false'}" aria-describedby="inputError2Status"/>  
                            </div>
                        </div>
                        <div class='col-sm-4 col-md-3 col-lg-3'>
                            <label class="col-sm-2 control-label" for="datePick">Дата</label>
                            <div id="datatext" class="col-sm-10">
                                <formSpring:errors path="prepare_date" cssClass="label label-danger"/>
                                <formSpring:input id="datePick" type="date" path="prepare_date" cssClass="form-control"/>
                            </div>
                        </div>
                    </div>
                </div>
            </fieldset>
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
                        <button type="submit" class="btn btn-default" formaction="/orders/order/show_page/selectCustomer">Выбрать</button>
                    </div>
                </div>
            </div>
        </formSpring:form> 

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
                                <tbody>
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
                                    </c:forEach>                                    
                                </tbody>
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
                        <formSpring:button class="btn btn-primary" formaction = "/providedServices/show_page/selectServices">
                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Добавить
                        </formSpring:button>
                        <button form="services" class="btn btn-info" onclick="return radioChecked('indexPrServ')" formaction="/orders/order/service_delete">
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
                        <formSpring:button class="btn btn-primary" formaction = "/usedMaterials/show_page/selectMaterials"><!--//"/orders/order/show_page/select/selectMaterials">-->
                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Добавить
                        </formSpring:button>
                        <button form="materials" class="btn btn-info" onclick="return radioChecked('indexUsMat')" type="submit" formaction="/orders/order/material_delete">
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
            <div class="row">
                <p class="col-lg-3 col-md-4 col-sm-5 col-xs-12 alert alert-info pull-left">
                    Сумма к оплате: <fmt:formatNumber value="${materialsSum + serviceSum}" minFractionDigits="2" maxFractionDigits="2"/>
                </p>
                <button form="mainForm" class="btn btn-primary" type="submit"><!--onclick="return validateAllForm(this.form)"-->
                    Сохранить
                </button>
                <div class="btn-group">
                    <a href="#" class="btn btn-info">Напечатать</a>
                    <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <span class="caret"></span>
                        <span class="sr-only">Toggle Dropdown</span>
                    </button>
                    <ul class="dropdown-menu">
                        <li><a href="#">Открыть в MS Word</a>
                    </ul>
                </div>                
                <a class="btn btn-default" onclick="return confirm('Вы дейстительно хотите отменить ${action == 'add' ? 'создание' : 'редактирование'} документа?')" href="/orders/order/${action}/cancel">
                    Отмена
                </a>
            </div>
        </div>
    </jsp:body>
</page:mainTamplate>
