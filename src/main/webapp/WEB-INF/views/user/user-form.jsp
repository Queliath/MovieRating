<%--
  Created by IntelliJ IDEA.
  User: Владислав
  Date: 14.07.2016
  Time: 18:40
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
<f:message bundle="${locale}" key="locale.registrationPageName" var="registrationPageName"/>
<f:message bundle="${locale}" key="locale.loginPageName" var="loginPageName"/>
<f:message bundle="${locale}" key="locale.profilePageName" var="profilePageName"/>
<f:message bundle="${locale}" key="locale.logoutName" var="logoutName"/>
<f:message bundle="${locale}" key="locale.personsPageName" var="personsPageName"/>
<f:message bundle="${locale}" key="locale.usersPageName" var="usersPageName"/>
<f:message bundle="${locale}" key="locale.other" var="localeOther"/>
<f:message bundle="${locale}" key="locale.genresPageName" var="genresPageName"/>
<f:message bundle="${locale}" key="locale.countriesPageName" var="countriesPageName"/>
<f:message bundle="${locale}" key="locale.serviceError" var="localeServiceError"/>
<f:message bundle="${locale}" key="locale.noUser" var="localeNoUser"/>
<f:message bundle="${locale}" key="locale.email" var="localeEmail"/>
<f:message bundle="${locale}" key="locale.password" var="localePassword"/>
<f:message bundle="${locale}" key="locale.enterEmail" var="localeEnterEmail"/>
<f:message bundle="${locale}" key="locale.enterPassword" var="localeEnterPassword"/>
<f:message bundle="${locale}" key="locale.rating" var="localeRating"/>
<f:message bundle="${locale}" key="locale.usedEmail" var="localeUsedEmail"/>
<f:message bundle="${locale}" key="locale.status" var="localeStatus"/>
<f:message bundle="${locale}" key="locale.firstName" var="localeFirstName"/>
<f:message bundle="${locale}" key="locale.enterFirstName" var="localeEnterFirstName"/>
<f:message bundle="${locale}" key="locale.lastName" var="localeLastName"/>
<f:message bundle="${locale}" key="locale.enterLastName" var="localeEnterLastName"/>
<f:message bundle="${locale}" key="locale.photo" var="localePhoto"/>
<f:message bundle="${locale}" key="locale.statusNormal" var="localeStatusNormal"/>
<f:message bundle="${locale}" key="locale.statusBanned" var="localeStatusBanned"/>
<f:message bundle="${locale}" key="locale.statusAdmin" var="localeStatusAdmin"/>
<f:message bundle="${locale}" key="locale.save" var="localeSave"/>
<f:message bundle="${locale}" key="locale.saveSuccess" var="localeSaveSuccess"/>
<f:message bundle="${locale}" key="locale.userForm" var="localeUserForm"/>
<f:message bundle="${locale}" key="locale.enterPhoto" var="localeEnterPhoto"/>
<f:message bundle="${locale}" key="locale.language" var="localeLanguage"/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${localeUserForm}</title>
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
                    <c:if test="${sessionScope.userId != null}">
                        <c:if test='${sessionScope.userStatus eq "admin"}'>
                            <li><a href="Controller?command=users">${usersPageName}</a></li>
                            <li class="dropdown">
                                <a class="dropdown-toggle" data-toggle="dropdown" href="#">${localeOther} <span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li><a href="Controller?command=genres">${genresPageName}</a></li>
                                    <li><a href="Controller?command=countries">${countriesPageName}</a></li>
                                </ul>
                            </li>
                        </c:if>
                    </c:if>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <c:if test="${sessionScope.userId != null}">
                        <li <c:if test="${sessionScope.userId == requestScope.user.id}">class="active"</c:if>><a href="Controller?command=user&id=${sessionScope.userId}"><span class="glyphicon glyphicon-user"></span> ${profilePageName}</a></li>
                        <li><a href="Controller?command=logout"><span class="glyphicon glyphicon-log-out"></span> ${logoutName}</a></li>
                    </c:if>
                    <c:if test="${sessionScope.userId == null}">
                        <li><a href="Controller?command=registration"><span class="glyphicon glyphicon-user"></span> ${registrationPageName}</a></li>
                        <li><a href="Controller?command=login"><span class="glyphicon glyphicon-log-in"></span> ${loginPageName}</a></li>
                    </c:if>
                    <li <c:if test='${requestScope.selectedLanguage eq "RU"}'>class="active"</c:if>><form id="change-language-ru" action="Controller?command=change-language" method="post"><input type="hidden" name="changeLanguageId" value="RU"><a href="#" onclick="document.getElementById('change-language-ru').submit()">RU</a></form></li>
                    <li <c:if test='${requestScope.selectedLanguage eq "EN"}'>class="active"</c:if>><form id="change-language-en" action="Controller?command=change-language" method="post"><input type="hidden" name="changeLanguageId" value="EN"><a href="#" onclick="document.getElementById('change-language-en').submit()">EN</a></form></li>
                </ul>
            </div>
        </div>
    </nav>
