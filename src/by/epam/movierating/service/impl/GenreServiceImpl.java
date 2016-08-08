package by.epam.movierating.service.impl;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.dao.interfaces.GenreDAO;
import by.epam.movierating.domain.Genre;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.interfaces.GenreService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 15.07.2016.
 */
public class GenreServiceImpl implements GenreService {
    @Override
    public List<Genre> getTopPositionGenres(int amount, String languageId) throws ServiceException {
        try {
            GenreDAO genreDAO = DAOFactory.getInstance().getGenreDAO();
            List<Genre> genres = genreDAO.getTopPositionGenres(amount, languageId);
            return genres;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get all genres", e);
        }
    }

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

    @Override
    public Genre getGenreById(int id, String languageId) throws ServiceException {
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            GenreDAO genreDAO = daoFactory.getGenreDAO();
            Genre genre = genreDAO.getGenreById(id, languageId);
            return genre;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get genre by id", e);
        }
    }

    @Override
    public void addGenre(String name, int position) throws ServiceException {
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

    @Override
    public void editGenre(int id, String name, int position, String languageId) throws ServiceException {
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

    @Override
    public void deleteGenre(int id) throws ServiceException {

    }
}
