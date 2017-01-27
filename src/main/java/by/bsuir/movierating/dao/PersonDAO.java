package by.bsuir.movierating.dao;

import by.bsuir.movierating.domain.Person;

import java.util.List;

/**
 * Provides a DAO-logic for the Person entity.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public interface PersonDAO {
    /**
     * Adds a person to the data storage (in the default language).
     *
     * @param person a person object
     */
    void addPerson(Person person);

    /**
     * Updates a person or adds/updates a localization of a person in the data storage.
     *
     * If the languageId argument is an id of the default language of the application, then it updates
     * a person. If the language argument is an id of the different language (not default) then it
     * adds/updates a localization of a person (it based on the fact of existence of a localization:
     * if it doesn't exist then it will be added, otherwise a localization will be updated).
     * @param person a person object
     * @param languageId a language id like 'EN', "RU' etc.
     */
    void updatePerson(Person person, String languageId);

    /**
     * Deletes a person from the data storage (with all of the localizations).
     *
     * @param id an id of the deleting person
     */
    void deletePerson(int id);

    /**
     * Returns all the persons from the data storage.
     *
     * @param languageId a language id like 'EN', "RU' etc.
     * @return all the persons
     */
    List<Person> getAllPersons(String languageId);

    /**
     * Returns a person by id from the data storage.
     *
     * @param id an id of the needed person
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a person by id
     */
    Person getPersonById(int id, String languageId);

    /**
     * Returns a persons which took part in the movie in the certain role.
     *
     * @param movieId an id of the movie
     * @param relationType an id of the relation type (role)
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a persons which took part in the movie in the certain role
     */
    List<Person> getPersonsByMovieAndRelationType(int movieId, int relationType, String languageId);

    /**
     * Returns a persons matching the criteria.
     *
     * @param name a person's name
     * @param from a starting position in the persons list (starting from 0)
     * @param amount a needed amount of persons
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a persons matching the criteria
     */
    List<Person> getPersonsByCriteria(String name, int from, int amount, String languageId);

    /**
     * Returns an amount of persons matching the criteria.
     *
     * @param name a person's name
     * @param languageId a language id like 'EN', "RU' etc.
     * @return an amount of persons matching the criteria
     */
    int getPersonsCountByCriteria(String name, String languageId);
}
