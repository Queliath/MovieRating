package by.epam.movierating.command.impl.genre;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.util.LanguageUtil;
import by.epam.movierating.command.util.QueryUtil;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.interfaces.CountryService;
import by.epam.movierating.service.interfaces.GenreService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Владислав on 08.08.2016.
 */
public class AddGenreCommand implements Command {
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

        String genreFormName = request.getParameter("genreFormName");
        String genreFormPosition = request.getParameter("genreFormPosition");
        if(genreFormName != null && genreFormPosition != null){
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                GenreService genreService = serviceFactory.getGenreService();
                genreService.addGenre(genreFormName, Integer.parseInt(genreFormPosition));
                response.sendRedirect("/Controller?command=genres");
                return;
            } catch (ServiceException e) {
                request.setAttribute("serviceError", true);
                request.setAttribute("genreFormName", genreFormName);
                request.setAttribute("genreFormPosition", genreFormPosition);
            }
        }

        request.getRequestDispatcher("WEB-INF/jsp/genre/add-genre-form.jsp").forward(request, response);
    }
}
