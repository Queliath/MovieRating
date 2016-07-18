package by.epam.movierating.domain;

import java.util.Date;

/**
 * Created by Владислав on 12.06.2016.
 */
public class Comment {
    private int id;
    private String title;
    private String content;
    private Date dateOfPublication;
    private int userId;
    private int movieId;

    private User user;
    private Movie movie;

    public Comment() {
    }

    public Comment(String title, String content, Date dateOfPublication, int userId, int movieId) {
        this.title = title;
        this.content = content;
        this.dateOfPublication = dateOfPublication;
        this.userId = userId;
        this.movieId = movieId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDateOfPublication() {
        return dateOfPublication;
    }

    public void setDateOfPublication(Date dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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

        Comment comment = (Comment) o;

        if (id != comment.id) return false;
        if (userId != comment.userId) return false;
        if (movieId != comment.movieId) return false;
        if (title != null ? !title.equals(comment.title) : comment.title != null) return false;
        if (content != null ? !content.equals(comment.content) : comment.content != null) return false;
        return dateOfPublication != null ? dateOfPublication.equals(comment.dateOfPublication) : comment.dateOfPublication == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (dateOfPublication != null ? dateOfPublication.hashCode() : 0);
        result = 31 * result + userId;
        result = 31 * result + movieId;
        return result;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", dateOfPublication=" + dateOfPublication +
                ", userId=" + userId +
                ", movieId=" + movieId +
                '}';
    }

}
