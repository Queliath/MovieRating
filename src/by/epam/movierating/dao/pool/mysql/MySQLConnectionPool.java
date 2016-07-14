package by.epam.movierating.dao.pool.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Владислав on 12.06.2016.
 */
public class MySQLConnectionPool {
    private static final int NUMBER_OF_CONNECTIONS = 5;
    private static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    private static final String HOST_CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "epam_movie_rating";
    private static final String USER_LOGIN = "root";
    private static final String USER_PASSWORD = "root";

    private static MySQLConnectionPool instance;

    private List<Connection> availableConnections = new ArrayList<>();
    private List<Connection> usedConnections = new ArrayList<>();

    private static final Lock staticLock = new ReentrantLock();
    private final Lock lock = new ReentrantLock();
    private final Condition atLeastOneFreeConnection = lock.newCondition();

    private MySQLConnectionPool() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Class.forName(DRIVER_CLASS_NAME).newInstance();
        for(int i = 0; i < NUMBER_OF_CONNECTIONS; i++){
            Connection newConnection = DriverManager.getConnection(HOST_CONNECTION_STRING +
                    DATABASE_NAME, USER_LOGIN, USER_PASSWORD);
            availableConnections.add(newConnection);
        }
    }

    public static MySQLConnectionPool getInstance() throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {
        staticLock.lock();
        try {
            if (instance == null) {
                instance = new MySQLConnectionPool();
            }
            return instance;
        } finally {
            staticLock.unlock();
        }
    }

    public Connection getConnection() throws SQLException, InterruptedException {
        lock.lock();
        try {
            while (availableConnections.isEmpty()){
                atLeastOneFreeConnection.await();
            }

            Connection connection = availableConnections.remove(0);
            usedConnections.add(connection);
            return connection;
        } finally {
            lock.unlock();
        }
    }

    public void freeConnection(Connection connection) throws SQLException, MySQLConnectionPoolException {
        lock.lock();
        try {
            if (usedConnections.isEmpty() || !usedConnections.contains(connection)) {
                throw new MySQLConnectionPoolException("Try to free pool which was created not in Connection Pool");
            }
            usedConnections.remove(connection);

            if (connection.isClosed()) {
                connection = DriverManager.getConnection(HOST_CONNECTION_STRING +
                        DATABASE_NAME, USER_LOGIN, USER_PASSWORD);
            } else {
                if (!connection.getAutoCommit()) {
                    connection.setAutoCommit(true);
                }
                if (connection.isReadOnly()) {
                    connection.setReadOnly(false);
                }
            }

            availableConnections.add(connection);

            atLeastOneFreeConnection.signal();
        } finally {
            lock.unlock();
        }
    }

}
