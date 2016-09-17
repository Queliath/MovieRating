package by.epam.movierating.service.impl;

import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.inter.SiteService;
import org.junit.Test;

/**
 * Created by Владислав on 17.09.2016.
 */
public class SiteServiceImplTest {
    @Test(expected = ServiceException.class)
    public void loginTest() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        SiteService siteService = serviceFactory.getSiteService();

        siteService.login("", "");
    }

    @Test(expected = ServiceException.class)
    public void registrationTest() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        SiteService siteService = serviceFactory.getSiteService();

        siteService.registration("", "", "", "", "EN");
    }
}
