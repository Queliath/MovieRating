package by.bsuir.movierating.dao.impl;

import by.bsuir.movierating.dao.MovieDAO;
import by.bsuir.movierating.domain.Movie;
import by.bsuir.movierating.domain.criteria.MovieCriteria;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.HashMap;
import java.util.List;

/**
 * Provides a DAO-logic for the Movie entity for the MySQL Database.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
@Repository("movieDao")
public class MySQLMovieDAO implements MovieDAO {
    private static final String ADD_MOVIE_QUERY = "INSERT INTO movie (name, year, tagline, budget, premiere," +
            "lasting, annotation, image) VALUES (:name, :year, :tagline, :budget, :premiere, :lasting, :annotation, :image)";
    private static final String UPDATE_MOVIE_QUERY = "UPDATE movie SET name = :name, year = :year, tagline = :tagline," +
            "budget = :budget, premiere = :premiere, lasting = :lasting, annotation = :annotation, image = :image WHERE id = :id";
    private static final String TMOVIE_CHECK_QUERY = "SELECT id FROM tmovie " +
            "WHERE language_id = :language_id AND id = :id";
    private static final String ADD_TMOVIE_QUERY = "INSERT INTO tmovie (language_id, id, name, tagline, " +
            "annotation, image) VALUES (:language_id, :id, :name, :tagline, :annotation, :image)";
    private static final String UPDATE_TMOVIE_QUERY = "UPDATE tmovie SET name = :name, tagline = :tagline, " +
            "annotation = :annotation, image = :image WHERE language_id = :language_id AND id = :id";
    private static final String DELETE_MOVIE_QUERY = "DELETE FROM movie WHERE id = :id";
    private static final String GET_ALL_MOVIES_QUERY = "SELECT * FROM movie";
    private static final String GET_ALL_MOVIES_NOT_DEFAULT_LANG_QUERY = "SELECT m.id, " +
            "coalesce(t.name, m.name), m.year, coalesce(t.tagline, m.tagline), " +
            "m.budget, m.premiere, m.lasting, coalesce(t.annotation, m.annotation), " +
            "coalesce(t.image, m.image) FROM movie AS m LEFT JOIN (SELECT * FROM tmovie " +
            "WHERE language_id = :language_id) AS t USING(id)";
    private static final String GET_MOVIE_BY_ID_QUERY = "SELECT * FROM movie WHERE id = :id";
    private static final String GET_MOVIE_BY_ID_NOT_DEFAULT_LANG_QUERY = "SELECT m.id, " +
            "coalesce(t.name, m.name), m.year, coalesce(t.tagline, m.tagline), " +
            "m.budget, m.premiere, m.lasting, coalesce(t.annotation, m.annotation), " +
            "coalesce(t.image, m.image) FROM movie AS m LEFT JOIN (SELECT * FROM tmovie " +
            "WHERE language_id = :language_id) AS t USING(id) WHERE m.id = :id";
    private static final String GET_MOVIES_BY_COUNTRY_QUERY = "SELECT movie.* FROM movie " +
            "INNER JOIN movie_country ON movie.id = movie_country.movie_id WHERE movie_country.country_id = :country_id ";
    private static final String GET_MOVIES_BY_COUNTRY_NOT_DEFAULT_LANG_QUERY = "SELECT m.id, coalesce(t.name, m.name), m.year, " +
            "coalesce(t.tagline, m.tagline), m.budget, m.premiere, m.lasting, " +
            "coalesce(t.annotation, m.annotation), coalesce(t.image, m.image) " +
            "FROM movie AS m INNER JOIN movie_country AS mc ON m.id = mc.movie_id " +
            "LEFT JOIN (SELECT * FROM tmovie WHERE language_id = :language_id) AS t USING(id) " +
            "WHERE mc.country_id = :country_id;";
    private static final String GET_MOVIES_BY_GENRE_QUERY = "SELECT movie.* FROM movie " +
            "INNER JOIN movie_genre ON movie.id = movie_genre.movie_id WHERE movie_genre.genre_id = :genre_id ";
    private static final String GET_MOVIES_BY_GENRE_NOT_DEFAULT_LANG_QUERY = "SELECT m.id, coalesce(t.name, m.name), m.year, " +
            "coalesce(t.tagline, m.tagline), m.budget, m.premiere, m.lasting, " +
            "coalesce(t.annotation, m.annotation), coalesce(t.image, m.image) " +
            "FROM movie AS m INNER JOIN movie_genre AS mg ON m.id = mg.movie_id " +
            "LEFT JOIN (SELECT * FROM tmovie WHERE language_id = :language_id) AS t USING(id) " +
            "WHERE mg.genre_id = :genre_id";
    private static final String GET_MOVIES_BY_PERSON_QUERY = "SELECT movie.* FROM movie " +
            "INNER JOIN movie_person_relation ON movie.id = movie_person_relation.movie_id WHERE " +
            "movie_person_relation.person_id = :person_id AND movie_person_relation.relation_type = :relation_type";
    private static final String GET_MOVIES_BY_PERSON_NOT_DEFAULT_LANG_QUERY = "SELECT m.id, coalesce(t.name, m.name), m.year, " +
            "coalesce(t.tagline, m.tagline), m.budget, m.premiere, m.lasting, " +
            "coalesce(t.annotation, m.annotation), coalesce(t.image, m.image) " +
            "FROM movie AS m INNER JOIN movie_person_relation AS mpr ON m.id = mpr.movie_id " +
            "LEFT JOIN (SELECT * FROM tmovie WHERE language_id = :language_id) AS t USING(id) " +
            "WHERE mpr.person_id = :person_id AND mpr.relation_type = :relation_type;";
    private static final String GET_RECENT_ADDED_MOVIES_QUERY = "SELECT * FROM movie ORDER BY id DESC LIMIT ";
    private static final String GET_RECENT_ADDED_MOVIES_NOT_DEFAULT_LANG_QUERY = "SELECT m.id, " +
            "coalesce(t.name, m.name), m.year, coalesce(t.tagline, m.tagline), " +
            "m.budget, m.premiere, m.lasting, coalesce(t.annotation, m.annotation), " +
            "coalesce(t.image, m.image) FROM movie AS m LEFT JOIN (SELECT * FROM tmovie " +
            "WHERE language_id = :language_id) AS t USING(id) ORDER BY id DESC LIMIT ";
    private static final String GET_MOVIES_BY_CRITERIA_HEAD_QUERY = "SELECT DISTINCT m.* FROM movie AS m ";
    private static final String GET_MOVIES_BY_CRITERIA_NOT_DEFAULT_LANGUAGE_HEAD_QUERY = "SELECT DISTINCT m.id, coalesce(t.name, m.name), m.year, " +
            "coalesce(t.tagline, m.tagline), m.budget, m.premiere, m.lasting, " +
            "coalesce(t.annotation, m.annotation), coalesce(t.image, m.image) " +
            "FROM movie AS m ";
    private static final String GET_MOVIES_BY_CRITERIA_MG_JOIN_QUERY = "INNER JOIN movie_genre AS mg ON m.id = mg.movie_id ";
    private static final String GET_MOVIES_BY_CRITERIA_MC_JOIN_QUERY = "INNER JOIN movie_country AS mc ON m.id = mc.movie_id ";
    private static final String GET_MOVIES_BY_CRITERIA_RATING_JOIN_QUERY = "INNER JOIN rating AS r ON m.id = r.movie_id ";
    private static final String GET_MOVIES_BY_CRITERIA_TRANSLATE_JOIN_QUERY_FIRST_PART = "LEFT JOIN (SELECT * FROM tmovie WHERE language_id = '";
    private static final String GET_MOVIES_BY_CRITERIA_TRANSLATE_JOIN_QUERY_SECOND_PART = "') AS t USING(id) ";
    private static final String GET_MOVIES_BY_CRITERIA_NAME_CRITERIA_QUERY_FIRST_PART = "WHERE m.name LIKE '%";
    private static final String GET_MOVIES_BY_CRITERIA_NAME_CRITERIA_QUERY_SECOND_PART = "%' ";
    private static final String GET_MOVIES_BY_CRITERIA_NAME_CRITERIA_NOT_DEFAULT_LANGUAGE_QUERY_FIRST_PART = "WHERE (m.name LIKE '%";
    private static final String GET_MOVIES_BY_CRITERIA_NAME_CRITERIA_NOT_DEFAULT_LANGUAGE_QUERY_SECOND_PART = "%' OR t.name LIKE '%";
    private static final String GET_MOVIES_BY_CRITERIA_NAME_CRITERIA_NOT_DEFAULT_LANGUAGE_QUERY_THIRD_PART = "%') ";
    private static final String WHERE_CRITERIA = "WHERE";
    private static final String AND_CRITERIA = "AND";
    private static final String HAVING_CRITERIA = "HAVING";
    private static final String SPACE_SEPARATOR = " ";
    private static final String COMA_SEPARATOR = ",";
    private static final String CLOSING_BRACKET = ") ";
    private static final String GET_MOVIES_BY_CRITERIA_MIN_YEAR_CRITERIA_QUERY = " m.year > ";
    private static final String GET_MOVIES_BY_CRITERIA_MAX_YEAR_CRITERIA_QUERY = " m.year < ";
    private static final String GET_MOVIES_BY_CRITERIA_GENRES_LIST_CRITERIA_QUERY = " mg.genre_id IN (";
    private static final String GET_MOVIES_BY_CRITERIA_COUNTRIES_LIST_CRITERIA_QUERY = " mc.country_id IN (";
    private static final String GET_MOVIES_BY_CRITERIA_GROUP_BY_ID_QUERY = "GROUP BY r.movie_id ";
    private static final String GET_MOVIES_BY_CRITERIA_MIN_RATING_CRITERIA_QUERY = "HAVING AVG(r.value) > ";
    private static final String GET_MOVIES_BY_CRITERIA_MAX_RATING_CRITERIA_QUERY = " AVG(r.value) < ";
    private static final String LIMIT_QUERY = "LIMIT ";
    private static final String GET_MOVIES_COUNT_BY_CRITERIA_HEAD_QUERY = "SELECT COUNT(*) FROM (SELECT DISTINCT m.* FROM movie AS m ";
    private static final String GET_MOVIES_COUNT_BY_CRITERIA_TAIL_QUERY = ") AS c";

    private static final String ID_COLUMN_NAME = "id";
    private static final String NAME_COLUMN_NAME = "name";
    private static final String YEAR_COLUMN_NAME = "year";
    private static final String TAGLINE_COLUMN_NAME = "tagline";
    private static final String BUDGET_COLUMN_NAME = "budget";
    private static final String PREMIERE_COLUMN_NAME = "premiere";
    private static final String LASTING_COLUMN_NAME = "lasting";
    private static final String ANNOTATION_COLUMN_NAME = "annotation";
    private static final String IMAGE_COLUMN_NAME = "image";
    private static final String LANGUAGE_ID_COLUMN_NAME = "language_id";
    private static final String COUNTRY_ID_PARAM_NAME = "country_id";
    private static final String GENRE_ID_PARAM_NAME = "genre_id";
    private static final String PERSON_ID_PARAM_NAME = "person_id";
    private static final String RELATION_TYPE_PARAM_NAME = "relation_type";

    private static final String DEFAULT_LANGUAGE_ID = "EN";

    private NamedParameterJdbcTemplate jdbcTemplate;
    private MovieMapper movieMapper = new MovieMapper();
    private KeyHolder keyHolder = new GeneratedKeyHolder();

    /**
     * Adds a movie to the data storage (in the default language).
     *
     * @param movie a movie object
     */
    @Override
    public void addMovie(Movie movie) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(NAME_COLUMN_NAME, movie.getName())
                .addValue(YEAR_COLUMN_NAME, movie.getYear())
                .addValue(TAGLINE_COLUMN_NAME, movie.getTagline())
                .addValue(BUDGET_COLUMN_NAME, movie.getBudget())
                .addValue(PREMIERE_COLUMN_NAME, new Date(movie.getPremiere().getTime()))
                .addValue(LASTING_COLUMN_NAME, movie.getLasting())
                .addValue(ANNOTATION_COLUMN_NAME, movie.getAnnotation())
                .addValue(IMAGE_COLUMN_NAME, movie.getImage());

        jdbcTemplate.update(ADD_MOVIE_QUERY, sqlParameterSource, keyHolder, new String[] {ID_COLUMN_NAME});

        movie.setId(keyHolder.getKey().intValue());
    }

    /**
     * Updates a movie or adds/updates a localization of a movie in the data storage.
     *
     * If the languageId argument is an id of the default language of the application, then it updates
     * a movie. If the language argument is an id of the different language (not default) then it
     * adds/updates a localization of a movie (it based on the fact of existence of a localization:
     * if it doesn't exist then it will be added, otherwise a localization will be updated).
     * @param movie a movie object
     * @param languageId a language id like 'EN', "RU' etc.
     */
    @Override
    public void updateMovie(Movie movie, String languageId) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(NAME_COLUMN_NAME, movie.getName())
                .addValue(TAGLINE_COLUMN_NAME, movie.getTagline())
                .addValue(ANNOTATION_COLUMN_NAME, movie.getAnnotation())
                .addValue(IMAGE_COLUMN_NAME, movie.getImage())
                .addValue(ID_COLUMN_NAME, movie.getId());

        if(languageId.equals(DEFAULT_LANGUAGE_ID)) {
            sqlParameterSource = sqlParameterSource
                    .addValue(YEAR_COLUMN_NAME, movie.getYear())
                    .addValue(BUDGET_COLUMN_NAME, movie.getBudget())
                    .addValue(PREMIERE_COLUMN_NAME, new Date(movie.getPremiere().getTime()))
                    .addValue(LASTING_COLUMN_NAME, movie.getLasting());
            jdbcTemplate.update(UPDATE_MOVIE_QUERY, sqlParameterSource);
        } else {
            SqlParameterSource testingSqlParameterSource = new MapSqlParameterSource()
                    .addValue(ID_COLUMN_NAME, movie.getId())
                    .addValue(LANGUAGE_ID_COLUMN_NAME, languageId);
            List<Movie> movies = jdbcTemplate.query(TMOVIE_CHECK_QUERY, testingSqlParameterSource, movieMapper);
            if(!movies.isEmpty()) {
                jdbcTemplate.update(UPDATE_TMOVIE_QUERY, sqlParameterSource.addValue(LANGUAGE_ID_COLUMN_NAME, languageId));
            } else {
                jdbcTemplate.update(ADD_TMOVIE_QUERY, sqlParameterSource.addValue(LANGUAGE_ID_COLUMN_NAME, languageId));
            }
        }
    }

    /**
     * Deletes a movie from the data storage (with all of the localizations).
     *
     * @param id an id of the deleting movie
     */
    @Override
    public void deleteMovie(int id) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(ID_COLUMN_NAME, id);

        jdbcTemplate.update(DELETE_MOVIE_QUERY, sqlParameterSource);
    }

    /**
     * Returns all the movies from the data storage.
     *
     * @param languageId a language id like 'EN', "RU' etc.
     * @return all the movies
     */
    @Override
    public List<Movie> getAllMovies(String languageId) {
        if(languageId.equals(DEFAULT_LANGUAGE_ID)) {
            return jdbcTemplate.query(GET_ALL_MOVIES_QUERY, movieMapper);
        } else {
            SqlParameterSource sqlParameterSource = new MapSqlParameterSource(LANGUAGE_ID_COLUMN_NAME, languageId);
            return jdbcTemplate.query(GET_ALL_MOVIES_NOT_DEFAULT_LANG_QUERY, sqlParameterSource, movieMapper);
        }
    }

    /**
     * Returns a movie by id from the data storage.
     *
     * @param id an id of the needed movie
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a movie by id
     */
    @Override
    public Movie getMovieById(int id, String languageId) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(ID_COLUMN_NAME, id);

        List<Movie> movies;
        if(languageId.equals(DEFAULT_LANGUAGE_ID)) {
            movies = jdbcTemplate.query(GET_MOVIE_BY_ID_QUERY, sqlParameterSource, movieMapper);
        } else {
            movies = jdbcTemplate.query(GET_MOVIE_BY_ID_NOT_DEFAULT_LANG_QUERY, sqlParameterSource.addValue(LANGUAGE_ID_COLUMN_NAME, languageId), movieMapper);
        }

        return movies.isEmpty() ? null : movies.get(0);
    }

    /**
     * Returns a movies belonging to the genre from the data storage.
     *
     * @param genreId an id of the genre
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a movies belonging to the genre
     */
    @Override
    public List<Movie> getMoviesByGenre(int genreId, String languageId) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(GENRE_ID_PARAM_NAME, genreId);

        if(languageId.equals(DEFAULT_LANGUAGE_ID)) {
            return jdbcTemplate.query(GET_MOVIES_BY_GENRE_QUERY, sqlParameterSource, movieMapper);
        } else {
            return jdbcTemplate.query(GET_MOVIES_BY_GENRE_NOT_DEFAULT_LANG_QUERY, sqlParameterSource.addValue(LANGUAGE_ID_COLUMN_NAME, languageId), movieMapper);
        }
    }

    /**
     * Returns a movies belonging to the country from the data storage.
     *
     * @param countryId an id of the country
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a movies belonging to the country
     */
    @Override
    public List<Movie> getMoviesByCountry(int countryId, String languageId) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(COUNTRY_ID_PARAM_NAME, countryId);

        if(languageId.equals(DEFAULT_LANGUAGE_ID)) {
            return jdbcTemplate.query(GET_MOVIES_BY_COUNTRY_QUERY, sqlParameterSource, movieMapper);
        } else {
            return jdbcTemplate.query(GET_MOVIES_BY_COUNTRY_NOT_DEFAULT_LANG_QUERY, sqlParameterSource.addValue(LANGUAGE_ID_COLUMN_NAME, languageId), movieMapper);
        }
    }

    /**
     * Returns a movies in which the person took part in the certain role from the data storage.
     *
     * @param personId an id of the person
     * @param relationType an id of the relation type (role)
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a movies in which the person took part in the certain role
     */
    @Override
    public List<Movie> getMoviesByPersonAndRelationType(int personId, int relationType, String languageId) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(PERSON_ID_PARAM_NAME, personId)
                .addValue(RELATION_TYPE_PARAM_NAME, relationType);

        if(languageId.equals(DEFAULT_LANGUAGE_ID)) {
            return jdbcTemplate.query(GET_MOVIES_BY_PERSON_QUERY, sqlParameterSource, movieMapper);
        } else {
            return jdbcTemplate.query(GET_MOVIES_BY_PERSON_NOT_DEFAULT_LANG_QUERY, sqlParameterSource.addValue(LANGUAGE_ID_COLUMN_NAME, languageId), movieMapper);
        }
    }

    /**
     * Returns a recent added movies from the data storage.
     *
     * @param amount a needed amount of movies
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a recent added movies
     */
    @Override
    public List<Movie> getRecentAddedMovies(int amount, String languageId) {
        if(languageId.equals(DEFAULT_LANGUAGE_ID)) {
            return jdbcTemplate.query(GET_RECENT_ADDED_MOVIES_QUERY + amount, movieMapper);
        } else {
            SqlParameterSource sqlParameterSource = new MapSqlParameterSource(LANGUAGE_ID_COLUMN_NAME, languageId);
            return jdbcTemplate.query(GET_RECENT_ADDED_MOVIES_NOT_DEFAULT_LANG_QUERY + amount, sqlParameterSource, movieMapper);
        }
    }

    /**
     * Returns a movies matching to the criteria from the data storage.
     *
     * @param criteria a MovieCriteria object
     * @param from a starting position in the movies list (starting from 0)
     * @param amount a needed amount of movies
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a movies matching to the criteria
     */
    @Override
    public List<Movie> getMoviesByCriteria(MovieCriteria criteria, int from, int amount, String languageId) {
        StringBuilder query = new StringBuilder();
        if(languageId.equals(DEFAULT_LANGUAGE_ID)){
            query.append(GET_MOVIES_BY_CRITERIA_HEAD_QUERY);
        }
        else {
            query.append(GET_MOVIES_BY_CRITERIA_NOT_DEFAULT_LANGUAGE_HEAD_QUERY);
        }
        if (criteria.getGenreIds() != null) {
            query.append(GET_MOVIES_BY_CRITERIA_MG_JOIN_QUERY);
        }
        if (criteria.getCountryIds() != null) {
            query.append(GET_MOVIES_BY_CRITERIA_MC_JOIN_QUERY);
        }
        if(criteria.getMinRating() != 0 || criteria.getMaxRating() != 0){
            query.append(GET_MOVIES_BY_CRITERIA_RATING_JOIN_QUERY);
        }
        if(!languageId.equals(DEFAULT_LANGUAGE_ID)){
            query.append(GET_MOVIES_BY_CRITERIA_TRANSLATE_JOIN_QUERY_FIRST_PART);
            query.append(languageId);
            query.append(GET_MOVIES_BY_CRITERIA_TRANSLATE_JOIN_QUERY_SECOND_PART);
        }

        boolean atLeastOneWhereCriteria = false;
        if (criteria.getName() != null) {
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                query.append(GET_MOVIES_BY_CRITERIA_NAME_CRITERIA_QUERY_FIRST_PART);
                query.append(criteria.getName());
                query.append(GET_MOVIES_BY_CRITERIA_NAME_CRITERIA_QUERY_SECOND_PART);
            }
            else {
                query.append(GET_MOVIES_BY_CRITERIA_NAME_CRITERIA_NOT_DEFAULT_LANGUAGE_QUERY_FIRST_PART);
                query.append(criteria.getName());
                query.append(GET_MOVIES_BY_CRITERIA_NAME_CRITERIA_NOT_DEFAULT_LANGUAGE_QUERY_SECOND_PART);
                query.append(criteria.getName());
                query.append(GET_MOVIES_BY_CRITERIA_NAME_CRITERIA_NOT_DEFAULT_LANGUAGE_QUERY_THIRD_PART);
            }
            atLeastOneWhereCriteria = true;
        }
        if (criteria.getMinYear() != 0) {
            query.append(atLeastOneWhereCriteria ? AND_CRITERIA : WHERE_CRITERIA);
            query.append(GET_MOVIES_BY_CRITERIA_MIN_YEAR_CRITERIA_QUERY);
            query.append(criteria.getMinYear());
            query.append(SPACE_SEPARATOR);
            atLeastOneWhereCriteria = true;
        }
        if (criteria.getMaxYear() != 0) {
            query.append(atLeastOneWhereCriteria ? AND_CRITERIA : WHERE_CRITERIA);
            query.append(GET_MOVIES_BY_CRITERIA_MAX_YEAR_CRITERIA_QUERY);
            query.append(criteria.getMaxYear());
            query.append(SPACE_SEPARATOR);
            atLeastOneWhereCriteria = true;
        }
        if (criteria.getGenreIds() != null) {
            query.append(atLeastOneWhereCriteria ? AND_CRITERIA : WHERE_CRITERIA);
            query.append(GET_MOVIES_BY_CRITERIA_GENRES_LIST_CRITERIA_QUERY);
            for (Integer integer : criteria.getGenreIds()) {
                query.append(integer);
                query.append(COMA_SEPARATOR);
            }
            query.deleteCharAt(query.length() - 1);
            query.append(CLOSING_BRACKET);
            atLeastOneWhereCriteria = true;
        }
        if (criteria.getCountryIds() != null) {
            query.append(atLeastOneWhereCriteria ? AND_CRITERIA : WHERE_CRITERIA);
            query.append(GET_MOVIES_BY_CRITERIA_COUNTRIES_LIST_CRITERIA_QUERY);
            for (Integer integer : criteria.getCountryIds()) {
                query.append(integer);
                query.append(COMA_SEPARATOR);
            }
            query.deleteCharAt(query.length() - 1);
            query.append(CLOSING_BRACKET);
        }
        if(criteria.getMinRating() != 0 || criteria.getMaxRating() != 0){
            query.append(GET_MOVIES_BY_CRITERIA_GROUP_BY_ID_QUERY);
            boolean atLeastOneHavingCriteria = false;
            if(criteria.getMinRating() != 0){
                query.append(GET_MOVIES_BY_CRITERIA_MIN_RATING_CRITERIA_QUERY);
                query.append(criteria.getMinRating());
                query.append(SPACE_SEPARATOR);
                atLeastOneHavingCriteria = true;
            }
            if(criteria.getMaxRating() != 0){
                query.append(atLeastOneHavingCriteria ? AND_CRITERIA : HAVING_CRITERIA);
                query.append(GET_MOVIES_BY_CRITERIA_MAX_RATING_CRITERIA_QUERY);
                query.append(criteria.getMaxRating());
                query.append(SPACE_SEPARATOR);
            }
        }
        if(amount != 0){
            query.append(LIMIT_QUERY);
            query.append(from);
            query.append(COMA_SEPARATOR);
            query.append(SPACE_SEPARATOR);
            query.append(amount);
            query.append(SPACE_SEPARATOR);
        }

        return jdbcTemplate.query(query.toString(), movieMapper);
    }

    /**
     * Returns an amount of movies matching to the criteria in the data storage.
     *
     * @param criteria a MovieCriteria object
     * @param languageId a language id like 'EN', "RU' etc.
     * @return an amount of movies matching to the criteria
     */
    @Override
    public int getMoviesCountByCriteria(MovieCriteria criteria, String languageId) {
        StringBuilder query = new StringBuilder();
        query.append(GET_MOVIES_COUNT_BY_CRITERIA_HEAD_QUERY);

        if (criteria.getGenreIds() != null) {
            query.append(GET_MOVIES_BY_CRITERIA_MG_JOIN_QUERY);
        }
        if (criteria.getCountryIds() != null) {
            query.append(GET_MOVIES_BY_CRITERIA_MC_JOIN_QUERY);
        }
        if(criteria.getMinRating() != 0 || criteria.getMaxRating() != 0){
            query.append(GET_MOVIES_BY_CRITERIA_RATING_JOIN_QUERY);
        }
        if(!languageId.equals(DEFAULT_LANGUAGE_ID)){
            query.append(GET_MOVIES_BY_CRITERIA_TRANSLATE_JOIN_QUERY_FIRST_PART);
            query.append(languageId);
            query.append(GET_MOVIES_BY_CRITERIA_TRANSLATE_JOIN_QUERY_SECOND_PART);
        }

        boolean atLeastOneWhereCriteria = false;
        if (criteria.getName() != null) {
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                query.append(GET_MOVIES_BY_CRITERIA_NAME_CRITERIA_QUERY_FIRST_PART);
                query.append(criteria.getName());
                query.append(GET_MOVIES_BY_CRITERIA_NAME_CRITERIA_QUERY_SECOND_PART);
            }
            else {
                query.append(GET_MOVIES_BY_CRITERIA_NAME_CRITERIA_NOT_DEFAULT_LANGUAGE_QUERY_FIRST_PART);
                query.append(criteria.getName());
                query.append(GET_MOVIES_BY_CRITERIA_NAME_CRITERIA_NOT_DEFAULT_LANGUAGE_QUERY_SECOND_PART);
                query.append(criteria.getName());
                query.append(GET_MOVIES_BY_CRITERIA_NAME_CRITERIA_NOT_DEFAULT_LANGUAGE_QUERY_THIRD_PART);
            }
            atLeastOneWhereCriteria = true;
        }
        if (criteria.getMinYear() != 0) {
            query.append(atLeastOneWhereCriteria ? AND_CRITERIA : WHERE_CRITERIA);
            query.append(GET_MOVIES_BY_CRITERIA_MIN_YEAR_CRITERIA_QUERY);
            query.append(criteria.getMinYear());
            query.append(SPACE_SEPARATOR);
            atLeastOneWhereCriteria = true;
        }
        if (criteria.getMaxYear() != 0) {
            query.append(atLeastOneWhereCriteria ? AND_CRITERIA : WHERE_CRITERIA);
            query.append(GET_MOVIES_BY_CRITERIA_MAX_YEAR_CRITERIA_QUERY);
            query.append(criteria.getMaxYear());
            query.append(SPACE_SEPARATOR);
            atLeastOneWhereCriteria = true;
        }
        if (criteria.getGenreIds() != null) {
            query.append(atLeastOneWhereCriteria ? AND_CRITERIA : WHERE_CRITERIA);
            query.append(GET_MOVIES_BY_CRITERIA_GENRES_LIST_CRITERIA_QUERY);
            for (Integer integer : criteria.getGenreIds()) {
                query.append(integer);
                query.append(COMA_SEPARATOR);
            }
            query.deleteCharAt(query.length() - 1);
            query.append(CLOSING_BRACKET);
            atLeastOneWhereCriteria = true;
        }
        if (criteria.getCountryIds() != null) {
            query.append(atLeastOneWhereCriteria ? AND_CRITERIA : WHERE_CRITERIA);
            query.append(GET_MOVIES_BY_CRITERIA_COUNTRIES_LIST_CRITERIA_QUERY);
            for (Integer integer : criteria.getCountryIds()) {
                query.append(integer);
                query.append(COMA_SEPARATOR);
            }
            query.deleteCharAt(query.length() - 1);
            query.append(CLOSING_BRACKET);
        }
        if(criteria.getMinRating() != 0 || criteria.getMaxRating() != 0){
            query.append(GET_MOVIES_BY_CRITERIA_GROUP_BY_ID_QUERY);
            boolean atLeastOneHavingCriteria = false;
            if(criteria.getMinRating() != 0){
                query.append(GET_MOVIES_BY_CRITERIA_MIN_RATING_CRITERIA_QUERY);
                query.append(criteria.getMinRating());
                query.append(SPACE_SEPARATOR);
                atLeastOneHavingCriteria = true;
            }
            if(criteria.getMaxRating() != 0){
                query.append(atLeastOneHavingCriteria ? AND_CRITERIA : HAVING_CRITERIA);
                query.append(GET_MOVIES_BY_CRITERIA_MAX_RATING_CRITERIA_QUERY);
                query.append(criteria.getMaxRating());
                query.append(SPACE_SEPARATOR);
            }
        }
        query.append(GET_MOVIES_COUNT_BY_CRITERIA_TAIL_QUERY);

        return jdbcTemplate.queryForObject(query.toString(), new HashMap<>(), Integer.TYPE);
    }

    private class MovieMapper implements RowMapper<Movie> {
        @Override
        public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
            Movie movie = new Movie();
            movie.setId(rs.getInt(ID_COLUMN_NAME));
            movie.setName(rs.getString(NAME_COLUMN_NAME));
            movie.setYear(rs.getInt(YEAR_COLUMN_NAME));
            movie.setTagline(rs.getString(TAGLINE_COLUMN_NAME));
            movie.setBudget(rs.getInt(BUDGET_COLUMN_NAME));
            movie.setPremiere(rs.getDate(PREMIERE_COLUMN_NAME));
            movie.setLasting(rs.getInt(LASTING_COLUMN_NAME));
            movie.setAnnotation(rs.getString(ANNOTATION_COLUMN_NAME));
            movie.setImage(rs.getString(IMAGE_COLUMN_NAME));
            return movie;
        }
    }
}
