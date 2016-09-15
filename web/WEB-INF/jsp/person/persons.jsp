<%--
  Created by IntelliJ IDEA.
  User: Владислав
  Date: 14.07.2016
  Time: 18:28
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
<f:message bundle="${locale}" key="locale.moviesTotal" var="localeMoviesTotal"/>
<f:message bundle="${locale}" key="locale.dateOfBirth" var="localeDateOfBirth"/>
<f:message bundle="${locale}" key="locale.placeOfBirth" var="localePlaceOfBirth"/>
<f:message bundle="${locale}" key="locale.serviceError" var="localeServiceError"/>
<f:message bundle="${locale}" key="locale.searchCriteria" var="localeSearchCriteria"/>
<f:message bundle="${locale}" key="locale.findButton" var="localeFindButton"/>
<f:message bundle="${locale}" key="locale.noResults" var="localeNoResults"/>
<f:message bundle="${locale}" key="locale.displaying" var="localeDisplaying"/>
<f:message bundle="${locale}" key="locale.of" var="localeOf"/>
<f:message bundle="${locale}" key="locale.personName" var="localePersonName"/>
<f:message bundle="${locale}" key="locale.addPerson" var="localeAddPerson"/>
<f:message bundle="${locale}" key="locale.add" var="localeAdd"/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${personsPageName}</title>
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
                    <li class="active"><a href="Controller?command=persons">${personsPageName}</a></li>
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
        <h3>${localeSearchCriteria}</h3>
        <form action="Controller?command=persons" method="post" role="form" id="search-form">
            <input name="page" value="1" type="hidden">
            <input name="movie" value="${requestScope.movieId}" type="hidden">
            <input name="rel" value="${requestScope.relationType}" type="hidden">
            <div class="row">
                <div class="form-group col-sm-6">
                    <label for="name">${localePersonName}:</label>
                    <input name="searchFormName" value="${requestScope.searchFormName}" type="text" id="name" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-default">${localeFindButton}</button>
            </div>
        </form>
    </div>
    <c:if test="${sessionScope.userId != null}">
        <c:if test='${sessionScope.userStatus eq "admin"}'>
            <c:if test="${requestScope.movieId == null && requestScope.relationType == null}">
                <a href="Controller?command=add-person" class="btn btn-success btn-lg">${localeAddPerson}</a>
            </c:if>
        </c:if>
    </c:if>
    <c:if test="${requestScope.personsCount == 0}">
        <div class="alert alert-info fade in">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            ${localeNoResults}
        </div>
    </c:if>
    <c:if test="${requestScope.personsCount != 0}">
        <p>${localeDisplaying} ${requestScope.personsFrom}-${requestScope.personsTo} ${localeOf} ${requestScope.personsCount}</p>
        <c:if test="${requestScope.persons != null}">
            <c:forEach items="${requestScope.persons}" var="person">
                <div class="well clearfix">
                    <a href="Controller?command=person&id=${person.id}">
                        <img src="${person.photo}" class="img-rounded" alt="${person.name}">
                    </a>
                    <a href="Controller?command=person&id=${person.id}"><h3>${person.name}</h3></a>
                    <ul>
                        <li>${localeMoviesTotal}: ${person.moviesTotal}</li>
                        <li>${localeDateOfBirth}: <f:formatDate value="${person.dateOfBirth}" type="date" dateStyle="long"/></li>
                        <li>${localePlaceOfBirth}: ${person.placeOfBirth}</li>
                    </ul>
                    <c:if test='${sessionScope.userStatus eq "admin" && requestScope.movieId != null && requestScope.relationType != null}'>
                        <form action="Controller?command=add-mpr" method="post" role="form">
                            <input name="movieId" value="${requestScope.movieId}" type="hidden">
                            <input name="personId" value="${person.id}" type="hidden">
                            <input name="relationType" value="${requestScope.relationType}" type="hidden">
                            <button type="submit" class="btn btn-success btn-sm">${localeAdd}</button>
                        </form>
                    </c:if>
                </div>
            </c:forEach>
        </c:if>
        <c:if test="${requestScope.pagination != null}">
            <ul class="pagination">
                <c:forEach items="${requestScope.pagination}" var="paginationItem">
                    <li <c:if test="${paginationItem == requestScope.activePage}">class="active"</c:if>><a href="#">${paginationItem}</a></li>
                </c:forEach>
            </ul>
        </c:if>
    </c:if>
</main>
<footer class="container-fluid">
    <p class="text-center">EPAM Training Center, Java 5 2016, Kostevich Vladislav</p>
</footer>
<script src="js/pagination.js"></script>
</body>
</html>
