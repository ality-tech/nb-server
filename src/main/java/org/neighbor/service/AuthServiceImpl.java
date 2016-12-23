package org.neighbor.service;

import org.neighbor.api.ErrorCode;
import org.neighbor.api.GeneralResponse;
import org.neighbor.api.JsonError;
import org.neighbor.api.dtos.AuthCheckRequest;
import org.neighbor.api.dtos.AuthRegisterRequest;
import org.neighbor.entity.ActivationStatus;
import org.neighbor.entity.NeighborAccount;
import org.neighbor.entity.NeighborOrg;
import org.neighbor.entity.NeighborUser;
import org.neighbor.repository.OrgRepository;
import org.neighbor.repository.UserRepository;
import org.neighbor.utils.ResponseGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrgRepository orgRepository;
    @Autowired
    private AccountService accountService;

    @Override
    public GeneralResponse check(AuthCheckRequest request) {

        String decoded = new String(Base64Utils.decodeFromString(request.getHash()));
        String[] strings = decoded.split(SEPARATOR);
        if (strings.length != 2) {
            JsonError error = new JsonError();
            error.setCode(ErrorCode.BAD_FORMED_CREDENTIALS);
            error.setMessage("could not check your request");
            return new GeneralResponse(400, error);
        }

        String login = strings[0];
        String pin = strings[1];

        Optional<NeighborUser> userOptional = userService.getUserByLoginAndPin(login, pin);
        if (!userOptional.isPresent()) {
            JsonError error = new JsonError();
            error.setCode(ErrorCode.USER_NOT_REGISTERED);
            error.setMessage("user not registered or you have wrong pin");
            return new GeneralResponse(400, error);
        }

        NeighborUser user = userOptional.get();
        ActivationStatus activationStatus = user.getActivationStatus();
        if (activationStatus == ActivationStatus.BLOCKED) {
            JsonError error = new JsonError();
            error.setCode(ErrorCode.USER_BLOCKED);
            error.setMessage("user was blocked");
            return new GeneralResponse(400, error);
        }

        return GeneralResponse.OK;
    }

    @Override
    public GeneralResponse register(AuthRegisterRequest request) {
        String login = request.getLogin();
        Optional<NeighborUser> userByLogin = userService.getUserByLogin(login);

        if (userByLogin.isPresent()) {
            return generateUserExistResponse(userByLogin.get());
        }

        Optional<NeighborOrg> orgById = orgRepository.findByExtId(request.getExtId());
        if (!orgById.isPresent()) {
            return ResponseGenerator.ORG_NOTEXIST_ERROR;
        }

        NeighborOrg org = orgById.get();
        if (!Boolean.TRUE.equals(org.getActive())) {
            return ResponseGenerator.ORG_BLOCKED_ERROR;
        }

        Optional<NeighborAccount> accountById = accountService.findOrgAndAccountNumber(org.getId(), request.getAccountNumber());
        if (!accountById.isPresent()) {
            return ResponseGenerator.ACCOUNT_NOTEXIST_ERROR;
        }

        NeighborAccount account = accountById.get();
        if (!Boolean.TRUE.equals(account.getActive())) {
            return ResponseGenerator.ACCOUNT_BLOCKED_ERROR;
        }

        NeighborUser user = new NeighborUser();
        user.setAccount(account);
        user.setLogin(request.getLogin());
        user.setPinCode(request.getPinCode());
        user.setCreatedOn(new Date());
        user.setActivationStatus(ActivationStatus.REQUESTED);
        user.setUserPhone(request.getUserPhone());
        String urn = account.getAccountUrn() + ":" + login;
        user.setUserUrn(urn);
        userRepository.save(user);
        return new GeneralResponse(201, null);
    }

    private GeneralResponse generateOrgNotExistError() {
        JsonError error = new JsonError();
        error.setCode(ErrorCode.ORG_WITH_EXTID_NOT_FOUND);
        error.setMessage("org number doesn't exist");
        return new GeneralResponse(400, error);
    }

    private GeneralResponse generateUserExistResponse(NeighborUser user) {
        switch (user.getActivationStatus()) {
            case BLOCKED:
                return ResponseGenerator.USER_BLOCKED_ERROR;
            case REQUESTED:
                return ResponseGenerator.USER_REGISTRATION_REQUESTED_ERROR;
            case ACTIVE:
            default:
                return ResponseGenerator.USER_ALREADY_EXIST_ERROR;
        }
    }


}
