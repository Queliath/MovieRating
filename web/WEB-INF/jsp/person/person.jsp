<%--
  Created by IntelliJ IDEA.
  User: Владислав
  Date: 14.07.2016
  Time: 18:29
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
<f:message bundle="${locale}" key="locale.country" var="localeCountry"/>
<f:message bundle="${locale}" key="locale.genre" var="localeGenre"/>
<f:message bundle="${locale}" key="locale.director" var="localeDirector"/>
<f:message bundle="${locale}" key="locale.year" var="localeYear"/>
<f:message bundle="${locale}" key="locale.budget" var="localeBudget"/>
<f:message bundle="${locale}" key="locale.premiere" var="localePremiere"/>
<f:message bundle="${locale}" key="locale.lasting" var="localeLasting"/>
<f:message bundle="${locale}" key="locale.minute" var="localeMinute"/>
<f:message bundle="${locale}" key="locale.rating" var="localeRating"/>
<f:message bundle="${locale}" key="locale.serviceError" var="localeServiceError"/>
<f:message bundle="${locale}" key="locale.noPerson" var="localeNoPerson"/>
<f:message bundle="${locale}" key="locale.asActor" var="localeAsActor"/>
<f:message bundle="${locale}" key="locale.asDirector" var="localeAsDirector"/>
<f:message bundle="${locale}" key="locale.asProducer" var="localeAsProducer"/>
<f:message bundle="${locale}" key="locale.asWriter" var="localeAsWriter"/>
<f:message bundle="${locale}" key="locale.asPainter" var="localeAsPainter"/>
<f:message bundle="${locale}" key="locale.asOperator" var="localeAsOperator"/>
<f:message bundle="${locale}" key="locale.asEditor" var="localeAsEditor"/>
<f:message bundle="${locale}" key="locale.asComposer" var="localeAsComposer"/>
<f:message bundle="${locale}" key="locale.edit" var="localeEdit"/>
<f:message bundle="${locale}" key="locale.delete" var="localeDelete"/>
<f:message bundle="${locale}" key="locale.addMovieToPerson" var="localeAddMovieToPerson"/>
<f:message bundle="${locale}" key="locale.deleteTitle" var="localeDeleteTitle"/>
<f:message bundle="${locale}" key="locale.deleteBody" var="localeDeleteBody"/>
<f:message bundle="${locale}" key="locale.cancel" var="localeCancel"/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${requestScope.person.name}</title>
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
    <c:if test="${requestScope.person == null}">
        <div class="alert alert-info fade in">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                ${localeNoPerson}
        </div>
    </c:if>
    <c:if test="${requestScope.person != null}">
        <div class="jumbotron">
            <h3>${requestScope.person.name}</h3>
            <div class="clearfix">
                <img src="${requestScope.person.photo}" class="img-rounded" alt="${requestScope.person.name}">
                <ul>
                    <li>${localeMoviesTotal}: ${requestScope.person.moviesTotal}</li>
                    <li>${localeDateOfBirth}: <f:formatDate value="${requestScope.person.dateOfBirth}" type="date" dateStyle="long"/></li>
                    <li>${localePlaceOfBirth}: ${requestScope.person.placeOfBirth}</li>
                </ul>
            </div>
            <c:if test='${sessionScope.userStatus eq "admin"}'>
                <a href="Controller?command=edit-person&id=${requestScope.person.id}" class="btn btn-success">${localeEdit}</a>
                <a class="btn btn-danger" data-toggle="modal" data-target="#person-remove-modal" data-id="${requestScope.person.id}">${localeDelete}</a>
            </c:if>
            <c:if test='${requestScope.person.moviesAsActor != null || sessionScope.userStatus eq "admin"}'>
                <h3>${localeAsActor}</h3>
                <c:if test='${sessionScope.userStatus eq "admin"}'>
                    <a href="Controller?command=movies&person=${requestScope.person.id}&rel=1" class="btn btn-success">${localeAddMovieToPerson}</a>
                </c:if>
                <c:forEach items="${requestScope.person.moviesAsActor}" var="movie">
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
                        <c:if test='${sessionScope.userStatus eq "admin"}'>
                            <a href="#" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#mpr1-remove-modal" data-id="${movie.id}">${localeDelete}</a>
                        </c:if>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test='${requestScope.person.moviesAsDirector != null || sessionScope.userStatus eq "admin"}'>
                <h3>${localeAsDirector}</h3>
                <c:if test='${sessionScope.userStatus eq "admin"}'>
                    <a href="Controller?command=movies&person=${requestScope.person.id}&rel=2" class="btn btn-success">${localeAddMovieToPerson}</a>
                </c:if>
                <c:forEach items="${requestScope.person.moviesAsDirector}" var="movie">
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
                        <c:if test='${sessionScope.userStatus eq "admin"}'>
                            <a href="#" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#mpr2-remove-modal" data-id="${movie.id}">${localeDelete}</a>
                        </c:if>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test='${requestScope.person.moviesAsProducer != null || sessionScope.userStatus eq "admin"}'>
                <h3>${localeAsProducer}</h3>
                <c:if test='${sessionScope.userStatus eq "admin"}'>
                    <a href="Controller?command=movies&person=${requestScope.person.id}&rel=3" class="btn btn-success">${localeAddMovieToPerson}</a>
                </c:if>
                <c:forEach items="${requestScope.person.moviesAsProducer}" var="movie">
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
                        <c:if test='${sessionScope.userStatus eq "admin"}'>
                            <a href="#" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#mpr3-remove-modal" data-id="${movie.id}">${localeDelete}</a>
                        </c:if>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test='${requestScope.person.moviesAsWriter != null || sessionScope.userStatus eq "admin"}'>
                <h3>${localeAsWriter}</h3>
                <c:if test='${sessionScope.userStatus eq "admin"}'>
                    <a href="Controller?command=movies&person=${requestScope.person.id}&rel=4" class="btn btn-success">${localeAddMovieToPerson}</a>
                </c:if>
                <c:forEach items="${requestScope.person.moviesAsWriter}" var="movie">
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
                        <c:if test='${sessionScope.userStatus eq "admin"}'>
                            <a href="#" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#mpr4-remove-modal" data-id="${movie.id}">${localeDelete}</a>
                        </c:if>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test='${requestScope.person.moviesAsPainter != null || sessionScope.userStatus eq "admin"}'>
                <h3>${localeAsPainter}</h3>
                <c:if test='${sessionScope.userStatus eq "admin"}'>
                    <a href="Controller?command=movies&person=${requestScope.person.id}&rel=6" class="btn btn-success">${localeAddMovieToPerson}</a>
                </c:if>
                <c:forEach items="${requestScope.person.moviesAsPainter}" var="movie">
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
                        <c:if test='${sessionScope.userStatus eq "admin"}'>
                            <a href="#" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#mpr6-remove-modal" data-id="${movie.id}">${localeDelete}</a>
                        </c:if>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test='${requestScope.person.moviesAsOperator != null || sessionScope.userStatus eq "admin"}'>
                <h3>${localeAsOperator}</h3>
                <c:if test='${sessionScope.userStatus eq "admin"}'>
                    <a href="Controller?command=movies&person=${requestScope.person.id}&rel=5" class="btn btn-success">${localeAddMovieToPerson}</a>
                </c:if>
                <c:forEach items="${requestScope.person.moviesAsOperator}" var="movie">
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
                        <c:if test='${sessionScope.userStatus eq "admin"}'>
                            <a href="#" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#mpr5-remove-modal" data-id="${movie.id}">${localeDelete}</a>
                        </c:if>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test='${requestScope.person.moviesAsEditor != null || sessionScope.userStatus eq "admin"}'>
                <h3>${localeAsEditor}</h3>
                <c:if test='${sessionScope.userStatus eq "admin"}'>
                    <a href="Controller?command=movies&person=${requestScope.person.id}&rel=7" class="btn btn-success">${localeAddMovieToPerson}</a>
                </c:if>
                <c:forEach items="${requestScope.person.moviesAsEditor}" var="movie">
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
                        <c:if test='${sessionScope.userStatus eq "admin"}'>
                            <a href="#" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#mpr7-remove-modal" data-id="${movie.id}">${localeDelete}</a>
                        </c:if>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test='${requestScope.person.moviesAsComposer != null || sessionScope.userStatus eq "admin"}'>
                <h3>${localeAsComposer}</h3>
                <c:if test='${sessionScope.userStatus eq "admin"}'>
                    <a href="Controller?command=movies&person=${requestScope.person.id}&rel=8" class="btn btn-success">${localeAddMovieToPerson}</a>
                </c:if>
                <c:forEach items="${requestScope.person.moviesAsComposer}" var="movie">
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
                        <c:if test='${sessionScope.userStatus eq "admin"}'>
                            <a href="#" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#mpr8-remove-modal" data-id="${movie.id}">${localeDelete}</a>
                        </c:if>
                    </div>
                </c:forEach>
            </c:if>
        </div>
        <div id="person-remove-modal" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">${localeDeleteTitle}</h4>
                    </div>
                    <div class="modal-body">
                        <p>${localeDeleteBody}</p>
                    </div>
                    <div class="modal-footer">
                        <form action="Controller?command=delete-person" method="post">
                            <input type="hidden" name="id">
                            <button type="submit" class="btn btn-danger">${localeDelete}</button>
                        </form>
                        <button type="button" class="btn btn-default" data-dismiss="modal">${localeCancel}</button>
                    </div>
                </div>
            </div>
        </div>
        <div id="mpr1-remove-modal" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">${localeDeleteTitle}</h4>
                    </div>
                    <div class="modal-body">
                        <p>${localeDeleteBody}</p>
                    </div>
                    <div class="modal-footer">
                        <form action="Controller?command=delete-mpr" method="post">
                            <input type="hidden" name="id">
                            <input type="hidden" name="personId" value="${requestScope.person.id}">
                            <input type="hidden" name="relationType" value="1">
                            <button type="submit" class="btn btn-danger">${localeDelete}</button>
                        </form>
                        <button type="button" class="btn btn-default" data-dismiss="modal">${localeCancel}</button>
                    </div>
                </div>
            </div>
        </div>
        <div id="mpr2-remove-modal" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">${localeDeleteTitle}</h4>
                    </div>
                    <div class="modal-body">
                        <p>${localeDeleteBody}</p>
                    </div>
                    <div class="modal-footer">
                        <form action="Controller?command=delete-mpr" method="post">
                            <input type="hidden" name="id">
                            <input type="hidden" name="personId" value="${requestScope.person.id}">
                            <input type="hidden" name="relationType" value="2">
                            <button type="submit" class="btn btn-danger">${localeDelete}</button>
                        </form>
                        <button type="button" class="btn btn-default" data-dismiss="modal">${localeCancel}</button>
                    </div>
                </div>
            </div>
        </div>
        <div id="mpr3-remove-modal" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">${localeDeleteTitle}</h4>
                    </div>
                    <div class="modal-body">
                        <p>${localeDeleteBody}</p>
                    </div>
                    <div class="modal-footer">
                        <form action="Controller?command=delete-mpr" method="post">
                            <input type="hidden" name="id">
                            <input type="hidden" name="personId" value="${requestScope.person.id}">
                            <input type="hidden" name="relationType" value="3">
                            <button type="submit" class="btn btn-danger">${localeDelete}</button>
                        </form>
                        <button type="button" class="btn btn-default" data-dismiss="modal">${localeCancel}</button>
                    </div>
                </div>
            </div>
        </div>
        <div id="mpr4-remove-modal" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">${localeDeleteTitle}</h4>
                    </div>
                    <div class="modal-body">
                        <p>${localeDeleteBody}</p>
                    </div>
                    <div class="modal-footer">
                        <form action="Controller?command=delete-mpr" method="post">
                            <input type="hidden" name="id">
                            <input type="hidden" name="personId" value="${requestScope.person.id}">
                            <input type="hidden" name="relationType" value="4">
                            <button type="submit" class="btn btn-danger">${localeDelete}</button>
                        </form>
                        <button type="button" class="btn btn-default" data-dismiss="modal">${localeCancel}</button>
                    </div>
                </div>
            </div>
        </div>
        <div id="mpr5-remove-modal" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">${localeDeleteTitle}</h4>
                    </div>
                    <div class="modal-body">
                        <p>${localeDeleteBody}</p>
                    </div>
                    <div class="modal-footer">
                        <form action="Controller?command=delete-mpr" method="post">
                            <input type="hidden" name="id">
                            <input type="hidden" name="personId" value="${requestScope.person.id}">
                            <input type="hidden" name="relationType" value="5">
                            <button type="submit" class="btn btn-danger">${localeDelete}</button>
                        </form>
                        <button type="button" class="btn btn-default" data-dismiss="modal">${localeCancel}</button>
                    </div>
                </div>
            </div>
        </div>
        <div id="mpr6-remove-modal" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">${localeDeleteTitle}</h4>
                    </div>
                    <div class="modal-body">
                        <p>${localeDeleteBody}</p>
                    </div>
                    <div class="modal-footer">
                        <form action="Controller?command=delete-mpr" method="post">
                            <input type="hidden" name="id">
                            <input type="hidden" name="personId" value="${requestScope.person.id}">
                            <input type="hidden" name="relationType" value="6">
                            <button type="submit" class="btn btn-danger">${localeDelete}</button>
                        </form>
                        <button type="button" class="btn btn-default" data-dismiss="modal">${localeCancel}</button>
                    </div>
                </div>
            </div>
        </div>
        <div id="mpr7-remove-modal" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">${localeDeleteTitle}</h4>
                    </div>
                    <div class="modal-body">
                        <p>${localeDeleteBody}</p>
                    </div>
                    <div class="modal-footer">
                        <form action="Controller?command=delete-mpr" method="post">
                            <input type="hidden" name="id">
                            <input type="hidden" name="personId" value="${requestScope.person.id}">
                            <input type="hidden" name="relationType" value="7">
                            <button type="submit" class="btn btn-danger">${localeDelete}</button>
                        </form>
                        <button type="button" class="btn btn-default" data-dismiss="modal">${localeCancel}</button>
                    </div>
                </div>
            </div>
        </div>
        <div id="mpr8-remove-modal" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">${localeDeleteTitle}</h4>
                    </div>
                    <div class="modal-body">
                        <p>${localeDeleteBody}</p>
                    </div>
                    <div class="modal-footer">
                        <form action="Controller?command=delete-mpr" method="post">
                            <input type="hidden" name="id">
                            <input type="hidden" name="personId" value="${requestScope.person.id}">
                            <input type="hidden" name="relationType" value="8">
                            <button type="submit" class="btn btn-danger">${localeDelete}</button>
                        </form>
                        <button type="button" class="btn btn-default" data-dismiss="modal">${localeCancel}</button>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
</main>
<footer class="container-fluid">
    <p class="text-center">EPAM Training Center, Java 5 2016, Kostevich Vladislav</p>
</footer>
<script src="js/remove.js"></script>
</body>
</html>
