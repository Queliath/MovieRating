package by.epam.movierating.dao.interfaces;

import by.epam.movierating.dao.exception.DAOException;

/**
 * Created by Владислав on 19.06.2016.
 */
public interface MovieCountryDAO {
    void addMovieToCountry(int movieId, int countryId) throws DAOException;
    void deleteMovieFromCountry(int movieId, int countryId) throws DAOException;
}
