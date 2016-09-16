package by.epam.movierating.command.impl.general;

import by.epam.movierating.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Services the request to log out from the system.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class LogoutCommand implements Command {
    private static final String SESSION_USER_ID = "userId";
    private static final String SESSION_USER_STATUS = "userStatus";

    private static final String LOGIN_PAGE_CAUSE_TIMEOUT = "/Controller?command=login&cause=timeout";
    private static final String SESSION_PREV_QUERY = "prevQuery";
    private static final String WELCOME_PAGE = "/Controller?command=welcome";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session == null){
            response.sendRedirect(LOGIN_PAGE_CAUSE_TIMEOUT);
        }
        else {
            session.setAttribute(SESSION_USER_ID, null);
            session.setAttribute(SESSION_USER_STATUS, null);

            String prevQuery = (String) session.getAttribute(SESSION_PREV_QUERY);
            if(prevQuery == null){
                prevQuery = WELCOME_PAGE;
            }
            response.sendRedirect(prevQuery);
        }
    }
}
