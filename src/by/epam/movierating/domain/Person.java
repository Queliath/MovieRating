package by.epam.movierating.domain;

import java.util.Date;
import java.util.List;

/**
 * Created by Владислав on 12.06.2016.
 */
public class Person {
    private int id;
    private String name;
    private Date dateOfBirth;
    private String placeOfBirth;
    private String photo;

    private int moviesTotal;
    private List<Movie> moviesAsActor;
    private List<Movie> moviesAsDirector;
    private List<Movie> moviesAsProducer;
    private List<Movie> moviesAsWriter;
    private List<Movie> moviesAsPainter;
    private List<Movie> moviesAsOperator;
    private List<Movie> moviesAsEditor;
    private List<Movie> moviesAsComposer;

    public Person() {
    }

    public Person(String name, Date dateOfBirth, String placeOfBirth, String photo) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.placeOfBirth = placeOfBirth;
        this.photo = photo;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getMoviesTotal() {
        return moviesTotal;
    }

    public void setMoviesTotal(int moviesTotal) {
        this.moviesTotal = moviesTotal;
    }

    public List<Movie> getMoviesAsActor() {
        return moviesAsActor;
    }

    public void setMoviesAsActor(List<Movie> moviesAsActor) {
        this.moviesAsActor = moviesAsActor;
    }

    public List<Movie> getMoviesAsDirector() {
        return moviesAsDirector;
    }

    public void setMoviesAsDirector(List<Movie> moviesAsDirector) {
        this.moviesAsDirector = moviesAsDirector;
    }

    public List<Movie> getMoviesAsProducer() {
        return moviesAsProducer;
    }

    public void setMoviesAsProducer(List<Movie> moviesAsProducer) {
        this.moviesAsProducer = moviesAsProducer;
    }

    public List<Movie> getMoviesAsWriter() {
        return moviesAsWriter;
    }

    public void setMoviesAsWriter(List<Movie> moviesAsWriter) {
        this.moviesAsWriter = moviesAsWriter;
    }

    public List<Movie> getMoviesAsPainter() {
        return moviesAsPainter;
    }

    public void setMoviesAsPainter(List<Movie> moviesAsPainter) {
        this.moviesAsPainter = moviesAsPainter;
    }

    public List<Movie> getMoviesAsOperator() {
        return moviesAsOperator;
    }

    public void setMoviesAsOperator(List<Movie> moviesAsOperator) {
        this.moviesAsOperator = moviesAsOperator;
    }

    public List<Movie> getMoviesAsEditor() {
        return moviesAsEditor;
    }

    public void setMoviesAsEditor(List<Movie> moviesAsEditor) {
        this.moviesAsEditor = moviesAsEditor;
    }

    public List<Movie> getMoviesAsComposer() {
        return moviesAsComposer;
    }

    public void setMoviesAsComposer(List<Movie> moviesAsComposer) {
        this.moviesAsComposer = moviesAsComposer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (id != person.id) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(person.dateOfBirth) : person.dateOfBirth != null) return false;
        if (placeOfBirth != null ? !placeOfBirth.equals(person.placeOfBirth) : person.placeOfBirth != null)
            return false;
        return photo != null ? photo.equals(person.photo) : person.photo == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (placeOfBirth != null ? placeOfBirth.hashCode() : 0);
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", placeOfBirth='" + placeOfBirth + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
