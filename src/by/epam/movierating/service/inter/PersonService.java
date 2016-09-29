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
    /**
     * Returns a certain person by id.
     *
     * @param id an id of a needed person
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a certain person
     * @throws ServiceException
     */
    Person getPersonById(int id, String languageId) throws ServiceException;

    /**
     * Returns a persons matching the criteria.
     *
     * @param name a name of the person criteria
     * @param from a starting position in the persons list (starting from 0)
     * @param amount a needed amount of a persons
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a persons matching the criteria
     * @throws ServiceException
     */
    List<Person> getPersonsByCriteria(String name, int from, int amount, String languageId) throws ServiceException;

    /**
     * Returns an amount of the persons matching the criteria.
     *
     * @param name a name of the person criteria
     * @param languageId a language id like 'EN', "RU' etc.
     * @return an amount of the persons matching the criteria
     * @throws ServiceException
     */
    int getPersonsCountByCriteria(String name, String languageId) throws ServiceException;

    /**
     * Adds a new person to the data storage (in the default language).
     *
     * @param name a name of the person
     * @param dateOfBirth a date of birth of the person
     * @param placeOfBirth a place of a birth of the person
     * @param photo a URL to the photo of the person
     * @throws ServiceException
     */
    void addPerson(String name, String dateOfBirth, String placeOfBirth, String photo) throws ServiceException;

    /**
     * Edits an already existing person or adds/edits a localization of a person.
     *
     * If the languageId argument is an id of the default language of the application, then it updates
     * a person. If the language argument is an id of the different language (not default) then it
     * adds/updates a localization of a person (it based on the fact of existence of a localization:
     * if it doesn't exist then it will be added, otherwise a localization will be updated).
     * @param id an id of a needed person
     * @param name a new name of the person
     * @param dateOfBirth a new date of a birth of the person
     * @param placeOfBirth a new place of a birth of the person
     * @param photo a URL to the new photo of the person
     * @param languageId a language id like 'EN', "RU' etc.
     * @throws ServiceException
     */
    void editPerson(int id, String name, String dateOfBirth, String placeOfBirth, String photo,
                    String languageId) throws ServiceException;

    /**
     * Deletes an existing person from the data storage (with all of the localizations).
     *
     * @param id an id of the deleting person
     * @throws ServiceException
     */
    void deletePerson(int id) throws ServiceException;
}
