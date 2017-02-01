package by.bsuir.movierating.controller.request;

import by.bsuir.movierating.domain.Movie;

public class MovieRequest extends BaseRequest {
    private Movie movie;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        MovieRequest that = (MovieRequest) o;

        return !(movie != null ? !movie.equals(that.movie) : that.movie != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (movie != null ? movie.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MovieRequest{" +
                "movie=" + movie +
                '}';
    }
}
