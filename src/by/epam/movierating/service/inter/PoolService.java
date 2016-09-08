package by.epam.movierating.service.inter;

import by.epam.movierating.service.exception.ServiceException;

/**
 * Created by Владислав on 19.07.2016.
 */
public interface PoolService {
    void init() throws ServiceException;
    void destroy() throws ServiceException;
}
