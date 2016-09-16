package by.epam.movierating.command.impl.comment;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.util.LanguageUtil;
import by.epam.movierating.command.util.QueryUtil;
import by.epam.movierating.domain.Comment;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.inter.CommentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Services the request from the editing comment form.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class EditCommentCommand implements Command {
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/comment/edit-comment-form.jsp";

    private static final String SESSION_TIMEOUT_PAGE = "/Controller?command=login&cause=timeout";
    private static final String WELCOME_PAGE = "/Controller?command=welcome";

    private static final String USER_ID_SESSION_ATTRIBUTE = "userId";

    private static final String REQUEST_ID_PARAM = "id";
    private static final String COMMENT_FORM_TITLE_PARAM = "commentFormTitle";
    private static final String COMMENT_FORM_CONTENT_PARAM = "commentFormContent";

    private static final String SERVICE_ERROR_REQUEST_ATTR = "serviceError";
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";
    private static final String SAVE_SUCCESS_REQUEST_ATTR = "saveSuccess";
    private static final String COMMENT_REQUEST_ATTR = "comment";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter(REQUEST_ID_PARAM);
        int id = (idStr == null) ? -1 : Integer.parseInt(idStr);
        if(id == -1){
            response.sendRedirect(WELCOME_PAGE);
            return;
        }

        HttpSession session = request.getSession(false);
        Integer userId = (session == null) ? null : (Integer) session.getAttribute(USER_ID_SESSION_ATTRIBUTE);
        if(userId == null){
            response.sendRedirect(SESSION_TIMEOUT_PAGE);
            return;
        }
        else {
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                CommentService commentService = serviceFactory.getCommentService();
                Comment comment = commentService.getCommentById(id);
                if(comment == null || comment.getUserId() != userId){
                    response.sendRedirect(WELCOME_PAGE);
                    return;
                }
            } catch (ServiceException e) {
                request.setAttribute(SERVICE_ERROR_REQUEST_ATTR, true);
                return;
            }
        }

        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        String commentFormTitle = request.getParameter(COMMENT_FORM_TITLE_PARAM);
        String commentFormContent = request.getParameter(COMMENT_FORM_CONTENT_PARAM);
        if(commentFormTitle != null && commentFormContent != null){
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                CommentService commentService = serviceFactory.getCommentService();
                commentService.editComment(id, commentFormTitle, commentFormContent);
                request.setAttribute(SAVE_SUCCESS_REQUEST_ATTR, true);
            } catch (ServiceException e) {
                request.setAttribute(SERVICE_ERROR_REQUEST_ATTR, true);
            }
        }

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            CommentService commentService = serviceFactory.getCommentService();
            Comment comment = commentService.getCommentById(id);
            request.setAttribute(COMMENT_REQUEST_ATTR, comment);
        } catch (ServiceException e) {
            request.setAttribute(SERVICE_ERROR_REQUEST_ATTR, true);
        }

        request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
    }
}
