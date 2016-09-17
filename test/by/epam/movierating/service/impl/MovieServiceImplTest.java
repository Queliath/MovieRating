package by.epam.movierating.service.impl;

import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.inter.MovieService;
import org.junit.Test;

/**
 * Tests the methods of MovieServiceImpl class.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class MovieServiceImplTest {
    @Test(expected = ServiceException.class)
    public void getRecentAddedMoviesTest() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        MovieService movieService = serviceFactory.getMovieService();

        movieService.getRecentAddedMovies(-1, "EN");
    }

    @Test(expected = ServiceException.class)
    public void getMovieByIdTest() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        MovieService movieService = serviceFactory.getMovieService();

        movieService.getMovieById(-1, "EN");
    }

    @Test(expected = ServiceException.class)
    public void addMovieTest() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        MovieService movieService = serviceFactory.getMovieService();

        movieService.addMovie("", -1, "", -1, "", -1, "", "");
    }

    @Test(expected = ServiceException.class)
    public void editMovieTest() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        MovieService movieService = serviceFactory.getMovieService();

        movieService.editMovie(-1, "", -1, "", -1, "", -1, "", "", "EN");
    }

    @Test(expected = ServiceException.class)
    public void deleteMovieTest() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        MovieService movieService = serviceFactory.getMovieService();

        movieService.deleteMovie(-1);
    }
}
