package by.epam.movierating.dao.impl.mysql;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.dao.inter.GenreDAO;
import by.epam.movierating.dao.inter.MovieDAO;
import by.epam.movierating.dao.inter.MovieGenreDAO;
import by.epam.movierating.dao.inter.PoolDAO;
import by.epam.movierating.domain.Genre;
import by.epam.movierating.domain.Movie;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Tests the methods of MySQLGenreDAO class.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class MySQLGenreDAOTest {
    @Test
    public void addGenreTest(){
        DAOFactory daoFactory = null;
        PoolDAO poolDAO = null;
        GenreDAO genreDAO = null;
        try{
            daoFactory = DAOFactory.getInstance();
            poolDAO = daoFactory.getPoolDAO();
            genreDAO = daoFactory.getGenreDAO();

            poolDAO.init();

            Genre expectedGenre = new Genre();
            expectedGenre.setName("Test genre name");
            expectedGenre.setPosition(10);
            genreDAO.addGenre(expectedGenre);

            List<Genre> genres = genreDAO.getAllGenres("EN");
            Genre actualGenre = genres.get(genres.size() - 1);

            genreDAO.deleteGenre(actualGenre.getId());

            Assert.assertEquals(expectedGenre.getName(), actualGenre.getName());
            Assert.assertEquals(expectedGenre.getPosition(), actualGenre.getPosition());
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
    public void updateGenreTest(){
        DAOFactory daoFactory = null;
        PoolDAO poolDAO = null;
        GenreDAO genreDAO = null;
        try{
            daoFactory = DAOFactory.getInstance();
            poolDAO = daoFactory.getPoolDAO();
            genreDAO = daoFactory.getGenreDAO();

            poolDAO.init();

            List<Genre> genres = genreDAO.getAllGenres("EN");
            Genre expectedGenre = genres.get(0);

            Genre rollbackGenre = new Genre();
            rollbackGenre.setId(expectedGenre.getId());
            rollbackGenre.setName(expectedGenre.getName());
            rollbackGenre.setPosition(expectedGenre.getPosition());

            expectedGenre.setName("Test genre name");
            expectedGenre.setPosition(10);
            genreDAO.updateGenre(expectedGenre, "EN");

            Genre actualGenre = genreDAO.getGenreById(expectedGenre.getId(), "EN");

            genreDAO.updateGenre(rollbackGenre, "EN");

            Assert.assertEquals(expectedGenre.getName(), actualGenre.getName());
            Assert.assertEquals(expectedGenre.getPosition(), actualGenre.getPosition());
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
    public void deleteGenreTest(){
        DAOFactory daoFactory = null;
        PoolDAO poolDAO = null;
        GenreDAO genreDAO = null;
        MovieDAO movieDAO = null;
        MovieGenreDAO movieGenreDAO = null;
        try{
            daoFactory = DAOFactory.getInstance();
            poolDAO = daoFactory.getPoolDAO();
            genreDAO = daoFactory.getGenreDAO();
            movieDAO = daoFactory.getMovieDAO();
            movieGenreDAO = daoFactory.getMovieGenreDAO();

            poolDAO.init();

            List<Genre> genres = genreDAO.getAllGenres("EN");
            Genre rollbackGenre = genres.get(0);
            List<Movie> rollbackMovies = movieDAO.getMoviesByGenre(rollbackGenre.getId(), "EN");

            genreDAO.deleteGenre(rollbackGenre.getId());

            Genre deletedGenre = genreDAO.getGenreById(rollbackGenre.getId(), "EN");

            genreDAO.addGenre(rollbackGenre);
            genres = genreDAO.getAllGenres("EN");
            rollbackGenre = genres.get(genres.size() - 1);
            for(Movie movie : rollbackMovies){
                movieGenreDAO.addMovieToGenre(movie.getId(), rollbackGenre.getId());
            }

            Assert.assertNull(deletedGenre);
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
