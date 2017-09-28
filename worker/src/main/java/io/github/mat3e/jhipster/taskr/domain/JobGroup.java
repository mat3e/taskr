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
 * Generalization for the job, e.g. manager.
 */
@ApiModel(description = "Generalization for the job, e.g. manager.")
@Document(collection = "job_group")
public class JobGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    /**
     * E.g. manager, office worker
     */
    @NotNull
    @ApiModelProperty(value = "E.g. manager, office worker", required = true)
    @Field("name")
    private String name;

    /**
     * Authority helps assigning tasks.
     * Worker can assign tasks only to workers with the lower authority level.
     */
    @NotNull
    @ApiModelProperty(value = "Authority helps assigning tasks. Worker can assign tasks only to workers with the lower authority level.", required = true)
    @Field("authority_lvl")
    private Integer authorityLvl;

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

    public JobGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAuthorityLvl() {
        return authorityLvl;
    }

    public JobGroup authorityLvl(Integer authorityLvl) {
        this.authorityLvl = authorityLvl;
        return this;
    }

    public void setAuthorityLvl(Integer authorityLvl) {
        this.authorityLvl = authorityLvl;
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
        JobGroup jobGroup = (JobGroup) o;
        if (jobGroup.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jobGroup.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JobGroup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", authorityLvl='" + getAuthorityLvl() + "'" +
            "}";
    }
}
