package by.bsuir.movierating.dao.impl;

import by.bsuir.movierating.dao.UserDAO;
import by.bsuir.movierating.domain.User;
import by.bsuir.movierating.domain.criteria.UserCriteria;
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
 * Provides a DAO-logic for the User entity for the MySQL Database.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
@Repository("userDao")
public class MySQLUserDAO implements UserDAO {
    private static final String ADD_USER_QUERY = "INSERT INTO user " +
            "(email, password, first_name, last_name, date_of_registry, photo, rating, status, language_id) " +
            "VALUES (:email, :password, :first_name, :last_name, :date_of_registry, :photo, :rating, :status, :language_id)";
    private static final String UPDATE_USER_QUERY = "UPDATE user " +
            "SET email = :email, password = :password, first_name = :first_name, last_name = :last_name, date_of_registry = :date_of_registry, " +
            "photo = :photo, rating = :rating, status = :status, language_id = :language_id WHERE id = :id";
    private static final String DELETE_USER_QUERY = "DELETE FROM user WHERE id = :id";
    private static final String GET_ALL_USERS_QUERY = "SELECT * FROM user";
    private static final String GET_USER_BY_ID_QUERY = "SELECT * FROM user WHERE id = :id";
    private static final String GET_USER_BY_EMAIL_QUERY = "SELECT * FROM user WHERE email = :email";
    private static final String GET_USERS_BY_STATUS_QUERY = "SELECT * FROM user WHERE status = :status";
    private static final String GET_USERS_BY_CRITERIA_HEAD_QUERY = "SELECT * FROM user ";
    private static final String WHERE_CRITERIA = "WHERE";
    private static final String AND_CRITERIA = "AND";
    private static final String SPACE_SEPARATOR = " ";
    private static final String COMA_SEPARATOR = ",";
    private static final String CLOSING_BRACKET = ") ";
    private static final String SINGLE_QUOTE = "'";
    private static final String GET_USERS_BY_CRITERIA_EMAIL_CRITERIA_QUERY_FIRST_PART = "WHERE email LIKE '%";
    private static final String GET_USERS_BY_CRITERIA_EMAIL_CRITERIA_QUERY_SECOND_PART = "%' ";
    private static final String GET_USERS_BY_CRITERIA_FIRST_NAME_CRITERIA_QUERY_FIRST_PART = " first_name LIKE '%";
    private static final String GET_USERS_BY_CRITERIA_FIRST_NAME_CRITERIA_QUERY_SECOND_PART = "%' ";
    private static final String GET_USERS_BY_CRITERIA_LAST_NAME_CRITERIA_QUERY_FIRST_PART = " last_name LIKE '%";
    private static final String GET_USERS_BY_CRITERIA_LAST_NAME_CRITERIA_QUERY_SECOND_PART = "%' ";
    private static final String GET_USERS_BY_CRITERIA_MIN_DATE_OF_REGISTRATION_CRITERIA_QUERY_FIRST_PART = " date_of_registry > '";
    private static final String GET_USERS_BY_CRITERIA_MIN_DATE_OF_REGISTRATION_CRITERIA_QUERY_SECOND_PART = "' ";
    private static final String GET_USERS_BY_CRITERIA_MAX_DATE_OF_REGISTRATION_CRITERIA_QUERY_FIRST_PART = " date_of_registry < '";
    private static final String GET_USERS_BY_CRITERIA_MAX_DATE_OF_REGISTRATION_CRITERIA_QUERY_SECOND_PART = "' ";
    private static final String GET_USERS_BY_CRITERIA_MIN_RATING_CRITERIA_QUERY = " rating > ";
    private static final String GET_USERS_BY_CRITERIA_MAX_RATING_CRITERIA_QUERY = " rating < ";
    private static final String GET_USERS_BY_CRITERIA_STATUSES_LIST_CRITERIA_QUERY = " status IN (";
    private static final String LIMIT_QUERY = "LIMIT ";
    private static final String GET_USERS_COUNT_BY_CRITERIA_HEAD_QUERY = "SELECT COUNT(*) FROM (SELECT * FROM user ";
    private static final String GET_USERS_COUNT_BY_CRITERIA_TAIL_QUERY = ") AS c";

    private static final String ID_COLUMN_NAME = "id";
    private static final String EMAIL_COLUMN_NAME = "email";
    private static final String PASSWORD_COLUMN_NAME = "password";
    private static final String FIRST_NAME_COLUMN_NAME = "first_name";
    private static final String LAST_NAME_COLUMN_NAME = "last_name";
    private static final String DATE_OF_REGISTRY_COLUMN_NAME = "date_of_registry";
    private static final String PHOTO_COLUMN_NAME = "photo";
    private static final String RATING_COLUMN_NAME = "rating";
    private static final String STATUS_COLUMN_NAME = "status";
    private static final String LANGUAGE_ID_COLUMN_NAME = "language_id";

