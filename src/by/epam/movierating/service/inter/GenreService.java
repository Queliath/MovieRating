package by.epam.movierating.service.inter;

import by.epam.movierating.domain.Genre;
import by.epam.movierating.service.exception.ServiceException;

import java.util.List;

/**
 * Provides a business-logic with the Genre entity.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public interface GenreService {
    /**
     * Returns a genres ordered by a position number.
     *
     * @param amount a needed amount of a genres
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a genres ordered by a position number
     * @throws ServiceException
     */
    List<Genre> getTopPositionGenres(int amount, String languageId) throws ServiceException;

    /**
     * Returns a concrete amount of a genres from a concrete position.
     *
     * @param from a staring position in the genres list (starting from 0)
     * @param amount a needed amount of a genres
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a concrete amount of a genres from a concrete position
     * @throws ServiceException
     */
    List<Genre> getGenres(int from, int amount, String languageId) throws ServiceException;

    /**
     * Returns a total amount of a genres in the data storage.
     *
     * @return a total amount of a genres
     * @throws ServiceException
     */
    int getGenresCount() throws ServiceException;

    /**
     * Returns a certain genre by id.
     *
     * @param id an id of a needed genre
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a certain genre
     * @throws ServiceException
     */
    Genre getGenreById(int id, String languageId) throws ServiceException;

    /**
     * Adds a new genre to the data storage (in the default language).
     *
     * @param name a name of the genre
     * @param position a number of a position of the genre
     * @throws ServiceException
     */
    void addGenre(String name, int position) throws ServiceException;

    /**
     * Edits an already existing genre or adds/edits a localizations of a genre.
     *
     * If the languageId argument is an id of the default language of the application, then it updates
     * a genre. If the language argument is an id of the different language (not default) then it
     * adds/updates a localization of a genre (it based on the fact of existence of a localization:
     * if it doesn't exist then it will be added, otherwise a localization will be updated).
     * @param id an id of the needed genre
     * @param name a new name of the genre
     * @param position a new position number of the genre
     * @param languageId a language id like 'EN', "RU' etc.
     * @throws ServiceException
     */
    void editGenre(int id, String name, int position, String languageId) throws ServiceException;

    /**
     * Deletes an existing genre from the data storage (with all of the localizations).
     *
     * @param id an id of the deleting genre
     * @throws ServiceException
     */
    void deleteGenre(int id) throws ServiceException;
}
