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

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Tests the methods of MySQLPersonDAO class.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class MySQLPersonDAOTest {
    @Test
    public void addPersonTest(){
        DAOFactory daoFactory = null;
        PoolDAO poolDAO = null;
        PersonDAO personDAO = null;
        try{
            daoFactory = DAOFactory.getInstance();
            poolDAO = daoFactory.getPoolDAO();
            personDAO = daoFactory.getPersonDAO();

            poolDAO.init();

            Person expectedPerson = new Person();
            expectedPerson.setName("Test person name");
            expectedPerson.setDateOfBirth(new GregorianCalendar(2016, 5, 19).getTime());
            expectedPerson.setPlaceOfBirth("Test person place of birth");
            expectedPerson.setPhoto("Test person photo");
            personDAO.addPerson(expectedPerson);

            List<Person> persons = personDAO.getAllPersons("EN");
            Person actualPerson = persons.get(persons.size() - 1);

            personDAO.deletePerson(actualPerson.getId());

            Assert.assertEquals(expectedPerson.getName(), actualPerson.getName());
            Assert.assertEquals(expectedPerson.getDateOfBirth(), actualPerson.getDateOfBirth());
            Assert.assertEquals(expectedPerson.getPlaceOfBirth(), actualPerson.getPlaceOfBirth());
            Assert.assertEquals(expectedPerson.getPhoto(), actualPerson.getPhoto());
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
    public void updatePersonTest(){
        DAOFactory daoFactory = null;
        PoolDAO poolDAO = null;
        PersonDAO personDAO = null;
        try{
            daoFactory = DAOFactory.getInstance();
            poolDAO = daoFactory.getPoolDAO();
            personDAO = daoFactory.getPersonDAO();

            poolDAO.init();

            List<Person> persons = personDAO.getAllPersons("EN");
            Person expectedPerson = persons.get(0);

            Person rollbackPerson = new Person();
            rollbackPerson.setId(expectedPerson.getId());
            rollbackPerson.setName(expectedPerson.getName());
            rollbackPerson.setDateOfBirth(expectedPerson.getDateOfBirth());
            rollbackPerson.setPlaceOfBirth(expectedPerson.getPlaceOfBirth());
            rollbackPerson.setPhoto(expectedPerson.getPhoto());

            expectedPerson.setName("Test person name");
            expectedPerson.setDateOfBirth(new GregorianCalendar(2016, 5, 19).getTime());
            expectedPerson.setPlaceOfBirth("Test person place of birth");
            expectedPerson.setPhoto("Test person photo");
            personDAO.updatePerson(expectedPerson, "EN");

            Person actualPerson = personDAO.getPersonById(expectedPerson.getId(), "EN");

            personDAO.updatePerson(rollbackPerson, "EN");

            Assert.assertEquals(expectedPerson.getName(), actualPerson.getName());
            Assert.assertEquals(expectedPerson.getDateOfBirth(), actualPerson.getDateOfBirth());
            Assert.assertEquals(expectedPerson.getPlaceOfBirth(), actualPerson.getPlaceOfBirth());
            Assert.assertEquals(expectedPerson.getPhoto(), actualPerson.getPhoto());
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
    public void deletePersonTest(){
        DAOFactory daoFactory = null;
        PoolDAO poolDAO = null;
        PersonDAO personDAO = null;
        MovieDAO movieDAO = null;
        MoviePersonRelationDAO moviePersonRelationDAO = null;
        try{
            daoFactory = DAOFactory.getInstance();
            poolDAO = daoFactory.getPoolDAO();
            personDAO = daoFactory.getPersonDAO();
            movieDAO = daoFactory.getMovieDAO();
            moviePersonRelationDAO = daoFactory.getMoviePersonRelationDAO();

            poolDAO.init();

            List<Person> persons = personDAO.getAllPersons("EN");
            Person rollbackPerson = persons.get(0);
            List<List<Movie>> rollbackMovies = new ArrayList<>();
            for(int i = 1; i < 9; i++){
                rollbackMovies.add(movieDAO.getMoviesByPersonAndRelationType(rollbackPerson.getId(), i, "EN"));
            }

            personDAO.deletePerson(rollbackPerson.getId());

            Person deletedPerson = personDAO.getPersonById(rollbackPerson.getId(), "EN");

            personDAO.addPerson(rollbackPerson);
            persons = personDAO.getAllPersons("EN");
            rollbackPerson = persons.get(persons.size() - 1);
            for(int i = 0; i < 8; i++){
                List<Movie> movieList = rollbackMovies.get(i);
                for(Movie movie : movieList){
                    moviePersonRelationDAO.addMovieToPersonWithRelation(movie.getId(), rollbackPerson.getId(), i + 1);
                }
            }

            Assert.assertNull(deletedPerson);
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
