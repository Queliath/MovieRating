package by.epam.movierating.dao.pool.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 12.06.2016.
 */
public class MySQLConnectionPool {

    private MySQLConnectionPool() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
    }

    private static MySQLConnectionPool instance;

    public static synchronized MySQLConnectionPool getInstance() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        if(instance == null){
            instance = new MySQLConnectionPool();
        }
        return instance;
    }

    private List<Connection> availableConnections = new ArrayList<>();
    private List<Connection> usedConnections = new ArrayList<>();

    public synchronized Connection getConnection() throws SQLException {
        if(availableConnections.isEmpty()){
            Connection newConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/epam_movie_rating",
                    "root", "root");
            availableConnections.add(newConnection);
        }

        Connection connection = availableConnections.remove(0);
        usedConnections.add(connection);
        return connection;
    }

    public synchronized void freeConnection(Connection connection) throws SQLException {
        if(usedConnections.isEmpty()){
            throw new MySQLConnectionPoolException("Try to free pool when there is no used connections");
        }
        if(connection.isClosed()){
            throw new MySQLConnectionPoolException("Try to free connection which was closed already");
        }
        if(!connection.getAutoCommit()){
            connection.setAutoCommit(true);
        }
        boolean removeSuccess = usedConnections.remove(connection);
        if(!removeSuccess){
            throw new MySQLConnectionPoolException("Try to free pool which was created not in Connection Pool");
        }
        availableConnections.add(connection);
    }

}
