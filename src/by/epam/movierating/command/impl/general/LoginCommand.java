package by.epam.movierating.command.impl.general;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.util.LanguageUtil;
import by.epam.movierating.command.util.QueryUtil;
import by.epam.movierating.domain.User;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.exception.ServiceWrongEmailException;
import by.epam.movierating.service.exception.ServiceWrongPasswordException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.inter.SiteService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Services the request from the log in form.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class LoginCommand implements Command {
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/login.jsp";

    private static final String WELCOME_PAGE = "/Controller?command=welcome";

    private static final String USER_ID_SESSION_ATTRIBUTE = "userId";
    private static final String USER_STATUS_SESSION_ATTRIBUTE = "userStatus";
    private static final String LANGUAGE_ID_SESSION_ATTRIBUTE = "languageId";

    private static final String LOGIN_FORM_EMAIL_PARAM = "loginFormEmail";
    private static final String LOGIN_FORM_PASSWORD_PARAM = "loginFormPassword";
    private static final String CAUSE_REQUEST_PARAM = "cause";
    private static final String TIMEOUT_CAUSE_REQUEST_PARAM = "timeout";

    private static final String SERVICE_ERROR_REQUEST_ATTR = "serviceError";
    private static final String WRONG_EMAIL_REQUEST_ATTR = "wrongEmail";
    private static final String WRONG_PASSWORD_REQUEST_ATTR = "wrongPassword";
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";
    private static final String TIMEOUT_REQUEST_ATTR = "timeout";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String loginFormEmail = request.getParameter(LOGIN_FORM_EMAIL_PARAM);
        String loginFormPassword = request.getParameter(LOGIN_FORM_PASSWORD_PARAM);

        if(loginFormEmail != null && loginFormPassword != null){
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                SiteService siteService = serviceFactory.getSiteService();
                User user = siteService.login(loginFormEmail, loginFormPassword);
                HttpSession session = request.getSession(true);
                session.setAttribute(USER_ID_SESSION_ATTRIBUTE, user.getId());
                session.setAttribute(USER_STATUS_SESSION_ATTRIBUTE, user.getStatus());
                session.setAttribute(LANGUAGE_ID_SESSION_ATTRIBUTE, user.getLanguageId());
                response.sendRedirect(WELCOME_PAGE);
            } catch (ServiceWrongEmailException e) {
                String languageId = LanguageUtil.getLanguageId(request);
                request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);
                request.setAttribute(WRONG_EMAIL_REQUEST_ATTR, true);
                request.setAttribute(LOGIN_FORM_EMAIL_PARAM, loginFormEmail);
                request.setAttribute(LOGIN_FORM_PASSWORD_PARAM, loginFormPassword);
                request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
            } catch (ServiceWrongPasswordException e) {
                String languageId = LanguageUtil.getLanguageId(request);
                request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);
                request.setAttribute(WRONG_PASSWORD_REQUEST_ATTR, true);
                request.setAttribute(LOGIN_FORM_EMAIL_PARAM, loginFormEmail);
                request.setAttribute(LOGIN_FORM_PASSWORD_PARAM, loginFormPassword);
                request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
            } catch (ServiceException e) {
                String languageId = LanguageUtil.getLanguageId(request);
                request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);
                request.setAttribute(SERVICE_ERROR_REQUEST_ATTR, true);
                request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
            }
        }
        else {
            String cause = request.getParameter(CAUSE_REQUEST_PARAM);
            if(cause != null){
                switch (cause){
                    case TIMEOUT_CAUSE_REQUEST_PARAM:
                        request.setAttribute(TIMEOUT_REQUEST_ATTR, true);
                        break;
                }
            }

            QueryUtil.saveCurrentQueryToSession(request);
            String languageId = LanguageUtil.getLanguageId(request);
            request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
        }
    }
}
