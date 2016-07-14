package by.epam.movierating.dao.interfaces;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.domain.Country;

import java.util.List;

/**
 * Created by Владислав on 18.06.2016.
 */
public interface CountryDAO {
    void addCountry(Country country) throws DAOException;
    void updateCountry(Country country) throws DAOException;
    void deleteCountry(int id) throws DAOException;
    List<Country> getAllCountries() throws DAOException;
    Country getCountryById(int id) throws DAOException;
    List<Country> getCountriesByMovie(int movieId) throws DAOException;
}
