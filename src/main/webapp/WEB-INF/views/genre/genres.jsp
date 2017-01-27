<%--
  Created by IntelliJ IDEA.
  User: Владислав
  Date: 14.07.2016
  Time: 18:44
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
<f:message bundle="${locale}" key="locale.displaying" var="localeDisplaying"/>
<f:message bundle="${locale}" key="locale.of" var="localeOf"/>
<f:message bundle="${locale}" key="locale.addNewGenre" var="localeAddNewGenre"/>
<f:message bundle="${locale}" key="locale.edit" var="localeEdit"/>
<f:message bundle="${locale}" key="locale.delete" var="localeDelete"/>
<f:message bundle="${locale}" key="locale.deleteTitle" var="localeDeleteTitle"/>
<f:message bundle="${locale}" key="locale.deleteBody" var="localeDeleteBody"/>
<f:message bundle="${locale}" key="locale.cancel" var="localeCancel"/>
<f:message bundle="${locale}" key="locale.add" var="localeAdd"/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${genresPageName}</title>
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
                            <li class="dropdown active">
                                <a class="dropdown-toggle" data-toggle="dropdown" href="#">${localeOther} <span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li class="active"><a href="Controller?command=genres">${genresPageName}</a></li>
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
    <form action="Controller?command=genres" method="post" id="search-form">
        <input name="page" value="1" type="hidden">
        <input name="movie" value="${requestScope.movieId}" type="hidden">
    </form>
    <c:if test="${requestScope.movieId == null}">
        <a href="Controller?command=add-genre" class="btn btn-success btn-lg">${localeAddNewGenre}</a>
    </c:if>
    <p>${localeDisplaying} ${requestScope.genresFrom}-${requestScope.genresTo} ${localeOf} ${requestScope.genresCount}</p>
    <c:if test="${requestScope.genres != null}">
        <c:forEach items="${requestScope.genres}" var="genre">
            <div class="well clearfix">
                <h3>${genre.name}</h3>
                <c:if test="${requestScope.movieId == null}">
                    <a href="Controller?command=edit-genre&id=${genre.id}" class="btn btn-success btn-sm">${localeEdit}</a>
                    <a href="#" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#genre-remove-modal" data-id="${genre.id}">${localeDelete}</a>
                </c:if>
                <c:if test="${requestScope.movieId != null}">
                    <form action="Controller?command=add-mg" method="post" role="form">
                        <input name="movieId" value="${requestScope.movieId}" type="hidden">
                        <input name="genreId" value="${genre.id}" type="hidden">
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
    <div id="genre-remove-modal" class="modal fade" role="dialog">
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
                    <form action="Controller?command=delete-genre" method="post">
                        <input type="hidden" name="id">
                        <button type="submit" class="btn btn-danger">${localeDelete}</button>
                    </form>
                    <button type="button" class="btn btn-default" data-dismiss="modal">${localeCancel}</button>
                </div>
            </div>
        </div>
    </div>
</main>
<footer class="container-fluid">
    <p class="text-center">EPAM Training Center, Java 5 2016, Kostevich Vladislav</p>
</footer>
<script src="js/pagination.js"></script>
<script src="js/remove.js"></script>
</body>
</html>
