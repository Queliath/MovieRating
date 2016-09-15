package by.epam.movierating.dao.inter;

import by.epam.movierating.dao.exception.DAOException;

/**
 * Provides a DAO-logic for the relations between Movie and Genre entities.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public interface MovieGenreDAO {
    void addMovieToGenre(int movieId, int genreId) throws DAOException;
    void deleteMovieFromGenre(int movieId, int genreId) throws DAOException;
}
