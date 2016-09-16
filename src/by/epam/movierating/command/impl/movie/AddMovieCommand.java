package by.epam.movierating.command.impl.movie;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.util.LanguageUtil;
import by.epam.movierating.command.util.QueryUtil;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.inter.MovieService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Services the request from the adding movie form.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class AddMovieCommand implements Command {
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/movie/add-movie-form.jsp";

    private static final String SESSION_TIMEOUT_PAGE = "/Controller?command=login&cause=timeout";
    private static final String SUCCESS_REDIRECT_PAGE = "/Controller?command=movies";

    private static final String USER_STATUS_SESSION_ATTRIBUTE = "userStatus";
    private static final String ADMIN_USER_STATUS = "admin";

    private static final String MOVIE_FORM_NAME_PARAM = "movieFormName";
    private static final String MOVIE_FORM_YEAR_PARAM = "movieFormYear";
    private static final String MOVIE_FORM_TAGLINE_PARAM = "movieFormTagline";
    private static final String MOVIE_FORM_BUDGET_PARAM = "movieFormBudget";
    private static final String MOVIE_FORM_PREMIERE_PARAM = "movieFormPremiere";
    private static final String MOVIE_FORM_LASTING_PARAM = "movieFormLasting";
    private static final String MOVIE_FORM_ANNOTATION_PARAM = "movieFormAnnotation";
    private static final String MOVIE_FORM_IMAGE_PARAM = "movieFormImage";

    private static final String SERVICE_ERROR_REQUEST_ATTR = "serviceError";
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String userStatus = (session == null) ? null : (String) session.getAttribute(USER_STATUS_SESSION_ATTRIBUTE);
        if(userStatus == null || !userStatus.equals(ADMIN_USER_STATUS)){
            response.sendRedirect(SESSION_TIMEOUT_PAGE);
            return;
        }

        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        String movieFormName = request.getParameter(MOVIE_FORM_NAME_PARAM);
        String movieFormYear = request.getParameter(MOVIE_FORM_YEAR_PARAM);
        String movieFormTagline= request.getParameter(MOVIE_FORM_TAGLINE_PARAM);
        String movieFormBudget = request.getParameter(MOVIE_FORM_BUDGET_PARAM);
        String movieFormPremiere = request.getParameter(MOVIE_FORM_PREMIERE_PARAM);
        String movieFormLasting = request.getParameter(MOVIE_FORM_LASTING_PARAM);
        String movieFormAnnotation = request.getParameter(MOVIE_FORM_ANNOTATION_PARAM);
        String movieFormImage = request.getParameter(MOVIE_FORM_IMAGE_PARAM);
        if(movieFormName != null && movieFormYear != null && movieFormTagline != null && movieFormBudget != null
                && movieFormPremiere != null && movieFormLasting != null && movieFormAnnotation != null && movieFormImage != null){
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                MovieService movieService = serviceFactory.getMovieService();
                movieService.addMovie(movieFormName, Integer.parseInt(movieFormYear), movieFormTagline,
                        Integer.parseInt(movieFormBudget), movieFormPremiere, Integer.parseInt(movieFormLasting),
                        movieFormAnnotation, movieFormImage);
                response.sendRedirect(SUCCESS_REDIRECT_PAGE);
                return;
            } catch (ServiceException e) {
                request.setAttribute(SERVICE_ERROR_REQUEST_ATTR, true);
                request.setAttribute(MOVIE_FORM_NAME_PARAM, movieFormName);
                request.setAttribute(MOVIE_FORM_YEAR_PARAM, movieFormYear);
                request.setAttribute(MOVIE_FORM_TAGLINE_PARAM, movieFormTagline);
                request.setAttribute(MOVIE_FORM_BUDGET_PARAM, movieFormBudget);
                request.setAttribute(MOVIE_FORM_PREMIERE_PARAM, movieFormPremiere);
                request.setAttribute(MOVIE_FORM_LASTING_PARAM, movieFormLasting);
                request.setAttribute(MOVIE_FORM_ANNOTATION_PARAM, movieFormAnnotation);
                request.setAttribute(MOVIE_FORM_IMAGE_PARAM, movieFormImage);
            }
        }

        request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
    }
}
