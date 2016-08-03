package by.epam.movierating.service.interfaces;

import by.epam.movierating.domain.Person;
import by.epam.movierating.service.exception.ServiceException;

import java.util.List;

/**
 * Created by Владислав on 25.07.2016.
 */
public interface PersonService {
    Person getPersonById(int id, String languageId) throws ServiceException;
    List<Person> getPersonsByCriteria(String name, int from, int amount, String languageId) throws ServiceException;
    int getPersonsCountByCriteria(String name, String languageId) throws ServiceException;
}
