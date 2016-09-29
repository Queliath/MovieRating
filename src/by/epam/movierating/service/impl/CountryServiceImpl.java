package by.epam.movierating.service.impl;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.dao.inter.CountryDAO;
import by.epam.movierating.domain.Country;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.inter.CountryService;

import java.util.List;

/**
 * Provides a business-logic with the Country entity.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class CountryServiceImpl implements CountryService {
    private static final int NAME_MAX_LENGTH = 45;

    /**
     * Returns a countries ordered by a position number.
     *
     * @param amount a needed amount of countries
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a countries ordered by a position number
     * @throws ServiceException
     */
    @Override
    public List<Country> getTopPositionCountries(int amount, String languageId) throws ServiceException {
        if(amount <= 0){
            throw new ServiceException("Wrong amount value");
        }

        try {
            CountryDAO countryDAO = DAOFactory.getInstance().getCountryDAO();
            List<Country> countries = countryDAO.getTopPositionCountries(amount, languageId);
            return countries;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get all countries", e);
        }
    }

    /**
     * Returns a concrete amount of the countries from a concrete position.
     *
     * @param from starting position in the countries list (starting from 0)
     * @param amount a needed amount of countries
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a concrete amount of the countries from a concrete position
     * @throws ServiceException
     */
    @Override
    public List<Country> getCountries(int from, int amount, String languageId) throws ServiceException {
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            CountryDAO countryDAO = daoFactory.getCountryDAO();
            List<Country> countries = countryDAO.getCountries(from, amount, languageId);
            return countries;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get countries", e);
        }
    }

    /**
     * Returns a total amount of countries in the data storage.
     *
     * @return a total amount of countries in the data storage
     * @throws ServiceException
     */
    @Override
    public int getCountriesCount() throws ServiceException {
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            CountryDAO countryDAO = daoFactory.getCountryDAO();
            int countriesCount = countryDAO.getCountriesCount();
            return countriesCount;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get countries count", e);
        }
    }

    /**
     * Returns a certain country by id.
     *
     * @param id an id of a needed country
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a certain country
     * @throws ServiceException
     */
    @Override
    public Country getCountryById(int id, String languageId) throws ServiceException {
        if(id <= 0){
            throw new ServiceException("Wrong id for getting country");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            CountryDAO countryDAO = daoFactory.getCountryDAO();
            Country country = countryDAO.getCountryById(id, languageId);
            return country;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get country by id", e);
        }
    }

    /**
     * Adds a new country to the data storage (in the default language).
     *
     * @param name a name of the country
     * @param position a number of a position of the country
     * @throws ServiceException
     */
    @Override
    public void addCountry(String name, int position) throws ServiceException {
        if(name.isEmpty() || name.length() > NAME_MAX_LENGTH || position <= 0){
            throw new ServiceException("Wrong parameters for adding country");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            CountryDAO countryDAO = daoFactory.getCountryDAO();

            Country country = new Country();
            country.setName(name);
            country.setPosition(position);

            countryDAO.addCountry(country);
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot add country", e);
        }
    }

    /**
     * Edits an already existing country or adds/edits a localization of a country.
     *
     * If the languageId argument is an id of the default language of the application, then it edits
     * a country. If the language argument is an id of the different language (not default) then it
     * adds/edits a localization of a country (it based on the fact of existence of a localization:
     * if it doesn't exist then it will be added, otherwise a localization will be updated).
     * @param id an id of the needed country
     * @param name a new name of the country
     * @param position a new position number of the country
     * @param languageId a language id like 'EN', "RU' etc.
     * @throws ServiceException
     */
    @Override
    public void editCountry(int id, String name, int position, String languageId) throws ServiceException {
        if(id <= 0 || name.isEmpty() || name.length() > NAME_MAX_LENGTH || position <= 0){
            throw new ServiceException("Wrong parameters for editing country");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            CountryDAO countryDAO = daoFactory.getCountryDAO();

            Country country = new Country();
            country.setId(id);
            country.setName(name);
            country.setPosition(position);

            countryDAO.updateCountry(country, languageId);
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot edit country", e);
        }
    }

    /**
     * Deletes an existing country from the data storage (with all of the localizations).
     *
     * @param id an id of the deleting country
     * @throws ServiceException
     */
    @Override
    public void deleteCountry(int id) throws ServiceException {
        if(id <= 0){
            throw new ServiceException("Wrong id for deleting country");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            CountryDAO countryDAO = daoFactory.getCountryDAO();
            countryDAO.deleteCountry(id);
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot delete country", e);
        }
    }
}
