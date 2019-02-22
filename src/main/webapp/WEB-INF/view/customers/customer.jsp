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
        <c:url var="actionUrl" value="/customers/customer/${action}?requestFromURL=${requestFromURL}"/>
        <formSpring:form cssClass="form-horizontal" modelAttribute="customer" method="POST" action="${actionUrl}" role="main">
            <fieldset>
                <legend>${action == 'add' ? 'Добавление' : 'Редактирование'} клиента</legend>
                <formSpring:hidden path="id"/>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="fullName">ФИО</label>
                    <div class="col-sm-10">  
                        <formSpring:errors path="fullName" cssClass="label label-danger"/>
                        <formSpring:input id="fullName" type="text" path="fullName" cssClass="form-control" placeholder="Введите ФИО клиента"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="login">E-mail</label>
                    <div class="col-sm-10"> 
                        <formSpring:errors path="login" cssClass="label label-danger"/>
                        <formSpring:input id="login" type="text" autofocus="${action == 'add' ? 'true' : ''}"  path="login" cssClass="form-control" placeholder="Введите адрес электронной почты клиента"/>
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
                                    <formSpring:select id="country" path="addressId.country" class="form-control">
                                        <formSpring:option value="РБ" label="Республика Беларусь"/>
                                        <formSpring:option value="ИГ" label="Иностранный гражданин"/>
                                    </formSpring:select>
                                    <%--<formSpring:input id="country" type="text" path="addressId.country" cssClass="form-control" placeholder="Введите страну в которой прописан клиент"/>--%>
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
                            <div id="telephone_numbers" class="form-group">
                                <a href="#" onclick="addTelnumber()">Добавить номер телефона</a>
                                <c:set var="telListLength" value="${fn:length(customer.telephonenumbersList)}"/>
                                <c:forEach begin="1" end="${telListLength == 0 ? 0 : telListLength}" var="i">
                                    <div id="tel_number${i - 1}" class="form-group tel-number">
                                        <formSpring:hidden path="telephonenumbersList[${i - 1}].id"/>
                                        <formSpring:errors path="telephonenumbersList[${i - 1}].telephoneNumber" cssClass="label label-danger"/>
                                        <div class="input-group">
                                            <formSpring:input type="text" id="tel${i - 1}" path="telephonenumbersList[${i - 1}].telephoneNumber"  cssClass="form-control"/>  
                                           
                                                <button class="input-group-btn btn btn-default" onclick="deleteField(${i - 1})" type="button">
                                                    <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                                                </button>
                                           
                                        </div>
                                        <formSpring:hidden path="telephonenumbersList[${i - 1}].customer"/>
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
                    <a class="btn btn-default" href="${requestFromURL}">
                        Отмена
                    </a>
                </div>
            </fieldset>
        </formSpring:form>
    </jsp:body>
</page:mainTamplate>
