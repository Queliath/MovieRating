package by.epam.movierating.service.impl;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.dao.inter.UserDAO;
import by.epam.movierating.domain.User;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.exception.ServiceWrongEmailException;
import by.epam.movierating.service.exception.ServiceWrongPasswordException;
import by.epam.movierating.service.inter.SiteService;

import java.util.Date;

/**
 * Provides a general business-logic for the whole application.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class SiteServiceImpl implements SiteService {
    private static final String DEFAULT_USER_STATUS = "normal";

    private static final int EMAIL_MAX_LENGTH = 45;
    private static final int PASSWORD_MAX_LENGTH = 45;
    private static final int FIRST_NAME_MAX_LENGTH = 25;
    private static final int LAST_NAME_MAX_LENGTH = 25;

    /**
     * Checks if a user with this email and password can login to the system.
     *
     * @param email an email of the user
     * @param password a password of the user
     * @return user object if login success
     * @throws ServiceWrongEmailException if there is no user with this email
     * @throws ServiceWrongPasswordException if the password is wrong
     * @throws ServiceException
     */
    @Override
    public User login(String email, String password) throws ServiceWrongEmailException, ServiceWrongPasswordException, ServiceException {
        if(email.isEmpty() || email.length() > EMAIL_MAX_LENGTH || password.isEmpty() || password.length() > PASSWORD_MAX_LENGTH){
            throw new ServiceException("Wrong parameters for login");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            UserDAO userDAO = daoFactory.getUserDAO();
            User user = userDAO.getUserByEmail(email);
            if(user == null){
                throw new ServiceWrongEmailException("Wrong email");
            }
            if(!user.getPassword().equals(password)){
                throw new ServiceWrongPasswordException("Wrong password");
            }
            return user;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot make a login operation", e);
        }
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
     * @throws ServiceWrongEmailException if there is already existing user with this email
     * @throws ServiceException
     */
    @Override
    public User registration(String email, String password, String firstName, String lastName, String languageId) throws ServiceWrongEmailException, ServiceException {
        if(email.isEmpty() || email.length() > EMAIL_MAX_LENGTH || password.isEmpty() || password.length() > PASSWORD_MAX_LENGTH ||
                firstName.isEmpty() || firstName.length() > FIRST_NAME_MAX_LENGTH || lastName.isEmpty() || lastName.length() > LAST_NAME_MAX_LENGTH){
            throw new ServiceException("Wrong parameters for registration");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            UserDAO userDAO = daoFactory.getUserDAO();

            User userWithThisEmail = userDAO.getUserByEmail(email);
            if(userWithThisEmail != null){
                throw new ServiceWrongEmailException("Wrong email");
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
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot make a registration", e);
        }
    }
}
