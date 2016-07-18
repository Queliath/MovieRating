package by.epam.movierating.command.impl;

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

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        setLocaleAttributes(request, languageId);

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();

            GenreService genreService = serviceFactory.getGenreService();
            List<Genre> genres = genreService.getAllGenres(languageId);
            request.setAttribute("genres", genres);

            CountryService countryService = serviceFactory.getCountryService();
            List<Country> countries = countryService.getAllCountries(languageId);
            request.setAttribute("countries", countries);

            MovieService movieService = serviceFactory.getMovieService();
            List<Movie> movies = movieService.getRecentAddedMovies(AMOUNT_OF_RECENT_ADDED_MOVIES, languageId);
            request.setAttribute("movies", movies);

            CommentService commentService = serviceFactory.getCommentService();
            List<Comment> comments = commentService.getRecentAddedComments(AMOUNT_OF_RECENT_ADDED_COMMENTS, languageId);
            request.setAttribute("comments", comments);
        } catch (ServiceException e) {
            request.setAttribute("errorMessage", "Ошибка загрузки данных.");
        }
        request.getRequestDispatcher("WEB-INF/jsp/index.jsp").forward(request, response);
    }

    private void setLocaleAttributes(HttpServletRequest request, String languageId) {
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
        request.setAttribute("searchMovieByGenre", resourceBundle.getString("locale.searchMovieByGenre"));
        request.setAttribute("searchMovieByCountry", resourceBundle.getString("locale.searchMovieByCountry"));
        request.setAttribute("findYourMovie", resourceBundle.getString("locale.findYourMovie"));
        request.setAttribute("enterMovieName", resourceBundle.getString("locale.enterMovieName"));
        request.setAttribute("findButton", resourceBundle.getString("locale.findButton"));
        request.setAttribute("localeRecentMovies", resourceBundle.getString("locale.recentMovies"));
        request.setAttribute("localeRecentComments", resourceBundle.getString("locale.recentComments"));
        request.setAttribute("localeCountry", resourceBundle.getString("locale.country"));
        request.setAttribute("localeGenre", resourceBundle.getString("locale.genre"));
        request.setAttribute("localeDirector", resourceBundle.getString("locale.director"));
        request.setAttribute("localeYear", resourceBundle.getString("locale.year"));
        request.setAttribute("localeBudget", resourceBundle.getString("locale.budget"));
        request.setAttribute("localePremiere", resourceBundle.getString("locale.premiere"));
        request.setAttribute("localeLasting", resourceBundle.getString("locale.lasting"));
        request.setAttribute("localeMinute", resourceBundle.getString("locale.minute"));
        request.setAttribute("localeRating", resourceBundle.getString("locale.rating"));
        request.setAttribute("localeToMovie", resourceBundle.getString("locale.toMovie"));

        request.setAttribute("selectedLanguage", languageId);
    }
}
