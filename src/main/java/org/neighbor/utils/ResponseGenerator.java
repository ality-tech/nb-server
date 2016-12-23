package org.neighbor.utils;

import org.neighbor.api.ErrorCode;
import org.neighbor.api.GeneralResponse;
import org.neighbor.api.JsonError;

public class ResponseGenerator {

    public static final GeneralResponse ORG_NOTEXIST_ERROR = generateOrgNotExistError();
    public static final GeneralResponse ACCOUNT_NOTEXIST_ERROR = generateAccountNotExistError();
    public static final GeneralResponse ACCOUNT_BLOCKED_ERROR = generateAccountBlockedError();
    public static final GeneralResponse USER_ALREADY_EXIST_ERROR = generateUserActiveError();
    public static final GeneralResponse USER_BLOCKED_ERROR = generateUserBlockedError();
    public static final GeneralResponse USER_REGISTRATION_REQUESTED_ERROR = generateUserBlockedError();
    public static final GeneralResponse ORG_BLOCKED_ERROR = generateOrgBlockedError();

    private static GeneralResponse generateOrgBlockedError() {
        JsonError error = new JsonError();
        error.setCode(ErrorCode.ORG_BLOCKED);
        error.setMessage("Org was blocked");
        GeneralResponse errorResponse = new GeneralResponse(400, error);
        return errorResponse;

    }

    private static GeneralResponse generateUserBlockedError() {
        JsonError error = new JsonError();
        error.setCode(ErrorCode.REGISTRATION_REQUESTED);
        error.setMessage("Registration for User already requested");
        GeneralResponse errorResponse = new GeneralResponse(400, error);
        return errorResponse;
    }

    private static GeneralResponse generateUserActiveError() {
        JsonError error = new JsonError();
        error.setCode(ErrorCode.USER_ACTIVE);
        error.setMessage("User active");
        GeneralResponse errorResponse = new GeneralResponse(400, error);
        return errorResponse;

    }

    private static GeneralResponse generateAccountBlockedError() {
        JsonError error = new JsonError();
        error.setCode(ErrorCode.ACCOUNT_IS_BLOCKED);
        error.setMessage("Account with given id is blocked");
        GeneralResponse errorResponse = new GeneralResponse(400, error);
        return errorResponse;
    }

    private final static GeneralResponse generateOrgNotExistError() {
        JsonError error = new JsonError();
        error.setCode(ErrorCode.ORG_WITH_EXTID_NOT_FOUND);
        error.setMessage("There is no org with given ext_id");
        GeneralResponse errorResponse = new GeneralResponse(400, error);
        return errorResponse;
    }

    private final static GeneralResponse generateAccountNotExistError() {
        JsonError error = new JsonError();
        error.setCode(ErrorCode.ACCOUNT_NUMBER_NOT_EXIST);
        error.setMessage("account number doesn't exist");
        return new GeneralResponse(400, error);
    }
}
