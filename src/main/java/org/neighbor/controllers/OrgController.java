package org.neighbor.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/org")
public class OrgController {

    @RequestMapping(value = "/create")
    public void create() {
    }

    @RequestMapping(value = "/update")
    public void update() {
    }

    @RequestMapping(value = "/delete")
    public void delete() {
    }

    @RequestMapping(value = "/list")
    public void list() {
    }

    @RequestMapping(value = "/get-by-id")
    public void getById() {
    }

}
