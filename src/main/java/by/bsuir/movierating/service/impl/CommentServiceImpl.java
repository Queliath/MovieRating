package by.bsuir.movierating.service.impl;

import by.bsuir.movierating.dao.MovieDAO;
import by.bsuir.movierating.dao.UserDAO;
import by.bsuir.movierating.domain.Comment;
import by.bsuir.movierating.domain.Movie;
import by.bsuir.movierating.domain.User;
import by.bsuir.movierating.service.CommentService;
import by.bsuir.movierating.dao.CommentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Provides a business-logic with the Comment entity.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
@Service("commentService")
public class CommentServiceImpl implements CommentService {
    private UserDAO userDAO;
    private MovieDAO movieDAO;
    private CommentDAO commentDAO;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    public void setMovieDAO(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    @Autowired
    public void setCommentDAO(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }

    /**
     * Returns a recent added comments.
     *
     * @param amount a needed amount of comments
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a recent added comments
     */
    @Override
    public List<Comment> getRecentAddedComments(int amount, String languageId) {
        List<Comment> comments = commentDAO.getRecentAddedComments(amount, languageId);

        for(Comment comment : comments){
            User user = userDAO.getUserById(comment.getUserId());
            comment.setUser(user);

            Movie movie = movieDAO.getMovieById(comment.getMovieId(), languageId);
            comment.setMovie(movie);
        }
        return comments;
    }

    /**
     * Adds a comment to the data storage.
     *
     * @param title a title of the comment
     * @param content a content of the comment
     * @param movieId an id of the movie to which the comment belongs
     * @param userId an id of the user to which the comment belongs
     * @param languageId a language id like 'EN', "RU' etc.
     */
    @Override
    public void addComment(String title, String content, int movieId, int userId, String languageId) {
        Comment comment = new Comment();
        comment.setTitle(title);
        comment.setContent(content);
        comment.setDateOfPublication(new Date());
        comment.setMovieId(movieId);
        comment.setUserId(userId);

        commentDAO.addComment(comment, languageId);
    }

    /**
     * Edits an already existing comment.
     *
     * @param id an id of the needed comment
     * @param title a new title of the comment
     * @param content a new content of the comment
     */
    @Override
    public void editComment(int id, String title, String content) {
        Comment comment = commentDAO.getCommentById(id);
        comment.setTitle(title);
        comment.setContent(content);

        commentDAO.updateComment(comment);
    }

    /**
     * Returns a concrete comment by id.
     *
     * @param id an id of a needed comment
     * @return a concrete comment by id
     */
    @Override
    public Comment getCommentById(int id) {
        return commentDAO.getCommentById(id);
    }

    /**
     * Deletes an existing comment from the data storage.
     *
     * @param id an id of the deleting comment
     */
    @Override
    public void deleteComment(int id) {
        commentDAO.deleteComment(id);
    }
}
