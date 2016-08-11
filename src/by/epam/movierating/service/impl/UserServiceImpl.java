package by.epam.movierating.service.impl;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.dao.interfaces.CommentDAO;
import by.epam.movierating.dao.interfaces.MovieDAO;
import by.epam.movierating.dao.interfaces.UserDAO;
import by.epam.movierating.domain.Comment;
import by.epam.movierating.domain.Movie;
import by.epam.movierating.domain.User;
import by.epam.movierating.domain.criteria.UserCriteria;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.exception.ServiceWrongEmailException;
import by.epam.movierating.service.interfaces.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 03.08.2016.
 */
public class UserServiceImpl implements UserService {
    private static final int EMAIL_MAX_LENGTH = 45;
    private static final int PASSWORD_MAX_LENGTH = 45;
    private static final int FIRST_NAME_MAX_LENGTH = 25;
    private static final int LAST_NAME_MAX_LENGTH = 25;
    private static final int PHOTO_MAX_LENGTH = 150;

    @Override
    public User getUserById(int id, String languageId) throws ServiceException {
        if(id <= 0){
            throw new ServiceException("Wrong id for getting user");
        }

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

    @Override
    public void editUserMainInf(int id, String email, String password, String firstName, String lastName, String photo, String languageId) throws ServiceWrongEmailException, ServiceException {
        if(id <= 0 || email.isEmpty() || email.length() > EMAIL_MAX_LENGTH || password.isEmpty() ||
                password.length() > PASSWORD_MAX_LENGTH || firstName.isEmpty() || firstName.length() > FIRST_NAME_MAX_LENGTH ||
                lastName.isEmpty() || lastName.length() > LAST_NAME_MAX_LENGTH || photo.isEmpty() || photo.length() > PHOTO_MAX_LENGTH){
            throw new ServiceException("Wrong parameters for editing user main inf");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            UserDAO userDAO = daoFactory.getUserDAO();

            User userWithThisEmail = userDAO.getUserByEmail(email);
            if(userWithThisEmail != null && userWithThisEmail.getId() != id){
                throw new ServiceWrongEmailException("Wrong email");
            }

            User user = userDAO.getUserById(id);
            user.setEmail(email);
            user.setPassword(password);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPhoto(photo);
            user.setLanguageId(languageId);

            userDAO.updateUser(user);
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot edit user main inf", e);
        }
    }

    @Override
    public void editUserSecondInf(int id, int rating, String status) throws ServiceException {
        if(id <= 0 || status.isEmpty()){
            throw new ServiceException("Wrong parameters for editing user second inf");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            UserDAO userDAO = daoFactory.getUserDAO();

            User user = userDAO.getUserById(id);
            user.setRating(rating);
            user.setStatus(status);

            userDAO.updateUser(user);
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot edit user main inf", e);
        }
    }

    @Override
    public void deleteUser(int id) throws ServiceException {
        if(id <= 0){
            throw new ServiceException("Wrong id for deleting user");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            UserDAO userDAO = daoFactory.getUserDAO();
            userDAO.deleteUser(id);
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot delete user", e);
        }
    }

    @Override
    public int getUsersCountByCriteria(String email, String firstName, String lastName, String minDateOfRegistry, String maxDateOfRegistry, Integer minRating, Integer maxRating, List<String> statuses) throws ServiceException {
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            UserDAO userDAO = daoFactory.getUserDAO();

            UserCriteria criteria = new UserCriteria();
            criteria.setEmail(email);
            criteria.setFirstName(firstName);
            criteria.setLastName(lastName);
            criteria.setMinDateOfRegistry(minDateOfRegistry);
            criteria.setMaxDateOfRegistry(maxDateOfRegistry);
            criteria.setMinRating(minRating);
            criteria.setMaxRating(maxRating);
            criteria.setStatuses(statuses);

            int usersCount = userDAO.getUsersCountByCriteria(criteria);
            return usersCount;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get users count by criteria", e);
        }
    }

    @Override
    public List<User> getUsersByCriteria(String email, String firstName, String lastName, String minDateOfRegistry, String maxDateOfRegistry, Integer minRating, Integer maxRating, List<String> statuses, int from, int amount) throws ServiceException {
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            UserDAO userDAO = daoFactory.getUserDAO();

            UserCriteria criteria = new UserCriteria();
            criteria.setEmail(email);
            criteria.setFirstName(firstName);
            criteria.setLastName(lastName);
            criteria.setMinDateOfRegistry(minDateOfRegistry);
            criteria.setMaxDateOfRegistry(maxDateOfRegistry);
            criteria.setMinRating(minRating);
            criteria.setMaxRating(maxRating);
            criteria.setStatuses(statuses);

            List<User> users = userDAO.getUsersByCriteria(criteria, from, amount);
            return users;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get users by criteria", e);
        }
    }
}
