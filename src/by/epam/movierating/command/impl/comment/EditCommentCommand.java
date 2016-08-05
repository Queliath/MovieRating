package by.epam.movierating.command.impl.comment;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.util.LanguageUtil;
import by.epam.movierating.command.util.QueryUtil;
import by.epam.movierating.domain.Comment;
import by.epam.movierating.domain.User;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.interfaces.CommentService;
import by.epam.movierating.service.interfaces.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by Владислав on 04.08.2016.
 */
public class EditCommentCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        int id = (idStr == null) ? -1 : Integer.parseInt(idStr);
        if(id == -1){
            response.sendRedirect("/Controller?command=welcome");
            return;
        }

        HttpSession session = request.getSession(false);
        Integer userId = (session == null) ? null : (Integer) session.getAttribute("userId");
        if(userId == null){
            response.sendRedirect("/Controller?command=login&cause=timeout");
            return;
        }
        else {
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                CommentService commentService = serviceFactory.getCommentService();
                Comment comment = commentService.getCommentById(id);
                if(comment == null || comment.getUserId() != userId){
                    response.sendRedirect("/Controller?command=welcome");
                    return;
                }
            } catch (ServiceException e) {
                request.setAttribute("serviceError", true);
                return;
            }
        }

        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute("selectedLanguage", languageId);

        String commentFormTitle = request.getParameter("commentFormTitle");
        String commentFormContent = request.getParameter("commentFormContent");
        if(commentFormTitle != null && commentFormContent != null){
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                CommentService commentService = serviceFactory.getCommentService();
                commentService.editComment(id, commentFormTitle, commentFormContent);
                request.setAttribute("saveSuccess", true);
            } catch (ServiceException e) {
                request.setAttribute("serviceError", true);
            }
        }

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            CommentService commentService = serviceFactory.getCommentService();
            Comment comment = commentService.getCommentById(id);
            request.setAttribute("comment", comment);
        } catch (ServiceException e) {
            request.setAttribute("serviceError", true);
        }

        request.getRequestDispatcher("WEB-INF/jsp/comment/comment-form.jsp").forward(request, response);
    }
}
