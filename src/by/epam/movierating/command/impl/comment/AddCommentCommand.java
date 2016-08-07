package by.epam.movierating.command.impl.comment;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.util.LanguageUtil;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.interfaces.CommentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Владислав on 07.08.2016.
 */
public class AddCommentCommand implements Command {
    private static final int SERVER_ERROR = 500;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Integer userId = (session == null) ? null : (Integer) session.getAttribute("userId");
        String userStatus = (session == null) ? null : (String) session.getAttribute("userStatus");
        if(userId == null || userStatus == null){
            response.sendRedirect("/Controller?command=login&cause=timeout");
            return;
        }

        String commentFormTitle = request.getParameter("commentFormTitle");
        String commentFormContent = request.getParameter("commentFormContent");
        String commentFormMovieId = request.getParameter("commentFormMovieId");
        if(commentFormTitle != null && commentFormContent != null && commentFormMovieId != null){
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                CommentService commentService = serviceFactory.getCommentService();

                String languageId = LanguageUtil.getLanguageId(request);
                commentService.addComment(commentFormTitle, commentFormContent, Integer.parseInt(commentFormMovieId), userId, languageId);
                response.sendRedirect("/Controller?command=movie&id=" + Integer.parseInt(commentFormMovieId));
            } catch (ServiceException e) {
                response.sendError(SERVER_ERROR);
            }
        }
        else {
            response.sendRedirect("/Controller?command=welcome");
        }
    }
}
