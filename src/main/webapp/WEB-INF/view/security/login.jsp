<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix= "c" uri= "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix= "spring" uri= "http://www.springframework.org/tags"%>
<%@ taglib prefix= "security" uri= "http://www.springframework.org/security/tags" %>
<%@ taglib prefix= "page" tagdir="/WEB-INF/tags/"%>

<page:mainTamplate>
    <jsp:attribute name="title">Вход в систему</jsp:attribute>
    <jsp:body>
        <div class="col-sm-6 col-sm-offset-3 col-md-offset-3 col-lg-offset-3">
            <form name= "form" action= "j_spring_security_check" method= "post" class= "form-signin">
                <security:authorize access= "hasAnyRole('ROLE_ADMIN','ROLE_USER')" var= "isUser"/>

                <font size= "2" color= "red">
                <c:if test= "${not isUser}">
                    <c:if test="${not param.error}">
                        <c:if test= "${param.isRedirected}">
                            Вы не вошли
                        </c:if>
                    </c:if>
                </c:if>
                </font>

                <font size= "2" color= "green">
                <c:if test= "${isUser}">Вы вошли как:
                    <security:authentication property= "principal.username"/> с ролью:
                    <b><security:authentication property= "principal.authorities"/></b>
                </c:if>
                </font>
                <br/>
                <c:if test= "${not empty param.error}">
                    <font size= "2" color= "red"><b>Неправильный логин или пароль</b></font>
                </c:if>



                <h2 class= "form-signin-heading">Пожалуйста войдите</h2>

                <div class="form-group">
                    <label for= "inputLogin" class= "form-label">Логин</label>
                    <input id= "inputLogin" class= "form-control" name= "j_username" placeholder="Введите логин" required autofocus/>
                </div>
                <div class="form-group">
                    <label for= "inputPassword" class= "form-label">Пароль</label>
                    <input type= "password" id= "inputPassword" class= "form-control" name= "j_password" placeholder="Введите пароль" required/>
                </div>
                <%--div class= "checkbox">
                    <label>
                        <input type= "checkbox" id= "rememberme"  name= "_spring_security_remember_me"/>Запомнить меня
                    </label>
                </div--%>
                <input type= "submit" value= "Войти" class= "btn btn-lg btn-primary btn-block" >
            </form>

        </div>
    </jsp:body>
</page:mainTamplate>