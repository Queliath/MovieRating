package by.epam.movierating.dao.inter;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.domain.Rating;

import java.util.List;

/**
 * Provides a DAO-logic for the Rating entity.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public interface RatingDAO {
    /**
     * Adds a rating to the data storage.
     *
     * @param rating a rating object
     * @throws DAOException
     */
    void addRating(Rating rating) throws DAOException;

    /**
     * Updates a rating in the data storage.
     *
     * @param rating a rating object
     * @throws DAOException
     */
    void updateRating(Rating rating) throws DAOException;

    /**
     * Deletes a rating from the data storage.
     *
     * @param movieId an id of the movie to which the rating belongs
     * @param userId an id of the user to which the rating belongs
     * @throws DAOException
     */
    void deleteRating(int movieId, int userId) throws DAOException;

    /**
     * Returns a rating belonging to the movie and the user.
     *
     * @param movieId an id of the movie
     * @param userId an id of the user
     * @return a rating belonging to the movie and the user
     * @throws DAOException
     */
    Rating getRatingByMovieAndUser(int movieId, int userId) throws DAOException;

    /**
     * Returns an average rating of the movie.
     *
     * @param movieId an id of the movie
     * @return an average rating of the movie
     * @throws DAOException
     */
    double getAverageRatingByMovie(int movieId) throws DAOException;

    /**
     * Returns a ratings belonging to the user.
     *
     * @param userId an id of the user
     * @return a ratings belonging to the user
     * @throws DAOException
     */
    List<Rating> getRatingsByUser(int userId) throws DAOException;
}
