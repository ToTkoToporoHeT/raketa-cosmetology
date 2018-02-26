<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="formSpring" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:mainTamplate>
    <jsp:attribute name="title">Выбор материалов</jsp:attribute>

    <jsp:body>         
        <formSpring:form cssClass="form-horizontal" role="main" modelAttribute="orderTemp" method="post">
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
                                            <th data-classes="id">№</th>
                                            <th data-classes="name">Наименование</th>
                                            <th data-classes="number-checkbox">Ставка</th>
                                            <th data-classes="cost">Стоимость</th>
                                        </tr>
                                    </thead> 
                                    <tbody>
                                        <c:forEach items="${allServices.providedservicesList}" var="providedService" varStatus="serviceNumber">
                                            <tr class="coloring">
                                                <td>
                                                    <formSpring:hidden path="providedservicesList[${serviceNumber.index}].id"/>
                                                    <c:if test="${providedService.service.forDelete}">
                                                        <formSpring:hidden path="providedservicesList[${serviceNumber.index}].service.id"/>
                                                        <formSpring:hidden path="providedservicesList[${serviceNumber.index}].rate"/>
                                                    </c:if>
                                                    <formSpring:checkbox value="${providedService.service.id}" 
                                                                         path="providedservicesList[${serviceNumber.index}].service.id" 
                                                                         id="serviceRadio${serviceNumber.count}" 
                                                                         disabled="${providedService.service.forDelete}"/>
                                                    ${serviceNumber.count}
                                                </td>
                                                <td>
                                                    ${providedService.service.name}
                                                </td>
                                                <td>
                                                    <formSpring:input required="" type="number" id="serviceInput${serviceNumber.count}"
                                                                      cssClass="form-control" for="serviceRadio${serviceNumber.count}" 
                                                                      path="providedservicesList[${serviceNumber.index}].rate" 
                                                                      disabled="${providedService.service.forDelete}"/>
                                                </td>
                                                <td>
                                                    <fmt:formatNumber value="${providedService.service.cost}" minFractionDigits="2"/>
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
                        <button tupe="submit" class="btn btn-default" formaction="/orders/order/selectServices">
                            <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                            <span class="text">Выбрать</span>
                        </button>
                        <button tupe="button" class="btn btn-default" value="back" formaction="/orders/order/show_page/{action}">
                            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                            <span class="text">Назад</span>
                        </button>
                    </div>
                </div>
            </div>
        </formSpring:form> 
    </jsp:body>        

</page:mainTamplate>
