package by.epam.movierating.command.impl.movie;

import by.epam.movierating.command.Command;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.inter.MovieService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Services the request to delete the movie.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class DeleteMovieCommand implements Command {
    private static final int SERVER_ERROR = 500;

    private static final String SESSION_TIMEOUT_PAGE = "/Controller?command=login&cause=timeout";
    private static final String WELCOME_PAGE = "/Controller?command=welcome";
    private static final String SUCCESS_REDIRECT_PAGE = "/Controller?command=movies";

    private static final String USER_STATUS_SESSION_ATTRIBUTE = "userStatus";
    private static final String ADMIN_USER_STATUS = "admin";

    private static final String REQUEST_ID_PARAM = "id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter(REQUEST_ID_PARAM);
        int id = (idStr == null) ? -1 : Integer.parseInt(idStr);

        if(id == -1){
            response.sendRedirect(WELCOME_PAGE);
            return;
        }

        HttpSession session = request.getSession(false);
        String userStatus = (session == null) ? null : (String) session.getAttribute(USER_STATUS_SESSION_ATTRIBUTE);
        if(userStatus == null || !userStatus.equals(ADMIN_USER_STATUS)){
            response.sendRedirect(SESSION_TIMEOUT_PAGE);
            return;
        }

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            MovieService movieService = serviceFactory.getMovieService();
            movieService.deleteMovie(id);
            response.sendRedirect(SUCCESS_REDIRECT_PAGE);
        } catch (ServiceException e) {
            response.sendError(SERVER_ERROR);
        }
    }
}
