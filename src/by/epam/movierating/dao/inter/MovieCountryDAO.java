package by.epam.movierating.dao.inter;

import by.epam.movierating.dao.exception.DAOException;

/**
 * Provides a DAO-logic for the relations between Movie and Country entities.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public interface MovieCountryDAO {
    void addMovieToCountry(int movieId, int countryId) throws DAOException;
    void deleteMovieFromCountry(int movieId, int countryId) throws DAOException;
}
