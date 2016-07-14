package by.epam.movierating.domain;

/**
 * Created by Владислав on 12.06.2016.
 */
public class Rating {
    private int value;
    private int userId;
    private int movieId;

    public Rating() {
    }

    public Rating(int value, int userId, int movieId) {
        this.value = value;
        this.userId = userId;
        this.movieId = movieId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rating rating = (Rating) o;

        if (value != rating.value) return false;
        if (userId != rating.userId) return false;
        return movieId == rating.movieId;

    }

    @Override
    public int hashCode() {
        int result = value;
        result = 31 * result + userId;
        result = 31 * result + movieId;
        return result;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "value=" + value +
                ", userId=" + userId +
                ", movieId=" + movieId +
                '}';
    }
}
