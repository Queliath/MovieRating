package by.epam.movierating.domain.criteria;

import java.util.List;

/**
 * Created by Владислав on 07.08.2016.
 */
public class UserCriteria {
    private String email;
    private String firstName;
    private String lastName;
    private String minDateOfRegistry;
    private String maxDateOfRegistry;
    private Integer minRating;
    private Integer maxRating;
    private List<String> statuses;

    public UserCriteria() {
    }

    public UserCriteria(String email, String firstName, String lastName, String minDateOfRegistry, String maxDateOfRegistry, Integer minRating, Integer maxRating, List<String> statuses) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.minDateOfRegistry = minDateOfRegistry;
        this.maxDateOfRegistry = maxDateOfRegistry;
        this.minRating = minRating;
        this.maxRating = maxRating;
        this.statuses = statuses;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getMaxDateOfRegistry() {
        return maxDateOfRegistry;
    }

    public void setMaxDateOfRegistry(String maxDateOfRegistry) {
        this.maxDateOfRegistry = maxDateOfRegistry;
    }

    public String getMinDateOfRegistry() {
        return minDateOfRegistry;
    }

    public void setMinDateOfRegistry(String minDateOfRegistry) {
        this.minDateOfRegistry = minDateOfRegistry;
    }

    public Integer getMinRating() {
        return minRating;
    }

    public void setMinRating(Integer minRating) {
        this.minRating = minRating;
    }

    public Integer getMaxRating() {
        return maxRating;
    }

    public void setMaxRating(Integer maxRating) {
        this.maxRating = maxRating;
    }

    public List<String> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<String> statuses) {
        this.statuses = statuses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserCriteria that = (UserCriteria) o;

        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (minDateOfRegistry != null ? !minDateOfRegistry.equals(that.minDateOfRegistry) : that.minDateOfRegistry != null)
            return false;
        if (maxDateOfRegistry != null ? !maxDateOfRegistry.equals(that.maxDateOfRegistry) : that.maxDateOfRegistry != null)
            return false;
        if (minRating != null ? !minRating.equals(that.minRating) : that.minRating != null) return false;
        if (maxRating != null ? !maxRating.equals(that.maxRating) : that.maxRating != null) return false;
        return statuses != null ? statuses.equals(that.statuses) : that.statuses == null;

    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (minDateOfRegistry != null ? minDateOfRegistry.hashCode() : 0);
        result = 31 * result + (maxDateOfRegistry != null ? maxDateOfRegistry.hashCode() : 0);
        result = 31 * result + (minRating != null ? minRating.hashCode() : 0);
        result = 31 * result + (maxRating != null ? maxRating.hashCode() : 0);
        result = 31 * result + (statuses != null ? statuses.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserCriteria{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", minDateOfRegistry='" + minDateOfRegistry + '\'' +
                ", maxDateOfRegistry='" + maxDateOfRegistry + '\'' +
                ", minRating=" + minRating +
                ", maxRating=" + maxRating +
                ", statuses=" + statuses +
                '}';
    }
}
