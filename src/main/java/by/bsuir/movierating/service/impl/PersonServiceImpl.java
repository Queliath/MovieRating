package by.bsuir.movierating.service.impl;

import by.bsuir.movierating.dao.*;
import by.bsuir.movierating.domain.Country;
import by.bsuir.movierating.domain.Genre;
import by.bsuir.movierating.domain.Movie;
import by.bsuir.movierating.domain.Person;
import by.bsuir.movierating.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service("personService")
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

    private PersonDAO personDAO;
    private MovieDAO movieDAO;
    private MoviePersonRelationDAO moviePersonRelationDAO;
    private GenreDAO genreDAO;
    private CountryDAO countryDAO;
    private RatingDAO ratingDAO;

    @Autowired
    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Autowired
    public void setMovieDAO(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    @Autowired
    public void setMoviePersonRelationDAO(MoviePersonRelationDAO moviePersonRelationDAO) {
        this.moviePersonRelationDAO = moviePersonRelationDAO;
    }

    @Autowired
    public void setGenreDAO(GenreDAO genreDAO) {
        this.genreDAO = genreDAO;
    }

    @Autowired
    public void setCountryDAO(CountryDAO countryDAO) {
        this.countryDAO = countryDAO;
    }

    @Autowired
    public void setRatingDAO(RatingDAO ratingDAO) {
        this.ratingDAO = ratingDAO;
    }

    /**
     * Returns a certain person by id.
     *
     * @param id an id of a needed person
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a certain person
     */
    @Override
    public Person getPersonById(int id, String languageId) {
        Person person = personDAO.getPersonById(id, languageId);

        if(person != null){
            int moviesTotal = moviePersonRelationDAO.getMoviesTotalByPerson(id);
            person.setMoviesTotal(moviesTotal);

            List<Movie> moviesAsActor = movieDAO.getMoviesByPersonAndRelationType(person.getId(), ACTOR, languageId);
            List<Movie> moviesAsDirector = movieDAO.getMoviesByPersonAndRelationType(person.getId(), DIRECTOR, languageId);
            List<Movie> moviesAsProducer = movieDAO.getMoviesByPersonAndRelationType(person.getId(), PRODUCER, languageId);
            List<Movie> moviesAsWriter = movieDAO.getMoviesByPersonAndRelationType(person.getId(), WRITER, languageId);
            List<Movie> moviesAsPainter = movieDAO.getMoviesByPersonAndRelationType(person.getId(), PAINTER, languageId);
            List<Movie> moviesAsOperator = movieDAO.getMoviesByPersonAndRelationType(person.getId(), OPERATOR, languageId);
            List<Movie> moviesAsEditor = movieDAO.getMoviesByPersonAndRelationType(person.getId(), EDITOR, languageId);
            List<Movie> moviesAsComposer = movieDAO.getMoviesByPersonAndRelationType(person.getId(), COMPOSER, languageId);

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
    }

    /**
     * Returns a persons matching the criteria.
     *
     * @param name a name of the person criteria
     * @param from a starting position in the persons list (starting from 0)
     * @param amount a needed amount of a persons
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a persons matching the criteria
     */
    @Override
    public List<Person> getPersonsByCriteria(String name, int from, int amount, String languageId) {
        List<Person> persons = personDAO.getPersonsByCriteria(name, from, amount, languageId);

        for(Person person : persons){
            int moviesTotal = moviePersonRelationDAO.getMoviesTotalByPerson(person.getId());
            person.setMoviesTotal(moviesTotal);
        }

        return persons;
    }

    /**
     * Returns an amount of the persons matching the criteria.
     *
     * @param name a name of the person criteria
     * @param languageId a language id like 'EN', "RU' etc.
     * @return an amount of the persons matching the criteria
     */
    @Override
    public int getPersonsCountByCriteria(String name, String languageId) {
        return personDAO.getPersonsCountByCriteria(name, languageId);
    }

    /**
     * Adds a new person to the data storage (in the default language).
     *
     * @param name a name of the person
     * @param dateOfBirth a date of birth of the person
     * @param placeOfBirth a place of a birth of the person
     * @param photo a URL to the photo of the person
     */
    @Override
    public void addPerson(String name, String dateOfBirth, String placeOfBirth, String photo) throws ParseException {
        Person person = new Person();
        person.setName(name);
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        person.setDateOfBirth(dateFormat.parse(dateOfBirth));
        person.setPlaceOfBirth(placeOfBirth);
        person.setPhoto(photo);

        personDAO.addPerson(person);
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
     */
    @Override
    public void editPerson(int id, String name, String dateOfBirth, String placeOfBirth, String photo, String languageId) throws ParseException {
        Person person = new Person();
        person.setId(id);
        person.setName(name);
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        person.setDateOfBirth(dateFormat.parse(dateOfBirth));
        person.setPlaceOfBirth(placeOfBirth);
        person.setPhoto(photo);

        personDAO.updatePerson(person, languageId);
    }

    /**
     * Deletes an existing person from the data storage (with all of the localizations).
     *
     * @param id an id of the deleting person
     */
    @Override
    public void deletePerson(int id) {
        personDAO.deletePerson(id);
    }
}
