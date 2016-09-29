package by.epam.movierating.service.impl;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.dao.inter.*;
import by.epam.movierating.domain.Country;
import by.epam.movierating.domain.Genre;
import by.epam.movierating.domain.Movie;
import by.epam.movierating.domain.Person;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.inter.PersonService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Provides a business-logic with the Person entity.
 *
 * @author Kostevich Vladislav
 * @version 1.0
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

    /**
     * Returns a certain person by id.
     *
     * @param id an id of a needed person
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a certain person
     * @throws ServiceException
     */
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

    /**
     * Returns a persons matching the criteria.
     *
     * @param name a name of the person criteria
     * @param from a starting position in the persons list (starting from 0)
     * @param amount a needed amount of a persons
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a persons matching the criteria
     * @throws ServiceException
     */
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

    /**
     * Returns an amount of the persons matching the criteria.
     *
     * @param name a name of the person criteria
     * @param languageId a language id like 'EN', "RU' etc.
     * @return an amount of the persons matching the criteria
     * @throws ServiceException
     */
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

    /**
     * Adds a new person to the data storage (in the default language).
     *
     * @param name a name of the person
     * @param dateOfBirth a date of birth of the person
     * @param placeOfBirth a place of a birth of the person
     * @param photo a URL to the photo of the person
     * @throws ServiceException
     */
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

    /**
     * Edits an already existing person or adds/edits a localization of a person.
     *
     * If the languageId argument is an id of the default language of the application, then it updates
     * a person. If the language argument is an id of the different language (not default) then it
     * adds/updates a localization of a person (it based on the fact of existence of a localization:
     * if it doesn't exist then it will be added, otherwise a localization will be updated).
     * @param id an id of a needed person
     * @param name a new name of the person
     * @param dateOfBirth a new date of a birth of the person
     * @param placeOfBirth a new place of a birth of the person
     * @param photo a URL to the new photo of the person
     * @param languageId a language id like 'EN', "RU' etc.
     * @throws ServiceException
     */
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

    /**
     * Deletes an existing person from the data storage (with all of the localizations).
     *
     * @param id an id of the deleting person
     * @throws ServiceException
     */
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
