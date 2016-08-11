package by.epam.movierating.domain;

import java.util.Date;
import java.util.List;

/**
 * Created by Владислав on 12.06.2016.
 */
public class User {
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Date dateOfRegistry;
    private String photo;
    private int rating;
    private String status;
    private String languageId;

    private List<Comment> comments;

    public User() {
    }

    public User(String email, String password, String firstName, String lastName,
                Date dateOfRegistry, String photo, int rating, String status) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfRegistry = dateOfRegistry;
        this.photo = photo;
        this.rating = rating;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfRegistry() {
        return dateOfRegistry;
    }

    public void setDateOfRegistry(Date dateOfRegistry) {
        this.dateOfRegistry = dateOfRegistry;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
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

        User user = (User) o;

        if (id != user.id) return false;
        if (rating != user.rating) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (dateOfRegistry != null ? !dateOfRegistry.equals(user.dateOfRegistry) : user.dateOfRegistry != null)
            return false;
        if (photo != null ? !photo.equals(user.photo) : user.photo != null) return false;
        return status != null ? status.equals(user.status) : user.status == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (dateOfRegistry != null ? dateOfRegistry.hashCode() : 0);
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        result = 31 * result + rating;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfRegistry=" + dateOfRegistry +
                ", photo='" + photo + '\'' +
                ", rating=" + rating +
                ", status='" + status + '\'' +
                '}';
    }
}
