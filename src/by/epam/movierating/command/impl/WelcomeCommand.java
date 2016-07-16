package by.epam.movierating.command.impl;

import by.epam.movierating.command.Command;
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
import java.util.List;

/**
 * Created by Владислав on 15.07.2016.
 */
public class WelcomeCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String languageId = request.getParameter("lang");

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();

            GenreService genreService = serviceFactory.getGenreService();
            List<Genre> genres = genreService.getAllGenres();
            request.setAttribute("genres", genres);

            CountryService countryService = serviceFactory.getCountryService();
            List<Country> countries = countryService.getAllCountries(languageId);
            request.setAttribute("countries", countries);

            MovieService movieService = serviceFactory.getMovieService();
            List<Movie> movies = movieService.getAllMovies(languageId);
            request.setAttribute("movies", movies);
        } catch (ServiceException e) {
            request.setAttribute("errorMessage", "Ошибка загрузки данных.");
        }
        request.getRequestDispatcher("WEB-INF/jsp/index.jsp").forward(request, response);
    }
}
