package org.neighbor.controller;

import org.neighbor.entity.NeighborAccount;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/account", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

    @RequestMapping(value = "/create")
    public NeighborAccount create(@RequestParam("account") Object account) {
        return new NeighborAccount();
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
    public NeighborAccount listByFilter(@RequestParam("filter") Object account) {
        return new NeighborAccount();
    }


}