    private NamedParameterJdbcTemplate jdbcTemplate;
    private UserMapper userMapper = new UserMapper();
    private KeyHolder keyHolder = new GeneratedKeyHolder();

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * Adds an user to the data storage.
     *
     * @param user an user object
     */
    @Override
    public void addUser(User user)  {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(EMAIL_COLUMN_NAME, user.getEmail())
                .addValue(PASSWORD_COLUMN_NAME, user.getPassword())
                .addValue(FIRST_NAME_COLUMN_NAME, user.getFirstName())
                .addValue(LAST_NAME_COLUMN_NAME, user.getLastName())
                .addValue(DATE_OF_REGISTRY_COLUMN_NAME, new Date(user.getDateOfRegistry().getTime()))
                .addValue(PHOTO_COLUMN_NAME, user.getPhoto())
                .addValue(RATING_COLUMN_NAME, user.getRating())
                .addValue(STATUS_COLUMN_NAME, user.getStatus())
                .addValue(LANGUAGE_ID_COLUMN_NAME, user.getLanguageId());

        jdbcTemplate.update(ADD_USER_QUERY, sqlParameterSource, keyHolder, new String[] {ID_COLUMN_NAME});

        user.setId(keyHolder.getKey().intValue());
    }

    /**
     * Updates an user in the data storage.
     *
     * @param user an user object
     */
    @Override
    public void updateUser(User user) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(EMAIL_COLUMN_NAME, user.getEmail())
                .addValue(PASSWORD_COLUMN_NAME, user.getPassword())
                .addValue(FIRST_NAME_COLUMN_NAME, user.getFirstName())
                .addValue(LAST_NAME_COLUMN_NAME, user.getLastName())
                .addValue(DATE_OF_REGISTRY_COLUMN_NAME, new Date(user.getDateOfRegistry().getTime()))
                .addValue(PHOTO_COLUMN_NAME, user.getPhoto())
                .addValue(RATING_COLUMN_NAME, user.getRating())
                .addValue(STATUS_COLUMN_NAME, user.getStatus())
                .addValue(LANGUAGE_ID_COLUMN_NAME, user.getLanguageId())
                .addValue(ID_COLUMN_NAME, user.getId());

