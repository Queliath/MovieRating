package by.epam.movierating.service.impl;

import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.inter.CountryService;
import org.junit.Test;

/**
 * Tests the methods of CountryServiceImpl class.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class CountryServiceImplTest {
    @Test(expected = ServiceException.class)
    public void getTopPositionCountriesTest() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        CountryService countryService = serviceFactory.getCountryService();

        countryService.getTopPositionCountries(-1, "EN");
    }

    @Test(expected = ServiceException.class)
    public void getCountryByIdTest() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        CountryService countryService = serviceFactory.getCountryService();

        countryService.getCountryById(-1, "EN");
    }

    @Test(expected = ServiceException.class)
    public void addCountryTest() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        CountryService countryService = serviceFactory.getCountryService();

        countryService.addCountry("", -1);
    }

    @Test(expected = ServiceException.class)
    public void editCountryTest() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        CountryService countryService = serviceFactory.getCountryService();

        countryService.editCountry(-1, "", -1, "EN");
    }

    @Test(expected = ServiceException.class)
    public void deleteCountryTest() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        CountryService countryService = serviceFactory.getCountryService();

        countryService.deleteCountry(-1);
    }
}
