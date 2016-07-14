<%--
  Created by IntelliJ IDEA.
  User: Владислав
  Date: 14.07.2016
  Time: 18:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Страны</title>
    <!--<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">-->
    <!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>-->
    <!--<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>-->
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/style.css">
    <script src="../js/jquery.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
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
                <a class="navbar-brand" href="movies.html">КиноРейтинг</a>
            </div>
            <div class="collapse navbar-collapse" id="myNavbar">
                <ul class="nav navbar-nav">
                    <li><a href="movies.html">Фильмы</a></li>
                    <li><a href="persons.html">Личности</a></li>
                    <li><a href="users.html">Пользователи</a></li>
                    <li class="dropdown active">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Прочее <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="genres.html">Жанры</a></li>
                            <li class="active"><a href="countries.html">Страны</a></li>
                        </ul>
                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#"><span class="glyphicon glyphicon-log-out"></span> Выход</a></li>
                    <li class="active"><a href="#">RU</a></li>
                    <li><a href="#">EN</a></li>
                </ul>
            </div>
        </div>
    </nav>
</header>
<main class="container">
    <form action="forms/country-form.html" method="post" role="form">
        <div class="form-group">
            <button type="submit" class="btn btn-success btn-lg">Добавить новую страну</button>
        </div>
    </form>
    <p>Отображается 10 из 78</p>
    <div class="well clearfix">
        <a href="country.html">
            <img src="../img/usa.png" class="img-rounded" alt="США">
        </a>
        <a href="country.html"><h3>США</h3></a>
        <ul>
            <li>Всего фильмов: 200</li>
        </ul>
    </div>
    <div class="well clearfix">
        <a href="country.html">
            <img src="../img/usa.png" class="img-rounded" alt="США">
        </a>
        <a href="country.html"><h3>США</h3></a>
        <ul>
            <li>Всего фильмов: 200</li>
        </ul>
    </div>
    <div class="well clearfix">
        <a href="country.html">
            <img src="../img/usa.png" class="img-rounded" alt="США">
        </a>
        <a href="country.html"><h3>США</h3></a>
        <ul>
            <li>Всего фильмов: 200</li>
        </ul>
    </div>
    <div class="well clearfix">
        <a href="country.html">
            <img src="../img/usa.png" class="img-rounded" alt="США">
        </a>
        <a href="country.html"><h3>США</h3></a>
        <ul>
            <li>Всего фильмов: 200</li>
        </ul>
    </div>
    <div class="well clearfix">
        <a href="country.html">
            <img src="../img/usa.png" class="img-rounded" alt="США">
        </a>
        <a href="country.html"><h3>США</h3></a>
        <ul>
            <li>Всего фильмов: 200</li>
        </ul>
    </div>
    <div class="well clearfix">
        <a href="country.html">
            <img src="../img/usa.png" class="img-rounded" alt="США">
        </a>
        <a href="country.html"><h3>США</h3></a>
        <ul>
            <li>Всего фильмов: 200</li>
        </ul>
    </div>
    <div class="well clearfix">
        <a href="country.html">
            <img src="../img/usa.png" class="img-rounded" alt="США">
        </a>
        <a href="country.html"><h3>США</h3></a>
        <ul>
            <li>Всего фильмов: 200</li>
        </ul>
    </div>
    <div class="well clearfix">
        <a href="country.html">
            <img src="../img/usa.png" class="img-rounded" alt="США">
        </a>
        <a href="country.html"><h3>США</h3></a>
        <ul>
            <li>Всего фильмов: 200</li>
        </ul>
    </div>
    <div class="well clearfix">
        <a href="country.html">
            <img src="../img/usa.png" class="img-rounded" alt="США">
        </a>
        <a href="country.html"><h3>США</h3></a>
        <ul>
            <li>Всего фильмов: 200</li>
        </ul>
    </div>
    <div class="well clearfix">
        <a href="country.html">
            <img src="../img/usa.png" class="img-rounded" alt="США">
        </a>
        <a href="country.html"><h3>США</h3></a>
        <ul>
            <li>Всего фильмов: 200</li>
        </ul>
    </div>
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