        jdbcTemplate.update(UPDATE_USER_QUERY, sqlParameterSource);
    }

    /**
     * Deletes an user from the data storage.
     *
     * @param id an id of the deleting user
     */
    @Override
    public void deleteUser(int id) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(ID_COLUMN_NAME, id);

        jdbcTemplate.update(DELETE_USER_QUERY, sqlParameterSource);
    }

    /**
     * Returns all the users from the data storage.
     *
     * @return all the users from
     */
    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query(GET_ALL_USERS_QUERY, userMapper);
    }

    /**
     * Returns an user by id from the data storage.
     *
     * @param id an id of the needed user
     * @return an user by id
     */
    @Override
    public User getUserById(int id) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(ID_COLUMN_NAME, id);

        List<User> users = jdbcTemplate.query(GET_USER_BY_ID_QUERY, sqlParameterSource, userMapper);
        return users.isEmpty() ? null : users.get(0);
    }

    /**
     * Returns an user by the email from the data storage.
     *
     * @param email an email of the needed user
     * @return an user by email
     */
    @Override
    public User getUserByEmail(String email) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(EMAIL_COLUMN_NAME, email);

        List<User> users = jdbcTemplate.query(GET_USER_BY_EMAIL_QUERY, sqlParameterSource, userMapper);
        return users.isEmpty() ? null : users.get(0);
    }

    /**
     * Returns an users by the status from the data storage.
     *
     * @param status a status of the needed user
     * @return an users by the status
     */
    @Override
    public List<User> getUsersByStatus(String status) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(STATUS_COLUMN_NAME, status);

        return jdbcTemplate.query(GET_USERS_BY_STATUS_QUERY, sqlParameterSource, userMapper);
    }

    /**
     * Returns an users matching the criteria from the data storage.
     *
     * @param criteria a UserCriteria object
     * @param from a starting position in users list (starting from 0)
     * @param amount a needed amount of users
     * @return an users matching the criteria
     */
    @Override
    public List<User> getUsersByCriteria(UserCriteria criteria, int from, int amount) {
        StringBuilder query = new StringBuilder(GET_USERS_BY_CRITERIA_HEAD_QUERY);
        boolean atLeastOneWhereCriteria = false;
        if(criteria.getEmail() != null){
            query.append(GET_USERS_BY_CRITERIA_EMAIL_CRITERIA_QUERY_FIRST_PART);
            query.append(criteria.getEmail());
            query.append(GET_USERS_BY_CRITERIA_EMAIL_CRITERIA_QUERY_SECOND_PART);
            atLeastOneWhereCriteria = true;
        }
        if(criteria.getFirstName() != null){
            query.append(atLeastOneWhereCriteria ? AND_CRITERIA : WHERE_CRITERIA);
            query.append(GET_USERS_BY_CRITERIA_FIRST_NAME_CRITERIA_QUERY_FIRST_PART);
            query.append(criteria.getFirstName());
            query.append(GET_USERS_BY_CRITERIA_FIRST_NAME_CRITERIA_QUERY_SECOND_PART);
            atLeastOneWhereCriteria = true;
        }
        if(criteria.getLastName() != null){
            query.append(atLeastOneWhereCriteria ? AND_CRITERIA : WHERE_CRITERIA);
            query.append(GET_USERS_BY_CRITERIA_LAST_NAME_CRITERIA_QUERY_FIRST_PART);
            query.append(criteria.getLastName());
            query.append(GET_USERS_BY_CRITERIA_LAST_NAME_CRITERIA_QUERY_SECOND_PART);
            atLeastOneWhereCriteria = true;
        }
        if(criteria.getMinDateOfRegistry() != null){
            query.append(atLeastOneWhereCriteria ? AND_CRITERIA : WHERE_CRITERIA);
            query.append(GET_USERS_BY_CRITERIA_MIN_DATE_OF_REGISTRATION_CRITERIA_QUERY_FIRST_PART);
            query.append(criteria.getMinDateOfRegistry());
            query.append(GET_USERS_BY_CRITERIA_MIN_DATE_OF_REGISTRATION_CRITERIA_QUERY_SECOND_PART);
            atLeastOneWhereCriteria = true;
        }
        if(criteria.getMaxDateOfRegistry() != null){
            query.append(atLeastOneWhereCriteria ? AND_CRITERIA : WHERE_CRITERIA);
            query.append(GET_USERS_BY_CRITERIA_MAX_DATE_OF_REGISTRATION_CRITERIA_QUERY_FIRST_PART);
            query.append(criteria.getMaxDateOfRegistry());
            query.append(GET_USERS_BY_CRITERIA_MAX_DATE_OF_REGISTRATION_CRITERIA_QUERY_SECOND_PART);
            atLeastOneWhereCriteria = true;
        }
        if(criteria.getMinRating() != null){
            query.append(atLeastOneWhereCriteria ? AND_CRITERIA : WHERE_CRITERIA);
            query.append(GET_USERS_BY_CRITERIA_MIN_RATING_CRITERIA_QUERY);
            query.append(criteria.getMinRating());
            query.append(SPACE_SEPARATOR);
            atLeastOneWhereCriteria = true;
        }
        if(criteria.getMaxRating() != null){
            query.append(atLeastOneWhereCriteria ? AND_CRITERIA : WHERE_CRITERIA);
            query.append(GET_USERS_BY_CRITERIA_MAX_RATING_CRITERIA_QUERY);
            query.append(criteria.getMaxRating());
            query.append(SPACE_SEPARATOR);
            atLeastOneWhereCriteria = true;
        }
        if(criteria.getStatuses() != null){
            query.append(atLeastOneWhereCriteria ? AND_CRITERIA : WHERE_CRITERIA);
            query.append(GET_USERS_BY_CRITERIA_STATUSES_LIST_CRITERIA_QUERY);
            for(String status : criteria.getStatuses()){
                query.append(SINGLE_QUOTE);
                query.append(status);
                query.append(SINGLE_QUOTE);
                query.append(COMA_SEPARATOR);
            }
            query.deleteCharAt(query.length() - 1);
            query.append(CLOSING_BRACKET);
        }
        if(amount != 0){
            query.append(LIMIT_QUERY);
            query.append(from);
            query.append(COMA_SEPARATOR);
            query.append(SPACE_SEPARATOR);
            query.append(amount);
            query.append(SPACE_SEPARATOR);
        }

        return jdbcTemplate.query(query.toString(), userMapper);
    }

    /**
     * Returns an amount of users matching the criteria.
     *
     * @param criteria a UserCriteria object
     * @return an amount of users matching the criteria
     */
    @Override
    public int getUsersCountByCriteria(UserCriteria criteria) {
        StringBuilder query = new StringBuilder(GET_USERS_COUNT_BY_CRITERIA_HEAD_QUERY);
        boolean atLeastOneWhereCriteria = false;
        if(criteria.getEmail() != null){
            query.append(GET_USERS_BY_CRITERIA_EMAIL_CRITERIA_QUERY_FIRST_PART);
            query.append(criteria.getEmail());
            query.append(GET_USERS_BY_CRITERIA_EMAIL_CRITERIA_QUERY_SECOND_PART);
            atLeastOneWhereCriteria = true;
        }
        if(criteria.getFirstName() != null){
            query.append(atLeastOneWhereCriteria ? AND_CRITERIA : WHERE_CRITERIA);
            query.append(GET_USERS_BY_CRITERIA_FIRST_NAME_CRITERIA_QUERY_FIRST_PART);
            query.append(criteria.getFirstName());
            query.append(GET_USERS_BY_CRITERIA_FIRST_NAME_CRITERIA_QUERY_SECOND_PART);
            atLeastOneWhereCriteria = true;
        }
        if(criteria.getLastName() != null){
            query.append(atLeastOneWhereCriteria ? AND_CRITERIA : WHERE_CRITERIA);
            query.append(GET_USERS_BY_CRITERIA_LAST_NAME_CRITERIA_QUERY_FIRST_PART);
            query.append(criteria.getLastName());
            query.append(GET_USERS_BY_CRITERIA_LAST_NAME_CRITERIA_QUERY_SECOND_PART);
            atLeastOneWhereCriteria = true;
        }
        if(criteria.getMinDateOfRegistry() != null){
            query.append(atLeastOneWhereCriteria ? AND_CRITERIA : WHERE_CRITERIA);
            query.append(GET_USERS_BY_CRITERIA_MIN_DATE_OF_REGISTRATION_CRITERIA_QUERY_FIRST_PART);
            query.append(criteria.getMinDateOfRegistry());
            query.append(GET_USERS_BY_CRITERIA_MIN_DATE_OF_REGISTRATION_CRITERIA_QUERY_SECOND_PART);
            atLeastOneWhereCriteria = true;
        }
        if(criteria.getMaxDateOfRegistry() != null){
            query.append(atLeastOneWhereCriteria ? AND_CRITERIA : WHERE_CRITERIA);
            query.append(GET_USERS_BY_CRITERIA_MAX_DATE_OF_REGISTRATION_CRITERIA_QUERY_FIRST_PART);
            query.append(criteria.getMaxDateOfRegistry());
            query.append(GET_USERS_BY_CRITERIA_MAX_DATE_OF_REGISTRATION_CRITERIA_QUERY_SECOND_PART);
            atLeastOneWhereCriteria = true;
        }
        if(criteria.getMinRating() != null){
            query.append(atLeastOneWhereCriteria ? AND_CRITERIA : WHERE_CRITERIA);
            query.append(GET_USERS_BY_CRITERIA_MIN_RATING_CRITERIA_QUERY);
            query.append(criteria.getMinRating());
            query.append(SPACE_SEPARATOR);
            atLeastOneWhereCriteria = true;
        }
        if(criteria.getMaxRating() != null){
            query.append(atLeastOneWhereCriteria ? AND_CRITERIA : WHERE_CRITERIA);
            query.append(GET_USERS_BY_CRITERIA_MAX_RATING_CRITERIA_QUERY);
            query.append(criteria.getMaxRating());
            query.append(SPACE_SEPARATOR);
            atLeastOneWhereCriteria = true;
        }
        if(criteria.getStatuses() != null){
            query.append(atLeastOneWhereCriteria ? AND_CRITERIA : WHERE_CRITERIA);
            query.append(GET_USERS_BY_CRITERIA_STATUSES_LIST_CRITERIA_QUERY);
            for(String status : criteria.getStatuses()){
                query.append(SINGLE_QUOTE);
                query.append(status);
                query.append(SINGLE_QUOTE);
                query.append(COMA_SEPARATOR);
            }
            query.deleteCharAt(query.length() - 1);
            query.append(CLOSING_BRACKET);
        }
        query.append(GET_USERS_COUNT_BY_CRITERIA_TAIL_QUERY);

        return jdbcTemplate.queryForObject(query.toString(), new HashMap<>(), Integer.TYPE);
    }

    private class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt(ID_COLUMN_NAME));
            user.setEmail(rs.getString(EMAIL_COLUMN_NAME));
            user.setPassword(rs.getString(PASSWORD_COLUMN_NAME));
            user.setFirstName(rs.getString(FIRST_NAME_COLUMN_NAME));
            user.setLastName(rs.getString(LAST_NAME_COLUMN_NAME));
            user.setDateOfRegistry(rs.getDate(DATE_OF_REGISTRY_COLUMN_NAME));
            user.setPhoto(rs.getString(PHOTO_COLUMN_NAME));
            user.setRating(rs.getInt(RATING_COLUMN_NAME));
            user.setStatus(rs.getString(STATUS_COLUMN_NAME));
            user.setLanguageId(rs.getString(LANGUAGE_ID_COLUMN_NAME));
            return user;
        }
    }
}
