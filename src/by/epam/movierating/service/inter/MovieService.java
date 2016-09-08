package by.epam.movierating.service.inter;

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
                                 List<Integer> countyIds, int minRating, int maxRating, String languageId) throws ServiceException;
    Movie getMovieById(int id, String languageId) throws ServiceException;
    void addMovie(String name, int year, String tagline, int budget, String premiere, int lasting,
                  String annotation, String image) throws ServiceException;
    void editMovie(int id, String name, int year, String tagline, int budget, String premiere, int lasting,
                   String annotation, String image, String languageId) throws ServiceException;
    void deleteMovie(int id) throws ServiceException;
}
