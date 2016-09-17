package by.epam.movierating.dao.pool.mysql;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Владислав on 15.09.2016.
 */
public class MySQLConnectionPoolTest {
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
