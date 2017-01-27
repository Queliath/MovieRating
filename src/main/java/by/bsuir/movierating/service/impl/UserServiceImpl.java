package by.bsuir.movierating.service.impl;

import by.bsuir.movierating.dao.MovieDAO;
import by.bsuir.movierating.dao.UserDAO;
import by.bsuir.movierating.domain.Comment;
import by.bsuir.movierating.domain.Movie;
import by.bsuir.movierating.domain.User;
import by.bsuir.movierating.domain.criteria.UserCriteria;
import by.bsuir.movierating.exception.UserInputException;
import by.bsuir.movierating.service.UserService;
import by.bsuir.movierating.dao.CommentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Provides a business-logic with the User entity.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;
    private MovieDAO movieDAO;
    private CommentDAO commentDAO;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    public void setMovieDAO(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    @Autowired
    public void setCommentDAO(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }

    /**
     * Returns a certain user by id.
     *
     * @param id an id of a needed user
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a certain user
     */
    @Override
    public User getUserById(int id, String languageId) {
        User user = userDAO.getUserById(id);

        if(user != null){
            List<Comment> comments = commentDAO.getCommentsByUser(id, languageId);
            user.setComments(comments.isEmpty() ? null : comments);

            for(Comment comment : comments){
                Movie movie = movieDAO.getMovieById(comment.getMovieId(), languageId);
                comment.setMovie(movie);
            }
        }

        return user;
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
     */
    @Override
    public void editUserMainInf(int id, String email, String password, String firstName, String lastName, String photo, String languageId) {
        User userWithThisEmail = userDAO.getUserByEmail(email);
        if(userWithThisEmail != null && userWithThisEmail.getId() != id){
            throw new UserInputException("Wrong email");
        }

        User user = userDAO.getUserById(id);
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoto(photo);
        user.setLanguageId(languageId);

        userDAO.updateUser(user);
    }

    /**
     * Edits a secondary information about the already existing user.
     *
     * @param id an id of a needed user
     * @param rating a new rating of the user
     * @param status a new status of the user
     */
    @Override
    public void editUserSecondInf(int id, int rating, String status) {
        User user = userDAO.getUserById(id);
        user.setRating(rating);
        user.setStatus(status);

        userDAO.updateUser(user);
    }

    /**
     * Deletes existing user from the data storage.
     *
     * @param id an id of the deleting user
     */
    @Override
    public void deleteUser(int id) {
        userDAO.deleteUser(id);
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
     */
    @Override
    public int getUsersCountByCriteria(String email, String firstName, String lastName, String minDateOfRegistry, String maxDateOfRegistry, Integer minRating, Integer maxRating, List<String> statuses) {
        UserCriteria criteria = new UserCriteria();
        criteria.setEmail(email);
        criteria.setFirstName(firstName);
        criteria.setLastName(lastName);
        criteria.setMinDateOfRegistry(minDateOfRegistry);
        criteria.setMaxDateOfRegistry(maxDateOfRegistry);
        criteria.setMinRating(minRating);
        criteria.setMaxRating(maxRating);
        criteria.setStatuses(statuses);

        return userDAO.getUsersCountByCriteria(criteria);
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
     */
    @Override
    public List<User> getUsersByCriteria(String email, String firstName, String lastName, String minDateOfRegistry, String maxDateOfRegistry, Integer minRating, Integer maxRating, List<String> statuses, int from, int amount) {
        UserCriteria criteria = new UserCriteria();
        criteria.setEmail(email);
        criteria.setFirstName(firstName);
        criteria.setLastName(lastName);
        criteria.setMinDateOfRegistry(minDateOfRegistry);
        criteria.setMaxDateOfRegistry(maxDateOfRegistry);
        criteria.setMinRating(minRating);
        criteria.setMaxRating(maxRating);
        criteria.setStatuses(statuses);

        return userDAO.getUsersByCriteria(criteria, from, amount);
    }
}
