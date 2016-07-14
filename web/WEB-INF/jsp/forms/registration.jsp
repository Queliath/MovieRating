<%--
  Created by IntelliJ IDEA.
  User: Владислав
  Date: 14.07.2016
  Time: 18:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Регистрация</title>
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
                <a class="navbar-brand" href="index.html">КиноРейтинг</a>
            </div>
            <div class="collapse navbar-collapse" id="myNavbar">
                <ul class="nav navbar-nav">
                    <li><a href="index.html">Главная</a></li>
                    <li><a href="catalog.html">Каталог</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li class="active"><a href="registration.html"><span class="glyphicon glyphicon-user"></span> Регистрация</a></li>
                    <li><a href="login.html"><span class="glyphicon glyphicon-log-in"></span> Вход</a></li>
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
                <label for="email">Эл. почта</label>
                <input type="email" class="form-control" id="email" placeholder="Введите email">
            </div>
            <div class="form-group">
                <label for="password">Пароль</label>
                <input type="password" class="form-control" id="password" placeholder="Введите пароль">
            </div>
            <div class="form-group">
                <label for="first-name">Имя</label>
                <input type="text" class="form-control" id="first-name" placeholder="Введите имя">
            </div>
            <div class="form-group">
                <label for="second-name">Фамилия</label>
                <input type="text" class="form-control" id="second-name" placeholder="Введите фамилию">
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-default">Регистрация</button>
            </div>
        </form>
        <div class="alert alert-success fade in">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            Вы успешно зарегистрированы!
        </div>
        <div class="alert alert-danger fade in">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            Аккаунт с такой почтой уже зарегистрирован на сайте!
        </div>
        <div class="alert alert-danger fade in">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            При регистрации на сервере произошла ошибка. Повторите попытку позже.
        </div>
    </div>
</main>
<footer class="container-fluid">
    <p class="text-center">EPAM Training Center, Java 5 2016, Kostevich Vladislav</p>
</footer>
</body>
</html>
