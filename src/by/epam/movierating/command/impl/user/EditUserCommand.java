package by.epam.movierating.command.impl.user;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.util.LanguageUtil;
import by.epam.movierating.command.util.QueryUtil;
import by.epam.movierating.domain.User;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.interfaces.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Владислав on 04.08.2016.
 */
public class EditUserCommand implements Command {
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
        String userStatus = (session == null) ? null : (String) session.getAttribute("userStatus");
        if(userId == null || userStatus == null){
            response.sendRedirect("/Controller?command=login&cause=timeout");
            return;
        }
        else {
            if(userId != id && !userStatus.equals("admin")){
                response.sendRedirect("/Controller?command=welcome");
                return;
            }
        }

        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute("selectedLanguage", languageId);

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserService userService = serviceFactory.getUserService();
            User user = userService.getUserById(id, languageId);
            if(user != null){
                request.setAttribute("user", user);
            }
        } catch (ServiceException e) {
            request.setAttribute("serviceError", true);
        }
        request.getRequestDispatcher("WEB-INF/jsp/user/user-form.jsp").forward(request, response);
    }
}
