package by.epam.movierating.dao.inter;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.domain.Comment;

import java.util.List;

/**
 * Provides a DAO-logic for the Comment entity.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public interface CommentDAO {
    void addComment(Comment comment, String languageId) throws DAOException;
    void updateComment(Comment comment) throws DAOException;
    void deleteComment(int id) throws DAOException;
    List<Comment> getAllComments(String languageId) throws DAOException;
    Comment getCommentById(int id) throws DAOException;
    List<Comment> getCommentsByMovie(int movieId, String languageId) throws DAOException;
    List<Comment> getCommentsByUser(int userId, String languageId) throws DAOException;
    List<Comment> getRecentAddedComments(int amount, String languageId) throws DAOException;
}
