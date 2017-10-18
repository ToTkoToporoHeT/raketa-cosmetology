<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="formSpring" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:mainTamplate>
    <jsp:attribute name="title">Список договоров</jsp:attribute>
    <jsp:attribute name="currentPage">viewOrders</jsp:attribute>

    <jsp:body>    
        <security:authorize access="hasRole('ROLE_ROOT')" var="isRoot"/>
        <formSpring:form cssClass="form-horizontal" role="main" modelAttribute="order" method="get" action="/orders">
            <div class="row">
                <div class="col-sm-10">
                    <div class="form-group">
                        <div class="panel panel-info">
                            <div class="panel-heading">Список договоров</div> 
                            <div class="panel-body">
                                <div class="row">                                
                                    <div class="col-sm-12">
                                        <input id="search" type="text" class="form-control" placeholder="Искать...">
                                    </div>
                                </div><!-- /.row -->
                            </div>
                            <table id="table" class="table table-condensed table-bordered table-hover ">
                                <thead>
                                    <tr class="info" role="row">
                                        <th class="info" width="5%" valign="middle">№</th>
                                        <th>Номер</th>
                                        <th>Дата</th>
                                        <th>ФИО клиента</th>
                                            <c:if test="${isRoot}">
                                            <th>ФИО сотрудника</th>
                                            </c:if>
                                        <th>Сумма</th>
                                    </tr>
                                </thead> 
                                <div class="controls">
                                    <tbody>
                                        <c:forEach items="${orders}" var="order" varStatus="orderNumber">
                                            <tr>
                                                <th class="info" style="padding: 5px; margin-left: 0px">
                                                    <input type="radio" name="id" id="customerRadio${order.id}" value="${order.id}">
                                                    ${orderNumber.count}</th>
                                                <td style="padding: 0; margin-left: 0px">
                                                    <div class="radio" style="padding: 0;">
                                                        <label style="padding: 5px; margin-left: 0px" class="radio" for="customerRadio${order.id}">${order.number}</label>
                                                    </div>
                                                </td>
                                                <td style="padding: 0; margin-left: 0px">
                                                    <div class="radio" style="padding: 0;">
                                                        <label style="padding: 5px; margin-left: 0px" class="radio" for="customerRadio${order.id}">${order.prepare_date}</label>
                                                    </div>
                                                </td>
                                                <td style="padding: 0; margin-left: 0px">
                                                    <div class="radio" style="padding: 0;">
                                                        <label style="padding: 5px; margin-left: 0px" class="radio" for="customerRadio${order.id}">${order.customer}</label>
                                                    </div>
                                                </td>
                                                <c:if test="${isRoot}">
                                                    <td style="padding: 0; margin-left: 0px">
                                                        <div class="radio" style="padding: 0;">
                                                            <label style="padding: 5px; margin-left: 0px" class="radio" for="customerRadio${order.id}">${order.manager}</label>
                                                        </div>
                                                    </td>  
                                                </c:if>
                                                <c:set var="servicesSum" value="${0}"/>
                                                <c:set var="materialsSum" value="${0}"/>
                                                <c:forEach items="${order.providedservicesList}" var="provService">
                                                    <c:set var="servicesSum" value="${servicesSum + provService.cost}"/>
                                                </c:forEach>
                                                <c:forEach items="${order.usedmaterialsList}" var="usedMaterial">
                                                    <c:set var="materialsSum" value="${materialsSum + usedMaterial.cost * usedMaterial.count}"/>
                                                </c:forEach>
                                                <td style="padding: 0; margin-left: 0px">
                                                    <div class="radio" style="padding: 0;">
                                                        <label style="padding: 5px; margin-left: 0px" class="radio" for="customerRadio${order.id}">
                                                            <fmt:formatNumber value="${servicesSum + materialsSum}" minFractionDigits="2"/>
                                                        </label>
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
                        <button tupe="submit" class="btn btn-default" formaction="/orders/order/create_page/add">
                            <table width="100%">
                                <tr>
                                    <td align="left" width="20"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></td>
                                    <td align="center"> Добавить</td>
                                </tr>                        
                            </table>
                        </button>
                        <button tupe="submit" class="btn btn-default" formaction="/orders/order/create_page/edit">
                            <table width="100%">
                                <tr>
                                    <td align="left" width="20"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></td>
                                    <td align="center"> Редактировать</td>
                                </tr> 
                            </table>
                        </button>
                        <button tupe="button" class="btn btn-default" value="delete" formaction="/orders/order/delete">
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
        </formSpring:form> 
    </jsp:body>        

</page:mainTamplate>
