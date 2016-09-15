<%--
  Created by IntelliJ IDEA.
  User: Владислав
  Date: 14.07.2016
  Time: 18:37
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
<f:message bundle="${locale}" key="locale.edit" var="localeEdit"/>
<f:message bundle="${locale}" key="locale.delete" var="localeDelete"/>
<f:message bundle="${locale}" key="locale.comments" var="localeComments"/>
<f:message bundle="${locale}" key="locale.toMovie" var="localeToMovie"/>
<f:message bundle="${locale}" key="locale.deleteTitle" var="localeDeleteTitle"/>
<f:message bundle="${locale}" key="locale.deleteBody" var="localeDeleteBody"/>
<f:message bundle="${locale}" key="locale.cancel" var="localeCancel"/>
<f:message bundle="${locale}" key="locale.email" var="localeEmail"/>
<f:message bundle="${locale}" key="locale.rating" var="localeRating"/>
<f:message bundle="${locale}" key="locale.noUser" var="localeNoUser"/>
<f:message bundle="${locale}" key="locale.dateOfRegistration" var="localeDateOfRegistration"/>
<f:message bundle="${locale}" key="locale.status" var="localeStatus"/>
<f:message bundle="${locale}" key="locale.statusNormal" var="localeStatusNormal"/>
<f:message bundle="${locale}" key="locale.statusBanned" var="localeStatusBanned"/>
<f:message bundle="${locale}" key="locale.statusAdmin" var="localeStatusAdmin"/>
<f:message bundle="${locale}" key="locale.language" var="localeLanguage"/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${requestScope.user.firstName} ${requestScope.user.lastName}</title>
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
            <h3>${requestScope.user.firstName} ${requestScope.user.lastName}</h3>
            <div class="clearfix">
                <img src="${requestScope.user.photo}" class="img-circle" alt="${requestScope.user.firstName} ${requestScope.user.lastName}">
                <ul>
                    <c:if test='${sessionScope.userId == requestScope.user.id || sessionScope.userStatus eq "admin"}'>
                        <li>${localeEmail}: ${requestScope.user.email}</li>
                    </c:if>
                    <li>${localeDateOfRegistration}: <f:formatDate value="${requestScope.user.dateOfRegistry}" type="date" dateStyle="long"/></li>
                    <li>${localeRating}: ${requestScope.user.rating}</li>
                    <li>${localeStatus}: <c:if test='${requestScope.user.status eq "normal"}'>${localeStatusNormal}</c:if><c:if test='${requestScope.user.status eq "banned"}'>${localeStatusBanned}</c:if><c:if test='${requestScope.user.status eq "admin"}'>${localeStatusAdmin}</c:if></li>
                    <c:if test='${sessionScope.userId == requestScope.user.id || sessionScope.userStatus eq "admin"}'>
                        <li>${localeLanguage}: ${requestScope.user.languageId}</li>
                    </c:if>
                </ul>
            </div>
            <c:if test='${sessionScope.userId == requestScope.user.id || sessionScope.userStatus eq "admin"}'>
                <a href="Controller?command=edit-user&id=${requestScope.user.id}" class="btn btn-success">${localeEdit}</a>
            </c:if>
            <c:if test='${sessionScope.userId == requestScope.user.id}'>
                <a href="#" class="btn btn-danger" data-toggle="modal" data-target="#user-remove-modal" data-id="${requestScope.user.id}">${localeDelete}</a>
            </c:if>
            <c:if test="${requestScope.user.comments != null}">
                <h3>${localeComments}</h3>
                <c:forEach items="${requestScope.user.comments}" var="comment">
                    <div class="well clearfix">
                        <a href="Controller?command=user&id=${requestScope.user.id}">
                            <img src="${requestScope.user.photo}" class="img-circle" alt="${requestScope.user.firstName} ${requestScope.user.lastName}">
                        </a>
                        <h3>${comment.title}</h3>
                        <p><f:formatDate value="${comment.dateOfPublication}" type="date" dateStyle="long"/> ${localeToMovie} <a href="Controller?command=movie&id=${comment.movie.id}">${comment.movie.name}</a></p>
                        <p>${comment.content}</p>
                        <c:if test='${sessionScope.userId == requestScope.user.id}'>
                            <a href="Controller?command=edit-comment&id=${comment.id}" class="btn btn-success btn-sm">${localeEdit}</a>
                            <a href="#" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#comment-remove-modal" data-id="${comment.id}">${localeDelete}</a>
                        </c:if>
                    </div>
                </c:forEach>
            </c:if>
        </div>
        <div id="user-remove-modal" class="modal fade" role="dialog">
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
                        <form action="Controller?command=delete-user" method="post">
                            <input type="hidden" name="id">
                            <button type="submit" class="btn btn-danger">${localeDelete}</button>
                        </form>
                        <button type="button" class="btn btn-default" data-dismiss="modal">${localeCancel}</button>
                    </div>
                </div>
            </div>
        </div>
        <div id="comment-remove-modal" class="modal fade" role="dialog">
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
                        <form action="Controller?command=delete-comment" method="post">
                            <input type="hidden" name="id">
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
