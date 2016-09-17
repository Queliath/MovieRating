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
 * Tests the methods of MySQLMovieGenreDAO class.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class MySQLMovieGenreDAOTest {
    @Test
    public void addMovieToGenreTest(){
        DAOFactory daoFactory = null;
        PoolDAO poolDAO = null;
        MovieGenreDAO movieGenreDAO = null;
        MovieDAO movieDAO = null;
        GenreDAO genreDAO = null;
        try{
            daoFactory = DAOFactory.getInstance();
            poolDAO = daoFactory.getPoolDAO();
            movieGenreDAO = daoFactory.getMovieGenreDAO();
            movieDAO = daoFactory.getMovieDAO();
            genreDAO = daoFactory.getGenreDAO();

            poolDAO.init();

            List<Movie> movies = movieDAO.getAllMovies("EN");
            Movie testMovie = movies.get(0);
            List<Genre> genres = genreDAO.getAllGenres("EN");
            Genre testGenre = genres.get(0);

            movieGenreDAO.addMovieToGenre(testMovie.getId(), testGenre.getId());

            List<Genre> genresOfMovie = genreDAO.getGenresByMovie(testMovie.getId(), "EN");
            boolean hasTestGenre = false;
            for (Genre genre : genresOfMovie){
                if(genre.getId() == testGenre.getId()){
                    hasTestGenre = true;
                }
            }

            movieGenreDAO.deleteMovieFromGenre(testMovie.getId(), testGenre.getId());

            Assert.assertTrue(hasTestGenre);
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
    public void deleteMovieFromGenreTest(){
        DAOFactory daoFactory = null;
        PoolDAO poolDAO = null;
        MovieGenreDAO movieGenreDAO = null;
        MovieDAO movieDAO = null;
        GenreDAO genreDAO = null;
        try{
            daoFactory = DAOFactory.getInstance();
            poolDAO = daoFactory.getPoolDAO();
            movieGenreDAO = daoFactory.getMovieGenreDAO();
            movieDAO = daoFactory.getMovieDAO();
            genreDAO = daoFactory.getGenreDAO();

            poolDAO.init();

            List<Movie> movies = movieDAO.getAllMovies("EN");
            Movie testMovie = movies.get(0);
            List<Genre> genres = genreDAO.getAllGenres("EN");
            Genre testGenre = genres.get(0);

            movieGenreDAO.deleteMovieFromGenre(testMovie.getId(), testGenre.getId());

            List<Genre> genresOfMovie = genreDAO.getGenresByMovie(testMovie.getId(), "EN");
            boolean hasTestGenre = false;
            for (Genre genre : genresOfMovie){
                if(genre.getId() == testGenre.getId()){
                    hasTestGenre = true;
                }
            }

            movieGenreDAO.addMovieToGenre(testMovie.getId(), testGenre.getId());

            Assert.assertFalse(hasTestGenre);
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
