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
                                <table id="table" class="table table-condensed table-hover" data-sorting="true">
                                    <thead>
                                        <tr class="info" role="row">
                                            <th data-classes="id">№</th>
                                            <th data-classes="name">Наименование</th>
                                            <th data-classes="cost">Стоимость</th>
                                            <th data-classes="cost" data-breakpoints="xs sm">Стоимость<br>для <abbr title="Иностранных граждан">ИГ</abbr></th>
                                        </tr>
                                    </thead> 
                                    <tbody>
                                        <c:forEach items="${services}" var="service" varStatus="serviceNumber">
                                            <tr class="coloring">
                                                <td>
                                                    <input type="radio" name="id" id="serviceRadio${service.id}" value="${service.id}">
                                                    ${service.number}
                                                </td>
                                                <td>
                                                    ${service.name}
                                                </td>
                                                <td>
                                                    <fmt:formatNumber value="${service.cost}" minFractionDigits="2"/>
                                                </td>
                                                <td>
                                                    <fmt:formatNumber value="${service.costFF}" minFractionDigits="2"/>
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
                        <button tupe="submit" class="btn btn-default" formaction="<c:url value="/services/service/show_page/add"/>">
                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                            <span class="text">Добавить</span>
                        </button>
                        <button tupe="submit" class="btn btn-default" formaction="<c:url value="/services/service/show_page/edit"/>">
                            <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                            <span class="text">Редактировать</span>
                        </button>
                        <button tupe="button" class="btn btn-default" 
                                onclick="return confirm('Вы дейстительно хотите удалить услугу?')"
                                value="delete" formaction="<c:url value="/services/service/delete"/>">
                            <span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
                            <span class="text">Удалить</span>
                        </button>
                    </div>
                </div>
            </div>
        </formSpring:form>        
    </jsp:body>        

</page:mainTamplate>
