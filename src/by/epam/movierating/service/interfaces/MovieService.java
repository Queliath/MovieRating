package by.epam.movierating.service.interfaces;

import by.epam.movierating.domain.Movie;
import by.epam.movierating.service.exception.ServiceException;

import java.util.List;

/**
 * Created by Владислав on 15.07.2016.
 */
public interface MovieService {
    List<Movie> getRecentAddedMovies(int amount, String languageId) throws ServiceException;
    List<Movie> getMoviesByCriteria(String name, int minYear, int maxYear,
                                    List<Integer> genreIds, List<Integer> countyIds, int minRating, int maxRating,
                                    int from, int amount, String languageId) throws ServiceException;
    int getMoviesCountByCriteria(String name, int minYear, int maxYear, List<Integer> genreIds,
                                 List<Integer> countyIds, int minRating, int maxRating) throws ServiceException;
    Movie getMovieById(int id, String languageId) throws ServiceException;
}
