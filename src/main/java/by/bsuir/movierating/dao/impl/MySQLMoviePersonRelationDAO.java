package by.bsuir.movierating.dao.impl;

import by.bsuir.movierating.dao.MoviePersonRelationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * Provides a DAO-logic for the relations between Movie and Person entities for the MySQL Database.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
@Repository("moviePersonRelationDao")
public class MySQLMoviePersonRelationDAO implements MoviePersonRelationDAO {
    private static final String ADD_MOVIE_TO_PERSON_QUERY = "INSERT INTO movie_person_relation " +
            "(movie_id, person_id, relation_type) VALUES (:movie_id, :person_id, :relation_type)";
    private static final String DELETE_MOVIE_FORM_PERSON_QUERY = "DELETE FROM movie_person_relation " +
            "WHERE movie_id = :movie_id AND person_id = :person_id AND relation_type = :relation_type";
    private static final String GET_MOVIES_TOTAL_BY_PERSON_QUERY = "SELECT COUNT(*) FROM " +
            "(SELECT DISTINCT movie_id FROM movie_person_relation WHERE person_id = :person_id) AS c";

    private static final String MOVIE_ID_COLUMN_NAME = "movie_id";
    private static final String PERSON_ID_COLUMN_NAME = "person_id";
    private static final String RELATION_TYPE_COLUMN_NAME = "relation_type";

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * Adds a relation between the movie and the person.
     *
     * @param movieId an id of the movie
     * @param personId an id of the person
     * @param relationType an id of the relation type (role)
     */
    @Override
    public void addMovieToPersonWithRelation(int movieId, int personId, int relationType) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(MOVIE_ID_COLUMN_NAME, movieId)
                .addValue(PERSON_ID_COLUMN_NAME, personId)
                .addValue(RELATION_TYPE_COLUMN_NAME, relationType);

        jdbcTemplate.update(ADD_MOVIE_TO_PERSON_QUERY, sqlParameterSource);
    }

    /**
     * Deletes a relation between the movie and the person.
     *
     * @param movieId an id of the movie
     * @param personId an id of the person
     * @param relationType an id of the relation type (role)
     */
    @Override
    public void deleteMovieFromPersonWithRelation(int movieId, int personId, int relationType) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(MOVIE_ID_COLUMN_NAME, movieId)
                .addValue(PERSON_ID_COLUMN_NAME, personId)
                .addValue(RELATION_TYPE_COLUMN_NAME, relationType);

        jdbcTemplate.update(DELETE_MOVIE_FORM_PERSON_QUERY, sqlParameterSource);
    }

    /**
     * Returns an amount of movies in which this person took part.
     *
     * @param personId an id of the person
     * @return an amount of movies in which this person took part
     */
    @Override
    public int getMoviesTotalByPerson(int personId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(PERSON_ID_COLUMN_NAME, personId);

        return jdbcTemplate.queryForObject(GET_MOVIES_TOTAL_BY_PERSON_QUERY, new HashMap<>(), Integer.TYPE);
    }
}
