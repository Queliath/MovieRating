package by.bsuir.movierating.service.impl;

import by.bsuir.movierating.dao.UserDAO;
import by.bsuir.movierating.domain.User;
import by.bsuir.movierating.exception.UserInputException;
import by.bsuir.movierating.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Provides a general business-logic for the whole application.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
@Service("siteService")
public class SiteServiceImpl implements SiteService {
    private static final String DEFAULT_USER_STATUS = "normal";

    private UserDAO userDAO;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Checks if a user with this email and password can login to the system.
     *
     * @param email an email of the user
     * @param password a password of the user
     * @return user object if login success
     */
    @Override
    public User login(String email, String password) {
        User user = userDAO.getUserByEmail(email);
        if(user == null){
            throw new UserInputException("Wrong email");
        }
        if(!user.getPassword().equals(password)){
            throw new UserInputException("Wrong password");
        }
        return user;
    }

    /**
     * Registers a new user to the system.
     *
     * @param email an email of the user
     * @param password a password of the user
     * @param firstName a first name of the user
     * @param lastName a last name of the user
     * @param languageId an id of the language of the user
     * @return new user object if registry success
     */
    @Override
    public User registration(String email, String password, String firstName, String lastName, String languageId) {
        User userWithThisEmail = userDAO.getUserByEmail(email);
        if(userWithThisEmail != null){
            throw new UserInputException("Wrong email");
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setDateOfRegistry(new Date());
        newUser.setStatus(DEFAULT_USER_STATUS);
        newUser.setLanguageId(languageId);

        userDAO.addUser(newUser);

        return newUser;
    }
}
