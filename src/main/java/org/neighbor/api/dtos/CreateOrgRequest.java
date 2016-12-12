package org.neighbor.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.Objects;

public class CreateOrgRequest {
    @JsonProperty("ext_id")
    private String extId;
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateOrgRequest that = (CreateOrgRequest) o;
        return Objects.equals(extId, that.extId) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(extId, name);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("extId", extId)
                .add("name", name)
                .toString();
    }
}
