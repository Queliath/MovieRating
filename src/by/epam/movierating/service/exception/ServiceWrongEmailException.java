package by.epam.movierating.service.exception;

/**
 * Created by Владислав on 17.07.2016.
 */
public class ServiceWrongEmailException extends ServiceException {
    public ServiceWrongEmailException(String message) {
        super(message);
    }

    public ServiceWrongEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
