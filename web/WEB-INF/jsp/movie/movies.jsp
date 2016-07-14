<%--
  Created by IntelliJ IDEA.
  User: Владислав
  Date: 14.07.2016
  Time: 18:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Фильмы</title>
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
                    <li class="active"><a href="movies.html">Фильмы</a></li>
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
        <h3>Критерии поиска</h3>
        <form role="form">
            <div class="row">
                <div class="form-group col-sm-6">
                    <label for="name">Название:</label>
                    <input type="text" id="name" class="form-control">
                </div>
                <div class="form-group col-sm-6 row">
                    <div class="col-xs-12">
                        <label>Год выпуска</label>
                    </div>
                    <div class="col-xs-6">
                        <label for="min-year">От:</label>
                        <input type="number" id="min-year" class="form-control">
                    </div>
                    <div class="col-xs-6">
                        <label for="max-year">До:</label>
                        <input type="number" id="max-year" class="form-control">
                    </div>
                </div>
                <div class="form-group col-sm-6">
                    <label>Жанр</label>
                    <div class="checkbox">
                        <label><input type="checkbox"> Триллер</label>
                    </div>
                    <div class="checkbox">
                        <label><input type="checkbox"> Драма</label>
                    </div>
                    <div class="checkbox">
                        <label><input type="checkbox"> Комедия</label>
                    </div>
                </div>
                <div class="form-group col-sm-6">
                    <label>Страна</label>
                    <div class="checkbox">
                        <label><input type="checkbox"> США</label>
                    </div>
                    <div class="checkbox">
                        <label><input type="checkbox"> Германия</label>
                    </div>
                    <div class="checkbox">
                        <label><input type="checkbox"> Великобритания</label>
                    </div>
                </div>
                <div class="form-group col-sm-6 row">
                    <div class="col-xs-12">
                        <label>Рейтинг</label>
                    </div>
                    <div class="col-xs-6">
                        <label for="min-rating">От:</label>
                        <input type="number" id="min-rating" class="form-control">
                    </div>
                    <div class="col-xs-6">
                        <label for="max-rating">До:</label>
                        <input type="number" id="max-rating" class="form-control">
                    </div>
                </div>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-default">Найти</button>
            </div>
        </form>
    </div>
    <form action="forms/movie-form.html" method="post" role="form">
        <div class="form-group">
            <button type="submit" class="btn btn-success btn-lg">Добавить новый фильм</button>
        </div>
    </form>
    <div class="alert alert-info fade in">
        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
        По выбранным критериям не найдено ни одного фильма.
    </div>
    <p>Отображается 10 из 78</p>
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
