package by.epam.movierating.service.impl;

import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.inter.RatingService;
import org.junit.Test;

/**
 * Created by Владислав on 17.09.2016.
 */
public class RatingServiceImplTest {
    @Test(expected = ServiceException.class)
    public void addRatingTest() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        RatingService ratingService = serviceFactory.getRatingService();

        ratingService.addRating(-1, -1, -1);
    }
}
