package by.bsuir.movierating.controller.listener;

import by.bsuir.movierating.service.exception.ServiceException;
import by.bsuir.movierating.service.factory.ServiceFactory;
import by.bsuir.movierating.service.PoolService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Initialize and destroys the Connection Pool at the same time as ServletContext.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class ConnectionPoolListener implements ServletContextListener {
    // Public constructor is required by servlet spec
    public ConnectionPoolListener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            PoolService poolService = serviceFactory.getPoolService();
            poolService.init();
        } catch (ServiceException e) {
            throw new ConnectionPoolListenerException("Cannot init a pool", e);
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            PoolService poolService = serviceFactory.getPoolService();
            poolService.destroy();
        } catch (ServiceException e) {
            throw new ConnectionPoolListenerException("Cannot destroy pool", e);
        }
    }
}
