package by.epam.movierating.service.impl;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.dao.interfaces.RatingDAO;
import by.epam.movierating.domain.Rating;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.interfaces.RatingService;

/**
 * Created by Владислав on 21.07.2016.
 */
public class RatingServiceImpl implements RatingService {
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

    @Override
    public void addRating(int value, int movieId, int userId) throws ServiceException {
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
}
