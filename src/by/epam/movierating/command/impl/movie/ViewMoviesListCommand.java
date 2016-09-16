package by.epam.movierating.command.impl.movie;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.util.LanguageUtil;
import by.epam.movierating.command.util.QueryUtil;
import by.epam.movierating.domain.Country;
import by.epam.movierating.domain.Genre;
import by.epam.movierating.domain.Movie;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import by.epam.movierating.service.inter.CountryService;
import by.epam.movierating.service.inter.GenreService;
import by.epam.movierating.service.inter.MovieService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Services the request from the movies list page.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public class ViewMoviesListCommand implements Command {
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/movie/movies.jsp";

    private static final String PAGE_REQUEST_PARAM = "page";
    private static final String PERSON_REQUEST_PARAM = "person";
    private static final String RELATION_REQUEST_PARAM = "rel";
    private static final String SEARCH_FORM_NAME_PARAM = "searchFormName";
    private static final String SEARCH_FORM_MIN_YEAR_PARAM = "searchFormMinYear";
    private static final String SEARCH_FORM_MAX_YEAR_PARAM = "searchFormMaxYear";
    private static final String SEARCH_FORM_GENRES_PARAM = "searchFormGenres[]";
    private static final String SEARCH_FORM_COUNTRIES_PARAM = "searchFormCountries[]";
    private static final String SEARCH_FORM_MIN_RATING_PARAM = "searchFormMinRating";
    private static final String SEARCH_FORM_MAX_RATING_PARAM = "searchFormMaxRating";

    private static final String SERVICE_ERROR_REQUEST_ATTR = "serviceError";
    private static final String SELECTED_LANGUAGE_REQUEST_ATTR = "selectedLanguage";
    private static final String PERSON_ID_REQUEST_ATTR = "personId";
    private static final String RELATION_TYPE_REQUEST_ATTR = "relationType";
    private static final String SEARCH_FORM_GENRES_REQUEST_ATTR = "searchFormGenres";
    private static final String SEARCH_FORM_COUNTRIES_REQUEST_ATTR = "searchFormCountries";
    private static final String GENRES_REQUEST_ATTR = "genres";
    private static final String COUNTRIES_REQUEST_ATTR = "countries";
    private static final String MOVIES_COUNT_REQUEST_ATTR = "moviesCount";
    private static final String MOVIES_REQUEST_ATTR = "movies";
    private static final String MOVIES_FROM_REQUEST_ATTR = "moviesFrom";
    private static final String MOVIES_TO_REQUEST_ATTR = "moviesTo";
    private static final String PAGINATION_REQUEST_ATTR = "pagination";
    private static final String ACTIVE_PAGE_REQUEST_ATTR = "activePage";

    private static final int AMOUNT_OF_TOP_POSITION_COUNTRIES = 5;
    private static final int AMOUNT_OF_TOP_POSITION_GENRES = 5;

    private static final int MOVIES_PER_PAGE = 10;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        QueryUtil.saveCurrentQueryToSession(request);
        String languageId = LanguageUtil.getLanguageId(request);
        request.setAttribute(SELECTED_LANGUAGE_REQUEST_ATTR, languageId);

        String pageStr = request.getParameter(PAGE_REQUEST_PARAM);
        int page = (pageStr == null) ? 1 : Integer.parseInt(pageStr);
        String searchFormNameParam = request.getParameter(SEARCH_FORM_NAME_PARAM);
        String searchFormName = (searchFormNameParam == null || searchFormNameParam.isEmpty()) ? null : searchFormNameParam;
        String searchFormMinYearStr = request.getParameter(SEARCH_FORM_MIN_YEAR_PARAM);
        int searchFormMinYear = (searchFormMinYearStr == null || searchFormMinYearStr.isEmpty()) ? 0 : Integer.parseInt(searchFormMinYearStr);
        String searchFormMaxYearStr = request.getParameter(SEARCH_FORM_MAX_YEAR_PARAM);
        int searchFormMaxYear = (searchFormMaxYearStr == null || searchFormMaxYearStr.isEmpty()) ? 0 : Integer.parseInt(searchFormMaxYearStr);
        String[] searchFormGenresStr = request.getParameterValues(SEARCH_FORM_GENRES_PARAM);
        List<Integer> searchFormGenres = null;
        if(searchFormGenresStr != null){
            searchFormGenres = new ArrayList<>();
            for(String genreStr : searchFormGenresStr){
                searchFormGenres.add(Integer.parseInt(genreStr));
            }
        }
        String[] searchFormCountriesStr = request.getParameterValues(SEARCH_FORM_COUNTRIES_PARAM);
        List<Integer> searchFormCountries = null;
        if(searchFormCountriesStr != null){
            searchFormCountries = new ArrayList<>();
            for(String countryStr : searchFormCountriesStr){
                searchFormCountries.add(Integer.parseInt(countryStr));
            }
        }
        String searchFormMinRatingStr = request.getParameter(SEARCH_FORM_MIN_RATING_PARAM);
        int searchFormMinRating = (searchFormMinRatingStr == null || searchFormMinRatingStr.isEmpty()) ? 0 : Integer.parseInt(searchFormMinRatingStr);
        String searchFormMaxRatingStr = request.getParameter(SEARCH_FORM_MAX_RATING_PARAM);
        int searchFormMaxRating = (searchFormMaxRatingStr == null || searchFormMaxRatingStr.isEmpty()) ? 0 : Integer.parseInt(searchFormMaxRatingStr);
        String personParam = request.getParameter(PERSON_REQUEST_PARAM);
        Integer personId = (personParam == null || personParam.isEmpty()) ? null : Integer.parseInt(personParam);
        String relParam = request.getParameter(RELATION_REQUEST_PARAM);
        Integer relationType = (relParam == null || relParam.isEmpty()) ? null : Integer.parseInt(relParam);

        request.setAttribute(SEARCH_FORM_NAME_PARAM, searchFormNameParam);
        request.setAttribute(SEARCH_FORM_MIN_YEAR_PARAM, searchFormMinYearStr);
        request.setAttribute(SEARCH_FORM_MAX_YEAR_PARAM, searchFormMaxYearStr);
        request.setAttribute(SEARCH_FORM_GENRES_REQUEST_ATTR, searchFormGenres);
        request.setAttribute(SEARCH_FORM_COUNTRIES_REQUEST_ATTR, searchFormCountries);
        request.setAttribute(SEARCH_FORM_MIN_RATING_PARAM, searchFormMinRatingStr);
        request.setAttribute(SEARCH_FORM_MAX_RATING_PARAM, searchFormMaxRatingStr);
        request.setAttribute(PERSON_ID_REQUEST_ATTR, personId);
        request.setAttribute(RELATION_TYPE_REQUEST_ATTR, relationType);

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();

            GenreService genreService = serviceFactory.getGenreService();
            List<Genre> genres = genreService.getTopPositionGenres(AMOUNT_OF_TOP_POSITION_GENRES, languageId);
            request.setAttribute(GENRES_REQUEST_ATTR, genres);

            CountryService countryService = serviceFactory.getCountryService();
            List<Country> countries = countryService.getTopPositionCountries(AMOUNT_OF_TOP_POSITION_COUNTRIES, languageId);
            request.setAttribute(COUNTRIES_REQUEST_ATTR, countries);

            MovieService movieService = serviceFactory.getMovieService();

            int moviesCount = movieService.getMoviesCountByCriteria(searchFormName, searchFormMinYear,
                    searchFormMaxYear, searchFormGenres, searchFormCountries, searchFormMinRating,
                    searchFormMaxRating, languageId);
            request.setAttribute(MOVIES_COUNT_REQUEST_ATTR, moviesCount);

            int from = (page - 1) * MOVIES_PER_PAGE;
            List<Movie> movies = movieService.getMoviesByCriteria(searchFormName, searchFormMinYear,
                    searchFormMaxYear, searchFormGenres, searchFormCountries, searchFormMinRating,
                    searchFormMaxRating, from, MOVIES_PER_PAGE, languageId);
            request.setAttribute(MOVIES_REQUEST_ATTR, movies);
            request.setAttribute(MOVIES_FROM_REQUEST_ATTR, from + 1);
            request.setAttribute(MOVIES_TO_REQUEST_ATTR, from + movies.size());

            List<Integer> pagination = new ArrayList<>();
            for(int i = 0; i < moviesCount; i += MOVIES_PER_PAGE){
                int pageNumber = (i / MOVIES_PER_PAGE) + 1;
                pagination.add(pageNumber);
            }
            if(pagination.size() > 1){
                request.setAttribute(PAGINATION_REQUEST_ATTR, pagination);
                request.setAttribute(ACTIVE_PAGE_REQUEST_ATTR, page);
            }
        } catch (ServiceException e) {
            request.setAttribute(SERVICE_ERROR_REQUEST_ATTR, true);
        }
        request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
    }
}
