package by.epam.movierating.dao.inter;

import by.epam.movierating.dao.exception.DAOException;

/**
 * Created by Владислав on 19.06.2016.
 */
public interface MoviePersonRelationDAO {
    void addMovieToPersonWithRelation(int movieId, int personId, int relationType) throws DAOException;
    void deleteMovieFromPersonWithRelation(int movieId, int personId, int relationType) throws DAOException;
    int getMoviesTotalByPerson(int personId) throws DAOException;
}
