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
        <formSpring:form cssClass="form-horizontal" modelAttribute="worker" method="POST" action="/staff/worker/${action}" role="main">
            <fieldset>
                <legend>${action == 'add' ? 'Добавление' : 'Редактирование'} сотрудника</legend>
                <formSpring:hidden path="id"/>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="name">Фамилия</label>
                    <div class="col-sm-10">    
                        <formSpring:input type="text" autofocus="${action == 'add' ? 'true' : ''}" path="lastName" cssClass="form-control" placeholder="Введите фамилию сотрудника" required="true"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="name">Имя</label>
                    <div class="col-sm-10">    
                        <formSpring:input type="text" path="firstName" cssClass="form-control" placeholder="Введите имя сотрудника" required="true"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="name">Отчесто</label>
                    <div class="col-sm-10">    
                        <formSpring:input type="text" path="middleName" cssClass="form-control" placeholder="Введите отчество сотрудника" required="true"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="name">Тип учетной записи</label>
                    <div class="col-sm-10">
                        <formSpring:select path="userType.id" cssClass="form-control">
                            <formSpring:option value="2" label="${userType == null ? 'Персонал' : userType.type}"/>
                            <formSpring:options items="${userTypes}" itemValue="id" itemLabel="type"/>
                        </formSpring:select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="name">Пароль</label>
                    <div class="col-sm-10">    
                        <formSpring:password path="password" cssClass="form-control" placeholder="Введите пароль сотрудника" required="true"/>
                    </div>
                </div>
                <div class="modal-footer">
                    <formSpring:button class="btn btn-primary" type="submit">
                        Сохранить
                    </formSpring:button>
                    <a class="btn btn-default" value="add" href="/staff/showAllStaff">
                        Отмена
                    </a>
                </div>
            </fieldset>
        </formSpring:form>
    </jsp:body>
</page:mainTamplate>
