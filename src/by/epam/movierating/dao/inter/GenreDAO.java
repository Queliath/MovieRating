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
    void addGenre(Genre genre) throws DAOException;
    void updateGenre(Genre genre, String languageId) throws DAOException;
    void deleteGenre(int id) throws DAOException;
    List<Genre> getAllGenres(String languageId) throws DAOException;
    Genre getGenreById(int id, String languageId) throws DAOException;
    List<Genre> getGenresByMovie(int movieId, String languageId) throws DAOException;
    List<Genre> getTopPositionGenres(int amount, String languageId) throws DAOException;
    List<Genre> getGenres(int from, int amount, String languageId) throws DAOException;
    int getGenresCount() throws DAOException;
}
