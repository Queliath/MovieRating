package by.epam.movierating.dao.interfaces;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.domain.Person;

import java.util.List;

/**
 * Created by Владислав on 11.06.2016.
 */
public interface PersonDAO {
    void addPerson(Person person, String languageId) throws DAOException;
    void updatePerson(Person person, String languageId) throws DAOException;
    void deletePerson(int id) throws DAOException;
    List<Person> getAllPersons(String languageId) throws DAOException;
    Person getPersonById(int id, String languageId) throws DAOException;
    List<Person> getPersonsByMovieAndRelationType(int movieId, int relationType, String languageId) throws DAOException;
}
