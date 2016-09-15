<%--
  Created by IntelliJ IDEA.
  User: Владислав
  Date: 14.07.2016
  Time: 18:18
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
<f:message bundle="${locale}" key="locale.noMovie" var="localeNoMovie"/>
<f:message bundle="${locale}" key="locale.actors" var="localeActors"/>
<f:message bundle="${locale}" key="locale.directors" var="localeDirectors"/>
<f:message bundle="${locale}" key="locale.producers" var="localeProducers"/>
<f:message bundle="${locale}" key="locale.writers" var="localeWriters"/>
<f:message bundle="${locale}" key="locale.editors" var="localeEditors"/>
<f:message bundle="${locale}" key="locale.painters" var="localePainters"/>
<f:message bundle="${locale}" key="locale.operators" var="localeOperators"/>
<f:message bundle="${locale}" key="locale.composers" var="localeComposers"/>
<f:message bundle="${locale}" key="locale.tagline" var="localeTagline"/>
<f:message bundle="${locale}" key="locale.edit" var="localeEdit"/>
<f:message bundle="${locale}" key="locale.delete" var="localeDelete"/>
<f:message bundle="${locale}" key="locale.addActor" var="localeAddActor"/>
<f:message bundle="${locale}" key="locale.addDirector" var="localeAddDirector"/>
<f:message bundle="${locale}" key="locale.addProducer" var="localeAddProducer"/>
<f:message bundle="${locale}" key="locale.addWriter" var="localeAddWriter"/>
<f:message bundle="${locale}" key="locale.addPainter" var="localeAddPainter"/>
<f:message bundle="${locale}" key="locale.addOperator" var="localeAddOperator"/>
<f:message bundle="${locale}" key="locale.addEditor" var="localeAddEditor"/>
<f:message bundle="${locale}" key="locale.addComposer" var="localeAddComposer"/>
<f:message bundle="${locale}" key="locale.addCountry" var="localeAddCountry"/>
<f:message bundle="${locale}" key="locale.addGenre" var="localeAddGenre"/>
<f:message bundle="${locale}" key="locale.writeComment" var="localeWriteComment"/>
<f:message bundle="${locale}" key="locale.title" var="localeTitle"/>
<f:message bundle="${locale}" key="locale.enterTitle" var="localeEnterTitle"/>
<f:message bundle="${locale}" key="locale.content" var="localeContent"/>
<f:message bundle="${locale}" key="locale.enterContent" var="localeEnterContent"/>
<f:message bundle="${locale}" key="locale.commentButton" var="localeCommentButton"/>
<f:message bundle="${locale}" key="locale.comments" var="localeComments"/>
<f:message bundle="${locale}" key="locale.deleteTitle" var="localeDeleteTitle"/>
<f:message bundle="${locale}" key="locale.deleteBody" var="localeDeleteBody"/>
<f:message bundle="${locale}" key="locale.cancel" var="localeCancel"/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${requestScope.movie.name}</title>
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
    <c:if test="${requestScope.movie == null}">
        <div class="alert alert-info fade in">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            ${localeNoMovie}
        </div>
    </c:if>
    <c:if test="${requestScope.movie != null}">
        <div class="jumbotron">
            <h3>${requestScope.movie.name}</h3>
            <div class="clearfix">
                <img src="${requestScope.movie.image}" class="img-rounded" alt="${requestScope.movie.name}">
                <ul>
                    <c:if test='${requestScope.movie.actors != null || sessionScope.userStatus eq "admin"}'><li>${localeActors}:<c:forEach items="${requestScope.movie.actors}" var="actor"> <span class="comma"><a href="Controller?command=person&id=${actor.id}">${actor.name}</a><c:if test='${sessionScope.userStatus eq "admin"}'> <a href="#" data-toggle="modal" data-target="#mpr1-remove-modal" data-id="${actor.id}"><span class="glyphicon glyphicon-remove"></span></a></c:if></span></c:forEach><c:if test='${sessionScope.userStatus eq "admin"}'> <a href="Controller?command=persons&movie=${requestScope.movie.id}&rel=1" class="btn btn-success btn-xs">${localeAddActor}</a></c:if></li></c:if>
                    <c:if test='${requestScope.movie.directors != null || sessionScope.userStatus eq "admin"}'><li>${localeDirectors}:<c:forEach items="${requestScope.movie.directors}" var="director"> <span class="comma"><a href="Controller?command=person&id=${director.id}">${director.name}</a><c:if test='${sessionScope.userStatus eq "admin"}'> <a href="#" data-toggle="modal" data-target="#mpr2-remove-modal" data-id="${director.id}"><span class="glyphicon glyphicon-remove"></span></a></c:if></span></c:forEach><c:if test='${sessionScope.userStatus eq "admin"}'> <a href="Controller?command=persons&movie=${requestScope.movie.id}&rel=2" class="btn btn-success btn-xs">${localeAddDirector}</a></c:if></li></c:if>
                    <c:if test='${requestScope.movie.producers != null || sessionScope.userStatus eq "admin"}'><li>${localeProducers}:<c:forEach items="${requestScope.movie.producers}" var="producer"> <span class="comma"><a href="Controller?command=person&id=${producer.id}">${producer.name}</a><c:if test='${sessionScope.userStatus eq "admin"}'> <a href="#" data-toggle="modal" data-target="#mpr3-remove-modal" data-id="${producer.id}"><span class="glyphicon glyphicon-remove"></span></a></c:if></span></c:forEach><c:if test='${sessionScope.userStatus eq "admin"}'> <a href="Controller?command=persons&movie=${requestScope.movie.id}&rel=3" class="btn btn-success btn-xs">${localeAddProducer}</a></c:if></li></c:if>
                    <c:if test='${requestScope.movie.writers != null || sessionScope.userStatus eq "admin"}'><li>${localeWriters}:<c:forEach items="${requestScope.movie.writers}" var="writer"> <span class="comma"><a href="Controller?command=person&id=${writer.id}">${writer.name}</a><c:if test='${sessionScope.userStatus eq "admin"}'> <a href="#" data-toggle="modal" data-target="#mpr4-remove-modal" data-id="${writer.id}"><span class="glyphicon glyphicon-remove"></span></a></c:if></span></c:forEach><c:if test='${sessionScope.userStatus eq "admin"}'> <a href="Controller?command=persons&movie=${requestScope.movie.id}&rel=4" class="btn btn-success btn-xs">${localeAddWriter}</a></c:if></li></c:if>
                    <c:if test='${requestScope.movie.painters != null || sessionScope.userStatus eq "admin"}'><li>${localePainters}:<c:forEach items="${requestScope.movie.painters}" var="painter"> <span class="comma"><a href="Controller?command=person&id=${painter.id}">${painter.name}</a><c:if test='${sessionScope.userStatus eq "admin"}'> <a href="#" data-toggle="modal" data-target="#mpr6-remove-modal" data-id="${painter.id}"><span class="glyphicon glyphicon-remove"></span></a></c:if></span></c:forEach><c:if test='${sessionScope.userStatus eq "admin"}'> <a href="Controller?command=persons&movie=${requestScope.movie.id}&rel=6" class="btn btn-success btn-xs">${localeAddPainter}</a></c:if></li></c:if>
                    <c:if test='${requestScope.movie.operators != null || sessionScope.userStatus eq "admin"}'><li>${localeOperators}:<c:forEach items="${requestScope.movie.operators}" var="operator"> <span class="comma"><a href="Controller?command=person&id=${operator.id}">${operator.name}</a><c:if test='${sessionScope.userStatus eq "admin"}'> <a href="#" data-toggle="modal" data-target="#mpr5-remove-modal" data-id="${operator.id}"><span class="glyphicon glyphicon-remove"></span></a></c:if></span></c:forEach><c:if test='${sessionScope.userStatus eq "admin"}'> <a href="Controller?command=persons&movie=${requestScope.movie.id}&rel=5" class="btn btn-success btn-xs">${localeAddOperator}</a></c:if></li></c:if>
                    <c:if test='${requestScope.movie.editors != null || sessionScope.userStatus eq "admin"}'><li>${localeEditors}:<c:forEach items="${requestScope.movie.editors}" var="editor"> <span class="comma"><a href="Controller?command=person&id=${editor.id}">${editor.name}</a><c:if test='${sessionScope.userStatus eq "admin"}'> <a href="#" data-toggle="modal" data-target="#mpr7-remove-modal" data-id="${editor.id}"><span class="glyphicon glyphicon-remove"></span></a></c:if></span></c:forEach><c:if test='${sessionScope.userStatus eq "admin"}'> <a href="Controller?command=persons&movie=${requestScope.movie.id}&rel=7" class="btn btn-success btn-xs">${localeAddEditor}</a></c:if></li></c:if>
                    <c:if test='${requestScope.movie.composers != null || sessionScope.userStatus eq "admin"}'><li>${localeComposers}:<c:forEach items="${requestScope.movie.composers}" var="composers"> <span class="comma"><a href="Controller?command=person&id=${composers.id}">${composers.name}</a><c:if test='${sessionScope.userStatus eq "admin"}'> <a href="#" data-toggle="modal" data-target="#mpr8-remove-modal" data-id="${composers.id}"><span class="glyphicon glyphicon-remove"></span></a></c:if></span></c:forEach><c:if test='${sessionScope.userStatus eq "admin"}'> <a href="Controller?command=persons&movie=${requestScope.movie.id}&rel=8" class="btn btn-success btn-xs">${localeAddComposer}</a></c:if></li></c:if>
                    <li>${localeCountry}:<c:forEach items="${requestScope.movie.countries}" var="country"> <span class="comma"><a href="Controller?command=movies&searchFormCountries[]=${country.id}">${country.name}</a><c:if test='${sessionScope.userStatus eq "admin"}'> <a href="#" data-toggle="modal" data-target="#mc-remove-modal" data-id="${country.id}"><span class="glyphicon glyphicon-remove"></span></a></c:if></span></c:forEach><c:if test='${sessionScope.userStatus eq "admin"}'> <a href="Controller?command=countries&movie=${requestScope.movie.id}" class="btn btn-success btn-xs">${localeAddCountry}</a></c:if></li>
                    <li>${localeGenre}:<c:forEach items="${requestScope.movie.genres}" var="genre"> <span class="comma"><a href="Controller?command=movies&searchFormGenres[]=${genre.id}">${genre.name}</a><c:if test='${sessionScope.userStatus eq "admin"}'> <a href="#" data-toggle="modal" data-target="#mg-remove-modal" data-id="${genre.id}"><span class="glyphicon glyphicon-remove"></span></a></c:if></span></c:forEach><c:if test='${sessionScope.userStatus eq "admin"}'> <a href="Controller?command=genres&movie=${requestScope.movie.id}" class="btn btn-success btn-xs">${localeAddGenre}</a></c:if></li>
                    <li>${localeYear}: ${requestScope.movie.year}</li>
                    <li>${localeTagline}: ${requestScope.movie.tagline}</li>
                    <li>${localeBudget}: <f:formatNumber value="${requestScope.movie.budget}"/> $</li>
                    <li>${localePremiere}: <f:formatDate value="${requestScope.movie.premiere}" type="date" dateStyle="long"/></li>
                    <li>${localeLasting}: ${requestScope.movie.lasting} ${localeMinute}</li>
                </ul>
            </div>
            <p>${requestScope.movie.annotation}</p>
            <c:if test='${sessionScope.userId != null && sessionScope.userStatus ne "banned"}'>
                <div id="star-rating" data-movie-id="${requestScope.movie.id}" data-url="Controller?command=add-rating" <c:if test="${requestScope.ratingValue != null}">data-value="${requestScope.ratingValue}"</c:if>></div>
            </c:if>
            <p>${localeRating}: <f:formatNumber value="${requestScope.movie.averageRating}" maxFractionDigits="2"/></p>
            <c:if test='${sessionScope.userStatus eq "admin"}'>
                <a href="Controller?command=edit-movie&id=${requestScope.movie.id}" class="btn btn-success">${localeEdit}</a>
                <a class="btn btn-danger" data-toggle="modal" data-target="#movie-remove-modal" data-id="${requestScope.movie.id}">${localeDelete}</a>
            </c:if>
        </div>
        <c:if test='${sessionScope.userId != null && sessionScope.userStatus ne "banned"}'>
            <h3>${localeWriteComment}</h3>
            <form action="Controller?command=add-comment" method="post" role="form">
                <input type="hidden" name="commentFormMovieId" value="${requestScope.movie.id}">
                <div class="form-group">
                    <label for="title">${localeTitle}</label>
                    <input name="commentFormTitle" type="text" minlength="1" maxlength="45" class="form-control" id="title" placeholder="${localeEnterTitle}">
                </div>
                <div class="form-group">
                    <label for="content">${localeContent}</label>
                    <textarea name="commentFormContent" minlength="1" id="content" class="form-control" rows="5" placeholder="${localeEnterContent}"></textarea>
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-default">${localeCommentButton}</button>
                </div>
            </form>
        </c:if>
        <c:if test="${requestScope.movie.comments != null}">
            <h3>${localeComments}</h3>
            <c:forEach items="${requestScope.movie.comments}" var="comment">
                <div class="well clearfix">
                    <a href="Controller?command=user&id=${comment.user.id}">
                        <img src="${comment.user.photo}" class="img-circle" alt="${comment.user.firstName} ${comment.user.lastName}">
                    </a>
                    <h3>${comment.title}</h3>
                    <p><a href="Controller?command=user&id=${comment.user.id}">${comment.user.firstName} ${comment.user.lastName}</a> <f:formatDate value="${comment.dateOfPublication}" type="date" dateStyle="long"/></p>
                    <p>${comment.content}</p>
                </div>
            </c:forEach>
        </c:if>
        <div id="movie-remove-modal" class="modal fade" role="dialog">
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
                        <form action="Controller?command=delete-movie" method="post">
                            <input type="hidden" name="id">
                            <button type="submit" class="btn btn-danger">${localeDelete}</button>
                        </form>
                        <button type="button" class="btn btn-default" data-dismiss="modal">${localeCancel}</button>
                    </div>
                </div>
            </div>
        </div>
        <div id="mg-remove-modal" class="modal fade" role="dialog">
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
                        <form action="Controller?command=delete-mg" method="post">
                            <input type="hidden" name="id">
                            <input type="hidden" name="movieId" value="${requestScope.movie.id}">
                            <button type="submit" class="btn btn-danger">${localeDelete}</button>
                        </form>
                        <button type="button" class="btn btn-default" data-dismiss="modal">${localeCancel}</button>
                    </div>
                </div>
            </div>
        </div>
        <div id="mc-remove-modal" class="modal fade" role="dialog">
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
                        <form action="Controller?command=delete-mc" method="post">
                            <input type="hidden" name="id">
                            <input type="hidden" name="movieId" value="${requestScope.movie.id}">
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
                            <input type="hidden" name="movieId" value="${requestScope.movie.id}">
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
                            <input type="hidden" name="movieId" value="${requestScope.movie.id}">
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
                            <input type="hidden" name="movieId" value="${requestScope.movie.id}">
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
                            <input type="hidden" name="movieId" value="${requestScope.movie.id}">
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
                            <input type="hidden" name="movieId" value="${requestScope.movie.id}">
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
                            <input type="hidden" name="movieId" value="${requestScope.movie.id}">
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
                            <input type="hidden" name="movieId" value="${requestScope.movie.id}">
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
                            <input type="hidden" name="movieId" value="${requestScope.movie.id}">
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
<script src="js/script.js"></script>
</body>
</html>
