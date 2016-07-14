package by.epam.movierating.dao.impl.mysql;

import by.epam.movierating.dao.interfaces.MovieCountryDAO;
import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Владислав on 19.06.2016.
 */
public class MySQLMovieCountryDAO implements MovieCountryDAO {
    @Override
    public void addMovieToCountry(int movieId, int countryId) throws DAOException {
        try {
            MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
            Connection connection = mySQLConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO movie_country " +
                    "(movie_id, country_id) VALUES (?, ?)");
            statement.setInt(1, movieId);
            statement.setInt(2, countryId);

            statement.executeUpdate();

            mySQLConnectionPool.freeConnection(connection);

        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            throw new DAOException("Exception in DAO layer when adding movie to country", e);
        }
    }

    @Override
    public void deleteMovieFromCountry(int movieId, int countryId) throws DAOException {
        try {
            MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
            Connection connection = mySQLConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM movie_country WHERE movie_id = ? AND country_id = ?");
            statement.setInt(1, movieId);
            statement.setInt(2, countryId);

            statement.executeUpdate();

            mySQLConnectionPool.freeConnection(connection);

        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            throw new DAOException("Exception in DAO layer when deleting movie from country", e);
        }
    }
}
