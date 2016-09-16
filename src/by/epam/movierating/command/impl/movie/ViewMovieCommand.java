package by.epam.movierating.command.impl.movie;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.util.LanguageUtil;
import by.epam.movierating.command.util.QueryUtil;
import by.epam.movierating.domain.Movie;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.inter.MovieService;
import by.epam.movierating.service.inter.RatingService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Services the request to show a certain movie by id.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class ViewMovieCommand implements Command {
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/movie/movie.jsp";

    private static final String MOVIES_PAGE = "/Controller?command=movies";

    private static final String USER_ID_SESSION_ATTRIBUTE = "userId";

    private static final String REQUEST_ID_PARAM = "id";

    private static final String SERVICE_ERROR_REQUEST_ATTR = "serviceError";
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";
    private static final String MOVIE_REQUEST_ATTR = "movie";
    private static final String RATING_VALUE_REQUEST_ATTR = "ratingValue";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter(REQUEST_ID_PARAM);
        int id = (idStr == null) ? -1 : Integer.parseInt(idStr);
        if(id == -1){
            response.sendRedirect(MOVIES_PAGE);
            return;
        }

        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            MovieService movieService = serviceFactory.getMovieService();
            Movie movie = movieService.getMovieById(id, languageId);
            if(movie != null){
                request.setAttribute(MOVIE_REQUEST_ATTR, movie);
            }

            HttpSession session = request.getSession(false);
            Integer userId = (session == null) ? null : (Integer) session.getAttribute(USER_ID_SESSION_ATTRIBUTE);
            if(userId != null){
                RatingService ratingService = serviceFactory.getRatingService();
                int ratingValue = ratingService.getRatingValueByMovieAndUser(id, userId);
                if(ratingValue != -1){
                    request.setAttribute(RATING_VALUE_REQUEST_ATTR, ratingValue);
                }
            }
        } catch (ServiceException e) {
            request.setAttribute(SERVICE_ERROR_REQUEST_ATTR, true);
        }
        request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
    }
}
