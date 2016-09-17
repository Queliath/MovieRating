package by.epam.movierating.service.impl;

import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.inter.GenreService;
import org.junit.Test;

/**
 * Tests the methods of GenreServiceImpl class.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class GenreServiceImplTest {
    @Test(expected = ServiceException.class)
    public void getTopPositionGenresTest() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        GenreService genreService = serviceFactory.getGenreService();

        genreService.getTopPositionGenres(-1, "EN");
    }

    @Test(expected = ServiceException.class)
    public void getGenreById() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        GenreService genreService = serviceFactory.getGenreService();

        genreService.getGenreById(-1, "EN");
    }

    @Test(expected = ServiceException.class)
    public void addGenreTest() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        GenreService genreService = serviceFactory.getGenreService();

        genreService.addGenre("", -1);
    }

    @Test(expected = ServiceException.class)
    public void editGenreTest() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        GenreService genreService = serviceFactory.getGenreService();

        genreService.editGenre(-1, "", -1, "EN");
    }

    @Test(expected = ServiceException.class)
    public void deleteGenreTest() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        GenreService genreService = serviceFactory.getGenreService();

        genreService.deleteGenre(-1);
    }
}
