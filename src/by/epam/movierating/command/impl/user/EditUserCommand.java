package by.epam.movierating.command.impl.user;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.util.LanguageUtil;
import by.epam.movierating.command.util.QueryUtil;
import by.epam.movierating.domain.User;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.exception.ServiceWrongEmailException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.inter.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Services the request from the editing user form.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class EditUserCommand implements Command {
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/user/user-form.jsp";

    private static final String SESSION_TIMEOUT_PAGE = "/Controller?command=login&cause=timeout";
    private static final String WELCOME_PAGE = "/Controller?command=welcome";

    private static final String USER_ID_SESSION_ATTRIBUTE = "userId";
    private static final String USER_STATUS_SESSION_ATTRIBUTE = "userStatus";
    private static final String LANGUAGE_ID_SESSION_ATTRIBUTE = "languageId";
    private static final String ADMIN_USER_STATUS = "admin";

    private static final String REQUEST_ID_PARAM = "id";
    private static final String USER_FORM_EMAIL_PARAM = "userFormEmail";
    private static final String USER_FORM_PASSWORD_PARAM = "userFormPassword";
    private static final String USER_FORM_FIRST_NAME_PARAM = "userFormFirstName";
    private static final String USER_FORM_LAST_NAME_PARAM = "userFormLastName";
    private static final String USER_FORM_PHOTO_PARAM = "userFormPhoto";
    private static final String USER_FORM_LANGUAGE_ID_PARAM = "userFormLanguageId";
    private static final String USER_FORM_RATING_PARAM = "userFormRating";
    private static final String USER_FORM_STATUS_PARAM = "userFormStatus";

    private static final String SERVICE_ERROR_REQUEST_ATTR = "serviceError";
    private static final String WRONG_EMAIL_REQUEST_ATTR = "wrongEmail";
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";
    private static final String SAVE_SUCCESS_REQUEST_ATTR = "saveSuccess";
    private static final String USER_REQUEST_ATTR = "user";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter(REQUEST_ID_PARAM);
        int id = (idStr == null) ? -1 : Integer.parseInt(idStr);
        if(id == -1){
            response.sendRedirect(WELCOME_PAGE);
            return;
        }

        HttpSession session = request.getSession(false);
        Integer userId = (session == null) ? null : (Integer) session.getAttribute(USER_ID_SESSION_ATTRIBUTE);
        String userStatus = (session == null) ? null : (String) session.getAttribute(USER_STATUS_SESSION_ATTRIBUTE);
        if(userId == null || userStatus == null){
            response.sendRedirect(SESSION_TIMEOUT_PAGE);
            return;
        }
        else {
            if(userId != id && !userStatus.equals(ADMIN_USER_STATUS)){
                response.sendRedirect(WELCOME_PAGE);
                return;
            }
        }

        String userFormEmail = request.getParameter(USER_FORM_EMAIL_PARAM);
        String userFormPassword = request.getParameter(USER_FORM_PASSWORD_PARAM);
        String userFormFirstName = request.getParameter(USER_FORM_FIRST_NAME_PARAM);
        String userFormLastName = request.getParameter(USER_FORM_LAST_NAME_PARAM);
        String userFormPhoto = request.getParameter(USER_FORM_PHOTO_PARAM);
        String userFormLanguageId = request.getParameter(USER_FORM_LANGUAGE_ID_PARAM);
        String userFormRating = request.getParameter(USER_FORM_RATING_PARAM);
        String userFormStatus = request.getParameter(USER_FORM_STATUS_PARAM);

        if(userFormEmail != null && userFormPassword != null && userFormFirstName != null && userFormLastName != null && userFormPhoto != null && userFormLanguageId != null){
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                UserService userService = serviceFactory.getUserService();
                userService.editUserMainInf(id, userFormEmail, userFormPassword, userFormFirstName, userFormLastName, userFormPhoto, userFormLanguageId);
                request.setAttribute(SAVE_SUCCESS_REQUEST_ATTR, true);
                session.setAttribute(LANGUAGE_ID_SESSION_ATTRIBUTE, userFormLanguageId);
            } catch (ServiceWrongEmailException e) {
                request.setAttribute(WRONG_EMAIL_REQUEST_ATTR, true);
            } catch (ServiceException e) {
                request.setAttribute(SERVICE_ERROR_REQUEST_ATTR, true);
            }
        }
        if(userFormRating != null && userFormStatus != null){
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                UserService userService = serviceFactory.getUserService();
                userService.editUserSecondInf(id, Integer.parseInt(userFormRating), userFormStatus);
                request.setAttribute(SAVE_SUCCESS_REQUEST_ATTR, true);
            } catch (ServiceException e) {
                request.setAttribute(SERVICE_ERROR_REQUEST_ATTR, true);
            }
        }

        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserService userService = serviceFactory.getUserService();
            User user = userService.getUserById(id, languageId);
            if(user != null){
                request.setAttribute(USER_REQUEST_ATTR, user);
            }
        } catch (ServiceException e) {
            request.setAttribute(SERVICE_ERROR_REQUEST_ATTR, true);
        }
        request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
    }
}
