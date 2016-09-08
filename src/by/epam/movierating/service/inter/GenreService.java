package by.epam.movierating.service.inter;

import by.epam.movierating.domain.Genre;
import by.epam.movierating.service.exception.ServiceException;

import java.util.List;

/**
 * Created by Владислав on 15.07.2016.
 */
public interface GenreService {
    List<Genre> getTopPositionGenres(int amount, String languageId) throws ServiceException;
    List<Genre> getGenres(int from, int amount, String languageId) throws ServiceException;
    int getGenresCount() throws ServiceException;
    Genre getGenreById(int id, String languageId) throws ServiceException;
    void addGenre(String name, int position) throws ServiceException;
    void editGenre(int id, String name, int position, String languageId) throws ServiceException;
    void deleteGenre(int id) throws ServiceException;
}
