package org.neighbor.api.organizaton;

import java.util.Objects;

public class OrgDto {

    private String name;
    private String extId;
    private Boolean isActive;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtId() {
        return extId;
    }

    public void setExtId(String extId) {
        this.extId = extId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrgDto orgDto = (OrgDto) o;
        return Objects.equals(name, orgDto.name) &&
                Objects.equals(extId, orgDto.extId) &&
                Objects.equals(isActive, orgDto.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, extId, isActive);
    }
}
