package org.neighbor.api.organizaton;

public class GetOrgByIdRequest {
    private String id;//extId

    public GetOrgByIdRequest() {
    }

    public GetOrgByIdRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
