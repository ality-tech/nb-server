package org.neighbor.controller;

import org.neighbor.api.GeneralResponse;
import org.neighbor.api.dtos.*;
import org.neighbor.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public GeneralResponse update(UpdateOrgRequest updateOrgRequest) {
        return orgService.update(updateOrgRequest);
    }

    @RequestMapping(value = "/delete")
    public GeneralResponse delete(DeleteOrgRequest deleteOrgRequest) {
        return orgService.delete(deleteOrgRequest);
    }

    @RequestMapping(value = "/list")
    public List<OrgDto> list() {
        return orgService.findAll();
    }

    @RequestMapping(value = "/get-by-id")
    public OrgDto getById(GetOrgByIdRequest id) {
        return orgService.getById(id);
    }

}
