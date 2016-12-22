package org.neighbor.service;

import org.neighbor.api.GeneralResponse;
import org.neighbor.api.dtos.AuthCheckRequest;

public interface AuthService {
    String SEPARATOR = ":";

    GeneralResponse check(AuthCheckRequest request);
}
