package by.epam.movierating.dao.impl.mysql;

import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.dao.inter.CommentDAO;
import by.epam.movierating.dao.inter.MovieDAO;
import by.epam.movierating.dao.inter.PoolDAO;
import by.epam.movierating.dao.inter.UserDAO;
import by.epam.movierating.domain.Comment;
import by.epam.movierating.domain.Movie;
import by.epam.movierating.domain.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.GregorianCalendar;
import java.util.List;

/**
 * Tests the methods of MySQLCommentDAO class.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class MySQLCommentDAOTest {
    @Test
    public void addCommentTest(){
        DAOFactory daoFactory = null;
        PoolDAO poolDAO = null;
        MovieDAO movieDAO = null;
        UserDAO userDAO = null;
        CommentDAO commentDAO = null;
        try {
            daoFactory = DAOFactory.getInstance();
            poolDAO = daoFactory.getPoolDAO();
            movieDAO = daoFactory.getMovieDAO();
            userDAO = daoFactory.getUserDAO();
            commentDAO = daoFactory.getCommentDAO();

            poolDAO.init();

            List<Movie> movies = movieDAO.getAllMovies("EN");
            Movie testMovie = movies.get(0);
            List<User> users = userDAO.getAllUsers();
            User testUser = users.get(0);

            Comment expectedComment = new Comment();
            expectedComment.setMovieId(testMovie.getId());
            expectedComment.setUserId(testUser.getId());
            expectedComment.setTitle("Test comment title");
            expectedComment.setContent("Test comment content");
            expectedComment.setDateOfPublication(new GregorianCalendar(2016, 5, 19).getTime());
            commentDAO.addComment(expectedComment, "EN");

            List<Comment> comments = commentDAO.getAllComments("EN");
            Comment actualComment = comments.get(comments.size() - 1);

            commentDAO.deleteComment(actualComment.getId());

            Assert.assertEquals(expectedComment.getMovieId(), actualComment.getMovieId());
            Assert.assertEquals(expectedComment.getUserId(), actualComment.getUserId());
            Assert.assertEquals(expectedComment.getTitle(), actualComment.getTitle());
            Assert.assertEquals(expectedComment.getContent(), actualComment.getContent());
            Assert.assertEquals(expectedComment.getDateOfPublication(), actualComment.getDateOfPublication());
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
    public void updateCommentTest(){
        DAOFactory daoFactory = null;
        PoolDAO poolDAO = null;
        MovieDAO movieDAO = null;
        UserDAO userDAO = null;
        CommentDAO commentDAO = null;
        try{
            daoFactory = DAOFactory.getInstance();
            poolDAO = daoFactory.getPoolDAO();
            movieDAO = daoFactory.getMovieDAO();
            userDAO = daoFactory.getUserDAO();
            commentDAO = daoFactory.getCommentDAO();

            poolDAO.init();

            List<Comment> comments = commentDAO.getAllComments("EN");
            Comment expectedComment = comments.get(0);

            Comment rollbackComment = new Comment();
            rollbackComment.setId(expectedComment.getId());
            rollbackComment.setMovieId(expectedComment.getMovieId());
            rollbackComment.setUserId(expectedComment.getUserId());
            rollbackComment.setTitle(expectedComment.getTitle());
            rollbackComment.setContent(expectedComment.getContent());
            rollbackComment.setDateOfPublication(expectedComment.getDateOfPublication());

            List<Movie> movies = movieDAO.getAllMovies("EN");
            Movie testMovie = movies.get(0);
            List<User> users = userDAO.getAllUsers();
            User testUser = users.get(0);

            expectedComment.setMovieId(testMovie.getId());
            expectedComment.setUserId(testUser.getId());
            expectedComment.setTitle("Test comment title");
            expectedComment.setContent("Test comment content");
            expectedComment.setDateOfPublication(new GregorianCalendar(2016, 5, 19).getTime());
            commentDAO.updateComment(expectedComment);

            Comment actualComment = commentDAO.getCommentById(expectedComment.getId());

            commentDAO.updateComment(rollbackComment);

            Assert.assertEquals(expectedComment.getMovieId(), actualComment.getMovieId());
            Assert.assertEquals(expectedComment.getUserId(), actualComment.getUserId());
            Assert.assertEquals(expectedComment.getTitle(), actualComment.getTitle());
            Assert.assertEquals(expectedComment.getContent(), actualComment.getContent());
            Assert.assertEquals(expectedComment.getDateOfPublication(), actualComment.getDateOfPublication());
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
    public void deleteCommentTest(){
        DAOFactory daoFactory = null;
        PoolDAO poolDAO = null;
        CommentDAO commentDAO = null;
        try{
            daoFactory = DAOFactory.getInstance();
            poolDAO = daoFactory.getPoolDAO();
            commentDAO = daoFactory.getCommentDAO();

            poolDAO.init();

            List<Comment> comments = commentDAO.getAllComments("EN");
            Comment rollbackComment = comments.get(0);

            commentDAO.deleteComment(rollbackComment.getId());

            Comment deletedComment = commentDAO.getCommentById(rollbackComment.getId());

            commentDAO.addComment(rollbackComment, "EN");

            Assert.assertNull(deletedComment);
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
