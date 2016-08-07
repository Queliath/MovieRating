package by.epam.movierating.domain.criteria;

import java.util.List;

/**
 * Created by Владислав on 19.07.2016.
 */
public class MovieCriteria {
    private String name;
    private int minYear;
    private int maxYear;
    private List<Integer> genreIds;
    private List<Integer> countryIds;
    private int minRating;
    private int maxRating;

    public MovieCriteria() {
    }

    public MovieCriteria(String name, int minYear, int maxYear, List<Integer> genreIds, List<Integer> countryIds, int minRating, int maxRating) {
        this.name = name;
        this.minYear = minYear;
        this.maxYear = maxYear;
        this.genreIds = genreIds;
        this.countryIds = countryIds;
        this.minRating = minRating;
        this.maxRating = maxRating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinYear() {
        return minYear;
    }

    public void setMinYear(int minYear) {
        this.minYear = minYear;
    }

    public int getMaxYear() {
        return maxYear;
    }

    public void setMaxYear(int maxYear) {
        this.maxYear = maxYear;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public List<Integer> getCountryIds() {
        return countryIds;
    }

    public void setCountryIds(List<Integer> countryIds) {
        this.countryIds = countryIds;
    }

    public int getMinRating() {
        return minRating;
    }

    public void setMinRating(int minRating) {
        this.minRating = minRating;
    }

    public int getMaxRating() {
        return maxRating;
    }

    public void setMaxRating(int maxRating) {
        this.maxRating = maxRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieCriteria that = (MovieCriteria) o;

        if (minYear != that.minYear) return false;
        if (maxYear != that.maxYear) return false;
        if (minRating != that.minRating) return false;
        if (maxRating != that.maxRating) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (genreIds != null ? !genreIds.equals(that.genreIds) : that.genreIds != null) return false;
        if (countryIds != null ? !countryIds.equals(that.countryIds) : that.countryIds != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + minYear;
        result = 31 * result + maxYear;
        result = 31 * result + (genreIds != null ? genreIds.hashCode() : 0);
        result = 31 * result + (countryIds != null ? countryIds.hashCode() : 0);
        result = 31 * result + minRating;
        result = 31 * result + maxRating;
        return result;
    }

    @Override
    public String toString() {
        return "MovieCriteria{" +
                "name='" + name + '\'' +
                ", minYear=" + minYear +
                ", maxYear=" + maxYear +
                ", genreIds=" + genreIds +
                ", countryIds=" + countryIds +
                ", minRating=" + minRating +
                ", maxRating=" + maxRating +
                '}';
    }
}
