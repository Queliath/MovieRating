package by.epam.movierating.service.impl;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.dao.interfaces.CountryDAO;
import by.epam.movierating.domain.Country;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.interfaces.CountryService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 15.07.2016.
 */
public class CountryServiceImpl implements CountryService {
    @Override
    public List<Country> getTopPositionCountries(int amount, String languageId) throws ServiceException {
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
}
