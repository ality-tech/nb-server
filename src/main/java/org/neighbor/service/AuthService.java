package org.neighbor.service;

import org.neighbor.api.GeneralResponse;
import org.neighbor.api.dtos.AuthCheckRequest;
import org.neighbor.api.dtos.AuthConfirmRequest;
import org.neighbor.api.dtos.AuthRegisterRequest;

public interface AuthService {
    String SEPARATOR = ":";

    GeneralResponse check(AuthCheckRequest request);

    GeneralResponse register(AuthRegisterRequest request);

    GeneralResponse confirm(AuthConfirmRequest confirmRequest);
}
