package by.bsuir.movierating.controller.request;

import java.io.Serializable;

public class BaseRequest implements Serializable {
    protected Integer userId;
    protected String userStatus;
    protected String languageId;
    protected String previousUri;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public String getPreviousUri() {
        return previousUri;
    }

    public void setPreviousUri(String previousUri) {
        this.previousUri = previousUri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseRequest that = (BaseRequest) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (userStatus != null ? !userStatus.equals(that.userStatus) : that.userStatus != null) return false;
        if (languageId != null ? !languageId.equals(that.languageId) : that.languageId != null) return false;
        return !(previousUri != null ? !previousUri.equals(that.previousUri) : that.previousUri != null);

    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (userStatus != null ? userStatus.hashCode() : 0);
        result = 31 * result + (languageId != null ? languageId.hashCode() : 0);
        result = 31 * result + (previousUri != null ? previousUri.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BaseRequest{" +
                "userId=" + userId +
                ", userStatus='" + userStatus + '\'' +
                ", languageId='" + languageId + '\'' +
                ", previousUri='" + previousUri + '\'' +
                '}';
    }
}
