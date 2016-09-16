package by.epam.movierating.service.inter;

import by.epam.movierating.domain.Person;
import by.epam.movierating.service.exception.ServiceException;

import java.util.List;

/**
 * Provides a business-logic with the Person entity.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public interface PersonService {
    Person getPersonById(int id, String languageId) throws ServiceException;
    List<Person> getPersonsByCriteria(String name, int from, int amount, String languageId) throws ServiceException;
    int getPersonsCountByCriteria(String name, String languageId) throws ServiceException;
    void addPerson(String name, String dateOfBirth, String placeOfBirth, String photo) throws ServiceException;
    void editPerson(int id, String name, String dateOfBirth, String placeOfBirth, String photo,
                    String languageId) throws ServiceException;
    void deletePerson(int id) throws ServiceException;
}
