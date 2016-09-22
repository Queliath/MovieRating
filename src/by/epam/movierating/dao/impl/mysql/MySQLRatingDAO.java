package by.epam.movierating.dao.impl.mysql;

import by.epam.movierating.dao.inter.RatingDAO;
import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPool;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPoolException;
import by.epam.movierating.domain.Rating;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides a DAO-logic for the Rating entity for the MySQL Database.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class MySQLRatingDAO implements RatingDAO {
    private static final String ADD_RATING_QUERY = "INSERT INTO rating " +
            "(movie_id, user_id, value) VALUES (?, ?, ?)";
    private static final String UPDATE_RATING_QUERY = "UPDATE rating " +
            "SET value = ? WHERE movie_id = ? AND user_id = ?";
    private static final String DELETE_RATING_QUERY = "DELETE FROM rating WHERE movie_id = ? AND user_id = ?";
    private static final String GET_RATING_BY_MOVIE_AND_USER_QUERY = "SELECT * FROM rating WHERE movie_id = ? AND user_id = ?";
    private static final String GET_AVERAGE_RATING_BY_MOVIE_QUERY = "SELECT AVG(value) " +
            "FROM rating WHERE movie_id = ? GROUP BY movie_id";
    private static final String GET_RATINGS_BY_USER_QUERY = "SELECT * FROM rating WHERE user_id = ?";

    /**
     * Adds a rating to the data storage.
     *
     * @param rating a rating object
     * @throws DAOException
     */
    @Override
    public void addRating(Rating rating) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            statement = connection.prepareStatement(ADD_RATING_QUERY);
            statement.setInt(1, rating.getMovieId());
            statement.setInt(2, rating.getUserId());
            statement.setInt(3, rating.getValue());

            statement.executeUpdate();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Exception in DAO layer when adding rating", e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            } finally {
                if (connection != null){
                    try {
                        mySQLConnectionPool.freeConnection(connection);
                    } catch (SQLException | MySQLConnectionPoolException e) {
                        throw new DAOException("Cannot free a connection from Connection Pool", e);
                    }
                }
            }
        }
    }

    /**
     * Updates a rating in the data storage.
     *
     * @param rating a rating object
     * @throws DAOException
     */
    @Override
    public void updateRating(Rating rating) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            statement = connection.prepareStatement(UPDATE_RATING_QUERY);
            statement.setInt(1, rating.getValue());
            statement.setInt(2, rating.getMovieId());
            statement.setInt(3, rating.getUserId());

            statement.executeUpdate();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Exception in DAO layer when updating rating", e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            } finally {
                if (connection != null){
                    try {
                        mySQLConnectionPool.freeConnection(connection);
                    } catch (SQLException | MySQLConnectionPoolException e) {
                        throw new DAOException("Cannot free a connection from Connection Pool", e);
                    }
                }
            }
        }
    }

    /**
     * Deletes a rating from the data storage.
     *
     * @param movieId an id of the movie to which the rating belongs
     * @param userId an id of the user to which the rating belongs
     * @throws DAOException
     */
    @Override
    public void deleteRating(int movieId, int userId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            statement = connection.prepareStatement(DELETE_RATING_QUERY);
            statement.setInt(1, movieId);
            statement.setInt(2, userId);

            statement.executeUpdate();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Exception in DAO layer when deleting rating", e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            } finally {
                if (connection != null){
                    try {
                        mySQLConnectionPool.freeConnection(connection);
                    } catch (SQLException | MySQLConnectionPoolException e) {
                        throw new DAOException("Cannot free a connection from Connection Pool", e);
                    }
                }
            }
        }
    }

    /**
     * Returns a rating belonging to the movie and the user.
     *
     * @param movieId an id of the movie
     * @param userId an id of the user
     * @return a rating belonging to the movie and the user
     * @throws DAOException
     */
    @Override
    public Rating getRatingByMovieAndUser(int movieId, int userId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            statement = connection.prepareStatement(GET_RATING_BY_MOVIE_AND_USER_QUERY);
            statement.setInt(1, movieId);
            statement.setInt(2, userId);
            ResultSet resultSet = statement.executeQuery();

            Rating rating = null;
            if(resultSet.next()){
                rating = new Rating();
                rating.setMovieId(resultSet.getInt(1));
                rating.setUserId(resultSet.getInt(2));
                rating.setValue(resultSet.getInt(3));
            }
            return rating;
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Exception in DAO layer when getting rating", e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            } finally {
                if (connection != null){
                    try {
                        mySQLConnectionPool.freeConnection(connection);
                    } catch (SQLException | MySQLConnectionPoolException e) {
                        throw new DAOException("Cannot free a connection from Connection Pool", e);
                    }
                }
            }
        }
    }

    /**
     * Returns an average rating of the movie.
     *
     * @param movieId an id of the movie
     * @return an average rating of the movie
     * @throws DAOException
     */
    @Override
    public double getAverageRatingByMovie(int movieId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            statement = connection.prepareStatement(GET_AVERAGE_RATING_BY_MOVIE_QUERY);
            statement.setInt(1, movieId);
            ResultSet resultSet = statement.executeQuery();

            double averageRating = 0.0;
            if(resultSet.next()){
                averageRating = resultSet.getDouble(1);
            }
            return averageRating;
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Exception in DAO layer when getting rating", e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            } finally {
                if (connection != null){
                    try {
                        mySQLConnectionPool.freeConnection(connection);
                    } catch (SQLException | MySQLConnectionPoolException e) {
                        throw new DAOException("Cannot free a connection from Connection Pool", e);
                    }
                }
            }
        }
    }

    /**
     * Returns a ratings belonging to the user.
     *
     * @param userId an id of the user
     * @return a ratings belonging to the user
     * @throws DAOException
     */
    @Override
    public List<Rating> getRatingsByUser(int userId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            statement = connection.prepareStatement(GET_RATINGS_BY_USER_QUERY);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            List<Rating> ratingsByUser = new ArrayList<>();
            while(resultSet.next()){
                Rating rating = new Rating();
                rating.setMovieId(resultSet.getInt(1));
                rating.setUserId(resultSet.getInt(2));
                rating.setValue(resultSet.getInt(3));

                ratingsByUser.add(rating);
            }
            return ratingsByUser;
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Exception in DAO layer when getting rating", e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            } finally {
                if (connection != null){
                    try {
                        mySQLConnectionPool.freeConnection(connection);
                    } catch (SQLException | MySQLConnectionPoolException e) {
                        throw new DAOException("Cannot free a connection from Connection Pool", e);
                    }
                }
            }
        }
    }
}
