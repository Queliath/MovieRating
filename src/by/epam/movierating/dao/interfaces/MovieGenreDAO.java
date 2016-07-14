package by.epam.movierating.dao.interfaces;

import by.epam.movierating.dao.exception.DAOException;

/**
 * Created by Владислав on 19.06.2016.
 */
public interface MovieGenreDAO {
    void addMovieToGenre(int movieId, int genreId) throws DAOException;
    void deleteMovieFromGenre(int movieId, int genreId) throws DAOException;
}
