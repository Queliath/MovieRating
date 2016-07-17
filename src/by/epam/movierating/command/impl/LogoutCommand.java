package by.epam.movierating.command.impl;

import by.epam.movierating.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Владислав on 17.07.2016.
 */
public class LogoutCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session == null){
            response.sendRedirect("/Controller?command=login&cause=timeout");
        }
        else {
            session.setAttribute("userId", null);
            response.sendRedirect("/Controller?command=welcome");
        }
    }
}
