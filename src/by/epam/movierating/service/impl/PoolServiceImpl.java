package by.epam.movierating.service.impl;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.dao.inter.PoolDAO;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.inter.PoolService;

/**
 * Provides a logic for the Connection Pool in the DAO layer.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class PoolServiceImpl implements PoolService {
    /**
     * Gives a command to initialize the Connection Pool in the DAO layer.
     *
     * @throws ServiceException
     */
    @Override
    public void init() throws ServiceException {
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            PoolDAO poolDAO = daoFactory.getPoolDAO();
            poolDAO.init();
        } catch (DAOException e) {
            throw new ServiceException("Cannot init a pool", e);
        }
    }

    /**
     * Gives a command to destroy the Connection Pool in the DAO layer.
     *
     * @throws ServiceException
     */
    @Override
    public void destroy() throws ServiceException {
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            PoolDAO poolDAO = daoFactory.getPoolDAO();
            poolDAO.destroy();
        } catch (DAOException e) {
            throw new ServiceException("Cannot destroy a pool", e);
        }
    }
}
