<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="formSpring" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:mainTamplate>
    <jsp:attribute name="title">Клиенты</jsp:attribute>
    <jsp:attribute name="currentPage">viewCustomers</jsp:attribute>

    <jsp:body> 
        <c:url var="addCustomer" value="/customers/customer/show_page/add">
            <c:param name="requestFrom" value="viewCustomers"/>
        </c:url>       
        <c:url var="editCustomer" value="/customers/customer/show_page/edit"/>
        <c:url var="deleteCustomer" value="/customers/customer/delete"/>
        
        <formSpring:form cssClass="form-horizontal" role="main" modelAttribute="customer" method="post" action="/customers">
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
                            <div class="controls table-fixedH">
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
                                                    <input type="radio" name="id" id="customerRadio${customer.id}" value="${customer.id}">
                                                    ${customerNumber.count}
                                                </td>
                                                <td>
                                                    ${customer.fullName}
                                                </td>
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
                    <div class="btn-group-vertical" role="group" aria-label="...">
                        <button tupe="submit" class="btn btn-default" formaction="${addCustomer}">
                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                            <span class="text">Добавить</span>
                        </button>
                        <button tupe="submit" class="btn btn-default" formaction="${editCustomer}">
                            <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                            <span class="text">Редактировать</span>
                        </button>
                        <button tupe="button" class="btn btn-default" value="delete" formaction="${deleteCustomer}">
                            <span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
                            <span class="text">Удалить</span>
                        </button>
                    </div>
                </div>
            </div>
        </formSpring:form> 
    </jsp:body>        

</page:mainTamplate>
