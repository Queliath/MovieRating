package by.epam.movierating.service.exception;

/**
 * Created by Владислав on 15.07.2016.
 */
public class ServiceException extends Exception {
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
