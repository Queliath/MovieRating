package by.epam.movierating.dao.inter;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.domain.Movie;
import by.epam.movierating.domain.criteria.MovieCriteria;

import java.util.List;

/**
 * Provides a DAO-logic for the Movie entity.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public interface MovieDAO {
    void addMovie(Movie movie) throws DAOException;
    void updateMovie(Movie movie, String languageId) throws DAOException;
    void deleteMovie(int id) throws DAOException;
    List<Movie> getAllMovies(String languageId) throws DAOException;
    Movie getMovieById(int id, String languageId) throws DAOException;
    List<Movie> getMoviesByGenre(int genreId, String languageId) throws DAOException;
    List<Movie> getMoviesByCountry(int countryId, String languageId) throws DAOException;
    List<Movie> getMoviesByPersonAndRelationType(int personId, int relationType, String languageId) throws DAOException;
    List<Movie> getRecentAddedMovies(int amount, String languageId) throws DAOException;
    List<Movie> getMoviesByCriteria(MovieCriteria criteria, int from, int amount, String languageId) throws DAOException;
    int getMoviesCountByCriteria(MovieCriteria criteria, String languageId) throws DAOException;
}
