package by.bsuir.movierating.controller;

import by.bsuir.movierating.controller.request.BaseRequest;
import by.bsuir.movierating.domain.Movie;
import by.bsuir.movierating.domain.criteria.MovieCriteria;
import by.bsuir.movierating.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {
    private MovieService movieService;

    @Autowired
    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable Integer id, @ModelAttribute BaseRequest request) {
        return movieService.getMovieById(id, request.getLanguageId());
    }

    @GetMapping
    public List<Movie> getMoviesByCriteria(@ModelAttribute BaseRequest request, @ModelAttribute MovieCriteria movieCriteria) {
        return movieService.getMoviesByCriteria(movieCriteria.getName(), movieCriteria.getMinYear(), movieCriteria.getMaxYear(),
                movieCriteria.getGenreIds(), movieCriteria.getCountryIds(), movieCriteria.getMinRating(),
                movieCriteria.getMaxRating(), movieCriteria.getFrom(), movieCriteria.getAmount(), request.getLanguageId());
    }

    @GetMapping("/recent")
    public List<Movie> getRecentAddedMovies(@ModelAttribute BaseRequest request, @RequestParam Integer amount) {
        return movieService.getRecentAddedMovies(amount, request.getLanguageId());
    }

    @GetMapping("/count")
    public Integer getMoviesCountByCriteria(@ModelAttribute BaseRequest baseRequest, @ModelAttribute MovieCriteria movieCriteria) {
        return movieService.getMoviesCountByCriteria(movieCriteria.getName(), movieCriteria.getMinYear(), movieCriteria.getMaxYear(),
                movieCriteria.getGenreIds(), movieCriteria.getCountryIds(), movieCriteria.getMinRating(),
                movieCriteria.getMaxRating(), baseRequest.getLanguageId());
    }
}
