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
 * Tests the methods of MySQLMovieCountryDAO class.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class MySQLMovieCountryDAOTest {
    @Test
    public void addMovieToCountryTest(){
        DAOFactory daoFactory = null;
        PoolDAO poolDAO = null;
        MovieCountryDAO movieCountryDAO = null;
        MovieDAO movieDAO = null;
        CountryDAO countryDAO = null;
        try{
            daoFactory = DAOFactory.getInstance();
            poolDAO = daoFactory.getPoolDAO();
            movieCountryDAO = daoFactory.getMovieCountryDAO();
            movieDAO = daoFactory.getMovieDAO();
            countryDAO = daoFactory.getCountryDAO();

            poolDAO.init();

            List<Movie> movies = movieDAO.getAllMovies("EN");
            Movie testMovie = movies.get(0);
            List<Country> countries = countryDAO.getAllCountries("EN");
            Country testCountry = countries.get(0);

            movieCountryDAO.addMovieToCountry(testMovie.getId(), testCountry.getId());

            List<Country> countriesOfMovie = countryDAO.getCountriesByMovie(testMovie.getId(), "EN");
            boolean hasTestCountry = false;
            for(Country country : countriesOfMovie){
                if(country.getId() == testCountry.getId()){
                    hasTestCountry = true;
                }
            }

            movieCountryDAO.deleteMovieFromCountry(testMovie.getId(), testCountry.getId());

            Assert.assertTrue(hasTestCountry);
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
    public void deleteMovieFromCountryTest(){
        DAOFactory daoFactory = null;
        PoolDAO poolDAO = null;
        MovieCountryDAO movieCountryDAO = null;
        MovieDAO movieDAO = null;
        CountryDAO countryDAO = null;
        try{
            daoFactory = DAOFactory.getInstance();
            poolDAO = daoFactory.getPoolDAO();
            movieCountryDAO = daoFactory.getMovieCountryDAO();
            movieDAO = daoFactory.getMovieDAO();
            countryDAO = daoFactory.getCountryDAO();

            poolDAO.init();

            List<Movie> movies = movieDAO.getAllMovies("EN");
            Movie testMovie = movies.get(0);
            List<Country> countries = countryDAO.getAllCountries("EN");
            Country testCountry = countries.get(0);

            movieCountryDAO.deleteMovieFromCountry(testMovie.getId(), testCountry.getId());

            List<Country> countriesOfMovie = countryDAO.getCountriesByMovie(testMovie.getId(), "EN");
            boolean hasTestCountry = false;
            for(Country country : countriesOfMovie){
                if(country.getId() == testCountry.getId()){
                    hasTestCountry = true;
                }
            }

            movieCountryDAO.addMovieToCountry(testMovie.getId(), testCountry.getId());

            Assert.assertFalse(hasTestCountry);
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
