package by.epam.movierating.service.inter;

import by.epam.movierating.domain.Country;
import by.epam.movierating.service.exception.ServiceException;

import java.util.List;

/**
 * Created by Владислав on 15.07.2016.
 */
public interface CountryService {
    List<Country> getTopPositionCountries(int amount, String languageId) throws ServiceException;
    List<Country> getCountries(int from, int amount, String languageId) throws ServiceException;
    int getCountriesCount() throws ServiceException;
    Country getCountryById(int id, String languageId) throws ServiceException;
    void addCountry(String name, int position) throws ServiceException;
    void editCountry(int id, String name, int position, String languageId) throws ServiceException;
    void deleteCountry(int id) throws ServiceException;
}
