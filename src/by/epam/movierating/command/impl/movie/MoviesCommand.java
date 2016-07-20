package by.epam.movierating.command.impl.movie;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.util.LanguageUtil;
import by.epam.movierating.command.util.PaginationUtil;
import by.epam.movierating.command.util.QueryUtil;
import by.epam.movierating.domain.Country;
import by.epam.movierating.domain.Genre;
import by.epam.movierating.domain.Movie;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.interfaces.CountryService;
import by.epam.movierating.service.interfaces.GenreService;
import by.epam.movierating.service.interfaces.MovieService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by Владислав on 19.07.2016.
 */
public class MoviesCommand implements Command {
    private static final int AMOUNT_OF_TOP_POSITION_COUNTRIES = 5;
    private static final int AMOUNT_OF_TOP_POSITION_GENRES = 5;

    private static final int MOVIES_PER_PAGE = 10;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        setLocaleAttributes(request, languageId);

        String pageStr = request.getParameter("page");
        int page = (pageStr == null) ? 1 : Integer.parseInt(pageStr);

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();

            GenreService genreService = serviceFactory.getGenreService();
            List<Genre> genres = genreService.getTopPositionGenres(AMOUNT_OF_TOP_POSITION_GENRES, languageId);
            request.setAttribute("genres", genres);

            CountryService countryService = serviceFactory.getCountryService();
            List<Country> countries = countryService.getTopPositionCountries(AMOUNT_OF_TOP_POSITION_COUNTRIES, languageId);
            request.setAttribute("countries", countries);

            MovieService movieService = serviceFactory.getMovieService();

            int moviesCount = movieService.getMoviesCountByCriteria(null, 0, 0, null, null, 0, 0);
            request.setAttribute("moviesCount", moviesCount);

            int from = (page - 1) * MOVIES_PER_PAGE;
            List<Movie> movies = movieService.getMoviesByCriteria(null, 0, 0, null, null, 0, 0, from, MOVIES_PER_PAGE, languageId);
            request.setAttribute("movies", movies);
            request.setAttribute("moviesFrom", from + 1);
            request.setAttribute("moviesTo", from + movies.size());

            Map<Integer, String> pagination = new LinkedHashMap<>();
            for(int i = 0; i < moviesCount; i += MOVIES_PER_PAGE){
                int pageNumber = (i / MOVIES_PER_PAGE) + 1;
                pagination.put(pageNumber, PaginationUtil.createLinkForPage(request, pageNumber));
            }
            if(pagination.size() > 1){
                request.setAttribute("pagination", pagination);
                request.setAttribute("activePage", page);
            }
        } catch (ServiceException e) {
            request.setAttribute("serviceError", true);
        }
        request.getRequestDispatcher("WEB-INF/jsp/movie/movies.jsp").forward(request, response);
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
        request.setAttribute("localeSearchCriteria", resourceBundle.getString("locale.searchCriteria"));
        request.setAttribute("localeName", resourceBundle.getString("locale.name"));
        request.setAttribute("localeFrom", resourceBundle.getString("locale.from"));
        request.setAttribute("localeTo", resourceBundle.getString("locale.to"));
        request.setAttribute("localeFindButton", resourceBundle.getString("locale.findButton"));
        request.setAttribute("localeCountry", resourceBundle.getString("locale.country"));
        request.setAttribute("localeGenre", resourceBundle.getString("locale.genre"));
        request.setAttribute("localeDirector", resourceBundle.getString("locale.director"));
        request.setAttribute("localeYear", resourceBundle.getString("locale.year"));
        request.setAttribute("localeBudget", resourceBundle.getString("locale.budget"));
        request.setAttribute("localePremiere", resourceBundle.getString("locale.premiere"));
        request.setAttribute("localeLasting", resourceBundle.getString("locale.lasting"));
        request.setAttribute("localeMinute", resourceBundle.getString("locale.minute"));
        request.setAttribute("localeRating", resourceBundle.getString("locale.rating"));
        request.setAttribute("localeServiceError", resourceBundle.getString("locale.serviceError"));
        request.setAttribute("localeAddMovie", resourceBundle.getString("locale.addMovie"));
        request.setAttribute("localeNoResults", resourceBundle.getString("locale.noResults"));
        request.setAttribute("localeDisplaying", resourceBundle.getString("locale.displaying"));
        request.setAttribute("localeOf", resourceBundle.getString("locale.of"));

        request.setAttribute("selectedLanguage", languageId);
    }
}
