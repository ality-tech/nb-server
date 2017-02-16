package org.neighbor.server.entity;

import com.google.common.base.MoreObjects;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "neighbor_job_type")
public class JobTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Column(name = "org_ext_id")
    private String orgExtId;

    @Column(name = "ext_id")
    private String extId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public String getOrgExtId() {
        return orgExtId;
    }

    public void setOrgExtId(String orgExtId) {
        this.orgExtId = orgExtId;
    }

    public String getExtId() {
        return extId;
    }

    public void setExtId(String extId) {
        this.extId = extId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JobTypeEntity that = (JobTypeEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(extId, that.extId) &&
                Objects.equals(orgExtId, that.orgExtId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(isEnabled, that.isEnabled);
    }


    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                extId,
                orgExtId,
                name,
                isEnabled);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("extId", extId)
                .add("orgExtId", orgExtId)
                .add("name", name)
                .add("isEnabled", isEnabled)
                .toString();
    }
}
