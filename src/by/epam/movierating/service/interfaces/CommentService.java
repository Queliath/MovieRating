package by.epam.movierating.service.interfaces;

import by.epam.movierating.domain.Comment;
import by.epam.movierating.service.exception.ServiceException;

import java.util.List;

/**
 * Created by Владислав on 15.07.2016.
 */
public interface CommentService {
    List<Comment> getAllComments(String languageId) throws ServiceException;
}
