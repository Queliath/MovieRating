package by.epam.movierating.command.impl.relation;

import by.epam.movierating.command.Command;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.inter.RelationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Services the request to delete the existing relation between the movie and the genre.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class DeleteGenreFromMovieCommand implements Command {
    private static final int SERVER_ERROR = 500;

    private static final String SESSION_TIMEOUT_PAGE = "/Controller?command=login&cause=timeout";
    private static final String WELCOME_PAGE = "/Controller?command=welcome";
    private static final String SUCCESS_REDIRECT_PAGE = "/Controller?command=movie&id=";

    private static final String USER_STATUS_SESSION_ATTRIBUTE = "userStatus";
    private static final String ADMIN_USER_STATUS = "admin";

    private static final String MOVIE_ID_REQUEST_PARAM = "movieId";
    private static final String GENRE_ID_REQUEST_PARAM = "id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String userStatus = (session == null) ? null : (String) session.getAttribute(USER_STATUS_SESSION_ATTRIBUTE);
        if(userStatus == null || !userStatus.equals(ADMIN_USER_STATUS)){
            response.sendRedirect(SESSION_TIMEOUT_PAGE);
            return;
        }

        String movieIdParam = request.getParameter(MOVIE_ID_REQUEST_PARAM);
        String genreIdParam = request.getParameter(GENRE_ID_REQUEST_PARAM);
        if(movieIdParam == null || genreIdParam == null){
            response.sendRedirect(WELCOME_PAGE);
            return;
        }

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            RelationService relationService = serviceFactory.getRelationService();
            relationService.deleteGenreFromMovie(Integer.parseInt(movieIdParam), Integer.parseInt(genreIdParam));
            response.sendRedirect(SUCCESS_REDIRECT_PAGE + movieIdParam);
        } catch (ServiceException e) {
            response.sendError(SERVER_ERROR);
        }
    }
}
