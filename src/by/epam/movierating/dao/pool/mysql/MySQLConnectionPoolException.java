package by.epam.movierating.dao.pool.mysql;

/**
 * Created by Владислав on 18.06.2016.
 */
public class MySQLConnectionPoolException extends Exception {
    public MySQLConnectionPoolException(String message) {
        super(message);
    }

    public MySQLConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }
}
