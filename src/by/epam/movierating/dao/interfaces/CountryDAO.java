package by.epam.movierating.dao.interfaces;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.domain.Country;

import java.util.List;

/**
 * Created by Владислав on 18.06.2016.
 */
public interface CountryDAO {
    void addCountry(Country country, String languageId) throws DAOException;
    void updateCountry(Country country, String languageId) throws DAOException;
    void deleteCountry(int id) throws DAOException;
    List<Country> getAllCountries(String languageId) throws DAOException;
    Country getCountryById(int id, String languageId) throws DAOException;
    List<Country> getCountriesByMovie(int movieId, String languageId) throws DAOException;
    List<Country> getTopPositionCountries(int amount, String languageId) throws DAOException;
    List<Country> getCountries(int from, int amount, String languageId) throws DAOException;
    int getCountriesCount() throws DAOException;
}
