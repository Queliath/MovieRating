<%--
  Created by IntelliJ IDEA.
  User: Владислав
  Date: 14.07.2016
  Time: 18:03
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
<f:message bundle="${locale}" key="locale.searchMovieByGenre" var="searchMovieByGenre"/>
<f:message bundle="${locale}" key="locale.searchMovieByCountry" var="searchMovieByCountry"/>
<f:message bundle="${locale}" key="locale.findYourMovie" var="findYourMovie"/>
<f:message bundle="${locale}" key="locale.enterMovieName" var="enterMovieName"/>
<f:message bundle="${locale}" key="locale.findButton" var="findButton"/>
<f:message bundle="${locale}" key="locale.recentMovies" var="localeRecentMovies"/>
<f:message bundle="${locale}" key="locale.recentComments" var="localeRecentComments"/>
<f:message bundle="${locale}" key="locale.country" var="localeCountry"/>
<f:message bundle="${locale}" key="locale.genre" var="localeGenre"/>
<f:message bundle="${locale}" key="locale.director" var="localeDirector"/>
<f:message bundle="${locale}" key="locale.year" var="localeYear"/>
<f:message bundle="${locale}" key="locale.budget" var="localeBudget"/>
<f:message bundle="${locale}" key="locale.premiere" var="localePremiere"/>
<f:message bundle="${locale}" key="locale.lasting" var="localeLasting"/>
<f:message bundle="${locale}" key="locale.minute" var="localeMinute"/>
<f:message bundle="${locale}" key="locale.rating" var="localeRating"/>
<f:message bundle="${locale}" key="locale.toMovie" var="localeToMovie"/>
<f:message bundle="${locale}" key="locale.serviceError" var="localeServiceError"/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${siteName}</title>
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
                    <li class="active"><a href="Controller?command=welcome">${mainPageName}</a></li>
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
    <div class="row">
        <div class="col-sm-6">
            <h2>${searchMovieByGenre}</h2>
            <div class="list-group">
                <c:if test="${requestScope.genres != null}">
                    <c:forEach items="${requestScope.genres}" var="genre">
                        <a href="Controller?command=movies&searchFormGenres[]=${genre.id}" class="list-group-item">${genre.name}</a>
                    </c:forEach>
                </c:if>
            </div>
        </div>
        <div class="col-sm-6">
            <h2>${searchMovieByCountry}</h2>
            <div class="list-group">
                <c:if test="${requestScope.countries != null}">
                    <c:forEach items="${requestScope.countries}" var="country">
                        <a href="Controller?command=movies&searchFormCountries[]=${country.id}" class="list-group-item">${country.name}</a>
                    </c:forEach>
                </c:if>
            </div>
        </div>
    </div>
    <div class="jumbotron">
        <form action="Controller?command=movies" method="post" role="form">
            <div class="form-group">
                <label for="keyword">${findYourMovie}:</label>
                <input name="searchFormName" type="text" class="form-control" id="keyword" placeholder="${enterMovieName}">
            </div>
            <button type="submit" class="btn btn-default">${findButton}</button>
        </form>
    </div>
    <div class="row">
        <div class="col-sm-6">
            <h2>${localeRecentMovies}</h2>
            <c:if test="${requestScope.movies != null}">
                <c:forEach items="${requestScope.movies}" var="movie">
                    <div class="well clearfix">
                        <a href="Controller?command=movie&id=${movie.id}">
                            <img src="${movie.image}" class="img-rounded" alt="${movie.name}">
                        </a>
                        <a href="Controller?command=movie&id=${movie.id}"><h3>${movie.name}</h3></a>
                        <ul>
                            <li>${localeCountry}: <c:forEach items="${movie.countries}" var="country"><a href="Controller?command=movies&searchFormCountries[]=${country.id}">${country.name}</a> </c:forEach></li>
                            <li>${localeGenre}: <c:forEach items="${movie.genres}" var="genre"><a href="Controller?command=movies&searchFormGenres[]=${genre.id}">${genre.name}</a> </c:forEach></li>
                            <li>${localeDirector}: <c:forEach items="${movie.directors}" var="director"><a href="Controller?command=person&id=${director.id}">${director.name}</a> </c:forEach></li>
                            <li>${localeYear}: ${movie.year}</li>
                            <li>${localeBudget}: <f:formatNumber value="${movie.budget}"/> $</li>
                            <li>${localePremiere}: <f:formatDate value="${movie.premiere}" type="date" dateStyle="long"/></li>
                            <li>${localeLasting}: ${movie.lasting} ${localeMinute}</li>
                            <li>${localeRating}: <f:formatNumber value="${movie.averageRating}" maxFractionDigits="2"/></li>
                        </ul>
                        <p>${movie.annotation}</p>
                    </div>
                </c:forEach>
            </c:if>
        </div>
        <div class="col-sm-6">
            <h2>${localeRecentComments}</h2>
            <c:if test="${requestScope.comments != null}">
                <c:forEach items="${requestScope.comments}" var="comment">
                    <div class="well clearfix">
                        <a href="Controller?command=user&id=${comment.user.id}">
                            <img src="${comment.user.photo}" class="img-circle" alt="${comment.user.firstName} ${comment.user.lastName}">
                        </a>
                        <h3>${comment.title}</h3>
                        <p><a href="Controller?command=user&id=${comment.user.id}">${comment.user.firstName} ${comment.user.lastName}</a> ${localeToMovie} <a href="Controller?command=movie&id=${comment.movie.id}">${comment.movie.name}</a></p>
                        <p>${comment.content}</p>
                    </div>
                </c:forEach>
            </c:if>
        </div>
    </div>
</main>
<footer class="container-fluid">
    <p class="text-center">EPAM Training Center, Java 5 2016, Kostevich Vladislav</p>
</footer>
</body>
</html>
