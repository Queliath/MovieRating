package by.bsuir.movierating.dao;

/**
 * Provides a DAO-logic for the relations between Movie and Country entities.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public interface MovieCountryDAO {
    /**
     * Adds a relation between the movie and the country.
     *
     * @param movieId an id of the movie
     * @param countryId an id of the country
     */
    void addMovieToCountry(int movieId, int countryId);

    /**
     * Deletes a relation between the movie and the country.
     *
     * @param movieId an id of the movie
     * @param countryId an id of the country
     */
    void deleteMovieFromCountry(int movieId, int countryId);
}
