package by.epam.movierating.service.impl;

import by.epam.movierating.dao.pool.mysql.MySQLConnectionPool;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPoolException;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.interfaces.PoolService;

/**
 * Created by Владислав on 19.07.2016.
 */
public class PoolServiceImpl implements PoolService {
    @Override
    public void init() throws ServiceException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        try {
            mySQLConnectionPool.init();
        } catch (MySQLConnectionPoolException e) {
            throw new ServiceException("Cannot init a pool", e);
        }
    }

    @Override
    public void destroy() throws ServiceException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        try {
            mySQLConnectionPool.destroy();
        } catch (MySQLConnectionPoolException e) {
            throw new ServiceException("Cannot destroy a pool", e);
        }
    }
}
