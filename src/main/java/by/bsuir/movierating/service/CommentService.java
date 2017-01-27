package by.bsuir.movierating.service;

import by.bsuir.movierating.domain.Comment;

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
     */
    List<Comment> getRecentAddedComments(int amount, String languageId);

    /**
     * Adds a comment to the data storage.
     *
     * @param title a title of the comment
     * @param content a content of the comment
     * @param movieId an id of the movie to which the comment belongs
     * @param userId an id of the user to which the comment belongs
     * @param languageId a language id like 'EN', "RU' etc.
     */
    void addComment(String title, String content, int movieId, int userId, String languageId);

    /**
     * Edits an already existing comment.
     *
     * @param id an id of the needed comment
     * @param title a new title of the comment
     * @param content a new content of the comment
     */
    void editComment(int id, String title, String content);

    /**
     * Returns a concrete comment by id.
     *
     * @param id an id of a needed comment
     * @return a concrete comment by id
     */
    Comment getCommentById(int id);

    /**
     * Deletes an existing comment from the data storage.
     *
     * @param id an id of the deleting comment
     */
    void deleteComment(int id);
}
