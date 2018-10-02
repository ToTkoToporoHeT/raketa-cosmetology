<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <jsp:attribute name="title">${action == add ? 'Добавить' : 'Редактировать'} материал</jsp:attribute>

    <jsp:body>    
        <c:url var="actionURL" value="/materials/material/${action}"/>
        <formSpring:form cssClass="form-horizontal" modelAttribute="material" method="POST" action="${actionURL}" role="main">
            <fieldset>
                <legend>${action == add ? 'Добавление' : 'Редактирование'} материала</legend>
                <formSpring:hidden path="id"/>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="name">Номенклатурный №</label>
                    <div class="col-sm-10">  
                        <formSpring:errors path="number" cssClass="label label-danger"/>
                        <formSpring:input id="number" autofocus="${action == 'add' ? 'true' : ''}" type="number" path="number" cssClass="form-control" placeholder="Введите номенклатурный номер"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="name">Наименование</label>
                    <div class="col-sm-10">  
                        <formSpring:errors path="name" cssClass="label label-danger"/>
                        <formSpring:input id="name" type="text" path="name" cssClass="form-control" placeholder="Введите наименование"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="unit">Единицы измерения</label>
                    <div class="col-sm-10">
                        <formSpring:select id="unit" path="unit.id" class="form-control">
                            <formSpring:options items="${units}" itemValue="id" itemLabel="unit"/>
                        </formSpring:select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="count">Количество</label>
                    <div class="col-sm-10">  
                        <formSpring:errors path="count" cssClass="label label-danger"/>
                        <formSpring:input type="number" id="count" path="count" cssClass="form-control" placeholder="Введите количество материала на складе"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="cost">Стоимость одной единицы</label>
                    <div class="col-sm-10">
                        <formSpring:errors path="cost" cssClass="label label-danger"/>
                        <formSpring:input type="text" oninput="cropFraction(this, 4)" onkeydown="formatCost(this)"
                                          id="cost" path="cost" cssClass="form-control" 
                                          placeholder="Введите стоимость единицы материала" 
                                          min="0" formnovalidate="true"/>
                    </div>
                </div>
                <div class="modal-footer">
                    <formSpring:button class="btn btn-primary" type="submit">
                        Сохранить
                    </formSpring:button>
                        <a class="btn btn-default" href="<c:url value="/materials/showAllMaterials"/>">
                        Отмена
                    </a>
                </div>
            </fieldset>
        </formSpring:form>
    </jsp:body>
</page:mainTamplate>
