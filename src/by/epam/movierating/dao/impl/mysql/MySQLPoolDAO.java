package by.epam.movierating.dao.impl.mysql;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.interfaces.PoolDAO;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPool;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPoolException;

/**
 * Created by Владислав on 02.08.2016.
 */
public class MySQLPoolDAO implements PoolDAO {
    @Override
    public void init() throws DAOException {
        try {
            MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
            mySQLConnectionPool.init();
        } catch (MySQLConnectionPoolException e) {
            throw new DAOException("Cannot init a mySQL connection pool", e);
        }
    }

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
