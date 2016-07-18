package by.epam.movierating.service.impl;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.dao.interfaces.GenreDAO;
import by.epam.movierating.domain.Genre;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.interfaces.GenreService;

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
}
