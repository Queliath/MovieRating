package by.epam.movierating.controller.exception;

/**
 * Created by Владислав on 17.07.2016.
 */
public class CommandNotFoundException extends Exception {
    public CommandNotFoundException(String message) {
        super(message);
    }

    public CommandNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
