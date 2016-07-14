<%--
  Created by IntelliJ IDEA.
  User: Владислав
  Date: 14.07.2016
  Time: 18:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Фильм</title>
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
        <h3>Бойцовский клуб</h3>
        <h5>Fight club</h5>
        <div class="clearfix">
            <img src="../img/fight-club.jpg" class="img-rounded" alt="Бойцовский клуб">
            <ul>
                <li>В ролях: <a href="person.html">Эдвард Нортон</a> <a href="#" data-toggle="modal" data-target="#remove-modal"><span class="glyphicon glyphicon-remove"></span></a>, <a href="person.html">Брэд Питт</a> <a href="#" data-toggle="modal" data-target="#remove-modal"><span class="glyphicon glyphicon-remove"></span></a>, <a href="person.html">Хелена Бонем-Картер</a> <a href="#" data-toggle="modal" data-target="#remove-modal"><span class="glyphicon glyphicon-remove"></span></a> <a href="persons.html" class="btn btn-success btn-xs">Добавить актера</a></li>
                <li>Режиссер: <a href="person.html">Дэвид Финчер</a> <a href="#" data-toggle="modal" data-target="#remove-modal"><span class="glyphicon glyphicon-remove"></span></a> <a href="persons.html" class="btn btn-success btn-xs">Добавить режиссера</a></li>
                <li>Сценарий: <a href="person.html">Джим Улс</a> <a href="#" data-toggle="modal" data-target="#remove-modal"><span class="glyphicon glyphicon-remove"></span></a>, <a href="person.html">Чак Паланик</a> <a href="#" data-toggle="modal" data-target="#remove-modal"><span class="glyphicon glyphicon-remove"></span></a> <a href="persons.html" class="btn btn-success btn-xs">Добавить сценариста</a></li>
                <li>Продюссер: <a href="person.html">Росс Грэйсон Белл</a> <a href="#" data-toggle="modal" data-target="#remove-modal"><span class="glyphicon glyphicon-remove"></span></a>, <a href="person.html">Арт Линсон</a> <a href="#" data-toggle="modal" data-target="#remove-modal"><span class="glyphicon glyphicon-remove"></span></a>, <a href="person.html">Сиан Чаффин</a> <a href="#" data-toggle="modal" data-target="#remove-modal"><span class="glyphicon glyphicon-remove"></span></a> <a href="persons.html" class="btn btn-success btn-xs">Добавить продюсера</a></li>
                <li>Оператор: <a href="person.html">Джефф Кроненвет</a> <a href="#" data-toggle="modal" data-target="#remove-modal"><span class="glyphicon glyphicon-remove"></span></a> <a href="persons.html" class="btn btn-success btn-xs">Добавить оператора</a></li>
                <li>Художник: <a href="person.html">Алекс Макдауэлл</a> <a href="#" data-toggle="modal" data-target="#remove-modal"><span class="glyphicon glyphicon-remove"></span></a>, <a href="person.html">Крис Горак</a> <a href="#" data-toggle="modal" data-target="#remove-modal"><span class="glyphicon glyphicon-remove"></span></a>, <a href="person.html">Майкл Каплан</a> <a href="#" data-toggle="modal" data-target="#remove-modal"><span class="glyphicon glyphicon-remove"></span></a> <a href="persons.html" class="btn btn-success btn-xs">Добавить художника</a></li>
                <li>Монтаж: <a href="person.html">Джеймс Хэйгуд</a> <a href="#" data-toggle="modal" data-target="#remove-modal"><span class="glyphicon glyphicon-remove"></span></a> <a href="persons.html" class="btn btn-success btn-xs">Добавить монтажера</a></li>
                <li>Композитор: <a href="person.html">Даст Бразерс</a> <a href="#" data-toggle="modal" data-target="#remove-modal"><span class="glyphicon glyphicon-remove"></span></a>, <a href="person.html">Джон Кинг</a> <a href="#" data-toggle="modal" data-target="#remove-modal"><span class="glyphicon glyphicon-remove"></span></a>, <a href="person.html">Майкл Симпсон</a> <a href="#" data-toggle="modal" data-target="#remove-modal"><span class="glyphicon glyphicon-remove"></span></a> <a href="persons.html" class="btn btn-success btn-xs">Добавить композитора</a></li>
                <li>Страна: <a href="movies.html">США</a> <a href="#" data-toggle="modal" data-target="#remove-modal"><span class="glyphicon glyphicon-remove"></span></a>, <a href="movies.html">Германия</a> <a href="#" data-toggle="modal" data-target="#remove-modal"><span class="glyphicon glyphicon-remove"></span></a> <a href="countries.html" class="btn btn-success btn-xs">Добавить страну</a></li>
                <li>Жанр: <a href="movies.html">Триллер</a> <a href="#" data-toggle="modal" data-target="#remove-modal"><span class="glyphicon glyphicon-remove"></span></a>, <a href="movies.html">Драма</a> <a href="#" data-toggle="modal" data-target="#remove-modal"><span class="glyphicon glyphicon-remove"></span></a> <a href="genres.html" class="btn btn-success btn-xs">Добавить жанр</a></li>
                <li>Год: 1999</li>
                <li>Слоган: "Интриги. Хаос. Мыло"</li>
                <li>Бюджет: 63 000 000 $</li>
                <li>Премьера: 10 сен 1999</li>
                <li>Время: 131 мин.</li>
            </ul>
        </div>
        <p>Терзаемый хронической бессонницей и отчаянно пытающийся вырваться из мучительно скучной жизни, клерк встречает некоего Тайлера Дардена, харизматического торговца мылом с извращенной философией. Тайлер уверен, что самосовершенствование — удел слабых, а саморазрушение — единственное, ради чего стоит жить.</p>
        <div id="star-rating"></div>
        <p>Рейтинг: 8.7</p>
        <form action="forms/movie-form.html" method="post" role="form">
            <div class="form-group">
                <button type="submit" class="btn btn-success">Редактировать</button>
            </div>
        </form>
        <form action="#" method="post" role="form">
            <div class="form-group">
                <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#remove-modal">Удалить</button>
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
</main>
<footer class="container-fluid">
    <p class="text-center">EPAM Training Center, Java 5 2016, Kostevich Vladislav</p>
</footer>
</body>
</html>
