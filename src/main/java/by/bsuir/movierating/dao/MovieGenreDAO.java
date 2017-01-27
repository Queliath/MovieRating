package by.bsuir.movierating.dao;

/**
 * Provides a DAO-logic for the relations between Movie and Genre entities.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public interface MovieGenreDAO {
    /**
     * Adds a relation between the movie and the genre to the data storage.
     *
     * @param movieId an id of the movie
     * @param genreId an id of the genre
     */
    void addMovieToGenre(int movieId, int genreId);

    /**
     * Deletes a relation between the movie and the genre from the data storage.
     *
     * @param movieId an id of the movie
     * @param genreId an id of the genre
     */
    void deleteMovieFromGenre(int movieId, int genreId);
}
