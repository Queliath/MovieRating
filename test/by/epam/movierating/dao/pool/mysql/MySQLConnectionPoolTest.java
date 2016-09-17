package by.epam.movierating.dao.pool.mysql;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Tests the methods of MySQLConnectionPool class.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class MySQLConnectionPoolTest {
    @Test(expected = MySQLConnectionPoolException.class)
    public void initAlreadyInitedPoolTest() throws MySQLConnectionPoolException{
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        mySQLConnectionPool.init();
        try {
            mySQLConnectionPool.init();
        } finally {
            mySQLConnectionPool.destroy();
        }
    }

    @Test(expected = MySQLConnectionPoolException.class)
    public void destroyNotInitedPoolTest() throws MySQLConnectionPoolException{
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        mySQLConnectionPool.destroy();
    }

    @Test(expected = MySQLConnectionPoolException.class)
    public void getConnectionFromUnavailablePoolTest() throws MySQLConnectionPoolException, InterruptedException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        mySQLConnectionPool.getConnection();
    }

    @Test(expected = MySQLConnectionPoolException.class)
    public void freeOutsideConnectionTest() throws MySQLConnectionPoolException, InterruptedException, ClassNotFoundException, SQLException {
        MySQLConnectionPool mySQLConnectionPool = MySQLConnectionPool.getInstance();
        Connection poolConnection = mySQLConnectionPool.getConnection();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection outsideConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/epam_movie_rating",
                    "root", "root");
            mySQLConnectionPool.freeConnection(outsideConnection);
        } finally {
            mySQLConnectionPool.freeConnection(poolConnection);
        }
    }
}
