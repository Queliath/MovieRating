package by.epam.movierating.service.interfaces;

import by.epam.movierating.service.exception.ServiceException;

/**
 * Created by Владислав on 21.07.2016.
 */
public interface RatingService {
    int getRatingValueByMovieAndUser(int movieId, int userId) throws ServiceException;
    void addRating(int value, int movieId, int userId) throws ServiceException;
}
