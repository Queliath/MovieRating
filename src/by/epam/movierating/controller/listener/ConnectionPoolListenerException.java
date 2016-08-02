package by.epam.movierating.controller.listener;

/**
 * Created by Владислав on 02.08.2016.
 */
public class ConnectionPoolListenerException extends RuntimeException {
    public ConnectionPoolListenerException(String message) {
        super(message);
    }

    public ConnectionPoolListenerException(String message, Throwable cause) {
        super(message, cause);
    }
}
