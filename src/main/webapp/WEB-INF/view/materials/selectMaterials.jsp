<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="formSpring" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:mainTamplate>
    <jsp:attribute name="title">Выбор материалов</jsp:attribute>

    <jsp:body>   
        <script>
            function setRequered(tabelRowNumber) {
                var chekbox = document.getElementById("materialRadio" + tabelRowNumber);
                if (chekbox.checked) {
                    document.getElementById("materialInput" + tabelRowNumber).setAttribute("required", "true");
                } else {
                    document.getElementById("materialInput" + tabelRowNumber).removeAttribute("required");
                }
            }
        </script>
        <formSpring:form cssClass="form-horizontal" role="main" modelAttribute="orderTemp" method="post">
            <div class="row">
                <div class="col-sm-10">
                    <div class="form-group">
                        <div class="panel panel-info">
                            <div class="panel-heading">Список материалов</div> 
                            <div class="panel-body">
                                <div class="row">                                
                                    <div class="col-sm-12">
                                        <input id="search" type="text" class="form-control" placeholder="Искать...">
                                    </div>
                                </div><!-- /.row -->
                            </div>
                            <div class="controls table-fixedH">
                                <table id="table" class="table table-condensed table-hover " data-sorting="true">
                                    <thead>
                                        <tr class="info" role="row">
                                            <th data-classes="id">
                                                №</th>
                                            <th data-classes="name">
                                                Наименование</th>
                                            <th data-classes="unit" 
                                                data-breakpoints="xs">
                                                Ед. измерения</th>
                                            <th data-classes="number-checkbox">
                                                Израсхо-<br>довано</th>
                                            <th data-classes="count" 
                                                data-breakpoints="xs sm">
                                                На складе</th>
                                            <th data-classes="cost" 
                                                data-breakpoints="xs">
                                                Стоимость единицы</th>
                                        </tr>
                                    </thead> 
                                    <tbody>
                                        <c:forEach items="${allMaterials.usedmaterialsList}" var="usedMaterial" varStatus="materialNumber">
                                            <tr class="coloring">
                                                <td>
                                                    <formSpring:hidden path="usedmaterialsList[${materialNumber.index}].id"/>
                                                    <c:if test="${usedMaterial.material.forDelete}">
                                                        <formSpring:hidden path="usedmaterialsList[${materialNumber.index}].material.id"/>
                                                        <formSpring:hidden path="usedmaterialsList[${materialNumber.index}].count"/>
                                                    </c:if>
                                                    <formSpring:checkbox id="materialRadio${materialNumber.count}" onclick="setRequered(${materialNumber.count})" value="${usedMaterial.material.id}" path="usedmaterialsList[${materialNumber.index}].material.id" disabled="${usedMaterial.material.forDelete}"/>
                                                    ${materialNumber.count}
                                                </td>
                                                <td>
                                                    ${usedMaterial.material.name}
                                                </td>
                                                <td>
                                                    ${usedMaterial.material.unit}
                                                </td>
                                                <td>
                                                    <div class="checkbox">
                                                        <formSpring:input required="" type="number" id="materialInput${materialNumber.count}" cssClass="form-control" for="materialRadio${materialNumber.count}" path="usedmaterialsList[${materialNumber.index}].count" disabled="${usedMaterial.material.forDelete}"/>
                                                    </div>
                                                </td>
                                                <td>
                                                    <fmt:formatNumber value="${usedMaterial.material.count}"/>
                                                </td>
                                                <td>
                                                    <fmt:formatNumber value="${usedMaterial.material.cost}" minFractionDigits="2"/>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-2">
                    <div class="btn-group-vertical" role="group" aria-label="...">
                        <button tupe="submit" class="btn btn-default" formaction="/orders/order/selectMaterials">
                            <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                            <span class="text">Выбрать</span>
                        </button>
                        <a class="btn btn-default" href= "/orders/order/show_page/{action}">
                            <span class="glyphicon glyphicon-chevron-left"></span>
                            <span class="text">Назад</span>
                        </a>
                    </div>
                </div>
            </div>
        </formSpring:form> 
    </jsp:body>        

</page:mainTamplate>
