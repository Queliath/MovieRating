package by.epam.movierating.dao.impl.mysql;

import by.epam.movierating.dao.interfaces.CountryDAO;
import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPool;
import by.epam.movierating.domain.Country;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 18.06.2016.
 */
public class MySQLCountryDAO implements CountryDAO {
    @Override
    public void addCountry(Country country) throws DAOException {
        try {
            MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
            Connection connection = mySQLConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO country " +
                    "(name, icon) VALUES (?, ?)");
            statement.setString(1, country.getName());
            statement.setString(2, country.getIcon());

            statement.executeUpdate();

            mySQLConnectionPool.freeConnection(connection);

        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            throw new DAOException("Error in DAO layer when adding country", e);
        }
    }

    @Override
    public void updateCountry(Country country) throws DAOException {
        try {
            MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
            Connection connection = mySQLConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE country " +
                    "SET name = ?, icon = ? WHERE id = ?");
            statement.setString(1, country.getName());
            statement.setString(2, country.getIcon());
            statement.setInt(3, country.getId());

            statement.executeUpdate();

            mySQLConnectionPool.freeConnection(connection);

        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            throw new DAOException("Error in DAO layer when updating country", e);
        }
    }

    @Override
    public void deleteCountry(int id) throws DAOException {
        try {
            MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
            Connection connection = mySQLConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM country WHERE id = ?");
            statement.setInt(1, id);

            statement.executeUpdate();

            mySQLConnectionPool.freeConnection(connection);

        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            throw new DAOException("Error in DAO layer when deleting country", e);
        }
    }

    @Override
    public List<Country> getAllCountries() throws DAOException {
        try {
            MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
            Connection connection = mySQLConnectionPool.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM country");

            mySQLConnectionPool.freeConnection(connection);

            List<Country> allCountries = new ArrayList<>();
            while (resultSet.next()){
                Country country = new Country();
                country.setId(resultSet.getInt(1));
                country.setName(resultSet.getString(2));
                country.setIcon(resultSet.getString(3));

                allCountries.add(country);
            }

            return allCountries;

        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            throw new DAOException("Error in DAO layer when getting country", e);
        }
    }

    @Override
    public Country getCountryById(int id) throws DAOException {
        try {
            MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
            Connection connection = mySQLConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM country WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            mySQLConnectionPool.freeConnection(connection);

            Country country = null;
            if (resultSet.next()){
                country = new Country();
                country.setId(resultSet.getInt(1));
                country.setName(resultSet.getString(2));
                country.setIcon(resultSet.getString(3));
            }

            return country;

        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            throw new DAOException("Error in DAO layer when getting country", e);
        }
    }

    @Override
    public List<Country> getCountriesByMovie(int movieId) throws DAOException {
        try {
            MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
            Connection connection = mySQLConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT country.* FROM country " +
                    "INNER JOIN movie_country ON country.id = movie_country.country_id " +
                    "WHERE movie_country.movie_id = ?");
            statement.setInt(1, movieId);
            ResultSet resultSet = statement.executeQuery();

            mySQLConnectionPool.freeConnection(connection);

            List<Country> countriesByMovie = new ArrayList<>();
            while (resultSet.next()){
                Country country = new Country();
                country.setId(resultSet.getInt(1));
                country.setName(resultSet.getString(2));
                country.setIcon(resultSet.getString(3));

                countriesByMovie.add(country);
            }

            return countriesByMovie;

        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            throw new DAOException("Error in DAO layer when getting country", e);
        }
    }
}
