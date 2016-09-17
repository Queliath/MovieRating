package by.epam.movierating.service.impl;

import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.inter.CommentService;
import org.junit.Test;

/**
 * Tests the methods of CommentServiceImpl class.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class CommentServiceImplTest {
    @Test(expected = ServiceException.class)
    public void getRecentAddedCommentsTest() throws ServiceException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        CommentService commentService = serviceFactory.getCommentService();

        commentService.getRecentAddedComments(-1, "EN");
    }

    @Test(expected = ServiceException.class)
    public void addCommentTest() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        CommentService commentService = serviceFactory.getCommentService();

        commentService.addComment("", "", -1, -1, "EN");
    }

    @Test(expected = ServiceException.class)
    public void editCommentTest() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        CommentService commentService = serviceFactory.getCommentService();

        commentService.editComment(-1, "", "");
    }

    @Test(expected = ServiceException.class)
    public void getCommentByIdTest() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        CommentService commentService = serviceFactory.getCommentService();

        commentService.getCommentById(-1);
    }

    @Test(expected = ServiceException.class)
    public void deleteCommentTest() throws ServiceException{
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        CommentService commentService = serviceFactory.getCommentService();

        commentService.deleteComment(-1);
    }
}
