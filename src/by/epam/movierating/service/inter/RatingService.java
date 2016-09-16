package by.epam.movierating.service.inter;

import by.epam.movierating.service.exception.ServiceException;

/**
 * Provides a business-logic with the Rating entity.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public interface RatingService {
    int getRatingValueByMovieAndUser(int movieId, int userId) throws ServiceException;
    void addRating(int value, int movieId, int userId) throws ServiceException;
}
