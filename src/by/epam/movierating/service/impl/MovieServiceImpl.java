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
 * Provides a business-logic with the Movie entity.
 *
 * @author Kostevich Vladislav
 * @version 1.0
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

    /**
     * Returns a recent added movies to the data storage.
     *
     * @param amount a needed amount of a movies
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a recent added movies
     * @throws ServiceException
     */
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

    /**
     * Returns a movies matching the criteria.
     *
     * @param name a name of the movie criteria
     * @param minYear a min year of the movie criteria
     * @param maxYear a max year of the movie criteria
     * @param genreIds a list of possible genres of the movie criteria
     * @param countyIds a list of a possible countries of the movie criteria
     * @param minRating a min average rating of the movie criteria
     * @param maxRating a max average rating of the movie criteria
     * @param from a starting position in the movies list (starting from 0)
     * @param amount a needed amount of a movies
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a movies matching the criteria
     * @throws ServiceException
     */
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

    /**
     * Returns an amount of the movies matching the criteria.
     *
     * @param name a name of the movie criteria
     * @param minYear a min year of the movie criteria
     * @param maxYear a max year of the movie criteria
     * @param genreIds a list of possible genres of the movie criteria
     * @param countyIds a list of a possible countries of the movie criteria
     * @param minRating a min average rating of the movie criteria
     * @param maxRating a max average rating of the movie criteria
     * @param languageId a language id like 'EN', "RU' etc.
     * @return an amount of the movies matching the criteria
     * @throws ServiceException
     */
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

    /**
     * Returns a certain movie by id.
     *
     * @param id an id of a needed movie
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a certain movie
     * @throws ServiceException
     */
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

    /**
     * Adds a new movie to the data storage (in the default language).
     *
     * @param name a name of the movie
     * @param year a year of the movie
     * @param tagline a tagline of the movie
     * @param budget a budget of a movie
     * @param premiere a premiere date of the movie
     * @param lasting a lasting of the movie (minute)
     * @param annotation an annotation of the movie
     * @param image an URL to the image of the movie
     * @throws ServiceException
     */
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

    /**
     * Edits an already existing movie or adds/edits a localization of a movie.
     *
     * If the languageId argument is an id of the default language of the application, then it updates
     * a movie. If the language argument is an id of the different language (not default) then it
     * adds/updates a localization of a movie (it based on the fact of existence of a localization:
     * if it doesn't exist then it will be added, otherwise a localization will be updated).
     * @param id an id of the needed movie
     * @param name a new name of the movie
     * @param year a new year of the movie
     * @param tagline a new tagline of the movie
     * @param budget a new budget of the movie
     * @param premiere a new premiere date of the movie
     * @param lasting a new lasting of the movie
     * @param annotation a new annotation of the movie
     * @param image a URL to the new image of the movie
     * @param languageId a language id like 'EN', "RU' etc.
     * @throws ServiceException
     */
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

    /**
     * Deletes an existing movie from the data storage (with all of the localizations).
     *
     * @param id an id of the deleting movie
     * @throws ServiceException
     */
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
