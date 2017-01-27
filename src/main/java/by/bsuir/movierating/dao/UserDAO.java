package by.bsuir.movierating.dao;

import by.bsuir.movierating.domain.User;
import by.bsuir.movierating.domain.criteria.UserCriteria;

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
     */
    void addUser(User user);

    /**
     * Updates an user in the data storage.
     *
     * @param user an user object
     */
    void updateUser(User user);

    /**
     * Deletes an user from the data storage.
     *
     * @param id an id of the deleting user
     */
    void deleteUser(int id);

    /**
     * Returns all the users from the data storage.
     *
     * @return all the users from
     */
    List<User> getAllUsers();

    /**
     * Returns an user by id from the data storage.
     *
     * @param id an id of the needed user
     * @return an user by id
     */
    User getUserById(int id);

    /**
     * Returns an user by the email from the data storage.
     *
     * @param email an email of the needed user
     * @return an user by email
     */
    User getUserByEmail(String email);

    /**
     * Returns an users by the status from the data storage.
     *
     * @param status a status of the needed user
     * @return an users by the status
     */
    List<User> getUsersByStatus(String status);

    /**
     * Returns an users matching the criteria from the data storage.
     *
     * @param criteria a UserCriteria object
     * @param from a starting position in users list (starting from 0)
     * @param amount a needed amount of users
     * @return an users matching the criteria
     */
    List<User> getUsersByCriteria(UserCriteria criteria, int from, int amount);

    /**
     * Returns an amount of users matching the criteria.
     *
     * @param criteria a UserCriteria object
     * @return an amount of users matching the criteria
     */
    int getUsersCountByCriteria(UserCriteria criteria);
}
