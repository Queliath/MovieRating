package by.epam.movierating.service.impl;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.dao.interfaces.*;
import by.epam.movierating.domain.Country;
import by.epam.movierating.domain.Genre;
import by.epam.movierating.domain.Movie;
import by.epam.movierating.domain.Person;
import by.epam.movierating.domain.criteria.MovieCriteria;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.interfaces.MovieService;

import java.util.List;

/**
 * Created by Владислав on 15.07.2016.
 */
public class MovieServiceImpl implements MovieService {
    private static final int ACTOR = 1;
    private static final int DIRECTOR = 2;

    @Override
    public List<Movie> getRecentAddedMovies(int amount, String languageId) throws ServiceException {
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            MovieDAO movieDAO = daoFactory.getMovieDAO();
            List<Movie> movies = movieDAO.getRecentAddedMovies(amount, languageId);

            CountryDAO countryDAO = daoFactory.getCountryDAO();
            GenreDAO genreDAO = daoFactory.getGenreDAO();
            PersonDAO personDAO = daoFactory.getPersonDAO();
            RatingDAO ratingDAO = daoFactory.getRatingDAO();
            for(Movie movie : movies){
                List<Country> countries = countryDAO.getCountriesByMovie(movie.getId(), languageId);
                movie.setCountries(countries);

                List<Genre> genres = genreDAO.getGenresByMovie(movie.getId(), languageId);
                movie.setGenres(genres);

                List<Person> directors = personDAO.getPersonsByMovieAndRelationType(movie.getId(), DIRECTOR, languageId);
                movie.setDirectors(directors);

                double averageRating = ratingDAO.getAverageRatingByMovie(movie.getId());
                movie.setAverageRating(averageRating);
            }
            return movies;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get all movies", e);
        }
    }

    @Override
    public List<Movie> getMoviesByCriteria(String name, int minYear, int maxYear, List<Integer> genreIds, List<Integer> countyIds, int minRating, int maxRating, int from, int amount, String languageId) throws ServiceException {
        MovieCriteria criteria = new MovieCriteria();
        criteria.setName(name);
        criteria.setMinYear(minYear);
        criteria.setMaxYear(maxYear);
        criteria.setGenreIds(genreIds);
        criteria.setCountryIds(countyIds);
        criteria.setMinRating(minRating);
        criteria.setMaxRating(maxRating);

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            MovieDAO movieDAO = daoFactory.getMovieDAO();
            List<Movie> movies = movieDAO.getMoviesByCriteria(criteria, from, amount, languageId);

            CountryDAO countryDAO = daoFactory.getCountryDAO();
            GenreDAO genreDAO = daoFactory.getGenreDAO();
            PersonDAO personDAO = daoFactory.getPersonDAO();
            RatingDAO ratingDAO = daoFactory.getRatingDAO();
            for(Movie movie : movies){
                List<Country> countries = countryDAO.getCountriesByMovie(movie.getId(), languageId);
                movie.setCountries(countries);

                List<Genre> genres = genreDAO.getGenresByMovie(movie.getId(), languageId);
                movie.setGenres(genres);

                List<Person> directors = personDAO.getPersonsByMovieAndRelationType(movie.getId(), DIRECTOR, languageId);
                movie.setDirectors(directors);

                double averageRating = ratingDAO.getAverageRatingByMovie(movie.getId());
                movie.setAverageRating(averageRating);
            }
            return movies;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get movies by criteria", e);
        }
    }

    @Override
    public int getMoviesCountByCriteria(String name, int minYear, int maxYear, List<Integer> genreIds, List<Integer> countyIds, int minRating, int maxRating) throws ServiceException {
        MovieCriteria criteria = new MovieCriteria();
        criteria.setName(name);
        criteria.setMinYear(minYear);
        criteria.setMaxYear(maxYear);
        criteria.setGenreIds(genreIds);
        criteria.setCountryIds(countyIds);
        criteria.setMinRating(minRating);
        criteria.setMaxRating(maxRating);

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            MovieDAO movieDAO = daoFactory.getMovieDAO();
            int moviesCount = movieDAO.getMoviesCountByCriteria(criteria);
            return moviesCount;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get movies count by criteria", e);
        }
    }
}
