package org.neighbor.server.entity;

import com.google.common.base.MoreObjects;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "neighbor_job_category")
public class JobCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ext_id")
    private String extId;

    @ManyToOne(targetEntity = JobTypeEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", insertable = false, updatable = false)
    private JobTypeEntity type;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExtId() {
        return extId;
    }

    public void setExtId(String extId) {
        this.extId = extId;
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

    public JobTypeEntity getType() {
        return type;
    }

    public void setType(JobTypeEntity type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JobCategoryEntity that = (JobCategoryEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(extId, that.extId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(isEnabled, that.isEnabled);
    }


    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                extId,
                name,
                description,
                isEnabled);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("extId", extId)
                .add("name", name)
                .add("description", description)
                .add("isEnabled", isEnabled)
                .toString();
    }
}
