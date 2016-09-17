package by.epam.movierating.dao.impl.mysql;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.dao.inter.MovieDAO;
import by.epam.movierating.dao.inter.PoolDAO;
import by.epam.movierating.dao.inter.RatingDAO;
import by.epam.movierating.dao.inter.UserDAO;
import by.epam.movierating.domain.Movie;
import by.epam.movierating.domain.Rating;
import by.epam.movierating.domain.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Tests the methods of MySQLRatingDAO class.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class MySQLRatingDAOTest {
    @Test
    public void addRatingTest(){
        DAOFactory daoFactory = null;
        PoolDAO poolDAO = null;
        RatingDAO ratingDAO = null;
        MovieDAO movieDAO = null;
        UserDAO userDAO = null;
        try {
            daoFactory = DAOFactory.getInstance();
            poolDAO = daoFactory.getPoolDAO();
            ratingDAO = daoFactory.getRatingDAO();
            movieDAO = daoFactory.getMovieDAO();
            userDAO = daoFactory.getUserDAO();

            poolDAO.init();

            List<Movie> movies = movieDAO.getAllMovies("EN");
            Movie testMovie = movies.get(0);
            List<User> users = userDAO.getAllUsers();
            User testUser = users.get(0);

            Rating expectedRating = new Rating();
            expectedRating.setMovieId(testMovie.getId());
            expectedRating.setUserId(testUser.getId());
            expectedRating.setValue(10);
            ratingDAO.addRating(expectedRating);

            Rating actualRating = ratingDAO.getRatingByMovieAndUser(testMovie.getId(), testUser.getId());

            ratingDAO.deleteRating(testMovie.getId(), testUser.getId());

            Assert.assertEquals(expectedRating.getMovieId(), actualRating.getMovieId());
            Assert.assertEquals(expectedRating.getUserId(), actualRating.getUserId());
            Assert.assertEquals(expectedRating.getValue(), actualRating.getValue());
        } catch (DAOException e) {
            e.printStackTrace();
        } finally {
            if(poolDAO != null){
                try {
                    poolDAO.destroy();
                } catch (DAOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void updateRatingTest(){
        DAOFactory daoFactory = null;
        PoolDAO poolDAO = null;
        RatingDAO ratingDAO = null;
        MovieDAO movieDAO = null;
        UserDAO userDAO = null;
        try{
            daoFactory = DAOFactory.getInstance();
            poolDAO = daoFactory.getPoolDAO();
            ratingDAO = daoFactory.getRatingDAO();
            movieDAO = daoFactory.getMovieDAO();
            userDAO = daoFactory.getUserDAO();

            poolDAO.init();

            List<Movie> movies = movieDAO.getAllMovies("EN");
            Movie testMovie = movies.get(0);
            List<User> users = userDAO.getAllUsers();
            User testUser = users.get(0);

            Rating expectedRating = ratingDAO.getRatingByMovieAndUser(testMovie.getId(), testUser.getId());

            Rating rollbackRating = new Rating();
            rollbackRating.setMovieId(expectedRating.getMovieId());
            rollbackRating.setUserId(expectedRating.getUserId());
            rollbackRating.setValue(expectedRating.getValue());

            expectedRating.setValue(3);
            ratingDAO.updateRating(expectedRating);

            Rating actualRating = ratingDAO.getRatingByMovieAndUser(expectedRating.getMovieId(), expectedRating.getUserId());

            ratingDAO.updateRating(rollbackRating);

            Assert.assertEquals(expectedRating.getMovieId(), actualRating.getMovieId());
            Assert.assertEquals(expectedRating.getUserId(), actualRating.getUserId());
            Assert.assertEquals(expectedRating.getValue(), actualRating.getValue());
        } catch (DAOException e) {
            e.printStackTrace();
        } finally {
            if(poolDAO != null){
                try {
                    poolDAO.destroy();
                } catch (DAOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void deleteRatingTest(){
        DAOFactory daoFactory = null;
        PoolDAO poolDAO = null;
        RatingDAO ratingDAO = null;
        MovieDAO movieDAO = null;
        UserDAO userDAO = null;
        try{
            daoFactory = DAOFactory.getInstance();
            poolDAO = daoFactory.getPoolDAO();
            ratingDAO = daoFactory.getRatingDAO();
            movieDAO = daoFactory.getMovieDAO();
            userDAO = daoFactory.getUserDAO();

            poolDAO.init();

            List<Movie> movies = movieDAO.getAllMovies("EN");
            Movie testMovie = movies.get(0);
            List<User> users = userDAO.getAllUsers();
            User testUser = users.get(0);

            Rating rollbackRating = ratingDAO.getRatingByMovieAndUser(testMovie.getId(), testUser.getId());

            ratingDAO.deleteRating(rollbackRating.getMovieId(), rollbackRating.getUserId());

            Rating deletedRating = ratingDAO.getRatingByMovieAndUser(rollbackRating.getMovieId(), rollbackRating.getUserId());

            ratingDAO.addRating(rollbackRating);

            Assert.assertNull(deletedRating);
        } catch (DAOException e) {
            e.printStackTrace();
        } finally {
            if(poolDAO != null){
                try {
                    poolDAO.destroy();
                } catch (DAOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
