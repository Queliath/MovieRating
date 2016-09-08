package by.epam.movierating.service.impl;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.dao.inter.CommentDAO;
import by.epam.movierating.dao.inter.MovieDAO;
import by.epam.movierating.dao.inter.UserDAO;
import by.epam.movierating.domain.Comment;
import by.epam.movierating.domain.Movie;
import by.epam.movierating.domain.User;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.inter.CommentService;

import java.util.Date;
import java.util.List;

/**
 * Created by Владислав on 15.07.2016.
 */
public class CommentServiceImpl implements CommentService {
    private static final int TITLE_MAX_LENGTH = 45;

    @Override
    public List<Comment> getRecentAddedComments(int amount, String languageId) throws ServiceException {
        if(amount <= 0){
            throw new ServiceException("Wrong amount value");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            CommentDAO commentDAO = daoFactory.getCommentDAO();
            List<Comment> comments = commentDAO.getRecentAddedComments(amount, languageId);

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

    @Override
    public void addComment(String title, String content, int movieId, int userId, String languageId) throws ServiceException {
        if(title.isEmpty() || title.length() > TITLE_MAX_LENGTH || content.isEmpty() || movieId <= 0 || userId <= 0){
            throw new ServiceException("Wrong parameters for adding comment");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            CommentDAO commentDAO = daoFactory.getCommentDAO();

            Comment comment = new Comment();
            comment.setTitle(title);
            comment.setContent(content);
            comment.setDateOfPublication(new Date());
            comment.setMovieId(movieId);
            comment.setUserId(userId);

            commentDAO.addComment(comment, languageId);
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot add a comment", e);
        }
    }

    @Override
    public void editComment(int id, String title, String content) throws ServiceException {
        if(id <= 0 || title.isEmpty() || title.length() > TITLE_MAX_LENGTH || content.isEmpty()){
            throw new ServiceException("Wrong parameters for editing comment");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            CommentDAO commentDAO = daoFactory.getCommentDAO();

            Comment comment = commentDAO.getCommentById(id);
            comment.setTitle(title);
            comment.setContent(content);

            commentDAO.updateComment(comment);
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot edit a comment", e);
        }
    }

    @Override
    public Comment getCommentById(int id) throws ServiceException {
        if(id <= 0){
            throw new ServiceException("Wrong id for getting comment");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            CommentDAO commentDAO = daoFactory.getCommentDAO();
            Comment comment = commentDAO.getCommentById(id);
            return comment;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get comment by id", e);
        }
    }

    @Override
    public void deleteComment(int id) throws ServiceException {
        if(id <= 0){
            throw new ServiceException("Wrong id for deleting comment");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            CommentDAO commentDAO = daoFactory.getCommentDAO();
            commentDAO.deleteComment(id);
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot delete comment", e);
        }
    }
}
