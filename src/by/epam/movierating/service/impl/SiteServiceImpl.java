package by.epam.movierating.service.impl;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.dao.interfaces.UserDAO;
import by.epam.movierating.domain.User;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.exception.ServiceWrongEmailException;
import by.epam.movierating.service.exception.ServiceWrongPasswordException;
import by.epam.movierating.service.interfaces.SiteService;

import java.util.Date;

/**
 * Created by Владислав on 15.07.2016.
 */
public class SiteServiceImpl implements SiteService {
    private static final String DEFAULT_USER_STATUS = "normal";

    private static final int EMAIL_MAX_LENGTH = 45;
    private static final int PASSWORD_MAX_LENGTH = 45;
    private static final int FIRST_NAME_MAX_LENGTH = 25;
    private static final int LAST_NAME_MAX_LENGTH = 25;

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

    @Override
    public User registration(String email, String password, String firstName, String lastName) throws ServiceWrongEmailException, ServiceException {
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
            userDAO.addUser(newUser);

            return newUser;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot make a registration", e);
        }
    }
}
