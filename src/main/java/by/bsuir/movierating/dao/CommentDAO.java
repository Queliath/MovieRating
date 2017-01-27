package by.bsuir.movierating.dao;
import by.bsuir.movierating.domain.Comment;

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
     */
    void addComment(Comment comment, String languageId);

    /**
     * Updates a comment in the data storage.
     *
     * @param comment a comment object
     */
    void updateComment(Comment comment);

    /**
     * Deletes a comment from the data storage.
     *
     * @param id - an id of a deleting comment
     */
    void deleteComment(int id);

    /**
     * Returns all the comments from the data storage.
     *
     * @param languageId a language id like 'EN', "RU' etc.
     * @return all the comments from the data storage
     */
    List<Comment> getAllComments(String languageId);

    /**
     * Returns a comment from the data storage by id.
     *
     * @param id an id of a needed comment
     * @return a comment object
     */
    Comment getCommentById(int id);

    /**
     * Returns a comments from the data storage belonging to the movie.
     *
     * @param movieId an id of the movie
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a comments belonging to the movie
     */
    List<Comment> getCommentsByMovie(int movieId, String languageId);

    /**
     * Returns a comments from the data storage belonging to the user.
     *
     * @param userId an id of the user
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a comments belonging to the user
     */
    List<Comment> getCommentsByUser(int userId, String languageId);

    /**
     * Returns a recent added comments from the data storage.
     *
     * @param amount a needed amount of a comment
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a recent added comments
     */
    List<Comment> getRecentAddedComments(int amount, String languageId);
}
