package by.epam.movierating.command.impl.general;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.util.LanguageUtil;
import by.epam.movierating.command.util.QueryUtil;
import by.epam.movierating.domain.User;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.exception.ServiceWrongEmailException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.interfaces.SiteService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Владислав on 15.07.2016.
 */
public class RegistrationCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String registrationFormEmail = request.getParameter("registrationFormEmail");
        String registrationFormPassword = request.getParameter("registrationFormPassword");
        String registrationFormFirstName = request.getParameter("registrationFormFirstName");
        String registrationFormLastName = request.getParameter("registrationFormLastName");

        if(registrationFormEmail != null && registrationFormPassword != null &&
                registrationFormFirstName != null && registrationFormLastName != null){
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                SiteService siteService = serviceFactory.getSiteService();
                User user = siteService.registration(registrationFormEmail, registrationFormPassword, registrationFormFirstName, registrationFormLastName);
                HttpSession session = request.getSession(true);
                session.setAttribute("userId", user.getId());
                session.setAttribute("userStatus", user.getStatus());
                response.sendRedirect("/Controller?command=welcome");
            } catch (ServiceWrongEmailException e) {
                String languageId = LanguageUtil.getLanguageId(request);
                request.setAttribute("selectedLanguage", languageId);
                request.setAttribute("registrationFormEmail", registrationFormEmail);
                request.setAttribute("registrationFormPassword", registrationFormPassword);
                request.setAttribute("registrationFormFirstName", registrationFormFirstName);
                request.setAttribute("registrationFormLastName", registrationFormLastName);
                request.setAttribute("wrongEmail", true);
                request.getRequestDispatcher("WEB-INF/jsp/registration.jsp").forward(request, response);
            } catch (ServiceException e) {
                String languageId = LanguageUtil.getLanguageId(request);
                request.setAttribute("selectedLanguage", languageId);
                request.setAttribute("registrationFormEmail", registrationFormEmail);
                request.setAttribute("registrationFormPassword", registrationFormPassword);
                request.setAttribute("registrationFormFirstName", registrationFormFirstName);
                request.setAttribute("registrationFormLastName", registrationFormLastName);
                request.setAttribute("serviceError", true);
                request.getRequestDispatcher("WEB-INF/jsp/registration.jsp").forward(request, response);
            }
        }
        else {
            QueryUtil.saveCurrentQueryToSession(request);
            String languageId = LanguageUtil.getLanguageId(request);
            request.setAttribute("selectedLanguage", languageId);

            request.getRequestDispatcher("WEB-INF/jsp/registration.jsp").forward(request, response);
        }
    }
}
