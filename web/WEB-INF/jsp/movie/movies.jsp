<%--
  Created by IntelliJ IDEA.
  User: Владислав
  Date: 14.07.2016
  Time: 18:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${requestScope.catalogPageName}</title>
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
                <a class="navbar-brand" href="Controller?command=welcome">${requestScope.siteName}</a>
            </div>
            <div class="collapse navbar-collapse" id="myNavbar">
                <ul class="nav navbar-nav">
                    <li><a href="Controller?command=welcome">${requestScope.mainPageName}</a></li>
                    <li class="active"><a href="Controller?command=movies">${requestScope.catalogPageName}</a></li>
                    <c:if test="${sessionScope.userId != null}">
                        <c:if test='${sessionScope.userStatus eq "admin"}'>
                            <li><a href="#">${requestScope.personsPageName}</a></li>
                            <li><a href="#">${requestScope.usersPageName}</a></li>
                            <li class="dropdown">
                                <a class="dropdown-toggle" data-toggle="dropdown" href="#">${requestScope.localeOther} <span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li><a href="#">${requestScope.genresPageName}</a></li>
                                    <li><a href="#">${requestScope.countriesPageName}</a></li>
                                </ul>
                            </li>
                        </c:if>
                    </c:if>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <c:if test="${sessionScope.userId != null}">
                        <li><a href="#"><span class="glyphicon glyphicon-user"></span> ${requestScope.profilePageName}</a></li>
                        <li><a href="Controller?command=logout"><span class="glyphicon glyphicon-log-out"></span> ${requestScope.logoutName}</a></li>
                    </c:if>
                    <c:if test="${sessionScope.userId == null}">
                        <li><a href="Controller?command=registration"><span class="glyphicon glyphicon-user"></span> ${requestScope.registrationPageName}</a></li>
                        <li><a href="Controller?command=login"><span class="glyphicon glyphicon-log-in"></span> ${requestScope.loginPageName}</a></li>
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
                ${requestScope.localeServiceError}
        </div>
    </c:if>
    <div class="jumbotron">
        <h3>${requestScope.localeSearchCriteria}</h3>
        <form role="form">
            <div class="row">
                <div class="form-group col-sm-6">
                    <label for="name">${requestScope.localeName}:</label>
                    <input type="text" id="name" class="form-control">
                </div>
                <div class="form-group col-sm-6 row">
                    <div class="col-xs-12">
                        <label>${requestScope.localeYear}</label>
                    </div>
                    <div class="col-xs-6">
                        <label for="min-year">${requestScope.localeFrom}:</label>
                        <input type="number" id="min-year" class="form-control">
                    </div>
                    <div class="col-xs-6">
                        <label for="max-year">${requestScope.localeTo}:</label>
                        <input type="number" id="max-year" class="form-control">
                    </div>
                </div>
                <div class="form-group col-sm-6">
                    <label>${requestScope.localeGenre}</label>
                    <c:if test="${requestScope.genres != null}">
                        <c:forEach items="${requestScope.genres}" var="genre">
                            <div class="checkbox">
                                <label><input type="checkbox"> ${genre.name}</label>
                            </div>
                        </c:forEach>
                    </c:if>
                </div>
                <div class="form-group col-sm-6">
                    <label>${requestScope.localeCountry}</label>
                    <c:if test="${requestScope.countries != null}">
                        <c:forEach items="${requestScope.countries}" var="country">
                            <div class="checkbox">
                                <label><input type="checkbox"> ${country.name}</label>
                            </div>
                        </c:forEach>
                    </c:if>
                </div>
                <div class="form-group col-sm-6 row">
                    <div class="col-xs-12">
                        <label>${requestScope.localeRating}</label>
                    </div>
                    <div class="col-xs-6">
                        <label for="min-rating">${requestScope.localeFrom}:</label>
                        <input type="number" id="min-rating" class="form-control">
                    </div>
                    <div class="col-xs-6">
                        <label for="max-rating">${requestScope.localeTo}:</label>
                        <input type="number" id="max-rating" class="form-control">
                    </div>
                </div>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-default">${requestScope.localeFindButton}</button>
            </div>
        </form>
    </div>
    <form action="forms/movie-form.html" method="post" role="form">
        <div class="form-group">
            <button type="submit" class="btn btn-success btn-lg">Добавить новый фильм</button>
        </div>
    </form>
    <div class="alert alert-info fade in">
        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
        По выбранным критериям не найдено ни одного фильма.
    </div>
    <p>Отображается 10 из 78</p>
    <c:if test="${requestScope.movies != null}">
        <c:forEach items="${requestScope.movies}" var="movie">
            <div class="well clearfix">
                <a href="#">
                    <img src="img/${movie.image}" class="img-rounded" alt="${movie.name}">
                </a>
                <a href="#"><h3>${movie.name}</h3></a>
                <ul>
                    <li>${requestScope.localeCountry}: <c:forEach items="${movie.countries}" var="country"><a href="#">${country.name}</a> </c:forEach></li>
                    <li>${requestScope.localeGenre}: <c:forEach items="${movie.genres}" var="genre"><a href="#">${genre.name}</a> </c:forEach></li>
                    <li>${requestScope.localeDirector}: <c:forEach items="${movie.directors}" var="director"><a href="#">${director.name}</a> </c:forEach></li>
                    <li>${requestScope.localeYear}: ${movie.year}</li>
                    <li>${requestScope.localeBudget}: ${movie.budget} $</li>
                    <li>${requestScope.localePremiere}: ${movie.premiere}</li>
                    <li>${requestScope.localeLasting}: ${movie.lasting} ${requestScope.localeMinute}</li>
                    <li>${requestScope.localeRating}: ${movie.averageRating}</li>
                </ul>
                <p>${movie.annotation}</p>
            </div>
        </c:forEach>
    </c:if>
    <ul class="pagination">
        <li><a href="#">Пред</a></li>
        <li class="active"><a href="#">1</a></li>
        <li><a href="#">2</a></li>
        <li><a href="#">3</a></li>
        <li><a href="#">4</a></li>
        <li><a href="#">5</a></li>
        <li><a href="#">6</a></li>
        <li><a href="#">7</a></li>
        <li><a href="#">8</a></li>
        <li><a href="#">След.</a></li>
    </ul>
</main>
<footer class="container-fluid">
    <p class="text-center">EPAM Training Center, Java 5 2016, Kostevich Vladislav</p>
</footer>
</body>
</html>
