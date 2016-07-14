<%--
  Created by IntelliJ IDEA.
  User: Владислав
  Date: 14.07.2016
  Time: 18:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Пользователь</title>
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
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Прочее <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="genres.html">Жанры</a></li>
                            <li><a href="countries.html">Страны</a></li>
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
        <h3>Арина Федосова</h3>
        <div class="clearfix">
            <img src="../img/arinafedosova@gmail.com.jpg" class="img-circle" alt="Арина Федосова">
            <ul>
                <li>Эл.почта: arinafedosova@gmail.com</li>
                <li>На сайте с: 18 июн 2016</li>
                <li>Рейтинг: 8</li>
                <li>Статус: Нормальный</li>
            </ul>
        </div>
        <form action="forms/user-form.html" method="post" role="form">
            <div class="form-group">
                <button type="submit" class="btn btn-success">Редактировать</button>
            </div>
        </form>
        <form action="#" method="post" role="form">
            <div class="form-group">
                <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#remove-modal">Удалить</button>
            </div>
        </form>
        <h3>Отзывы</h3>
        <div class="well clearfix">
            <a href="user.html">
                <img src="img/arinafedosova@gmail.com.jpg" class="img-circle" alt="Арина Федосова">
            </a>
            <h3>Lorem ipsum dolor sit amet</h3>
            <p>04.07.2016 к фильму <a href="movie.html">Бойцовский клуб</a></p>
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam sollicitudin, dolor in porta consectetur, dolor mauris ultricies nulla, et lacinia arcu nibh eu quam. Curabitur in malesuada nisi. Etiam vulputate, mauris nec tristique euismod, leo massa tincidunt justo, malesuada varius nulla nulla non enim. Nunc nisl purus, tincidunt eu est interdum, faucibus ultricies odio. Aenean pharetra porttitor mollis. In neque dolor, aliquet ac condimentum et, malesuada vel sem.</p>
            <a href="comment-form.html" class="btn btn-success btn-sm">Редактировать</a>
            <a href="#" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#remove-modal">Удалить</a>
        </div>
        <div class="well clearfix">
            <a href="user.html">
                <img src="img/arinafedosova@gmail.com.jpg" class="img-circle" alt="Арина Федосова">
            </a>
            <h3>Lorem ipsum dolor sit amet</h3>
            <p>04.07.2016 к фильму <a href="movie.html">Бойцовский клуб</a></p>
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam sollicitudin, dolor in porta consectetur, dolor mauris ultricies nulla, et lacinia arcu nibh eu quam. Curabitur in malesuada nisi. Etiam vulputate, mauris nec tristique euismod, leo massa tincidunt justo, malesuada varius nulla nulla non enim. Nunc nisl purus, tincidunt eu est interdum, faucibus ultricies odio. Aenean pharetra porttitor mollis. In neque dolor, aliquet ac condimentum et, malesuada vel sem.</p>
            <a href="comment-form.html" class="btn btn-success btn-sm">Редактировать</a>
            <a href="#" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#remove-modal">Удалить</a>
        </div>
        <div class="well clearfix">
            <a href="user.html">
                <img src="img/arinafedosova@gmail.com.jpg" class="img-circle" alt="Арина Федосова">
            </a>
            <h3>Lorem ipsum dolor sit amet</h3>
            <p>04.07.2016 к фильму <a href="movie.html">Бойцовский клуб</a></p>
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam sollicitudin, dolor in porta consectetur, dolor mauris ultricies nulla, et lacinia arcu nibh eu quam. Curabitur in malesuada nisi. Etiam vulputate, mauris nec tristique euismod, leo massa tincidunt justo, malesuada varius nulla nulla non enim. Nunc nisl purus, tincidunt eu est interdum, faucibus ultricies odio. Aenean pharetra porttitor mollis. In neque dolor, aliquet ac condimentum et, malesuada vel sem.</p>
            <a href="comment-form.html" class="btn btn-success btn-sm">Редактировать</a>
            <a href="#" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#remove-modal">Удалить</a>
        </div>
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
</main>
<footer class="container-fluid">
    <p class="text-center">EPAM Training Center, Java 5 2016, Kostevich Vladislav</p>
</footer>
</body>
</html>
