package by.epam.movierating.service.interfaces;

import by.epam.movierating.domain.User;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.exception.ServiceWrongEmailException;
import by.epam.movierating.service.exception.ServiceWrongPasswordException;

/**
 * Created by Владислав on 15.07.2016.
 */
public interface SiteService {
    User login(String email, String password) throws ServiceWrongEmailException, ServiceWrongPasswordException, ServiceException;
}
