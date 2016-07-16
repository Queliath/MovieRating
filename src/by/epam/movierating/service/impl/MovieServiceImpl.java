package by.epam.movierating.service.impl;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.dao.interfaces.MovieDAO;
import by.epam.movierating.domain.Movie;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.interfaces.MovieService;

import java.util.List;

/**
 * Created by Владислав on 15.07.2016.
 */
public class MovieServiceImpl implements MovieService {

    @Override
    public List<Movie> getAllMovies(String languageId) throws ServiceException {
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            MovieDAO movieDAO = daoFactory.getMovieDAO();
            List<Movie> movies = movieDAO.getAllMovies(languageId);
            return movies;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get all movies", e);
        }
    }
}
