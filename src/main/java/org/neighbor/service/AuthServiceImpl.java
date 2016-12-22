package org.neighbor.service;

import org.neighbor.api.ErrorCode;
import org.neighbor.api.GeneralResponse;
import org.neighbor.api.JsonError;
import org.neighbor.api.dtos.AuthCheckRequest;
import org.neighbor.entity.ActivationStatus;
import org.neighbor.entity.NeighborUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserService userService;

    @Override
    public GeneralResponse check(AuthCheckRequest request) {

        String decoded = new String(Base64Utils.decodeFromString(request.getHash()));
        String[] strings = decoded.split(SEPARATOR);
        if (strings.length != 2) {
            JsonError error = new JsonError();
            error.setCode(ErrorCode.BAD_FORMED_CREDENTIALS);
            error.setMessage("could not check your request");
            return new GeneralResponse(400, error);
        }

        String login = strings[0];
        String pin = strings[1];

        Optional<NeighborUser> userOptional = userService.getUserByLoginAndPin(login, pin);
        if (!userOptional.isPresent()) {
            JsonError error = new JsonError();
            error.setCode(ErrorCode.USER_NOT_REGISTERED);
            error.setMessage("user not registered or you have wrong pin");
            return new GeneralResponse(400, error);
        }

        NeighborUser user = userOptional.get();
        ActivationStatus activationStatus = user.getActivationStatus();
        if (activationStatus == ActivationStatus.BLOCKED) {
            JsonError error = new JsonError();
            error.setCode(ErrorCode.USER_BLOCKED);
            error.setMessage("user was blocked");
            return new GeneralResponse(400, error);
        }

        return GeneralResponse.OK;
    }
}
