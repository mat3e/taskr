package io.github.mat3e.jhipster.taskr.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Worker is a business representation of the system user.
 */
@ApiModel(description = "Worker is a business representation of the system user.")
@Document(collection = "worker")
public class Worker implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @NotNull
    @Size(min = 1)
    @Field("name")
    private String name;

    @NotNull
    @Size(min = 1)
    @Field("surname")
    private String surname;

    @NotNull
    @Field("user_login")
    private String userLogin;

    @NotNull
    @Field("user_email")
    private String userEmail;

    @NotNull
    @Field("job_id")
    private String jobId;

    @NotNull
    @Field("job_title")
    private String jobTitle;

    /**
     * Authority helps assigning tasks.
     * Worker can assign tasks only to workers with the lower authority level.
     * 
     * Authority level defined on the worker overrides the one defined on the worker group.
     */
    @ApiModelProperty(value = "Authority helps assigning tasks. Worker can assign tasks only to workers with the lower authority level. Authority level defined on the worker overrides the one defined on the worker group.")
    @Field("authority_lvl")
    private Integer authorityLvl;

    @NotNull
    @Size(min = 1)
    @Field("city")
    private String city;

    @NotNull
    @Size(min = 1)
    @Field("street")
    private String street;

    @NotNull
    @Size(min = 1)
    @Field("house")
    private String house;

    @Field("apartment")
    private String apartment;

    @NotNull
    @Size(min = 6, max = 6)
    @Field("postal_code")
    private String postalCode;

    @NotNull
    @Size(min = 1)
    @Field("post")
    private String post;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Worker name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public Worker surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public Worker userLogin(String userLogin) {
        this.userLogin = userLogin;
        return this;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Worker userEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getJobId() {
        return jobId;
    }

    public Worker jobId(String jobId) {
        this.jobId = jobId;
        return this;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public Worker jobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
        return this;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Integer getAuthorityLvl() {
        return authorityLvl;
    }

    public Worker authorityLvl(Integer authorityLvl) {
        this.authorityLvl = authorityLvl;
        return this;
    }

    public void setAuthorityLvl(Integer authorityLvl) {
        this.authorityLvl = authorityLvl;
    }

    public String getCity() {
        return city;
    }

    public Worker city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public Worker street(String street) {
        this.street = street;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public Worker house(String house) {
        this.house = house;
        return this;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getApartment() {
        return apartment;
    }

    public Worker apartment(String apartment) {
        this.apartment = apartment;
        return this;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Worker postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPost() {
        return post;
    }

    public Worker post(String post) {
        this.post = post;
        return this;
    }

    public void setPost(String post) {
        this.post = post;
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
        Worker worker = (Worker) o;
        if (worker.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), worker.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Worker{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            ", userLogin='" + getUserLogin() + "'" +
            ", userEmail='" + getUserEmail() + "'" +
            ", jobId='" + getJobId() + "'" +
            ", jobTitle='" + getJobTitle() + "'" +
            ", authorityLvl='" + getAuthorityLvl() + "'" +
            ", city='" + getCity() + "'" +
            ", street='" + getStreet() + "'" +
            ", house='" + getHouse() + "'" +
            ", apartment='" + getApartment() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", post='" + getPost() + "'" +
            "}";
    }
}
