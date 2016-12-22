package org.neighbor.api;

import com.google.common.base.MoreObjects;

import java.util.Objects;

public class GeneralResponse {

    private int httpCode;
    private JsonError jsonError;
    public static GeneralResponse OK = new GeneralResponse(200, null);
    public static GeneralResponse CREATED = new GeneralResponse(201, null);

    public GeneralResponse() {
    }

    public GeneralResponse(int httpCode, JsonError jsonError) {
        this.httpCode = httpCode;
        this.jsonError = jsonError;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public JsonError getJsonError() {
        return jsonError;
    }

    public void setJsonError(JsonError jsonError) {
        this.jsonError = jsonError;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneralResponse that = (GeneralResponse) o;
        return httpCode == that.httpCode &&
                Objects.equals(jsonError, that.jsonError);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpCode, jsonError);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("httpCode", httpCode)
                .add("jsonError", jsonError)
                .toString();
    }
}
