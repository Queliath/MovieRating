package by.epam.movierating.command.impl.country;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.util.LanguageUtil;
import by.epam.movierating.command.util.QueryUtil;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.inter.CountryService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Services the request from the adding country form.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class AddCountryCommand implements Command {
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/country/add-country-form.jsp";

    private static final String SESSION_TIMEOUT_PAGE = "/Controller?command=login&cause=timeout";
    private static final String SUCCESS_REDIRECT_PAGE = "/Controller?command=countries";

    private static final String USER_STATUS_SESSION_ATTRIBUTE = "userStatus";
    private static final String ADMIN_USER_STATUS = "admin";

    private static final String COUNTRY_FORM_NAME_PARAM = "countryFormName";
    private static final String COUNTRY_FORM_POSITION_PARAM = "countryFormPosition";

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

        String countryFormName = request.getParameter(COUNTRY_FORM_NAME_PARAM);
        String countryFormPosition = request.getParameter(COUNTRY_FORM_POSITION_PARAM);
        if(countryFormName != null && countryFormPosition != null){
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                CountryService countryService = serviceFactory.getCountryService();
                countryService.addCountry(countryFormName, Integer.parseInt(countryFormPosition));
                response.sendRedirect(SUCCESS_REDIRECT_PAGE);
                return;
            } catch (ServiceException e) {
                request.setAttribute(SERVICE_ERROR_REQUEST_ATTR, true);
                request.setAttribute(COUNTRY_FORM_NAME_PARAM, countryFormName);
                request.setAttribute(COUNTRY_FORM_POSITION_PARAM, countryFormPosition);
            }
        }

        request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
    }
}
