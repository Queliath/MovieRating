package by.epam.movierating.service.inter;

import by.epam.movierating.service.exception.ServiceException;

/**
 * Provides a business-logic for the relations between entities.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public interface RelationService {
    void addPersonToMovie(int movieId, int personId, int relationType) throws ServiceException;
    void addCountryToMovie(int movieId, int countryId) throws ServiceException;
    void addGenreToMovie(int movieId, int genreId) throws ServiceException;
    void deletePersonFromMovie(int movieId, int personId, int relationType) throws ServiceException;
    void deleteCountryFromMovie(int movieId, int countryId) throws ServiceException;
    void deleteGenreFromMovie(int movieId, int genreId) throws ServiceException;
}
