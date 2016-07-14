package by.epam.movierating.dao.impl.mysql;

import by.epam.movierating.dao.interfaces.MovieGenreDAO;
import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Владислав on 19.06.2016.
 */
public class MySQLMovieGenreDAO implements MovieGenreDAO {
    @Override
    public void addMovieToGenre(int movieId, int genreId) throws DAOException {
        try {
            MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
            Connection connection = mySQLConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO movie_genre " +
                    "(movie_id, genre_id) VALUES (?, ?)");
            statement.setInt(1, movieId);
            statement.setInt(2, genreId);

            statement.executeUpdate();

            mySQLConnectionPool.freeConnection(connection);

        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            throw new DAOException("Exception in DAO layer when adding movie to genre", e);
        }
    }

    @Override
    public void deleteMovieFromGenre(int movieId, int genreId) throws DAOException {
        try {
            MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
            Connection connection = mySQLConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM movie_genre WHERE movie_id = ? AND genre_id = ?");
            statement.setInt(1, movieId);
            statement.setInt(2, genreId);

            statement.executeUpdate();

            mySQLConnectionPool.freeConnection(connection);

        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            throw new DAOException("Exception in DAO layer when deleting movie from genre", e);
        }
    }
}
