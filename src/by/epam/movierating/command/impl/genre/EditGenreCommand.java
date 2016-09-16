package by.epam.movierating.command.impl.genre;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.util.LanguageUtil;
import by.epam.movierating.command.util.QueryUtil;
import by.epam.movierating.domain.Genre;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.inter.GenreService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Services the request from the editing genre form.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class EditGenreCommand implements Command {
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/genre/edit-genre-form.jsp";

    private static final String SESSION_TIMEOUT_PAGE = "/Controller?command=login&cause=timeout";
    private static final String WELCOME_PAGE = "/Controller?command=welcome";

    private static final String USER_STATUS_SESSION_ATTRIBUTE = "userStatus";
    private static final String ADMIN_USER_STATUS = "admin";

    private static final String REQUEST_ID_PARAM = "id";
    private static final String REQUEST_LANG_PARAM = "lang";
    private static final String REQUEST_LANG_PARAM_DEFAULT = "EN";
    private static final String GENRE_FORM_NAME_PARAM = "genreFormName";
    private static final String GENRE_FORM_POSITION_PARAM = "genreFormPosition";

    private static final String SERVICE_ERROR_REQUEST_ATTR = "serviceError";
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";
    private static final String SAVE_SUCCESS_REQUEST_ATTR = "saveSuccess";
    private static final String EDITING_LANGUAGE_REQUEST_ATTR = "editingLanguage";
    private static final String GENRE_REQUEST_ATTR = "genre";

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

        String genreFormName = request.getParameter(GENRE_FORM_NAME_PARAM);
        String genreFormPosition = request.getParameter(GENRE_FORM_POSITION_PARAM);
        if(genreFormName != null && genreFormPosition != null){
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                GenreService genreService = serviceFactory.getGenreService();
                genreService.editGenre(id, genreFormName, Integer.parseInt(genreFormPosition), editingLanguageId);
                request.setAttribute(SAVE_SUCCESS_REQUEST_ATTR, true);
            } catch (ServiceException e) {
                request.setAttribute(SERVICE_ERROR_REQUEST_ATTR, true);
            }
        }

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            GenreService genreService = serviceFactory.getGenreService();
            Genre genre = genreService.getGenreById(id, editingLanguageId);
            if(genre != null){
                request.setAttribute(GENRE_REQUEST_ATTR, genre);
            }
        } catch (ServiceException e) {
            request.setAttribute(SERVICE_ERROR_REQUEST_ATTR, true);
        }

        request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
    }
}
