<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="formSpring" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:mainTamplate>
    <jsp:attribute name="title">Выбор клиента</jsp:attribute>

    <jsp:body> 
        <c:url value="/customers/customer/show_page/add" var="addCustomer">            
            <c:param name="requestFrom" value="selectCustomer"/>
        </c:url>
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
                                <table id="table" class="table table-condensed table-bordered table-hover ">
                                    <thead>
                                        <tr class="info" role="row">
                                            <th class="info" width="5%" valign="middle">№</th>
                                            <th >Фамилия</th>
                                            <th >Имя</th>
                                            <th >Отчесто</th>
                                            <th width="250">Адрес</th>
                                            <th width="160">Телефоны</th>                                        
                                            <th >E-mail</th>
                                        </tr>
                                    </thead> 
                                    <tbody>
                                        <c:forEach items="${customers}" var="customer" varStatus="customerNumber">
                                            <tr>
                                                <th class="info">
                                                    <input type="radio" name="customerId" id="customerRadio${customer.id}" value="${customer.id}" required="true">
                                                    ${customerNumber.count}</th>
                                                <td>
                                                    <div class="radio">
                                                        <label for="customerRadio${customer.id}">${customer.lastName}</label>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="radio">
                                                        <label for="customerRadio${customer.id}">${customer.firstName}</label>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="radio">
                                                        <label for="customerRadio${customer.id}">${customer.middleName}</label>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="radio">
                                                        <label for="customerRadio${customer.id}">${customer.addressId}</label>
                                                    </div>
                                                </td>                                                
                                                <td>
                                                    <div class="radio">
                                                        <label class="radio" for="customerRadio${customer.id}">
                                                            <ul>
                                                                <c:forEach items="${customer.telephonenumbersList}" var="telNumb">
                                                                    <c:if test="${telNumb.telephoneNumber != ''}">
                                                                        <li>${telNumb}</li>
                                                                        </c:if>
                                                                    </c:forEach>
                                                            </ul>  
                                                        </label>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="radio">
                                                        <label for="customerRadio${customer.id}">${customer.login}</label>
                                                    </div>
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
                        <button tupe="submit" class="btn btn-default" formaction="/orders/order/selectCustomer">
                            <table width="100%">
                                <tr>
                                    <td align="left" width="20"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span></td>
                                    <td align="center"> Выбрать</td>
                                </tr>                        
                            </table>
                        </button>
                        <a class="btn btn-default" href="${addCustomer}">
                            <table width="100%">
                                <tr>
                                    <td align="left" width="20"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></td>
                                    <td align="center"> Добавить</td>
                                </tr>                        
                            </table>
                        </a>
                        <a class="btn btn-default" value="back" href="/orders/order/show_page/${action}">
                            <table width="100%">
                                <tr>
                                    <td align="left" width="20"><span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span></td>
                                    <td align="center"> Назад</td>
                                </tr>                        
                            </table>
                        </a>
                    </div>
                </div>
            </div>
        </formSpring:form> 
    </jsp:body>        

</page:mainTamplate>
