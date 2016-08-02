package by.epam.movierating.controller.listener; /**
 * Created by Владислав on 19.07.2016.
 */

import by.epam.movierating.dao.pool.mysql.MySQLConnectionPool;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPoolException;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.interfaces.PoolService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;

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
            sce.getServletContext().setAttribute("poolException", true);
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            PoolService poolService = serviceFactory.getPoolService();
            poolService.destroy();
        } catch (ServiceException e) {
            sce.getServletContext().setAttribute("poolException", true);
        }
    }
}
