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
import java.util.ArrayList;
import java.util.List;

/**
 * Services the request from the countries list page.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class ViewCountriesListCommand implements Command {
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/country/countries.jsp";

    private static final String SESSION_TIMEOUT_PAGE = "/Controller?command=login&cause=timeout";

    private static final String USER_STATUS_SESSION_ATTRIBUTE = "userStatus";
    private static final String ADMIN_USER_STATUS = "admin";

    private static final String PAGE_REQUEST_PARAM = "page";
    private static final String MOVIE_REQUEST_PARAM = "movie";

    private static final String SERVICE_ERROR_REQUEST_ATTR = "serviceError";
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";
    private static final String MOVIE_ID_REQUEST_ATTR = "movieId";
    private static final String COUNTRIES_COUNT_REQUEST_ATTR = "countriesCount";
    private static final String COUNTRIES_REQUEST_ATTR = "countries";
    private static final String COUNTRIES_FROM_REQUEST_ATTR = "countriesFrom";
    private static final String COUNTRIES_TO_REQUEST_ATTR = "countriesTo";
    private static final String PAGINATION_REQUEST_ATTR = "pagination";
    private static final String ACTIVE_PAGE_REQUEST_ATTR = "activePage";

    private static final int COUNTRIES_PER_PAGE = 10;

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

        String pageStr = request.getParameter(PAGE_REQUEST_PARAM);
        int page = (pageStr == null) ? 1 : Integer.parseInt(pageStr);
        String movieParam = request.getParameter(MOVIE_REQUEST_PARAM);
        Integer movieId = (movieParam == null || movieParam.isEmpty()) ? null : Integer.parseInt(movieParam);

        request.setAttribute(MOVIE_ID_REQUEST_ATTR, movieId);

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            CountryService countryService = serviceFactory.getCountryService();

            int countriesCount = countryService.getCountriesCount();
            request.setAttribute(COUNTRIES_COUNT_REQUEST_ATTR, countriesCount);

            int from = (page - 1) * COUNTRIES_PER_PAGE;
            List<Country> countries = countryService.getCountries(from, COUNTRIES_PER_PAGE, languageId);
            request.setAttribute(COUNTRIES_REQUEST_ATTR, countries);
            request.setAttribute(COUNTRIES_FROM_REQUEST_ATTR, from + 1);
            request.setAttribute(COUNTRIES_TO_REQUEST_ATTR, from + countries.size());

            List<Integer> pagination = new ArrayList<>();
            for(int i = 0; i < countriesCount; i += COUNTRIES_PER_PAGE){
                int pageNumber = (i / COUNTRIES_PER_PAGE) + 1;
                pagination.add(pageNumber);
            }
            if(pagination.size() > 1){
                request.setAttribute(PAGINATION_REQUEST_ATTR, pagination);
                request.setAttribute(ACTIVE_PAGE_REQUEST_ATTR, page);
            }
        } catch (ServiceException e) {
            request.setAttribute(SERVICE_ERROR_REQUEST_ATTR, true);
        }

        request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
    }
}
