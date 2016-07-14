package by.epam.movierating.dao.interfaces;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.domain.Person;

import java.util.List;

/**
 * Created by Владислав on 11.06.2016.
 */
public interface PersonDAO {
    void addPerson(Person person) throws DAOException;
    void updatePerson(Person person) throws DAOException;
    void deletePerson(int id) throws DAOException;
    List<Person> getAllPersons() throws DAOException;
    Person getPersonById(int id) throws DAOException;
    List<Person> getPersonsByMovieAndRelationType(int movieId, int relationType) throws DAOException;
}
