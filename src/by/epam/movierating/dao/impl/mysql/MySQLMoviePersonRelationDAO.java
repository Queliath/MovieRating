package by.epam.movierating.dao.impl.mysql;

import by.epam.movierating.dao.interfaces.MoviePersonRelationDAO;
import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPool;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPoolException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Владислав on 19.06.2016.
 */
public class MySQLMoviePersonRelationDAO implements MoviePersonRelationDAO {
    @Override
    public void addMovieToPersonWithRelation(int movieId, int personId, int relationType) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO movie_person_relation " +
                    "(movie_id, person_id, relation_type) VALUES (?, ?, ?)");
            statement.setInt(1, movieId);
            statement.setInt(2, personId);
            statement.setInt(3, relationType);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Exception in DAO layer when adding movie to person with relation", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public void deleteMovieFromPersonWithRelation(int movieId, int personId, int relationType) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM movie_person_relation " +
                    "WHERE movie_id = ? AND person_id = ? AND relation_type = ?");
            statement.setInt(1, movieId);
            statement.setInt(2, personId);
            statement.setInt(3, relationType);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Exception in DAO layer when deleting movie from person with relation", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }
}
