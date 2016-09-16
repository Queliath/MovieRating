package by.epam.movierating.command.impl.general;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.util.LanguageUtil;
import by.epam.movierating.command.util.QueryUtil;
import by.epam.movierating.domain.Comment;
import by.epam.movierating.domain.Country;
import by.epam.movierating.domain.Genre;
import by.epam.movierating.domain.Movie;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.inter.CommentService;
import by.epam.movierating.service.inter.CountryService;
import by.epam.movierating.service.inter.GenreService;
import by.epam.movierating.service.inter.MovieService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Services the request from the main (welcome) apge.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class WelcomeCommand implements Command {
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/index.jsp";

    private static final String SERVICE_ERROR_REQUEST_ATTR = "serviceError";
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";
    private static final String GENRES_REQUEST_ATTR = "genres";
    private static final String COUNTRIES_REQUEST_ATTR = "countries";
    private static final String MOVIES_REQUEST_ATTR = "movies";
    private static final String COMMENTS_REQUEST_ATTR = "comments";

    private static final int AMOUNT_OF_RECENT_ADDED_MOVIES = 3;
    private static final int AMOUNT_OF_RECENT_ADDED_COMMENTS = 3;
    private static final int AMOUNT_OF_TOP_POSITION_COUNTRIES = 5;
    private static final int AMOUNT_OF_TOP_POSITION_GENRES = 5;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();

            GenreService genreService = serviceFactory.getGenreService();
            List<Genre> genres = genreService.getTopPositionGenres(AMOUNT_OF_TOP_POSITION_GENRES, languageId);
            request.setAttribute(GENRES_REQUEST_ATTR, genres);

            CountryService countryService = serviceFactory.getCountryService();
            List<Country> countries = countryService.getTopPositionCountries(AMOUNT_OF_TOP_POSITION_COUNTRIES, languageId);
            request.setAttribute(COUNTRIES_REQUEST_ATTR, countries);

            MovieService movieService = serviceFactory.getMovieService();
            List<Movie> movies = movieService.getRecentAddedMovies(AMOUNT_OF_RECENT_ADDED_MOVIES, languageId);
            request.setAttribute(MOVIES_REQUEST_ATTR, movies);

            CommentService commentService = serviceFactory.getCommentService();
            List<Comment> comments = commentService.getRecentAddedComments(AMOUNT_OF_RECENT_ADDED_COMMENTS, languageId);
            request.setAttribute(COMMENTS_REQUEST_ATTR, comments);
        } catch (ServiceException e) {
            request.setAttribute(SERVICE_ERROR_REQUEST_ATTR, true);
        }
        request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
    }
}
