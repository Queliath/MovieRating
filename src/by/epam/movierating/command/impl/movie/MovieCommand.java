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

public class MovieCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        int id = (idStr == null) ? -1 : Integer.parseInt(idStr);

        if(id == -1){
            response.sendRedirect("/Controller?command=movies");
            return;
        }

        HttpSession session = request.getSession(false);
        Integer userId = (session == null) ? null : (Integer) session.getAttribute("userId");

        String ratingValueStr = request.getParameter("ratingValue");
        if(ratingValueStr != null){
            if(userId == null){
                response.sendRedirect("Controller?command=login&cause=timeout");
            }
            else {
                try {
                    ServiceFactory serviceFactory = ServiceFactory.getInstance();
                    RatingService ratingService = serviceFactory.getRatingService();
                    ratingService.addRating(Integer.parseInt(ratingValueStr), id, userId);
                } catch (ServiceException ignored) {}
            }
            return;
        }

        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute("selectedLanguage", languageId);

        String commentFormTitle = request.getParameter("commentFormTitle");
        String commentFormContent = request.getParameter("commentFormContent");
        if(commentFormTitle != null && commentFormContent != null){
            if(userId == null){
                response.sendRedirect("Controller?command=login&cause=timeout");
                return;
            }
            else {
                try {
                    ServiceFactory serviceFactory = ServiceFactory.getInstance();
                    CommentService commentService = serviceFactory.getCommentService();
                    commentService.addComment(commentFormTitle, commentFormContent, id, userId, languageId);
                } catch (ServiceException e) {
                    request.setAttribute("commentFormTitle", commentFormTitle);
                    request.setAttribute("commentFormContent", commentFormContent);
                    request.setAttribute("serviceError", true);
                }
            }
        }

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            MovieService movieService = serviceFactory.getMovieService();
            Movie movie = movieService.getMovieById(id, languageId);
            if(movie != null){
                request.setAttribute("movie", movie);
            }

            if(userId != null){
                request.setAttribute("currentUrl", request.getRequestURI() + '?' + request.getQueryString());
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
