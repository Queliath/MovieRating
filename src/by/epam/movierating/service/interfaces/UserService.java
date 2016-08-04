package by.epam.movierating.service.interfaces;

import by.epam.movierating.domain.User;
import by.epam.movierating.service.exception.ServiceException;

/**
 * Created by Владислав on 03.08.2016.
 */
public interface UserService {
    User getUserById(int id, String languageId) throws ServiceException;
    void editUserMainInf(int id, String email, String password, String firstName, String lastName, String photo) throws ServiceException;
    void editUserSecondInf(int id, int rating, String status) throws ServiceException;
}
