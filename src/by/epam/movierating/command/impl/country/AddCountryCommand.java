package by.epam.movierating.command.impl.country;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.util.LanguageUtil;
import by.epam.movierating.command.util.QueryUtil;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.interfaces.CountryService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Владислав on 08.08.2016.
 */
public class AddCountryCommand implements Command {
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

        String countryFormName = request.getParameter("countryFormName");
        String countryFormPosition = request.getParameter("countryFormPosition");
        if(countryFormName != null && countryFormPosition != null){
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                CountryService countryService = serviceFactory.getCountryService();
                countryService.addCountry(countryFormName, Integer.parseInt(countryFormPosition));
                response.sendRedirect("/Controller?command=countries");
                return;
            } catch (ServiceException e) {
                request.setAttribute("serviceError", true);
                request.setAttribute("countryFormName", countryFormName);
                request.setAttribute("countryFormPosition", countryFormPosition);
            }
        }

        request.getRequestDispatcher("WEB-INF/jsp/country/add-country-form.jsp").forward(request, response);
    }
}
