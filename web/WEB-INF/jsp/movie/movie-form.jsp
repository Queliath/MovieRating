<%--
  Created by IntelliJ IDEA.
  User: Владислав
  Date: 14.07.2016
  Time: 18:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Форма фильма</title>
    <!--<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">-->
    <!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>-->
    <!--<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>-->
    <link rel="stylesheet" href="../../css/bootstrap.min.css">
    <link rel="stylesheet" href="../../css/style.css">
    <script src="../../js/jquery.min.js"></script>
    <script src="../../js/bootstrap.min.js"></script>
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
                <a class="navbar-brand" href="../movies.html">КиноРейтинг</a>
            </div>
            <div class="collapse navbar-collapse" id="myNavbar">
                <ul class="nav navbar-nav">
                    <li><a href="../movies.html">Фильмы</a></li>
                    <li><a href="../persons.html">Личности</a></li>
                    <li><a href="../users.html">Пользователи</a></li>
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Прочее <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="../genres.html">Жанры</a></li>
                            <li><a href="../countries.html">Страны</a></li>
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
    <div class="jumbotron">
        <form role="form">
            <div class="form-group">
                <label for="name">Название</label>
                <input type="text" class="form-control" id="name" placeholder="Введите название">
            </div>
            <div class="form-group">
                <label for="original-name">Оригинальное название</label>
                <input type="text" class="form-control" id="original-name" placeholder="Введите оригинальное название">
            </div>
            <div class="form-group">
                <label for="year">Год выпуска</label>
                <input type="number" class="form-control" id="year" placeholder="Введите год выпуска">
            </div>
            <div class="form-group">
                <label for="tagline">Слоган</label>
                <textarea class="form-control" id="tagline" placeholder="Введите слоган"></textarea>
            </div>
            <div class="form-group">
                <label for="budget">Бюджет</label>
                <input type="number" class="form-control" id="budget" placeholder="Введите бюджет">
            </div>
            <div class="form-group">
                <label for="premiere">Дата премьеры</label>
                <input type="date" class="form-control" id="premiere">
            </div>
            <div class="form-group">
                <label for="lasting">Длительность (в мин.)</label>
                <input type="number" class="form-control" id="lasting" placeholder="Введите длительность">
            </div>
            <div class="form-group">
                <label for="annotation">Аннотация</label>
                <textarea class="form-control" id="annotation" placeholder="Введите аннотацию" rows="10"></textarea>
            </div>
            <div class="form-group">
                <label for="image">Изображение</label>
                <input type="file" class="form-control" id="image">
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-success">Сохранить</button>
            </div>
        </form>
        <div class="alert alert-success fade in">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            Данные успешно сохранены!
        </div>
        <div class="alert alert-danger fade in">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            При сохранении данных на сервере произошла ошибка. Повторите попытку позже.
        </div>
    </div>
</main>
<footer class="container-fluid">
    <p class="text-center">EPAM Training Center, Java 5 2016, Kostevich Vladislav</p>
</footer>
</body>
</html>
