<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="formSpring" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="page" tagdir="/WEB-INF/tags/"%>

<style>
    #cost { 
        -moz-appearance: textfield;
    }
    #cost::-webkit-inner-spin-button { 
        display: none;
    }
</style>

<page:mainTamplate>
    <jsp:attribute name="title">${action == 'add' ? 'Добавить' : 'Редактировать'} услугу</jsp:attribute>

    <jsp:body>        
        <c:url var="actionURL" value="/services/service/${action}"/>
        <formSpring:form cssClass="form-horizontal" modelAttribute="service" method="POST" action="${actionURL}" role="main">
            <fieldset>
                <legend>${action == 'add' ? 'Добавление' : 'Редактирование'} услуги</legend>
                <div class="form-group">
                    <formSpring:hidden path="id"/>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="name">Наименование</label>
                    <div class="col-sm-10">  
                        <formSpring:errors path="name" cssClass="label label-danger"/>
                        <formSpring:input autofocus="${action == 'add' ? 'true' : ''}" type="text" path="name" cssClass="form-control" placeholder="Введите наименование"/>
                    </div>
                </div>                
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="cost">Стоимость услуги</label>
                    <div class="col-sm-10">
                        <formSpring:errors path="cost" cssClass="label label-danger"/>
                        <formSpring:input type="text" oninput="cropFraction(this, 2)" onkeydown="formatCost(this)"
                                          id="cost" path="cost" cssClass="form-control" 
                                          placeholder="Введите стоимость единицы материала" 
                                          min="0" formnovalidate="true"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="costFF">Стоимость услуг для иностранных граждан</label>
                    <div class="col-sm-10">
                        <formSpring:errors path="costFF" cssClass="label label-danger"/>
                        <formSpring:input type="text" oninput="cropFraction(this, 2)" onkeydown="formatCost(this)"
                                          id="costFF" path="costFF" cssClass="form-control" 
                                          placeholder="Введите стоимость единицы материала" 
                                          min="0" formnovalidate="true"/>
                    </div>
                </div>
                <div class="modal-footer">
                    <formSpring:button class="btn btn-primary" type="submit">
                        Сохранить
                    </formSpring:button>
                        <a class="btn btn-default" href= "<c:url value="/services/showAllServices"/>">
                        Отмена
                    </a>
                </div>
            </fieldset>
        </formSpring:form>
    </jsp:body>
</page:mainTamplate>
