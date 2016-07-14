package by.epam.movierating.dao.impl.mysql;

import by.epam.movierating.dao.interfaces.CommentDAO;
import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPool;
import by.epam.movierating.domain.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 11.06.2016.
 */
public class MySQLCommentDAO implements CommentDAO {
    @Override
    public void addComment(Comment comment) throws DAOException {
        try {
            MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
            Connection connection = mySQLConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO comment " +
                    "(movie_id, user_id, title, content, date_of_publication) VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1, comment.getMovieId());
            statement.setInt(2, comment.getUserId());
            statement.setString(3, comment.getTitle());
            statement.setString(4, comment.getContent());
            statement.setDate(5, new Date(comment.getDateOfPublication().getTime()));

            statement.executeUpdate();

            mySQLConnectionPool.freeConnection(connection);

        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            throw new DAOException("Exception in DAO layer when adding comment", e);
        }
    }

    @Override
    public void updateComment(Comment comment) throws DAOException {
        try {
            MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
            Connection connection = mySQLConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE comment " +
                    "SET movie_id = ?, user_id = ?, title = ?, content = ?, date_of_publication = ? WHERE id = ?");
            statement.setInt(1, comment.getMovieId());
            statement.setInt(2, comment.getUserId());
            statement.setString(3, comment.getTitle());
            statement.setString(4, comment.getContent());
            statement.setDate(5, new Date(comment.getDateOfPublication().getTime()));
            statement.setInt(6, comment.getId());

            statement.executeUpdate();

            mySQLConnectionPool.freeConnection(connection);

        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            throw new DAOException("Exception in DAO layer when updating comment", e);
        }
    }

    @Override
    public void deleteComment(int id) throws DAOException {
        try {
            MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
            Connection connection = mySQLConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM comment WHERE id = ?");
            statement.setInt(1, id);

            statement.executeUpdate();

            mySQLConnectionPool.freeConnection(connection);

        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            throw new DAOException("Exception in DAO layer when deleting comment", e);
        }
    }

    @Override
    public List<Comment> getAllComments() throws DAOException {
        try {
            MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
            Connection connection = mySQLConnectionPool.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM comment");

            mySQLConnectionPool.freeConnection(connection);

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

        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            throw new DAOException("Exception in DAO layer when getting comment", e);
        }
    }

    @Override
    public Comment getCommentById(int id) throws DAOException {
        try {
            MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
            Connection connection = mySQLConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM comment WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            mySQLConnectionPool.freeConnection(connection);

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

        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            throw new DAOException("Exception in DAO layer when getting comment", e);
        }
    }

    @Override
    public List<Comment> getCommentsByMovie(int movieId) throws DAOException {
        try {
            MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
            Connection connection = mySQLConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM comment WHERE movie_id = ?");
            statement.setInt(1, movieId);
            ResultSet resultSet = statement.executeQuery();

            mySQLConnectionPool.freeConnection(connection);

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

        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            throw new DAOException("Exception in DAO layer when getting comment", e);
        }
    }

    @Override
    public List<Comment> getCommentsByUser(int userId) throws DAOException {
        try {
            MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
            Connection connection = mySQLConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM comment WHERE user_id = ?");
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            mySQLConnectionPool.freeConnection(connection);

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

        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            throw new DAOException("Exception in DAO layer when getting comment", e);
        }
    }
}
