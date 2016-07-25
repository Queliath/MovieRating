<%--
  Created by IntelliJ IDEA.
  User: Владислав
  Date: 14.07.2016
  Time: 18:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <a class="navbar-brand" href="Controller?command=welcome">${requestScope.siteName}</a>
            </div>
            <div class="collapse navbar-collapse" id="myNavbar">
                <ul class="nav navbar-nav">
                    <li><a href="Controller?command=welcome">${requestScope.mainPageName}</a></li>
                    <li><a href="Controller?command=movies">${requestScope.catalogPageName}</a></li>
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
    <c:if test="${requestScope.person == null}">
        <div class="alert alert-info fade in">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                ${requestScope.localeNoPerson}
        </div>
    </c:if>
    <c:if test="${requestScope.person != null}">
        <div class="jumbotron">
            <h3>${requestScope.person.name}</h3>
            <div class="clearfix">
                <img src="img/${requestScope.person.photo}" class="img-rounded" alt="${requestScope.person.name}">
                <ul>
                    <li>${requestScope.localeFilmsTotal}: ${requestScope.person.moviesTotal}</li>
                    <li>${requestScope.localeDateOfBirth}: ${requestScope.person.dateOfBirth}</li>
                    <li>${requestScope.localePlaceOfBirth}: ${requestScope.person.placeOfBirth}</li>
                </ul>
            </div>
            <c:if test='${sessionScope.userStatus eq "admin"}'>
                <form action="#" method="post" role="form">
                    <div class="form-group">
                        <button type="submit" class="btn btn-success">${requestScope.localeEdit}</button>
                    </div>
                </form>
                <form action="#" method="post" role="form">
                    <div class="form-group">
                        <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#remove-modal">${requestScope.localeDelete}</button>
                    </div>
                </form>
            </c:if>
            <c:if test='${requestScope.person.moviesAsActor != null || sessionScope.userStatus eq "admin"}'>
                <h3>${requestScope.localeAsActor}</h3>
                <c:if test='${sessionScope.userStatus eq "admin"}'>
                    <form action="#" method="post" role="form">
                        <div class="form-group">
                            <button type="submit" class="btn btn-success">${requestScope.localeAddMovie}</button>
                        </div>
                    </form>
                </c:if>
                <c:forEach items="${requestScope.person.moviesAsActor}" var="movie">
                    <div class="well clearfix">
                        <a href="Controller?command=movie&id=${movie.id}">
                            <img src="img/${movie.image}" class="img-rounded" alt="${movie.name}">
                        </a>
                        <a href="Controller?command=movie&id=${movie.id}"><h3>${movie.name}</h3></a>
                        <ul>
                            <li>${requestScope.localeCountry}: <c:forEach items="${movie.countries}" var="country"><a href="Controller?command=movies&searchFormCountries[]=${country.id}">${country.name}</a> </c:forEach></li>
                            <li>${requestScope.localeGenre}: <c:forEach items="${movie.genres}" var="genre"><a href="Controller?command=movies&searchFormGenres[]=${genre.id}">${genre.name}</a> </c:forEach></li>
                            <li>${requestScope.localeDirector}: <c:forEach items="${movie.directors}" var="director"><a href="Controller?command=person&id=${director.id}">${director.name}</a> </c:forEach></li>
                            <li>${requestScope.localeYear}: ${movie.year}</li>
                            <li>${requestScope.localeBudget}: ${movie.budget} $</li>
                            <li>${requestScope.localePremiere}: ${movie.premiere}</li>
                            <li>${requestScope.localeLasting}: ${movie.lasting} ${requestScope.localeMinute}</li>
                            <li>${requestScope.localeRating}: ${movie.averageRating}</li>
                        </ul>
                        <p>${movie.annotation}</p>
                        <c:if test='${sessionScope.userStatus eq "admin"}'>
                            <a href="#" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#remove-modal">${requestScope.localeDelete}</a>
                        </c:if>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test='${requestScope.person.moviesAsDirector != null || sessionScope.userStatus eq "admin"}'>
                <h3>${requestScope.localeAsDirector}</h3>
                <c:if test='${sessionScope.userStatus eq "admin"}'>
                    <form action="#" method="post" role="form">
                        <div class="form-group">
                            <button type="submit" class="btn btn-success">${requestScope.localeAddMovie}</button>
                        </div>
                    </form>
                </c:if>
                <c:forEach items="${requestScope.person.moviesAsDirector}" var="movie">
                    <div class="well clearfix">
                        <a href="Controller?command=movie&id=${movie.id}">
                            <img src="img/${movie.image}" class="img-rounded" alt="${movie.name}">
                        </a>
                        <a href="Controller?command=movie&id=${movie.id}"><h3>${movie.name}</h3></a>
                        <ul>
                            <li>${requestScope.localeCountry}: <c:forEach items="${movie.countries}" var="country"><a href="Controller?command=movies&searchFormCountries[]=${country.id}">${country.name}</a> </c:forEach></li>
                            <li>${requestScope.localeGenre}: <c:forEach items="${movie.genres}" var="genre"><a href="Controller?command=movies&searchFormGenres[]=${genre.id}">${genre.name}</a> </c:forEach></li>
                            <li>${requestScope.localeDirector}: <c:forEach items="${movie.directors}" var="director"><a href="Controller?command=person&id=${director.id}">${director.name}</a> </c:forEach></li>
                            <li>${requestScope.localeYear}: ${movie.year}</li>
                            <li>${requestScope.localeBudget}: ${movie.budget} $</li>
                            <li>${requestScope.localePremiere}: ${movie.premiere}</li>
                            <li>${requestScope.localeLasting}: ${movie.lasting} ${requestScope.localeMinute}</li>
                            <li>${requestScope.localeRating}: ${movie.averageRating}</li>
                        </ul>
                        <p>${movie.annotation}</p>
                        <c:if test='${sessionScope.userStatus eq "admin"}'>
                            <a href="#" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#remove-modal">${requestScope.localeDelete}</a>
                        </c:if>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test='${requestScope.person.moviesAsProducer != null || sessionScope.userStatus eq "admin"}'>
                <h3>${requestScope.localeAsProducer}</h3>
                <c:if test='${sessionScope.userStatus eq "admin"}'>
                    <form action="#" method="post" role="form">
                        <div class="form-group">
                            <button type="submit" class="btn btn-success">${requestScope.localeAddMovie}</button>
                        </div>
                    </form>
                </c:if>
                <c:forEach items="${requestScope.person.moviesAsProducer}" var="movie">
                    <div class="well clearfix">
                        <a href="Controller?command=movie&id=${movie.id}">
                            <img src="img/${movie.image}" class="img-rounded" alt="${movie.name}">
                        </a>
                        <a href="Controller?command=movie&id=${movie.id}"><h3>${movie.name}</h3></a>
                        <ul>
                            <li>${requestScope.localeCountry}: <c:forEach items="${movie.countries}" var="country"><a href="Controller?command=movies&searchFormCountries[]=${country.id}">${country.name}</a> </c:forEach></li>
                            <li>${requestScope.localeGenre}: <c:forEach items="${movie.genres}" var="genre"><a href="Controller?command=movies&searchFormGenres[]=${genre.id}">${genre.name}</a> </c:forEach></li>
                            <li>${requestScope.localeDirector}: <c:forEach items="${movie.directors}" var="director"><a href="Controller?command=person&id=${director.id}">${director.name}</a> </c:forEach></li>
                            <li>${requestScope.localeYear}: ${movie.year}</li>
                            <li>${requestScope.localeBudget}: ${movie.budget} $</li>
                            <li>${requestScope.localePremiere}: ${movie.premiere}</li>
                            <li>${requestScope.localeLasting}: ${movie.lasting} ${requestScope.localeMinute}</li>
                            <li>${requestScope.localeRating}: ${movie.averageRating}</li>
                        </ul>
                        <p>${movie.annotation}</p>
                        <c:if test='${sessionScope.userStatus eq "admin"}'>
                            <a href="#" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#remove-modal">${requestScope.localeDelete}</a>
                        </c:if>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test='${requestScope.person.moviesAsWriter != null || sessionScope.userStatus eq "admin"}'>
                <h3>${requestScope.localeAsWriter}</h3>
                <c:if test='${sessionScope.userStatus eq "admin"}'>
                    <form action="#" method="post" role="form">
                        <div class="form-group">
                            <button type="submit" class="btn btn-success">${requestScope.localeAddMovie}</button>
                        </div>
                    </form>
                </c:if>
                <c:forEach items="${requestScope.person.moviesAsWriter}" var="movie">
                    <div class="well clearfix">
                        <a href="Controller?command=movie&id=${movie.id}">
                            <img src="img/${movie.image}" class="img-rounded" alt="${movie.name}">
                        </a>
                        <a href="Controller?command=movie&id=${movie.id}"><h3>${movie.name}</h3></a>
                        <ul>
                            <li>${requestScope.localeCountry}: <c:forEach items="${movie.countries}" var="country"><a href="Controller?command=movies&searchFormCountries[]=${country.id}">${country.name}</a> </c:forEach></li>
                            <li>${requestScope.localeGenre}: <c:forEach items="${movie.genres}" var="genre"><a href="Controller?command=movies&searchFormGenres[]=${genre.id}">${genre.name}</a> </c:forEach></li>
                            <li>${requestScope.localeDirector}: <c:forEach items="${movie.directors}" var="director"><a href="Controller?command=person&id=${director.id}">${director.name}</a> </c:forEach></li>
                            <li>${requestScope.localeYear}: ${movie.year}</li>
                            <li>${requestScope.localeBudget}: ${movie.budget} $</li>
                            <li>${requestScope.localePremiere}: ${movie.premiere}</li>
                            <li>${requestScope.localeLasting}: ${movie.lasting} ${requestScope.localeMinute}</li>
                            <li>${requestScope.localeRating}: ${movie.averageRating}</li>
                        </ul>
                        <p>${movie.annotation}</p>
                        <c:if test='${sessionScope.userStatus eq "admin"}'>
                            <a href="#" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#remove-modal">${requestScope.localeDelete}</a>
                        </c:if>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test='${requestScope.person.moviesAsPainter != null || sessionScope.userStatus eq "admin"}'>
                <h3>${requestScope.localeAsPainter}</h3>
                <c:if test='${sessionScope.userStatus eq "admin"}'>
                    <form action="#" method="post" role="form">
                        <div class="form-group">
                            <button type="submit" class="btn btn-success">${requestScope.localeAddMovie}</button>
                        </div>
                    </form>
                </c:if>
                <c:forEach items="${requestScope.person.moviesAsPainter}" var="movie">
                    <div class="well clearfix">
                        <a href="Controller?command=movie&id=${movie.id}">
                            <img src="img/${movie.image}" class="img-rounded" alt="${movie.name}">
                        </a>
                        <a href="Controller?command=movie&id=${movie.id}"><h3>${movie.name}</h3></a>
                        <ul>
                            <li>${requestScope.localeCountry}: <c:forEach items="${movie.countries}" var="country"><a href="Controller?command=movies&searchFormCountries[]=${country.id}">${country.name}</a> </c:forEach></li>
                            <li>${requestScope.localeGenre}: <c:forEach items="${movie.genres}" var="genre"><a href="Controller?command=movies&searchFormGenres[]=${genre.id}">${genre.name}</a> </c:forEach></li>
                            <li>${requestScope.localeDirector}: <c:forEach items="${movie.directors}" var="director"><a href="Controller?command=person&id=${director.id}">${director.name}</a> </c:forEach></li>
                            <li>${requestScope.localeYear}: ${movie.year}</li>
                            <li>${requestScope.localeBudget}: ${movie.budget} $</li>
                            <li>${requestScope.localePremiere}: ${movie.premiere}</li>
                            <li>${requestScope.localeLasting}: ${movie.lasting} ${requestScope.localeMinute}</li>
                            <li>${requestScope.localeRating}: ${movie.averageRating}</li>
                        </ul>
                        <p>${movie.annotation}</p>
                        <c:if test='${sessionScope.userStatus eq "admin"}'>
                            <a href="#" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#remove-modal">${requestScope.localeDelete}</a>
                        </c:if>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test='${requestScope.person.moviesAsOperator != null || sessionScope.userStatus eq "admin"}'>
                <h3>${requestScope.localeAsOperator}</h3>
                <c:if test='${sessionScope.userStatus eq "admin"}'>
                    <form action="#" method="post" role="form">
                        <div class="form-group">
                            <button type="submit" class="btn btn-success">${requestScope.localeAddMovie}</button>
                        </div>
                    </form>
                </c:if>
                <c:forEach items="${requestScope.person.moviesAsOperator}" var="movie">
                    <div class="well clearfix">
                        <a href="Controller?command=movie&id=${movie.id}">
                            <img src="img/${movie.image}" class="img-rounded" alt="${movie.name}">
                        </a>
                        <a href="Controller?command=movie&id=${movie.id}"><h3>${movie.name}</h3></a>
                        <ul>
                            <li>${requestScope.localeCountry}: <c:forEach items="${movie.countries}" var="country"><a href="Controller?command=movies&searchFormCountries[]=${country.id}">${country.name}</a> </c:forEach></li>
                            <li>${requestScope.localeGenre}: <c:forEach items="${movie.genres}" var="genre"><a href="Controller?command=movies&searchFormGenres[]=${genre.id}">${genre.name}</a> </c:forEach></li>
                            <li>${requestScope.localeDirector}: <c:forEach items="${movie.directors}" var="director"><a href="Controller?command=person&id=${director.id}">${director.name}</a> </c:forEach></li>
                            <li>${requestScope.localeYear}: ${movie.year}</li>
                            <li>${requestScope.localeBudget}: ${movie.budget} $</li>
                            <li>${requestScope.localePremiere}: ${movie.premiere}</li>
                            <li>${requestScope.localeLasting}: ${movie.lasting} ${requestScope.localeMinute}</li>
                            <li>${requestScope.localeRating}: ${movie.averageRating}</li>
                        </ul>
                        <p>${movie.annotation}</p>
                        <c:if test='${sessionScope.userStatus eq "admin"}'>
                            <a href="#" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#remove-modal">${requestScope.localeDelete}</a>
                        </c:if>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test='${requestScope.person.moviesAsEditor != null || sessionScope.userStatus eq "admin"}'>
                <h3>${requestScope.localeAsEditor}</h3>
                <c:if test='${sessionScope.userStatus eq "admin"}'>
                    <form action="#" method="post" role="form">
                        <div class="form-group">
                            <button type="submit" class="btn btn-success">${requestScope.localeAddMovie}</button>
                        </div>
                    </form>
                </c:if>
                <c:forEach items="${requestScope.person.moviesAsEditor}" var="movie">
                    <div class="well clearfix">
                        <a href="Controller?command=movie&id=${movie.id}">
                            <img src="img/${movie.image}" class="img-rounded" alt="${movie.name}">
                        </a>
                        <a href="Controller?command=movie&id=${movie.id}"><h3>${movie.name}</h3></a>
                        <ul>
                            <li>${requestScope.localeCountry}: <c:forEach items="${movie.countries}" var="country"><a href="Controller?command=movies&searchFormCountries[]=${country.id}">${country.name}</a> </c:forEach></li>
                            <li>${requestScope.localeGenre}: <c:forEach items="${movie.genres}" var="genre"><a href="Controller?command=movies&searchFormGenres[]=${genre.id}">${genre.name}</a> </c:forEach></li>
                            <li>${requestScope.localeDirector}: <c:forEach items="${movie.directors}" var="director"><a href="Controller?command=person&id=${director.id}">${director.name}</a> </c:forEach></li>
                            <li>${requestScope.localeYear}: ${movie.year}</li>
                            <li>${requestScope.localeBudget}: ${movie.budget} $</li>
                            <li>${requestScope.localePremiere}: ${movie.premiere}</li>
                            <li>${requestScope.localeLasting}: ${movie.lasting} ${requestScope.localeMinute}</li>
                            <li>${requestScope.localeRating}: ${movie.averageRating}</li>
                        </ul>
                        <p>${movie.annotation}</p>
                        <c:if test='${sessionScope.userStatus eq "admin"}'>
                            <a href="#" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#remove-modal">${requestScope.localeDelete}</a>
                        </c:if>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test='${requestScope.person.moviesAsComposer != null || sessionScope.userStatus eq "admin"}'>
                <h3>${requestScope.localeAsComposer}</h3>
                <c:if test='${sessionScope.userStatus eq "admin"}'>
                    <form action="#" method="post" role="form">
                        <div class="form-group">
                            <button type="submit" class="btn btn-success">${requestScope.localeAddMovie}</button>
                        </div>
                    </form>
                </c:if>
                <c:forEach items="${requestScope.person.moviesAsComposer}" var="movie">
                    <div class="well clearfix">
                        <a href="Controller?command=movie&id=${movie.id}">
                            <img src="img/${movie.image}" class="img-rounded" alt="${movie.name}">
                        </a>
                        <a href="Controller?command=movie&id=${movie.id}"><h3>${movie.name}</h3></a>
                        <ul>
                            <li>${requestScope.localeCountry}: <c:forEach items="${movie.countries}" var="country"><a href="Controller?command=movies&searchFormCountries[]=${country.id}">${country.name}</a> </c:forEach></li>
                            <li>${requestScope.localeGenre}: <c:forEach items="${movie.genres}" var="genre"><a href="Controller?command=movies&searchFormGenres[]=${genre.id}">${genre.name}</a> </c:forEach></li>
                            <li>${requestScope.localeDirector}: <c:forEach items="${movie.directors}" var="director"><a href="Controller?command=person&id=${director.id}">${director.name}</a> </c:forEach></li>
                            <li>${requestScope.localeYear}: ${movie.year}</li>
                            <li>${requestScope.localeBudget}: ${movie.budget} $</li>
                            <li>${requestScope.localePremiere}: ${movie.premiere}</li>
                            <li>${requestScope.localeLasting}: ${movie.lasting} ${requestScope.localeMinute}</li>
                            <li>${requestScope.localeRating}: ${movie.averageRating}</li>
                        </ul>
                        <p>${movie.annotation}</p>
                        <c:if test='${sessionScope.userStatus eq "admin"}'>
                            <a href="#" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#remove-modal">${requestScope.localeDelete}</a>
                        </c:if>
                    </div>
                </c:forEach>
            </c:if>
        </div>
        <div id="remove-modal" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">${requestScope.localeDeleteTitle}</h4>
                    </div>
                    <div class="modal-body">
                        <p>${requestScope.localeDeleteBody}</p>
                    </div>
                    <div class="modal-footer">
                        <a href="#" class="btn btn-danger">${requestScope.localeDelete}</a>
                        <button type="button" class="btn btn-default" data-dismiss="modal">${requestScope.localeCancel}</button>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
</main>
<footer class="container-fluid">
    <p class="text-center">EPAM Training Center, Java 5 2016, Kostevich Vladislav</p>
</footer>
</body>
</html>
