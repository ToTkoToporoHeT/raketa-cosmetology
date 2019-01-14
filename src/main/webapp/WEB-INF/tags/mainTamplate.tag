<!DOCTYPE html>
<%@tag description="Main interface for web-app" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<%@attribute name="title" fragment="true" %>
<%@attribute name="currentPage"%>

<html>
    <head>
        <link rel="shortcut icon" href="<c:url value="/resources/images/logo.ico"/>" type="image/x-icon">
        <title><jsp:invoke fragment="title"/></title>

        <!--Подключение JQUERY-->
        <script src="<c:url value="/resources/js/jquery-3.3.1.min.js"/>"></script>
        <!--Подключение bootstrap CSS стилей-->
        <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css"/>" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap-theme.min.css"/>" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
        <!--Подключение bootstrap JS скриптов-->
        <script src="<c:url value="/resources/js/bootstrap.min.js"/>" ></script> 

        <!--Подключение footable CSS стилей-->
        <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>">
        <link rel="stylesheet" href="<c:url value="/resources/css/footable.bootstrap.css"/>">
        <link rel="stylesheet" href="<c:url value="/resources/css/footable.bootstrap.min.css"/>">
        <!--Подключение footable JS скриптов-->
        <script src="<c:url value="/resources/js/footable.min.js"/>"></script>
        <script src="<c:url value="/resources/js/footable.js"/>"></script>   
        <script src="<c:url value="/resources/js/moment.js"/>"></script>
        <!--Подключение кастомных скриптов-->        
        <script src="<c:url value="/resources/js/tabel_scripts.js"/>"></script>
        <script src="<c:url value="/resources/js/input_scripts.js"/>" type="text/javascript"></script>

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

            /*Функция получает введенные с клавиатуры символы, 
             * подключается: <input onkeypress="this.nextSibling.innerHTML = getChar(event)+''"><b></b>
             // event.type должен быть keypress
             function getChar(event) {
             if (event.which == null) { // IE
             if (event.keyCode < 32)
             return null; // спец. символ
             return String.fromCharCode(event.keyCode)
             }
             
             if (event.which != 0 && event.charCode != 0) { // все кроме IE
             if (event.which < 32)
             return null; // спец. символ
             return String.fromCharCode(event.which); // остальные
             }
             
             return null; // спец. символ
             }*/

            //оставлят после разделителя дроби fractCount символов
            function cropFraction(e, fractCount) {
                var seporIndex = e.value.indexOf(".");
                if (seporIndex == '-1')
                    seporIndex = e.value.indexOf(",");

                if (seporIndex != '-1') {
                    if (seporIndex + fractCount < e.value.length) {
                        e.value = e.value.substring(0, seporIndex + fractCount + 1);
                    }
                }
            }

            function formatCost(docElement) {
                docElement.onkeypress = function (e) {
                    if (docElement.value.indexOf(".") != '-1' || docElement.value.indexOf(",") != '-1') {// позволяет ввести или одну точку, или одну запятую
                        return (/[0-9]/.test(String.fromCharCode(e.charCode)));
                    }
                    return (/[,.0-9]/.test(String.fromCharCode(e.charCode)));
                }
            }
        </script>
    </head>
    <body>
        <security:authorize access= "hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_ROOT')" var= "isUser"/>
        <security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ROOT')" var="isAdmin"/>
        <security:authorize access="hasRole('ROLE_ROOT')" var="isRoot"/>

        <c:url var="home" value="/"/>
        <c:url var="showMaterials" value="/materials/showAllMaterials"/>
        <c:url var="showServices" value="/services/showAllServices"/>
        <c:url var="viewCustomers" value="/customers/showAllCustomers"/>
        <c:url var="viewStaff" value="/staff/showAllStaff"/>
        <c:url var="viewOrders" value="/orders/showAllOrders"/>
        <c:url var="addOrder" value="/orders/order/create_page/add"/>

        <!--Администрирование ROOT-->
        <c:url var="importData" value="/import/createPage"/>

        <c:url var="materialsTest" value="/materials/showMaterialsTest"/>
        <c:url var="test" value="/ajax_test/show"/>

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
                        <a class="navbar-brand" href="#">Косметология</a>
                    </div>
                    <div id="navbar" class="navbar-collapse collapse">
                        <ul class="nav navbar-nav">
                            <%--
                            <li class="${currentPage == 'index' ? 'active' : ''}"><a href="${home}">Главная</a></li>                            
                            <li><a href="#">Запись на прием</a></li>
                            --%>
                            <li class="${currentPage == 'addOrder' ? 'active' : ''}"><a href="${addOrder}">Оформление договора</a></li>                            
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
                            <c:if test="${isRoot}">
                                <li class="dropdown">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Администрирование <span class="caret"></span></a>
                                    <ul class="dropdown-menu">
                                        <li class="${currentPage == 'importData' ? 'active' : ''}"><a href="${importData}">Импорт данных</a></li>
                                        <li class="${currentPage == 'ajaxTest' ? 'active' : ''}"><a href="${test}">Test</a></li>
                                    </ul>
                                </li>
                            </c:if>
                        </ul>
                        <%--<c:if test="${not fn:contains(currentPage, 'error')}">--%>
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
                        <%--</c:if>--%>
                    </div><!--/.nav-collapse -->
                </div><!--/.container-fluid -->
            </nav>          
            <div class="col-sm-12">
                <jsp:doBody/>
            </div>
        </div>
    </body>
</html>