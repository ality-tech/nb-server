package org.neighbor.utils;

import org.neighbor.api.ErrorCode;
import org.neighbor.api.GeneralResponse;
import org.neighbor.api.JsonError;

public class ResponseGenerator {

    public static final GeneralResponse ORG_NOTEXIST_ERROR = generateOrgNotExistError();

    private final static GeneralResponse generateOrgNotExistError() {
        JsonError error = new JsonError();
        error.setCode(ErrorCode.ORG_WITH_EXTID_NOT_FOUND);
        error.setMessage("There is no org with given ext_id");
        GeneralResponse errorResponse = new GeneralResponse(400, error);
        return errorResponse;
    }
}
