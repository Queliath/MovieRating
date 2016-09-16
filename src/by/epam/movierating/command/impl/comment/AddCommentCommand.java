package by.epam.movierating.command.impl.comment;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.util.LanguageUtil;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.inter.CommentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Services the request from the adding comment form.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class AddCommentCommand implements Command {
    private static final int SERVER_ERROR = 500;

    private static final String USER_ID_SESSION_ATTRIBUTE = "userId";
    private static final String USER_STATUS_SESSION_ATTRIBUTE = "userStatus";
    private static final String BANNED_USER_STATUS = "banned";

    private static final String SESSION_TIMEOUT_PAGE = "/Controller?command=login&cause=timeout";
    private static final String WELCOME_PAGE = "/Controller?command=welcome";
    private static final String SUCCESS_REDIRECT_PAGE = "/Controller?command=movie&id=";

    private static final String COMMENT_FORM_TITLE_PARAM = "commentFormTitle";
    private static final String COMMENT_FORM_CONTENT_PARAM = "commentFormContent";
    private static final String COMMENT_FORM_MOVIE_ID_PARAM = "commentFormMovieId";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Integer userId = (session == null) ? null : (Integer) session.getAttribute(USER_ID_SESSION_ATTRIBUTE);
        String userStatus = (session == null) ? null : (String) session.getAttribute(USER_STATUS_SESSION_ATTRIBUTE);
        if(userId == null || userStatus == null){
            response.sendRedirect(SESSION_TIMEOUT_PAGE);
            return;
        }
        if(userStatus.equals(BANNED_USER_STATUS)){
            response.sendRedirect(WELCOME_PAGE);
            return;
        }

        String commentFormTitle = request.getParameter(COMMENT_FORM_TITLE_PARAM);
        String commentFormContent = request.getParameter(COMMENT_FORM_CONTENT_PARAM);
        String commentFormMovieId = request.getParameter(COMMENT_FORM_MOVIE_ID_PARAM);
        if(commentFormTitle != null && commentFormContent != null && commentFormMovieId != null){
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                CommentService commentService = serviceFactory.getCommentService();

                String languageId = LanguageUtil.getLanguageId(request);
                commentService.addComment(commentFormTitle, commentFormContent, Integer.parseInt(commentFormMovieId), userId, languageId);
                response.sendRedirect(SUCCESS_REDIRECT_PAGE + Integer.parseInt(commentFormMovieId));
            } catch (ServiceException e) {
                response.sendError(SERVER_ERROR);
            }
        }
        else {
            response.sendRedirect(WELCOME_PAGE);
        }
    }
}
