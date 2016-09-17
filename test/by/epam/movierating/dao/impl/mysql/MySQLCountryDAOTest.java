package by.epam.movierating.dao.impl.mysql;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.dao.inter.CountryDAO;
import by.epam.movierating.dao.inter.MovieCountryDAO;
import by.epam.movierating.dao.inter.MovieDAO;
import by.epam.movierating.dao.inter.PoolDAO;
import by.epam.movierating.domain.Country;
import by.epam.movierating.domain.Movie;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Tests the methods of MySQLCountryDAO class.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class MySQLCountryDAOTest {
    @Test
    public void addCountryTest(){
        DAOFactory daoFactory = null;
        PoolDAO poolDAO = null;
        CountryDAO countryDAO = null;
        try{
            daoFactory = DAOFactory.getInstance();
            poolDAO = daoFactory.getPoolDAO();
            countryDAO = daoFactory.getCountryDAO();

            poolDAO.init();

            Country expectedCountry = new Country();
            expectedCountry.setName("Test country name");
            expectedCountry.setPosition(10);
            countryDAO.addCountry(expectedCountry);

            List<Country> allCountries = countryDAO.getAllCountries("EN");
            Country actualCountry = allCountries.get(allCountries.size() - 1);

            countryDAO.deleteCountry(actualCountry.getId());

            Assert.assertEquals(expectedCountry.getName(), actualCountry.getName());
            Assert.assertEquals(expectedCountry.getPosition(), actualCountry.getPosition());
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
    public void updateCountryTest(){
        DAOFactory daoFactory = null;
        PoolDAO poolDAO = null;
        CountryDAO countryDAO = null;
        try{
            daoFactory = DAOFactory.getInstance();
            poolDAO = daoFactory.getPoolDAO();
            countryDAO = daoFactory.getCountryDAO();

            poolDAO.init();

            List<Country> countries = countryDAO.getAllCountries("EN");
            Country expectedCountry = countries.get(0);

            Country rollbackCountry = new Country();
            rollbackCountry.setId(expectedCountry.getId());
            rollbackCountry.setName(expectedCountry.getName());
            rollbackCountry.setPosition(expectedCountry.getPosition());

            expectedCountry.setName("Test country name");
            expectedCountry.setPosition(10);
            countryDAO.updateCountry(expectedCountry, "EN");

            Country actualCountry = countryDAO.getCountryById(expectedCountry.getId(), "EN");

            countryDAO.updateCountry(rollbackCountry, "EN");

            Assert.assertEquals(expectedCountry.getName(), actualCountry.getName());
            Assert.assertEquals(expectedCountry.getPosition(), actualCountry.getPosition());
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
    public void deleteCountryTest(){
        DAOFactory daoFactory = null;
        PoolDAO poolDAO = null;
        CountryDAO countryDAO = null;
        MovieDAO movieDAO = null;
        MovieCountryDAO movieCountryDAO = null;
        try{
            daoFactory = DAOFactory.getInstance();
            poolDAO = daoFactory.getPoolDAO();
            countryDAO = daoFactory.getCountryDAO();
            movieDAO = daoFactory.getMovieDAO();
            movieCountryDAO = daoFactory.getMovieCountryDAO();

            poolDAO.init();

            List<Country> countries = countryDAO.getAllCountries("EN");
            Country rollbackCountry = countries.get(0);
            List<Movie> rollbackMovies = movieDAO.getMoviesByCountry(rollbackCountry.getId(), "EN");

            countryDAO.deleteCountry(rollbackCountry.getId());

            Country deletedCountry = countryDAO.getCountryById(rollbackCountry.getId(), "EN");

            countryDAO.addCountry(rollbackCountry);
            countries = countryDAO.getAllCountries("EN");
            rollbackCountry = countries.get(countries.size() - 1);
            for(Movie movie : rollbackMovies){
                movieCountryDAO.addMovieToCountry(movie.getId(), rollbackCountry.getId());
            }

            Assert.assertNull(deletedCountry);
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
