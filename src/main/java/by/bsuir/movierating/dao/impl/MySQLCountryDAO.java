package by.bsuir.movierating.dao.impl;

import by.bsuir.movierating.dao.CountryDAO;
import by.bsuir.movierating.domain.Country;
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
 * Provides a DAO-logic for the Country entity for the MySQL Database.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
@Repository("countryDao")
public class MySQLCountryDAO implements CountryDAO {
    private static final String ADD_COUNTRY_QUERY = "INSERT INTO country " +
            "(name, position) VALUES (:name, :position)";
    private static final String UPDATE_COUNTRY_QUERY = "UPDATE country " +
            "SET name = :name, position = :position WHERE id = :id";
    private static final String TCOUNTRY_CHECK_QUERY = "SELECT id FROM tcountry " +
            "WHERE language_id = :language_id AND id = :id";
    private static final String ADD_TCOUNTRY_QUERY = "INSERT INTO tcountry " +
            "(language_id, id, name) VALUES (:language_id, :id, :name)";
    private static final String UPDATE_TCOUNTRY_QUERY = "UPDATE tcountry " +
            "SET name = :name WHERE language_id = :language_id AND id = :id";
    private static final String DELETE_COUNTRY_QUERY = "DELETE FROM country WHERE id = :id";
    private static final String GET_ALL_COUNTRIES_QUERY = "SELECT * FROM country";
    private static final String GET_ALL_COUNTRIES_NOT_DEFAULT_LANG_QUERY = "SELECT c.id, " +
            "coalesce(t.name, c.name), c.position FROM country AS c LEFT JOIN " +
            "(SELECT * FROM tcountry WHERE language_id = :language_id) AS t USING(id)";
    private static final String GET_COUNTRY_BY_ID_QUERY = "SELECT * FROM country WHERE id = :id";
    private static final String GET_COUNTRY_BY_ID_NOT_DEFAULT_LANG_QUERY = "SELECT c.id, " +
            "coalesce(t.name, c.name), c.position FROM country AS c LEFT JOIN " +
            "(SELECT * FROM tcountry WHERE language_id = :language_id) AS t USING(id) WHERE c.id = :id";
    private static final String GET_COUNTRIES_BY_MOVIE_QUERY = "SELECT country.* FROM country " +
            "INNER JOIN movie_country ON country.id = movie_country.country_id " +
            "WHERE movie_country.movie_id = :movie_id";
    private static final String GET_COUNTRIES_BY_MOVIE_NOT_DEFAULT_LANG_QUERY = "SELECT c.id, coalesce(t.name, c.name), " +
            " c.position FROM country AS c INNER JOIN movie_country AS mc ON c.id = mc.country_id " +
            "LEFT JOIN (SELECT * FROM tcountry WHERE language_id = :language_id) AS t USING(id) WHERE mc.movie_id = :movie_id";
    private static final String GET_TOP_POSITION_COUNTRIES_QUERY = "SELECT * FROM country ORDER BY position LIMIT ";
    private static final String GET_TOP_POSITION_COUNTRIES_NOT_DEFAULT_LANG_QUERY = "SELECT c.id, " +
            "coalesce(t.name, c.name), c.position FROM country AS c LEFT JOIN " +
            "(SELECT * FROM tcountry WHERE language_id = :language_id) AS t USING(id) " +
            "ORDER BY c.position LIMIT ";
    private static final String GET_COUNTRIES_QUERY = "SELECT * FROM country LIMIT ";
    private static final String GET_COUNTRIES_NOT_DEFAULT_LANG_QUERY = "SELECT c.id, " +
            "coalesce(t.name, c.name), c.position FROM country AS c LEFT JOIN " +
            "(SELECT * FROM tcountry WHERE language_id = :language_id) AS t USING(id) LIMIT ";
    private static final String GET_COUNTRIES_COUNT_QUERY = "SELECT COUNT(*) FROM country";

    private static final String ID_COLUMN_NAME = "id";
    private static final String NAME_COLUMN_NAME = "name";
    private static final String POSITION_COLUMN_NAME = "position";
    private static final String LANGUAGE_ID_COLUMN_NAME = "language_id";
    private static final String MOVIE_ID_PARAM_NAME = "movie_id";

    private static final String DEFAULT_LANGUAGE_ID = "EN";

    private NamedParameterJdbcTemplate jdbcTemplate;
    private CountryMapper countryMapper = new CountryMapper();
    private KeyHolder keyHolder = new GeneratedKeyHolder();

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * Adds a country to the data storage (in the default language).
     *
     * @param country a country object
     */
    @Override
    public void addCountry(Country country) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(NAME_COLUMN_NAME, country.getName())
                .addValue(POSITION_COLUMN_NAME, country.getPosition());

        jdbcTemplate.update(ADD_COUNTRY_QUERY, sqlParameterSource, keyHolder, new String[] {ID_COLUMN_NAME});

