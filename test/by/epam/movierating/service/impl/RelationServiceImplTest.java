package by.epam.movierating.service.impl;

import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.inter.RelationService;
import org.junit.Test;

/**
 * Tests the methods of RelationServiceImpl class.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class RelationServiceImplTest {
    @Test(expected = ServiceException.class)
    public void addPersonToMovieTest() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        RelationService relationService = serviceFactory.getRelationService();

        relationService.addPersonToMovie(-1, -1, -1);
    }

    @Test(expected = ServiceException.class)
    public void addCountryToMovieTest() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        RelationService relationService = serviceFactory.getRelationService();

        relationService.addCountryToMovie(-1, -1);
    }

    @Test(expected = ServiceException.class)
    public void addGenreToMovieTest() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        RelationService relationService = serviceFactory.getRelationService();

        relationService.addGenreToMovie(-1, -1);
    }

    @Test(expected = ServiceException.class)
    public void deletePersonFromMovieTest() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        RelationService relationService = serviceFactory.getRelationService();

        relationService.deletePersonFromMovie(-1, -1, -1);
    }

    @Test(expected = ServiceException.class)
    public void deleteCountryFromMovieTest() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        RelationService relationService = serviceFactory.getRelationService();

        relationService.deleteCountryFromMovie(-1, -1);
    }

    @Test(expected = ServiceException.class)
    public void deleteGenreFromMovieTest() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        RelationService relationService = serviceFactory.getRelationService();

        relationService.deleteGenreFromMovie(-1, -1);
    }
}
