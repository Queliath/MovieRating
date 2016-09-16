package by.epam.movierating.dao.inter;

import by.epam.movierating.dao.exception.DAOException;

/**
 * Provides a DAO-logic for the relations between Movie and Genre entities.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public interface MovieGenreDAO {
    /**
     * Adds a relation between the movie and the genre to the data storage.
     *
     * @param movieId an id of the movie
     * @param genreId an id of the genre
     * @throws DAOException
     */
    void addMovieToGenre(int movieId, int genreId) throws DAOException;

    /**
     * Deletes a relation between the movie and the genre from the data storage.
     *
     * @param movieId an id of the movie
     * @param genreId an id of the genre
     * @throws DAOException
     */
    void deleteMovieFromGenre(int movieId, int genreId) throws DAOException;
}
