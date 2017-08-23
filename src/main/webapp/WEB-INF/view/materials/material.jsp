<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="formSpring" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="page" tagdir="/WEB-INF/tags/"%>

<page:mainTamplate>
    <jsp:attribute name="title">Добавить материал</jsp:attribute>

    <jsp:body>        
        <formSpring:form cssClass="form-horizontal" modelAttribute="material" method="POST" action="/materials/material/add" role="main">
            <fieldset>
                <legend>Добавление материала</legend>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="name">Наименование</label>
                    <div class="col-sm-10">    
                        <formSpring:input autofocus="true" type="text" path="name" cssClass="form-control" placeholder="Введите наименование" required="true"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="unit">Единицы измерения</label>
                    <div class="col-sm-10">
                        <select name="materialUnitId" class="form-control">
                            <c:forEach items="${units}" var="unit">
                                <option value="${unit.id}">${unit.unit}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="count">Количество</label>
                    <div class="col-sm-10">    
                        <formSpring:input type="number" path="count" cssClass="form-control" placeholder="Введите количество материала на складе" required="true"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="cost">Стоимость одной единицы</label>
                    <div class="col-sm-10">    
                        <formSpring:input type="text" pattern="\d+(\.\d{2})?" path="cost" cssClass="form-control" placeholder="Введите стоимость единицы материала в формате #.##" required="true"/>
                    </div>
                </div>
                <div class="modal-footer">
                    <formSpring:button class="btn btn-primary" type="submit">
                        Сохранить
                    </formSpring:button>
                    <a class="btn btn-default" value="add" href="/materials/showAllMaterials">
                        Отмена
                    </a>
                </div>
            </fieldset>
        </formSpring:form>
    </jsp:body>
</page:mainTamplate>
