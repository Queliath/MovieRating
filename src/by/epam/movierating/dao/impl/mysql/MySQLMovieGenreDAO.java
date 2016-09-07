package by.epam.movierating.dao.impl.mysql;

import by.epam.movierating.dao.interfaces.MovieGenreDAO;
import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPool;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPoolException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Владислав on 19.06.2016.
 */
public class MySQLMovieGenreDAO implements MovieGenreDAO {
    private static final String ADD_MOVIE_TO_GENRE_QUERY = "INSERT INTO movie_genre " +
            "(movie_id, genre_id) VALUES (?, ?)";
    private static final String DELETE_MOVIE_FROM_GENRE_QUERY = "DELETE FROM movie_genre WHERE movie_id = ? AND genre_id = ?";

    @Override
    public void addMovieToGenre(int movieId, int genreId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            statement = connection.prepareStatement(ADD_MOVIE_TO_GENRE_QUERY);
            statement.setInt(1, movieId);
            statement.setInt(2, genreId);

            statement.executeUpdate();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Exception in DAO layer when adding movie to genre", e);
        } finally {
            try {
                if (connection != null) {
                    if (statement != null) {
                        statement.close();
                    }
                    mySQLConnectionPool.freeConnection(connection);
                }
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public void deleteMovieFromGenre(int movieId, int genreId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            statement = connection.prepareStatement(DELETE_MOVIE_FROM_GENRE_QUERY);
            statement.setInt(1, movieId);
            statement.setInt(2, genreId);

            statement.executeUpdate();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Exception in DAO layer when deleting movie from genre", e);
        } finally {
            try {
                if (connection != null) {
                    if (statement != null) {
                        statement.close();
                    }
                    mySQLConnectionPool.freeConnection(connection);
                }
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }
}
