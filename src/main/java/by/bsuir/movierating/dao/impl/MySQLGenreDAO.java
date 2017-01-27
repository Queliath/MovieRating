package by.bsuir.movierating.dao.impl;

import by.bsuir.movierating.dao.GenreDAO;
import by.bsuir.movierating.domain.Genre;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.HashMap;
import java.util.List;

/**
 * Provides a DAO-logic for the Genre entity for the MySQL Database.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class MySQLGenreDAO implements GenreDAO {
    private static final String ADD_GENRE_QUERY = "INSERT INTO genre " +
            "(name, position) VALUES (:name, :position)";
    private static final String UPDATE_GENRE_QUERY = "UPDATE genre " +
            "SET name = :name, position = :position WHERE id = :id";
    private static final String TGENRE_CHECK_QUERY = "SELECT id FROM tgenre " +
            "WHERE language_id = :language_id AND id = :id";
    private static final String ADD_TGENRE_QUERY = "INSERT INTO tgenre " +
            "(language_id, id, name) VALUES (:language_id, :id, :name)";
    private static final String UPDATE_TGENRE_QUERY = "UPDATE tgenre " +
            "SET name = :name WHERE language_id = :language_id AND id = :id";
    private static final String DELETE_GENRE_QUERY = "DELETE FROM genre WHERE id = :id";
    private static final String GET_ALL_GENRES_QUERY = "SELECT * FROM genre";
    private static final String GET_ALL_GENRES_NOT_DEFAULT_LANG_QUERY = "SELECT g.id, " +
            "coalesce(t.name, g.name), g.position FROM genre AS g " +
            "LEFT JOIN (SELECT * FROM tgenre WHERE language_id = :language_id) AS t USING(id)";
    private static final String GET_GENRE_BY_ID_QUERY = "SELECT * FROM genre WHERE id = :id";
    private static final String GET_GENRE_BY_ID_NOT_DEFAULT_LANG_QUERY = "SELECT g.id, coalesce(t.name, g.name), " +
            " g.position FROM genre AS g LEFT JOIN " +
            "(SELECT * FROM tgenre WHERE language_id = :language_id) AS t USING(id) WHERE g.id = :id";
    private static final String GET_GENRES_BY_MOVIE_QUERY = "SELECT genre.* FROM genre " +
            "INNER JOIN movie_genre ON genre.id = movie_genre.genre_id " +
            "WHERE movie_genre.movie_id = :movie_id";
    private static final String GET_GENRES_BY_MOVIE_NOT_DEFAULT_LANG_QUERY = "SELECT g.id, coalesce(t.name, g.name), " +
            " g.position FROM genre AS g INNER JOIN movie_genre AS mg " +
            "ON g.id = mg.genre_id LEFT JOIN (SELECT * FROM tgenre WHERE language_id = :language_id) AS t " +
            "USING(id) WHERE mg.movie_id = :movie_id";
    private static final String GET_TOP_POSITION_GENRES_QUERY = "SELECT * FROM genre ORDER BY position LIMIT ";
    private static final String GET_TOP_POSITION_GENRES_NOT_DEFAULT_LANG_QUERY = "SELECT g.id, " +
            "coalesce(t.name, g.name), g.position FROM genre AS g " +
            "LEFT JOIN (SELECT * FROM tgenre WHERE language_id = :language_id) AS t USING(id) " +
            "ORDER BY g.position LIMIT ";
    private static final String GET_GENRES_QUERY = "SELECT * FROM genre LIMIT ";
    private static final String GET_GENRES_NOT_DEFAULT_LANG_QUERY = "SELECT g.id, " +
            "coalesce(t.name, g.name), g.position FROM genre AS g " +
            "LEFT JOIN (SELECT * FROM tgenre WHERE language_id = :language_id) AS t USING(id) LIMIT ";
    private static final String GET_GENRES_COUNT_QUERY = "SELECT COUNT(*) FROM genre";

    private static final String ID_COLUMN_NAME = "id";
    private static final String NAME_COLUMN_NAME = "name";
    private static final String POSITION_COLUMN_NAME = "position";
    private static final String LANGUAGE_ID_COLUMN_NAME = "language_id";
    private static final String MOVIE_ID_PARAM_NAME = "movie_id";

    private static final String DEFAULT_LANGUAGE_ID = "EN";

    private NamedParameterJdbcTemplate jdbcTemplate;
    private GenreMapper genreMapper = new GenreMapper();
    private KeyHolder keyHolder = new GeneratedKeyHolder();

    /**
     * Adds a genre to the data storage (in the default language).
     *
     * @param genre a genre object
     */
    @Override
    public void addGenre(Genre genre) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(NAME_COLUMN_NAME, genre.getName())
                .addValue(POSITION_COLUMN_NAME, genre.getPosition());

        jdbcTemplate.update(ADD_GENRE_QUERY, sqlParameterSource, keyHolder, new String[] {ID_COLUMN_NAME});

        genre.setId(keyHolder.getKey().intValue());
    }

    /**
     * Updates a genre or adds/updates a localization of a genre in the data storage.
     *
     * If the languageId argument is an id of the default language of the application, then it updates
     * a genre. If the language argument is an id of the different language (not default) then it
     * adds/updates a localization of a genre (it based on the fact of existence of a localization:
     * if it doesn't exist then it will be added, otherwise a localization will be updated).
     * @param genre a genre object
     * @param languageId a language id like 'EN', "RU' etc.
     */
    @Override
    public void updateGenre(Genre genre, String languageId) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(ID_COLUMN_NAME, genre.getId())
                .addValue(NAME_COLUMN_NAME, genre.getName());

        if(languageId.equals(DEFAULT_LANGUAGE_ID)) {
            jdbcTemplate.update(UPDATE_GENRE_QUERY, sqlParameterSource.addValue(POSITION_COLUMN_NAME, genre.getPosition()));
        } else {
            SqlParameterSource testingSqlParameterSource = new MapSqlParameterSource()
                    .addValue(LANGUAGE_ID_COLUMN_NAME, languageId)
                    .addValue(ID_COLUMN_NAME, genre.getId());
            List<Genre> genres = jdbcTemplate.query(TGENRE_CHECK_QUERY, testingSqlParameterSource, genreMapper);
            if(!genres.isEmpty()) {
                jdbcTemplate.update(UPDATE_TGENRE_QUERY, sqlParameterSource.addValue(LANGUAGE_ID_COLUMN_NAME, languageId));
            } else {
                jdbcTemplate.update(ADD_TGENRE_QUERY, sqlParameterSource.addValue(LANGUAGE_ID_COLUMN_NAME, languageId));
            }
        }
    }

    /**
     * Deletes a genre from the data storage (with all of the localizations).
     *
     * @param id an id of a deleting genre
     */
    @Override
    public void deleteGenre(int id) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(ID_COLUMN_NAME, id);

        jdbcTemplate.update(DELETE_GENRE_QUERY, sqlParameterSource);
    }

    /**
     * Returns all the genres from the data storage.
     *
     * @param languageId a language id like 'EN', "RU' etc.
     * @return all the genres
     */
    @Override
    public List<Genre> getAllGenres(String languageId) {
        if(languageId.equals(DEFAULT_LANGUAGE_ID)) {
            return jdbcTemplate.query(GET_ALL_GENRES_QUERY, genreMapper);
        } else {
            SqlParameterSource sqlParameterSource = new MapSqlParameterSource(LANGUAGE_ID_COLUMN_NAME, languageId);
            return jdbcTemplate.query(GET_ALL_GENRES_NOT_DEFAULT_LANG_QUERY, sqlParameterSource, genreMapper);
        }
    }

    /**
     * Returns a genre by id from the data storage.
     *
     * @param id an id of a needed genre
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a genre by id
     */
    @Override
    public Genre getGenreById(int id, String languageId) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(ID_COLUMN_NAME, id);

        List<Genre> genres;
        if(languageId.equals(DEFAULT_LANGUAGE_ID)) {
            genres = jdbcTemplate.query(GET_GENRE_BY_ID_QUERY, sqlParameterSource, genreMapper);
        } else {
            genres = jdbcTemplate.query(GET_GENRE_BY_ID_NOT_DEFAULT_LANG_QUERY, sqlParameterSource.addValue(LANGUAGE_ID_COLUMN_NAME, languageId), genreMapper);
        }

        return genres.isEmpty() ? null : genres.get(0);
    }

    /**
     * Returns a genres belonging to the movie from the data storage.
     *
     * @param movieId an id of the movie
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a genres belonging to the movie
     */
    @Override
    public List<Genre> getGenresByMovie(int movieId, String languageId) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(MOVIE_ID_PARAM_NAME, movieId);

        if(languageId.equals(DEFAULT_LANGUAGE_ID)) {
            return jdbcTemplate.query(GET_GENRES_BY_MOVIE_QUERY, sqlParameterSource, genreMapper);
        } else {
            return jdbcTemplate.query(GET_GENRES_BY_MOVIE_NOT_DEFAULT_LANG_QUERY, sqlParameterSource.addValue(LANGUAGE_ID_COLUMN_NAME, languageId), genreMapper);
        }
    }

    /**
     * Returns a genres ordered by a position number from the data storage.
     *
     * @param amount a needed amount of genres
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a genres ordered by a position number
     */
    @Override
    public List<Genre> getTopPositionGenres(int amount, String languageId) {
        if(languageId.equals(DEFAULT_LANGUAGE_ID)) {
            return jdbcTemplate.query(GET_TOP_POSITION_GENRES_QUERY + amount, genreMapper);
        } else {
            SqlParameterSource sqlParameterSource = new MapSqlParameterSource(LANGUAGE_ID_COLUMN_NAME, languageId);
            return jdbcTemplate.query(GET_TOP_POSITION_GENRES_NOT_DEFAULT_LANG_QUERY + amount, sqlParameterSource, genreMapper);
        }
    }

    /**
     * Returns a genres from the data storage.
     *
     * @param from a start position in the genres list (started from 0)
     * @param amount a needed amount of genres
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a genres
     */
    @Override
    public List<Genre> getGenres(int from, int amount, String languageId) {
        if(languageId.equals(DEFAULT_LANGUAGE_ID)) {
            return jdbcTemplate.query(GET_GENRES_QUERY + from + ", " + amount, genreMapper);
        } else {
            SqlParameterSource sqlParameterSource = new MapSqlParameterSource(LANGUAGE_ID_COLUMN_NAME, languageId);
            return jdbcTemplate.query(GET_GENRES_NOT_DEFAULT_LANG_QUERY + from + ", " + amount, sqlParameterSource, genreMapper);
        }
    }

    /**
     * Returns an amount of genres in the data storage.
     *
     * @return an amount of genres in the data storage
     */
    @Override
    public int getGenresCount() {
        return jdbcTemplate.queryForObject(GET_GENRES_COUNT_QUERY, new HashMap<>(), Integer.TYPE);
    }

    private class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            Genre genre = new Genre();
            genre.setId(rs.getInt(ID_COLUMN_NAME));
            genre.setName(rs.getString(NAME_COLUMN_NAME));
            genre.setPosition(rs.getInt(POSITION_COLUMN_NAME));
            return genre;
        }
    }
}
