package org.neighbor.server.entity;

import com.google.common.base.MoreObjects;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "neighbor_job")
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @ManyToOne(targetEntity = JobCategoryEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private JobCategoryEntity category;

    @Column(name = "job_urn")
    private String jobUrn;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "is_Enabled")
    private Boolean isEnabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getJobUrn() {
        return jobUrn;
    }

    public void setJobUrn(String jobUrn) {
        this.jobUrn = jobUrn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public JobCategoryEntity getCategory() {
        return category;
    }

    public void setCategory(JobCategoryEntity category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JobEntity that = (JobEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(jobUrn, that.jobUrn) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(isEnabled, that.isEnabled);
    }


    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                jobUrn,
                name,
                description,
                isEnabled);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("jobUrn", jobUrn)
                .add("name", name)
                .add("description", description)
                .add("isEnabled", isEnabled)
                .toString();
    }

}
