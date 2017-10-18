<!DOCTYPE html>
<%@tag description="Main interface for web-app" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<%@attribute name="title" fragment="true" %>
<%@attribute name="currentPage"%>

<html>
    <head>
        <title><jsp:invoke fragment="title"/></title>

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>

        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script> 

        <meta name="viewport" content="width=device-width, initial-scale=1">

        <script>
            $(document).ready(function () {
                $("#search").keyup(function () {
                    _this = this;
                    $.each($("#table tbody tr"), function () {
                        if ($(this).text().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1) {
                            $(this).hide();
                        } else {
                            $(this).show();
                        }
                    });
                });
            });
        </script>
    </head>
    <body>
        <security:authorize access= "hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_ROOT')" var= "isUser"/>
        <security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ROOT')" var="isAdmin"/>

        <c:url var="showMaterials" value="/materials/showAllMaterials"/>
        <c:url var="showServices" value="/services/showAllServices"/>
        <c:url var="viewCustomers" value="/customers/showAllCustomers"/>
        <c:url var="viewStaff" value="/staff/showAllStaff"/>
        <c:url var="viewOrders" value="/orders/showAllOrders"/>
        <c:url var="addOrder" value="/orders/order/create_page/add"/>

        <c:url var="materialsTest" value="/materials/showMaterialsTest"/>
        <c:url var="test" value="/customers/test"/>

        <div class="container">

            <!-- Static navbar -->
            <nav class="navbar navbar-default">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <a class="navbar-brand" href="/index.html">Косметология</a>
                    </div>
                    <div id="navbar" class="navbar-collapse collapse">
                        <ul class="nav navbar-nav">
                            <li class="${currentPage == 'index' ? 'active' : ''}"><a href="/index.html">Главная</a></li>
                            <li class="${currentPage == 'addOrder' ? 'active' : ''}"><a href="${addOrder}">Оформление договора</a></li>
                            <li><a href="#">Запись на прием</a></li>
                            <li><a href="${test}">Test</a></li>
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Учет <span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li class="${currentPage == 'viewOrders' ? 'active' : ''}"><a href="${viewOrders}">Договора</a></li>
                                    <li class="${currentPage == 'viewCustomers' ? 'active' : ''}"><a href="${viewCustomers}">Клиенты</a></li>
                                        <c:if test="${isAdmin}">
                                        <li class="${currentPage == 'viewStaff' ? 'active' : ''}"><a href="${viewStaff}">Персонал</a></li>
                                        </c:if>
                                    <li role="separator" class="divider"></li>
                                    <li class="dropdown-header">Материалы и услуги</li>
                                    <li class="${currentPage == 'viewMaterials' ? 'active' : ''}"><a href="${showMaterials}">Материалы</a></li>
                                    <li class="${currentPage == 'viewServices' ? 'active' : ''}"><a href="${showServices}">Услуги</a></li>
                                </ul>
                            </li>
                        </ul>
                        <c:if test="${not fn:contains(currentPage, 'error')}">
                            <ul class="nav navbar-nav navbar-right">

                                <c:if test= "${not isUser}">
                                    <li style= "padding-top: 15px; padding-bottom: 15px;">
                                        <font size="1">Вы не вошли в приложение</font>
                                    </li>
                                    <li>
                                        <a href= "<c:url value= "/login.html"/>">Войти</a>
                                    </li>
                                </c:if>

                                <c:if test= "${isUser}">
                                    <li style= "padding-top: 15px; padding-bottom: 15px;">
                                        <font size="1">Вы вошли как:</font>
                                        <security:authentication property= "principal.username"/>
                                    </li>
                                    <li>
                                        <a href= "<c:url value= "/j_spring_security_logout"/>">Выход</a>
                                    </li>
                                </c:if>

                            </ul>
                        </c:if>
                    </div><!--/.nav-collapse -->
                </div><!--/.container-fluid -->
            </nav>          
            <div class="container"> 
                <div class="col-sm-12">
                    <jsp:doBody/>
                </div>
            </div>
    </body>
</html>