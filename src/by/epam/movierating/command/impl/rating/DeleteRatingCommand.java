package by.epam.movierating.command.impl.rating;

import by.epam.movierating.command.Command;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.inter.RatingService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Владислав on 26.09.2016.
 */
public class DeleteRatingCommand implements Command {
    private static final int SERVER_ERROR = 500;

    private static final String SESSION_TIMEOUT_PAGE = "/Controller?command=login&cause=timeout";
    private static final String WELCOME_PAGE = "/Controller?command=welcome";

    private static final String USER_ID_SESSION_ATTRIBUTE = "userId";
    private static final String USER_STATUS_SESSION_ATTRIBUTE = "userStatus";

    private static final String MOVIE_ID_REQUEST_PARAM = "movieId";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Integer userId = (session == null) ? null : (Integer) session.getAttribute(USER_ID_SESSION_ATTRIBUTE);
        String userStatus = (session == null) ? null : (String) session.getAttribute(USER_STATUS_SESSION_ATTRIBUTE);
        if(userId == null || userStatus == null){
            response.sendRedirect(SESSION_TIMEOUT_PAGE);
            return;
        }

        String movieId = request.getParameter(MOVIE_ID_REQUEST_PARAM);
        if(movieId != null){
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                RatingService ratingService = serviceFactory.getRatingService();
                ratingService.deleteRating(Integer.parseInt(movieId), userId);
            } catch (ServiceException e) {
                response.sendError(SERVER_ERROR);
            }
        }
        else {
            response.sendRedirect(WELCOME_PAGE);
        }
    }
}
