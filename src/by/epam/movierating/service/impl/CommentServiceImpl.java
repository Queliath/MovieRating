package by.epam.movierating.service.impl;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.dao.interfaces.CommentDAO;
import by.epam.movierating.dao.interfaces.MovieDAO;
import by.epam.movierating.dao.interfaces.UserDAO;
import by.epam.movierating.domain.Comment;
import by.epam.movierating.domain.Movie;
import by.epam.movierating.domain.User;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.interfaces.CommentService;

import java.util.List;

/**
 * Created by Владислав on 15.07.2016.
 */
public class CommentServiceImpl implements CommentService {
    @Override
    public List<Comment> getAllComments(String languageId) throws ServiceException {
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            CommentDAO commentDAO = daoFactory.getCommentDAO();
            List<Comment> comments = commentDAO.getAllComments(languageId);

            UserDAO userDAO = daoFactory.getUserDAO();
            MovieDAO movieDAO = daoFactory.getMovieDAO();
            for(Comment comment : comments){
                User user = userDAO.getUserById(comment.getUserId());
                comment.setUser(user);

                Movie movie = movieDAO.getMovieById(comment.getMovieId(), languageId);
                comment.setMovie(movie);
            }
            return comments;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get all comments", e);
        }
    }
}
