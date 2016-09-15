package by.epam.movierating.dao.inter;

import by.epam.movierating.dao.exception.DAOException;

/**
 * Provides a DAO-logic for the relations between Movie and Person entities.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public interface MoviePersonRelationDAO {
    void addMovieToPersonWithRelation(int movieId, int personId, int relationType) throws DAOException;
    void deleteMovieFromPersonWithRelation(int movieId, int personId, int relationType) throws DAOException;
    int getMoviesTotalByPerson(int personId) throws DAOException;
}
