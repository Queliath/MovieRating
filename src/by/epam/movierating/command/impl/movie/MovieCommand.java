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
        setLocaleAttributes(request, languageId);

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

    private void setLocaleAttributes(HttpServletRequest request, String languageId){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("locale", new Locale(languageId));

        request.setAttribute("siteName", resourceBundle.getString("locale.siteName"));
        request.setAttribute("mainPageName", resourceBundle.getString("locale.mainPageName"));
        request.setAttribute("catalogPageName", resourceBundle.getString("locale.catalogPageName"));
        request.setAttribute("registrationPageName", resourceBundle.getString("locale.registrationPageName"));
        request.setAttribute("loginPageName", resourceBundle.getString("locale.loginPageName"));
        request.setAttribute("profilePageName", resourceBundle.getString("locale.profilePageName"));
        request.setAttribute("logoutName", resourceBundle.getString("locale.logoutName"));
        request.setAttribute("personsPageName", resourceBundle.getString("locale.personsPageName"));
        request.setAttribute("usersPageName", resourceBundle.getString("locale.usersPageName"));
        request.setAttribute("localeOther", resourceBundle.getString("locale.other"));
        request.setAttribute("genresPageName", resourceBundle.getString("locale.genresPageName"));
        request.setAttribute("countriesPageName", resourceBundle.getString("locale.countriesPageName"));
        request.setAttribute("localeCountry", resourceBundle.getString("locale.country"));
        request.setAttribute("localeGenre", resourceBundle.getString("locale.genre"));
        request.setAttribute("localeYear", resourceBundle.getString("locale.year"));
        request.setAttribute("localeBudget", resourceBundle.getString("locale.budget"));
        request.setAttribute("localePremiere", resourceBundle.getString("locale.premiere"));
        request.setAttribute("localeLasting", resourceBundle.getString("locale.lasting"));
        request.setAttribute("localeMinute", resourceBundle.getString("locale.minute"));
        request.setAttribute("localeRating", resourceBundle.getString("locale.rating"));
        request.setAttribute("localeServiceError", resourceBundle.getString("locale.serviceError"));
        request.setAttribute("localeNoMovie", resourceBundle.getString("locale.noMovie"));
        request.setAttribute("localeActors", resourceBundle.getString("locale.actors"));
        request.setAttribute("localeDirectors", resourceBundle.getString("locale.directors"));
        request.setAttribute("localeProducers", resourceBundle.getString("locale.producers"));
        request.setAttribute("localeWriters", resourceBundle.getString("locale.writers"));
        request.setAttribute("localeEditors", resourceBundle.getString("locale.editors"));
        request.setAttribute("localePainters", resourceBundle.getString("locale.painters"));
        request.setAttribute("localeOperators", resourceBundle.getString("locale.operators"));
        request.setAttribute("localeComposers", resourceBundle.getString("locale.composers"));
        request.setAttribute("localeTagline", resourceBundle.getString("locale.tagline"));
        request.setAttribute("localeEdit", resourceBundle.getString("locale.edit"));
        request.setAttribute("localeDelete", resourceBundle.getString("locale.delete"));
        request.setAttribute("localeAddActor", resourceBundle.getString("locale.addActor"));
        request.setAttribute("localeAddDirector", resourceBundle.getString("locale.addDirector"));
        request.setAttribute("localeAddProducer", resourceBundle.getString("locale.addProducer"));
        request.setAttribute("localeAddWriter", resourceBundle.getString("locale.addWriter"));
        request.setAttribute("localeAddPainter", resourceBundle.getString("locale.addPainter"));
        request.setAttribute("localeAddOperator", resourceBundle.getString("locale.addOperator"));
        request.setAttribute("localeAddEditor", resourceBundle.getString("locale.addEditor"));
        request.setAttribute("localeAddComposer", resourceBundle.getString("locale.addComposer"));
        request.setAttribute("localeAddCountry", resourceBundle.getString("locale.addCountry"));
        request.setAttribute("localeAddGenre", resourceBundle.getString("locale.addGenre"));
        request.setAttribute("localeWriteComment", resourceBundle.getString("locale.writeComment"));
        request.setAttribute("localeTitle", resourceBundle.getString("locale.title"));
        request.setAttribute("localeEnterTitle", resourceBundle.getString("locale.enterTitle"));
        request.setAttribute("localeContent", resourceBundle.getString("locale.content"));
        request.setAttribute("localeEnterContent", resourceBundle.getString("locale.enterContent"));
        request.setAttribute("localeCommentButton", resourceBundle.getString("locale.commentButton"));
        request.setAttribute("localeComments", resourceBundle.getString("locale.comments"));
        request.setAttribute("localeDeleteTitle", resourceBundle.getString("locale.deleteTitle"));
        request.setAttribute("localeDeleteBody", resourceBundle.getString("locale.deleteBody"));
        request.setAttribute("localeCancel", resourceBundle.getString("locale.cancel"));

        request.setAttribute("selectedLanguage", languageId);
    }
}
