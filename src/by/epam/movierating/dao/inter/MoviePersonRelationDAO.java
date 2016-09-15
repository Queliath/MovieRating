package by.epam.movierating.dao.inter;

import by.epam.movierating.dao.exception.DAOException;

/**
 * Provides a DAO-logic for the relations between Movie and Person entities.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public interface MoviePersonRelationDAO {
    /**
     * Adds a relation between the movie and the person.
     *
     * @param movieId an id of the movie
     * @param personId an id of the person
     * @param relationType an id of the relation type (role)
     * @throws DAOException
     */
    void addMovieToPersonWithRelation(int movieId, int personId, int relationType) throws DAOException;

    /**
     * Deletes a relation between the movie and the person.
     *
     * @param movieId an id of the movie
     * @param personId an id of the person
     * @param relationType an id of the relation type (role)
     * @throws DAOException
     */
    void deleteMovieFromPersonWithRelation(int movieId, int personId, int relationType) throws DAOException;

    /**
     * Returns an amount of movies in which this person took part.
     *
     * @param personId an id of the person
     * @return an amount of movies in which this person took part
     * @throws DAOException
     */
    int getMoviesTotalByPerson(int personId) throws DAOException;
}
