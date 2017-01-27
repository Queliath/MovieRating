package by.bsuir.movierating.service.impl;

import by.bsuir.movierating.dao.*;
import by.bsuir.movierating.domain.*;
import by.bsuir.movierating.domain.criteria.MovieCriteria;
import by.bsuir.movierating.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service("movieService")
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

    private MovieDAO movieDAO;
    private CountryDAO countryDAO;
    private GenreDAO genreDAO;
    private PersonDAO personDAO;
    private CommentDAO commentDAO;
    private RatingDAO ratingDAO;
    private UserDAO userDAO;

    @Autowired
    public void setMovieDAO(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    @Autowired
    public void setCountryDAO(CountryDAO countryDAO) {
        this.countryDAO = countryDAO;
    }

    @Autowired
    public void setGenreDAO(GenreDAO genreDAO) {
        this.genreDAO = genreDAO;
    }

    @Autowired
    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Autowired
    public void setCommentDAO(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }

    @Autowired
    public void setRatingDAO(RatingDAO ratingDAO) {
        this.ratingDAO = ratingDAO;
    }

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Returns a recent added movies to the data storage.
     *
     * @param amount a needed amount of a movies
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a recent added movies
     */
    @Override
    public List<Movie> getRecentAddedMovies(int amount, String languageId) {
        List<Movie> movies = movieDAO.getRecentAddedMovies(amount, languageId);

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
     */
    @Override
    public List<Movie> getMoviesByCriteria(String name, int minYear, int maxYear, List<Integer> genreIds, List<Integer> countyIds, int minRating, int maxRating, int from, int amount, String languageId) {
        MovieCriteria criteria = new MovieCriteria();
        criteria.setName(name);
        criteria.setMinYear(minYear);
        criteria.setMaxYear(maxYear);
        criteria.setGenreIds(genreIds);
        criteria.setCountryIds(countyIds);
        criteria.setMinRating(minRating);
        criteria.setMaxRating(maxRating);

        List<Movie> movies = movieDAO.getMoviesByCriteria(criteria, from, amount, languageId);

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
     */
    @Override
    public int getMoviesCountByCriteria(String name, int minYear, int maxYear, List<Integer> genreIds, List<Integer> countyIds, int minRating, int maxRating, String languageId) {
        MovieCriteria criteria = new MovieCriteria();
        criteria.setName(name);
        criteria.setMinYear(minYear);
        criteria.setMaxYear(maxYear);
        criteria.setGenreIds(genreIds);
        criteria.setCountryIds(countyIds);
        criteria.setMinRating(minRating);
        criteria.setMaxRating(maxRating);

        return movieDAO.getMoviesCountByCriteria(criteria, languageId);
    }

    /**
     * Returns a certain movie by id.
     *
     * @param id an id of a needed movie
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a certain movie
     */
    @Override
    public Movie getMovieById(int id, String languageId) {
        Movie movie = movieDAO.getMovieById(id, languageId);

        if(movie != null){
            List<Country> countries = countryDAO.getCountriesByMovie(movie.getId(), languageId);
            movie.setCountries(countries);

            List<Genre> genres = genreDAO.getGenresByMovie(movie.getId(), languageId);
            movie.setGenres(genres);

            double averageRating = ratingDAO.getAverageRatingByMovie(movie.getId());
            movie.setAverageRating(averageRating);

            List<Comment> comments = commentDAO.getCommentsByMovie(movie.getId(), languageId);
            for(Comment comment : comments){
                User user = userDAO.getUserById(comment.getUserId());
                comment.setUser(user);
            }
            movie.setComments(comments.isEmpty() ? null : comments);

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
     */
    @Override
    public void addMovie(String name, int year, String tagline, int budget, String premiere, int lasting, String annotation, String image) throws ParseException {
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
     */
    @Override
    public void editMovie(int id, String name, int year, String tagline, int budget, String premiere, int lasting, String annotation, String image, String languageId) throws ParseException {
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
    }

    /**
     * Deletes an existing movie from the data storage (with all of the localizations).
     *
     * @param id an id of the deleting movie
     */
    @Override
    public void deleteMovie(int id) {
        movieDAO.deleteMovie(id);
    }
}
