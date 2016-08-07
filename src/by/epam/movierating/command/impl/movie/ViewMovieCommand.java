package by.epam.movierating.command.impl.movie;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.util.LanguageUtil;
import by.epam.movierating.command.util.QueryUtil;
import by.epam.movierating.domain.Movie;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.interfaces.CommentService;
import by.epam.movierating.service.interfaces.MovieService;
import by.epam.movierating.service.interfaces.RatingService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ViewMovieCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        int id = (idStr == null) ? -1 : Integer.parseInt(idStr);
        if(id == -1){
            response.sendRedirect("/Controller?command=movies");
            return;
        }

        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute("selectedLanguage", languageId);

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            MovieService movieService = serviceFactory.getMovieService();
            Movie movie = movieService.getMovieById(id, languageId);
            if(movie != null){
                request.setAttribute("movie", movie);
            }

            HttpSession session = request.getSession(false);
            Integer userId = (session == null) ? null : (Integer) session.getAttribute("userId");
            if(userId != null){
                RatingService ratingService = serviceFactory.getRatingService();
                int ratingValue = ratingService.getRatingValueByMovieAndUser(id, userId);
                if(ratingValue != -1){
                    request.setAttribute("ratingValue", ratingValue);
                }
            }
        } catch (ServiceException e) {
            request.setAttribute("serviceError", true);
        }
        request.getRequestDispatcher("WEB-INF/jsp/movie/movie.jsp").forward(request, response);
    }
}
