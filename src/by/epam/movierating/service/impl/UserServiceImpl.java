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
import by.epam.movierating.service.interfaces.UserService;

import java.util.List;

/**
 * Created by Владислав on 03.08.2016.
 */
public class UserServiceImpl implements UserService {
    @Override
    public User getUserById(int id, String languageId) throws ServiceException {
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            UserDAO userDAO = daoFactory.getUserDAO();
            User user = userDAO.getUserById(id);

            if(user != null){
                CommentDAO commentDAO = daoFactory.getCommentDAO();
                List<Comment> comments = commentDAO.getCommentsByUser(id, languageId);
                user.setComments(comments.isEmpty() ? null : comments);

                MovieDAO movieDAO = daoFactory.getMovieDAO();
                for(Comment comment : comments){
                    Movie movie = movieDAO.getMovieById(comment.getMovieId(), languageId);
                    comment.setMovie(movie);
                }
            }

            return user;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get user by id", e);
        }
    }
}
