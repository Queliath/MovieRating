package by.bsuir.movierating.dao.impl;

import by.bsuir.movierating.dao.RatingDAO;
import by.bsuir.movierating.domain.Rating;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Provides a DAO-logic for the Rating entity for the MySQL Database.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
@Repository("ratingDao")
public class MySQLRatingDAO implements RatingDAO {
    private static final String ADD_RATING_QUERY = "INSERT INTO rating " +
            "(movie_id, user_id, value) VALUES (:movie_id, :user_id, :value)";
    private static final String UPDATE_RATING_QUERY = "UPDATE rating " +
            "SET value = :value WHERE movie_id = :movie_id AND user_id = :movie_id";
    private static final String DELETE_RATING_QUERY = "DELETE FROM rating WHERE movie_id = :movie_id AND user_id = :user_id";
    private static final String GET_RATING_BY_MOVIE_AND_USER_QUERY = "SELECT * FROM rating WHERE movie_id = :movie_id AND user_id = :user_id";
    private static final String GET_AVERAGE_RATING_BY_MOVIE_QUERY = "SELECT AVG(value) " +
            "FROM rating WHERE movie_id = :movie_id GROUP BY movie_id";
    private static final String GET_RATINGS_BY_USER_QUERY = "SELECT * FROM rating WHERE user_id = :user_id";

    private static final String MOVIE_ID_COLUMN_NAME = "movie_id";
    private static final String USER_ID_COLUMN_NAME = "user_id";
    private static final String VALUE_ID_COLUMN_NAME = "value";

    private NamedParameterJdbcTemplate jdbcTemplate;
    private RatingMapper ratingMapper = new RatingMapper();

    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * Adds a rating to the data storage.
     *
     * @param rating a rating object
     */
    @Override
    public void addRating(Rating rating) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(MOVIE_ID_COLUMN_NAME, rating.getMovieId())
                .addValue(USER_ID_COLUMN_NAME, rating.getUserId())
                .addValue(VALUE_ID_COLUMN_NAME, rating.getValue());

        jdbcTemplate.update(ADD_RATING_QUERY, sqlParameterSource);
    }

    /**
     * Updates a rating in the data storage.
     *
     * @param rating a rating object
     */
    @Override
    public void updateRating(Rating rating) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(MOVIE_ID_COLUMN_NAME, rating.getMovieId())
                .addValue(USER_ID_COLUMN_NAME, rating.getUserId())
                .addValue(VALUE_ID_COLUMN_NAME, rating.getValue());

        jdbcTemplate.update(UPDATE_RATING_QUERY, sqlParameterSource);
    }

    /**
     * Deletes a rating from the data storage.
     *
     * @param movieId an id of the movie to which the rating belongs
     * @param userId an id of the user to which the rating belongs
     */
    @Override
    public void deleteRating(int movieId, int userId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(MOVIE_ID_COLUMN_NAME, movieId)
                .addValue(USER_ID_COLUMN_NAME, userId);

        jdbcTemplate.update(DELETE_RATING_QUERY, sqlParameterSource);
    }

    /**
     * Returns a rating belonging to the movie and the user.
     *
     * @param movieId an id of the movie
     * @param userId an id of the user
     * @return a rating belonging to the movie and the user
     */
    @Override
    public Rating getRatingByMovieAndUser(int movieId, int userId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(MOVIE_ID_COLUMN_NAME, movieId)
                .addValue(USER_ID_COLUMN_NAME, userId);

        List<Rating> ratings = jdbcTemplate.query(GET_RATING_BY_MOVIE_AND_USER_QUERY, sqlParameterSource, ratingMapper);
        return ratings.isEmpty() ? null : ratings.get(0);
    }

    /**
     * Returns an average rating of the movie.
     *
     * @param movieId an id of the movie
     * @return an average rating of the movie
     */
    @Override
    public double getAverageRatingByMovie(int movieId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(MOVIE_ID_COLUMN_NAME, movieId);

        return jdbcTemplate.queryForObject(GET_AVERAGE_RATING_BY_MOVIE_QUERY, sqlParameterSource, Double.TYPE);
    }

    /**
     * Returns a ratings belonging to the user.
     *
     * @param userId an id of the user
     * @return a ratings belonging to the user
     */
    @Override
    public List<Rating> getRatingsByUser(int userId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(USER_ID_COLUMN_NAME, userId);

        return jdbcTemplate.query(GET_RATINGS_BY_USER_QUERY, sqlParameterSource, ratingMapper);
    }

    private class RatingMapper implements RowMapper<Rating> {
        @Override
        public Rating mapRow(ResultSet rs, int rowNum) throws SQLException {
            Rating rating = new Rating();
            rating.setMovieId(rs.getInt(MOVIE_ID_COLUMN_NAME));
            rating.setUserId(rs.getInt(USER_ID_COLUMN_NAME));
            rating.setValue(rs.getInt(VALUE_ID_COLUMN_NAME));
            return rating;
        }
    }
}
