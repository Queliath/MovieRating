package by.bsuir.movierating.dao.impl.mysql;

import by.bsuir.movierating.dao.exception.DAOException;
import by.bsuir.movierating.dao.inter.PoolDAO;
import by.bsuir.movierating.dao.pool.mysql.MySQLConnectionPool;
import by.bsuir.movierating.dao.pool.mysql.MySQLConnectionPoolException;

/**
 * Provides a logic for the MySQL Connection Pool.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class MySQLPoolDAO implements PoolDAO {
    /**
     * Initialize the Connection Pool.
     *
     * @throws DAOException
     */
    @Override
    public void init() throws DAOException {
        try {
            MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
            mySQLConnectionPool.init();
        } catch (MySQLConnectionPoolException e) {
            throw new DAOException("Cannot init a mySQL connection pool", e);
        }
    }

    /**
     * Destroys the Connection Pool.
     *
     * @throws DAOException
     */
    @Override
    public void destroy() throws DAOException {
        try {
            MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
            mySQLConnectionPool.destroy();
        } catch (MySQLConnectionPoolException e) {
            throw new DAOException("Cannot destroy a mySQL connection pool", e);
        }
    }
}
