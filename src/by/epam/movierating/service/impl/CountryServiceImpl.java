package by.epam.movierating.service.impl;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.dao.interfaces.CountryDAO;
import by.epam.movierating.domain.Country;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.interfaces.CountryService;

import javax.print.attribute.standard.MediaSize;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 15.07.2016.
 */
public class CountryServiceImpl implements CountryService {
    private static final int NAME_MAX_LENGTH = 45;

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

    @Override
    public List<Country> getCountries(int from, int amount, String languageId) throws ServiceException {
        if(from <= 0 || amount <= 0){
            throw new ServiceException("Wrong parameters for getting countries");
        }

        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            CountryDAO countryDAO = daoFactory.getCountryDAO();
            List<Country> countries = countryDAO.getCountries(from, amount, languageId);
            return countries;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get countries", e);
        }
    }

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
