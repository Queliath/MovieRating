package by.epam.movierating.service.impl;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.dao.inter.RatingDAO;
import by.epam.movierating.domain.Rating;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.inter.RatingService;

/**
 * Provides a business-logic with the Rating entity.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class RatingServiceImpl implements RatingService {
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 10;

    /**
     * Returns a value of the rating belonging to the movie and to the user (if exists).
     *
     * @param movieId an id of the movie
     * @param userId an id of the user
     * @return a value of the rating
     * @throws ServiceException
     */
    @Override
    public int getRatingValueByMovieAndUser(int movieId, int userId) throws ServiceException {
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            RatingDAO ratingDAO = daoFactory.getRatingDAO();
            Rating rating = ratingDAO.getRatingByMovieAndUser(movieId, userId);
            return (rating == null) ? -1 : rating.getValue();
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get rating by movie and user", e);
        }
    }

    /**
     * Adds a new rating to the data storage.
     *
     * @param value a value of the rating
     * @param movieId an id of the movie to which the rating belongs
     * @param userId an id of the user to which the rating belongs
     * @throws ServiceException
     */
    @Override
    public void addRating(int value, int movieId, int userId) throws ServiceException {
        if(value < MIN_VALUE || value > MAX_VALUE || movieId <= 0 || userId <= 0){
            throw new ServiceException("Wrong parameters for adding rating");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            RatingDAO ratingDAO = daoFactory.getRatingDAO();

            Rating rating = new Rating();
            rating.setValue(value);
            rating.setMovieId(movieId);
            rating.setUserId(userId);

            ratingDAO.addRating(rating);
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot add rating", e);
        }
    }

    @Override
    public void deleteRating(int movieId, int userId) throws ServiceException {
        if(movieId <= 0 || userId <= 0){
            throw new ServiceException("Wrong parameters for deleting movie");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            RatingDAO ratingDAO = daoFactory.getRatingDAO();
            ratingDAO.deleteRating(movieId, userId);
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot delete a rating", e);
        }
    }
}
