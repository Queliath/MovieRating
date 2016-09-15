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
    void addRating(Rating rating) throws DAOException;
    void updateRating(Rating rating) throws DAOException;
    void deleteRating(int movieId, int userId) throws DAOException;
    Rating getRatingByMovieAndUser(int movieId, int userId) throws DAOException;
    double getAverageRatingByMovie(int movieId) throws DAOException;
    List<Rating> getRatingsByUser(int userId) throws DAOException;
}
