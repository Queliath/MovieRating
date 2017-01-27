<%--
  Created by IntelliJ IDEA.
  User: Владислав
  Date: 14.07.2016
  Time: 18:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setLocale value="${requestScope.selectedLanguage}"/>
<f:setBundle basename="locale" var="locale"/>
<f:message bundle="${locale}" key="locale.siteName" var="siteName"/>
<f:message bundle="${locale}" key="locale.mainPageName" var="mainPageName"/>
<f:message bundle="${locale}" key="locale.catalogPageName" var="catalogPageName"/>
<f:message bundle="${locale}" key="locale.personsPageName" var="personsPageName"/>
<f:message bundle="${locale}" key="locale.registrationPageName" var="registrationPageName"/>
<f:message bundle="${locale}" key="locale.loginPageName" var="loginPageName"/>
<f:message bundle="${locale}" key="locale.email" var="localeEmail"/>
<f:message bundle="${locale}" key="locale.password" var="localePassword"/>
<f:message bundle="${locale}" key="locale.enterEmail" var="localeEnterEmail"/>
<f:message bundle="${locale}" key="locale.enterPassword" var="localeEnterPassword"/>
<f:message bundle="${locale}" key="locale.firstName" var="localeFirstName"/>
<f:message bundle="${locale}" key="locale.enterFirstName" var="localeEnterFirstName"/>
<f:message bundle="${locale}" key="locale.lastName" var="localeLastName"/>
<f:message bundle="${locale}" key="locale.enterLastName" var="localeEnterLastName"/>
<f:message bundle="${locale}" key="locale.registrationButton" var="localeRegistrationButton"/>
<f:message bundle="${locale}" key="locale.usedEmail" var="localeUsedEmail"/>
<f:message bundle="${locale}" key="locale.serviceError" var="localeServiceError"/>
<f:message bundle="${locale}" key="locale.registrationSuccess" var="localeRegistrationSuccess"/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${registrationPageName}</title>
    <!--<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">-->
    <!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>-->
    <!--<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>-->
    <link rel="shortcut icon" href="img/movie-roll.png" type="image/png">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
<header>
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="Controller?command=welcome">${siteName}</a>
            </div>
            <div class="collapse navbar-collapse" id="myNavbar">
                <ul class="nav navbar-nav">
                    <li><a href="Controller?command=welcome">${mainPageName}</a></li>
                    <li><a href="Controller?command=movies">${catalogPageName}</a></li>
                    <li><a href="Controller?command=persons">${personsPageName}</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li class="active"><a href="Controller?command=registration"><span class="glyphicon glyphicon-user"></span> ${registrationPageName}</a></li>
                    <li><a href="Controller?command=login"><span class="glyphicon glyphicon-log-in"></span> ${loginPageName}</a></li>
                    <li <c:if test='${requestScope.selectedLanguage eq "RU"}'>class="active"</c:if>><form id="change-language-ru" action="Controller?command=change-language" method="post"><input type="hidden" name="changeLanguageId" value="RU"><a href="#" onclick="document.getElementById('change-language-ru').submit()">RU</a></form></li>
                    <li <c:if test='${requestScope.selectedLanguage eq "EN"}'>class="active"</c:if>><form id="change-language-en" action="Controller?command=change-language" method="post"><input type="hidden" name="changeLanguageId" value="EN"><a href="#" onclick="document.getElementById('change-language-en').submit()">EN</a></form></li>
                </ul>
            </div>
        </div>
    </nav>
</header>
<main class="container">
    <div class="jumbotron">
        <form action="Controller?command=registration" method="post" role="form">
            <div class="form-group">
                <label for="email">${localeEmail}</label>
                <input name="registrationFormEmail" value="${requestScope.registrationFormEmail}" type="email" minlength="3" maxlength="45" class="form-control" id="email" placeholder="${localeEnterEmail}">
            </div>
            <div class="form-group">
                <label for="password">${localePassword}</label>
                <input name="registrationFormPassword" value="${requestScope.registrationFormPassword}" type="password" minlength="1" maxlength="45" class="form-control" id="password" placeholder="${localeEnterPassword}">
            </div>
            <div class="form-group">
                <label for="first-name">${localeFirstName}</label>
                <input name="registrationFormFirstName" value="${requestScope.registrationFormFirstName}" type="text" minlength="1" maxlength="25" class="form-control" id="first-name" placeholder="${localeEnterFirstName}">
            </div>
            <div class="form-group">
                <label for="second-name">${localeLastName}</label>
                <input name="registrationFormLastName" value="${requestScope.registrationFormLastName}" type="text" minlength="1" maxlength="25" class="form-control" id="second-name" placeholder="${localeEnterLastName}">
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-default">${localeRegistrationButton}</button>
            </div>
        </form>
        <c:if test="${requestScope.registrationSuccess}">
            <div class="alert alert-success fade in">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                ${localeRegistrationSuccess}
            </div>
        </c:if>
        <c:if test="${requestScope.wrongEmail}">
            <div class="alert alert-danger fade in">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                ${localeUsedEmail}
            </div>
        </c:if>
        <c:if test="${requestScope.serviceError}">
            <div class="alert alert-danger fade in">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                ${localeServiceError}
            </div>
        </c:if>
    </div>
</main>
<footer class="container-fluid">
    <p class="text-center">EPAM Training Center, Java 5 2016, Kostevich Vladislav</p>
</footer>
</body>
</html>
