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
    /**
     * Adds an user to the data storage.
     *
     * @param user an user object
     * @throws DAOException
     */
    void addUser(User user) throws DAOException;

    /**
     * Updates an user in the data storage.
     *
     * @param user an user object
     * @throws DAOException
     */
    void updateUser(User user) throws DAOException;

    /**
     * Deletes an user from the data storage.
     *
     * @param id an id of the deleting user
     * @throws DAOException
     */
    void deleteUser(int id) throws DAOException;

    /**
     * Returns all the users from the data storage.
     *
     * @return all the users from
     * @throws DAOException
     */
    List<User> getAllUsers() throws DAOException;

    /**
     * Returns an user by id from the data storage.
     *
     * @param id an id of the needed user
     * @return an user by id
     * @throws DAOException
     */
    User getUserById(int id) throws DAOException;

    /**
     * Returns an user by the email from the data storage.
     *
     * @param email an email of the needed user
     * @return an user by email
     * @throws DAOException
     */
    User getUserByEmail(String email) throws DAOException;

    /**
     * Returns an users by the status from the data storage.
     *
     * @param status a status of the needed user
     * @return an users by the status
     * @throws DAOException
     */
    List<User> getUsersByStatus(String status) throws DAOException;

    /**
     * Returns an users matching the criteria from the data storage.
     *
     * @param criteria a UserCriteria object
     * @param from a starting position in users list (starting from 0)
     * @param amount a needed amount of users
     * @return an users matching the criteria
     * @throws DAOException
     */
    List<User> getUsersByCriteria(UserCriteria criteria, int from, int amount) throws DAOException;

    /**
     * Returns an amount of users matching the criteria.
     *
     * @param criteria a UserCriteria object
     * @return an amount of users matching the criteria
     * @throws DAOException
     */
    int getUsersCountByCriteria(UserCriteria criteria) throws DAOException;
}
