package by.bsuir.movierating.dao;

import by.bsuir.movierating.domain.Rating;

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
     */
    void addRating(Rating rating);

    /**
     * Updates a rating in the data storage.
     *
     * @param rating a rating object
     */
    void updateRating(Rating rating);

    /**
     * Deletes a rating from the data storage.
     *
     * @param movieId an id of the movie to which the rating belongs
     * @param userId an id of the user to which the rating belongs
     */
    void deleteRating(int movieId, int userId);

    /**
     * Returns a rating belonging to the movie and the user.
     *
     * @param movieId an id of the movie
     * @param userId an id of the user
     * @return a rating belonging to the movie and the user
     */
    Rating getRatingByMovieAndUser(int movieId, int userId);

    /**
     * Returns an average rating of the movie.
     *
     * @param movieId an id of the movie
     * @return an average rating of the movie
     */
    double getAverageRatingByMovie(int movieId);

    /**
     * Returns a ratings belonging to the user.
     *
     * @param userId an id of the user
     * @return a ratings belonging to the user
     */
    List<Rating> getRatingsByUser(int userId);
}
