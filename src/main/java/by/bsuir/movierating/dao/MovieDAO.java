package by.bsuir.movierating.dao;

import by.bsuir.movierating.domain.criteria.MovieCriteria;
import by.bsuir.movierating.domain.Movie;

import java.util.List;

/**
 * Provides a DAO-logic for the Movie entity.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public interface MovieDAO {
    /**
     * Adds a movie to the data storage (in the default language).
     *
     * @param movie a movie object
     */
    void addMovie(Movie movie);

    /**
     * Updates a movie or adds/updates a localization of a movie in the data storage.
     *
     * If the languageId argument is an id of the default language of the application, then it updates
     * a movie. If the language argument is an id of the different language (not default) then it
     * adds/updates a localization of a movie (it based on the fact of existence of a localization:
     * if it doesn't exist then it will be added, otherwise a localization will be updated).
     * @param movie a movie object
     * @param languageId a language id like 'EN', "RU' etc.
     */
    void updateMovie(Movie movie, String languageId);

    /**
     * Deletes a movie from the data storage (with all of the localizations).
     *
     * @param id an id of the deleting movie
     */
    void deleteMovie(int id);

    /**
     * Returns all the movies from the data storage.
     *
     * @param languageId a language id like 'EN', "RU' etc.
     * @return all the movies
     */
    List<Movie> getAllMovies(String languageId);

    /**
     * Returns a movie by id from the data storage.
     *
     * @param id an id of the needed movie
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a movie by id
     */
    Movie getMovieById(int id, String languageId);

    /**
     * Returns a movies belonging to the genre from the data storage.
     *
     * @param genreId an id of the genre
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a movies belonging to the genre
     */
    List<Movie> getMoviesByGenre(int genreId, String languageId);

    /**
     * Returns a movies belonging to the country from the data storage.
     *
     * @param countryId an id of the country
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a movies belonging to the country
     */
    List<Movie> getMoviesByCountry(int countryId, String languageId);

    /**
     * Returns a movies in which the person took part in the certain role from the data storage.
     *
     * @param personId an id of the person
     * @param relationType an id of the relation type (role)
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a movies in which the person took part in the certain role
     */
    List<Movie> getMoviesByPersonAndRelationType(int personId, int relationType, String languageId);

    /**
     * Returns a recent added movies from the data storage.
     *
     * @param amount a needed amount of movies
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a recent added movies
     */
    List<Movie> getRecentAddedMovies(int amount, String languageId);

    /**
     * Returns a movies matching to the criteria from the data storage.
     *
     * @param criteria a MovieCriteria object
     * @param from a starting position in the movies list (starting from 0)
     * @param amount a needed amount of movies
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a movies matching to the criteria
     */
    List<Movie> getMoviesByCriteria(MovieCriteria criteria, int from, int amount, String languageId);

    /**
     * Returns an amount of movies matching to the criteria in the data storage.
     *
     * @param criteria a MovieCriteria object
     * @param languageId a language id like 'EN', "RU' etc.
     * @return an amount of movies matching to the criteria
     */
    int getMoviesCountByCriteria(MovieCriteria criteria, String languageId);
}
