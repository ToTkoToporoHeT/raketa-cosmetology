<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="formSpring" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:mainTamplate>
    <jsp:attribute name="title">Выбор клиента</jsp:attribute>

    <jsp:body> 
        <c:url value="/customers/customer/show_page/add" var="addCustomer"/>
        <formSpring:form cssClass="form-horizontal" role="main" method="post">
            <div class="row">
                <div class="col-sm-10">
                    <div class="form-group">
                        <div class="panel panel-info">
                            <div class="panel-heading">Список клиентов</div> 
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
                                            <th data-classes="full-name">
                                                ФИО</th>
                                            <th data-classes="address" 
                                                data-breakpoints="xs sm">
                                                Адрес</th>
                                            <th data-classes="telephone"
                                                data-breakpoints="xs">
                                                Телефоны</th>                                        
                                            <th data-classes="login"
                                                data-breakpoints="xs sm md">
                                                E-mail</th>
                                        </tr>
                                    </thead> 
                                    <tbody>
                                        <c:forEach items="${customers}" var="customer" varStatus="customerNumber">
                                            <tr class="coloring">
                                                <td>
                                                    <input type="radio" name="customerId" id="customerRadio${customer.id}" value="${customer.id}">
                                                    ${customerNumber.count}
                                                </td>
                                                <td>
                                                    ${customer.fullName}
                                                </td>
                                                <td>
                                                    ${customer.addressId}
                                                </td>                                                
                                                <td>
                                                    <ul>
                                                        <c:forEach items="${customer.telephonenumbersList}" var="telNumb">
                                                            <c:if test="${telNumb.telephoneNumber != ''}">
                                                                <li>
                                                                    ${telNumb}
                                                                </li>
                                                            </c:if>
                                                        </c:forEach>
                                                    </ul>                                                    
                                                </td>
                                                <td>
                                                    ${customer.login}
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
                        <button tupe="submit" class="btn btn-default" formaction="<c:url value="/orders/order/selectCustomer"/>">
                            <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                            <span class="text">Выбрать</span>
                        </button>
                        <a class="btn btn-default" href="${addCustomer}">
                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                            <span class="text">Добавить</span>
                        </a>
                        <a class="btn btn-default" value="back" href="<c:url value="/orders/order/show_page/${action}"/>">
                            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                            <span class="text">Назад</span>
                        </a>
                    </div>
                </div>
            </div>
        </formSpring:form> 
    </jsp:body>        

</page:mainTamplate>
