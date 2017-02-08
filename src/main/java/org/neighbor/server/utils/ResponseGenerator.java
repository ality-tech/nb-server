package org.neighbor.server.utils;

import org.neighbor.api.ErrorCode;
import org.neighbor.api.GeneralResponse;
import org.neighbor.api.JsonError;

public class ResponseGenerator {

    public static final GeneralResponse OK = GeneralResponse.OK;
    public static final GeneralResponse CREATED = GeneralResponse.CREATED;

    public static GeneralResponse generateUnauthorizedError() {
        JsonError error = new JsonError();
        error.setCode(ErrorCode.UNAUTHORIZED);
        error.setMessage("User is not authorized");
        GeneralResponse errorResponse = new GeneralResponse(401, error);
        return errorResponse;
    }

    public static GeneralResponse generateSecurityViolationError() {
        JsonError error = new JsonError();
        error.setCode(ErrorCode.SECURITY_VIOLATION);
        error.setMessage("No token for user found");
        GeneralResponse errorResponse = new GeneralResponse(400, error);
        return errorResponse;
    }

    public static GeneralResponse generateOrgAlreadyExistError() {
        JsonError error = new JsonError();
        error.setCode(ErrorCode.ORG_WITH_EXTID_ALREADY_EXISTS);
        error.setMessage("There is already exist org with given ext_id");
        GeneralResponse errorResponse = new GeneralResponse(400, error);
        return errorResponse;
    }

    public static GeneralResponse generateOrgHasLinkedEntitiesError() {
        JsonError error = new JsonError();
        error.setCode(ErrorCode.ORG_HAS_LINKED_ENTITIES);
        error.setMessage("There are exist non default accounts linked to given org");
        GeneralResponse errorResponse = new GeneralResponse(400, error);
        return errorResponse;
    }

    public static GeneralResponse generateOrgBlockedError() {
        JsonError error = new JsonError();
        error.setCode(ErrorCode.ORG_BLOCKED);
        error.setMessage("Org was blocked");
        GeneralResponse errorResponse = new GeneralResponse(400, error);
        return errorResponse;

    }

    public static GeneralResponse generateUserBlockedError() {
        JsonError error = new JsonError();
        error.setCode(ErrorCode.USER_BLOCKED);
        error.setMessage("user was blocked");
        return new GeneralResponse(400, error);
    }

    public static GeneralResponse generateUserRequestedRegistrationError() {
        JsonError error = new JsonError();
        error.setCode(ErrorCode.REGISTRATION_REQUESTED);
        error.setMessage("Registration for User already requested");
        GeneralResponse errorResponse = new GeneralResponse(400, error);
        return errorResponse;
    }

    public static GeneralResponse generateUserActiveError() {
        JsonError error = new JsonError();
        error.setCode(ErrorCode.USER_ACTIVE);
        error.setMessage("User active");
        GeneralResponse errorResponse = new GeneralResponse(400, error);
        return errorResponse;

    }

    public static GeneralResponse generateAccountBlockedError() {
        JsonError error = new JsonError();
        error.setCode(ErrorCode.ACCOUNT_IS_BLOCKED);
        error.setMessage("Account with given id is blocked");
        GeneralResponse errorResponse = new GeneralResponse(400, error);
        return errorResponse;
    }

    public final static GeneralResponse generateOrgNotExistError() {
        JsonError error = new JsonError();
        error.setCode(ErrorCode.ORG_WITH_EXTID_NOT_FOUND);
        error.setMessage("There is no org with given ext_id");
        GeneralResponse errorResponse = new GeneralResponse(400, error);
        return errorResponse;
    }

    public final static GeneralResponse generateAccountNotExistError() {
        JsonError error = new JsonError();
        error.setCode(ErrorCode.ACCOUNT_NUMBER_NOT_EXIST);
        error.setMessage("account number doesn't exist");
        return new GeneralResponse(400, error);
    }
}
