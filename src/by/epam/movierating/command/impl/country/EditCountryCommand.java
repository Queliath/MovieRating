package by.epam.movierating.command.impl.country;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.util.LanguageUtil;
import by.epam.movierating.command.util.QueryUtil;
import by.epam.movierating.domain.Country;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.inter.CountryService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Services the request from the editing country form.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class EditCountryCommand implements Command {
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/country/edit-country-form.jsp";

    private static final String SESSION_TIMEOUT_PAGE = "/Controller?command=login&cause=timeout";
    private static final String WELCOME_PAGE = "/Controller?command=welcome";

    private static final String USER_STATUS_SESSION_ATTRIBUTE = "userStatus";
    private static final String ADMIN_USER_STATUS = "admin";

    private static final String REQUEST_ID_PARAM = "id";
    private static final String REQUEST_LANG_PARAM = "lang";
    private static final String REQUEST_LANG_PARAM_DEFAULT = "EN";
    private static final String COUNTRY_FORM_NAME_PARAM = "countryFormName";
    private static final String COUNTRY_FORM_POSITION_PARAM = "countryFormPosition";

    private static final String SERVICE_ERROR_REQUEST_ATTR = "serviceError";
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";
    private static final String SAVE_SUCCESS_REQUEST_ATTR = "saveSuccess";
    private static final String EDITING_LANGUAGE_REQUEST_ATTR = "editingLanguage";
    private static final String COUNTRY_REQUEST_ATTR = "country";

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

        String countryFormName = request.getParameter(COUNTRY_FORM_NAME_PARAM);
        String countryFormPosition = request.getParameter(COUNTRY_FORM_POSITION_PARAM);
        if(countryFormName != null && countryFormPosition != null){
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                CountryService countryService = serviceFactory.getCountryService();
                countryService.editCountry(id, countryFormName, Integer.parseInt(countryFormPosition), editingLanguageId);
                request.setAttribute(SAVE_SUCCESS_REQUEST_ATTR, true);
            } catch (ServiceException e) {
                request.setAttribute(SERVICE_ERROR_REQUEST_ATTR, true);
            }
        }

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            CountryService countryService = serviceFactory.getCountryService();
            Country country = countryService.getCountryById(id, editingLanguageId);
            if(country != null){
                request.setAttribute(COUNTRY_REQUEST_ATTR, country);
            }
        } catch (ServiceException e) {
            request.setAttribute(SERVICE_ERROR_REQUEST_ATTR, true);
        }

        request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
    }
}
