package by.epam.movierating.service.impl;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.dao.interfaces.*;
import by.epam.movierating.domain.Country;
import by.epam.movierating.domain.Genre;
import by.epam.movierating.domain.Movie;
import by.epam.movierating.domain.Person;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.interfaces.PersonService;

import java.util.List;

/**
 * Created by Владислав on 25.07.2016.
 */
public class PersonServiceImpl implements PersonService {
    private static final int ACTOR = 1;
    private static final int DIRECTOR = 2;
    private static final int PRODUCER = 3;
    private static final int WRITER = 4;
    private static final int OPERATOR = 5;
    private static final int PAINTER = 6;
    private static final int EDITOR = 7;
    private static final int COMPOSER = 8;

    @Override
    public Person getPersonById(int id, String languageId) throws ServiceException {
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            PersonDAO personDAO = daoFactory.getPersonDAO();
            Person person = personDAO.getPersonById(id, languageId);

            if(person != null){
                MovieDAO movieDAO = daoFactory.getMovieDAO();
                List<Movie> moviesAsActor = movieDAO.getMoviesByPersonAndRelationType(person.getId(), ACTOR, languageId);
                List<Movie> moviesAsDirector = movieDAO.getMoviesByPersonAndRelationType(person.getId(), DIRECTOR, languageId);
                List<Movie> moviesAsProducer = movieDAO.getMoviesByPersonAndRelationType(person.getId(), PRODUCER, languageId);
                List<Movie> moviesAsWriter = movieDAO.getMoviesByPersonAndRelationType(person.getId(), WRITER, languageId);
                List<Movie> moviesAsPainter = movieDAO.getMoviesByPersonAndRelationType(person.getId(), PAINTER, languageId);
                List<Movie> moviesAsOperator = movieDAO.getMoviesByPersonAndRelationType(person.getId(), OPERATOR, languageId);
                List<Movie> moviesAsEditor = movieDAO.getMoviesByPersonAndRelationType(person.getId(), EDITOR, languageId);
                List<Movie> moviesAsComposer = movieDAO.getMoviesByPersonAndRelationType(person.getId(), COMPOSER, languageId);

                CountryDAO countryDAO = daoFactory.getCountryDAO();
                GenreDAO genreDAO = daoFactory.getGenreDAO();
                RatingDAO ratingDAO = daoFactory.getRatingDAO();
                for(Movie movie : moviesAsActor){
                    List<Country> countries = countryDAO.getCountriesByMovie(movie.getId(), languageId);
                    movie.setCountries(countries);
                    List<Genre> genres = genreDAO.getGenresByMovie(movie.getId(), languageId);
                    movie.setGenres(genres);
                    List<Person> directors = personDAO.getPersonsByMovieAndRelationType(movie.getId(), DIRECTOR, languageId);
                    movie.setDirectors(directors);
                    double averageRating = ratingDAO.getAverageRatingByMovie(movie.getId());
                    movie.setAverageRating(averageRating);
                }
                for(Movie movie : moviesAsDirector){
                    List<Country> countries = countryDAO.getCountriesByMovie(movie.getId(), languageId);
                    movie.setCountries(countries);
                    List<Genre> genres = genreDAO.getGenresByMovie(movie.getId(), languageId);
                    movie.setGenres(genres);
                    List<Person> directors = personDAO.getPersonsByMovieAndRelationType(movie.getId(), DIRECTOR, languageId);
                    movie.setDirectors(directors);
                    double averageRating = ratingDAO.getAverageRatingByMovie(movie.getId());
                    movie.setAverageRating(averageRating);
                }
                for(Movie movie : moviesAsProducer){
                    List<Country> countries = countryDAO.getCountriesByMovie(movie.getId(), languageId);
                    movie.setCountries(countries);
                    List<Genre> genres = genreDAO.getGenresByMovie(movie.getId(), languageId);
                    movie.setGenres(genres);
                    List<Person> directors = personDAO.getPersonsByMovieAndRelationType(movie.getId(), DIRECTOR, languageId);
                    movie.setDirectors(directors);
                    double averageRating = ratingDAO.getAverageRatingByMovie(movie.getId());
                    movie.setAverageRating(averageRating);
                }
                for(Movie movie : moviesAsWriter){
                    List<Country> countries = countryDAO.getCountriesByMovie(movie.getId(), languageId);
                    movie.setCountries(countries);
                    List<Genre> genres = genreDAO.getGenresByMovie(movie.getId(), languageId);
                    movie.setGenres(genres);
                    List<Person> directors = personDAO.getPersonsByMovieAndRelationType(movie.getId(), DIRECTOR, languageId);
                    movie.setDirectors(directors);
                    double averageRating = ratingDAO.getAverageRatingByMovie(movie.getId());
                    movie.setAverageRating(averageRating);
                }
                for(Movie movie : moviesAsPainter){
                    List<Country> countries = countryDAO.getCountriesByMovie(movie.getId(), languageId);
                    movie.setCountries(countries);
                    List<Genre> genres = genreDAO.getGenresByMovie(movie.getId(), languageId);
                    movie.setGenres(genres);
                    List<Person> directors = personDAO.getPersonsByMovieAndRelationType(movie.getId(), DIRECTOR, languageId);
                    movie.setDirectors(directors);
                    double averageRating = ratingDAO.getAverageRatingByMovie(movie.getId());
                    movie.setAverageRating(averageRating);
                }
                for(Movie movie : moviesAsOperator){
                    List<Country> countries = countryDAO.getCountriesByMovie(movie.getId(), languageId);
                    movie.setCountries(countries);
                    List<Genre> genres = genreDAO.getGenresByMovie(movie.getId(), languageId);
                    movie.setGenres(genres);
                    List<Person> directors = personDAO.getPersonsByMovieAndRelationType(movie.getId(), DIRECTOR, languageId);
                    movie.setDirectors(directors);
                    double averageRating = ratingDAO.getAverageRatingByMovie(movie.getId());
                    movie.setAverageRating(averageRating);
                }
                for(Movie movie : moviesAsEditor){
                    List<Country> countries = countryDAO.getCountriesByMovie(movie.getId(), languageId);
                    movie.setCountries(countries);
                    List<Genre> genres = genreDAO.getGenresByMovie(movie.getId(), languageId);
                    movie.setGenres(genres);
                    List<Person> directors = personDAO.getPersonsByMovieAndRelationType(movie.getId(), DIRECTOR, languageId);
                    movie.setDirectors(directors);
                    double averageRating = ratingDAO.getAverageRatingByMovie(movie.getId());
                    movie.setAverageRating(averageRating);
                }
                for(Movie movie : moviesAsComposer){
                    List<Country> countries = countryDAO.getCountriesByMovie(movie.getId(), languageId);
                    movie.setCountries(countries);
                    List<Genre> genres = genreDAO.getGenresByMovie(movie.getId(), languageId);
                    movie.setGenres(genres);
                    List<Person> directors = personDAO.getPersonsByMovieAndRelationType(movie.getId(), DIRECTOR, languageId);
                    movie.setDirectors(directors);
                    double averageRating = ratingDAO.getAverageRatingByMovie(movie.getId());
                    movie.setAverageRating(averageRating);
                }

                person.setMoviesAsActor(moviesAsActor.isEmpty() ? null : moviesAsActor);
                person.setMoviesAsDirector(moviesAsDirector.isEmpty() ? null : moviesAsDirector);
                person.setMoviesAsProducer(moviesAsProducer.isEmpty() ? null : moviesAsProducer);
                person.setMoviesAsWriter(moviesAsWriter.isEmpty() ? null : moviesAsWriter);
                person.setMoviesAsPainter(moviesAsPainter.isEmpty() ? null : moviesAsPainter);
                person.setMoviesAsOperator(moviesAsOperator.isEmpty() ? null : moviesAsOperator);
                person.setMoviesAsEditor(moviesAsEditor.isEmpty() ? null : moviesAsEditor);
                person.setMoviesAsComposer(moviesAsComposer.isEmpty() ? null : moviesAsComposer);
            }

            return person;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get a person", e);
        }
    }
}
