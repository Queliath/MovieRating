package by.epam.movierating.dao.impl.mysql;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.dao.inter.MovieDAO;
import by.epam.movierating.dao.inter.MoviePersonRelationDAO;
import by.epam.movierating.dao.inter.PersonDAO;
import by.epam.movierating.dao.inter.PoolDAO;
import by.epam.movierating.domain.Movie;
import by.epam.movierating.domain.Person;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Tests the methods of MySQLMoviePersonRelationDAO class.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class MySQLMoviePersonRelationDAOTest {
    @Test
    public void addMovieToPersonWithRelationTest(){
        DAOFactory daoFactory = null;
        PoolDAO poolDAO = null;
        MoviePersonRelationDAO moviePersonRelationDAO = null;
        MovieDAO movieDAO = null;
        PersonDAO personDAO = null;
        try {
            daoFactory = DAOFactory.getInstance();
            poolDAO = daoFactory.getPoolDAO();
            moviePersonRelationDAO = daoFactory.getMoviePersonRelationDAO();
            movieDAO = daoFactory.getMovieDAO();
            personDAO = daoFactory.getPersonDAO();

            poolDAO.init();

            List<Movie> movies = movieDAO.getAllMovies("EN");
            Movie testMovie = movies.get(0);
            List<Person> persons = personDAO.getAllPersons("EN");
            Person testPerson = persons.get(0);

            moviePersonRelationDAO.addMovieToPersonWithRelation(testMovie.getId(), testPerson.getId(), 1);

            List<Person> personsOfMovie = personDAO.getPersonsByMovieAndRelationType(testMovie.getId(), 1, "EN");
            boolean hasTestPerson = false;
            for (Person person : personsOfMovie){
                if(person.getId() == testPerson.getId()){
                    hasTestPerson = true;
                }
            }

            moviePersonRelationDAO.deleteMovieFromPersonWithRelation(testMovie.getId(), testPerson.getId(), 1);

            Assert.assertTrue(hasTestPerson);
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
    public void deleteMovieFromPersonWithRelationTest(){
        DAOFactory daoFactory = null;
        PoolDAO poolDAO = null;
        MoviePersonRelationDAO moviePersonRelationDAO = null;
        MovieDAO movieDAO = null;
        PersonDAO personDAO = null;
        try {
            daoFactory = DAOFactory.getInstance();
            poolDAO = daoFactory.getPoolDAO();
            moviePersonRelationDAO = daoFactory.getMoviePersonRelationDAO();
            movieDAO = daoFactory.getMovieDAO();
            personDAO = daoFactory.getPersonDAO();

            poolDAO.init();

            List<Movie> movies = movieDAO.getAllMovies("EN");
            Movie testMovie = movies.get(0);
            List<Person> persons = personDAO.getAllPersons("EN");
            Person testPerson = persons.get(0);

            moviePersonRelationDAO.deleteMovieFromPersonWithRelation(testMovie.getId(), testPerson.getId(), 1);

            List<Person> personsOfMovie = personDAO.getPersonsByMovieAndRelationType(testMovie.getId(), 1, "EN");
            boolean hasTestPerson = false;
            for (Person person : personsOfMovie){
                if(person.getId() == testPerson.getId()){
                    hasTestPerson = true;
                }
            }

            moviePersonRelationDAO.addMovieToPersonWithRelation(testMovie.getId(), testPerson.getId(), 1);

            Assert.assertFalse(hasTestPerson);
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
