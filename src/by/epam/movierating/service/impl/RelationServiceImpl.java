package by.epam.movierating.service.impl;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.dao.interfaces.MovieCountryDAO;
import by.epam.movierating.dao.interfaces.MovieGenreDAO;
import by.epam.movierating.dao.interfaces.MoviePersonRelationDAO;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.interfaces.RelationService;

/**
 * Created by Владислав on 10.08.2016.
 */
public class RelationServiceImpl implements RelationService {
    private static final int MIN_RELATION_TYPE = 1;
    private static final int MAX_RELATION_TYPE = 8;

    @Override
    public void addPersonToMovie(int movieId, int personId, int relationType) throws ServiceException {
        if(movieId <= 0 || personId <= 0 || relationType < MIN_RELATION_TYPE || relationType > MAX_RELATION_TYPE){
            throw new ServiceException("Wrong parameters for adding person to movie");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            MoviePersonRelationDAO moviePersonRelationDAO = daoFactory.getMoviePersonRelationDAO();
            moviePersonRelationDAO.addMovieToPersonWithRelation(movieId, personId, relationType);
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot add person to movie", e);
        }
    }

    @Override
    public void addCountryToMovie(int movieId, int countryId) throws ServiceException {
        if(movieId <= 0 || countryId <= 0){
            throw new ServiceException("Wrong parameters for adding country to movie");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            MovieCountryDAO movieCountryDAO = daoFactory.getMovieCountryDAO();
            movieCountryDAO.addMovieToCountry(movieId, countryId);
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot add country to movie", e);
        }
    }

    @Override
    public void addGenreToMovie(int movieId, int genreId) throws ServiceException {
        if(movieId <= 0 || genreId <= 0){
            throw new ServiceException("Wrong parameters for adding genre to movie");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            MovieGenreDAO movieGenreDAO = daoFactory.getMovieGenreDAO();
            movieGenreDAO.addMovieToGenre(movieId, genreId);
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot add genre to movie", e);
        }
    }

    @Override
    public void deletePersonFromMovie(int movieId, int personId, int relationType) throws ServiceException {
        if(movieId <= 0 || personId <= 0 || relationType < MIN_RELATION_TYPE || relationType > MAX_RELATION_TYPE){
            throw new ServiceException("Wrong parameters for deleting person to movie");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            MoviePersonRelationDAO moviePersonRelationDAO = daoFactory.getMoviePersonRelationDAO();
            moviePersonRelationDAO.deleteMovieFromPersonWithRelation(movieId, personId, relationType);
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot delete person from movie", e);
        }
    }

    @Override
    public void deleteCountryFromMovie(int movieId, int countryId) throws ServiceException {
        if(movieId <= 0 || countryId <= 0){
            throw new ServiceException("Wrong parameters for deleting country to movie");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            MovieCountryDAO movieCountryDAO = daoFactory.getMovieCountryDAO();
            movieCountryDAO.deleteMovieFromCountry(movieId, countryId);
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot delete country from movie", e);
        }
    }

    @Override
    public void deleteGenreFromMovie(int movieId, int genreId) throws ServiceException {
        if(movieId <= 0 || genreId <= 0){
            throw new ServiceException("Wrong parameters for deleting genre to movie");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            MovieGenreDAO movieGenreDAO = daoFactory.getMovieGenreDAO();
            movieGenreDAO.deleteMovieFromGenre(movieId, genreId);
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot delete genre from movie", e);
        }
    }
}
