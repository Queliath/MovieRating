package by.epam.movierating.dao.impl.mysql;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.dao.inter.*;
import by.epam.movierating.domain.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Tests the methods of MySQLMovieDAO class.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class MySQLMovieDAOTest {
    @Test
    public void addMovieTest(){
        DAOFactory daoFactory = null;
        PoolDAO poolDAO = null;
        MovieDAO movieDAO = null;
        try{
            daoFactory = DAOFactory.getInstance();
            poolDAO = daoFactory.getPoolDAO();
            movieDAO = daoFactory.getMovieDAO();

            poolDAO.init();

            Movie expectedMovie = new Movie();
            expectedMovie.setName("Test movie name");
            expectedMovie.setYear(2016);
            expectedMovie.setTagline("Test movie tagline");
            expectedMovie.setBudget(3_000_000);
            expectedMovie.setPremiere(new GregorianCalendar(2016, 5, 19).getTime());
            expectedMovie.setLasting(120);
            expectedMovie.setAnnotation("Test movie annotation");
            expectedMovie.setImage("Test movie image");

            movieDAO.addMovie(expectedMovie);
            List<Movie> movies = movieDAO.getAllMovies("EN");
            Movie actualMovie = movies.get(movies.size() - 1);

            movieDAO.deleteMovie(actualMovie.getId());

            Assert.assertEquals(expectedMovie.getName(), actualMovie.getName());
            Assert.assertEquals(expectedMovie.getYear(), actualMovie.getYear());
            Assert.assertEquals(expectedMovie.getTagline(), actualMovie.getTagline());
            Assert.assertEquals(expectedMovie.getBudget(), actualMovie.getBudget());
            Assert.assertEquals(expectedMovie.getPremiere(), actualMovie.getPremiere());
            Assert.assertEquals(expectedMovie.getLasting(), actualMovie.getLasting());
            Assert.assertEquals(expectedMovie.getAnnotation(), actualMovie.getAnnotation());
            Assert.assertEquals(expectedMovie.getImage(), actualMovie.getImage());
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
    public void updateMovieTest(){
        DAOFactory daoFactory = null;
        PoolDAO poolDAO = null;
        MovieDAO movieDAO = null;
        try{
            daoFactory = DAOFactory.getInstance();
            poolDAO = daoFactory.getPoolDAO();
            movieDAO = daoFactory.getMovieDAO();

            poolDAO.init();

            List<Movie> movies = movieDAO.getAllMovies("EN");
            Movie expectedMovie = movies.get(0);

            Movie rollbackMovie = new Movie();
            rollbackMovie.setId(expectedMovie.getId());
            rollbackMovie.setName(expectedMovie.getName());
            rollbackMovie.setYear(expectedMovie.getYear());
            rollbackMovie.setTagline(expectedMovie.getTagline());
            rollbackMovie.setBudget(expectedMovie.getBudget());
            rollbackMovie.setPremiere(expectedMovie.getPremiere());
            rollbackMovie.setLasting(expectedMovie.getLasting());
            rollbackMovie.setAnnotation(expectedMovie.getAnnotation());
            rollbackMovie.setImage(expectedMovie.getImage());

            expectedMovie.setName("Test movie name");
            expectedMovie.setYear(2016);
            expectedMovie.setTagline("Test movie tagline");
            expectedMovie.setBudget(3_000_000);
            expectedMovie.setPremiere(new GregorianCalendar(2016, 5, 19).getTime());
            expectedMovie.setLasting(120);
            expectedMovie.setAnnotation("Test movie annotation");
            expectedMovie.setImage("Test movie image");
            movieDAO.updateMovie(expectedMovie, "EN");

            Movie actualMovie = movieDAO.getMovieById(expectedMovie.getId(), "EN");

            movieDAO.updateMovie(rollbackMovie, "EN");

            Assert.assertEquals(expectedMovie.getName(), actualMovie.getName());
            Assert.assertEquals(expectedMovie.getYear(), actualMovie.getYear());
            Assert.assertEquals(expectedMovie.getTagline(), actualMovie.getTagline());
            Assert.assertEquals(expectedMovie.getBudget(), actualMovie.getBudget());
            Assert.assertEquals(expectedMovie.getPremiere(), actualMovie.getPremiere());
            Assert.assertEquals(expectedMovie.getLasting(), actualMovie.getLasting());
            Assert.assertEquals(expectedMovie.getAnnotation(), actualMovie.getAnnotation());
            Assert.assertEquals(expectedMovie.getImage(), actualMovie.getImage());
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
    public void deleteMovieTest(){
        DAOFactory daoFactory = null;
        PoolDAO poolDAO = null;
        MovieDAO movieDAO = null;
        CountryDAO countryDAO = null;
        MovieCountryDAO movieCountryDAO = null;
        GenreDAO genreDAO = null;
        MovieGenreDAO movieGenreDAO = null;
        PersonDAO personDAO = null;
        MoviePersonRelationDAO moviePersonRelationDAO = null;
        CommentDAO commentDAO = null;
        try{
            daoFactory = DAOFactory.getInstance();
            poolDAO = daoFactory.getPoolDAO();
            movieDAO = daoFactory.getMovieDAO();
            countryDAO = daoFactory.getCountryDAO();
            movieCountryDAO = daoFactory.getMovieCountryDAO();
            genreDAO = daoFactory.getGenreDAO();
            movieGenreDAO = daoFactory.getMovieGenreDAO();
            personDAO = daoFactory.getPersonDAO();
            moviePersonRelationDAO = daoFactory.getMoviePersonRelationDAO();
            commentDAO = daoFactory.getCommentDAO();

            poolDAO.init();

            List<Movie> movies = movieDAO.getAllMovies("EN");
            Movie rollbackMovie = movies.get(0);
            List<Genre> rollbackGenres = genreDAO.getGenresByMovie(rollbackMovie.getId(), "EN");
            List<Country> rollbackCountries = countryDAO.getCountriesByMovie(rollbackMovie.getId(), "EN");
            List<List<Person>> rollbackPersons = new ArrayList<>();
            for(int i = 1; i < 9; i++){
                rollbackPersons.add(personDAO.getPersonsByMovieAndRelationType(rollbackMovie.getId(), i, "EN"));
            }
            List<Comment> rollbackComments = commentDAO.getCommentsByMovie(rollbackMovie.getId(), "EN");

            movieDAO.deleteMovie(rollbackMovie.getId());

            Movie deletedMovie = movieDAO.getMovieById(rollbackMovie.getId(), "EN");

            movieDAO.addMovie(rollbackMovie);
            movies = movieDAO.getAllMovies("EN");
            rollbackMovie = movies.get(movies.size() - 1);
            for(Genre genre : rollbackGenres){
                movieGenreDAO.addMovieToGenre(rollbackMovie.getId(), genre.getId());
            }
            for(Country country : rollbackCountries){
                movieCountryDAO.addMovieToCountry(rollbackMovie.getId(), country.getId());
            }
            for(int i = 0; i < 8; i++){
                List<Person> personList = rollbackPersons.get(i);
                for(Person person : personList){
                    moviePersonRelationDAO.addMovieToPersonWithRelation(rollbackMovie.getId(), person.getId(), i + 1);
                }
            }
            for(Comment comment : rollbackComments){
                comment.setMovieId(rollbackMovie.getId());
                commentDAO.addComment(comment, "EN");
            }

            Assert.assertNull(deletedMovie);
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
