package by.epam.movierating.service.impl;

import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.inter.UserService;
import org.junit.Test;

/**
 * Created by Владислав on 17.09.2016.
 */
public class UserServiceImplTest {
    @Test(expected = ServiceException.class)
    public void getUserByIdTest() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();

        userService.getUserById(-1, "EN");
    }

    @Test(expected = ServiceException.class)
    public void editUserMainInfTest() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();

        userService.editUserMainInf(-1, "", "", "", "", "", "EN");
    }

    @Test(expected = ServiceException.class)
    public void editUserSecondInfTest() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();

        userService.editUserSecondInf(-1, -1, "");
    }

    @Test(expected = ServiceException.class)
    public void deleteUserTest() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();

        userService.deleteUser(-1);
    }
}
