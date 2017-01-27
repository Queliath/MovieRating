package by.bsuir.movierating.service;

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
     */
    void addPersonToMovie(int movieId, int personId, int relationType);

    /**
     * Adds a relation between the movie and the country.
     *
     * @param movieId an id of the movie
     * @param countryId an id of the country
     */
    void addCountryToMovie(int movieId, int countryId);

    /**
     * Adds a relation between the movie and the genre.
     *
     * @param movieId an id of the movie
     * @param genreId an id of the genre
     */
    void addGenreToMovie(int movieId, int genreId);

    /**
     * Deletes a relation between the movie and the person.
     *
     * @param movieId an id of the movie
     * @param personId an id of the person
     * @param relationType an id of the relation type (role)
     */
    void deletePersonFromMovie(int movieId, int personId, int relationType);

    /**
     * Deletes a relation between the movie and the country.
     *
     * @param movieId an id of the movie
     * @param countryId an id of the country
     */
    void deleteCountryFromMovie(int movieId, int countryId);

    /**
     * Deletes a relation between the movie and the genre.
     *
     * @param movieId an id of the movie
     * @param genreId an id of the genre
     */
    void deleteGenreFromMovie(int movieId, int genreId);
}
