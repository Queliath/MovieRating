package by.epam.movierating.service.impl;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.dao.inter.GenreDAO;
import by.epam.movierating.domain.Genre;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.inter.GenreService;

import java.util.List;

/**
 * Provides a business-logic with the Genre entity.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class GenreServiceImpl implements GenreService {
    private static final int NAME_MAX_LENGTH = 45;

    /**
     * Returns a genres ordered by a position number.
     *
     * @param amount a needed amount of a genres
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a genres ordered by a position number
     * @throws ServiceException
     */
    @Override
    public List<Genre> getTopPositionGenres(int amount, String languageId) throws ServiceException {
        if(amount <= 0){
            throw new ServiceException("Wrong amount value");
        }

        try {
            GenreDAO genreDAO = DAOFactory.getInstance().getGenreDAO();
            List<Genre> genres = genreDAO.getTopPositionGenres(amount, languageId);
            return genres;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get all genres", e);
        }
    }

    /**
     * Returns a concrete amount of a genres from a concrete position.
     *
     * @param from a staring position in the genres list (starting from 0)
     * @param amount a needed amount of a genres
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a concrete amount of a genres from a concrete position
     * @throws ServiceException
     */
    @Override
    public List<Genre> getGenres(int from, int amount, String languageId) throws ServiceException {
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            GenreDAO genreDAO = daoFactory.getGenreDAO();
            List<Genre> genres = genreDAO.getGenres(from, amount, languageId);
            return genres;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get genres", e);
        }
    }

    /**
     * Returns a total amount of a genres in the data storage.
     *
     * @return a total amount of a genres
     * @throws ServiceException
     */
    @Override
    public int getGenresCount() throws ServiceException {
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            GenreDAO genreDAO = daoFactory.getGenreDAO();
            int genresCount = genreDAO.getGenresCount();
            return genresCount;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get genres count", e);
        }
    }

    /**
     * Returns a certain genre by id.
     *
     * @param id an id of a needed genre
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a certain genre
     * @throws ServiceException
     */
    @Override
    public Genre getGenreById(int id, String languageId) throws ServiceException {
        if(id <= 0){
            throw new ServiceException("Wrong id for getting genre");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            GenreDAO genreDAO = daoFactory.getGenreDAO();
            Genre genre = genreDAO.getGenreById(id, languageId);
            return genre;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get genre by id", e);
        }
    }

    /**
     * Adds a new genre to the data storage (in the default language).
     *
     * @param name a name of the genre
     * @param position a number of a position of the genre
     * @throws ServiceException
     */
    @Override
    public void addGenre(String name, int position) throws ServiceException {
        if(name.isEmpty() || name.length() > NAME_MAX_LENGTH || position <= 0){
            throw new ServiceException("Wrong parameters for adding genre");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            GenreDAO genreDAO = daoFactory.getGenreDAO();

            Genre genre = new Genre();
            genre.setName(name);
            genre.setPosition(position);

            genreDAO.addGenre(genre);
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get genre by id", e);
        }
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
     * @throws ServiceException
     */
    @Override
    public void editGenre(int id, String name, int position, String languageId) throws ServiceException {
        if(id <= 0 || name.isEmpty() || name.length() > NAME_MAX_LENGTH || position <= 0){
            throw new ServiceException("Wrong parameters for editing genre");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            GenreDAO genreDAO = daoFactory.getGenreDAO();

            Genre genre = new Genre();
            genre.setId(id);
            genre.setName(name);
            genre.setPosition(position);

            genreDAO.updateGenre(genre, languageId);
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get genre by id", e);
        }
    }

    /**
     * Deletes an existing genre from the data storage (with all of the localizations).
     *
     * @param id an id of the deleting genre
     * @throws ServiceException
     */
    @Override
    public void deleteGenre(int id) throws ServiceException {
        if(id <= 0){
            throw new ServiceException("Wrong id for deleting genre");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            GenreDAO genreDAO = daoFactory.getGenreDAO();
            genreDAO.deleteGenre(id);
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot delete genre", e);
        }
    }
}
