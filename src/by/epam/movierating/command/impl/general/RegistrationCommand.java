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
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            SiteService siteService = serviceFactory.getSiteService();
            try {
                User user = siteService.registration(registrationFormEmail, registrationFormPassword, registrationFormFirstName, registrationFormLastName);
                HttpSession session = request.getSession(true);
                session.setAttribute("userId", user.getId());
                session.setAttribute("userStatus", user.getStatus());
                response.sendRedirect("/Controller?command=welcome");
            } catch (ServiceWrongEmailException e) {
                String languageId = LanguageUtil.getLanguageId(request);
                setLocaleAttributes(request, languageId);
                request.setAttribute("registrationFormEmail", registrationFormEmail);
                request.setAttribute("registrationFormPassword", registrationFormPassword);
                request.setAttribute("registrationFormFirstName", registrationFormFirstName);
                request.setAttribute("registrationFormLastName", registrationFormLastName);
                request.setAttribute("wrongEmail", true);
                request.getRequestDispatcher("WEB-INF/jsp/registration.jsp").forward(request, response);
            } catch (ServiceException e) {
                String languageId = LanguageUtil.getLanguageId(request);
                setLocaleAttributes(request, languageId);
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
            setLocaleAttributes(request, languageId);

            request.getRequestDispatcher("WEB-INF/jsp/registration.jsp").forward(request, response);
        }
    }

    private void setLocaleAttributes(HttpServletRequest request, String languageId){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("locale", new Locale(languageId));

        request.setAttribute("siteName", resourceBundle.getString("locale.siteName"));
        request.setAttribute("mainPageName", resourceBundle.getString("locale.mainPageName"));
        request.setAttribute("catalogPageName", resourceBundle.getString("locale.catalogPageName"));
        request.setAttribute("registrationPageName", resourceBundle.getString("locale.registrationPageName"));
        request.setAttribute("loginPageName", resourceBundle.getString("locale.loginPageName"));
        request.setAttribute("localeEmail", resourceBundle.getString("locale.email"));
        request.setAttribute("localePassword", resourceBundle.getString("locale.password"));
        request.setAttribute("localeEnterEmail", resourceBundle.getString("locale.enterEmail"));
        request.setAttribute("localeEnterPassword", resourceBundle.getString("locale.enterPassword"));
        request.setAttribute("localeFirstName", resourceBundle.getString("locale.firstName"));
        request.setAttribute("localeEnterFirstName", resourceBundle.getString("locale.enterFirstName"));
        request.setAttribute("localeLastName", resourceBundle.getString("locale.lastName"));
        request.setAttribute("localeEnterLastName", resourceBundle.getString("locale.enterLastName"));
        request.setAttribute("localeRegistrationButton", resourceBundle.getString("locale.registrationButton"));
        request.setAttribute("localeUsedEmail", resourceBundle.getString("locale.usedEmail"));
        request.setAttribute("localeServiceError", resourceBundle.getString("locale.serviceError"));

        request.setAttribute("selectedLanguage", languageId);
    }
}
