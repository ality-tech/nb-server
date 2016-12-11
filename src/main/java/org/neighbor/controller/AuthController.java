package org.neighbor.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    @RequestMapping(value = "/check")
    public void check() {
    }

    @RequestMapping(value = "/register")
    public void register() {
    }

    @RequestMapping(value = "/confirm")
    public void confirm() {
    }

    @RequestMapping(value = "/recovery")
    public void recovery() {
    }

}

