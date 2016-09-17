package by.epam.movierating.dao.impl.mysql;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.dao.inter.CommentDAO;
import by.epam.movierating.dao.inter.PoolDAO;
import by.epam.movierating.dao.inter.RatingDAO;
import by.epam.movierating.dao.inter.UserDAO;
import by.epam.movierating.domain.Comment;
import by.epam.movierating.domain.Rating;
import by.epam.movierating.domain.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.GregorianCalendar;
import java.util.List;

/**
 * Tests the methods of MySQLUserDAO class.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class MySQLUserDAOTest {
    @Test
    public void addUserTest(){
        DAOFactory daoFactory = null;
        PoolDAO poolDAO = null;
        UserDAO userDAO = null;
        try {
            daoFactory = DAOFactory.getInstance();
            poolDAO = daoFactory.getPoolDAO();
            userDAO = daoFactory.getUserDAO();

            poolDAO.init();

            User expectedUser = new User();
            expectedUser.setEmail("Test user email");
            expectedUser.setPassword("Test user password");
            expectedUser.setFirstName("Test user first name");
            expectedUser.setLastName("Test user last name");
            expectedUser.setDateOfRegistry(new GregorianCalendar(2016, 5, 19).getTime());
            expectedUser.setPhoto("Test user photo");
            expectedUser.setRating(10);
            expectedUser.setStatus("normal");
            expectedUser.setLanguageId("EN");
            userDAO.addUser(expectedUser);

            List<User> users = userDAO.getAllUsers();
            User actualUser = users.get(users.size() - 1);

            userDAO.deleteUser(actualUser.getId());

            Assert.assertEquals(expectedUser.getEmail(), actualUser.getEmail());
            Assert.assertEquals(expectedUser.getPassword(), actualUser.getPassword());
            Assert.assertEquals(expectedUser.getFirstName(), actualUser.getFirstName());
            Assert.assertEquals(expectedUser.getLastName(), actualUser.getLastName());
            Assert.assertEquals(expectedUser.getDateOfRegistry(), actualUser.getDateOfRegistry());
            Assert.assertEquals(expectedUser.getPhoto(), actualUser.getPhoto());
            Assert.assertEquals(expectedUser.getRating(), actualUser.getRating());
            Assert.assertEquals(expectedUser.getStatus(), actualUser.getStatus());
            Assert.assertEquals(expectedUser.getLanguageId(), actualUser.getLanguageId());
        } catch (DAOException e) {
            e.printStackTrace();
        } finally {
            if(poolDAO != null){
                try {
                    poolDAO.destroy();
                } catch (DAOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void updateUserTest(){
        DAOFactory daoFactory = null;
        PoolDAO poolDAO = null;
        UserDAO userDAO = null;
        try{
            daoFactory = DAOFactory.getInstance();
            poolDAO = daoFactory.getPoolDAO();
            userDAO = daoFactory.getUserDAO();

            poolDAO.init();

            List<User> users = userDAO.getAllUsers();
            User expectedUser = users.get(0);

            User rollbackUser = new User();
            rollbackUser.setId(expectedUser.getId());
            rollbackUser.setEmail(expectedUser.getEmail());
            rollbackUser.setPassword(expectedUser.getPassword());
            rollbackUser.setFirstName(expectedUser.getFirstName());
            rollbackUser.setLastName(expectedUser.getLastName());
            rollbackUser.setDateOfRegistry(expectedUser.getDateOfRegistry());
            rollbackUser.setPhoto(expectedUser.getPhoto());
            rollbackUser.setRating(expectedUser.getRating());
            rollbackUser.setStatus(expectedUser.getStatus());
            rollbackUser.setLanguageId(expectedUser.getLanguageId());

            expectedUser.setEmail("Test user email");
            expectedUser.setPassword("Test user password");
            expectedUser.setFirstName("Test user first name");
            expectedUser.setLastName("Test user last name");
            expectedUser.setDateOfRegistry(new GregorianCalendar(2016, 5, 19).getTime());
            expectedUser.setPhoto("Test user photo");
            expectedUser.setRating(10);
            expectedUser.setStatus("normal");
            expectedUser.setLanguageId("RU");
            userDAO.updateUser(expectedUser);

            User actualUser = userDAO.getUserById(expectedUser.getId());

            userDAO.updateUser(rollbackUser);

            Assert.assertEquals(expectedUser.getEmail(), actualUser.getEmail());
            Assert.assertEquals(expectedUser.getPassword(), actualUser.getPassword());
            Assert.assertEquals(expectedUser.getFirstName(), actualUser.getFirstName());
            Assert.assertEquals(expectedUser.getLastName(), actualUser.getLastName());
            Assert.assertEquals(expectedUser.getDateOfRegistry(), actualUser.getDateOfRegistry());
            Assert.assertEquals(expectedUser.getPhoto(), actualUser.getPhoto());
            Assert.assertEquals(expectedUser.getRating(), actualUser.getRating());
            Assert.assertEquals(expectedUser.getStatus(), actualUser.getStatus());
            Assert.assertEquals(expectedUser.getLanguageId(), actualUser.getLanguageId());
        } catch (DAOException e) {
            e.printStackTrace();
        } finally {
            if(poolDAO != null){
                try {
                    poolDAO.destroy();
                } catch (DAOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void deleteUserTest(){
        DAOFactory daoFactory = null;
        PoolDAO poolDAO = null;
        UserDAO userDAO = null;
        CommentDAO commentDAO = null;
        RatingDAO ratingDAO = null;
        try {
            daoFactory = DAOFactory.getInstance();
            poolDAO = daoFactory.getPoolDAO();
            userDAO = daoFactory.getUserDAO();
            commentDAO = daoFactory.getCommentDAO();
            ratingDAO = daoFactory.getRatingDAO();

            poolDAO.init();

            List<User> users = userDAO.getAllUsers();
            User rollbackUser = users.get(0);
            List<Comment> rollbackComments = commentDAO.getCommentsByUser(rollbackUser.getId(), "EN");
            List<Rating> rollbackRatings = ratingDAO.getRatingsByUser(rollbackUser.getId());

            userDAO.deleteUser(rollbackUser.getId());

            User deletedUser = userDAO.getUserById(rollbackUser.getId());

            userDAO.addUser(rollbackUser);
            users = userDAO.getAllUsers();
            rollbackUser = users.get(users.size() - 1);
            for(Comment comment : rollbackComments){
                comment.setUserId(rollbackUser.getId());
                commentDAO.addComment(comment, "EN");
            }
            for(Rating rating : rollbackRatings){
                rating.setUserId(rollbackUser.getId());
                ratingDAO.addRating(rating);
            }

            Assert.assertNull(deletedUser);
        } catch (DAOException e) {
            e.printStackTrace();
        } finally {
            if(poolDAO != null){
                try {
                    poolDAO.destroy();
                } catch (DAOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
