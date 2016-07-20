<%--
  Created by IntelliJ IDEA.
  User: Владислав
  Date: 14.07.2016
  Time: 18:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${requestScope.movie.name}</title>
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
    <c:if test="${requestScope.movie == null}">
        <div class="alert alert-info fade in">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            ${requestScope.localeNoMovie}
        </div>
    </c:if>
    <c:if test="${requestScope.movie != null}">
        <div class="jumbotron">
            <h3>${requestScope.movie.name}</h3>
            <div class="clearfix">
                <img src="img/${requestScope.movie.image}" class="img-rounded" alt="${requestScope.movie.name}">
                <ul>
                    <li>${requestScope.localeActors}: <c:forEach items="${requestScope.movie.actors}" var="actor"><a href="#">${actor.name}</a> <a href="#" data-toggle="modal" data-target="#remove-modal"><span class="glyphicon glyphicon-remove"></span></a></c:forEach> <a href="#" class="btn btn-success btn-xs">${requestScope.localeAddActor}</a></li>
                    <li>${requestScope.localeDirectors}: <c:forEach items="${requestScope.movie.directors}" var="director"><a href="#">${director.name}</a> <a href="#" data-toggle="modal" data-target="#remove-modal"><span class="glyphicon glyphicon-remove"></span></a></c:forEach> <a href="#" class="btn btn-success btn-xs">${requestScope.localeAddDirector}</a></li>
                    <li>${requestScope.localeProducers}: <c:forEach items="${requestScope.movie.producers}" var="producer"><a href="#">${producer.name}</a> <a href="#" data-toggle="modal" data-target="#remove-modal"><span class="glyphicon glyphicon-remove"></span></a></c:forEach> <a href="#" class="btn btn-success btn-xs">${requestScope.localeAddProducer}</a></li>
                    <li>${requestScope.localeWriters}: <c:forEach items="${requestScope.movie.writers}" var="writer"><a href="#">${writer.name}</a> <a href="#" data-toggle="modal" data-target="#remove-modal"><span class="glyphicon glyphicon-remove"></span></a></c:forEach> <a href="#" class="btn btn-success btn-xs">${requestScope.localeAddWriter}</a></li>
                    <li>${requestScope.localePainters}: <c:forEach items="${requestScope.movie.painters}" var="painter"><a href="#">${painter.name}</a> <a href="#" data-toggle="modal" data-target="#remove-modal"><span class="glyphicon glyphicon-remove"></span></a></c:forEach> <a href="#" class="btn btn-success btn-xs">${requestScope.localeAddPainter}</a></li>
                    <li>${requestScope.localeOperators}: <c:forEach items="${requestScope.movie.operators}" var="operator"><a href="#">${operator.name}</a> <a href="#" data-toggle="modal" data-target="#remove-modal"><span class="glyphicon glyphicon-remove"></span></a></c:forEach> <a href="#" class="btn btn-success btn-xs">${requestScope.localeAddOperator}</a></li>
                    <li>${requestScope.localeEditors}: <c:forEach items="${requestScope.movie.editors}" var="editor"><a href="#">${editor.name}</a> <a href="#" data-toggle="modal" data-target="#remove-modal"><span class="glyphicon glyphicon-remove"></span></a></c:forEach> <a href="#" class="btn btn-success btn-xs">${requestScope.localeAddEditor}</a></li>
                    <li>${requestScope.localeComposers}: <c:forEach items="${requestScope.movie.composers}" var="composers"><a href="#">${composers.name}</a> <a href="#" data-toggle="modal" data-target="#remove-modal"><span class="glyphicon glyphicon-remove"></span></a></c:forEach> <a href="#" class="btn btn-success btn-xs">${requestScope.localeAddComposer}</a></li>
                    <li>${requestScope.localeCountry}: <c:forEach items="${requestScope.movie.countries}" var="country"><a href="#">${country.name}</a> <a href="#" data-toggle="modal" data-target="#remove-modal"><span class="glyphicon glyphicon-remove"></span></a></c:forEach> <a href="#" class="btn btn-success btn-xs">${requestScope.localeAddCountry}</a></li>
                    <li>${requestScope.localeGenre}: <c:forEach items="${requestScope.movie.genres}" var="genre"><a href="#">${genre.name}</a> <a href="#" data-toggle="modal" data-target="#remove-modal"><span class="glyphicon glyphicon-remove"></span></a></c:forEach> <a href="#" class="btn btn-success btn-xs">${requestScope.localeAddGenre}</a></li>
                    <li>${requestScope.localeYear}: ${requestScope.movie.year}</li>
                    <li>${requestScope.localeTagline}: ${requestScope.movie.tagline}</li>
                    <li>${requestScope.localeBudget}: ${requestScope.movie.budget} $</li>
                    <li>${requestScope.localePremiere}: ${requestScope.movie.premiere}</li>
                    <li>${requestScope.localeLasting}: ${requestScope.movie.lasting} ${requestScope.localeMin}</li>
                </ul>
            </div>
            <p>${requestScope.movie.annotation}</p>
            <div id="star-rating"></div>
            <p>${requestScope.localeRating}: ${requestScope.movie.averageRating}</p>
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
        </div>
        <h3>Оставить отзыв</h3>
        <form role="form">
            <div class="form-group">
                <label for="title">Заголовок</label>
                <input type="text" class="form-control" id="title" placeholder="Введите заголовок">
            </div>
            <div class="form-group">
                <label for="content">Содержимое</label>
                <textarea id="content" class="form-control" rows="5" placeholder="Введите содержимое"></textarea>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-default">Оставить</button>
            </div>
        </form>
        <div class="alert alert-success fade in">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            Отзыв оставлен!
        </div>
        <div class="alert alert-danger fade in">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            При сохранении отзыва на сервере произошла ошибка. Повторите попытку позже.
        </div>
        <h3>Отзывы</h3>
        <div class="well clearfix">
            <a href="user.html">
                <img src="../img/arinafedosova@gmail.com.jpg" class="img-circle" alt="Арина Федосова">
            </a>
            <h3>Lorem ipsum dolor sit amet</h3>
            <p><a href="user.html">Арина Федосова</a> 04.07.2016</p>
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam sollicitudin, dolor in porta consectetur, dolor mauris ultricies nulla, et lacinia arcu nibh eu quam. Curabitur in malesuada nisi. Etiam vulputate, mauris nec tristique euismod, leo massa tincidunt justo, malesuada varius nulla nulla non enim. Nunc nisl purus, tincidunt eu est interdum, faucibus ultricies odio. Aenean pharetra porttitor mollis. In neque dolor, aliquet ac condimentum et, malesuada vel sem.</p>
        </div>
        <div class="well clearfix">
            <a href="user.html">
                <img src="../img/arinafedosova@gmail.com.jpg" class="img-circle" alt="Арина Федосова">
            </a>
            <h3>Lorem ipsum dolor sit amet</h3>
            <p><a href="user.html">Арина Федосова</a> 04.07.2016</p>
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam sollicitudin, dolor in porta consectetur, dolor mauris ultricies nulla, et lacinia arcu nibh eu quam. Curabitur in malesuada nisi. Etiam vulputate, mauris nec tristique euismod, leo massa tincidunt justo, malesuada varius nulla nulla non enim. Nunc nisl purus, tincidunt eu est interdum, faucibus ultricies odio. Aenean pharetra porttitor mollis. In neque dolor, aliquet ac condimentum et, malesuada vel sem.</p>
        </div>
        <div class="well clearfix">
            <a href="user.html">
                <img src="../img/arinafedosova@gmail.com.jpg" class="img-circle" alt="Арина Федосова">
            </a>
            <h3>Lorem ipsum dolor sit amet</h3>
            <p><a href="user.html">Арина Федосова</a> 04.07.2016</p>
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam sollicitudin, dolor in porta consectetur, dolor mauris ultricies nulla, et lacinia arcu nibh eu quam. Curabitur in malesuada nisi. Etiam vulputate, mauris nec tristique euismod, leo massa tincidunt justo, malesuada varius nulla nulla non enim. Nunc nisl purus, tincidunt eu est interdum, faucibus ultricies odio. Aenean pharetra porttitor mollis. In neque dolor, aliquet ac condimentum et, malesuada vel sem.</p>
        </div>
        <div id="remove-modal" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Подтверждение удаления</h4>
                    </div>
                    <div class="modal-body">
                        <p>Вы уверены что хотите произвести удаление?</p>
                    </div>
                    <div class="modal-footer">
                        <a href="#" class="btn btn-danger">Удалить</a>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Отмена</button>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
</main>
<footer class="container-fluid">
    <p class="text-center">EPAM Training Center, Java 5 2016, Kostevich Vladislav</p>
</footer>
<script src="js/script.js"></script>
</body>
</html>
