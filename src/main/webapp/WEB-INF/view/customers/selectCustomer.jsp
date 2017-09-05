<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="formSpring" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:mainTamplate>
    <jsp:attribute name="title">Выбор клиента</jsp:attribute>

    <jsp:body>         
        <formSpring:form cssClass="form-horizontal" role="main" modelAttribute="customer" method="post" action="/orders">
            <div class="row">
                <div class="col-sm-10">
                    <div class="form-group">
                        <div class="panel panel-info">
                            <div class="panel-heading">Список клиентов</div> 
                            <div class="panel-body">
                                <div class="row">                                
                                    <div class="col-sm-12">
                                        <input id="search" type="text" class="form-control" width="10%" placeholder="Искать...">
                                    </div>
                                </div><!-- /.row -->
                            </div>
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
                                <div class="controls">
                                    <tbody>
                                        <c:forEach items="${customers}" var="customer" varStatus="customerNumber">
                                            <tr>
                                                <th class="info" style="padding: 5px; margin-left: 0px">
                                                    <input type="radio" name="login" id="customerRadio${customer.login}" value="${customer.login}">
                                                    ${customerNumber.count}</th>
                                                <td style="padding: 0; margin-left: 0px">
                                                    <div class="radio" style="padding: 0;">
                                                        <label style="padding: 5px; margin-left: 0px" class="radio" for="customerRadio${customer.login}">${customer.lastName}</label>
                                                    </div>
                                                </td>
                                                <td style="padding: 0; margin-left: 0px">
                                                    <div class="radio" style="padding: 0;">
                                                        <label style="padding: 5px; margin-left: 0px" class="radio" for="customerRadio${customer.login}">${customer.firstName}</label>
                                                    </div>
                                                </td>
                                                <td style="padding: 0; margin-left: 0px">
                                                    <div class="radio" style="padding: 0;">
                                                        <label style="padding: 5px; margin-left: 0px" class="radio" for="customerRadio${customer.login}">${customer.middleName}</label>
                                                    </div>
                                                </td>
                                                <td style="padding: 0; margin-left: 0px">
                                                    <div class="radio" style="padding: 0;">
                                                        <label style="padding: 5px; margin-left: 0px" class="radio" for="customerRadio${customer.login}">${customer.addressId}</label>
                                                    </div>
                                                </td>                                                
                                                <td style="padding: 0; margin-left: 0px">
                                                    <ul style="padding: 0; margin-left: 0px">
                                                        <div class="radio" style="padding: 0;">
                                                            <label class="radio" for="customerRadio${customer.login}">
                                                                <c:forEach items="${customer.telephonenumbersList}" var="telNumb">
                                                                    <c:if test="${telNumb.telephoneNumber != ''}">
                                                                        <li>${telNumb}</li>
                                                                        </c:if>
                                                                    </c:forEach>
                                                            </label>
                                                        </div>
                                                    </ul>                                                    
                                                </td>
                                                <td style="padding: 0; margin-left: 0px">
                                                    <div class="radio" style="padding: 0;">
                                                        <label style="padding: 5px; margin-left: 0px" class="radio" for="customerRadio${customer.login}">${customer.login}</label>
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
                        <button tupe="submit" class="btn btn-default" formaction="/orders/order/selectCustomer">
                            <table width="100%">
                                <tr>
                                    <td align="left" width="20"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span></td>
                                    <td align="center"> Выбрать</td>
                                </tr>                        
                            </table>
                        </button>
                        <button tupe="button" class="btn btn-default" value="delete" formaction="../orders/order.jsp">
                            <table width="100%">
                                <tr>
                                    <td align="left" width="20"><span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span></td>
                                    <td align="center"> Назад</td>
                                </tr>                        
                            </table>
                        </button>
                    </div>
                </div>
            </div>
        </formSpring:form> 
    </jsp:body>        

</page:mainTamplate>
