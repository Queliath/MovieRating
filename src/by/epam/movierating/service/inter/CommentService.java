package by.epam.movierating.service.inter;

import by.epam.movierating.domain.Comment;
import by.epam.movierating.service.exception.ServiceException;

import java.util.List;

/**
 * Provides a business-logic with the Comment entity.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public interface CommentService {
    /**
     * Returns a recent added comments.
     *
     * @param amount a needed amount of comments
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a recent added comments
     * @throws ServiceException
     */
    List<Comment> getRecentAddedComments(int amount, String languageId) throws ServiceException;

    /**
     * Adds a comment to the data storage.
     *
     * @param title a title of the comment
     * @param content a content of the comment
     * @param movieId an id of the movie to which the comment belongs
     * @param userId an id of the user to which the comment belongs
     * @param languageId a language id like 'EN', "RU' etc.
     * @throws ServiceException
     */
    void addComment(String title, String content, int movieId, int userId, String languageId) throws ServiceException;

    /**
     * Edits an already existing comment.
     *
     * @param id an id of the needed comment
     * @param title a new title of the comment
     * @param content a new content of the comment
     * @throws ServiceException
     */
    void editComment(int id, String title, String content) throws ServiceException;

    /**
     * Returns a concrete comment by id.
     *
     * @param id an id of a needed comment
     * @return a concrete comment by id
     * @throws ServiceException
     */
    Comment getCommentById(int id) throws ServiceException;

    /**
     * Deletes an existing comment from the data storage.
     *
     * @param id an id of the deleting comment
     * @throws ServiceException
     */
    void deleteComment(int id) throws ServiceException;
}
