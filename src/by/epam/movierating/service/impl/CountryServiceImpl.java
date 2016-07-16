package by.epam.movierating.service.impl;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.dao.interfaces.CountryDAO;
import by.epam.movierating.domain.Country;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.interfaces.CountryService;

import java.util.List;

/**
 * Created by Владислав on 15.07.2016.
 */
public class CountryServiceImpl implements CountryService {
    @Override
    public List<Country> getAllCountries(String languageId) throws ServiceException {
        try {
            CountryDAO countryDAO = DAOFactory.getInstance().getCountryDAO();
            List<Country> countries = countryDAO.getAllCountries(languageId);
            return countries;
        } catch (DAOException e) {
            throw new ServiceException("Service layer: cannot get all countries", e);
        }
    }
}
