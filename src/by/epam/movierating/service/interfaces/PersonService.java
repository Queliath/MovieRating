package by.epam.movierating.service.interfaces;

import by.epam.movierating.domain.Person;
import by.epam.movierating.service.exception.ServiceException;

/**
 * Created by Владислав on 25.07.2016.
 */
public interface PersonService {
    Person getPersonById(int id, String languageId) throws ServiceException;
}
