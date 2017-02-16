package org.neighbor.server.controller;

import org.neighbor.api.GeneralResponse;
import org.neighbor.api.auth.AuthConfirmRequest;
import org.neighbor.api.auth.AuthRegisterRequest;
import org.neighbor.api.user.UserDto;
import org.neighbor.lib.security.UserAwareController;
import org.neighbor.server.service.AuthService;
import org.neighbor.server.util.ResponseWrapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/auth", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController extends UserAwareController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/check")
    public void check(@ApiIgnore Authentication authentication) {
        LOG.debug("authentication: {}", authentication);
        // user must be resolved here
        UserDto authUser = getUser(authentication);
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

