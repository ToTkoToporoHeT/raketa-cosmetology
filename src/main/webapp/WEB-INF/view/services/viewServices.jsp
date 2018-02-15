<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="formSpring" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:mainTamplate>
    <jsp:attribute name="title">Услуги</jsp:attribute>
    <jsp:attribute name="currentPage">viewServices</jsp:attribute>

    <jsp:body>         
        <formSpring:form cssClass="form-horizontal" role="main" modelAttribute="service" method="post" action="/services">
            <div class="row">
                <div class="col-sm-10">
                    <div class="form-group">
                        <div class="panel panel-info">
                            <div class="panel-heading">Список услуг</div> 
                            <div class="panel-body">
                                <div class="row">                                
                                    <div class="col-sm-12">
                                        <input  id="search" type="text" class="form-control" placeholder="Искать...">
                                    </div>
                                </div><!-- /.row -->
                            </div>
                            <div class="table-fixedH">
                                <table id="table" class="table table-condensed table-bordered table-hover">
                                    <thead>
                                        <tr class="info" role="row">
                                            <th class="info" width="5%" valign="middle">№</th>
                                            <th width="61%">Наименование</th>
                                            <th width="12%">Стоимость</th>
                                            <th width="12%">Стоимость<br>для ИГ</th>
                                        </tr>
                                    </thead> 
                                    <tbody>
                                        <c:forEach items="${services}" var="service" varStatus="serviceNumber">
                                            <tr>
                                                <th class="info">
                                                    <input type="radio" name="id" id="serviceRadio${service.id}" value="${service.id}">
                                                    ${serviceNumber.count}</th>
                                                <td>
                                                    <div class="radio">
                                                        <label for="serviceRadio${service.id}">
                                                            <text>${service.name}</text>
                                                        </label>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="radio">
                                                        <label for="serviceRadio${service.id}">
                                                            <text><fmt:formatNumber value="${service.cost}" minFractionDigits="2"/></text>
                                                        </label>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="radio">
                                                        <label for="serviceRadio${service.id}">
                                                            <text><fmt:formatNumber value="${service.costFF}" minFractionDigits="2"/></text>
                                                        </label>
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
                        <button tupe="submit" class="btn btn-default" formaction="/services/service/show_page/add">
                            <table width="100%">
                                <tr>
                                    <td align="left" width="20"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></td>
                                    <td align="center"> Добавить</td>
                                </tr>                        
                            </table>
                        </button>
                        <button tupe="submit" class="btn btn-default" formaction="/services/service/show_page/edit">
                            <table width="100%">
                                <tr>
                                    <td align="left" width="20"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></td>
                                    <td align="center"> Редактировать</td>
                                </tr> 
                            </table>
                        </button>
                        <button tupe="button" class="btn btn-default" value="delete" formaction="/services/service/delete">
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
