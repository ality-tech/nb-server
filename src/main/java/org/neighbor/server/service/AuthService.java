package org.neighbor.server.service;

import org.neighbor.api.GeneralResponse;
import org.neighbor.api.auth.AuthCheckRequest;
import org.neighbor.api.auth.AuthConfirmRequest;
import org.neighbor.api.auth.AuthRegisterRequest;

public interface AuthService {
    String SEPARATOR = ":";

    void check();

    GeneralResponse register(AuthRegisterRequest request);

    GeneralResponse confirm(AuthConfirmRequest confirmRequest);
}
