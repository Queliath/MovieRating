package by.epam.movierating.service.interfaces;

import by.epam.movierating.domain.Country;
import by.epam.movierating.service.exception.ServiceException;

import java.util.List;

/**
 * Created by Владислав on 15.07.2016.
 */
public interface CountryService {
    List<Country> getAllCountries() throws ServiceException;
}
