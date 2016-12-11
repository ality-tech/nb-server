package org.neighbor.controller;

import org.neighbor.api.JsonError;
import org.neighbor.api.dtos.UserDto;
import org.neighbor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/get-by-id", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto getUserById(@RequestBody String urn) {
        return userService.getUserByUrn(urn);
    }

    @RequestMapping(value = "/get-by-filter", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDto> getUserByFilter(@RequestBody Object filter) {
        return userService.getUsersByFilter(filter);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonError update(@RequestBody String urn) {
        return new JsonError();
    }

}
