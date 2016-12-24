package org.neighbor.controller;

import org.neighbor.api.GeneralResponse;
import org.neighbor.api.dtos.AuthCheckRequest;
import org.neighbor.api.dtos.AuthConfirmRequest;
import org.neighbor.api.dtos.AuthRegisterRequest;
import org.neighbor.service.AuthService;
import org.neighbor.utils.ResponseWrapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/check")
    public ResponseEntity<GeneralResponse> check(@RequestBody AuthCheckRequest request) {
        GeneralResponse response = authService.check(request);
        return ResponseWrapUtil.wrap(response);
    }

    @RequestMapping(value = "/register")
    public ResponseEntity<GeneralResponse> register(AuthRegisterRequest request) {
        GeneralResponse response = authService.register(request);
        return ResponseWrapUtil.wrap(response);
    }

    @RequestMapping(value = "/confirm")
    public ResponseEntity<GeneralResponse> confirm(AuthConfirmRequest confirmRequest) {
        return ResponseWrapUtil.wrap(authService.confirm(confirmRequest));
    }

    @RequestMapping(value = "/recovery")
    public void recovery() {
    }

}

