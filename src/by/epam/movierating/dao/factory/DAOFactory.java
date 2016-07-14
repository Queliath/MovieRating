package by.epam.movierating.dao.factory;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.interfaces.*;

/**
 * Created by Владислав on 14.07.2016.
 */
public abstract class DAOFactory {
    private static final int MY_SQL = 1;

    private static final DAOFactory mySQLDAOFactory = new MySQLDAOFactory();

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
        int factoryType = readConfig();

        switch (factoryType) {
            case MY_SQL:
                return mySQLDAOFactory;
            default:
                throw new DAOException("Wrong config file for DAO layer");
        }
    }

    private static int readConfig(){
        return MY_SQL;
    }
}
