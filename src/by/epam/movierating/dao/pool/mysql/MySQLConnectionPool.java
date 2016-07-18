package by.epam.movierating.dao.pool.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Владислав on 12.06.2016.
 */
public class MySQLConnectionPool {
    private static final String RESOURCE_BUNDLE_NAME = "mysql-connection";
    private static final String NUMBER_OF_CONNECTIONS_PROPERTY = "numberOfConnections";
    private static final String DRIVER_CLASS_NAME_PROPERTY = "driverClassName";
    private static final String HOST_CONNECTION_STRING_PROPERTY = "hostConnectionString";
    private static final String DATABASE_NAME_PROPERTY = "databaseName";
    private static final String USER_LOGIN_PROPERTY = "userLogin";
    private static final String USER_PASSWORD_PROPERTY = "userPassword";

    private String hostConnectionString;
    private String databaseName;
    private String userLogin;
    private String userPassword;

    private volatile boolean isAvailable = false;

    private static MySQLConnectionPool instance = new MySQLConnectionPool();

    private List<Connection> availableConnections = new ArrayList<>();
    private List<Connection> usedConnections = new ArrayList<>();

    private final Lock lock = new ReentrantLock();
    private final Condition atLeastOneFreeConnection = lock.newCondition();

    private MySQLConnectionPool() {}

    public void init() throws MySQLConnectionPoolException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME);
        String numberOfConnectionsStr = resourceBundle.getString(NUMBER_OF_CONNECTIONS_PROPERTY);
        String driverClassName = resourceBundle.getString(DRIVER_CLASS_NAME_PROPERTY);
        String hostConnectionString = resourceBundle.getString(HOST_CONNECTION_STRING_PROPERTY);
        String databaseName = resourceBundle.getString(DATABASE_NAME_PROPERTY);
        String userLogin = resourceBundle.getString(USER_LOGIN_PROPERTY);
        String userPassword = resourceBundle.getString(USER_PASSWORD_PROPERTY);

        this.hostConnectionString = hostConnectionString;
        this.databaseName = databaseName;
        this.userLogin = userLogin;
        this.userPassword = userPassword;

        try {
            Class.forName(driverClassName).newInstance();
            for(int i = 0; i < Integer.parseInt(numberOfConnectionsStr); i++){
                Connection newConnection = DriverManager.getConnection(hostConnectionString +
                        databaseName, userLogin, userPassword);

                lock.lock();
                availableConnections.add(newConnection);
                isAvailable = true;
                lock.unlock();
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
            throw new MySQLConnectionPoolException("Cannot init a pool", e);
        }
    }

    public void destroy() throws MySQLConnectionPoolException {
        try {
            for (Connection connection : availableConnections) {
                connection.close();
            }
            for (Connection connection : usedConnections) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new MySQLConnectionPoolException("Cannot destroy a pool", e);
        }
    }

    public static MySQLConnectionPool getInstance() {
        return instance;
    }

    public Connection getConnection() throws InterruptedException, MySQLConnectionPoolException {
        if(isAvailable){
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
        else {
            throw new MySQLConnectionPoolException("Try to use pool when it is not available");
        }
    }

    public void freeConnection(Connection connection) throws SQLException, MySQLConnectionPoolException {
        if(isAvailable){
            lock.lock();
            try {
                if (usedConnections.isEmpty() || !usedConnections.contains(connection)) {
                    throw new MySQLConnectionPoolException("Try to free pool which was created not in Connection Pool");
                }
                usedConnections.remove(connection);

                if (connection.isClosed()) {
                    connection = DriverManager.getConnection(hostConnectionString +
                            databaseName, userLogin, userPassword);

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
        else {
            throw new MySQLConnectionPoolException("Try to use pool when it is not available");
        }
    }

}
