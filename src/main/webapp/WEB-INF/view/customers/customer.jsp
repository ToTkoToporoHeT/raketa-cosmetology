<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="formSpring" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="page" tagdir="/WEB-INF/tags/"%>

<style>
    #tel { 
        -moz-appearance: textfield;
    }
    #tel::-webkit-inner-spin-button { 
        display: none;
    }
</style>

<page:mainTamplate>
    <jsp:attribute name="title">${action == 'add' ? 'Добавить' : 'Редактировать'} клиента</jsp:attribute>

    <jsp:body>        
        <formSpring:form cssClass="form-horizontal" modelAttribute="customer" method="POST" action="/customers/customer/${action}" role="main">
            <fieldset>
                <legend>${action == 'add' ? 'Добавление' : 'Редактирование'} клиента</legend>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="name">E-mail</label>
                    <div class="col-sm-10">    
                        <formSpring:input type="email" readonly="${action == 'add' ? 'false' : 'true'}" autofocus="${action == 'add' ? 'true' : ''}"  path="login" cssClass="form-control" placeholder="Введите адрес электронной почты клиента" required="true"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="name">Фамилия</label>
                    <div class="col-sm-10">    
                        <formSpring:input type="text" path="lastName" cssClass="form-control" placeholder="Введите фамилию клиента" required="true"/>
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
                <div class="row">
                    <div class="col-sm-8">
                        <fieldset>
                            <legend>Адрес прописки</legend>
                            <div class="form-group">
                                <label class="col-sm-2 control-label" for="count">Страна</label>
                                <div class="col-sm-10">
                                    <formSpring:input type="text" path="addressId.country" cssClass="form-control" placeholder="Введите страну прописки клиента" required="true"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label" for="count">Населенный пункт</label>
                                <div class="col-sm-10">
                                    <formSpring:input type="text" path="addressId.city" cssClass="form-control" placeholder="Введите населенный пункт прописки клиента" required="true"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label" for="count">Улица</label>
                                <div class="col-sm-10">
                                    <formSpring:input type="text" path="addressId.street" cssClass="form-control" placeholder="Введите улицу прописки клиента" required="true"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label" for="count">Дом</label>
                                <div class="col-sm-10">
                                    <formSpring:input type="text" path="addressId.house" cssClass="form-control" placeholder="Введите дом прописки клиента" required="true"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label" for="count">Квартира</label>
                                <div class="col-sm-10">
                                    <formSpring:input type="text" path="addressId.flat" cssClass="form-control" placeholder="Введите квартиру прописки клиента, если необходимо"/>
                                </div>                                
                            </div>
                        </fieldset>
                    </div>
                    <div class="col-sm-3 col-sm-offset-1">
                        <fieldset>
                            <legend>Телефоные номера</legend>
                            <div class="form-group">
                                <c:forEach begin="0" end="${fn:length(customer.telephonenumbersList) - 1}" var="i">
                                    <div class="form-group">
                                        <formSpring:hidden path="telephonenumbersList[${i}].id"/>
                                        <formSpring:input type="number" id="tel" path="telephonenumbersList[${i}].telephoneNumber"  cssClass="form-control"/>  
                                        <formSpring:hidden path="telephonenumbersList[${i}].customer"/>
                                    </div>
                                </c:forEach>
                            </div>
                        </fieldset>
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
