package by.epam.movierating.command.impl.general;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.util.LanguageUtil;
import by.epam.movierating.command.util.QueryUtil;
import by.epam.movierating.domain.User;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.exception.ServiceWrongEmailException;
import by.epam.movierating.service.exception.ServiceWrongPasswordException;
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
public class LoginCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String loginFormEmail = request.getParameter("loginFormEmail");
        String loginFormPassword = request.getParameter("loginFormPassword");

        if(loginFormEmail != null && loginFormPassword != null){
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                SiteService siteService = serviceFactory.getSiteService();
                User user = siteService.login(loginFormEmail, loginFormPassword);
                HttpSession session = request.getSession(true);
                session.setAttribute("userId", user.getId());
                session.setAttribute("userStatus", user.getStatus());
                response.sendRedirect("/Controller?command=welcome");
            } catch (ServiceWrongEmailException e) {
                String languageId = LanguageUtil.getLanguageId(request);
                setLocaleAttributes(request, languageId);
                request.setAttribute("wrongEmail", true);
                request.setAttribute("loginFormEmail", loginFormEmail);
                request.setAttribute("loginFormPassword", loginFormPassword);
                request.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(request, response);
            } catch (ServiceWrongPasswordException e) {
                String languageId = LanguageUtil.getLanguageId(request);
                setLocaleAttributes(request, languageId);
                request.setAttribute("wrongPassword", true);
                request.setAttribute("loginFormEmail", loginFormEmail);
                request.setAttribute("loginFormPassword", loginFormPassword);
                request.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(request, response);
            } catch (ServiceException e) {
                String languageId = LanguageUtil.getLanguageId(request);
                setLocaleAttributes(request, languageId);
                request.setAttribute("serviceError", true);
                request.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(request, response);
            }
        }
        else {
            String cause = request.getParameter("cause");
            if(cause != null){
                switch (cause){
                    case "timeout":
                        request.setAttribute("timeout", true);
                        break;
                }
            }

            QueryUtil.saveCurrentQueryToSession(request);
            String languageId = LanguageUtil.getLanguageId(request);
            setLocaleAttributes(request, languageId);

            request.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(request, response);
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
        request.setAttribute("localeLoginButton", resourceBundle.getString("locale.loginButton"));
        request.setAttribute("localeWrongEmail", resourceBundle.getString("locale.wrongEmail"));
        request.setAttribute("localeWrongPassword", resourceBundle.getString("locale.wrongPassword"));
        request.setAttribute("localeTimeout", resourceBundle.getString("locale.timeout"));

        request.setAttribute("selectedLanguage", languageId);
    }
}
