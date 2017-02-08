package org.neighbor.server.controller;

import org.neighbor.api.GeneralResponse;
import org.neighbor.api.dtos.UserDto;
import org.neighbor.server.service.UserService;
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
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserService userService;

    /*@RequestMapping(value = "/get-by-id", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeneralResponse> create(@RequestBody CreateUserRequest request) {
        AccountUrn accountUrn = null;
        try {
            accountUrn = new AccountUrn(request.getAccountUrn());
        } catch (RuntimeException e) {
            return new ResponseEntity<GeneralResponse>(HttpStatus.BAD_REQUEST);
        }
        return ResponseWrapUtil.wrap(userService.create(request, accountUrn));
    }*/

    @RequestMapping(value = "/get-by-id", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getUserById(@RequestBody String urn) {
        UserDto userByUrn = userService.getUserByUrn(urn);
        if (userByUrn != null) {
            return new ResponseEntity<>(userByUrn, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/get-by-filter", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiIgnore
    public ResponseEntity<List<UserDto>> getUserByFilter(@RequestBody Object filter) {
        return new ResponseEntity<>(userService.getUsersByFilter(filter), HttpStatus.I_AM_A_TEAPOT);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiIgnore
    public ResponseEntity<GeneralResponse> update(@RequestBody String urn) {
        return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    }

}
