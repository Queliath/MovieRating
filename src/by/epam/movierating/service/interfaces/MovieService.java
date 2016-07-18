package by.epam.movierating.service.interfaces;

import by.epam.movierating.domain.Movie;
import by.epam.movierating.service.exception.ServiceException;

import java.util.List;

/**
 * Created by Владислав on 15.07.2016.
 */
public interface MovieService {
    List<Movie> getRecentAddedMovies(int amount, String languageId) throws ServiceException;
}
