package by.epam.movierating.dao.inter;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.domain.Country;

import java.util.List;

/**
 * Provides a DAO-logic for the Country entity.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public interface CountryDAO {
    void addCountry(Country country) throws DAOException;
    void updateCountry(Country country, String languageId) throws DAOException;
    void deleteCountry(int id) throws DAOException;
    List<Country> getAllCountries(String languageId) throws DAOException;
    Country getCountryById(int id, String languageId) throws DAOException;
    List<Country> getCountriesByMovie(int movieId, String languageId) throws DAOException;
    List<Country> getTopPositionCountries(int amount, String languageId) throws DAOException;
    List<Country> getCountries(int from, int amount, String languageId) throws DAOException;
    int getCountriesCount() throws DAOException;
}
