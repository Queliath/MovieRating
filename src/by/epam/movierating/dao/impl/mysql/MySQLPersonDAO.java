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
    private static final String ADD_PERSON_QUERY = "INSERT INTO person " +
            "(name, date_of_birth, place_of_birth, photo) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_PERSON_QUERY = "UPDATE person " +
            "SET name = ?, date_of_birth = ?, place_of_birth = ?, photo = ? WHERE id = ?";
    private static final String TPERSON_CHECK_QUERY = "SELECT id FROM tperson " +
            "WHERE language_id = ? AND id = ?";
    private static final String ADD_TPERSON_QUERY = "INSERT INTO tperson " +
            "(language_id, id, name, place_of_birth) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_TPERSON_QUERY = "UPDATE tperson " +
            "SET name = ?, place_of_birth = ? WHERE language_id = ? AND id = ?";
    private static final String DELETE_PERSON_QUERY = "DELETE FROM person WHERE id = ?";
    private static final String GET_ALL_PERSONS_QUERY = "SELECT * FROM person";
    private static final String GET_ALL_PERSONS_NOT_DEFAULT_LANG_QUERY = "SELECT p.id, " +
            "coalesce(t.name, p.name), p.date_of_birth, coalesce(t.place_of_birth, p.place_of_birth), " +
            "p.photo FROM person AS p LEFT JOIN (SELECT * FROM tperson WHERE language_id = ?) AS t " +
            "USING(id) WHERE p.id = ?";
    private static final String GET_PERSON_BY_ID_QUERY = "SELECT * FROM person WHERE id = ?";
    private static final String GET_PERSON_BY_ID_NOT_DEFAULT_LANG_QUERY = "SELECT p.id, " +
            "coalesce(t.name, p.name), p.date_of_birth, coalesce(t.place_of_birth, p.place_of_birth), " +
            "p.photo FROM person AS p LEFT JOIN (SELECT * FROM tperson WHERE language_id = ?) AS t " +
            "USING(id) WHERE p.id = ?";
    private static final String GET_PERSONS_BY_MOVIE_QUERY = "SELECT person.* FROM person " +
            "INNER JOIN movie_person_relation ON person.id = movie_person_relation.person_id WHERE " +
            "movie_person_relation.movie_id = ? AND movie_person_relation.relation_type = ?";
    private static final String GET_PERSONS_BY_MOVIE_NOT_DEFAULT_LANG_QUERY = "SELECT p.id, coalesce(t.name, p.name), p.date_of_birth, " +
            "coalesce(t.place_of_birth, p.place_of_birth), p.photo FROM person AS p INNER JOIN " +
            "movie_person_relation AS mpr ON p.id = mpr.person_id LEFT JOIN " +
            "(SELECT * FROM tperson WHERE language_id = ?) AS t USING(id) " +
            "WHERE mpr.movie_id = ? AND mpr.relation_type = ?";

    private static final String DEFAULT_LANGUAGE_ID = "EN";

    @Override
    public void addPerson(Person person) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try{
            PreparedStatement statement = connection.prepareStatement(ADD_PERSON_QUERY);
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
    public void updatePerson(Person person, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try{
            PreparedStatement statement = null;
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.prepareStatement(UPDATE_PERSON_QUERY);
                statement.setString(1, person.getName());
                statement.setDate(2, new Date(person.getDateOfBirth().getTime()));
                statement.setString(3, person.getPlaceOfBirth());
                statement.setString(4, person.getPhoto());
                statement.setInt(5, person.getId());
            }
            else {
                PreparedStatement checkStatement = connection.prepareStatement(TPERSON_CHECK_QUERY);
                checkStatement.setString(1, languageId);
                checkStatement.setInt(2, person.getId());
                ResultSet resultSet = checkStatement.executeQuery();
                if(resultSet.next()){
                    statement = connection.prepareStatement(UPDATE_TPERSON_QUERY);
                    statement.setString(1, person.getName());
                    statement.setString(2, person.getPlaceOfBirth());
                    statement.setString(3, languageId);
                    statement.setInt(4, person.getId());
                }
                else {
                    statement = connection.prepareStatement(ADD_TPERSON_QUERY);
                    statement.setString(1, languageId);
                    statement.setInt(2, person.getId());
                    statement.setString(3, person.getName());
                    statement.setString(4, person.getPlaceOfBirth());
                }
            }

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
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try{
            PreparedStatement statement = connection.prepareStatement(DELETE_PERSON_QUERY);
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
    public List<Person> getAllPersons(String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try{
            ResultSet resultSet = null;
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                Statement statement = connection.createStatement();
                resultSet = statement.executeQuery(GET_ALL_PERSONS_QUERY);
            }
            else {
                PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_PERSONS_NOT_DEFAULT_LANG_QUERY);
                preparedStatement.setString(1, languageId);
                resultSet = preparedStatement.executeQuery();
            }

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
    public Person getPersonById(int id, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try{
            PreparedStatement statement = null;
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.prepareStatement(GET_PERSON_BY_ID_QUERY);
                statement.setInt(1, id);
            }
            else {
                statement = connection.prepareStatement(GET_PERSON_BY_ID_NOT_DEFAULT_LANG_QUERY);
                statement.setString(1, languageId);
                statement.setInt(2, id);
            }
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
    public List<Person> getPersonsByMovieAndRelationType(int movieId, int relationType, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try{
            PreparedStatement statement = null;
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.prepareStatement(GET_PERSONS_BY_MOVIE_QUERY);
                statement.setInt(1, movieId);
                statement.setInt(2, relationType);
            }
            else {
                statement = connection.prepareStatement(GET_PERSONS_BY_MOVIE_NOT_DEFAULT_LANG_QUERY);
                statement.setString(1, languageId);
                statement.setInt(2, movieId);
                statement.setInt(3, relationType);
            }
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

    @Override
    public List<Person> getPersonsByCriteria(String name, int from, int amount, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            StringBuilder query = new StringBuilder();
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                query.append("SELECT DISTINCT p.* FROM person AS p ");
            }
            else {
                query.append("SELECT DISTINCT p.id, coalesce(t.name, p.name), p.date_of_birth, " +
                        "coalesce(t.place_of_birth, p.place_of_birth), p.photo FROM person AS p ");
            }
            if(!languageId.equals(DEFAULT_LANGUAGE_ID)){
                query.append("LEFT JOIN (SELECT * FROM tperson WHERE language_id = '");
                query.append(languageId);
                query.append("') AS t USING(id) ");
            }

            boolean atLeastOneWhereCriteria = false;
            if (name != null) {
                if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                    query.append("WHERE p.name LIKE '%");
                    query.append(name);
                    query.append("%' ");
                }
                else {
                    query.append("WHERE (p.name LIKE '%");
                    query.append(name);
                    query.append("%' OR t.name LIKE '%");
                    query.append(name);
                    query.append("%') ");
                }
                atLeastOneWhereCriteria = true;
            }
            if(amount != 0){
                query.append("LIMIT ");
                query.append(from);
                query.append(", ");
                query.append(amount);
                query.append(" ");
            }

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());

            List<Person> persons = new ArrayList<>();
            while (resultSet.next()){
                Person person = new Person();
                person.setId(resultSet.getInt(1));
                person.setName(resultSet.getString(2));
                person.setDateOfBirth(resultSet.getDate(3));
                person.setPlaceOfBirth(resultSet.getString(4));
                person.setPhoto(resultSet.getString(5));

                persons.add(person);
            }

            return persons;
        } catch (SQLException e) {
            throw new DAOException("Cannot get persons by criteria", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public int getPersonsCountByCriteria(String name, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT COUNT(*) FROM (SELECT p.* FROM person AS p ");
            if(!languageId.equals(DEFAULT_LANGUAGE_ID)){
                query.append("LEFT JOIN (SELECT * FROM tperson WHERE language_id = '");
                query.append(languageId);
                query.append("') AS t USING(id) ");
            }

            boolean atLeastOneWhereCriteria = false;
            if (name != null) {
                if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                    query.append("WHERE p.name LIKE '%");
                    query.append(name);
                    query.append("%' ");
                }
                else {
                    query.append("WHERE (p.name LIKE '%");
                    query.append(name);
                    query.append("%' OR t.name LIKE '%");
                    query.append(name);
                    query.append("%') ");
                }
                atLeastOneWhereCriteria = true;
            }
            query.append(") AS c");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());

            int personsCount = 0;
            if(resultSet.next()){
                personsCount = resultSet.getInt(1);
            }
            return personsCount;
        } catch (SQLException e) {
            throw new DAOException("Cannot get persons by criteria", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

}
