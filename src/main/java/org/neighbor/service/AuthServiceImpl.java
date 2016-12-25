package org.neighbor.service;

import org.neighbor.api.ErrorCode;
import org.neighbor.api.GeneralResponse;
import org.neighbor.api.JsonError;
import org.neighbor.api.dtos.AuthCheckRequest;
import org.neighbor.api.dtos.AuthConfirmRequest;
import org.neighbor.api.dtos.AuthRegisterRequest;
import org.neighbor.entity.*;
import org.neighbor.repository.*;
import org.neighbor.utils.ResponseGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

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
    @Autowired
    private UserHistoryRepository userHistoryRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public GeneralResponse check(AuthCheckRequest request) {

        String decoded = new String(Base64Utils.decodeFromString(request.getHash()));
        String[] strings = decoded.split(SEPARATOR);
        //todo cover by tests
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

        //todo cover by tests
        NeighborUser user = userOptional.get();
        ActivationStatus activationStatus = user.getActivationStatus();
        if (activationStatus == ActivationStatus.BLOCKED) {
            return ResponseGenerator.USER_BLOCKED_ERROR;
        }

        return ResponseGenerator.OK;
    }

    @Override
    public GeneralResponse register(AuthRegisterRequest request) {
        String login = request.getLogin();
        Optional<NeighborUser> userByLogin = userService.getUserByLogin(login);

        //todo cover by tests
        if (userByLogin.isPresent()) {
            return generateUserExistResponse(userByLogin.get());
        }

        Optional<NeighborOrg> orgById = orgRepository.findByExtId(request.getExtId());
        if (!orgById.isPresent()) {
            return ResponseGenerator.ORG_NOTEXIST_ERROR;
        }

        NeighborOrg org = orgById.get();
        //todo cover by tests
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

        NeighborUser user = persistUser(request, account);
        persistHistory(user);
        NeighborActivationToken token = persistToken(user);

        NeighborRole role = new NeighborRole();
        role.setUserRole(RoleEnum.ROLE_NB_USER);
        role.setUser(user);
        roleRepository.save(role);

        GeneralResponse ok = new GeneralResponse();
        ok.setHttpCode(201);
        ok.setToken(token.getToken());
        return ok;
    }

    private NeighborUser persistUser(AuthRegisterRequest request, NeighborAccount account) {
        String login = request.getLogin();
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
        return user;
    }

    private NeighborActivationToken persistToken(NeighborUser user) {
        NeighborActivationToken token = new NeighborActivationToken();
        token.setCreatedOn(new Date());
        token.setTokenStatus(TokenStatus.SENT);
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        tokenRepository.save(token);
        return token;
    }

    private NeighborUserStatusHistory persistHistory(NeighborUser user) {
        NeighborUserStatusHistory history = new NeighborUserStatusHistory();
        history.setActivationStatus(user.getActivationStatus());
        history.setCreatedOn(new Date());
        history.setUser(user);
        userHistoryRepository.save(history);
        return history;
    }

    //todo cover by tests
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

    @Override
    public GeneralResponse confirm(AuthConfirmRequest confirmRequest) {
        String login = confirmRequest.getLogin();
        Optional<NeighborUser> userByLogin = userService.getUserByLogin(login);
        if (!userByLogin.isPresent())
            return ResponseGenerator.USER_NOT_FOUND_ERROR;

        NeighborUser user = userByLogin.get();
        Optional<NeighborActivationToken> tokenByUser = tokenRepository.findLastByUserId(user.getId());
        if (!tokenByUser.isPresent())
            return ResponseGenerator.TOKEN_NOT_FOUND_ERROR;

        //ok
        NeighborActivationToken token = tokenByUser.get();
        token.setTokenStatus(TokenStatus.CONFIRMED);
        NeighborUserStatusHistory history = new NeighborUserStatusHistory();
        history.setCreatedOn(new Date());
        history.setUser(user);
        history.setActivationStatus(ActivationStatus.ACTIVE);
        userHistoryRepository.save(history);

        user.setActivationStatus(ActivationStatus.ACTIVE);
        user.setUpdatedOn(new Date());
        userRepository.save(user);
        GeneralResponse ok = new GeneralResponse();
        ok.setHttpCode(201);
        ok.setToken(token.getToken());
        return ok;
    }
}
