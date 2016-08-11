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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";

    private static final int NAME_MAX_LENGTH = 70;
    private static final int PLACE_OF_BIRTH_MAX_LENGTH = 45;
    private static final int PHOTO_MAX_LENGTH = 150;

    @Override
    public Person getPersonById(int id, String languageId) throws ServiceException {
        if(id <= 0){
            throw new ServiceException("Wrong id for getting person");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            PersonDAO personDAO = daoFactory.getPersonDAO();
            Person person = personDAO.getPersonById(id, languageId);

            if(person != null){
                MoviePersonRelationDAO moviePersonRelationDAO = daoFactory.getMoviePersonRelationDAO();
                int moviesTotal = moviePersonRelationDAO.getMoviesTotalByPerson(id);
                person.setMoviesTotal(moviesTotal);

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

    @Override
    public List<Person> getPersonsByCriteria(String name, int from, int amount, String languageId) throws ServiceException {
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            PersonDAO personDAO = daoFactory.getPersonDAO();
            List<Person> persons = personDAO.getPersonsByCriteria(name, from, amount, languageId);

            MoviePersonRelationDAO moviePersonRelationDAO = daoFactory.getMoviePersonRelationDAO();
            for(Person person : persons){
                int moviesTotal = moviePersonRelationDAO.getMoviesTotalByPerson(person.getId());
                person.setMoviesTotal(moviesTotal);
            }

            return persons;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get persons by a criteria", e);
        }
    }

    @Override
    public int getPersonsCountByCriteria(String name, String languageId) throws ServiceException {
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            PersonDAO personDAO = daoFactory.getPersonDAO();
            int personsCount = personDAO.getPersonsCountByCriteria(name, languageId);
            return personsCount;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get persons count by a criteria", e);
        }
    }

    @Override
    public void addPerson(String name, String dateOfBirth, String placeOfBirth, String photo) throws ServiceException {
        if(name.isEmpty() || name.length() > NAME_MAX_LENGTH || dateOfBirth.isEmpty() || placeOfBirth.isEmpty() ||
                placeOfBirth.length() > PLACE_OF_BIRTH_MAX_LENGTH || photo.isEmpty() || photo.length() > PHOTO_MAX_LENGTH){
            throw new ServiceException("Wrong parameters for adding person");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            PersonDAO personDAO = daoFactory.getPersonDAO();

            Person person = new Person();
            person.setName(name);
            DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);
            person.setDateOfBirth(dateFormat.parse(dateOfBirth));
            person.setPlaceOfBirth(placeOfBirth);
            person.setPhoto(photo);

            personDAO.addPerson(person);
        } catch (DAOException | ParseException e) {
            throw new ServiceException("Service layer: cannot add a person", e);
        }
    }

    @Override
    public void editPerson(int id, String name, String dateOfBirth, String placeOfBirth, String photo, String languageId) throws ServiceException {
        if(id <= 0 || name.isEmpty() || name.length() > NAME_MAX_LENGTH || dateOfBirth.isEmpty() || placeOfBirth.isEmpty() ||
                placeOfBirth.length() > PLACE_OF_BIRTH_MAX_LENGTH || photo.isEmpty() || photo.length() > PHOTO_MAX_LENGTH){
            throw new ServiceException("Wrong parameters for editing person");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            PersonDAO personDAO = daoFactory.getPersonDAO();

            Person person = new Person();
            person.setId(id);
            person.setName(name);
            DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);
            person.setDateOfBirth(dateFormat.parse(dateOfBirth));
            person.setPlaceOfBirth(placeOfBirth);
            person.setPhoto(photo);

            personDAO.updatePerson(person, languageId);
        } catch (DAOException | ParseException e) {
            throw new ServiceException("Service layer: cannot edit a person", e);
        }
    }

    @Override
    public void deletePerson(int id) throws ServiceException {
        if(id <= 0){
            throw new ServiceException("Wrong id for deleting person");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            PersonDAO personDAO = daoFactory.getPersonDAO();
            personDAO.deletePerson(id);
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot delete person", e);
        }
    }
}
