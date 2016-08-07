package by.epam.movierating.dao.impl.mysql;

import by.epam.movierating.dao.interfaces.CountryDAO;
import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPool;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPoolException;
import by.epam.movierating.domain.Country;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 18.06.2016.
 */
public class MySQLCountryDAO implements CountryDAO {
    private static final String DEFAULT_LANGUAGE_ID = "EN";

    @Override
    public void addCountry(Country country, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            PreparedStatement statement = null;
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.prepareStatement("INSERT INTO country " +
                        "(name, position) VALUES (?, ?, ?)");
                statement.setString(1, country.getName());
                statement.setInt(2, country.getPosition());
            }
            else {
                statement = connection.prepareStatement("INSERT INTO tcountry " +
                        "(language_id, id, name) VALUES (?, ?, ?)");
                statement.setString(1, languageId);
                statement.setInt(2, country.getId());
                statement.setString(3, country.getName());
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when adding country", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public void updateCountry(Country country, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            PreparedStatement statement = null;
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.prepareStatement("UPDATE country " +
                        "SET name = ?, position = ? WHERE id = ?");
                statement.setString(1, country.getName());
                statement.setInt(2, country.getPosition());
                statement.setInt(3, country.getId());
            }
            else {
                statement = connection.prepareStatement("UPDATE tcountry " +
                        "SET name = ? WHERE language_id = ? AND id = ?");
                statement.setString(1, country.getName());
                statement.setString(2, languageId);
                statement.setInt(3, country.getId());
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when updating country", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public void deleteCountry(int id) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM country WHERE id = ?");
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when deleting country", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public List<Country> getAllCountries(String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            ResultSet resultSet = null;
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                Statement statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM country");
            }
            else {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT c.id, " +
                        "coalesce(t.name, c.name), c.position FROM country AS c LEFT JOIN " +
                        "(SELECT * FROM tcountry WHERE language_id = ?) AS t USING(id)");
                preparedStatement.setString(1, languageId);
                resultSet = preparedStatement.executeQuery();
            }

            List<Country> allCountries = new ArrayList<>();
            while (resultSet.next()){
                Country country = new Country();
                country.setId(resultSet.getInt(1));
                country.setName(resultSet.getString(2));
                country.setPosition(resultSet.getInt(3));

                allCountries.add(country);
            }

            return allCountries;
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting country", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public Country getCountryById(int id, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            PreparedStatement statement = null;
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.prepareStatement("SELECT * FROM country WHERE id = ?");
                statement.setInt(1, id);
            }
            else {
                statement = connection.prepareStatement("SELECT c.id, " +
                        "coalesce(t.name, c.name), c.position FROM country AS c LEFT JOIN " +
                        "(SELECT * FROM tcountry WHERE language_id = ?) AS t USING(id) WHERE c.id = ?");
                statement.setString(1, languageId);
                statement.setInt(2, id);
            }
            ResultSet resultSet = statement.executeQuery();

            Country country = null;
            if (resultSet.next()){
                country = new Country();
                country.setId(resultSet.getInt(1));
                country.setName(resultSet.getString(2));
                country.setPosition(resultSet.getInt(3));
            }

            return country;
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting country", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public List<Country> getCountriesByMovie(int movieId, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            PreparedStatement statement = null;
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.prepareStatement("SELECT country.* FROM country " +
                        "INNER JOIN movie_country ON country.id = movie_country.country_id " +
                        "WHERE movie_country.movie_id = ?");
                statement.setInt(1, movieId);
            }
            else {
                statement = connection.prepareStatement("SELECT c.id, coalesce(t.name, c.name), " +
                        " c.position FROM country AS c INNER JOIN movie_country AS mc ON c.id = mc.country_id " +
                        "LEFT JOIN (SELECT * FROM tcountry WHERE language_id = ?) AS t USING(id) WHERE mc.movie_id = ?");
                statement.setString(1, languageId);
                statement.setInt(2, movieId);
            }
            ResultSet resultSet = statement.executeQuery();

            List<Country> countriesByMovie = new ArrayList<>();
            while (resultSet.next()){
                Country country = new Country();
                country.setId(resultSet.getInt(1));
                country.setName(resultSet.getString(2));
                country.setPosition(resultSet.getInt(3));

                countriesByMovie.add(country);
            }

            return countriesByMovie;

        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting country", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public List<Country> getTopPositionCountries(int amount, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            ResultSet resultSet = null;
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                Statement statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM country ORDER BY position LIMIT " + amount);
            }
            else {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT c.id, " +
                        "coalesce(t.name, c.name), c.position FROM country AS c LEFT JOIN " +
                        "(SELECT * FROM tcountry WHERE language_id = ?) AS t USING(id) " +
                        "ORDER BY c.position LIMIT " + amount);
                preparedStatement.setString(1, languageId);
                resultSet = preparedStatement.executeQuery();
            }

            List<Country> allCountries = new ArrayList<>();
            while (resultSet.next()){
                Country country = new Country();
                country.setId(resultSet.getInt(1));
                country.setName(resultSet.getString(2));
                country.setPosition(resultSet.getInt(3));

                allCountries.add(country);
            }

            return allCountries;
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting country", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }
}
