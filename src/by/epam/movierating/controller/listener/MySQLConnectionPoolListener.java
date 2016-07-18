package by.epam.movierating.controller.listener; /**
 * Created by Владислав on 19.07.2016.
 */

import by.epam.movierating.dao.pool.mysql.MySQLConnectionPool;
import by.epam.movierating.dao.pool.mysql.MySQLConnectionPoolException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;

public class MySQLConnectionPoolListener implements ServletContextListener {

    // Public constructor is required by servlet spec
    public MySQLConnectionPoolListener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        try {
            mySQLConnectionPool.init();
        } catch (MySQLConnectionPoolException e) {
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        try {
            mySQLConnectionPool.destroy();
        } catch (MySQLConnectionPoolException e) {
            e.printStackTrace();
        }
    }
}
