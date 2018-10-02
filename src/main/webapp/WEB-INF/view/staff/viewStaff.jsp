<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="formSpring" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:mainTamplate>
    <jsp:attribute name="title">Персонал</jsp:attribute>
    <jsp:attribute name="currentPage">viewStaff</jsp:attribute>

    <jsp:body>         
        <formSpring:form cssClass="form-horizontal" role="main" modelAttribute="worker" method="post" action="/staff">
            <div class="row">
                <div class="col-sm-10">
                    <div class="form-group">
                        <div class="panel panel-info">
                            <div class="panel-heading">Список персонала</div> 
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
                                            <th data-classes="login">Логин</th>
                                            <th data-classes="last-name">Фамилия</th>
                                            <th data-classes="first-name" data-breakpoints="xs sm md">Имя</th>
                                            <th data-classes="middle-name" data-breakpoints="xs sm md">Отчесто</th>
                                            <th data-classes="type" data-breakpoints="xs sm">Тип<br>пользователя</th>
                                            <th data-classes="status" data-breakpoints="xs sm md">Статус<br>учетной записи</th>
                                        </tr>
                                    </thead> 
                                    <tbody>
                                        <c:forEach items="${staff}" var="worker" varStatus="workerNumber">
                                            <tr class="coloring">
                                                <td>
                                                    <input type="radio" name="id" id="customerRadio${worker.id}" value="${worker.id}">
                                                    ${workerNumber.count}
                                                </td>
                                                <td>
                                                    ${worker.login}
                                                </td>
                                                <td>
                                                    ${worker.lastName}
                                                </td>
                                                <td>
                                                    ${worker.firstName}
                                                </td>
                                                <td>
                                                    ${worker.middleName}
                                                </td>
                                                <td>
                                                    ${worker.userType}
                                                </td>   
                                                <td>
                                                    ${worker.enabled == true ? 'активна' : 'заблокирована'}
                                                </td>   
                                            </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-2">
                    <div class="btn-group-vertical" role="group" aria-label="...">
                        <button tupe="submit" class="btn btn-default" formaction="<c:url value="/staff/worker/show_page/add"/>">
                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                            <span class="text">Добавить</span>
                        </button>
                        <button tupe="submit" class="btn btn-default" formaction="<c:url value="/staff/worker/show_page/edit"/>">
                            <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                            <span class="text">Редактировать</span>
                        </button>
                        <button tupe="button" class="btn btn-default" value="delete" formaction="<c:url value="/staff/worker/delete"/>">
                            <span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
                            <span class="text">Удалить</span>
                        </button>
                    </div>
                </div>
            </div>
        </formSpring:form> 
    </jsp:body>        

</page:mainTamplate>
