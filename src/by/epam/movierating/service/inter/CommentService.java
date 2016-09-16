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
    List<Comment> getRecentAddedComments(int amount, String languageId) throws ServiceException;
    void addComment(String title, String content, int movieId, int userId, String languageId) throws ServiceException;
    void editComment(int id, String title, String content) throws ServiceException;
    Comment getCommentById(int id) throws ServiceException;
    void deleteComment(int id) throws ServiceException;
}
