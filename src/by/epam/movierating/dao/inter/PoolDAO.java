package by.epam.movierating.dao.inter;

import by.epam.movierating.dao.exception.DAOException;

/**
 * Provides a logic for the Connection Pool in DAO layer.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public interface PoolDAO {
    void init() throws DAOException;
    void destroy() throws DAOException;
}
