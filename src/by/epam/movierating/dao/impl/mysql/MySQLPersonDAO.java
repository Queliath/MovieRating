package by.epam.movierating.dao.impl.mysql;

import by.epam.movierating.dao.inter.PersonDAO;
import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPool;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPoolException;
import by.epam.movierating.domain.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides a DAO-logic for the Person entity for the MySQL Database.
 *
 * @author Kostevich Vladislav
 * @version 1.0
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
    private static final String GET_PERSONS_BY_CRITERIA_HEAD_QUERY = "SELECT DISTINCT p.* FROM person AS p ";
    private static final String GET_PERSONS_BY_CRITERIA_NOT_DEFAULT_LANGUAGE_QUERY = "SELECT DISTINCT p.id, coalesce(t.name, p.name), p.date_of_birth, " +
            "coalesce(t.place_of_birth, p.place_of_birth), p.photo FROM person AS p ";
    private static final String GET_PERSONS_BY_CRITERIA_TRANSLATE_JOIN_QUERY_FIRST_PART = "LEFT JOIN (SELECT * FROM tperson WHERE language_id = '";
    private static final String GET_PERSONS_BY_CRITERIA_TRANSLATE_JOIN_QUERY_SECOND_PART = "') AS t USING(id) ";
    private static final String GET_PERSONS_BY_CRITERIA_NAME_CRITERIA_QUERY_FIRST_PART = "WHERE p.name LIKE '%";
    private static final String GET_PERSONS_BY_CRITERIA_NAME_CRITERIA_QUERY_SECOND_PART = "%' ";
    private static final String GET_PERSONS_BY_CRITERIA_NAME_CRITERIA_NOT_DEFAULT_LANGUAGE_QUERY_FIRST_PART = "WHERE (p.name LIKE '%";
    private static final String GET_PERSONS_BY_CRITERIA_NAME_CRITERIA_NOT_DEFAULT_LANGUAGE_QUERY_SECOND_PART = "%' OR t.name LIKE '%";
    private static final String GET_PERSONS_BY_CRITERIA_NAME_CRITERIA_NOT_DEFAULT_LANGUAGE_QUERY_THIRD_PART = "%') ";
    private static final String LIMIT_QUERY = "LIMIT ";
    private static final String SPACE_SEPARATOR = " ";
    private static final String COMA_SEPARATOR = ",";
    private static final String GET_PERSONS_COUNT_BY_CRITERIA_HEAD_QUERY = "SELECT COUNT(*) FROM (SELECT p.* FROM person AS p ";
    private static final String GET_PERSONS_COUNT_BY_CRITERIA_TAIL_QUERY = ") AS c";

    private static final String DEFAULT_LANGUAGE_ID = "EN";

    /**
     * Adds a person to the data storage (in the default language).
     *
     * @param person a person object
     * @throws DAOException
     */
    @Override
    public void addPerson(Person person) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            statement = connection.prepareStatement(ADD_PERSON_QUERY);
            statement.setString(1, person.getName());
            statement.setDate(2, new Date(person.getDateOfBirth().getTime()));
            statement.setString(3, person.getPlaceOfBirth());
            statement.setString(4, person.getPhoto());

            statement.executeUpdate();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when adding person", e);
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
     * Updates a person or adds/updates a localization of a person in the data storage.
     *
     * If the languageId argument is an id of the default language of the application, then it updates
     * a person. If the language argument is an id of the different language (not default) then it
     * adds/updates a localization of a person (it based on the fact of existence of a localization:
     * if it doesn't exist then it will be added, otherwise a localization will be updated).
     * @param person a person object
     * @param languageId a language id like 'EN', "RU' etc.
     * @throws DAOException
     */
    @Override
    public void updatePerson(Person person, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

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
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when updating person", e);
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
     * Deletes a person from the data storage (with all of the localizations).
     *
     * @param id an id of the deleting person
     * @throws DAOException
     */
    @Override
    public void deletePerson(int id) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            statement = connection.prepareStatement(DELETE_PERSON_QUERY);
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when deleting person", e);
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
     * Returns all the persons from the data storage.
     *
     * @param languageId a language id like 'EN', "RU' etc.
     * @return all the persons
     * @throws DAOException
     */
    @Override
    public List<Person> getAllPersons(String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            ResultSet resultSet = null;
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                statement = connection.createStatement();
                resultSet = statement.executeQuery(GET_ALL_PERSONS_QUERY);
            }
            else {
                preparedStatement = connection.prepareStatement(GET_ALL_PERSONS_NOT_DEFAULT_LANG_QUERY);
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
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting all persons", e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (preparedStatement != null){
                    preparedStatement.close();
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
     * Returns a person by id from the data storage.
     *
     * @param id an id of the needed person
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a person by id
     * @throws DAOException
     */
    @Override
    public Person getPersonById(int id, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

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
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting all persons", e);
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
     * Returns a persons which took part in the movie in the certain role.
     *
     * @param movieId an id of the movie
     * @param relationType an id of the relation type (role)
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a persons which took part in the movie in the certain role
     * @throws DAOException
     */
    @Override
    public List<Person> getPersonsByMovieAndRelationType(int movieId, int relationType, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

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
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Error in DAO layer when getting all persons", e);
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
     * Returns a persons matching the criteria.
     *
     * @param name a person's name
     * @param from a starting position in the persons list (starting from 0)
     * @param amount a needed amount of persons
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a persons matching the criteria
     * @throws DAOException
     */
    @Override
    public List<Person> getPersonsByCriteria(String name, int from, int amount, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            StringBuilder query = new StringBuilder();
            if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                query.append(GET_PERSONS_BY_CRITERIA_HEAD_QUERY);
            }
            else {
                query.append(GET_PERSONS_BY_CRITERIA_NOT_DEFAULT_LANGUAGE_QUERY);
            }
            if(!languageId.equals(DEFAULT_LANGUAGE_ID)){
                query.append(GET_PERSONS_BY_CRITERIA_TRANSLATE_JOIN_QUERY_FIRST_PART);
                query.append(languageId);
                query.append(GET_PERSONS_BY_CRITERIA_TRANSLATE_JOIN_QUERY_SECOND_PART);
            }

            boolean atLeastOneWhereCriteria = false;
            if (name != null) {
                if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                    query.append(GET_PERSONS_BY_CRITERIA_NAME_CRITERIA_QUERY_FIRST_PART);
                    query.append(name);
                    query.append(GET_PERSONS_BY_CRITERIA_NAME_CRITERIA_QUERY_SECOND_PART);
                }
                else {
                    query.append(GET_PERSONS_BY_CRITERIA_NAME_CRITERIA_NOT_DEFAULT_LANGUAGE_QUERY_FIRST_PART);
                    query.append(name);
                    query.append(GET_PERSONS_BY_CRITERIA_NAME_CRITERIA_NOT_DEFAULT_LANGUAGE_QUERY_SECOND_PART);
                    query.append(name);
                    query.append(GET_PERSONS_BY_CRITERIA_NAME_CRITERIA_NOT_DEFAULT_LANGUAGE_QUERY_THIRD_PART);
                }
                atLeastOneWhereCriteria = true;
            }
            if(amount != 0){
                query.append(LIMIT_QUERY);
                query.append(from);
                query.append(COMA_SEPARATOR);
                query.append(SPACE_SEPARATOR);
                query.append(amount);
                query.append(SPACE_SEPARATOR);
            }

            statement = connection.createStatement();
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
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Cannot get persons by criteria", e);
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
     * Returns an amount of persons matching the criteria.
     *
     * @param name a person's name
     * @param languageId a language id like 'EN', "RU' etc.
     * @return an amount of persons matching the criteria
     * @throws DAOException
     */
    @Override
    public int getPersonsCountByCriteria(String name, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = mySQLConnectionPool.getConnection();

            StringBuilder query = new StringBuilder();
            query.append(GET_PERSONS_COUNT_BY_CRITERIA_HEAD_QUERY);
            if(!languageId.equals(DEFAULT_LANGUAGE_ID)){
                query.append(GET_PERSONS_BY_CRITERIA_TRANSLATE_JOIN_QUERY_FIRST_PART);
                query.append(languageId);
                query.append(GET_PERSONS_BY_CRITERIA_TRANSLATE_JOIN_QUERY_SECOND_PART);
            }

            boolean atLeastOneWhereCriteria = false;
            if (name != null) {
                if(languageId.equals(DEFAULT_LANGUAGE_ID)){
                    query.append(GET_PERSONS_BY_CRITERIA_NAME_CRITERIA_QUERY_FIRST_PART);
                    query.append(name);
                    query.append(GET_PERSONS_BY_CRITERIA_NAME_CRITERIA_QUERY_SECOND_PART);
                }
                else {
                    query.append(GET_PERSONS_BY_CRITERIA_NAME_CRITERIA_NOT_DEFAULT_LANGUAGE_QUERY_FIRST_PART);
                    query.append(name);
                    query.append(GET_PERSONS_BY_CRITERIA_NAME_CRITERIA_NOT_DEFAULT_LANGUAGE_QUERY_SECOND_PART);
                    query.append(name);
                    query.append(GET_PERSONS_BY_CRITERIA_NAME_CRITERIA_NOT_DEFAULT_LANGUAGE_QUERY_THIRD_PART);
                }
                atLeastOneWhereCriteria = true;
            }
            query.append(GET_PERSONS_COUNT_BY_CRITERIA_TAIL_QUERY);

            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());

            int personsCount = 0;
            if(resultSet.next()){
                personsCount = resultSet.getInt(1);
            }
            return personsCount;
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        } catch (SQLException e) {
            throw new DAOException("Cannot get persons by criteria", e);
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
