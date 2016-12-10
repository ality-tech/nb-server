package org.neighbor.controllers;

import org.neighbor.api.JsonError;
import org.neighbor.entity.NeighborUser;
import org.neighbor.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/get-by-id", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public NeighborUser getUserById(@RequestBody String urn) {
        return userService.getUserByUrn(urn);
    }

    @RequestMapping(value = "/get-by-filter", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<NeighborUser> getUserByFilter(@RequestParam("filter") Object urn) {
        return userService.getUsersByFilter(urn);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonError update(@RequestParam("id") String urn) {
        return new JsonError();
    }

}
