package by.epam.movierating.service.impl;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.dao.inter.CommentDAO;
import by.epam.movierating.dao.inter.MovieDAO;
import by.epam.movierating.dao.inter.UserDAO;
import by.epam.movierating.domain.Comment;
import by.epam.movierating.domain.Movie;
import by.epam.movierating.domain.User;
import by.epam.movierating.domain.criteria.UserCriteria;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.exception.ServiceWrongEmailException;
import by.epam.movierating.service.inter.UserService;

import java.util.List;

/**
 * Provides a business-logic with the User entity.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class UserServiceImpl implements UserService {
    private static final int EMAIL_MAX_LENGTH = 45;
    private static final int PASSWORD_MAX_LENGTH = 45;
    private static final int FIRST_NAME_MAX_LENGTH = 25;
    private static final int LAST_NAME_MAX_LENGTH = 25;
    private static final int PHOTO_MAX_LENGTH = 150;

    /**
     * Returns a certain user by id.
     *
     * @param id an id of a needed user
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a certain user
     * @throws ServiceException
     */
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

    /**
     * Edits a main information about the already existing user.
     *
     * @param id an id of a needed user
     * @param email a new email of the user
     * @param password a new password of the user
     * @param firstName a new first name of the user
     * @param lastName a new last name of the user
     * @param photo a URL to the new photo of the user
     * @param languageId an id of the new language of the user
     * @throws ServiceWrongEmailException if there is already existing user with email like the new email of the editing user
     * @throws ServiceException
     */
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

    /**
     * Edits a secondary information about the already existing user.
     *
     * @param id an id of a needed user
     * @param rating a new rating of the user
     * @param status a new status of the user
     * @throws ServiceException
     */
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

    /**
     * Deletes existing user from the data storage.
     *
     * @param id an id of the deleting user
     * @throws ServiceException
     */
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

    /**
     * Returns an amount of the users matching the criteria.
     *
     * @param email an email of the user criteria
     * @param firstName a first name of the user criteria
     * @param lastName a last name of the user criteria
     * @param minDateOfRegistry a min date of registration of the user criteria
     * @param maxDateOfRegistry a max date of registration of the user criteria
     * @param minRating a min rating of the user criteria
     * @param maxRating a max rating of the user criteria
     * @param statuses a list of possible statuses of the user criteria
     * @return an amount of the users matching the criteria
     * @throws ServiceException
     */
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

    /**
     * Returns a users matching the criteria.
     *
     * @param email an email of the user criteria
     * @param firstName a first name of the user criteria
     * @param lastName a last name of the user criteria
     * @param minDateOfRegistry a min date of registration of the user criteria
     * @param maxDateOfRegistry a max date of registration of the user criteria
     * @param minRating a min rating of the user criteria
     * @param maxRating a max rating of the user criteria
     * @param statuses a list of possible statuses of the user criteria
     * @param from a starting position in the users list (starting from 0)
     * @param amount a needed amount of the users
     * @return a users matching the criteria
     * @throws ServiceException
     */
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
