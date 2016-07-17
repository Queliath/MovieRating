<%--
  Created by IntelliJ IDEA.
  User: Владислав
  Date: 14.07.2016
  Time: 18:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${requestScope.siteName}</title>
    <!--<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">-->
    <!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>-->
    <!--<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>-->
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
                    <li class="active"><a href="Controller?command=welcome">${requestScope.mainPageName}</a></li>
                    <li><a href="catalog.html">${requestScope.catalogPageName}</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="profile.html"><span class="glyphicon glyphicon-user"></span> ${requestScope.profilePageName}</a></li>
                    <li><a href="registration.html"><span class="glyphicon glyphicon-user"></span> ${requestScope.registrationPageName}</a></li>
                    <li><a href="login.html"><span class="glyphicon glyphicon-log-in"></span> ${requestScope.loginPageName}</a></li>
                    <li class="active"><a href="#">RU</a></li>
                    <li><a href="#">EN</a></li>
                </ul>
            </div>
        </div>
    </nav>
</header>
<main class="container">
    <div class="row">
        <div class="col-sm-6">
            <h2>${requestScope.searchMovieByGenre}</h2>
            <div class="list-group">
                <c:if test="${requestScope.genres != null}">
                    <c:forEach items="${requestScope.genres}" var="genre">
                        <a href="#" class="list-group-item">${genre.name}</a>
                    </c:forEach>
                </c:if>
            </div>
        </div>
        <div class="col-sm-6">
            <h2>${requestScope.searchMovieByCountry}</h2>
            <div class="list-group">
                <c:if test="${requestScope.countries != null}">
                    <c:forEach items="${requestScope.countries}" var="country">
                        <a href="#" class="list-group-item">${country.name}</a>
                    </c:forEach>
                </c:if>
            </div>
        </div>
    </div>
    <div class="jumbotron">
        <form role="form">
            <div class="form-group">
                <label for="keyword">${requestScope.findYourMovie}:</label>
                <input type="text" class="form-control" id="keyword" placeholder="${requestScope.enterMovieName}">
            </div>
            <button type="submit" class="btn btn-default">${requestScope.findButton}</button>
        </form>
    </div>
    <div class="row">
        <div class="col-sm-6">
            <h2>${requestScope.localeRecentMovies}</h2>
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
                            <li>${requestScope.localeRating}: 8.7</li>
                        </ul>
                        <p>${movie.annotation}</p>
                    </div>
                </c:forEach>
            </c:if>
        </div>
        <div class="col-sm-6">
            <h2>${requestScope.localeRecentComments}</h2>
            <div class="well clearfix">
                <a href="user.html">
                    <img src="img/arinafedosova@gmail.com.jpg" class="img-circle" alt="Арина Федосова">
                </a>
                <h3>Lorem ipsum dolor sit amet</h3>
                <p><a href="user.html">Арина Федосова</a> ${requestScope.localeToMovie} <a href="movie.html">Бойцовский клуб</a></p>
                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam sollicitudin, dolor in porta consectetur, dolor mauris ultricies nulla, et lacinia arcu nibh eu quam. Curabitur in malesuada nisi. Etiam vulputate, mauris nec tristique euismod, leo massa tincidunt justo, malesuada varius nulla nulla non enim. Nunc nisl purus, tincidunt eu est interdum, faucibus ultricies odio. Aenean pharetra porttitor mollis. In neque dolor, aliquet ac condimentum et, malesuada vel sem.</p>
            </div>
            <div class="well clearfix">
                <a href="user.html">
                    <img src="img/arinafedosova@gmail.com.jpg" class="img-circle" alt="Арина Федосова">
                </a>
                <h3>Lorem ipsum dolor sit amet</h3>
                <p><a href="user.html">Арина Федосова</a> к фильму <a href="movie.html">Бойцовский клуб</a></p>
                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam sollicitudin, dolor in porta consectetur, dolor mauris ultricies nulla, et lacinia arcu nibh eu quam. Curabitur in malesuada nisi. Etiam vulputate, mauris nec tristique euismod, leo massa tincidunt justo, malesuada varius nulla nulla non enim. Nunc nisl purus, tincidunt eu est interdum, faucibus ultricies odio. Aenean pharetra porttitor mollis. In neque dolor, aliquet ac condimentum et, malesuada vel sem.</p>
            </div>
            <div class="well clearfix">
                <a href="user.html">
                    <img src="img/arinafedosova@gmail.com.jpg" class="img-circle" alt="Арина Федосова">
                </a>
                <h3>Lorem ipsum dolor sit amet</h3>
                <p><a href="user.html">Арина Федосова</a> к фильму <a href="movie.html">Бойцовский клуб</a></p>
                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam sollicitudin, dolor in porta consectetur, dolor mauris ultricies nulla, et lacinia arcu nibh eu quam. Curabitur in malesuada nisi. Etiam vulputate, mauris nec tristique euismod, leo massa tincidunt justo, malesuada varius nulla nulla non enim. Nunc nisl purus, tincidunt eu est interdum, faucibus ultricies odio. Aenean pharetra porttitor mollis. In neque dolor, aliquet ac condimentum et, malesuada vel sem.</p>
            </div>
        </div>
    </div>
</main>
<footer class="container-fluid">
    <p class="text-center">EPAM Training Center, Java 5 2016, Kostevich Vladislav</p>
</footer>
</body>
</html>
