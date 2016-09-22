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
    /**
     * Adds a country to the data storage (in the default language).
     *
     * @param country a country object
     * @throws DAOException
     */
    void addCountry(Country country) throws DAOException;

    /**
     * Updates a country or adds/updates a localization of a country in the data storage.
     *
     * If the languageId argument is an id of the default language of the application, then it updates
     * a country. If the language argument is an id of the different language (not default) then it
     * adds/updates a localization of a country (it based on the fact of existence of a localization:
     * if it doesn't exist then it will be added, otherwise a localization will be updated).
     * @param country a country object
     * @param languageId a language id like 'EN', "RU' etc.
     * @throws DAOException
     */
    void updateCountry(Country country, String languageId) throws DAOException;

    /**
     * Deletes a country from the data storage (with all of the localizations).
     *
     * @param id an id of a deleting country
     * @throws DAOException
     */
    void deleteCountry(int id) throws DAOException;

    /**
     * Returns all the countries from data storage.
     *
     * @param languageId a language id like 'EN', "RU' etc.
     * @return all the countries
     * @throws DAOException
     */
    List<Country> getAllCountries(String languageId) throws DAOException;

    /**
     * Returns a country by id from data storage.
     *
     * @param id an id of a needed country
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a country by id
     * @throws DAOException
     */
    Country getCountryById(int id, String languageId) throws DAOException;

    /**
     * Returns a countries belonging to the movie from the data storage.
     *
     * @param movieId an id of the movie
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a countries belonging to the movie
     * @throws DAOException
     */
    List<Country> getCountriesByMovie(int movieId, String languageId) throws DAOException;

    /**
     * Returns a countries ordered by a position number from the data storage.
     *
     * @param amount a needed amount of countries
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a countries ordered by a position number
     * @throws DAOException
     */
    List<Country> getTopPositionCountries(int amount, String languageId) throws DAOException;

    /**
     * Returns a countries from data storage.
     *
     * @param from a start position in the countries list (started from 0)
     * @param amount a needed amount of countries
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a countries from data storage
     * @throws DAOException
     */
    List<Country> getCountries(int from, int amount, String languageId) throws DAOException;

    /**
     * Returns an amount of countries in the data storage.
     *
     * @return an amount of countries in the data storage
     * @throws DAOException
     */
    int getCountriesCount() throws DAOException;
}
