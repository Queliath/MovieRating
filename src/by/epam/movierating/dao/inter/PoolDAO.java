package by.epam.movierating.dao.inter;

import by.epam.movierating.dao.exception.DAOException;

/**
 * Provides a logic for the Connection Pool in DAO layer.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public interface PoolDAO {
    /**
     * Initialize the Connection Pool.
     *
     * @throws DAOException
     */
    void init() throws DAOException;

    /**
     * Destroys the Connection Pool.
     *
     * @throws DAOException
     */
    void destroy() throws DAOException;
}
