package org.neighbor.server.controller;

import org.neighbor.api.*;
import org.neighbor.api.organizaton.*;
import org.neighbor.server.service.OrgService;
import org.neighbor.server.util.ResponseWrapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping(value = "/org",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
public class OrgController {

    @Autowired
    private OrgService orgService;

    @ApiIgnore
    @RequestMapping(value = "/create")
    public ResponseEntity<GeneralResponse> create(@RequestBody CreateOrgRequest request) {
        GeneralResponse response = orgService.create(request);
        return ResponseWrapUtil.wrap(response);
    }

    @ApiIgnore
    @RequestMapping(value = "/update")
    public ResponseEntity<GeneralResponse> update(UpdateOrgRequest updateOrgRequest) {
        return ResponseWrapUtil.wrap(orgService.update(updateOrgRequest));
    }

    @ApiIgnore
    @RequestMapping(value = "/delete")
    public ResponseEntity<GeneralResponse> delete(DeleteOrgRequest deleteOrgRequest) {
        return ResponseWrapUtil.wrap(orgService.delete(deleteOrgRequest));
    }

    @RequestMapping(value = "/list")
    public ResponseEntity<List<OrgDto>> list() {
        return ResponseEntity.ok(orgService.findAll());
    }

    @ApiIgnore
    @RequestMapping(value = "/get-by-id")
    public ResponseEntity<OrgDto> getById(GetOrgByIdRequest id) {
        OrgDto dto = orgService.getById(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
