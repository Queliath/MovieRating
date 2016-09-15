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
    /**
     * Adds a comment to the data storage.
     *
     * @param comment a comment object
     * @param languageId a language id like 'EN', "RU' etc.
     * @throws DAOException
     */
    void addComment(Comment comment, String languageId) throws DAOException;

    /**
     * Updates a comment in the data storage.
     *
     * @param comment a comment object
     * @throws DAOException
     */
    void updateComment(Comment comment) throws DAOException;

    /**
     * Deletes a comment from the data storage.
     *
     * @param id - an id of a deleting comment
     * @throws DAOException
     */
    void deleteComment(int id) throws DAOException;

    /**
     * Returns all the comments from the data storage.
     *
     * @param languageId a language id like 'EN', "RU' etc.
     * @return all the comments from the data storage
     * @throws DAOException
     */
    List<Comment> getAllComments(String languageId) throws DAOException;

    /**
     * Returns a comment from the data storage by id.
     *
     * @param id an id of a needed comment
     * @return a comment object
     * @throws DAOException
     */
    Comment getCommentById(int id) throws DAOException;

    /**
     * Returns a comments from the data storage belonging to the movie.
     *
     * @param movieId an id of the movie
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a comments belonging to the movie
     * @throws DAOException
     */
    List<Comment> getCommentsByMovie(int movieId, String languageId) throws DAOException;

    /**
     * Returns a comments from the data storage belonging to the user.
     *
     * @param userId an id of the user
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a comments belonging to the user
     * @throws DAOException
     */
    List<Comment> getCommentsByUser(int userId, String languageId) throws DAOException;

    /**
     * Returns a recent added comments from the data storage.
     *
     * @param amount a needed amount of a comment
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a recent added comments
     * @throws DAOException
     */
    List<Comment> getRecentAddedComments(int amount, String languageId) throws DAOException;
}
