package by.epam.movierating.service.impl;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.dao.interfaces.*;
import by.epam.movierating.domain.*;
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
    private static final int PRODUCER = 3;
    private static final int WRITER = 4;
    private static final int OPERATOR = 5;
    private static final int PAINTER = 6;
    private static final int EDITOR = 7;
    private static final int COMPOSER = 8;

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
    public int getMoviesCountByCriteria(String name, int minYear, int maxYear, List<Integer> genreIds, List<Integer> countyIds, int minRating, int maxRating, String languageId) throws ServiceException {
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
            int moviesCount = movieDAO.getMoviesCountByCriteria(criteria, languageId);
            return moviesCount;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get movies count by criteria", e);
        }
    }

    @Override
    public Movie getMovieById(int id, String languageId) throws ServiceException {
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            MovieDAO movieDAO = daoFactory.getMovieDAO();
            Movie movie = movieDAO.getMovieById(id, languageId);

            if(movie != null){
                CountryDAO countryDAO = daoFactory.getCountryDAO();
                List<Country> countries = countryDAO.getCountriesByMovie(movie.getId(), languageId);
                movie.setCountries(countries);

                GenreDAO genreDAO = daoFactory.getGenreDAO();
                List<Genre> genres = genreDAO.getGenresByMovie(movie.getId(), languageId);
                movie.setGenres(genres);

                RatingDAO ratingDAO = daoFactory.getRatingDAO();
                double averageRating = ratingDAO.getAverageRatingByMovie(movie.getId());
                movie.setAverageRating(averageRating);

                CommentDAO commentDAO = daoFactory.getCommentDAO();
                List<Comment> comments = commentDAO.getCommentsByMovie(movie.getId(), languageId);
                UserDAO userDAO = daoFactory.getUserDAO();
                for(Comment comment : comments){
                    User user = userDAO.getUserById(comment.getUserId());
                    comment.setUser(user);
                }
                movie.setComments(comments.isEmpty() ? null : comments);

                PersonDAO personDAO = daoFactory.getPersonDAO();
                List<Person> actors = personDAO.getPersonsByMovieAndRelationType(movie.getId(), ACTOR, languageId);
                List<Person> directors = personDAO.getPersonsByMovieAndRelationType(movie.getId(), DIRECTOR, languageId);
                List<Person> producers = personDAO.getPersonsByMovieAndRelationType(movie.getId(), PRODUCER, languageId);
                List<Person> writers = personDAO.getPersonsByMovieAndRelationType(movie.getId(), WRITER, languageId);
                List<Person> operators = personDAO.getPersonsByMovieAndRelationType(movie.getId(), OPERATOR, languageId);
                List<Person> painters = personDAO.getPersonsByMovieAndRelationType(movie.getId(), PAINTER, languageId);
                List<Person> editors = personDAO.getPersonsByMovieAndRelationType(movie.getId(), EDITOR, languageId);
                List<Person> composers = personDAO.getPersonsByMovieAndRelationType(movie.getId(), COMPOSER, languageId);
                movie.setActors(actors.isEmpty() ? null : actors);
                movie.setDirectors(directors.isEmpty() ? null : directors);
                movie.setProducers(producers.isEmpty() ? null : producers);
                movie.setWriters(writers.isEmpty() ? null : writers);
                movie.setOperators(operators.isEmpty() ? null : operators);
                movie.setPainters(painters.isEmpty() ? null : painters);
                movie.setEditors(editors.isEmpty() ? null : editors);
                movie.setComposers(composers.isEmpty() ? null : composers);
            }

            return movie;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get movie by Id", e);
        }
    }
}
