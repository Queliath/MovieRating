package by.bsuir.movierating.service;

import by.bsuir.movierating.domain.User;

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
     */
    User login(String email, String password);

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
    User registration(String email, String password, String firstName, String lastName, String languageId);
}
