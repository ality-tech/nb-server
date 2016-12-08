package org.neighbor.controller;

import org.neighbor.api.GeneralResponse;
import org.neighbor.api.dtos.CreateOrgRequest;
import org.neighbor.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/org",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
public class OrgController {

    @Autowired
    private OrgService orgService;

    @RequestMapping(value = "/create")
    public GeneralResponse create(@RequestBody CreateOrgRequest request) {
        return orgService.create(request);
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
