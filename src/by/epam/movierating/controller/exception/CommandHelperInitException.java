package by.epam.movierating.controller.exception;

/**
 * Created by Владислав on 02.08.2016.
 */
public class CommandHelperInitException extends Exception {
    public CommandHelperInitException(String message) {
        super(message);
    }

    public CommandHelperInitException(String message, Throwable cause) {
        super(message, cause);
    }
}
