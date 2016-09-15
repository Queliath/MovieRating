<%--
  Created by IntelliJ IDEA.
  User: Владислав
  Date: 14.07.2016
  Time: 18:36
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
<f:message bundle="${locale}" key="locale.email" var="localeEmail"/>
<f:message bundle="${locale}" key="locale.rating" var="localeRating"/>
<f:message bundle="${locale}" key="locale.dateOfRegistration" var="localeDateOfRegistration"/>
<f:message bundle="${locale}" key="locale.status" var="localeStatus"/>
<f:message bundle="${locale}" key="locale.statusNormal" var="localeStatusNormal"/>
<f:message bundle="${locale}" key="locale.statusBanned" var="localeStatusBanned"/>
<f:message bundle="${locale}" key="locale.statusAdmin" var="localeStatusAdmin"/>
<f:message bundle="${locale}" key="locale.searchCriteria" var="localeSearchCriteria"/>
<f:message bundle="${locale}" key="locale.from" var="localeFrom"/>
<f:message bundle="${locale}" key="locale.to" var="localeTo"/>
<f:message bundle="${locale}" key="locale.findButton" var="localeFindButton"/>
<f:message bundle="${locale}" key="locale.noResults" var="localeNoResults"/>
<f:message bundle="${locale}" key="locale.displaying" var="localeDisplaying"/>
<f:message bundle="${locale}" key="locale.of" var="localeOf"/>
<f:message bundle="${locale}" key="locale.firstName" var="localeFirstName"/>
<f:message bundle="${locale}" key="locale.lastName" var="localeLastName"/>
<f:message bundle="${locale}" key="locale.language" var="localeLanguage"/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${usersPageName}</title>
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
                            <li class="active"><a href="Controller?command=users">${usersPageName}</a></li>
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
    <div class="jumbotron">
        <h3>${localeSearchCriteria}</h3>
        <form action="Controller?command=users" method="post" role="form" id="search-form">
            <input name="page" value="1" type="hidden">
            <div class="row">
                <div class="form-group col-sm-6">
                    <label for="email">${localeEmail}:</label>
                    <input name="searchFormEmail" value="${requestScope.searchFormEmail}" type="email" id="email" class="form-control">
                </div>
                <div class="form-group col-sm-6">
                    <label for="first-name">${localeFirstName}:</label>
                    <input name="searchFormFirstName" value="${requestScope.searchFormFirstName}" type="text" id="first-name" class="form-control">
                </div>
                <div class="form-group col-sm-6">
                    <label for="last-name">${localeLastName}:</label>
                    <input name="searchFormLastName" value="${requestScope.searchFormLastName}" type="text" id="last-name" class="form-control">
                </div>
                <div class="form-group col-sm-6 row">
                    <div class="col-xs-12">
                        <label>${localeDateOfRegistration}</label>
                    </div>
                    <div class="col-xs-6">
                        <label for="min-date">${localeFrom}:</label>
                        <input name="searchFormMinDateOfRegistry" value="${requestScope.searchFormMinDateOfRegistry}" type="date" id="min-date" class="form-control">
                    </div>
                    <div class="col-xs-6">
                        <label for="max-date">${localeTo}:</label>
                        <input name="searchFormMaxDateOfRegistry" value="${requestScope.searchFormMaxDateOfRegistry}" type="date" id="max-date" class="form-control">
                    </div>
                </div>
                <div class="form-group col-sm-6 row">
                    <div class="col-xs-12">
                        <label>${localeRating}</label>
                    </div>
                    <div class="col-xs-6">
                        <label for="min-rating">${localeFrom}:</label>
                        <input name="searchFormMinRating" value="${requestScope.searchFormMinRating}" type="number" id="min-rating" class="form-control">
                    </div>
                    <div class="col-xs-6">
                        <label for="max-rating">${localeTo}:</label>
                        <input name="searchFormMaxRating" value="${requestScope.searchFormMaxRating}" type="number" id="max-rating" class="form-control">
                    </div>
                </div>
                <div class="form-group col-sm-6">
                    <label>${localeStatus}</label>
                    <div class="checkbox">
                        <label><input name="searchFormStatuses[]" value="normal" type="checkbox" <c:forEach items="${requestScope.searchFormStatuses}" var="searchFormStatus"><c:if test='${searchFormStatus eq "normal"}'>checked</c:if></c:forEach>> ${localeStatusNormal}</label>
                    </div>
                    <div class="checkbox">
                        <label><input name="searchFormStatuses[]" value="banned" type="checkbox" <c:forEach items="${requestScope.searchFormStatuses}" var="searchFormStatus"><c:if test='${searchFormStatus eq "banned"}'>checked</c:if></c:forEach>> ${localeStatusBanned}</label>
                    </div>
                    <div class="checkbox">
                        <label><input name="searchFormStatuses[]" value="admin" type="checkbox" <c:forEach items="${requestScope.searchFormStatuses}" var="searchFormStatus"><c:if test='${searchFormStatus eq "admin"}'>checked</c:if></c:forEach>> ${localeStatusAdmin}</label>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-default">${localeFindButton}</button>
            </div>
        </form>
    </div>
    <c:if test="${requestScope.usersCount == 0}">
        <div class="alert alert-info fade in">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                ${localeNoResults}
        </div>
    </c:if>
    <c:if test="${requestScope.usersCount != 0}">
        <p>${localeDisplaying} ${requestScope.usersFrom}-${requestScope.usersTo} ${localeOf} ${requestScope.usersCount}</p>
        <c:if test="${requestScope.users != null}">
            <c:forEach items="${requestScope.users}" var="user">
                <div class="well clearfix">
                    <a href="Controller?command=user&id=${user.id}">
                        <img src="${user.photo}" class="img-rounded" alt="${user.firstName} ${user.lastName}">
                    </a>
                    <a href="Controller?command=user&id=${user.id}"><h3>${user.firstName} ${user.lastName}</h3></a>
                    <ul>
                        <li>${localeEmail}: ${user.email}</li>
                        <li>${localeDateOfRegistration}: <f:formatDate value="${user.dateOfRegistry}" type="date" dateStyle="long"/></li>
                        <li>${localeRating}: ${user.rating}</li>
                        <li>${localeStatus}: <c:if test='${user.status eq "normal"}'>${localeStatusNormal}</c:if><c:if test='${user.status eq "banned"}'>${localeStatusBanned}</c:if><c:if test='${user.status eq "admin"}'>${localeStatusAdmin}</c:if></li>
                        <li>${localeLanguage}: ${user.languageId}</li>
                    </ul>
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
