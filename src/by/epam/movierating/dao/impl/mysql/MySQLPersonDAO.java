package by.epam.movierating.dao.impl.mysql;

import by.epam.movierating.dao.interfaces.PersonDAO;
import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPool;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPoolException;
import by.epam.movierating.domain.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 11.06.2016.
 */
public class MySQLPersonDAO implements PersonDAO {
    @Override
    public void addPerson(Person person) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = null;
        try {
            mySQLConnectionPool = MySQLConnectionPool.getInstance();
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            throw new DAOException("Cannot create a Connection Pool", e);
        }

        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO person " +
                    "(name, date_of_birth, place_of_birth, photo) VALUES (?, ?, ?, ?)");
            statement.setString(1, person.getName());
            statement.setDate(2, new Date(person.getDateOfBirth().getTime()));
            statement.setString(3, person.getPlaceOfBirth());
            statement.setString(4, person.getPhoto());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when adding person", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public void updatePerson(Person person) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = null;
        try {
            mySQLConnectionPool = MySQLConnectionPool.getInstance();
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            throw new DAOException("Cannot create a Connection Pool", e);
        }

        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try{
            PreparedStatement statement = connection.prepareStatement("UPDATE person " +
                    "SET name = ?, date_of_birth = ?, place_of_birth = ?, photo = ? WHERE id = ?");
            statement.setString(1, person.getName());
            statement.setDate(2, new Date(person.getDateOfBirth().getTime()));
            statement.setString(3, person.getPlaceOfBirth());
            statement.setString(4, person.getPhoto());
            statement.setInt(5, person.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when updating person", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public void deletePerson(int id) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = null;
        try {
            mySQLConnectionPool = MySQLConnectionPool.getInstance();
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            throw new DAOException("Cannot create a Connection Pool", e);
        }

        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try{
            PreparedStatement statement = connection.prepareStatement("DELETE FROM person WHERE id = ?");
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when deleting person", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public List<Person> getAllPersons() throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = null;
        try {
            mySQLConnectionPool = MySQLConnectionPool.getInstance();
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            throw new DAOException("Cannot create a Connection Pool", e);
        }

        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM person");

            List<Person> allPersons = new ArrayList<>();
            while (resultSet.next()){
                Person person = new Person();
                person.setId(resultSet.getInt(1));
                person.setName(resultSet.getString(2));
                person.setDateOfBirth(resultSet.getDate(3));
                person.setPlaceOfBirth(resultSet.getString(4));
                person.setPhoto(resultSet.getString(5));

                allPersons.add(person);
            }

            return allPersons;
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting all persons", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public Person getPersonById(int id) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = null;
        try {
            mySQLConnectionPool = MySQLConnectionPool.getInstance();
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            throw new DAOException("Cannot create a Connection Pool", e);
        }

        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            Person person = null;
            if(resultSet.next()){
                person = new Person();
                person.setId(resultSet.getInt(1));
                person.setName(resultSet.getString(2));
                person.setDateOfBirth(resultSet.getDate(3));
                person.setPlaceOfBirth(resultSet.getString(4));
                person.setPhoto(resultSet.getString(5));
            }
            return person;
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting all persons", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public List<Person> getPersonsByMovieAndRelationType(int movieId, int relationType) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = null;
        try {
            mySQLConnectionPool = MySQLConnectionPool.getInstance();
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            throw new DAOException("Cannot create a Connection Pool", e);
        }

        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try{
            PreparedStatement statement = connection.prepareStatement("SELECT person.* FROM person " +
                    "INNER JOIN movie_person_relation ON person.id = movie_person_relation.person_id WHERE " +
                    "movie_person_relation.movie_id = ? AND movie_person_relation.relation_type = ?");
            statement.setInt(1, movieId);
            statement.setInt(2, relationType);
            ResultSet resultSet = statement.executeQuery();

            List<Person> personsByMovieAndRelationType = new ArrayList<>();
            while (resultSet.next()){
                Person person = new Person();
                person.setId(resultSet.getInt(1));
                person.setName(resultSet.getString(2));
                person.setDateOfBirth(resultSet.getDate(3));
                person.setPlaceOfBirth(resultSet.getString(4));
                person.setPhoto(resultSet.getString(5));

                personsByMovieAndRelationType.add(person);
            }

            return personsByMovieAndRelationType;
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting all persons", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

}
