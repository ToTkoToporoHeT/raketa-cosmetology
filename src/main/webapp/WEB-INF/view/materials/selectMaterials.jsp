<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="formSpring" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
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

        <security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ROOT')" var="isAdmin"/>
        <security:authorize access="hasRole('ROLE_ROOT')" var="isRoot"/>

        <formSpring:form cssClass="form-horizontal" role="main" modelAttribute="orderTemp" method="post"
                         onsubmit="return checkCheckedMaterials()">
            <div class="row">
                <div class="col-sm-10">
                    <div class="form-group">
                        <div class="panel panel-info">
                            <div class="panel-heading">Список материалов</div> 
                            <div class="panel-body" style="padding: 0 15px;">
                                <div class="row">                                
                                    <div class="">
                                        <div class="input-group">
                                            <input id="search" type="search" class="form-control" placeholder="Искать..." autocomplete=off>
                                            <span class="input-group-btn">
                                                <button id="searchReset" class="btn btn-default" type="button">
                                                    <span class="glyphicon glyphicon-remove" aria-hidden="true"/>
                                                </button>
                                            </span>
                                        </div>
                                    </div>
                                </div><!-- /.row -->
                            </div>
                            <div class="controls table-fixedH">
                                <table id="table" class="table table-condensed table-hover " data-sorting="true">
                                    <thead>
                                        <tr class="info" role="row">
                                            <th data-classes="id">
                                                Номенкл. №</th>
                                            <th data-classes="name">
                                                Наименование</th>
                                            <th data-classes="unit" 
                                                data-breakpoints="xs">
                                                Ед. измерения</th>
                                            <th data-classes="count">
                                                Израсхо-<br>довано</th>
                                            <th data-classes="count" 
                                                data-breakpoints="xs sm">
                                                На складе</th>
                                            <th data-classes="cost" 
                                                data-breakpoints="xs">
                                                Стоимость единицы</th>
                                                <c:if test="${isRoot or isAdmin}">
                                                <th data-breakpoints="xs sm md">
                                                    Сотрудник</th>
                                                </c:if>
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
                                                    ${usedMaterial.material.number}
                                                </td>
                                                <td>
                                                    ${usedMaterial.material.name}
                                                </td>
                                                <td>
                                                    ${usedMaterial.material.unit}
                                                </td>
                                                <td>
                                                    <formSpring:input required="" 
                                                                      type="number" step="0.01" 
                                                                      oninput="cropFraction(this, 2)" 
                                                                      onkeydown="formatCost(this)"
                                                                      id="usMaterialCount${materialNumber.count}" 
                                                                      min="0"
                                                                      max="${usedMaterial.material.count}"
                                                                      cssClass="form-control" for="materialRadio${materialNumber.count}" 
                                                                      path="usedmaterialsList[${materialNumber.index}].count" 
                                                                      disabled="${usedMaterial.material.forDelete}"/>
                                                </td>
                                                <td id="stockBalance${materialNumber.count}">
                                                    <fmt:formatNumber value="${usedMaterial.material.count}"/>
                                                </td>
                                                <td>
                                                    <fmt:formatNumber value="${usedMaterial.material.cost}" minFractionDigits="4"/>
                                                </td>
                                                <c:if test="${isRoot or isAdmin}">
                                                    <td>
                                                        ${usedMaterial.material.manager}
                                                    </td>
                                                </c:if>
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
                        <button type="submit" class="btn btn-default" 
                                formaction="<c:url value="/orders/order/selectMaterials"/>">
                            <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                            <span class="text">Выбрать</span>
                        </button>
                        <a class="btn btn-default" href= "<c:url value="/orders/order/show_page/{action}"/>">
                            <span class="glyphicon glyphicon-chevron-left"></span>
                            <span class="text">Назад</span>
                        </a>
                    </div>
                </div>
            </div>
        </formSpring:form> 
    </jsp:body>        

</page:mainTamplate>
