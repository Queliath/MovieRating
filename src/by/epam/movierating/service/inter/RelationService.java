package by.epam.movierating.service.inter;

import by.epam.movierating.service.exception.ServiceException;

/**
 * Provides a business-logic for the relations between entities.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public interface RelationService {
    /**
     * Adds a relation between the movie and the person.
     *
     * @param movieId an id of the movie
     * @param personId an id of the person
     * @param relationType an id of the relation type (role)
     * @throws ServiceException
     */
    void addPersonToMovie(int movieId, int personId, int relationType) throws ServiceException;

    /**
     * Adds a relation between the movie and the country.
     *
     * @param movieId an id of the movie
     * @param countryId an id of the country
     * @throws ServiceException
     */
    void addCountryToMovie(int movieId, int countryId) throws ServiceException;

    /**
     * Adds a relation between the movie and the genre.
     *
     * @param movieId an id of the movie
     * @param genreId an id of the genre
     * @throws ServiceException
     */
    void addGenreToMovie(int movieId, int genreId) throws ServiceException;

    /**
     * Deletes a relation between the movie and the person.
     *
     * @param movieId an id of the movie
     * @param personId an id of the person
     * @param relationType an id of the relation type (role)
     * @throws ServiceException
     */
    void deletePersonFromMovie(int movieId, int personId, int relationType) throws ServiceException;

    /**
     * Deletes a relation between the movie and the country.
     *
     * @param movieId an id of the movie
     * @param countryId an id of the country
     * @throws ServiceException
     */
    void deleteCountryFromMovie(int movieId, int countryId) throws ServiceException;

    /**
     * Deletes a relation between the movie and the genre.
     *
     * @param movieId an id of the movie
     * @param genreId an id of the genre
     * @throws ServiceException
     */
    void deleteGenreFromMovie(int movieId, int genreId) throws ServiceException;
}
