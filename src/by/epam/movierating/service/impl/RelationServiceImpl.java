package by.epam.movierating.service.impl;

import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.interfaces.RelationService;

/**
 * Created by Владислав on 10.08.2016.
 */
public class RelationServiceImpl implements RelationService {
    @Override
    public void addPersonToMovie(int movieId, int personId, int relationType) throws ServiceException {

    }

    @Override
    public void addCountryToMovie(int movieId, int countryId) throws ServiceException {

    }

    @Override
    public void addGenreToMovie(int movieId, int genreId) throws ServiceException {

    }
}