</header>
<main class="container">
    <c:if test="${requestScope.serviceError}">
        <div class="alert alert-danger fade in">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                ${localeServiceError}
        </div>
    </c:if>
    <c:if test="${requestScope.user == null}">
        <div class="alert alert-info fade in">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                ${localeNoUser}
        </div>
    </c:if>
    <c:if test="${requestScope.user != null}">
        <div class="jumbotron">
            <form method="post" role="form">
                <c:if test="${sessionScope.userId == requestScope.user.id}">
                    <div class="form-group">
                        <label for="email">${localeEmail}</label>
                        <input name="userFormEmail" value="${requestScope.user.email}" type="email" minlength="3" maxlength="45" class="form-control" id="email" placeholder="${localeEnterEmail}">
                    </div>
                    <div class="form-group">
                        <label for="password">${localePassword}</label>
                        <input name="userFormPassword" value="${requestScope.user.password}" type="password" minlength="1" maxlength="45" class="form-control" id="password" placeholder="${localeEnterPassword}">
                    </div>
                    <div class="form-group">
                        <label for="first-name">${localeFirstName}</label>
                        <input name="userFormFirstName" value="${requestScope.user.firstName}" type="text" minlength="1" maxlength="25" class="form-control" id="first-name" placeholder="${localeEnterFirstName}">
                    </div>
                    <div class="form-group">
                        <label for="second-name">${localeLastName}</label>
                        <input name="userFormLastName" value="${requestScope.user.lastName}" type="text" minlength="1" maxlength="25" class="form-control" id="second-name" placeholder="${localeEnterLastName}">
                    </div>
                    <div class="form-group">
                        <label for="photo">${localePhoto}</label>
                        <input name="userFormPhoto" value="${requestScope.user.photo}" type="text" maxlength="150" class="form-control" id="photo" placeholder="${localeEnterPhoto}">
                    </div>
                    <div class="form-group">
                        <label for="language-id">${localeLanguage}</label>
                        <select name="userFormLanguageId" class="form-control" id="language-id">
                            <option value="EN" <c:if test='${requestScope.user.languageId eq "EN"}'>selected</c:if>>EN</option>
                            <option value="RU" <c:if test='${requestScope.user.languageId eq "RU"}'>selected</c:if>>RU</option>
                        </select>
                    </div>
                </c:if>
                <c:if test='${sessionScope.userStatus eq "admin"}'>
                    <div class="form-group">
                        <label for="rating">${localeRating}</label>
                        <input name="userFormRating" value="${requestScope.user.rating}" type="number" class="form-control" id="rating">
                    </div>
                    <div class="form-group">
                        <label for="status">${localeStatus}</label>
                        <select name="userFormStatus" class="form-control" id="status">
                            <option value="normal" <c:if test='${requestScope.user.status eq "normal"}'>selected</c:if>>${localeStatusNormal}</option>
                            <option value="banned" <c:if test='${requestScope.user.status eq "banned"}'>selected</c:if>>${localeStatusBanned}</option>
                            <option value="admin" <c:if test='${requestScope.user.status eq "admin"}'>selected</c:if>>${localeStatusAdmin}</option>
                        </select>
                    </div>
                </c:if>
                <div class="form-group">
                    <button type="submit" class="btn btn-success">${localeSave}</button>
                </div>
            </form>
            <c:if test="${requestScope.saveSuccess}">
                <div class="alert alert-success fade in">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                        ${localeSaveSuccess}
                </div>
            </c:if>
            <c:if test="${requestScope.wrongEmail}">
                <div class="alert alert-danger fade in">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    ${localeUsedEmail}
                </div>
            </c:if>
        </div>
    </c:if>
</main>
<footer class="container-fluid">
    <p class="text-center">EPAM Training Center, Java 5 2016, Kostevich Vladislav</p>
</footer>
</body>
</html>
