<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="formSpring" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="page" tagdir="/WEB-INF/tags/"%>

<page:mainTamplate>
    <jsp:attribute name="title">${action == 'add' ? 'Добавить' : 'Редактировать'} клиента</jsp:attribute>

    <jsp:body>
        
        <formSpring:form cssClass="form-horizontal" modelAttribute="customer" method="POST" action="/customers/customer/${action}?requestFrom=${requestFrom}" role="main">
            <fieldset>
                <legend>${action == 'add' ? 'Добавление' : 'Редактирование'} клиента</legend>
                <formSpring:hidden path="id"/>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="login">E-mail</label>
                    <div class="col-sm-10"> 
                        <formSpring:errors path="login" cssClass="label label-danger"/>
                        <formSpring:input id="login" type="text" autofocus="${action == 'add' ? 'true' : ''}"  path="login" cssClass="form-control" placeholder="Введите адрес электронной почты клиента"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="lastName">Фамилия</label>
                    <div class="col-sm-10">  
                        <formSpring:errors path="lastName" cssClass="label label-danger"/>
                        <formSpring:input id="lastName" type="text" path="lastName" cssClass="form-control" placeholder="Введите фамилию клиента"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="firstName">Имя</label>
                    <div class="col-sm-10">  
                        <formSpring:errors path="firstName" cssClass="label label-danger"/>
                        <formSpring:input id="firstName" type="text" path="firstName" cssClass="form-control" placeholder="Введите имя клиента"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="middleName">Отчесто</label>
                    <div class="col-sm-10">   
                        <formSpring:errors path="middleName" cssClass="label label-danger"/>
                        <formSpring:input id="middleName" type="text" path="middleName" cssClass="form-control" placeholder="Введите отчество клиента"/>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-8">
                        <fieldset>
                            <legend>Адрес прописки</legend>
                            <formSpring:hidden path="addressId.id"/>
                            <div class="form-group">
                                <label class="col-sm-2 control-label" for="country">Страна</label>
                                <div class="col-sm-10">
                                    <formSpring:errors path="addressId.country" cssClass="label label-danger"/>
                                    <formSpring:input id="country" type="text" path="addressId.country" cssClass="form-control" placeholder="Введите страну в которой прописан клиент"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label" for="city">Населенный пункт</label>
                                <div class="col-sm-10">
                                    <formSpring:errors path="addressId.city" cssClass="label label-danger"/>
                                    <formSpring:input id="city" type="text" path="addressId.city" cssClass="form-control" placeholder="Введите населенный в котором прописан клиент"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label" for="street">Улица</label>
                                <div class="col-sm-10">
                                    <formSpring:errors path="addressId.street" cssClass="label label-danger"/>
                                    <formSpring:input id="street" type="text" path="addressId.street" cssClass="form-control" placeholder="Введите улицу на которой прописан клиент"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label" for="house">Дом</label>
                                <div class="col-sm-10">
                                    <formSpring:errors path="addressId.house" cssClass="label label-danger"/>
                                    <formSpring:input id="house" type="text" path="addressId.house" cssClass="form-control" placeholder="Введите дом в котором прописан клиент"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label" for="flat">Квартира</label>
                                <div class="col-sm-10">
                                    <formSpring:errors path="addressId.flat" cssClass="label label-danger"/>
                                    <formSpring:input id="flat" type="text" path="addressId.flat" cssClass="form-control" placeholder="Введите квартиру в которой прописан клиент, если необходимо"/>
                                </div>                                
                            </div>
                        </fieldset>
                    </div>
                    <div class="col-sm-3 col-sm-offset-1">
                        <fieldset>
                            <legend>Телефоные номера</legend>
                            <div class="form-group">
                                <c:set var="telListLength" value="${fn:length(customer.telephonenumbersList)}"/>
                                <c:forEach begin="0" end="${telListLength == 0 ? 0 : telListLength - 1}" var="i">
                                    <div class="form-group">
                                        <formSpring:hidden path="telephonenumbersList[${i}].id"/>
                                        <formSpring:errors path="telephonenumbersList[${i}].telephoneNumber" cssClass="label label-danger"/>
                                        <formSpring:input type="text" id="tel${i}" path="telephonenumbersList[${i}].telephoneNumber"  cssClass="form-control"/>  
                                        <formSpring:hidden path="telephonenumbersList[${i}].customer"/>
                                    </div>
                                    <script>
                                        document.getElementById('tel${i}').onkeypress = function (e) {
                                            return !(/[А-Яа-яA-Za-z ]/.test(String.fromCharCode(e.charCode)));
                                        };
                                    </script>
                                </c:forEach>                                
                            </div>
                        </fieldset>
                    </div>
                </div>
                <formSpring:hidden path="enabled"/>
                <div class="modal-footer">
                    <formSpring:button class="btn btn-primary" type="submit">
                        Сохранить
                    </formSpring:button>
                    <a class="btn btn-default" href="javascript:history.back()">
                        Отмена
                    </a>
                </div>
            </fieldset>
        </formSpring:form>
    </jsp:body>
</page:mainTamplate>
