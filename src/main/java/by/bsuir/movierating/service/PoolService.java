package by.bsuir.movierating.service;

/**
 * Provides a logic for the Connection Pool in the DAO layer.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public interface PoolService {
    /**
     * Gives a command to initialize the Connection Pool in the DAO layer.
     */
    void init();

    /**
     * Gives a command to destroy the Connection Pool in the DAO layer.
     */
    void destroy();
}
