package org.neighbor.server.controller;

import io.swagger.annotations.ApiParam;
import org.neighbor.api.GeneralResponse;
import org.neighbor.api.auth.AuthCheckRequest;
import org.neighbor.api.auth.AuthConfirmRequest;
import org.neighbor.api.auth.AuthRegisterRequest;
import org.neighbor.server.service.AuthService;
import org.neighbor.server.util.ResponseWrapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/auth", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/check")
    public void check() {
        authService.check();
    }

    @RequestMapping(value = "/register")
    public ResponseEntity<GeneralResponse> register(@RequestBody AuthRegisterRequest request) {
        GeneralResponse response = authService.register(request);
        return ResponseWrapUtil.wrap(response);
    }

    @RequestMapping(value = "/confirm")
    public ResponseEntity<GeneralResponse> confirm(@RequestBody AuthConfirmRequest confirmRequest) {
        return ResponseWrapUtil.wrap(authService.confirm(confirmRequest));
    }

    @RequestMapping(value = "/recovery")
    @ApiIgnore
    public void recovery() {
    }

}

