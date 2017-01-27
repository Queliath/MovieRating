package by.bsuir.movierating.dao;

import by.bsuir.movierating.domain.Genre;

import java.util.List;

/**
 * Provides a DAO-logic for the Genre entity.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public interface GenreDAO {
    /**
     * Adds a genre to the data storage (in the default language).
     *
     * @param genre a genre object
     */
    void addGenre(Genre genre);

    /**
     * Updates a genre or adds/updates a localization of a genre in the data storage.
     *
     * If the languageId argument is an id of the default language of the application, then it updates
     * a genre. If the language argument is an id of the different language (not default) then it
     * adds/updates a localization of a genre (it based on the fact of existence of a localization:
     * if it doesn't exist then it will be added, otherwise a localization will be updated).
     * @param genre a genre object
     * @param languageId a language id like 'EN', "RU' etc.
     */
    void updateGenre(Genre genre, String languageId);

    /**
     * Deletes a genre from the data storage (with all of the localizations).
     *
     * @param id an id of a deleting genre
     */
    void deleteGenre(int id);

    /**
     * Returns all the genres from the data storage.
     *
     * @param languageId a language id like 'EN', "RU' etc.
     * @return all the genres
     */
    List<Genre> getAllGenres(String languageId);

    /**
     * Returns a genre by id from the data storage.
     *
     * @param id an id of a needed genre
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a genre by id
     */
    Genre getGenreById(int id, String languageId);

    /**
     * Returns a genres belonging to the movie from the data storage.
     *
     * @param movieId an id of the movie
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a genres belonging to the movie
     */
    List<Genre> getGenresByMovie(int movieId, String languageId);

    /**
     * Returns a genres ordered by a position number from the data storage.
     *
     * @param amount a needed amount of genres
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a genres ordered by a position number
     */
    List<Genre> getTopPositionGenres(int amount, String languageId);

    /**
     * Returns a genres from the data storage.
     *
     * @param from a start position in the genres list (started from 0)
     * @param amount a needed amount of genres
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a genres
     */
    List<Genre> getGenres(int from, int amount, String languageId);

    /**
     * Returns an amount of genres in the data storage.
     *
     * @return an amount of genres in the data storage
     */
    int getGenresCount();
}
