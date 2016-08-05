package by.epam.movierating.service.interfaces;

import by.epam.movierating.domain.Comment;
import by.epam.movierating.service.exception.ServiceException;

import java.util.List;

/**
 * Created by Владислав on 15.07.2016.
 */
public interface CommentService {
    List<Comment> getRecentAddedComments(int amount, String languageId) throws ServiceException;
    void addComment(String title, String content, int movieId, int userId, String languageId) throws ServiceException;
    void editComment(int id, String title, String content) throws ServiceException;
    Comment getCommentById(int id) throws ServiceException;
}
