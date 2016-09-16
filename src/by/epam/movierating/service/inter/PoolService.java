package by.epam.movierating.service.inter;

import by.epam.movierating.service.exception.ServiceException;

/**
 * Provides a logic for the Connection Pool in the DAO layer.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public interface PoolService {
    void init() throws ServiceException;
    void destroy() throws ServiceException;
}
