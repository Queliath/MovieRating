package by.epam.movierating.dao.inter;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.domain.User;
import by.epam.movierating.domain.criteria.UserCriteria;

import java.util.List;

/**
 * Provides a DAO-logic for the User entity.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public interface UserDAO {
    void addUser(User user) throws DAOException;
    void updateUser(User user) throws DAOException;
    void deleteUser(int id) throws DAOException;
    List<User> getAllUsers() throws DAOException;
    User getUserById(int id) throws DAOException;
    User getUserByEmail(String email) throws DAOException;
    List<User> getUsersByStatus(String status) throws DAOException;
    List<User> getUsersByCriteria(UserCriteria criteria, int from, int amount) throws DAOException;
    int getUsersCountByCriteria(UserCriteria criteria) throws DAOException;
}
