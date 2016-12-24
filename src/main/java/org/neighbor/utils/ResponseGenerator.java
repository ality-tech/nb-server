package org.neighbor.utils;

import org.neighbor.api.ErrorCode;
import org.neighbor.api.GeneralResponse;
import org.neighbor.api.JsonError;

public class ResponseGenerator {

    public static final GeneralResponse OK = GeneralResponse.OK;
    public static final GeneralResponse CREATED = GeneralResponse.CREATED;
    public static final GeneralResponse ORG_NOTEXIST_ERROR = generateOrgNotExistError();
    public static final GeneralResponse ACCOUNT_NOTEXIST_ERROR = generateAccountNotExistError();
    public static final GeneralResponse ACCOUNT_BLOCKED_ERROR = generateAccountBlockedError();
    public static final GeneralResponse USER_ALREADY_EXIST_ERROR = generateUserActiveError();
    public static final GeneralResponse USER_BLOCKED_ERROR = generateUserBlockedError();
    public static final GeneralResponse USER_REGISTRATION_REQUESTED_ERROR = generateUserRequestedRegistrationError();
    public static final GeneralResponse ORG_BLOCKED_ERROR = generateOrgBlockedError();
    public static final GeneralResponse ORG_HAS_LINKED_ENTITIES_ERROR = generateOrgHasLinkedEntitiesError();
    public static final GeneralResponse ORG_ALREADY_EXISTS_ERROR = generateOrgAlreadyExistError();
    public static final GeneralResponse USER_NOT_FOUND_ERROR = generateUserNotFoundError();
    public static final GeneralResponse TOKEN_NOT_FOUND_ERROR = generateTokenNotFoundError();

    private static GeneralResponse generateTokenNotFoundError() {
        JsonError error = new JsonError();
        error.setCode(ErrorCode.TOKEN_NOT_FOUND);
        error.setMessage("No token for user found");
        GeneralResponse errorResponse = new GeneralResponse(400, error);
        return errorResponse;

    }

    private static GeneralResponse generateUserNotFoundError() {
        JsonError error = new JsonError();
        error.setCode(ErrorCode.USER_NOT_FOUND);
        error.setMessage("User with given login not found");
        GeneralResponse errorResponse = new GeneralResponse(400, error);
        return errorResponse;
    }

    private static GeneralResponse generateOrgAlreadyExistError() {
        JsonError error = new JsonError();
        error.setCode(ErrorCode.ORG_WITH_EXTID_ALREADY_EXISTS);
        error.setMessage("There is already exist org with given ext_id");
        GeneralResponse errorResponse = new GeneralResponse(400, error);
        return errorResponse;
    }

    private static GeneralResponse generateOrgHasLinkedEntitiesError() {
        JsonError error = new JsonError();
        error.setCode(ErrorCode.ORG_HAS_LINKED_ENTITIES);
        error.setMessage("There are exist non default accounts linked to given org");
        GeneralResponse errorResponse = new GeneralResponse(400, error);
        return errorResponse;
    }

    private static GeneralResponse generateOrgBlockedError() {
        JsonError error = new JsonError();
        error.setCode(ErrorCode.ORG_BLOCKED);
        error.setMessage("Org was blocked");
        GeneralResponse errorResponse = new GeneralResponse(400, error);
        return errorResponse;

    }

    private static GeneralResponse generateUserBlockedError() {
        JsonError error = new JsonError();
        error.setCode(ErrorCode.USER_BLOCKED);
        error.setMessage("user was blocked");
        return new GeneralResponse(400, error);
    }

    private static GeneralResponse generateUserRequestedRegistrationError() {
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
