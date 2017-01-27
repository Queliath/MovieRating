package by.bsuir.movierating.dao.impl;

import by.bsuir.movierating.dao.PersonDAO;
import by.bsuir.movierating.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.List;

/**
 * Provides a DAO-logic for the Person entity for the MySQL Database.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
@Repository("personDao")
public class MySQLPersonDAO implements PersonDAO {
    private static final String ADD_PERSON_QUERY = "INSERT INTO person " +
            "(name, date_of_birth, place_of_birth, photo) VALUES (:name, :date_of_birth, :place_of_birth, :photo)";
    private static final String UPDATE_PERSON_QUERY = "UPDATE person " +
            "SET name = :name, date_of_birth = :date_of_birth, place_of_birth = :plcae_of_birth, photo = :photo WHERE id = :id";
    private static final String TPERSON_CHECK_QUERY = "SELECT id FROM tperson " +
            "WHERE language_id = :language_id AND id = :id";
    private static final String ADD_TPERSON_QUERY = "INSERT INTO tperson " +
            "(language_id, id, name, place_of_birth) VALUES (:language_id, :id, :name, :place_of_birth)";
    private static final String UPDATE_TPERSON_QUERY = "UPDATE tperson " +
            "SET name = :name, place_of_birth = :place_of_birth WHERE language_id = :language_id AND id = :id";
    private static final String DELETE_PERSON_QUERY = "DELETE FROM person WHERE id = :id";
    private static final String GET_ALL_PERSONS_QUERY = "SELECT * FROM person";
    private static final String GET_ALL_PERSONS_NOT_DEFAULT_LANG_QUERY = "SELECT p.id, " +
            "coalesce(t.name, p.name), p.date_of_birth, coalesce(t.place_of_birth, p.place_of_birth), " +
            "p.photo FROM person AS p LEFT JOIN (SELECT * FROM tperson WHERE language_id = :language_id) AS t " +
            "USING(id)";
    private static final String GET_PERSON_BY_ID_QUERY = "SELECT * FROM person WHERE id = :id";
    private static final String GET_PERSON_BY_ID_NOT_DEFAULT_LANG_QUERY = "SELECT p.id, " +
            "coalesce(t.name, p.name), p.date_of_birth, coalesce(t.place_of_birth, p.place_of_birth), " +
            "p.photo FROM person AS p LEFT JOIN (SELECT * FROM tperson WHERE language_id = :language_id) AS t " +
            "USING(id) WHERE p.id = :id";
    private static final String GET_PERSONS_BY_MOVIE_QUERY = "SELECT person.* FROM person " +
            "INNER JOIN movie_person_relation ON person.id = movie_person_relation.person_id WHERE " +
            "movie_person_relation.movie_id = :movie_id AND movie_person_relation.relation_type = :relation_type";
    private static final String GET_PERSONS_BY_MOVIE_NOT_DEFAULT_LANG_QUERY = "SELECT p.id, coalesce(t.name, p.name), p.date_of_birth, " +
            "coalesce(t.place_of_birth, p.place_of_birth), p.photo FROM person AS p INNER JOIN " +
            "movie_person_relation AS mpr ON p.id = mpr.person_id LEFT JOIN " +
            "(SELECT * FROM tperson WHERE language_id = :language_id) AS t USING(id) " +
            "WHERE mpr.movie_id = :movie_id AND mpr.relation_type = :relation_type";
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

    private static final String ID_COLUMN_NAME = "id";
    private static final String NAME_COLUMN_NAME = "name";
    private static final String DATE_OF_BIRTH_COLUMN_NAME = "date_of_birth";
    private static final String PLACE_OF_BIRTH_COLUMN_NAME = "place_of_birth";
    private static final String PHOTO_COLUMN_NAME = "photo";
    private static final String LANGUAGE_ID_COLUMN_NAME = "language_id";
    private static final String MOVIE_ID_PARAM_NAME = "movie_id";
    private static final String RELATION_TYPE_PARAM_NAME = "relation_type";

    private static final String DEFAULT_LANGUAGE_ID = "EN";

    private NamedParameterJdbcTemplate jdbcTemplate;
    private PersonMapper personMapper = new PersonMapper();
    private KeyHolder keyHolder = new GeneratedKeyHolder();

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * Adds a person to the data storage (in the default language).
     *
     * @param person a person object
     */
    @Override
    public void addPerson(Person person) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(NAME_COLUMN_NAME, person.getName())
                .addValue(DATE_OF_BIRTH_COLUMN_NAME, new Date(person.getDateOfBirth().getTime()))
                .addValue(PLACE_OF_BIRTH_COLUMN_NAME, person.getPlaceOfBirth())
                .addValue(PHOTO_COLUMN_NAME, person.getPhoto());

        jdbcTemplate.update(ADD_PERSON_QUERY, sqlParameterSource, keyHolder, new String[] {ID_COLUMN_NAME});

        person.setId(keyHolder.getKey().intValue());
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
     */
    @Override
    public void updatePerson(Person person, String languageId) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(NAME_COLUMN_NAME, person.getName())
                .addValue(PLACE_OF_BIRTH_COLUMN_NAME, person.getPlaceOfBirth())
                .addValue(PHOTO_COLUMN_NAME, person.getPhoto())
                .addValue(ID_COLUMN_NAME, person.getId());

        if(languageId.equals(DEFAULT_LANGUAGE_ID)) {
            jdbcTemplate.update(UPDATE_PERSON_QUERY, sqlParameterSource.addValue(DATE_OF_BIRTH_COLUMN_NAME, new Date(person.getDateOfBirth().getTime())));
        } else {
            SqlParameterSource testingSqlParameterSource = new MapSqlParameterSource()
                    .addValue(ID_COLUMN_NAME, person.getId())
                    .addValue(LANGUAGE_ID_COLUMN_NAME, languageId);
            List<Person> persons = jdbcTemplate.query(TPERSON_CHECK_QUERY, testingSqlParameterSource, personMapper);
            if(!persons.isEmpty()) {
                jdbcTemplate.update(UPDATE_TPERSON_QUERY, sqlParameterSource.addValue(LANGUAGE_ID_COLUMN_NAME, languageId));
            } else {
                jdbcTemplate.update(ADD_TPERSON_QUERY, sqlParameterSource.addValue(LANGUAGE_ID_COLUMN_NAME, languageId));
            }
        }
    }

    /**
     * Deletes a person from the data storage (with all of the localizations).
     *
     * @param id an id of the deleting person
     */
    @Override
    public void deletePerson(int id) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(ID_COLUMN_NAME, id);

        jdbcTemplate.update(DELETE_PERSON_QUERY, sqlParameterSource);
    }

    /**
     * Returns all the persons from the data storage.
     *
     * @param languageId a language id like 'EN', "RU' etc.
     * @return all the persons
     */
    @Override
    public List<Person> getAllPersons(String languageId) {
        if(languageId.equals(DEFAULT_LANGUAGE_ID)) {
            return jdbcTemplate.query(GET_ALL_PERSONS_QUERY, personMapper);
        } else {
            SqlParameterSource sqlParameterSource = new MapSqlParameterSource(LANGUAGE_ID_COLUMN_NAME, languageId);
            return jdbcTemplate.query(GET_ALL_PERSONS_NOT_DEFAULT_LANG_QUERY, sqlParameterSource, personMapper);
        }
    }

    /**
     * Returns a person by id from the data storage.
     *
     * @param id an id of the needed person
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a person by id
     */
    @Override
    public Person getPersonById(int id, String languageId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(ID_COLUMN_NAME, id);

        List<Person> persons;
        if(languageId.equals(DEFAULT_LANGUAGE_ID)) {
            persons = jdbcTemplate.query(GET_PERSON_BY_ID_QUERY, mapSqlParameterSource, personMapper);
        } else {
            persons = jdbcTemplate.query(GET_PERSON_BY_ID_NOT_DEFAULT_LANG_QUERY, mapSqlParameterSource.addValue(LANGUAGE_ID_COLUMN_NAME, languageId), personMapper);
        }

        return persons.isEmpty() ? null : persons.get(0);
    }

    /**
     * Returns a persons which took part in the movie in the certain role.
     *
     * @param movieId an id of the movie
     * @param relationType an id of the relation type (role)
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a persons which took part in the movie in the certain role
     */
    @Override
    public List<Person> getPersonsByMovieAndRelationType(int movieId, int relationType, String languageId) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(MOVIE_ID_PARAM_NAME, movieId)
                .addValue(RELATION_TYPE_PARAM_NAME, relationType);

        if(languageId.equals(DEFAULT_LANGUAGE_ID)) {
            return jdbcTemplate.query(GET_PERSONS_BY_MOVIE_QUERY, sqlParameterSource, personMapper);
        } else {
            return jdbcTemplate.query(GET_PERSONS_BY_MOVIE_NOT_DEFAULT_LANG_QUERY, sqlParameterSource.addValue(LANGUAGE_ID_COLUMN_NAME, languageId), personMapper);
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
     */
    @Override
    public List<Person> getPersonsByCriteria(String name, int from, int amount, String languageId) {
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

        return jdbcTemplate.query(query.toString(), personMapper);
    }

    /**
     * Returns an amount of persons matching the criteria.
     *
     * @param name a person's name
     * @param languageId a language id like 'EN', "RU' etc.
     * @return an amount of persons matching the criteria
     */
    @Override
    public int getPersonsCountByCriteria(String name, String languageId) {
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

        return jdbcTemplate.queryForObject(query.toString(), new HashMap<>(), Integer.TYPE);
    }

    private class PersonMapper implements RowMapper<Person> {
        @Override
        public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
            Person person = new Person();
            person.setId(rs.getInt(ID_COLUMN_NAME));
            person.setName(rs.getString(NAME_COLUMN_NAME));
            person.setDateOfBirth(rs.getDate(DATE_OF_BIRTH_COLUMN_NAME));
            person.setPlaceOfBirth(rs.getString(PLACE_OF_BIRTH_COLUMN_NAME));
            person.setPhoto(rs.getString(PHOTO_COLUMN_NAME));
            return person;
        }
    }

}
