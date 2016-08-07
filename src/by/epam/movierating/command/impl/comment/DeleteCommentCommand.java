package by.epam.movierating.command.impl.comment;

import by.epam.movierating.command.Command;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.interfaces.CommentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Владислав on 06.08.2016.
 */
public class DeleteCommentCommand implements Command {
    private static final int SERVER_ERROR = 500;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        int id = (idStr == null) ? -1 : Integer.parseInt(idStr);

        if(id == -1){
            response.sendRedirect("/Controller?command=welcome");
            return;
        }

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            CommentService commentService = serviceFactory.getCommentService();
            commentService.deleteComment(id);

            HttpSession session = request.getSession(false);
            if(session == null){
                response.sendRedirect("/Controller?command=welcome");
            }
            else {
                Integer userId = (Integer) session.getAttribute("userId");
                if(userId == null){
                    response.sendRedirect("/Controller?command=welcome");
                }
                else {
                    response.sendRedirect("/Controller?command=user&id=" + userId);
                }
            }
        } catch (ServiceException e) {
            response.sendError(SERVER_ERROR);
        }
    }
}
