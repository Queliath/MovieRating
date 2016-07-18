package by.epam.movierating.domain;

import java.util.Date;
import java.util.List;

/**
 * Created by Владислав on 11.06.2016.
 */
public class Movie {
    private int id;
    private String name;
    private int year;
    private String tagline;
    private int budget;
    private Date premiere;
    private int lasting;
    private String annotation;
    private String image;

    private List<Country> countries;
    private List<Genre> genres;

    private List<Person> actors;
    private List<Person> directors;
    private List<Person> producers;
    private List<Person> writers;
    private List<Person> operators;
    private List<Person> painters;
    private List<Person> editors;
    private List<Person> composers;

    private double averageRating;
    private List<Comment> comments;

    public Movie() {
    }

    public Movie(String name, int year, String tagline,
                 int budget, Date premiere, int lasting, String annotation, String image) {
        this.name = name;
        this.year = year;
        this.tagline = tagline;
        this.budget = budget;
        this.premiere = premiere;
        this.lasting = lasting;
        this.annotation = annotation;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public Date getPremiere() {
        return premiere;
    }

    public void setPremiere(Date premiere) {
        this.premiere = premiere;
    }

    public int getLasting() {
        return lasting;
    }

    public void setLasting(int lasting) {
        this.lasting = lasting;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Person> getActors() {
        return actors;
    }

    public void setActors(List<Person> actors) {
        this.actors = actors;
    }

    public List<Person> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Person> directors) {
        this.directors = directors;
    }

    public List<Person> getProducers() {
        return producers;
    }

    public void setProducers(List<Person> producers) {
        this.producers = producers;
    }

    public List<Person> getWriters() {
        return writers;
    }

    public void setWriters(List<Person> writers) {
        this.writers = writers;
    }

    public List<Person> getOperators() {
        return operators;
    }

    public void setOperators(List<Person> operators) {
        this.operators = operators;
    }

    public List<Person> getPainters() {
        return painters;
    }

    public void setPainters(List<Person> painters) {
        this.painters = painters;
    }

    public List<Person> getEditors() {
        return editors;
    }

    public void setEditors(List<Person> editors) {
        this.editors = editors;
    }

    public List<Person> getComposers() {
        return composers;
    }

    public void setComposers(List<Person> composers) {
        this.composers = composers;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (id != movie.id) return false;
        if (year != movie.year) return false;
        if (budget != movie.budget) return false;
        if (lasting != movie.lasting) return false;
        if (name != null ? !name.equals(movie.name) : movie.name != null) return false;
        if (tagline != null ? !tagline.equals(movie.tagline) : movie.tagline != null) return false;
        if (premiere != null ? !premiere.equals(movie.premiere) : movie.premiere != null) return false;
        if (annotation != null ? !annotation.equals(movie.annotation) : movie.annotation != null) return false;
        return image != null ? image.equals(movie.image) : movie.image == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + year;
        result = 31 * result + (tagline != null ? tagline.hashCode() : 0);
        result = 31 * result + budget;
        result = 31 * result + (premiere != null ? premiere.hashCode() : 0);
        result = 31 * result + lasting;
        result = 31 * result + (annotation != null ? annotation.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", tagline='" + tagline + '\'' +
                ", budget=" + budget +
                ", premiere=" + premiere +
                ", lasting=" + lasting +
                ", annotation='" + annotation + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
