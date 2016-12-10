package org.neighbor.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
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

