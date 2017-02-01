package by.bsuir.movierating.domain.criteria;

import java.util.List;

public class MovieCriteria {
    private String name;
    private int minYear;
    private int maxYear;
    private List<Integer> genreIds;
    private List<Integer> countryIds;
    private int minRating;
    private int maxRating;
    private int from;
    private int amount;

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

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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
        if (from != that.from) return false;
        if (amount != that.amount) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (genreIds != null ? !genreIds.equals(that.genreIds) : that.genreIds != null) return false;
        return !(countryIds != null ? !countryIds.equals(that.countryIds) : that.countryIds != null);

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
        result = 31 * result + from;
        result = 31 * result + amount;
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
                ", from=" + from +
                ", amount=" + amount +
                '}';
    }
}
