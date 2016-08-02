package by.epam.movierating.dao.factory;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.interfaces.*;

import java.util.ResourceBundle;

/**
 * Created by Владислав on 14.07.2016.
 */
public abstract class DAOFactory {
    private static final String RESOURCE_BUNDLE_NAME = "dao-factory";
    private static final String FACTORY_CLASS_KEY = "factory-class";

    private static DAOFactory instance;

    public abstract MovieDAO getMovieDAO();
    public abstract GenreDAO getGenreDAO();
    public abstract CountryDAO getCountryDAO();
    public abstract PersonDAO getPersonDAO();
    public abstract UserDAO getUserDAO();
    public abstract RatingDAO getRatingDAO();
    public abstract CommentDAO getCommentDAO();
    public abstract MovieCountryDAO getMovieCountryDAO();
    public abstract MovieGenreDAO getMovieGenreDAO();
    public abstract MoviePersonRelationDAO getMoviePersonRelationDAO();

    public static DAOFactory getInstance() throws DAOException {
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
