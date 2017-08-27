<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="formSpring" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="page" tagdir="/WEB-INF/tags/"%>

<page:mainTamplate>
    <jsp:attribute name="title">${action == add ? 'Добавить' : 'Редактировать'} клиента</jsp:attribute>

    <jsp:body>        
        <formSpring:form cssClass="form-horizontal" modelAttribute="customer" method="POST" action="/customers/customer/${action}" role="main">
            <fieldset>
                <legend>${action == add ? 'Добавление' : 'Редактирование'} клиента</legend>
                <div class="form-group">
                    <formSpring:hidden path="login"/>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="name">Фамилия</label>
                    <div class="col-sm-10">    
                        <formSpring:input autofocus="${action == 'add' ? 'true' : ''}" type="text" path="lastName" cssClass="form-control" placeholder="Введите фамилию клиента" required="true"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="name">Имя</label>
                    <div class="col-sm-10">    
                        <formSpring:input type="text" path="firstName" cssClass="form-control" placeholder="Введите имя клиента" required="true"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="name">Отчесто</label>
                    <div class="col-sm-10">    
                        <formSpring:input type="text" path="middleName" cssClass="form-control" placeholder="Введите отчество клиента" required="true"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="count">Адрес</label>
                    <div class="col-sm-10">    
                        <formSpring:input type="text" path="addressId" cssClass="form-control" placeholder="Введите адрес прописки клиента" required="true"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="cost">Телефоны</label>
                    <div class="col-sm-10">
                        <formSpring:input type="text" path="telephonenumbersList" cssClass="form-control" placeholder="Введите телефонный номер клиента"/>                        
                    </div>
                </div>
                <div class="modal-footer">
                    <formSpring:button class="btn btn-primary" type="submit">
                        Сохранить
                    </formSpring:button>
                    <a class="btn btn-default" value="add" href="/customers/showAllCustomers">
                        Отмена
                    </a>
                </div>
            </fieldset>
        </formSpring:form>
    </jsp:body>
</page:mainTamplate>
