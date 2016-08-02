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
        request.setAttribute("selectedLanguage", languageId);

        String pageStr = request.getParameter("page");
        int page = (pageStr == null) ? 1 : Integer.parseInt(pageStr);
        String searchFormNameParam = request.getParameter("searchFormName");
        String searchFormName = (searchFormNameParam == null || searchFormNameParam.isEmpty()) ? null : searchFormNameParam;
        String searchFormMinYearStr = request.getParameter("searchFormMinYear");
        int searchFormMinYear = (searchFormMinYearStr == null || searchFormMinYearStr.isEmpty()) ? 0 : Integer.parseInt(searchFormMinYearStr);
        String searchFormMaxYearStr = request.getParameter("searchFormMaxYear");
        int searchFormMaxYear = (searchFormMaxYearStr == null || searchFormMaxYearStr.isEmpty()) ? 0 : Integer.parseInt(searchFormMaxYearStr);
        String[] searchFormGenresStr = request.getParameterValues("searchFormGenres[]");
        List<Integer> searchFormGenres = null;
        if(searchFormGenresStr != null){
            searchFormGenres = new ArrayList<>();
            for(String genreStr : searchFormGenresStr){
                searchFormGenres.add(Integer.parseInt(genreStr));
            }
        }
        String[] searchFormCountriesStr = request.getParameterValues("searchFormCountries[]");
        List<Integer> searchFormCountries = null;
        if(searchFormCountriesStr != null){
            searchFormCountries = new ArrayList<>();
            for(String countryStr : searchFormCountriesStr){
                searchFormCountries.add(Integer.parseInt(countryStr));
            }
        }
        String searchFormMinRatingStr = request.getParameter("searchFormMinRating");
        int searchFormMinRating = (searchFormMinRatingStr == null || searchFormMinRatingStr.isEmpty()) ? 0 : Integer.parseInt(searchFormMinRatingStr);
        String searchFormMaxRatingStr = request.getParameter("searchFormMaxRating");
        int searchFormMaxRating = (searchFormMaxRatingStr == null || searchFormMaxRatingStr.isEmpty()) ? 0 : Integer.parseInt(searchFormMaxRatingStr);

        request.setAttribute("searchFormName", searchFormNameParam);
        request.setAttribute("searchFormMinYear", searchFormMinYearStr);
        request.setAttribute("searchFormMaxYear", searchFormMaxYearStr);
        request.setAttribute("searchFormGenres", searchFormGenres);
        request.setAttribute("searchFormCountries", searchFormCountries);
        request.setAttribute("searchFormMinRating", searchFormMinRatingStr);
        request.setAttribute("searchFormMaxRating", searchFormMaxRatingStr);

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();

            GenreService genreService = serviceFactory.getGenreService();
            List<Genre> genres = genreService.getTopPositionGenres(AMOUNT_OF_TOP_POSITION_GENRES, languageId);
            request.setAttribute("genres", genres);

            CountryService countryService = serviceFactory.getCountryService();
            List<Country> countries = countryService.getTopPositionCountries(AMOUNT_OF_TOP_POSITION_COUNTRIES, languageId);
            request.setAttribute("countries", countries);

            MovieService movieService = serviceFactory.getMovieService();

            int moviesCount = movieService.getMoviesCountByCriteria(searchFormName, searchFormMinYear,
                    searchFormMaxYear, searchFormGenres, searchFormCountries, searchFormMinRating,
                    searchFormMaxRating, languageId);
            request.setAttribute("moviesCount", moviesCount);

            int from = (page - 1) * MOVIES_PER_PAGE;
            List<Movie> movies = movieService.getMoviesByCriteria(searchFormName, searchFormMinYear,
                    searchFormMaxYear, searchFormGenres, searchFormCountries, searchFormMinRating,
                    searchFormMaxRating, from, MOVIES_PER_PAGE, languageId);
            request.setAttribute("movies", movies);
            request.setAttribute("moviesFrom", from + 1);
            request.setAttribute("moviesTo", from + movies.size());

            List<Integer> pagination = new ArrayList<>();
            for(int i = 0; i < moviesCount; i += MOVIES_PER_PAGE){
                int pageNumber = (i / MOVIES_PER_PAGE) + 1;
                pagination.add(pageNumber);
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
}
