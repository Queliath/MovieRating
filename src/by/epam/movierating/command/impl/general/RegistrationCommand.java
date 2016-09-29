package by.epam.movierating.command.impl.general;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.util.LanguageUtil;
import by.epam.movierating.command.util.QueryUtil;
import by.epam.movierating.domain.User;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.exception.ServiceWrongEmailException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.inter.SiteService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Services the request from the registration form.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class RegistrationCommand implements Command {
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/registration.jsp";

    private static final String LANGUAGE_ID_SESSION_ATTRIBUTE = "languageId";

    private static final String REGISTRATION_FORM_EMAIL_PARAM = "registrationFormEmail";
    private static final String REGISTRATION_FORM_PASSWORD_PARAM = "registrationFormPassword";
    private static final String REGISTRATION_FORM_FIRST_NAME_PARAM = "registrationFormFirstName";
    private static final String REGISTRATION_FORM_LAST_NAME_PARAM = "registrationFormLastName";

    private static final String REGISTRATION_SUCCESS_REQUEST_ATTR = "registrationSuccess";
    private static final String SERVICE_ERROR_REQUEST_ATTR = "serviceError";
    private static final String WRONG_EMAIL_REQUEST_ATTR = "wrongEmail";
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String languageId = LanguageUtil.getLanguageId(request);
        String registrationFormEmail = request.getParameter(REGISTRATION_FORM_EMAIL_PARAM);
        String registrationFormPassword = request.getParameter(REGISTRATION_FORM_PASSWORD_PARAM);
        String registrationFormFirstName = request.getParameter(REGISTRATION_FORM_FIRST_NAME_PARAM);
        String registrationFormLastName = request.getParameter(REGISTRATION_FORM_LAST_NAME_PARAM);

        if(registrationFormEmail != null && registrationFormPassword != null &&
                registrationFormFirstName != null && registrationFormLastName != null){
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                SiteService siteService = serviceFactory.getSiteService();
                User user = siteService.registration(registrationFormEmail, registrationFormPassword, registrationFormFirstName, registrationFormLastName, languageId);
                HttpSession session = request.getSession(true);
                session.setAttribute(LANGUAGE_ID_SESSION_ATTRIBUTE, user.getLanguageId());
                request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);
                request.setAttribute(REGISTRATION_SUCCESS_REQUEST_ATTR, true);
            } catch (ServiceWrongEmailException e) {
                request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);
                request.setAttribute(REGISTRATION_FORM_EMAIL_PARAM, registrationFormEmail);
                request.setAttribute(REGISTRATION_FORM_PASSWORD_PARAM, registrationFormPassword);
                request.setAttribute(REGISTRATION_FORM_FIRST_NAME_PARAM, registrationFormFirstName);
                request.setAttribute(REGISTRATION_FORM_LAST_NAME_PARAM, registrationFormLastName);
                request.setAttribute(WRONG_EMAIL_REQUEST_ATTR, true);
            } catch (ServiceException e) {
                request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);
                request.setAttribute(REGISTRATION_FORM_EMAIL_PARAM, registrationFormEmail);
                request.setAttribute(REGISTRATION_FORM_PASSWORD_PARAM, registrationFormPassword);
                request.setAttribute(REGISTRATION_FORM_FIRST_NAME_PARAM, registrationFormFirstName);
                request.setAttribute(REGISTRATION_FORM_LAST_NAME_PARAM, registrationFormLastName);
                request.setAttribute(SERVICE_ERROR_REQUEST_ATTR, true);
            }
        }
        else {
            QueryUtil.saveCurrentQueryToSession(request);
            request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);
        }

        request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
    }
}
