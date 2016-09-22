package by.epam.movierating.dao.impl.mysql;

import by.epam.movierating.dao.inter.UserDAO;
import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPool;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPoolException;
import by.epam.movierating.domain.User;
import by.epam.movierating.domain.criteria.UserCriteria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides a DAO-logic for the User entity for the MySQL Database.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class MySQLUserDAO implements UserDAO {
    private static final String ADD_USER_QUERY = "INSERT INTO user " +
            "(email, password, first_name, last_name, date_of_registry, photo, rating, status, language_id) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_USER_QUERY = "UPDATE user " +
            "SET email = ?, password = ?, first_name = ?, last_name = ?, date_of_registry = ?, " +
            "photo = ?, rating = ?, status = ?, language_id = ? WHERE id = ?";
    private static final String DELETE_USER_QUERY = "DELETE FROM user WHERE id = ?";
    private static final String GET_ALL_USERS_QUERY = "SELECT * FROM user";
    private static final String GET_USER_BY_ID_QUERY = "SELECT * FROM user WHERE id = ?";
    private static final String GET_USER_BY_EMAIL_QUERY = "SELECT * FROM user WHERE email = ?";
    private static final String GET_USERS_BY_STATUS_QUERY = "SELECT * FROM user WHERE status = ?";

    /**
     * Adds an user to the data storage.
     *
     * @param user an user object
     * @throws DAOException
     */
    @Override
    public void addUser(User user) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            statement = connection.prepareStatement(ADD_USER_QUERY);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setDate(5, new Date(user.getDateOfRegistry().getTime()));
            statement.setString(6, user.getPhoto());
            statement.setInt(7, user.getRating());
            statement.setString(8, user.getStatus());
            statement.setString(9, user.getLanguageId());

            statement.executeUpdate();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Exception in DAO layer when adding user", e);
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
     * Updates an user in the data storage.
     *
     * @param user an user object
     * @throws DAOException
     */
    @Override
    public void updateUser(User user) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            statement = connection.prepareStatement(UPDATE_USER_QUERY);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setDate(5, new Date(user.getDateOfRegistry().getTime()));
            statement.setString(6, user.getPhoto());
            statement.setInt(7, user.getRating());
            statement.setString(8, user.getStatus());
            statement.setString(9, user.getLanguageId());
            statement.setInt(10, user.getId());

            statement.executeUpdate();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Exception in DAO layer when updating user", e);
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
     * Deletes an user from the data storage.
     *
     * @param id an id of the deleting user
     * @throws DAOException
     */
    @Override
    public void deleteUser(int id) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            statement = connection.prepareStatement(DELETE_USER_QUERY);
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Exception in DAO layer when deleting user", e);
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
     * Returns all the users from the data storage.
     *
     * @return all the users from
     * @throws DAOException
     */
    @Override
    public List<User> getAllUsers() throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_USERS_QUERY);

            List<User> allUsers = new ArrayList<>();
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getInt(1));
                user.setEmail(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setFirstName(resultSet.getString(4));
                user.setLastName(resultSet.getString(5));
                user.setDateOfRegistry(resultSet.getDate(6));
                user.setPhoto(resultSet.getString(7));
                user.setRating(resultSet.getInt(8));
                user.setStatus(resultSet.getString(9));
                user.setLanguageId(resultSet.getString(10));

                allUsers.add(user);
            }
            return allUsers;
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Exception in DAO layer when getting user", e);
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
     * Returns an user by id from the data storage.
     *
     * @param id an id of the needed user
     * @return an user by id
     * @throws DAOException
     */
    @Override
    public User getUserById(int id) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            statement = connection.prepareStatement(GET_USER_BY_ID_QUERY);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            User user = null;
            if(resultSet.next()){
                user = new User();
                user.setId(resultSet.getInt(1));
                user.setEmail(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setFirstName(resultSet.getString(4));
                user.setLastName(resultSet.getString(5));
                user.setDateOfRegistry(resultSet.getDate(6));
                user.setPhoto(resultSet.getString(7));
                user.setRating(resultSet.getInt(8));
                user.setStatus(resultSet.getString(9));
                user.setLanguageId(resultSet.getString(10));
            }
            return user;
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Exception in DAO layer when getting user", e);
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
     * Returns an user by the email from the data storage.
     *
     * @param email an email of the needed user
     * @return an user by email
     * @throws DAOException
     */
    @Override
    public User getUserByEmail(String email) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            statement = connection.prepareStatement(GET_USER_BY_EMAIL_QUERY);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            User user = null;
            if(resultSet.next()){
                user = new User();
                user.setId(resultSet.getInt(1));
                user.setEmail(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setFirstName(resultSet.getString(4));
                user.setLastName(resultSet.getString(5));
                user.setDateOfRegistry(resultSet.getDate(6));
                user.setPhoto(resultSet.getString(7));
                user.setRating(resultSet.getInt(8));
                user.setStatus(resultSet.getString(9));
                user.setLanguageId(resultSet.getString(10));
            }
            return user;
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Exception in DAO layer when getting user", e);
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
     * Returns an users by the status from the data storage.
     *
     * @param status a status of the needed user
     * @return an users by the status
     * @throws DAOException
     */
    @Override
    public List<User> getUsersByStatus(String status) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            statement = connection.prepareStatement(GET_USERS_BY_STATUS_QUERY);
            statement.setString(1, status);
            ResultSet resultSet = statement.executeQuery();

            List<User> usersByStatus = new ArrayList<>();
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getInt(1));
                user.setEmail(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setFirstName(resultSet.getString(4));
                user.setLastName(resultSet.getString(5));
                user.setDateOfRegistry(resultSet.getDate(6));
                user.setPhoto(resultSet.getString(7));
                user.setRating(resultSet.getInt(8));
                user.setStatus(resultSet.getString(9));
                user.setLanguageId(resultSet.getString(10));

                usersByStatus.add(user);
            }
            return usersByStatus;
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Exception in DAO layer when getting user", e);
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
     * Returns an users matching the criteria from the data storage.
     *
     * @param criteria a UserCriteria object
     * @param from a starting position in users list (starting from 0)
     * @param amount a needed amount of users
     * @return an users matching the criteria
     * @throws DAOException
     */
    @Override
    public List<User> getUsersByCriteria(UserCriteria criteria, int from, int amount) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            StringBuilder query = new StringBuilder("SELECT * FROM user ");
            boolean atLeastOneWhereCriteria = false;
            if(criteria.getEmail() != null){
                query.append("WHERE email LIKE '%");
                query.append(criteria.getEmail());
                query.append("%' ");
                atLeastOneWhereCriteria = true;
            }
            if(criteria.getFirstName() != null){
                query.append(atLeastOneWhereCriteria ? "AND" : "WHERE");
                query.append(" first_name LIKE '%");
                query.append(criteria.getFirstName());
                query.append("%' ");
                atLeastOneWhereCriteria = true;
            }
            if(criteria.getLastName() != null){
                query.append(atLeastOneWhereCriteria ? "AND" : "WHERE");
                query.append(" last_name LIKE '%");
                query.append(criteria.getLastName());
                query.append("%' ");
                atLeastOneWhereCriteria = true;
            }
            if(criteria.getMinDateOfRegistry() != null){
                query.append(atLeastOneWhereCriteria ? "AND" : "WHERE");
                query.append(" date_of_registry > '");
                query.append(criteria.getMinDateOfRegistry());
                query.append("' ");
                atLeastOneWhereCriteria = true;
            }
            if(criteria.getMaxDateOfRegistry() != null){
                query.append(atLeastOneWhereCriteria ? "AND" : "WHERE");
                query.append(" date_of_registry < '");
                query.append(criteria.getMaxDateOfRegistry());
                query.append("' ");
                atLeastOneWhereCriteria = true;
            }
            if(criteria.getMinRating() != null){
                query.append(atLeastOneWhereCriteria ? "AND" : "WHERE");
                query.append(" rating > ");
                query.append(criteria.getMinRating());
                query.append(" ");
                atLeastOneWhereCriteria = true;
            }
            if(criteria.getMaxRating() != null){
                query.append(atLeastOneWhereCriteria ? "AND" : "WHERE");
                query.append(" rating < ");
                query.append(criteria.getMaxRating());
                query.append(" ");
                atLeastOneWhereCriteria = true;
            }
            if(criteria.getStatuses() != null){
                query.append(atLeastOneWhereCriteria ? "AND" : "WHERE");
                query.append(" status IN (");
                for(String status : criteria.getStatuses()){
                    query.append("'");
                    query.append(status);
                    query.append("'");
                    query.append(',');
                }
                query.deleteCharAt(query.length() - 1);
                query.append(") ");
            }
            if(amount != 0){
                query.append("LIMIT ");
                query.append(from);
                query.append(", ");
                query.append(amount);
                query.append(" ");
            }

            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());

            List<User> users = new ArrayList<>();
            while(resultSet.next()){
                User user = new User();
                user.setId(resultSet.getInt(1));
                user.setEmail(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setFirstName(resultSet.getString(4));
                user.setLastName(resultSet.getString(5));
                user.setDateOfRegistry(resultSet.getDate(6));
                user.setPhoto(resultSet.getString(7));
                user.setRating(resultSet.getInt(8));
                user.setStatus(resultSet.getString(9));
                user.setLanguageId(resultSet.getString(10));

                users.add(user);
            }
            return users;
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("DAO layer: cannot get users by criteria", e);
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
     * Returns an amount of users matching the criteria.
     *
     * @param criteria a UserCriteria object
     * @return an amount of users matching the criteria
     * @throws DAOException
     */
    @Override
    public int getUsersCountByCriteria(UserCriteria criteria) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            StringBuilder query = new StringBuilder("SELECT COUNT(*) FROM (SELECT * FROM user ");
            boolean atLeastOneWhereCriteria = false;
            if(criteria.getEmail() != null){
                query.append("WHERE email LIKE '%");
                query.append(criteria.getEmail());
                query.append("%' ");
                atLeastOneWhereCriteria = true;
            }
            if(criteria.getFirstName() != null){
                query.append(atLeastOneWhereCriteria ? "AND" : "WHERE");
                query.append(" first_name LIKE '%");
                query.append(criteria.getFirstName());
                query.append("%' ");
                atLeastOneWhereCriteria = true;
            }
            if(criteria.getLastName() != null){
                query.append(atLeastOneWhereCriteria ? "AND" : "WHERE");
                query.append(" last_name LIKE '%");
                query.append(criteria.getLastName());
                query.append("%' ");
                atLeastOneWhereCriteria = true;
            }
            if(criteria.getMinDateOfRegistry() != null){
                query.append(atLeastOneWhereCriteria ? "AND" : "WHERE");
                query.append(" date_of_registry > '");
                query.append(criteria.getMinDateOfRegistry());
                query.append("' ");
                atLeastOneWhereCriteria = true;
            }
            if(criteria.getMaxDateOfRegistry() != null){
                query.append(atLeastOneWhereCriteria ? "AND" : "WHERE");
                query.append(" date_of_registry < '");
                query.append(criteria.getMaxDateOfRegistry());
                query.append("' ");
                atLeastOneWhereCriteria = true;
            }
            if(criteria.getMinRating() != null){
                query.append(atLeastOneWhereCriteria ? "AND" : "WHERE");
                query.append(" rating > ");
                query.append(criteria.getMinRating());
                query.append(" ");
                atLeastOneWhereCriteria = true;
            }
            if(criteria.getMaxRating() != null){
                query.append(atLeastOneWhereCriteria ? "AND" : "WHERE");
                query.append(" rating < ");
                query.append(criteria.getMaxRating());
                query.append(" ");
                atLeastOneWhereCriteria = true;
            }
            if(criteria.getStatuses() != null){
                query.append(atLeastOneWhereCriteria ? "AND" : "WHERE");
                query.append(" status IN (");
                for(String status : criteria.getStatuses()){
                    query.append("'");
                    query.append(status);
                    query.append("'");
                    query.append(',');
                }
                query.deleteCharAt(query.length() - 1);
                query.append(") ");
            }
            query.append(") AS c");

            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());

            int usersCount = 0;
            if(resultSet.next()){
                usersCount = resultSet.getInt(1);
            }
            return usersCount;
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("DAO layer: cannot get users by criteria", e);
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
