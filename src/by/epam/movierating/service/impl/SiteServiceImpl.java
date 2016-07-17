package by.epam.movierating.service.impl;

import by.epam.movierating.domain.User;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.exception.ServiceWrongEmailException;
import by.epam.movierating.service.exception.ServiceWrongPasswordException;
import by.epam.movierating.service.interfaces.SiteService;

/**
 * Created by Владислав on 15.07.2016.
 */
public class SiteServiceImpl implements SiteService {
    @Override
    public User login(String email, String password) throws ServiceWrongEmailException, ServiceWrongPasswordException, ServiceException {
        throw new ServiceWrongPasswordException("Wrong password");
    }
}
