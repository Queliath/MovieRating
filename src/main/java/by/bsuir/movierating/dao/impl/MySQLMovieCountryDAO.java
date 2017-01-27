package by.bsuir.movierating.dao.impl;

import by.bsuir.movierating.dao.MovieCountryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * Provides a DAO-logic for the relations between Movie and Country entities for the MySQL Database.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
@Repository("movieCountryDao")
public class MySQLMovieCountryDAO implements MovieCountryDAO {
    private static final String ADD_MOVIE_TO_COUNTRY_QUERY = "INSERT INTO movie_country " +
            "(movie_id, country_id) VALUES (:movie_id, :country_id)";
    private static final String DELETE_MOVIE_FORM_COUNTRY_QUERY = "DELETE FROM movie_country WHERE movie_id = :movie_id AND country_id = :country_id";

    private static final String MOVIE_ID_COLUMN_NAME = "movie_id";
    private static final String COUNTRY_ID_COLUMN_NAME = "country_id";

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * Adds a relation between the movie and the country.
     *
     * @param movieId an id of the movie
     * @param countryId an id of the country
     */
    @Override
    public void addMovieToCountry(int movieId, int countryId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(MOVIE_ID_COLUMN_NAME, movieId)
                .addValue(COUNTRY_ID_COLUMN_NAME, countryId);

        jdbcTemplate.update(ADD_MOVIE_TO_COUNTRY_QUERY, sqlParameterSource);
    }

    /**
     * Deletes a relation between the movie and the country.
     *
     * @param movieId an id of the movie
     * @param countryId an id of the country
     */
    @Override
    public void deleteMovieFromCountry(int movieId, int countryId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(MOVIE_ID_COLUMN_NAME, movieId)
                .addValue(COUNTRY_ID_COLUMN_NAME, countryId);

        jdbcTemplate.update(DELETE_MOVIE_FORM_COUNTRY_QUERY, sqlParameterSource);
    }
}
