package by.epam.movierating.dao.impl.mysql;

import by.epam.movierating.dao.inter.MoviePersonRelationDAO;
import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPool;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPoolException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Provides a DAO-logic for the relations between Movie and Person entities for the MySQL Database.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class MySQLMoviePersonRelationDAO implements MoviePersonRelationDAO {
    private static final String ADD_MOVIE_TO_PERSON_QUERY = "INSERT INTO movie_person_relation " +
            "(movie_id, person_id, relation_type) VALUES (?, ?, ?)";
    private static final String DELETE_MOVIE_FORM_PERSON_QUERY = "DELETE FROM movie_person_relation " +
            "WHERE movie_id = ? AND person_id = ? AND relation_type = ?";
    private static final String GET_MOVIES_TOTAL_BY_PERSON_QUERY = "SELECT COUNT(*) FROM " +
            "(SELECT DISTINCT movie_id FROM movie_person_relation WHERE person_id = ?) AS c";

    /**
     * Adds a relation between the movie and the person.
     *
     * @param movieId an id of the movie
     * @param personId an id of the person
     * @param relationType an id of the relation type (role)
     * @throws DAOException
     */
    @Override
    public void addMovieToPersonWithRelation(int movieId, int personId, int relationType) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            statement = connection.prepareStatement(ADD_MOVIE_TO_PERSON_QUERY);
            statement.setInt(1, movieId);
            statement.setInt(2, personId);
            statement.setInt(3, relationType);

            statement.executeUpdate();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Exception in DAO layer when adding movie to person with relation", e);
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
     * Deletes a relation between the movie and the person.
     *
     * @param movieId an id of the movie
     * @param personId an id of the person
     * @param relationType an id of the relation type (role)
     * @throws DAOException
     */
    @Override
    public void deleteMovieFromPersonWithRelation(int movieId, int personId, int relationType) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            statement = connection.prepareStatement(DELETE_MOVIE_FORM_PERSON_QUERY);
            statement.setInt(1, movieId);
            statement.setInt(2, personId);
            statement.setInt(3, relationType);

            statement.executeUpdate();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Exception in DAO layer when deleting movie from person with relation", e);
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
     * Returns an amount of movies in which this person took part.
     *
     * @param personId an id of the person
     * @return an amount of movies in which this person took part
     * @throws DAOException
     */
    @Override
    public int getMoviesTotalByPerson(int personId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            statement = connection.prepareStatement(GET_MOVIES_TOTAL_BY_PERSON_QUERY);
            statement.setInt(1, personId);
            ResultSet resultSet = statement.executeQuery();

            int moviesTotal = 0;
            if(resultSet.next()){
                moviesTotal = resultSet.getInt(1);
            }
            return moviesTotal;
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Exception in DAO layer when getting movies total by person", e);
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
