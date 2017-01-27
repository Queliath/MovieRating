package by.bsuir.movierating.dao.impl;

import by.bsuir.movierating.dao.MovieGenreDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * Provides a DAO-logic for the relations between Movie and Genre entities for the MySQL Database.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
@Repository("movieGenreDao")
public class MySQLMovieGenreDAO implements MovieGenreDAO {
    private static final String ADD_MOVIE_TO_GENRE_QUERY = "INSERT INTO movie_genre " +
            "(movie_id, genre_id) VALUES (:movie_id, :genre_id)";
    private static final String DELETE_MOVIE_FROM_GENRE_QUERY = "DELETE FROM movie_genre WHERE movie_id = :movie_id AND genre_id = :genre_id";

    private static final String MOVIE_ID_COLUMN_NAME = "movie_id";
    private static final String GENRE_ID_COLUMN_NAME = "genre_id";

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * Adds a relation between the movie and the genre to the data storage.
     *
     * @param movieId an id of the movie
     * @param genreId an id of the genre
     */
    @Override
    public void addMovieToGenre(int movieId, int genreId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(MOVIE_ID_COLUMN_NAME, movieId)
                .addValue(GENRE_ID_COLUMN_NAME, genreId);

        jdbcTemplate.update(ADD_MOVIE_TO_GENRE_QUERY, sqlParameterSource);
    }

    /**
     * Deletes a relation between the movie and the genre from the data storage.
     *
     * @param movieId an id of the movie
     * @param genreId an id of the genre
     */
    @Override
    public void deleteMovieFromGenre(int movieId, int genreId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(MOVIE_ID_COLUMN_NAME, movieId)
                .addValue(GENRE_ID_COLUMN_NAME, genreId);

        jdbcTemplate.update(DELETE_MOVIE_FROM_GENRE_QUERY, sqlParameterSource);
    }
}
