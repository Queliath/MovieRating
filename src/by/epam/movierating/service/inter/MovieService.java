package by.epam.movierating.service.inter;

import by.epam.movierating.domain.Movie;
import by.epam.movierating.service.exception.ServiceException;

import java.util.List;

/**
 * Provides a business-logic with the Movie entity.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public interface MovieService {
    /**
     * Returns a recent added movies to the data storage.
     *
     * @param amount a needed amount of a movies
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a recent added movies
     * @throws ServiceException
     */
    List<Movie> getRecentAddedMovies(int amount, String languageId) throws ServiceException;

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
    List<Movie> getMoviesByCriteria(String name, int minYear, int maxYear,
                                    List<Integer> genreIds, List<Integer> countyIds, int minRating, int maxRating,
                                    int from, int amount, String languageId) throws ServiceException;

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
    int getMoviesCountByCriteria(String name, int minYear, int maxYear, List<Integer> genreIds,
                                 List<Integer> countyIds, int minRating, int maxRating, String languageId) throws ServiceException;

    /**
     * Returns a certain movie by id.
     *
     * @param id an id of a needed movie
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a certain movie
     * @throws ServiceException
     */
    Movie getMovieById(int id, String languageId) throws ServiceException;

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
    void addMovie(String name, int year, String tagline, int budget, String premiere, int lasting,
                  String annotation, String image) throws ServiceException;

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
    void editMovie(int id, String name, int year, String tagline, int budget, String premiere, int lasting,
                   String annotation, String image, String languageId) throws ServiceException;

    /**
     * Deletes an existing movie from the data storage (with all of the localizations).
     *
     * @param id an id of the deleting movie
     * @throws ServiceException
     */
    void deleteMovie(int id) throws ServiceException;
}
