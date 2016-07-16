package by.epam.movierating.service.interfaces;

import by.epam.movierating.domain.Genre;
import by.epam.movierating.service.exception.ServiceException;

import java.util.List;

/**
 * Created by Владислав on 15.07.2016.
 */
public interface GenreService {
    List<Genre> getAllGenres(String languageId) throws ServiceException;
}
