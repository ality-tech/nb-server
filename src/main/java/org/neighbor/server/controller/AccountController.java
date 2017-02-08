package org.neighbor.server.controller;

import org.neighbor.api.GeneralResponse;
import org.neighbor.api.dtos.CreateAccountRequest;
import org.neighbor.server.entity.NeighborAccountEntity;
import org.neighbor.server.service.AccountService;
import org.neighbor.server.utils.ResponseWrapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/account", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
@ApiIgnore
public class AccountController {

    @Autowired
    private AccountService accountService;

    @ApiIgnore
    @RequestMapping(value = "/create")
    public ResponseEntity<GeneralResponse> create(@RequestBody CreateAccountRequest createAccountRequest) {
        return ResponseWrapUtil.wrap(accountService.createAccount(createAccountRequest));
    }

    @RequestMapping(value = "/update")
    @ApiIgnore
    public NeighborAccountEntity update(@RequestBody Object account) {
        return new NeighborAccountEntity();
    }

    @RequestMapping(value = "/delete")
    @ApiIgnore
    public NeighborAccountEntity delete(@RequestBody Object account) {
        return new NeighborAccountEntity();
    }

    @RequestMapping(value = "/list-by-filter")
    @ApiIgnore
    public Iterable<NeighborAccountEntity> listByFilter(@RequestBody Object filter) {
        return accountService.listByFilter(filter);
    }

}
