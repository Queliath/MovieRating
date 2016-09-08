package by.epam.movierating.service.impl;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.dao.inter.*;
import by.epam.movierating.domain.*;
import by.epam.movierating.domain.criteria.MovieCriteria;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.inter.MovieService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";

    private static final int NAME_MAX_LENGTH = 45;
    private static final int IMAGE_MAX_LENGTH = 150;

    @Override
    public List<Movie> getRecentAddedMovies(int amount, String languageId) throws ServiceException {
        if(amount <= 0){
            throw new ServiceException("Wrong amount value");
        }

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
        if(id <= 0){
            throw new ServiceException("Wrong id for getting movie");
        }

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

    @Override
    public void addMovie(String name, int year, String tagline, int budget, String premiere, int lasting, String annotation, String image) throws ServiceException {
        if(name.isEmpty() || name.length() > NAME_MAX_LENGTH || year <= 0 || tagline.isEmpty() ||
                budget <= 0 || premiere.isEmpty() || lasting <= 0 || annotation.isEmpty() || image.isEmpty() ||
                image.length() > IMAGE_MAX_LENGTH){
            throw new ServiceException("Wrong parameters for adding movie");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            MovieDAO movieDAO = daoFactory.getMovieDAO();

            Movie movie = new Movie();
            movie.setName(name);
            movie.setYear(year);
            movie.setTagline(tagline);
            movie.setBudget(budget);
            DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);
            movie.setPremiere(dateFormat.parse(premiere));
            movie.setLasting(lasting);
            movie.setAnnotation(annotation);
            movie.setImage(image);

            movieDAO.addMovie(movie);
        } catch (DAOException | ParseException e) {
            throw new ServiceException("Service layer: cannot add a movie", e);
        }
    }

    @Override
    public void editMovie(int id, String name, int year, String tagline, int budget, String premiere, int lasting, String annotation, String image, String languageId) throws ServiceException {
        if(id <= 0 || name.isEmpty() || name.length() > NAME_MAX_LENGTH || year <= 0 || tagline.isEmpty() ||
                budget <= 0 || premiere.isEmpty() || lasting <= 0 || annotation.isEmpty() || image.isEmpty() ||
                image.length() > IMAGE_MAX_LENGTH){
            throw new ServiceException("Wrong parameters for editing movie");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            MovieDAO movieDAO = daoFactory.getMovieDAO();

            Movie movie = new Movie();
            movie.setId(id);
            movie.setName(name);
            movie.setYear(year);
            movie.setTagline(tagline);
            movie.setBudget(budget);
            DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);
            movie.setPremiere(dateFormat.parse(premiere));
            movie.setLasting(lasting);
            movie.setAnnotation(annotation);
            movie.setImage(image);

            movieDAO.updateMovie(movie, languageId);
        } catch (DAOException | ParseException e) {
            throw new ServiceException("Service layer: cannot edit a movie", e);
        }
    }

    @Override
    public void deleteMovie(int id) throws ServiceException {
        if(id <= 0){
            throw new ServiceException("Wrong id for deleting movie");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            MovieDAO movieDAO = daoFactory.getMovieDAO();
            movieDAO.deleteMovie(id);
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot delete movie", e);
        }
    }
}
