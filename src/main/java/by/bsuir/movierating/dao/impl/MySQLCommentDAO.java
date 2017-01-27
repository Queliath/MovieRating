package by.bsuir.movierating.dao.impl;

import by.bsuir.movierating.domain.Comment;
import by.bsuir.movierating.dao.CommentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides a DAO-logic for the Comment entity for the MySQL Database.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
@Repository("commentDao")
public class MySQLCommentDAO implements CommentDAO {
    private static final String ADD_COMMENT_QUERY = "INSERT INTO " +
            "comment (movie_id, user_id, title, content, date_of_publication, language_id) " +
            "VALUES (:movie_id, :user_id, :title, :content, :date_of_publication, :language_id)";
    private static final String UPDATE_COMMENT_QUERY = "UPDATE comment " +
            "SET movie_id = :movie_id, user_id = :user_id, title = :title, content = :content, date_of_publication = :date_of_publication WHERE id = :id";
    private static final String DELETE_COMMENT_QUERY = "DELETE FROM comment WHERE id = :id";
    private static final String GET_ALL_COMMENTS_QUERY = "SELECT * FROM comment WHERE language_id = :language_id";
    private static final String GET_COMMENT_BY_ID_QUERY = "SELECT * FROM comment WHERE id = :id";
    private static final String GET_COMMENTS_BY_MOVIE_QUERY = "SELECT * FROM comment WHERE movie_id = :movie_id " +
            "AND language_id = :language_id";
    private static final String GET_COMMENTS_BY_USER_QUERY = "SELECT * FROM comment WHERE user_id = :user_id " +
            "AND language_id = :language_id";
    private static final String GET_RECENT_ADDED_COMMENTS_QUERY = "SELECT * FROM comment " +
            "WHERE language_id = :language_id ORDER BY id DESC LIMIT ";

    private static final String ID_COLUMN_NAME = "id";
    private static final String MOVIE_ID_COLUMN_NAME = "movie_id";
    private static final String USER_ID_COLUMN_NAME = "user_id";
    private static final String TITLE_COLUMN_NAME = "title";
    private static final String CONTENT_COLUMN_NAME = "content";
    private static final String DATE_OF_PUBLICATION_COLUMN_NAME = "date_of_publication";
    private static final String LANGUAGE_ID_COLUMN_NAME = "language_id";

    private NamedParameterJdbcTemplate jdbcTemplate;
    private CommentMapper commentMapper = new CommentMapper();
    private KeyHolder keyHolder = new GeneratedKeyHolder();

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * Adds a comment to the data storage.
     *
     * @param comment a comment object
     * @param languageId a language id like 'EN', "RU' etc.
     */
    @Override
    public void addComment(Comment comment, String languageId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(MOVIE_ID_COLUMN_NAME, comment.getMovieId())
                .addValue(USER_ID_COLUMN_NAME, comment.getUserId())
                .addValue(TITLE_COLUMN_NAME, comment.getTitle())
                .addValue(CONTENT_COLUMN_NAME, comment.getContent())
                .addValue(DATE_OF_PUBLICATION_COLUMN_NAME, new Date(comment.getDateOfPublication().getTime()))
                .addValue(LANGUAGE_ID_COLUMN_NAME, languageId);

        jdbcTemplate.update(ADD_COMMENT_QUERY, sqlParameterSource, keyHolder, new String[] {ID_COLUMN_NAME});

        comment.setId(keyHolder.getKey().intValue());
    }

    /**
     * Updates a comment in the data storage.
     *
     * @param comment a comment object
     */
    @Override
    public void updateComment(Comment comment) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(MOVIE_ID_COLUMN_NAME, comment.getMovieId())
                .addValue(USER_ID_COLUMN_NAME, comment.getUserId())
                .addValue(TITLE_COLUMN_NAME, comment.getTitle())
                .addValue(CONTENT_COLUMN_NAME, comment.getContent())
                .addValue(DATE_OF_PUBLICATION_COLUMN_NAME, new Date(comment.getDateOfPublication().getTime()))
                .addValue(ID_COLUMN_NAME, comment.getId());

        jdbcTemplate.update(UPDATE_COMMENT_QUERY, sqlParameterSource);
    }

    /**
     * Deletes a comment from the data storage.
     *
     * @param id - an id of a deleting comment
     */
    @Override
    public void deleteComment(int id) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(ID_COLUMN_NAME, id);

        jdbcTemplate.update(DELETE_COMMENT_QUERY, sqlParameterSource);
    }

    /**
     * Returns all the comments from the data storage.
     *
     * @param languageId a language id like 'EN', "RU' etc.
     * @return all the comments from the data storage
     */
    @Override
    public List<Comment> getAllComments(String languageId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(LANGUAGE_ID_COLUMN_NAME, languageId);

        return jdbcTemplate.query(GET_ALL_COMMENTS_QUERY, sqlParameterSource, commentMapper);
    }

    /**
     * Returns a comment from the data storage by id.
     *
     * @param id an id of a needed comment
     * @return a comment object
     */
    @Override
    public Comment getCommentById(int id) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(ID_COLUMN_NAME, id);

        List<Comment> comments = jdbcTemplate.query(GET_COMMENT_BY_ID_QUERY, sqlParameterSource, commentMapper);
        return comments.isEmpty() ? null : comments.get(0);
    }

    /**
     * Returns a comments from the data storage belonging to the movie.
     *
     * @param movieId an id of the movie
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a comments belonging to the movie
     */
    @Override
    public List<Comment> getCommentsByMovie(int movieId, String languageId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(MOVIE_ID_COLUMN_NAME, movieId)
                .addValue(LANGUAGE_ID_COLUMN_NAME, languageId);

        return jdbcTemplate.query(GET_COMMENTS_BY_MOVIE_QUERY, sqlParameterSource, commentMapper);
    }

    /**
     * Returns a comments from the data storage belonging to the user.
     *
     * @param userId an id of the user
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a comments belonging to the user
     */
    @Override
    public List<Comment> getCommentsByUser(int userId, String languageId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(USER_ID_COLUMN_NAME, userId)
                .addValue(LANGUAGE_ID_COLUMN_NAME, languageId);

        return jdbcTemplate.query(GET_COMMENTS_BY_USER_QUERY, sqlParameterSource, commentMapper);
    }

    /**
     * Returns a recent added comments from the data storage.
     *
     * @param amount a needed amount of a comment
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a recent added comments
     */
    @Override
    public List<Comment> getRecentAddedComments(int amount, String languageId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(LANGUAGE_ID_COLUMN_NAME, languageId);

        return jdbcTemplate.query(GET_RECENT_ADDED_COMMENTS_QUERY + amount, sqlParameterSource, commentMapper);
    }

    private class CommentMapper implements RowMapper<Comment> {
        @Override
        public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
            Comment comment = new Comment();
            comment.setId(rs.getInt(ID_COLUMN_NAME));
            comment.setMovieId(rs.getInt(MOVIE_ID_COLUMN_NAME));
            comment.setUserId(rs.getInt(USER_ID_COLUMN_NAME));
            comment.setTitle(rs.getString(TITLE_COLUMN_NAME));
            comment.setContent(rs.getString(CONTENT_COLUMN_NAME));
            comment.setDateOfPublication(rs.getDate(DATE_OF_PUBLICATION_COLUMN_NAME));
            return comment;
        }
    }
}
