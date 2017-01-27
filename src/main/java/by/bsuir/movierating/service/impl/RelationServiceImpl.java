package by.bsuir.movierating.service.impl;

import by.bsuir.movierating.dao.MovieCountryDAO;
import by.bsuir.movierating.dao.MovieGenreDAO;
import by.bsuir.movierating.dao.MoviePersonRelationDAO;
import by.bsuir.movierating.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Provides a business-logic for the relations between entities.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
@Service("relationService")
public class RelationServiceImpl implements RelationService {
    private MovieCountryDAO movieCountryDAO;
    private MovieGenreDAO movieGenreDAO;
    private MoviePersonRelationDAO moviePersonRelationDAO;

    @Autowired
    public void setMovieCountryDAO(MovieCountryDAO movieCountryDAO) {
        this.movieCountryDAO = movieCountryDAO;
    }

    @Autowired
    public void setMovieGenreDAO(MovieGenreDAO movieGenreDAO) {
        this.movieGenreDAO = movieGenreDAO;
    }

    @Autowired
    public void setMoviePersonRelationDAO(MoviePersonRelationDAO moviePersonRelationDAO) {
        this.moviePersonRelationDAO = moviePersonRelationDAO;
    }

    /**
     * Adds a relation between the movie and the person.
     *
     * @param movieId an id of the movie
     * @param personId an id of the person
     * @param relationType an id of the relation type (role)
     */
    @Override
    public void addPersonToMovie(int movieId, int personId, int relationType) {
        moviePersonRelationDAO.addMovieToPersonWithRelation(movieId, personId, relationType);
    }

    /**
     * Adds a relation between the movie and the country.
     *
     * @param movieId an id of the movie
     * @param countryId an id of the country
     */
    @Override
    public void addCountryToMovie(int movieId, int countryId) {
        movieCountryDAO.addMovieToCountry(movieId, countryId);
    }

    /**
     * Adds a relation between the movie and the genre.
     *
     * @param movieId an id of the movie
     * @param genreId an id of the genre
     */
    @Override
    public void addGenreToMovie(int movieId, int genreId) {
        movieGenreDAO.addMovieToGenre(movieId, genreId);
    }

    /**
     * Deletes a relation between the movie and the person.
     *
     * @param movieId an id of the movie
     * @param personId an id of the person
     * @param relationType an id of the relation type (role)
     */
    @Override
    public void deletePersonFromMovie(int movieId, int personId, int relationType) {
        moviePersonRelationDAO.deleteMovieFromPersonWithRelation(movieId, personId, relationType);
    }

    /**
     * Deletes a relation between the movie and the country.
     *
     * @param movieId an id of the movie
     * @param countryId an id of the country
     */
    @Override
    public void deleteCountryFromMovie(int movieId, int countryId) {
        movieCountryDAO.deleteMovieFromCountry(movieId, countryId);
    }

    /**
     * Deletes a relation between the movie and the genre.
     *
     * @param movieId an id of the movie
     * @param genreId an id of the genre
     */
    @Override
    public void deleteGenreFromMovie(int movieId, int genreId) {
        movieGenreDAO.deleteMovieFromGenre(movieId, genreId);
    }
}
