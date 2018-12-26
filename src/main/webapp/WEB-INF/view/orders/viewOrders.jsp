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
                            <div class="table-fixedH">
                                <table id="table" class="table table-condensed table-hover" data-sorting="true">
                                    <thead>
                                        <tr class="info" role="row">
                                            <th data-classes="id">
                                                №</th>
                                            <th data-classes="number" 
                                                data-breakpoints="xs sm">
                                                Номер</th>
                                            <th data-classes="date">
                                                Дата</th>
                                            <th data-classes="name">
                                                ФИО клиента</th>
                                                <c:if test="${isRoot}">
                                                <th data-classes="name"
                                                    data-breakpoints="xs sm">
                                                    ФИО сотрудника</th>
                                                </c:if>
                                            <th data-classes="cost"
                                                data-breakpoints="xs sm">
                                                Сумма</th>
                                        </tr>
                                    </thead> 
                                    <tbody>
                                        <c:forEach items="${orders}" var="order" varStatus="orderNumber">
                                            <tr class="coloring">
                                                <td>
                                                    <input type="radio" name="id" id="customerRadio${order.id}" value="${order.id}">
                                                    ${orderNumber.count}
                                                </td>
                                                <td>
                                                    ${order.check_number}
                                                </td>
                                                <td>
                                                    <fmt:formatDate type = "date" dateStyle = "long"
                                                                    timeStyle = "long" 
                                                                    value="${order.prepare_date}"/>
                                                </td>
                                                <td>
                                                    ${order.customer}
                                                </td>

                                                <c:if test="${isRoot}">
                                                    <td>
                                                        ${order.manager}
                                                    </td>  
                                                </c:if>
                                                <c:set var="servicesSum" value="${0}"/>
                                                <c:set var="materialsSum" value="${0}"/>
                                                <c:forEach items="${order.providedservicesList}" var="provService">
                                                    <c:set var="servicesSum" value="${servicesSum + provService.cost * provService.rate}"/>
                                                </c:forEach>
                                                <c:forEach items="${order.usedmaterialsList}" var="usedMaterial">
                                                    <c:set var="materialsSum" value="${materialsSum + usedMaterial.cost * usedMaterial.count}"/>
                                                </c:forEach>

                                                <td>
                                                    <fmt:formatNumber value="${servicesSum + materialsSum}" minFractionDigits="2"/>
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
                        <button tupe="submit" class="btn btn-default" formaction="<c:url value="/orders/order/create_page/add"/>">
                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                            <span class="text">Добавить</span>
                        </button>
                        <button tupe="submit" class="btn btn-default" formaction="<c:url value="/orders/order/create_page/edit"/>">
                            <span class="glyphicon glyphicon-edit" aria-hidden="edit"></span>
                            <span class="text">Редактировать</span>
                        </button>
                        <button tupe="button" class="btn btn-default" value="delete" formaction="<c:url value="/orders/order/delete"/>">
                            <span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
                            <span class="text">Удалить</span>
                        </button>
                    </div>
                </div>
            </div>
        </formSpring:form> 
    </jsp:body>        

</page:mainTamplate>
