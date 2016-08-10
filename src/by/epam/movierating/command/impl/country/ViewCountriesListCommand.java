package by.epam.movierating.command.impl.country;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.util.LanguageUtil;
import by.epam.movierating.command.util.QueryUtil;
import by.epam.movierating.domain.Country;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.interfaces.CountryService;

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
public class ViewCountriesListCommand implements Command {
    private static final int COUNTRIES_PER_PAGE = 10;

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
        String movieParam = request.getParameter("movie");
        Integer movieId = (movieParam == null || movieParam.isEmpty()) ? null : Integer.parseInt(movieParam);

        request.setAttribute("movieId", movieId);

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            CountryService countryService = serviceFactory.getCountryService();

            int countriesCount = countryService.getCountriesCount();
            request.setAttribute("countriesCount", countriesCount);

            int from = (page - 1) * COUNTRIES_PER_PAGE;
            List<Country> countries = countryService.getCountries(from, COUNTRIES_PER_PAGE, languageId);
            request.setAttribute("countries", countries);
            request.setAttribute("countriesFrom", from + 1);
            request.setAttribute("countriesTo", from + countries.size());

            List<Integer> pagination = new ArrayList<>();
            for(int i = 0; i < countriesCount; i += COUNTRIES_PER_PAGE){
                int pageNumber = (i / COUNTRIES_PER_PAGE) + 1;
                pagination.add(pageNumber);
            }
            if(pagination.size() > 1){
                request.setAttribute("pagination", pagination);
                request.setAttribute("activePage", page);
            }
        } catch (ServiceException e) {
            request.setAttribute("serviceError", true);
        }

        request.getRequestDispatcher("WEB-INF/jsp/country/countries.jsp").forward(request, response);
    }
}
