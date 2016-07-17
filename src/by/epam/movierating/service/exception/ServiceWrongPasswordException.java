package by.epam.movierating.service.exception;

/**
 * Created by Владислав on 17.07.2016.
 */
public class ServiceWrongPasswordException extends ServiceException {
    public ServiceWrongPasswordException(String message) {
        super(message);
    }

    public ServiceWrongPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
