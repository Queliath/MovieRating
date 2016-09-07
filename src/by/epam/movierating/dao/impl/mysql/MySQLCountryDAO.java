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
    private static final String ADD_COUNTRY_QUERY = "INSERT INTO country " +
            "(name, position) VALUES (?, ?)";
    private static final String UPDATE_COUNTRY_QUERY = "UPDATE country " +
            "SET name = ?, position = ? WHERE id = ?";
    private static final String TCOUNTRY_CHECK_QUERY = "SELECT id FROM tcountry " +
            "WHERE language_id = ? AND id = ?";
    private static final String ADD_TCOUNTRY_QUERY = "INSERT INTO tcountry " +
            "(language_id, id, name) VALUES (?, ?, ?)";
    private static final String UPDATE_TCOUNTRY_QUERY = "UPDATE tcountry " +
            "SET name = ? WHERE language_id = ? AND id = ?";
    private static final String DELETE_COUNTRY_QUERY = "DELETE FROM country WHERE id = ?";
    private static final String GET_ALL_COUNTRIES_QUERY = "SELECT * FROM country";
    private static final String GET_ALL_COUNTRIES_NOT_DEFAULT_LANG_QUERY = "SELECT c.id, " +
            "coalesce(t.name, c.name), c.position FROM country AS c LEFT JOIN " +
            "(SELECT * FROM tcountry WHERE language_id = ?) AS t USING(id)";
    private static final String GET_COUNTRY_BY_ID_QUERY = "SELECT * FROM country WHERE id = ?";
    private static final String GET_COUNTRY_BY_ID_NOT_DEFAULT_LANG_QUERY = "SELECT c.id, " +
            "coalesce(t.name, c.name), c.position FROM country AS c LEFT JOIN " +
            "(SELECT * FROM tcountry WHERE language_id = ?) AS t USING(id) WHERE c.id = ?";
    private static final String GET_COUNTRIES_BY_MOVIE_QUERY = "SELECT country.* FROM country " +
            "INNER JOIN movie_country ON country.id = movie_country.country_id " +
            "WHERE movie_country.movie_id = ?";
    private static final String GET_COUNTRIES_BY_MOVIE_NOT_DEFAULT_LANG_QUERY = "SELECT c.id, coalesce(t.name, c.name), " +
            " c.position FROM country AS c INNER JOIN movie_country AS mc ON c.id = mc.country_id " +
            "LEFT JOIN (SELECT * FROM tcountry WHERE language_id = ?) AS t USING(id) WHERE mc.movie_id = ?";
    private static final String GET_TOP_POSITION_COUNTRIES_QUERY = "SELECT * FROM country ORDER BY position LIMIT ";
    private static final String GET_TOP_POSITION_COUNTRIES_NOT_DEFAULT_LANG_QUERY = "SELECT c.id, " +
            "coalesce(t.name, c.name), c.position FROM country AS c LEFT JOIN " +
            "(SELECT * FROM tcountry WHERE language_id = ?) AS t USING(id) " +
            "ORDER BY c.position LIMIT ";
    private static final String GET_COUNTRIES_QUERY = "SELECT * FROM country LIMIT ";
    private static final String GET_COUNTRIES_NOT_DEFAULT_LANG_QUERY = "SELECT c.id, " +
            "coalesce(t.name, c.name), c.position FROM country AS c LEFT JOIN " +
            "(SELECT * FROM tcountry WHERE language_id = ?) AS t USING(id) LIMIT ";
    private static final String GET_COUNTRIES_COUNT_QUERY = "SELECT COUNT(*) FROM country";

    private static final String DEFAULT_LANGUAGE_ID = "EN";

    @Override
    public void addCountry(Country country) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            statement = connection.prepareStatement(ADD_COUNTRY_QUERY);
            statement.setString(1, country.getName());
            statement.setInt(2, country.getPosition());

            statement.executeUpdate();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when adding country", e);
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
    public void updateCountry(Country country, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.prepareStatement(UPDATE_COUNTRY_QUERY);
                statement.setString(1, country.getName());
                statement.setInt(2, country.getPosition());
                statement.setInt(3, country.getId());
            }
            else {
                PreparedStatement checkStatement = connection.prepareStatement(TCOUNTRY_CHECK_QUERY);
                checkStatement.setString(1, languageId);
                checkStatement.setInt(2, country.getId());
                ResultSet resultSet = checkStatement.executeQuery();
                if(resultSet.next()){
                    statement = connection.prepareStatement(UPDATE_TCOUNTRY_QUERY);
                    statement.setString(1, country.getName());
                    statement.setString(2, languageId);
                    statement.setInt(3, country.getId());
                }
                else {
                    statement = connection.prepareStatement(ADD_TCOUNTRY_QUERY);
                    statement.setString(1, languageId);
                    statement.setInt(2, country.getId());
                    statement.setString(3, country.getName());
                }
            }

            statement.executeUpdate();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when updating country", e);
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
    public void deleteCountry(int id) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            statement = connection.prepareStatement(DELETE_COUNTRY_QUERY);
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when deleting country", e);
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
    public List<Country> getAllCountries(String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            ResultSet resultSet = null;
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.createStatement();
                resultSet = statement.executeQuery(GET_ALL_COUNTRIES_QUERY);
            }
            else {
                preparedStatement = connection.prepareStatement(GET_ALL_COUNTRIES_NOT_DEFAULT_LANG_QUERY);
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
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting country", e);
        } finally {
            try {
                if (connection != null) {
                    if (statement != null) {
                        statement.close();
                    }
                    if(preparedStatement != null){
                        preparedStatement.close();
                    }
                    mySQLConnectionPool.freeConnection(connection);
                }
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public Country getCountryById(int id, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.prepareStatement(GET_COUNTRY_BY_ID_QUERY);
                statement.setInt(1, id);
            }
            else {
                statement = connection.prepareStatement(GET_COUNTRY_BY_ID_NOT_DEFAULT_LANG_QUERY);
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
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting country", e);
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
    public List<Country> getCountriesByMovie(int movieId, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.prepareStatement(GET_COUNTRIES_BY_MOVIE_QUERY);
                statement.setInt(1, movieId);
            }
            else {
                statement = connection.prepareStatement(GET_COUNTRIES_BY_MOVIE_NOT_DEFAULT_LANG_QUERY);
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
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting country", e);
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
    public List<Country> getTopPositionCountries(int amount, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            ResultSet resultSet = null;
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.createStatement();
                resultSet = statement.executeQuery(GET_TOP_POSITION_COUNTRIES_QUERY + amount);
            }
            else {
                preparedStatement = connection.prepareStatement(GET_TOP_POSITION_COUNTRIES_NOT_DEFAULT_LANG_QUERY + amount);
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
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting country", e);
        } finally {
            try {
                if (connection != null) {
                    if (statement != null) {
                        statement.close();
                    }
                    if(preparedStatement != null){
                        preparedStatement.close();
                    }
                    mySQLConnectionPool.freeConnection(connection);
                }
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public List<Country> getCountries(int from, int amount, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            ResultSet resultSet = null;
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.createStatement();
                resultSet = statement.executeQuery(GET_COUNTRIES_QUERY + from + ", " + amount);
            }
            else {
                preparedStatement = connection.prepareStatement(GET_COUNTRIES_NOT_DEFAULT_LANG_QUERY + from + ", " + amount);
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
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting country", e);
        } finally {
            try {
                if (connection != null) {
                    if (statement != null) {
                        statement.close();
                    }
                    if(preparedStatement != null){
                        preparedStatement.close();
                    }
                    mySQLConnectionPool.freeConnection(connection);
                }
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public int getCountriesCount() throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_COUNTRIES_COUNT_QUERY);

            int countriesCount = 0;
            if(resultSet.next()){
                countriesCount = resultSet.getInt(1);
            }
            return countriesCount;
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("DAO layer: cannot get countries count", e);
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
