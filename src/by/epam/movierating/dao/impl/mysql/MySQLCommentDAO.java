package by.epam.movierating.dao.impl.mysql;

import by.epam.movierating.dao.interfaces.CommentDAO;
import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPool;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPoolException;
import by.epam.movierating.domain.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 11.06.2016.
 */
public class MySQLCommentDAO implements CommentDAO {
    private static final String ADD_COMMENT_QUERY = "INSERT INTO " +
            "comment (movie_id, user_id, title, content, date_of_publication, language_id) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_COMMENT_QUERY = "UPDATE comment " +
            "SET movie_id = ?, user_id = ?, title = ?, content = ?, date_of_publication = ? WHERE id = ?";
    private static final String DELETE_COMMENT_QUERY = "DELETE FROM comment WHERE id = ?";
    private static final String GET_ALL_COMMENTS_QUERY = "SELECT * FROM comment WHERE language_id = ?";
    private static final String GET_COMMENT_BY_ID_QUERY = "SELECT * FROM comment WHERE id = ?";
    private static final String GET_COMMENTS_BY_MOVIE_QUERY = "SELECT * FROM comment WHERE movie_id = ? " +
            "AND language_id = ?";
    private static final String GET_COMMENTS_BY_USER_QUERY = "SELECT * FROM comment WHERE user_id = ? " +
            "AND language_id = ?";
    private static final String GET_RECENT_ADDED_COMMENTS_QUERY = "SELECT * FROM comment " +
            "WHERE language_id = ? ORDER BY id DESC LIMIT ";

    @Override
    public void addComment(Comment comment, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            PreparedStatement statement = connection.prepareStatement(ADD_COMMENT_QUERY);
            statement.setInt(1, comment.getMovieId());
            statement.setInt(2, comment.getUserId());
            statement.setString(3, comment.getTitle());
            statement.setString(4, comment.getContent());
            statement.setDate(5, new Date(comment.getDateOfPublication().getTime()));
            statement.setString(6, languageId);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Exception in DAO layer when adding comment", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public void updateComment(Comment comment) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_COMMENT_QUERY);
            statement.setInt(1, comment.getMovieId());
            statement.setInt(2, comment.getUserId());
            statement.setString(3, comment.getTitle());
            statement.setString(4, comment.getContent());
            statement.setDate(5, new Date(comment.getDateOfPublication().getTime()));
            statement.setInt(6, comment.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Exception in DAO layer when updating comment", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public void deleteComment(int id) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_COMMENT_QUERY);
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Exception in DAO layer when deleting comment", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public List<Comment> getAllComments(String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COMMENTS_QUERY);
            statement.setString(1, languageId);
            ResultSet resultSet = statement.executeQuery();

            List<Comment> allComments = new ArrayList<>();
            while (resultSet.next()){
                Comment comment = new Comment();
                comment.setId(resultSet.getInt(1));
                comment.setMovieId(resultSet.getInt(2));
                comment.setUserId(resultSet.getInt(3));
                comment.setTitle(resultSet.getString(4));
                comment.setContent(resultSet.getString(5));
                comment.setDateOfPublication(resultSet.getDate(6));

                allComments.add(comment);
            }
            return allComments;
        } catch (SQLException e) {
            throw new DAOException("Exception in DAO layer when getting comment", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public Comment getCommentById(int id) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            PreparedStatement statement = connection.prepareStatement(GET_COMMENT_BY_ID_QUERY);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            Comment comment = null;
            if(resultSet.next()){
                comment = new Comment();
                comment.setId(resultSet.getInt(1));
                comment.setMovieId(resultSet.getInt(2));
                comment.setUserId(resultSet.getInt(3));
                comment.setTitle(resultSet.getString(4));
                comment.setContent(resultSet.getString(5));
                comment.setDateOfPublication(resultSet.getDate(6));
            }
            return comment;
        } catch (SQLException e) {
            throw new DAOException("Exception in DAO layer when getting comment", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public List<Comment> getCommentsByMovie(int movieId, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            PreparedStatement statement = connection.prepareStatement(GET_COMMENTS_BY_MOVIE_QUERY);
            statement.setInt(1, movieId);
            statement.setString(2, languageId);
            ResultSet resultSet = statement.executeQuery();

            List<Comment> commentsByMovie = new ArrayList<>();
            while (resultSet.next()){
                Comment comment = new Comment();
                comment.setId(resultSet.getInt(1));
                comment.setMovieId(resultSet.getInt(2));
                comment.setUserId(resultSet.getInt(3));
                comment.setTitle(resultSet.getString(4));
                comment.setContent(resultSet.getString(5));
                comment.setDateOfPublication(resultSet.getDate(6));

                commentsByMovie.add(comment);
            }
            return commentsByMovie;
        } catch (SQLException e) {
            throw new DAOException("Exception in DAO layer when getting comment", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public List<Comment> getCommentsByUser(int userId, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            PreparedStatement statement = connection.prepareStatement(GET_COMMENTS_BY_USER_QUERY);
            statement.setInt(1, userId);
            statement.setString(2, languageId);
            ResultSet resultSet = statement.executeQuery();

            List<Comment> commentsByUser = new ArrayList<>();
            while (resultSet.next()){
                Comment comment = new Comment();
                comment.setId(resultSet.getInt(1));
                comment.setMovieId(resultSet.getInt(2));
                comment.setUserId(resultSet.getInt(3));
                comment.setTitle(resultSet.getString(4));
                comment.setContent(resultSet.getString(5));
                comment.setDateOfPublication(resultSet.getDate(6));

                commentsByUser.add(comment);
            }
            return commentsByUser;
        } catch (SQLException e) {
            throw new DAOException("Exception in DAO layer when getting comment", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }

    @Override
    public List<Comment> getRecentAddedComments(int amount, String languageId) throws DAOException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = mySQLConnectionPool.getConnection();
        } catch (InterruptedException | MySQLConnectionPoolException e) {
            throw new DAOException("Cannot get a connection from Connection Pool", e);
        }

        try {
            PreparedStatement statement = connection.prepareStatement(GET_RECENT_ADDED_COMMENTS_QUERY + amount);
            statement.setString(1, languageId);
            ResultSet resultSet = statement.executeQuery();

            List<Comment> allComments = new ArrayList<>();
            while (resultSet.next()){
                Comment comment = new Comment();
                comment.setId(resultSet.getInt(1));
                comment.setMovieId(resultSet.getInt(2));
                comment.setUserId(resultSet.getInt(3));
                comment.setTitle(resultSet.getString(4));
                comment.setContent(resultSet.getString(5));
                comment.setDateOfPublication(resultSet.getDate(6));

                allComments.add(comment);
            }
            return allComments;
        } catch (SQLException e) {
            throw new DAOException("Exception in DAO layer when getting comment", e);
        } finally {
            try {
                mySQLConnectionPool.freeConnection(connection);
            } catch (SQLException | MySQLConnectionPoolException e) {
                throw new DAOException("Cannot free a connection from Connection Pool", e);
            }
        }
    }
}
