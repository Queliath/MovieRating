package by.epam.movierating.dao.inter;

import by.epam.movierating.dao.exception.DAOException;

/**
 * Created by Владислав on 02.08.2016.
 */
public interface PoolDAO {
    void init() throws DAOException;
    void destroy() throws DAOException;
}
