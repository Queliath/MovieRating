package by.epam.movierating.dao.factory;

import by.epam.movierating.dao.impl.mysql.*;
import by.epam.movierating.dao.inter.*;

/**
 * Provides a logic of instancing DAO objects for the MySQL Database
 */
public class MySQLDAOFactory extends DAOFactory {
    private final MovieDAO mySQLMovieDAO = new MySQLMovieDAO();
    private final GenreDAO mySQLGenreDAO = new MySQLGenreDAO();
    private final CountryDAO mySQLCountryDAO = new MySQLCountryDAO();
    private final PersonDAO mySQLPersonDAO = new MySQLPersonDAO();
    private final UserDAO mySQLUserDAO = new MySQLUserDAO();
    private final RatingDAO mySQLRatingDAO = new MySQLRatingDAO();
    private final CommentDAO mySQLCommentDAO = new MySQLCommentDAO();
    private final MovieCountryDAO mySQLMovieCountryDAO = new MySQLMovieCountryDAO();
    private final MovieGenreDAO mySQLMovieGenreDAO = new MySQLMovieGenreDAO();
    private final MoviePersonRelationDAO mySQLMoviePersonRelationDAO = new MySQLMoviePersonRelationDAO();
    private final PoolDAO mySQLPoolDAO = new MySQLPoolDAO();

    @Override
    public MovieDAO getMovieDAO() {
        return mySQLMovieDAO;
    }

    @Override
    public GenreDAO getGenreDAO() {
        return mySQLGenreDAO;
    }

    @Override
    public CountryDAO getCountryDAO() {
        return mySQLCountryDAO;
    }

    @Override
    public PersonDAO getPersonDAO() {
        return mySQLPersonDAO;
    }

    @Override
    public UserDAO getUserDAO() {
        return mySQLUserDAO;
    }

    @Override
    public RatingDAO getRatingDAO() {
        return mySQLRatingDAO;
    }

    @Override
    public CommentDAO getCommentDAO() {
        return mySQLCommentDAO;
    }

    @Override
    public MovieCountryDAO getMovieCountryDAO() {
        return mySQLMovieCountryDAO;
    }

    @Override
    public MovieGenreDAO getMovieGenreDAO() {
        return mySQLMovieGenreDAO;
    }

    @Override
    public MoviePersonRelationDAO getMoviePersonRelationDAO() {
        return mySQLMoviePersonRelationDAO;
    }

    @Override
    public PoolDAO getPoolDAO() {
        return mySQLPoolDAO;
    }
}
