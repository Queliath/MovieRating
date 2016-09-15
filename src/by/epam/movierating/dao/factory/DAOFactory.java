package by.epam.movierating.dao.factory;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.inter.*;

import java.util.ResourceBundle;

/**
 * Provides a logic of instancing DAO objects.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public abstract class DAOFactory {
    private static final String RESOURCE_BUNDLE_NAME = "dao-factory";
    private static final String FACTORY_CLASS_KEY = "factory-class";

    private static DAOFactory instance;

    /**
     * Returns an implementation of a MovieDAO interface.
     *
     * @return an implementation of a MovieDAO interface
     */
    public abstract MovieDAO getMovieDAO();

    /**
     * Returns an implementation of a GenreDAO interface.
     *
     * @return an implementation of a GenreDAO interface
     */
    public abstract GenreDAO getGenreDAO();

    /**
     * Returns an implementation of a CountryDAO interface.
     *
     * @return an implementation of a CountryDAO interface
     */
    public abstract CountryDAO getCountryDAO();

    /**
     * Returns an implementation of a PersonDAO interface.
     *
     * @return an implementation of a PersonDAO interface
     */
    public abstract PersonDAO getPersonDAO();

    /**
     * Returns an implementation of a UserDAO interface.
     *
     * @return an implementation of a UserDAO interface
     */
    public abstract UserDAO getUserDAO();

    /**
     * Returns an implementation of a RatingDAO interface.
     *
     * @return an implementation of a RatingDAO interface
     */
    public abstract RatingDAO getRatingDAO();

    /**
     * Returns an implementation of a CommentDAO interface.
     *
     * @return an implementation of a CommentDAO interface
     */
    public abstract CommentDAO getCommentDAO();

    /**
     * Returns an implementation of a MovieCountryDAO interface.
     *
     * @return an implementation of a MovieCountryDAO interface
     */
    public abstract MovieCountryDAO getMovieCountryDAO();

    /**
     * Returns an implementation of a MovieGenreDAO interface.
     *
     * @return an implementation of a MovieGenreDAO interface
     */
    public abstract MovieGenreDAO getMovieGenreDAO();

    /**
     * Returns an implementation of a MoviePersonRelationDAO interface.
     *
     * @return an implementation of a MoviePersonRelationDAO interface
     */
    public abstract MoviePersonRelationDAO getMoviePersonRelationDAO();

    /**
     * Returns an implementation of a PoolDAO interface.
     *
     * @return an implementation of a PoolDAO interface
     */
    public abstract PoolDAO getPoolDAO();

    /**
     * Returns the instance of the DAOFactory.
     *
     * @return the instance of the DAOFactory
     * @throws DAOException if there is instance initialization error
     */
    public static synchronized DAOFactory getInstance() throws DAOException {
        if(instance == null){
            try {
                ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME);
                String factoryClassName = resourceBundle.getString(FACTORY_CLASS_KEY);
                instance = (DAOFactory) Class.forName(factoryClassName).newInstance();
            } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
                throw new DAOException("Cannot init a DAOFactory", e);
            }
        }
        return instance;
    }
}
