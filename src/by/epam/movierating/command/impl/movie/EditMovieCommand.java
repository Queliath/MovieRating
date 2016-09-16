package by.epam.movierating.command.impl.movie;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.util.LanguageUtil;
import by.epam.movierating.command.util.QueryUtil;
import by.epam.movierating.domain.Movie;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.inter.MovieService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Services the request from the editing movie form.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class EditMovieCommand implements Command {
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/movie/edit-movie-form.jsp";

    private static final String SESSION_TIMEOUT_PAGE = "/Controller?command=login&cause=timeout";
    private static final String WELCOME_PAGE = "/Controller?command=welcome";

    private static final String USER_STATUS_SESSION_ATTRIBUTE = "userStatus";
    private static final String ADMIN_USER_STATUS = "admin";

    private static final String REQUEST_ID_PARAM = "id";
    private static final String REQUEST_LANG_PARAM = "lang";
    private static final String REQUEST_LANG_PARAM_DEFAULT = "EN";
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
    private static final String SAVE_SUCCESS_REQUEST_ATTR = "saveSuccess";
    private static final String EDITING_LANGUAGE_REQUEST_ATTR = "editingLanguage";
    private static final String MOVIE_REQUEST_ATTR = "movie";

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

        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        String langParam = request.getParameter(REQUEST_LANG_PARAM);
        String editingLanguageId = (langParam == null) ? REQUEST_LANG_PARAM_DEFAULT : langParam;
        request.setAttribute(EDITING_LANGUAGE_REQUEST_ATTR, editingLanguageId);

        String movieFormName = request.getParameter(MOVIE_FORM_NAME_PARAM);
        String movieFormYear = request.getParameter(MOVIE_FORM_YEAR_PARAM);
        String movieFormTagline= request.getParameter(MOVIE_FORM_TAGLINE_PARAM);
        String movieFormBudget = request.getParameter(MOVIE_FORM_BUDGET_PARAM);
        String movieFormPremiere = request.getParameter(MOVIE_FORM_PREMIERE_PARAM);
        String movieFormLasting = request.getParameter(MOVIE_FORM_LASTING_PARAM);
        String movieFormAnnotation = request.getParameter(MOVIE_FORM_ANNOTATION_PARAM);
        String movieFormImage = request.getParameter(MOVIE_FORM_IMAGE_PARAM);
        if(movieFormName != null && movieFormYear != null && movieFormTagline != null && movieFormBudget != null
                && movieFormPremiere != null && movieFormLasting != null && movieFormAnnotation != null && movieFormImage != null) {
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                MovieService movieService = serviceFactory.getMovieService();
                movieService.editMovie(id, movieFormName, Integer.parseInt(movieFormYear), movieFormTagline,
                        Integer.parseInt(movieFormBudget), movieFormPremiere, Integer.parseInt(movieFormLasting),
                        movieFormAnnotation, movieFormImage, editingLanguageId);
                request.setAttribute(SAVE_SUCCESS_REQUEST_ATTR, true);
            } catch (ServiceException e) {
                request.setAttribute(SERVICE_ERROR_REQUEST_ATTR, true);
            }
        }

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            MovieService movieService = serviceFactory.getMovieService();
            Movie movie = movieService.getMovieById(id, editingLanguageId);
            if(movie != null){
                request.setAttribute(MOVIE_REQUEST_ATTR, movie);
            }
        } catch (ServiceException e) {
            request.setAttribute(SERVICE_ERROR_REQUEST_ATTR, true);
        }

        request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
    }
}
