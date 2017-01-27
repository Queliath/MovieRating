package by.bsuir.movierating.service.impl;

import by.bsuir.movierating.dao.GenreDAO;
import by.bsuir.movierating.domain.Genre;
import by.bsuir.movierating.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Provides a business-logic with the Genre entity.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
@Service("genreService")
public class GenreServiceImpl implements GenreService {
    private GenreDAO genreDAO;

    @Autowired
    public void setGenreDAO(GenreDAO genreDAO) {
        this.genreDAO = genreDAO;
    }

    /**
     * Returns a genres ordered by a position number.
     *
     * @param amount a needed amount of a genres
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a genres ordered by a position number
     */
    @Override
    public List<Genre> getTopPositionGenres(int amount, String languageId) {
        return genreDAO.getTopPositionGenres(amount, languageId);
    }

    /**
     * Returns a concrete amount of a genres from a concrete position.
     *
     * @param from a staring position in the genres list (starting from 0)
     * @param amount a needed amount of a genres
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a concrete amount of a genres from a concrete position
     */
    @Override
    public List<Genre> getGenres(int from, int amount, String languageId) {
        return genreDAO.getGenres(from, amount, languageId);
    }

    /**
     * Returns a total amount of a genres in the data storage.
     *
     * @return a total amount of a genres
     */
    @Override
    public int getGenresCount() {
        return genreDAO.getGenresCount();
    }

    /**
     * Returns a certain genre by id.
     *
     * @param id an id of a needed genre
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a certain genre
     */
    @Override
    public Genre getGenreById(int id, String languageId) {
        return genreDAO.getGenreById(id, languageId);
    }

    /**
     * Adds a new genre to the data storage (in the default language).
     *
     * @param name a name of the genre
     * @param position a number of a position of the genre
     */
    @Override
    public void addGenre(String name, int position) {
        Genre genre = new Genre();
        genre.setName(name);
        genre.setPosition(position);

        genreDAO.addGenre(genre);
    }

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
     */
    @Override
    public void editGenre(int id, String name, int position, String languageId) {
        Genre genre = new Genre();
        genre.setId(id);
        genre.setName(name);
        genre.setPosition(position);

        genreDAO.updateGenre(genre, languageId);
    }

    /**
     * Deletes an existing genre from the data storage (with all of the localizations).
     *
     * @param id an id of the deleting genre
     */
    @Override
    public void deleteGenre(int id) {
        genreDAO.deleteGenre(id);
    }
}
