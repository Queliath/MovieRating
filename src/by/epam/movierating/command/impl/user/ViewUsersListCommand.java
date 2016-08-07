package by.epam.movierating.command.impl.user;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.util.LanguageUtil;
import by.epam.movierating.command.util.QueryUtil;
import by.epam.movierating.domain.User;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.interfaces.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 07.08.2016.
 */
public class ViewUsersListCommand implements Command {
    private static final int USERS_PER_PAGE = 10;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String userStatus = (session == null) ? null : (String) session.getAttribute("userStatus");
        if(userStatus == null || !userStatus.equals("admin")){
            response.sendRedirect("/Controller?command=login&cause=timeout");
            return;
        }

        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute("selectedLanguage", languageId);

        String pageStr = request.getParameter("page");
        int page = (pageStr == null) ? 1 : Integer.parseInt(pageStr);
        String searchFormEmailParam = request.getParameter("searchFormEmail");
        String searchFormEmail = (searchFormEmailParam == null || searchFormEmailParam.isEmpty()) ? null : searchFormEmailParam;
        String searchFormFirstNameParam = request.getParameter("searchFormFirstName");
        String searchFormFirstName = (searchFormFirstNameParam == null || searchFormFirstNameParam.isEmpty()) ? null : searchFormFirstNameParam;
        String searchFormLastNameParam = request.getParameter("searchFormLastName");
        String searchFormLastName = (searchFormLastNameParam == null || searchFormLastNameParam.isEmpty()) ? null : searchFormLastNameParam;
        String searchFormMinDateOfRegistryParam = request.getParameter("searchFormMinDateOfRegistry");
        String searchFormMinDateOfRegistry = (searchFormMinDateOfRegistryParam == null || searchFormMinDateOfRegistryParam.isEmpty()) ? null : searchFormMinDateOfRegistryParam;
        String searchFormMaxDateOfRegistryParam = request.getParameter("searchFormMaxDateOfRegistry");
        String searchFormMaxDateOfRegistry = (searchFormMaxDateOfRegistryParam == null || searchFormMaxDateOfRegistryParam.isEmpty()) ? null : searchFormMaxDateOfRegistryParam;
        String searchFormMinRatingParam = request.getParameter("searchFormMinRating");
        Integer searchFormMinRating = (searchFormMinRatingParam == null || searchFormMinRatingParam.isEmpty()) ? null : Integer.parseInt(searchFormMinRatingParam);
        String searchFormMaxRatingParam = request.getParameter("searchFormMaxRating");
        Integer searchFormMaxRating = (searchFormMaxRatingParam == null || searchFormMaxRatingParam.isEmpty()) ? null : Integer.parseInt(searchFormMaxRatingParam);
        String[] searchFormStatusesParams = request.getParameterValues("searchFormStatuses[]");
        List<String> searchFormStatuses = null;
        if(searchFormStatusesParams != null){
            searchFormStatuses = new ArrayList<>();
            for(String statusParam : searchFormStatusesParams){
                searchFormStatuses.add(statusParam);
            }
        }

        request.setAttribute("searchFormEmail", searchFormEmailParam);
        request.setAttribute("searchFormFirstName", searchFormFirstNameParam);
        request.setAttribute("searchFormLastName", searchFormLastNameParam);
        request.setAttribute("searchFormMinDateOfRegistry", searchFormMinDateOfRegistryParam);
        request.setAttribute("searchFormMaxDateOfRegistry", searchFormMaxDateOfRegistryParam);
        request.setAttribute("searchFormMinRating", searchFormMinRatingParam);
        request.setAttribute("searchFormMaxRating", searchFormMaxRatingParam);
        request.setAttribute("searchFormStatuses", searchFormStatuses);

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserService userService = serviceFactory.getUserService();

            int usersCount = userService.getUsersCountByCriteria(searchFormEmail, searchFormFirstName,
                    searchFormLastName, searchFormMinDateOfRegistry, searchFormMaxDateOfRegistry, searchFormMinRating,
                    searchFormMaxRating, searchFormStatuses);
            request.setAttribute("usersCount", usersCount);

            int from = (page - 1) * USERS_PER_PAGE;
            List<User> users = userService.getUsersByCriteria(searchFormEmail, searchFormFirstName,
                    searchFormLastName, searchFormMinDateOfRegistry, searchFormMaxDateOfRegistry, searchFormMinRating,
                    searchFormMaxRating, searchFormStatuses, from, USERS_PER_PAGE);
            request.setAttribute("users", users);
            request.setAttribute("usersFrom", from + 1);
            request.setAttribute("usersTo", from + users.size());

            List<Integer> pagination = new ArrayList<>();
            for(int i = 0; i < usersCount; i += USERS_PER_PAGE){
                int pageNumber = (i / USERS_PER_PAGE) + 1;
                pagination.add(pageNumber);
            }
            if(pagination.size() > 1){
                request.setAttribute("pagination", pagination);
                request.setAttribute("activePage", page);
            }
        } catch (ServiceException e) {
            request.setAttribute("serviceError", true);
        }

        request.getRequestDispatcher("WEB-INF/jsp/user/users.jsp").forward(request, response);
    }
}
