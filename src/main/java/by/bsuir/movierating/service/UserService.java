package by.bsuir.movierating.service;

import by.bsuir.movierating.domain.User;

import java.util.List;

/**
 * Provides a business-logic with the User entity.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public interface UserService {
    /**
     * Returns a certain user by id.
     *
     * @param id an id of a needed user
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a certain user
     */
    User getUserById(int id, String languageId);

    /**
     * Edits a main information about the already existing user.
     *
     * @param id an id of a needed user
     * @param email a new email of the user
     * @param password a new password of the user
     * @param firstName a new first name of the user
     * @param lastName a new last name of the user
     * @param photo a URL to the new photo of the user
     * @param languageId an id of the new language of the user
     */
    void editUserMainInf(int id, String email, String password, String firstName, String lastName, String photo, String languageId);

    /**
     * Edits a secondary information about the already existing user.
     *
     * @param id an id of a needed user
     * @param rating a new rating of the user
     * @param status a new status of the user
     */
    void editUserSecondInf(int id, int rating, String status);

    /**
     * Deletes existing user from the data storage.
     *
     * @param id an id of the deleting user
     */
    void deleteUser(int id);

    /**
     * Returns an amount of the users matching the criteria.
     *
     * @param email an email of the user criteria
     * @param firstName a first name of the user criteria
     * @param lastName a last name of the user criteria
     * @param minDateOfRegistry a min date of registration of the user criteria
     * @param maxDateOfRegistry a max date of registration of the user criteria
     * @param minRating a min rating of the user criteria
     * @param maxRating a max rating of the user criteria
     * @param statuses a list of possible statuses of the user criteria
     * @return an amount of the users matching the criteria
     */
    int getUsersCountByCriteria(String email, String firstName, String lastName,
                                String minDateOfRegistry, String maxDateOfRegistry, Integer minRating, Integer maxRating,
                                List<String> statuses);

    /**
     * Returns a users matching the criteria.
     *
     * @param email an email of the user criteria
     * @param firstName a first name of the user criteria
     * @param lastName a last name of the user criteria
     * @param minDateOfRegistry a min date of registration of the user criteria
     * @param maxDateOfRegistry a max date of registration of the user criteria
     * @param minRating a min rating of the user criteria
     * @param maxRating a max rating of the user criteria
     * @param statuses a list of possible statuses of the user criteria
     * @param from a starting position in the users list (starting from 0)
     * @param amount a needed amount of the users
     * @return a users matching the criteria
     */
    List<User> getUsersByCriteria(String email, String firstName, String lastName,
                                  String minDateOfRegistry, String maxDateOfRegistry, Integer minRating, Integer maxRating,
                                  List<String> statuses, int from, int amount);
}
