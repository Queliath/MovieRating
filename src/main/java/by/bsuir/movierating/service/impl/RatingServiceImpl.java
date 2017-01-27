package by.bsuir.movierating.service.impl;

import by.bsuir.movierating.dao.RatingDAO;
import by.bsuir.movierating.domain.Rating;
import by.bsuir.movierating.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Provides a business-logic with the Rating entity.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
@Service("ratingService")
public class RatingServiceImpl implements RatingService {
    private RatingDAO ratingDAO;

    @Autowired
    public void setRatingDAO(RatingDAO ratingDAO) {
        this.ratingDAO = ratingDAO;
    }

    /**
     * Returns a value of the rating belonging to the movie and to the user (if exists).
     *
     * @param movieId an id of the movie
     * @param userId an id of the user
     * @return a value of the rating
     */
    @Override
    public int getRatingValueByMovieAndUser(int movieId, int userId) {
        Rating rating = ratingDAO.getRatingByMovieAndUser(movieId, userId);
        return (rating == null) ? -1 : rating.getValue();
    }

    /**
     * Adds a new rating to the data storage.
     *
     * @param value a value of the rating
     * @param movieId an id of the movie to which the rating belongs
     * @param userId an id of the user to which the rating belongs
     */
    @Override
    public void addRating(int value, int movieId, int userId) {
        Rating rating = new Rating();
        rating.setValue(value);
        rating.setMovieId(movieId);
        rating.setUserId(userId);

        ratingDAO.addRating(rating);
    }

    @Override
    public void deleteRating(int movieId, int userId) {
        ratingDAO.deleteRating(movieId, userId);
    }
}
