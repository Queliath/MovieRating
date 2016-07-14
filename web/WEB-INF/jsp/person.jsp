<%--
  Created by IntelliJ IDEA.
  User: Владислав
  Date: 14.07.2016
  Time: 18:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Личность</title>
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
        <h3>Эдвард Нортон</h3>
        <div class="clearfix">
            <img src="../img/edward-norton.jpg" class="img-rounded" alt="Эдвард Нортон">
            <ul>
                <li>Карьера: Актер, Режиссер, Сценарист, Продюсер</li>
                <li>Всего фильмов: 130</li>
                <li>Дата рождения: 18 авг 1969</li>
                <li>Место рождения: Бостон, Массачусетс, США</li>
            </ul>
        </div>
        <form action="forms/person-form.html" method="post" role="form">
            <div class="form-group">
                <button type="submit" class="btn btn-success">Редактировать</button>
            </div>
        </form>
        <form action="#" method="post" role="form">
            <div class="form-group">
                <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#remove-modal">Удалить</button>
            </div>
        </form>
        <h3>Актер</h3>
        <form action="movies.html" method="post" role="form">
            <div class="form-group">
                <button type="submit" class="btn btn-success">Добавить фильм</button>
            </div>
        </form>
        <div class="well clearfix">
            <a href="movie.html">
                <img src="../img/fight-club.jpg" class="img-rounded" alt="Бойцовский клуб">
            </a>
            <a href="movie.html"><h3>Бойцовский клуб</h3></a>
            <ul>
                <li>Страна: <a href="movies.html">США</a>, <a href="movies.html">Германия</a></li>
                <li>Жанр: <a href="movies.html">Триллер</a>, <a href="movies.html">Драма</a></li>
                <li>Режиссер: <a href="person.html">Дэвид Финчер</a></li>
                <li>Год: 1999</li>
                <li>Бюджет: 63 000 000 $</li>
                <li>Премьера: 10 сен 1999</li>
                <li>Время: 131 мин.</li>
                <li>Рейтинг: 8.7</li>
            </ul>
            <p>Терзаемый хронической бессонницей и отчаянно пытающийся вырваться из мучительно скучной жизни, клерк встречает некоего Тайлера Дардена, харизматического торговца мылом с извращенной философией. Тайлер уверен, что самосовершенствование — удел слабых, а саморазрушение — единственное, ради чего стоит жить.</p>
            <a href="#" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#remove-modal">Удалить</a>
        </div>
        <div class="well clearfix">
            <a href="movie.html">
                <img src="../img/fight-club.jpg" class="img-rounded" alt="Бойцовский клуб">
            </a>
            <a href="movie.html"><h3>Бойцовский клуб</h3></a>
            <ul>
                <li>Страна: <a href="movies.html">США</a>, <a href="movies.html">Германия</a></li>
                <li>Жанр: <a href="movies.html">Триллер</a>, <a href="movies.html">Драма</a></li>
                <li>Режиссер: <a href="person.html">Дэвид Финчер</a></li>
                <li>Год: 1999</li>
                <li>Бюджет: 63 000 000 $</li>
                <li>Премьера: 10 сен 1999</li>
                <li>Время: 131 мин.</li>
                <li>Рейтинг: 8.7</li>
            </ul>
            <p>Терзаемый хронической бессонницей и отчаянно пытающийся вырваться из мучительно скучной жизни, клерк встречает некоего Тайлера Дардена, харизматического торговца мылом с извращенной философией. Тайлер уверен, что самосовершенствование — удел слабых, а саморазрушение — единственное, ради чего стоит жить.</p>
            <a href="#" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#remove-modal">Удалить</a>
        </div>
        <div class="well clearfix">
            <a href="movie.html">
                <img src="../img/fight-club.jpg" class="img-rounded" alt="Бойцовский клуб">
            </a>
            <a href="movie.html"><h3>Бойцовский клуб</h3></a>
            <ul>
                <li>Страна: <a href="movies.html">США</a>, <a href="movies.html">Германия</a></li>
                <li>Жанр: <a href="movies.html">Триллер</a>, <a href="movies.html">Драма</a></li>
                <li>Режиссер: <a href="person.html">Дэвид Финчер</a></li>
                <li>Год: 1999</li>
                <li>Бюджет: 63 000 000 $</li>
                <li>Премьера: 10 сен 1999</li>
                <li>Время: 131 мин.</li>
                <li>Рейтинг: 8.7</li>
            </ul>
            <p>Терзаемый хронической бессонницей и отчаянно пытающийся вырваться из мучительно скучной жизни, клерк встречает некоего Тайлера Дардена, харизматического торговца мылом с извращенной философией. Тайлер уверен, что самосовершенствование — удел слабых, а саморазрушение — единственное, ради чего стоит жить.</p>
            <a href="#" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#remove-modal">Удалить</a>
        </div>
        <div class="well clearfix">
            <a href="movie.html">
                <img src="../img/fight-club.jpg" class="img-rounded" alt="Бойцовский клуб">
            </a>
            <a href="movie.html"><h3>Бойцовский клуб</h3></a>
            <ul>
                <li>Страна: <a href="movies.html">США</a>, <a href="movies.html">Германия</a></li>
                <li>Жанр: <a href="movies.html">Триллер</a>, <a href="movies.html">Драма</a></li>
                <li>Режиссер: <a href="person.html">Дэвид Финчер</a></li>
                <li>Год: 1999</li>
                <li>Бюджет: 63 000 000 $</li>
                <li>Премьера: 10 сен 1999</li>
                <li>Время: 131 мин.</li>
                <li>Рейтинг: 8.7</li>
            </ul>
            <p>Терзаемый хронической бессонницей и отчаянно пытающийся вырваться из мучительно скучной жизни, клерк встречает некоего Тайлера Дардена, харизматического торговца мылом с извращенной философией. Тайлер уверен, что самосовершенствование — удел слабых, а саморазрушение — единственное, ради чего стоит жить.</p>
            <a href="#" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#remove-modal">Удалить</a>
        </div>
        <div class="well clearfix">
            <a href="movie.html">
                <img src="../img/fight-club.jpg" class="img-rounded" alt="Бойцовский клуб">
            </a>
            <a href="movie.html"><h3>Бойцовский клуб</h3></a>
            <ul>
                <li>Страна: <a href="movies.html">США</a>, <a href="movies.html">Германия</a></li>
                <li>Жанр: <a href="movies.html">Триллер</a>, <a href="movies.html">Драма</a></li>
                <li>Режиссер: <a href="person.html">Дэвид Финчер</a></li>
                <li>Год: 1999</li>
                <li>Бюджет: 63 000 000 $</li>
                <li>Премьера: 10 сен 1999</li>
                <li>Время: 131 мин.</li>
                <li>Рейтинг: 8.7</li>
            </ul>
            <p>Терзаемый хронической бессонницей и отчаянно пытающийся вырваться из мучительно скучной жизни, клерк встречает некоего Тайлера Дардена, харизматического торговца мылом с извращенной философией. Тайлер уверен, что самосовершенствование — удел слабых, а саморазрушение — единственное, ради чего стоит жить.</p>
            <a href="#" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#remove-modal">Удалить</a>
        </div>
        <h3>Режиссер</h3>
        <form action="movies.html" method="post" role="form">
            <div class="form-group">
                <button type="submit" class="btn btn-success">Добавить фильм</button>
            </div>
        </form>
        <div class="well clearfix">
            <a href="movie.html">
                <img src="../img/fight-club.jpg" class="img-rounded" alt="Бойцовский клуб">
            </a>
            <a href="movie.html"><h3>Бойцовский клуб</h3></a>
            <ul>
                <li>Страна: <a href="movies.html">США</a>, <a href="movies.html">Германия</a></li>
                <li>Жанр: <a href="movies.html">Триллер</a>, <a href="movies.html">Драма</a></li>
                <li>Режиссер: <a href="person.html">Дэвид Финчер</a></li>
                <li>Год: 1999</li>
                <li>Бюджет: 63 000 000 $</li>
                <li>Премьера: 10 сен 1999</li>
                <li>Время: 131 мин.</li>
                <li>Рейтинг: 8.7</li>
            </ul>
            <p>Терзаемый хронической бессонницей и отчаянно пытающийся вырваться из мучительно скучной жизни, клерк встречает некоего Тайлера Дардена, харизматического торговца мылом с извращенной философией. Тайлер уверен, что самосовершенствование — удел слабых, а саморазрушение — единственное, ради чего стоит жить.</p>
            <a href="#" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#remove-modal">Удалить</a>
        </div>
        <h3>Сценарист</h3>
        <form action="movies.html" method="post" role="form">
            <div class="form-group">
                <button type="submit" class="btn btn-success">Добавить фильм</button>
            </div>
        </form>
        <div class="well clearfix">
            <a href="movie.html">
                <img src="../img/fight-club.jpg" class="img-rounded" alt="Бойцовский клуб">
            </a>
            <a href="movie.html"><h3>Бойцовский клуб</h3></a>
            <ul>
                <li>Страна: <a href="movies.html">США</a>, <a href="movies.html">Германия</a></li>
                <li>Жанр: <a href="movies.html">Триллер</a>, <a href="movies.html">Драма</a></li>
                <li>Режиссер: <a href="person.html">Дэвид Финчер</a></li>
                <li>Год: 1999</li>
                <li>Бюджет: 63 000 000 $</li>
                <li>Премьера: 10 сен 1999</li>
                <li>Время: 131 мин.</li>
                <li>Рейтинг: 8.7</li>
            </ul>
            <p>Терзаемый хронической бессонницей и отчаянно пытающийся вырваться из мучительно скучной жизни, клерк встречает некоего Тайлера Дардена, харизматического торговца мылом с извращенной философией. Тайлер уверен, что самосовершенствование — удел слабых, а саморазрушение — единственное, ради чего стоит жить.</p>
            <a href="#" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#remove-modal">Удалить</a>
        </div>
        <div class="well clearfix">
            <a href="movie.html">
                <img src="../img/fight-club.jpg" class="img-rounded" alt="Бойцовский клуб">
            </a>
            <a href="movie.html"><h3>Бойцовский клуб</h3></a>
            <ul>
                <li>Страна: <a href="movies.html">США</a>, <a href="movies.html">Германия</a></li>
                <li>Жанр: <a href="movies.html">Триллер</a>, <a href="movies.html">Драма</a></li>
                <li>Режиссер: <a href="person.html">Дэвид Финчер</a></li>
                <li>Год: 1999</li>
                <li>Бюджет: 63 000 000 $</li>
                <li>Премьера: 10 сен 1999</li>
                <li>Время: 131 мин.</li>
                <li>Рейтинг: 8.7</li>
            </ul>
            <p>Терзаемый хронической бессонницей и отчаянно пытающийся вырваться из мучительно скучной жизни, клерк встречает некоего Тайлера Дардена, харизматического торговца мылом с извращенной философией. Тайлер уверен, что самосовершенствование — удел слабых, а саморазрушение — единственное, ради чего стоит жить.</p>
            <a href="#" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#remove-modal">Удалить</a>
        </div>
        <h3>Продюсер</h3>
        <form action="movies.html" method="post" role="form">
            <div class="form-group">
                <button type="submit" class="btn btn-success">Добавить фильм</button>
            </div>
        </form>
        <div class="well clearfix">
            <a href="movie.html">
                <img src="../img/fight-club.jpg" class="img-rounded" alt="Бойцовский клуб">
            </a>
            <a href="movie.html"><h3>Бойцовский клуб</h3></a>
            <ul>
                <li>Страна: <a href="movies.html">США</a>, <a href="movies.html">Германия</a></li>
                <li>Жанр: <a href="movies.html">Триллер</a>, <a href="movies.html">Драма</a></li>
                <li>Режиссер: <a href="person.html">Дэвид Финчер</a></li>
                <li>Год: 1999</li>
                <li>Бюджет: 63 000 000 $</li>
                <li>Премьера: 10 сен 1999</li>
                <li>Время: 131 мин.</li>
                <li>Рейтинг: 8.7</li>
            </ul>
            <p>Терзаемый хронической бессонницей и отчаянно пытающийся вырваться из мучительно скучной жизни, клерк встречает некоего Тайлера Дардена, харизматического торговца мылом с извращенной философией. Тайлер уверен, что самосовершенствование — удел слабых, а саморазрушение — единственное, ради чего стоит жить.</p>
            <a href="#" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#remove-modal">Удалить</a>
        </div>
        <div class="well clearfix">
            <a href="movie.html">
                <img src="../img/fight-club.jpg" class="img-rounded" alt="Бойцовский клуб">
            </a>
            <a href="movie.html"><h3>Бойцовский клуб</h3></a>
            <ul>
                <li>Страна: <a href="movies.html">США</a>, <a href="movies.html">Германия</a></li>
                <li>Жанр: <a href="movies.html">Триллер</a>, <a href="movies.html">Драма</a></li>
                <li>Режиссер: <a href="person.html">Дэвид Финчер</a></li>
                <li>Год: 1999</li>
                <li>Бюджет: 63 000 000 $</li>
                <li>Премьера: 10 сен 1999</li>
                <li>Время: 131 мин.</li>
                <li>Рейтинг: 8.7</li>
            </ul>
            <p>Терзаемый хронической бессонницей и отчаянно пытающийся вырваться из мучительно скучной жизни, клерк встречает некоего Тайлера Дардена, харизматического торговца мылом с извращенной философией. Тайлер уверен, что самосовершенствование — удел слабых, а саморазрушение — единственное, ради чего стоит жить.</p>
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
