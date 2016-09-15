package by.epam.movierating.dao.inter;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.domain.Person;

import java.util.List;

/**
 * Provides a DAO-logic for the Person entity.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public interface PersonDAO {
    void addPerson(Person person) throws DAOException;
    void updatePerson(Person person, String languageId) throws DAOException;
    void deletePerson(int id) throws DAOException;
    List<Person> getAllPersons(String languageId) throws DAOException;
    Person getPersonById(int id, String languageId) throws DAOException;
    List<Person> getPersonsByMovieAndRelationType(int movieId, int relationType, String languageId) throws DAOException;
    List<Person> getPersonsByCriteria(String name, int from, int amount, String languageId) throws DAOException;
    int getPersonsCountByCriteria(String name, String languageId) throws DAOException;
}
