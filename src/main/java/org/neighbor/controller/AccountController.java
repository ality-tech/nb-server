package org.neighbor.controller;

import org.neighbor.api.GeneralResponse;
import org.neighbor.api.dtos.CreateAccountRequest;
import org.neighbor.entity.NeighborAccount;
import org.neighbor.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/account", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/create")
    public GeneralResponse create(@RequestParam("account") CreateAccountRequest createAccountRequest) {
        return accountService.createAccount(createAccountRequest);
    }

    @RequestMapping(value = "/update")
    public NeighborAccount update(@RequestParam("account") Object account) {
        return new NeighborAccount();
    }

    @RequestMapping(value = "/delete")
    public NeighborAccount delete(@RequestParam("account") Object account) {
        return new NeighborAccount();
    }

    @RequestMapping(value = "/list-by-filter")
    public Iterable<NeighborAccount> listByFilter(@RequestParam("filter") Object filter) {
        return accountService.listByFilter(filter);
    }


}
