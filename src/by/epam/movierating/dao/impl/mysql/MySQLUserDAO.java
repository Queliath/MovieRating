package by.epam.movierating.dao.impl.mysql;

import by.epam.movierating.dao.interfaces.UserDAO;
import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPool;
import by.epam.movierating.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 11.06.2016.
 */
public class MySQLUserDAO implements UserDAO {
    @Override
    public void addUser(User user) throws DAOException {
        try {
            MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
            Connection connection = mySQLConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO user " +
                    "(email, password, first_name, last_name, date_of_registry, photo, rating, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setDate(5, new Date(user.getDateOfRegistry().getTime()));
            statement.setString(6, user.getPhoto());
            statement.setInt(7, user.getRating());
            statement.setString(8, user.getStatus());

            statement.executeUpdate();

            mySQLConnectionPool.freeConnection(connection);

        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            throw new DAOException("Exception in DAO layer when adding user", e);
        }
    }

    @Override
    public void updateUser(User user) throws DAOException {
        try {
            MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
            Connection connection = mySQLConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE user " +
                    "SET email = ?, password = ?, first_name = ?, last_name = ?, date_of_registry = ?, " +
                    "photo = ?, rating = ?, status = ? WHERE id = ?");
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setDate(5, new Date(user.getDateOfRegistry().getTime()));
            statement.setString(6, user.getPhoto());
            statement.setInt(7, user.getRating());
            statement.setString(8, user.getStatus());
            statement.setInt(9, user.getId());

            statement.executeUpdate();

            mySQLConnectionPool.freeConnection(connection);

        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            throw new DAOException("Exception in DAO layer when updating user", e);
        }
    }

    @Override
    public void deleteUser(int id) throws DAOException {
        try {
            MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
            Connection connection = mySQLConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM user WHERE id = ?");
            statement.setInt(1, id);

            statement.executeUpdate();

            mySQLConnectionPool.freeConnection(connection);

        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            throw new DAOException("Exception in DAO layer when deleting user", e);
        }
    }

    @Override
    public List<User> getAllUsers() throws DAOException {
        try {
            MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
            Connection connection = mySQLConnectionPool.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");

            mySQLConnectionPool.freeConnection(connection);

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

                allUsers.add(user);
            }
            return allUsers;

        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            throw new DAOException("Exception in DAO layer when getting user", e);
        }
    }

    @Override
    public User getUserById(int id) throws DAOException {
        try {
            MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
            Connection connection = mySQLConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            mySQLConnectionPool.freeConnection(connection);

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
            }
            return user;

        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            throw new DAOException("Exception in DAO layer when getting user", e);
        }
    }

    @Override
    public User getUserByEmail(String email) throws DAOException {
        try {
            MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
            Connection connection = mySQLConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE email = ?");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            mySQLConnectionPool.freeConnection(connection);

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
            }
            return user;

        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            throw new DAOException("Exception in DAO layer when getting user", e);
        }
    }

    @Override
    public List<User> getUsersByStatus(String status) throws DAOException {
        try {
            MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
            Connection connection = mySQLConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE status = ?");
            statement.setString(1, status);
            ResultSet resultSet = statement.executeQuery();

            mySQLConnectionPool.freeConnection(connection);

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

                usersByStatus.add(user);
            }
            return usersByStatus;

        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            throw new DAOException("Exception in DAO layer when getting user", e);
        }
    }
}
