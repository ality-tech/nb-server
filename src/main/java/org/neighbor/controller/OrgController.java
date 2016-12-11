package org.neighbor.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/org",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
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
