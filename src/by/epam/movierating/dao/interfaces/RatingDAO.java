package by.epam.movierating.dao.interfaces;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.domain.Rating;

import java.util.List;

/**
 * Created by Владислав on 11.06.2016.
 */
public interface RatingDAO {
    void addRating(Rating rating) throws DAOException;
    void updateRating(Rating rating) throws DAOException;
    void deleteRating(int movieId, int userId) throws DAOException;
    Rating getRatingByMovieAndUser(int movieId, int userId) throws DAOException;
    List<Rating> getRatingsByMovie(int movieId) throws DAOException;
    List<Rating> getRatingsByUser(int userId) throws DAOException;
}