        country.setId(keyHolder.getKey().intValue());
    }

    /**
     * Updates a country or adds/updates a localization of a country in the data storage.
     *
     * If the languageId argument is an id of the default language of the application, then it updates
     * a country. If the language argument is an id of the different language (not default) then it
     * adds/updates a localization of a country (it based on the fact of existence of a localization:
     * if it doesn't exist then it will be added, otherwise a localization will be updated).
     * @param country a country object
     * @param languageId a language id like 'EN', "RU' etc.
     */
    @Override
    public void updateCountry(Country country, String languageId) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(ID_COLUMN_NAME, country.getId())
                .addValue(NAME_COLUMN_NAME, country.getName());

        if(languageId.equals(DEFAULT_LANGUAGE_ID)) {
            jdbcTemplate.update(UPDATE_COUNTRY_QUERY, sqlParameterSource.addValue(POSITION_COLUMN_NAME, country.getPosition()));
        } else {
            SqlParameterSource testingSqlParameterSource = new MapSqlParameterSource()
                    .addValue(LANGUAGE_ID_COLUMN_NAME, languageId)
                    .addValue(ID_COLUMN_NAME, country.getId());
            List<Country> countries = jdbcTemplate.query(TCOUNTRY_CHECK_QUERY, testingSqlParameterSource, countryMapper);
            if(!countries.isEmpty()) {
                jdbcTemplate.update(UPDATE_TCOUNTRY_QUERY, sqlParameterSource.addValue(LANGUAGE_ID_COLUMN_NAME, languageId));
            } else {
                jdbcTemplate.update(ADD_TCOUNTRY_QUERY, sqlParameterSource.addValue(LANGUAGE_ID_COLUMN_NAME, languageId));
            }
        }
    }

    /**
     * Deletes a country from the data storage (with all of the localizations).
     *
     * @param id an id of a deleting country
     */
    @Override
    public void deleteCountry(int id) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(ID_COLUMN_NAME, id);

        jdbcTemplate.update(DELETE_COUNTRY_QUERY, sqlParameterSource);
    }

    /**
     * Returns all the countries from data storage.
     *
     * @param languageId a language id like 'EN', "RU' etc.
     * @return all the countries
     */
    @Override
    public List<Country> getAllCountries(String languageId) {
        if(languageId.equals(DEFAULT_LANGUAGE_ID)) {
            return jdbcTemplate.query(GET_ALL_COUNTRIES_QUERY, countryMapper);
        } else {
            SqlParameterSource sqlParameterSource = new MapSqlParameterSource(LANGUAGE_ID_COLUMN_NAME, languageId);
            return jdbcTemplate.query(GET_ALL_COUNTRIES_NOT_DEFAULT_LANG_QUERY, sqlParameterSource, countryMapper);
        }
    }

    /**
     * Returns a country by id from data storage.
     *
     * @param id an id of a needed country
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a country by id
     */
    @Override
    public Country getCountryById(int id, String languageId) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(ID_COLUMN_NAME, id);

        List<Country> countries;
        if(languageId.equals(DEFAULT_LANGUAGE_ID)) {
            countries = jdbcTemplate.query(GET_COUNTRY_BY_ID_QUERY, sqlParameterSource, countryMapper);
        } else {
            countries = jdbcTemplate.query(GET_COUNTRY_BY_ID_NOT_DEFAULT_LANG_QUERY, sqlParameterSource.addValue(LANGUAGE_ID_COLUMN_NAME, languageId), countryMapper);
        }

        return countries.isEmpty() ? null : countries.get(0);
    }

    /**
     * Returns a countries belonging to the movie from the data storage.
     *
     * @param movieId an id of the movie
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a countries belonging to the movie
     */
    @Override
    public List<Country> getCountriesByMovie(int movieId, String languageId) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(MOVIE_ID_PARAM_NAME, movieId);

        if(languageId.equals(DEFAULT_LANGUAGE_ID)) {
            return jdbcTemplate.query(GET_COUNTRIES_BY_MOVIE_QUERY, sqlParameterSource, countryMapper);
        } else {
            return jdbcTemplate.query(GET_COUNTRIES_BY_MOVIE_NOT_DEFAULT_LANG_QUERY, sqlParameterSource.addValue(LANGUAGE_ID_COLUMN_NAME, languageId), countryMapper);
        }
    }

    /**
     * Returns a countries ordered by a position number from the data storage.
     *
     * @param amount a needed amount of countries
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a countries ordered by a position number
     */
    @Override
    public List<Country> getTopPositionCountries(int amount, String languageId) {
        if(languageId.equals(DEFAULT_LANGUAGE_ID)) {
            return jdbcTemplate.query(GET_TOP_POSITION_COUNTRIES_QUERY + amount, countryMapper);
        } else {
            SqlParameterSource sqlParameterSource = new MapSqlParameterSource(LANGUAGE_ID_COLUMN_NAME, languageId);
            return jdbcTemplate.query(GET_TOP_POSITION_COUNTRIES_NOT_DEFAULT_LANG_QUERY + amount, sqlParameterSource, countryMapper);
        }
    }

    /**
     * Returns a countries from data storage.
     *
     * @param from a start position in the countries list (started from 0)
     * @param amount a needed amount of countries
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a countries from data storage
     */
    @Override
    public List<Country> getCountries(int from, int amount, String languageId) {
        if(languageId.equals(DEFAULT_LANGUAGE_ID)) {
            return jdbcTemplate.query(GET_COUNTRIES_QUERY + from + ", " + amount, countryMapper);
        } else {
            SqlParameterSource sqlParameterSource = new MapSqlParameterSource(LANGUAGE_ID_COLUMN_NAME, languageId);
            return jdbcTemplate.query(GET_COUNTRIES_NOT_DEFAULT_LANG_QUERY + from + ", " + amount, sqlParameterSource, countryMapper);
        }
    }

    /**
     * Returns an amount of countries in the data storage.
     *
     * @return an amount of countries in the data storage
     */
    @Override
    public int getCountriesCount() {
        return jdbcTemplate.queryForObject(GET_COUNTRIES_COUNT_QUERY, new HashMap<>(), Integer.TYPE);
    }

    private class CountryMapper implements RowMapper<Country> {
        @Override
        public Country mapRow(ResultSet rs, int rowNum) throws SQLException {
            Country country = new Country();
            country.setId(rs.getInt(ID_COLUMN_NAME));
            country.setName(rs.getString(NAME_COLUMN_NAME));
            country.setPosition(rs.getInt(POSITION_COLUMN_NAME));
            return country;
        }
    }
}
