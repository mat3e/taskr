package io.github.mat3e.jhipster.taskr.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import io.github.mat3e.jhipster.taskr.domain.enumeration.TaskStatus;

/**
 * Central application structure.
 */
@ApiModel(description = "Central application structure.")
@Document(collection = "task")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    /**
     * Date when sender sent the message.
     */
    @ApiModelProperty(value = "Date when sender sent the message.")
    @Field("dispatch_date")
    private ZonedDateTime dispatchDate;

    /**
     * Date when recipient read the message.
     */
    @ApiModelProperty(value = "Date when recipient read the message.")
    @Field("read_date")
    private ZonedDateTime readDate;

    /**
     * Assigned user.
     */
    @ApiModelProperty(value = "Assigned user.")
    @Field("user_login")
    private String userLogin;

    /**
     * Assigned user e-mail. Denormalization.
     */
    @ApiModelProperty(value = "Assigned user e-mail. Denormalization.")
    @Field("user_email")
    private String userEmail;

    @Field("status")
    private TaskStatus status;

    @NotNull
    @Size(min = 1)
    @Field("title")
    private String title;

    @NotNull
    @Size(min = 1)
    @Field("body")
    private String body;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ZonedDateTime getDispatchDate() {
        return dispatchDate;
    }

    public Task dispatchDate(ZonedDateTime dispatchDate) {
        this.dispatchDate = dispatchDate;
        return this;
    }

    public void setDispatchDate(ZonedDateTime dispatchDate) {
        this.dispatchDate = dispatchDate;
    }

    public ZonedDateTime getReadDate() {
        return readDate;
    }

    public Task readDate(ZonedDateTime readDate) {
        this.readDate = readDate;
        return this;
    }

    public void setReadDate(ZonedDateTime readDate) {
        this.readDate = readDate;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public Task userLogin(String userLogin) {
        this.userLogin = userLogin;
        return this;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Task userEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public Task status(TaskStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public Task title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public Task body(String body) {
        this.body = body;
        return this;
    }

    public void setBody(String body) {
        this.body = body;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        if (task.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), task.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Task{" +
            "id=" + getId() +
            ", dispatchDate='" + getDispatchDate() + "'" +
            ", readDate='" + getReadDate() + "'" +
            ", userLogin='" + getUserLogin() + "'" +
            ", userEmail='" + getUserEmail() + "'" +
            ", status='" + getStatus() + "'" +
            ", title='" + getTitle() + "'" +
            ", body='" + getBody() + "'" +
            "}";
    }
}
