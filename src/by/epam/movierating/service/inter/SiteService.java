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
    User login(String email, String password) throws ServiceWrongEmailException, ServiceWrongPasswordException, ServiceException;
    User registration(String email, String password, String firstName, String lastName, String languageId) throws ServiceWrongEmailException, ServiceException;
}
