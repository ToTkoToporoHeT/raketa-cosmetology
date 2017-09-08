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

<script>
    function up(e) {
        if (e.value.indexOf(".") != '-1') {
            e.value = e.value.substring(0, e.value.indexOf(".") + 3);
        }
    }
</script>


<page:mainTamplate>
    <jsp:attribute name="title">${action == add ? 'Добавить' : 'Редактировать'} материал</jsp:attribute>

    <jsp:body>        
        <formSpring:form cssClass="form-horizontal" modelAttribute="material" method="POST" action="/materials/material/${action}" role="main">
            <fieldset>
                <legend>${action == add ? 'Добавление' : 'Редактирование'} материала</legend>
                <div class="form-group">
                    <formSpring:hidden path="id"/>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="name">Наименование</label>
                    <div class="col-sm-10">    
                        <formSpring:input autofocus="${action == 'add' ? 'true' : ''}" type="text" path="name" cssClass="form-control" placeholder="Введите наименование" required="true"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="unit">Единицы измерения</label>
                    <div class="col-sm-10">
                        <select name="materialUnitId" class="form-control">
                            <c:forEach items="${units}" var="unit">
                                <option ${unit.id == material.unit.id ? 'selected' : ''} value="${unit.id}">${unit.unit}</option>
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
                        <formSpring:input type="text" oninput="up(this)" id="cost" path="cost" cssClass="form-control" 
                                          placeholder="Введите стоимость единицы материала" 
                                          required="true" min="0" formnovalidate="true"/>
                        <script>
                            document.getElementById('cost').onkeypress = function (e) {                                
                                if (this.value.indexOf(".") != '-1' || this.value.indexOf(",") != '-1') { // позволяет ввести или одну точку, или одну запятую
                                    return !(/[.,А-Яа-яA-Za-z]/.test(String.fromCharCode(e.charCode)));
                                }
                                return !(/[А-Яа-яA-Za-z ]/.test(String.fromCharCode(e.charCode)));
                            }
                        </script>
                    </div>
                </div>
                <div class="modal-footer">
                    <formSpring:button class="btn btn-primary" type="submit">
                        Сохранить
                    </formSpring:button>
                    <a class="btn btn-default" href="/materials/showAllMaterials">
                        Отмена
                    </a>
                </div>
            </fieldset>
        </formSpring:form>
    </jsp:body>
</page:mainTamplate>
