package by.epam.movierating.service.inter;

import by.epam.movierating.domain.User;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.exception.ServiceWrongEmailException;
import by.epam.movierating.service.exception.ServiceWrongPasswordException;

/**
 * Provides a general business-logic for the whole application.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public interface SiteService {
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
    User login(String email, String password) throws ServiceWrongEmailException, ServiceWrongPasswordException, ServiceException;

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
    User registration(String email, String password, String firstName, String lastName, String languageId) throws ServiceWrongEmailException, ServiceException;
}
