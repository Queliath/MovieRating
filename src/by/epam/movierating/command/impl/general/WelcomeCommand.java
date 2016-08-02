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
import by.epam.movierating.service.interfaces.CommentService;
import by.epam.movierating.service.interfaces.CountryService;
import by.epam.movierating.service.interfaces.GenreService;
import by.epam.movierating.service.interfaces.MovieService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Владислав on 15.07.2016.
 */
public class WelcomeCommand implements Command {
    private static final int AMOUNT_OF_RECENT_ADDED_MOVIES = 3;
    private static final int AMOUNT_OF_RECENT_ADDED_COMMENTS = 3;
    private static final int AMOUNT_OF_TOP_POSITION_COUNTRIES = 5;
    private static final int AMOUNT_OF_TOP_POSITION_GENRES = 5;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute("selectedLanguage", languageId);

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();

            GenreService genreService = serviceFactory.getGenreService();
            List<Genre> genres = genreService.getTopPositionGenres(AMOUNT_OF_TOP_POSITION_GENRES, languageId);
            request.setAttribute("genres", genres);

            CountryService countryService = serviceFactory.getCountryService();
            List<Country> countries = countryService.getTopPositionCountries(AMOUNT_OF_TOP_POSITION_COUNTRIES, languageId);
            request.setAttribute("countries", countries);

            MovieService movieService = serviceFactory.getMovieService();
            List<Movie> movies = movieService.getRecentAddedMovies(AMOUNT_OF_RECENT_ADDED_MOVIES, languageId);
            request.setAttribute("movies", movies);

            CommentService commentService = serviceFactory.getCommentService();
            List<Comment> comments = commentService.getRecentAddedComments(AMOUNT_OF_RECENT_ADDED_COMMENTS, languageId);
            request.setAttribute("comments", comments);
        } catch (ServiceException e) {
            request.setAttribute("serviceError", true);
        }
        request.getRequestDispatcher("WEB-INF/jsp/index.jsp").forward(request, response);
    }
}
