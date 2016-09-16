package by.epam.movierating.command.impl.user;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.util.LanguageUtil;
import by.epam.movierating.command.util.QueryUtil;
import by.epam.movierating.domain.User;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.inter.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Services the request from the users list page.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class ViewUsersListCommand implements Command {
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/user/users.jsp";

    private static final String SESSION_TIMEOUT_PAGE = "/Controller?command=login&cause=timeout";

    private static final String USER_STATUS_SESSION_ATTRIBUTE = "userStatus";
    private static final String ADMIN_USER_STATUS = "admin";

    private static final String PAGE_REQUEST_PARAM = "page";
    private static final String SEARCH_FORM_EMAIL_PARAM = "searchFormEmail";
    private static final String SEARCH_FORM_FIRST_NAME_PARAM = "searchFormFirstName";
    private static final String SEARCH_FORM_LAST_NAME_PARAM = "searchFormLastName";
    private static final String SEARCH_FORM_MIN_DATE_OF_REGISTRY_PARAM = "searchFormMinDateOfRegistry";
    private static final String SEARCH_FORM_MAX_DATE_OF_REGISTRY_PARAM = "searchFormMaxDateOfRegistry";
    private static final String SEARCH_FORM_MIN_RATING_PARAM = "searchFormMinRating";
    private static final String SEARCH_FORM_MAX_RATING_PARAM = "searchFormMaxRating";
    private static final String SEARCH_FORM_STATUSES_PARAM = "searchFormStatuses[]";

    private static final String SERVICE_ERROR_REQUEST_ATTR = "serviceError";
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";
    private static final String SEARCH_FORM_STATUSES_REQUEST_ATTR = "searchFormStatuses";
    private static final String USERS_COUNT_REQUEST_ATTR = "usersCount";
    private static final String USERS_REQUEST_ATTR = "users";
    private static final String USERS_FROM_REQUEST_ATTR = "usersFrom";
    private static final String USERS_TO_REQUEST_ATTR = "usersTo";
    private static final String PAGINATION_REQUEST_ATTR = "pagination";
    private static final String ACTIVE_PAGE_REQUEST_ATTR = "activePage";

    private static final int USERS_PER_PAGE = 10;

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
        String searchFormEmailParam = request.getParameter(SEARCH_FORM_EMAIL_PARAM);
        String searchFormEmail = (searchFormEmailParam == null || searchFormEmailParam.isEmpty()) ? null : searchFormEmailParam;
        String searchFormFirstNameParam = request.getParameter(SEARCH_FORM_FIRST_NAME_PARAM);
        String searchFormFirstName = (searchFormFirstNameParam == null || searchFormFirstNameParam.isEmpty()) ? null : searchFormFirstNameParam;
        String searchFormLastNameParam = request.getParameter(SEARCH_FORM_LAST_NAME_PARAM);
        String searchFormLastName = (searchFormLastNameParam == null || searchFormLastNameParam.isEmpty()) ? null : searchFormLastNameParam;
        String searchFormMinDateOfRegistryParam = request.getParameter(SEARCH_FORM_MIN_DATE_OF_REGISTRY_PARAM);
        String searchFormMinDateOfRegistry = (searchFormMinDateOfRegistryParam == null || searchFormMinDateOfRegistryParam.isEmpty()) ? null : searchFormMinDateOfRegistryParam;
        String searchFormMaxDateOfRegistryParam = request.getParameter(SEARCH_FORM_MAX_DATE_OF_REGISTRY_PARAM);
        String searchFormMaxDateOfRegistry = (searchFormMaxDateOfRegistryParam == null || searchFormMaxDateOfRegistryParam.isEmpty()) ? null : searchFormMaxDateOfRegistryParam;
        String searchFormMinRatingParam = request.getParameter(SEARCH_FORM_MIN_RATING_PARAM);
        Integer searchFormMinRating = (searchFormMinRatingParam == null || searchFormMinRatingParam.isEmpty()) ? null : Integer.parseInt(searchFormMinRatingParam);
        String searchFormMaxRatingParam = request.getParameter(SEARCH_FORM_MAX_RATING_PARAM);
        Integer searchFormMaxRating = (searchFormMaxRatingParam == null || searchFormMaxRatingParam.isEmpty()) ? null : Integer.parseInt(searchFormMaxRatingParam);
        String[] searchFormStatusesParams = request.getParameterValues(SEARCH_FORM_STATUSES_PARAM);
        List<String> searchFormStatuses = null;
        if(searchFormStatusesParams != null){
            searchFormStatuses = new ArrayList<>();
            for(String statusParam : searchFormStatusesParams){
                searchFormStatuses.add(statusParam);
            }
        }

        request.setAttribute(SEARCH_FORM_EMAIL_PARAM, searchFormEmailParam);
        request.setAttribute(SEARCH_FORM_FIRST_NAME_PARAM, searchFormFirstNameParam);
        request.setAttribute(SEARCH_FORM_LAST_NAME_PARAM, searchFormLastNameParam);
        request.setAttribute(SEARCH_FORM_MIN_DATE_OF_REGISTRY_PARAM, searchFormMinDateOfRegistryParam);
        request.setAttribute(SEARCH_FORM_MAX_DATE_OF_REGISTRY_PARAM, searchFormMaxDateOfRegistryParam);
        request.setAttribute(SEARCH_FORM_MIN_RATING_PARAM, searchFormMinRatingParam);
        request.setAttribute(SEARCH_FORM_MAX_RATING_PARAM, searchFormMaxRatingParam);
        request.setAttribute(SEARCH_FORM_STATUSES_REQUEST_ATTR, searchFormStatuses);

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserService userService = serviceFactory.getUserService();

            int usersCount = userService.getUsersCountByCriteria(searchFormEmail, searchFormFirstName,
                    searchFormLastName, searchFormMinDateOfRegistry, searchFormMaxDateOfRegistry, searchFormMinRating,
                    searchFormMaxRating, searchFormStatuses);
            request.setAttribute(USERS_COUNT_REQUEST_ATTR, usersCount);

            int from = (page - 1) * USERS_PER_PAGE;
            List<User> users = userService.getUsersByCriteria(searchFormEmail, searchFormFirstName,
                    searchFormLastName, searchFormMinDateOfRegistry, searchFormMaxDateOfRegistry, searchFormMinRating,
                    searchFormMaxRating, searchFormStatuses, from, USERS_PER_PAGE);
            request.setAttribute(USERS_REQUEST_ATTR, users);
            request.setAttribute(USERS_FROM_REQUEST_ATTR, from + 1);
            request.setAttribute(USERS_TO_REQUEST_ATTR, from + users.size());

            List<Integer> pagination = new ArrayList<>();
            for(int i = 0; i < usersCount; i += USERS_PER_PAGE){
                int pageNumber = (i / USERS_PER_PAGE) + 1;
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
