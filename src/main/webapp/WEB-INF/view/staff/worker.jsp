<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="formSpring" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="page" tagdir="/WEB-INF/tags/"%>

<page:mainTamplate>
    <jsp:attribute name="title">${action == 'add' ? 'Добавить' : 'Редактировать'} сотрудника</jsp:attribute>

    <jsp:body>      
        <c:url var="actionURL" value="/staff/worker/${action}"/>
        <formSpring:form cssClass="form-horizontal" modelAttribute="worker" method="POST" action="${actionURL}" role="main">
            <fieldset>
                <legend>${action == 'add' ? 'Добавление' : 'Редактирование'} сотрудника</legend>
                <formSpring:hidden path="id"/>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="login">Логин</label>
                    <div class="col-sm-10">   
                        <formSpring:errors path="login" cssClass="label label-danger"/>
                        <formSpring:input path="login" autofocus="${action == 'add' ? 'true' : ''}" cssClass="form-control" placeholder="Введите пароль сотрудника"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="name">Фамилия</label>
                    <div class="col-sm-10">
                        <formSpring:errors path="lastName" cssClass="label label-danger" for="lastName"/>
                        <formSpring:input id="lastName" type="text" path="lastName" cssClass="form-control" placeholder="Введите фамилию сотрудника"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="name">Имя</label>
                    <div class="col-sm-10">    
                        <formSpring:errors path="firstName" cssClass="label label-danger"/>
                        <formSpring:input type="text" path="firstName" cssClass="form-control" placeholder="Введите имя сотрудника"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="name">Отчесто</label>
                    <div class="col-sm-10">    
                        <formSpring:errors path="middleName" cssClass="label label-danger"/>
                        <formSpring:input type="text" path="middleName" cssClass="form-control" placeholder="Введите отчество сотрудника"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="name">Тип учетной записи</label>
                    <div class="col-sm-10">
                        <formSpring:select path="userType.id" cssClass="form-control">
                            <formSpring:options items="${userTypes}" itemValue="id" itemLabel="type"/>
                        </formSpring:select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="name">Пароль</label>
                    <div class="col-sm-10">   
                        <formSpring:errors path="password" cssClass="label label-danger"/> 
                        <formSpring:input path="password" cssClass="form-control" placeholder="Введите пароль сотрудника"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="enabled">Учетная запись активна</label>
                    <div class="col-sm-10">
                        <formSpring:checkbox id="enabled" cssClass="form-control" path="enabled"/>
                    </div>
                </div>
                <div class="modal-footer">
                    <formSpring:button class="btn btn-primary" type="submit">
                        Сохранить
                    </formSpring:button>
                        <a class="btn btn-default" href="<c:url value="/staff/showAllStaff"/>">
                        Отмена
                    </a>
                </div>
            </fieldset>
        </formSpring:form>
    </jsp:body>
</page:mainTamplate>
