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
 * Services the request to delete the existing relation between the movie and the person.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class DeletePersonFromMovieCommand implements Command {
    private static final int SERVER_ERROR = 500;

    private static final String SESSION_TIMEOUT_PAGE = "/Controller?command=login&cause=timeout";
    private static final String WELCOME_PAGE = "/Controller?command=welcome";
    private static final String MOVIE_SUCCESS_REDIRECT_PAGE = "/Controller?command=movie&id=";
    private static final String PERSON_SUCCESS_REDIRECT_PAGE = "/Controller?command=person&id=";

    private static final String USER_STATUS_SESSION_ATTRIBUTE = "userStatus";
    private static final String ADMIN_USER_STATUS = "admin";

    private static final String ID_REQUEST_PARAM = "id";
    private static final String MOVIE_ID_REQUEST_PARAM = "movieId";
    private static final String PERSON_ID_REQUEST_PARAM = "personId";
    private static final String RELATION_TYPE_REQUEST_PARAM = "relationType";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String userStatus = (session == null) ? null : (String) session.getAttribute(USER_STATUS_SESSION_ATTRIBUTE);
        if(userStatus == null || !userStatus.equals(ADMIN_USER_STATUS)){
            response.sendRedirect(SESSION_TIMEOUT_PAGE);
            return;
        }

        String idParam = request.getParameter(ID_REQUEST_PARAM);
        String movieIdParam = request.getParameter(MOVIE_ID_REQUEST_PARAM);
        String personIdParam = request.getParameter(PERSON_ID_REQUEST_PARAM);
        String relationTypeParam = request.getParameter(RELATION_TYPE_REQUEST_PARAM);
        if(idParam == null || relationTypeParam == null){
            response.sendRedirect(WELCOME_PAGE);
            return;
        }

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            RelationService relationService = serviceFactory.getRelationService();
            if(movieIdParam != null){
                relationService.deletePersonFromMovie(Integer.parseInt(movieIdParam), Integer.parseInt(idParam),
                        Integer.parseInt(relationTypeParam));
                response.sendRedirect(MOVIE_SUCCESS_REDIRECT_PAGE + movieIdParam);
            }
            if(personIdParam != null){
                relationService.deletePersonFromMovie(Integer.parseInt(idParam), Integer.parseInt(personIdParam),
                        Integer.parseInt(relationTypeParam));
                response.sendRedirect(PERSON_SUCCESS_REDIRECT_PAGE + personIdParam);
            }
        } catch (ServiceException e) {
            response.sendError(SERVER_ERROR);
        }
    }
}
