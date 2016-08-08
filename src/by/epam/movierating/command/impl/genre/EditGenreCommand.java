package by.epam.movierating.command.impl.genre;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.util.LanguageUtil;
import by.epam.movierating.command.util.QueryUtil;
import by.epam.movierating.domain.Country;
import by.epam.movierating.domain.Genre;
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
public class EditGenreCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        int id = (idStr == null) ? -1 : Integer.parseInt(idStr);
        if(id == -1){
            response.sendRedirect("/Controller?command=welcome");
            return;
        }

        HttpSession session = request.getSession(false);
        String userStatus = (session == null) ? null : (String) session.getAttribute("userStatus");
        if(userStatus == null || !userStatus.equals("admin")){
            response.sendRedirect("/Controller?command=login&cause=timeout");
            return;
        }

        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute("selectedLanguage", languageId);

        String langParam = request.getParameter("lang");
        String editingLanguageId = (langParam == null) ? "EN" : langParam;
        request.setAttribute("editingLanguage", editingLanguageId);

        String genreFormName = request.getParameter("genreFormName");
        String genreFormPosition = request.getParameter("genreFormPosition");
        if(genreFormName != null && genreFormPosition != null){
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                GenreService genreService = serviceFactory.getGenreService();
                genreService.editGenre(id, genreFormName, Integer.parseInt(genreFormPosition), editingLanguageId);
                request.setAttribute("saveSuccess", true);
            } catch (ServiceException e) {
                request.setAttribute("serviceError", true);
            }
        }

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            GenreService genreService = serviceFactory.getGenreService();
            Genre genre = genreService.getGenreById(id, editingLanguageId);
            if(genre != null){
                request.setAttribute("genre", genre);
            }
        } catch (ServiceException e) {
            request.setAttribute("serviceError", true);
        }

        request.getRequestDispatcher("WEB-INF/jsp/genre/edit-genre-form.jsp").forward(request, response);
    }
}
