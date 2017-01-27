<%--
  Created by IntelliJ IDEA.
  User: Владислав
  Date: 14.07.2016
  Time: 18:22
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
<f:message bundle="${locale}" key="locale.save" var="localeSave"/>
<f:message bundle="${locale}" key="locale.year" var="localeYear"/>
<f:message bundle="${locale}" key="locale.budget" var="localeBudget"/>
<f:message bundle="${locale}" key="locale.premiere" var="localePremiere"/>
<f:message bundle="${locale}" key="locale.lasting" var="localeLasting"/>
<f:message bundle="${locale}" key="locale.minute" var="localeMinute"/>
<f:message bundle="${locale}" key="locale.tagline" var="localeTagline"/>
<f:message bundle="${locale}" key="locale.movieForm" var="localeMovieForm"/>
<f:message bundle="${locale}" key="locale.name" var="localeName"/>
<f:message bundle="${locale}" key="locale.annotation" var="localeAnnotation"/>
<f:message bundle="${locale}" key="locale.image" var="localeImage"/>
<f:message bundle="${locale}" key="locale.enterName" var="localeEnterName"/>
<f:message bundle="${locale}" key="locale.enterYear" var="localeEnterYear"/>
<f:message bundle="${locale}" key="locale.enterTagline" var="localeEnterTagline"/>
<f:message bundle="${locale}" key="locale.enterBudget" var="localeEnterBudget"/>
<f:message bundle="${locale}" key="locale.enterLasting" var="localeEnterLasting"/>
<f:message bundle="${locale}" key="locale.enterAnnotation" var="localeEnterAnnotation"/>
<f:message bundle="${locale}" key="locale.enterImage" var="localeEnterImage"/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${localeMovieForm}</title>
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
                        <li><a href="Controller?command=user&id=${sessionScope.userId}"><span class="glyphicon glyphicon-user"></span> ${profilePageName}</a></li>
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
    <div class="jumbotron">
        <ul class="nav nav-tabs">
            <li class="active"><a href="Controller?command=add-movie">EN</a></li>
        </ul>
        <form action="Controller?command=add-movie" method="post" role="form">
            <div class="form-group">
                <label for="name">${localeName}</label>
                <input name="movieFormName" value="${requestScope.movieFormName}" type="text" minlength="1" maxlength="45" class="form-control" id="name" placeholder="${localeEnterName}">
            </div>
            <div class="form-group">
                <label for="year">${localeYear}</label>
                <input name="movieFormYear" value="${requestScope.movieFormYear}" type="number" min="1" class="form-control" id="year" placeholder="${localeEnterYear}">
            </div>
            <div class="form-group">
                <label for="tagline">${localeTagline}</label>
                <textarea name="movieFormTagline" minlength="1" class="form-control" id="tagline" placeholder="${localeEnterTagline}">${requestScope.movieFormTagline}</textarea>
            </div>
            <div class="form-group">
                <label for="budget">${localeBudget}</label>
                <input name="movieFormBudget" value="${requestScope.movieFormBudget}" type="number" min="1" class="form-control" id="budget" placeholder="${localeEnterBudget}">
            </div>
            <div class="form-group">
                <label for="premiere">${localePremiere}</label>
                <input name="movieFormPremiere" value="${requestScope.movieFormPremiere}" type="date" class="form-control" id="premiere">
            </div>
            <div class="form-group">
                <label for="lasting">${localeLasting} (${localeMinute})</label>
                <input name="movieFormLasting" value="${requestScope.movieFormLasting}" type="number" min="1" class="form-control" id="lasting" placeholder="${localeEnterLasting}">
            </div>
            <div class="form-group">
                <label for="annotation">${localeAnnotation}</label>
                <textarea name="movieFormAnnotation" minlength="1" class="form-control" id="annotation" placeholder="${localeEnterAnnotation}" rows="10">${requestScope.movieFormAnnotation}</textarea>
            </div>
            <div class="form-group">
                <label for="image">${localeImage}</label>
                <input name="movieFormImage" value="${requestScope.movieFormImage}" type="text" minlength="1" maxlength="150" class="form-control" id="image" placeholder="${localeEnterImage}">
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-success">${localeSave}</button>
            </div>
        </form>
    </div>
</main>
<footer class="container-fluid">
    <p class="text-center">EPAM Training Center, Java 5 2016, Kostevich Vladislav</p>
</footer>
</body>
</html>
