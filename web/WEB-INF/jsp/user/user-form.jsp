<%--
  Created by IntelliJ IDEA.
  User: Владислав
  Date: 14.07.2016
  Time: 18:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Форма пользователя</title>
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
                    <li><a href="profile.html"><span class="glyphicon glyphicon-user"></span> Профиль</a></li>
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
                <label for="email">Эл. почта</label>
                <input type="email" class="form-control" id="email" placeholder="Введите email" value="arinafedosova@gmail.com">
            </div>
            <div class="form-group">
                <label for="password">Пароль</label>
                <input type="password" class="form-control" id="password" placeholder="Введите пароль" value="123456789">
            </div>
            <div class="form-group">
                <label for="first-name">Имя</label>
                <input type="text" class="form-control" id="first-name" placeholder="Введите имя" value="Арина">
            </div>
            <div class="form-group">
                <label for="second-name">Фамилия</label>
                <input type="text" class="form-control" id="second-name" placeholder="Введите фамилию" value="Федосова">
            </div>
            <div class="form-group">
                <label for="photo">Фотография</label>
                <input type="file" class="form-control" id="photo">
            </div>
            <div class="form-group">
                <label for="rating">Рейтинг</label>
                <input type="number" class="form-control" id="rating" placeholder="Введите рейтинг" value="8">
            </div>
            <div class="form-group">
                <label for="status">Статус</label>
                <select class="form-control" id="status">
                    <option>Нормальный</option>
                    <option>Заблокирован</option>
                    <option>Администратор</option>
                </select>
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
            Аккаунт с такой почтой уже зарегистрирован на сайте!
        </div>
        <div class="alert alert-danger fade in">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            При сохранении данных на сервер произошла ошибка. Повторите попытку позже.
        </div>
    </div>
</main>
<footer class="container-fluid">
    <p class="text-center">EPAM Training Center, Java 5 2016, Kostevich Vladislav</p>
</footer>
</body>
</html>
