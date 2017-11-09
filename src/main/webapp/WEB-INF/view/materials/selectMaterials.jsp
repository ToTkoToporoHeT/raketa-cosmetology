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
            function setRequered(tabelRowNumber){
                var chekbox = document.getElementById("materialRadio" + tabelRowNumber);
                if (chekbox.checked){
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
                            <table id="table" class="table table-condensed table-bordered table-hover ">
                                <thead>
                                    <tr class="info" role="row">
                                        <th class="info" width="5%" valign="middle">№</th>
                                        <th width="50%">Наименование</th>
                                        <th width="10%">Единицы измерения</th>
                                        <th width="11%">Количество</th>
                                        <th width="11%">На складе</th>
                                        <th width="13%">Стоимость единицы</th>
                                    </tr>
                                </thead> 
                                <div class="controls">
                                    <tbody>
                                        <c:forEach items="${allMaterials.usedmaterialsList}" var="usedMaterial" varStatus="materialNumber">
                                            <tr>
                                                <th class="info"  style="padding: 5px; margin-left: 0px">
                                                    <formSpring:hidden path="usedmaterialsList[${materialNumber.index}].id"/>
                                                    <c:if test="${usedMaterial.material.forDelete}">
                                                        <formSpring:hidden path="usedmaterialsList[${materialNumber.index}].material.id"/>
                                                        <formSpring:hidden path="usedmaterialsList[${materialNumber.index}].count"/>
                                                    </c:if>
                                                    <formSpring:checkbox id="materialRadio${materialNumber.count}" onclick="setRequered(${materialNumber.count})" value="${usedMaterial.material.id}" path="usedmaterialsList[${materialNumber.index}].material.id" disabled="${usedMaterial.material.forDelete}"/>
                                                    ${materialNumber.count}
                                                </th>
                                                <td style="padding: 0; margin-left: 0px">
                                                    <div class="checkbox" style="padding: 0;">
                                                        <formSpring:label style="padding: 5px; margin-left: 0px" class="checkbox" for="materialRadio${materialNumber.count}" path="usedmaterialsList[${materialNumber.index}].material.name">${usedMaterial.material.name}</formSpring:label>
                                                        </div>
                                                    </td>
                                                    <td style="padding: 0; margin-left: 0px">
                                                        <div class="checkbox" style="padding: 0;">
                                                            <label style="padding: 5px; margin-left: 0px" class="checkbox" for="materialRadio${materialNumber.count}" >${usedMaterial.material.unit}</label>
                                                    </div>
                                                </td>
                                                <td style="padding: 0; margin-left: 0px">
                                                    <div class="checkbox" style="padding: 0;">
                                                        <formSpring:input required="" type="number" id="materialInput${materialNumber.count}" style="padding: 5px; margin-left: 0px" cssClass="form-control" for="materialRadio${materialNumber.count}" path="usedmaterialsList[${materialNumber.index}].count" disabled="${usedMaterial.material.forDelete}"/>
                                                    </div>
                                                </td>
                                                <td style="padding: 0; margin-left: 0px">
                                                    <div class="checkbox" style="padding: 0;">
                                                        <label style="padding: 5px; margin-left: 0px" class="checkbox" for="materialRadio${materialNumber.count}" >
                                                            <fmt:formatNumber value="${usedMaterial.material.count}"/>
                                                        </label>
                                                    </div>
                                                </td>
                                                <td style="padding: 0; margin-left: 0px">
                                                    <div class="checkbox" style="padding: 0;">
                                                        <label style="padding: 5px; margin-left: 0px" class="checkbox" for="materialRadio${materialNumber.count}" >
                                                            <fmt:formatNumber value="${usedMaterial.material.cost}" minFractionDigits="2"/>
                                                        </label>
                                                    </div>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </div>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="col-sm-2">
                    <div class="btn-group btn-group-vertical" role="group" aria-label="...">
                        <button tupe="submit" class="btn btn-default" formaction="/orders/order/selectMaterials">
                            <table width="100%">
                                <tr>
                                    <td align="left" width="20"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span></td>
                                    <td align="center"> Выбрать</td>
                                </tr>                        
                            </table>
                        </button>
                        <a class="btn btn-default" href= "/orders/order/show_page/{action}">
                            <table width="100%">
                                <tr>
                                    <td align="left" width="20"><span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span></td>
                                    <td align="center"> Назад</td>
                                </tr>                        
                            </table>
                        </a>
                    </div>
                </div>
            </div>
        </formSpring:form> 
    </jsp:body>        

</page:mainTamplate>
