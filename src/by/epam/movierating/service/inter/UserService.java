package by.epam.movierating.service.inter;

import by.epam.movierating.domain.User;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.exception.ServiceWrongEmailException;

import java.util.List;

/**
 * Provides a business-logic with the User entity.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public interface UserService {
    User getUserById(int id, String languageId) throws ServiceException;
    void editUserMainInf(int id, String email, String password, String firstName, String lastName, String photo, String languageId) throws ServiceWrongEmailException, ServiceException;
    void editUserSecondInf(int id, int rating, String status) throws ServiceException;
    void deleteUser(int id) throws ServiceException;
    int getUsersCountByCriteria(String email, String firstName, String lastName,
                                String minDateOfRegistry, String maxDateOfRegistry, Integer minRating, Integer maxRating,
                                List<String> statuses) throws ServiceException;
    List<User> getUsersByCriteria(String email, String firstName, String lastName,
                                  String minDateOfRegistry, String maxDateOfRegistry, Integer minRating, Integer maxRating,
                                  List<String> statuses, int from, int amount) throws ServiceException;
}
