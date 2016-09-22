package by.epam.movierating.dao.inter;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.domain.Genre;

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
     * @throws DAOException
     */
    void addGenre(Genre genre) throws DAOException;

    /**
     * Updates a genre or adds/updates a localization of a genre in the data storage.
     *
     * If the languageId argument is an id of the default language of the application, then it updates
     * a genre. If the language argument is an id of the different language (not default) then it
     * adds/updates a localization of a genre (it based on the fact of existence of a localization:
     * if it doesn't exist then it will be added, otherwise a localization will be updated).
     * @param genre a genre object
     * @param languageId a language id like 'EN', "RU' etc.
     * @throws DAOException
     */
    void updateGenre(Genre genre, String languageId) throws DAOException;

    /**
     * Deletes a genre from the data storage (with all of the localizations).
     *
     * @param id an id of a deleting genre
     * @throws DAOException
     */
    void deleteGenre(int id) throws DAOException;

    /**
     * Returns all the genres from the data storage.
     *
     * @param languageId a language id like 'EN', "RU' etc.
     * @return all the genres
     * @throws DAOException
     */
    List<Genre> getAllGenres(String languageId) throws DAOException;

    /**
     * Returns a genre by id from the data storage.
     *
     * @param id an id of a needed genre
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a genre by id
     * @throws DAOException
     */
    Genre getGenreById(int id, String languageId) throws DAOException;

    /**
     * Returns a genres belonging to the movie from the data storage.
     *
     * @param movieId an id of the movie
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a genres belonging to the movie
     * @throws DAOException
     */
    List<Genre> getGenresByMovie(int movieId, String languageId) throws DAOException;

    /**
     * Returns a genres ordered by a position number from the data storage.
     *
     * @param amount a needed amount of genres
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a genres ordered by a position number
     * @throws DAOException
     */
    List<Genre> getTopPositionGenres(int amount, String languageId) throws DAOException;

    /**
     * Returns a genres from the data storage.
     *
     * @param from a start position in the genres list (started from 0)
     * @param amount a needed amount of genres
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a genres
     * @throws DAOException
     */
    List<Genre> getGenres(int from, int amount, String languageId) throws DAOException;

    /**
     * Returns an amount of genres in the data storage.
     *
     * @return an amount of genres in the data storage
     * @throws DAOException
     */
    int getGenresCount() throws DAOException;
}
