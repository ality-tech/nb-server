package org.neighbor.server.service.impl;

import org.neighbor.api.ActivationStatus;
import org.neighbor.api.GeneralResponse;
import org.neighbor.api.RoleEnum;
import org.neighbor.api.TokenStatus;
import org.neighbor.api.dtos.AuthCheckRequest;
import org.neighbor.api.dtos.AuthConfirmRequest;
import org.neighbor.api.dtos.AuthRegisterRequest;
import org.neighbor.server.entity.*;
import org.neighbor.server.repository.*;
import org.neighbor.server.service.AccountService;
import org.neighbor.server.service.AuthService;
import org.neighbor.server.service.UserService;
import org.neighbor.server.utils.ResponseGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
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
    @Autowired
    private UserHistoryRepository userHistoryRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final static SecureRandom random  = new SecureRandom();

    @Override
    public GeneralResponse check(AuthCheckRequest request) {
        return ResponseGenerator.OK;
    }

    @Override
    public GeneralResponse register(AuthRegisterRequest request) {
        String login = request.getLogin();
        Optional<NeighborUserEntity> userByLogin = userService.getUserByLogin(login);

        //todo cover by tests
        if (userByLogin.isPresent()) {
            return generateUserExistResponse(userByLogin.get());
        }

        Optional<NeighborOrgEntity> orgById = orgRepository.findByExtId(request.getExtId());
        if (!orgById.isPresent()) {
            return ResponseGenerator.generateOrgNotExistError();
        }

        NeighborOrgEntity org = orgById.get();
        //todo cover by tests
        if (!Boolean.TRUE.equals(org.getActive())) {
            return ResponseGenerator.generateOrgBlockedError();
        }

        Optional<NeighborAccountEntity> accountById = accountService.findOrgAndAccountNumber(org.getId(), request.getAccountNumber());
        if (!accountById.isPresent()) {
            return ResponseGenerator.generateAccountNotExistError();
        }

        NeighborAccountEntity account = accountById.get();
        if (!Boolean.TRUE.equals(account.getActive())) {
            return ResponseGenerator.generateAccountBlockedError();
        }

        NeighborUserEntity user = persistUser(request, account);
        persistHistory(user);
        NeighborActivationTokenEntity token = persistToken(user);

        NeighborRoleEntity role = new NeighborRoleEntity();
        role.setUserRole(RoleEnum.ROLE_NB_USER);
        role.setUser(user);
        roleRepository.save(role);

        GeneralResponse ok = new GeneralResponse();
        ok.setHttpCode(201);
        ok.setToken(token.getToken());
        return ok;
    }

    private NeighborUserEntity persistUser(AuthRegisterRequest request, NeighborAccountEntity account) {
        String login = request.getLogin();
        NeighborUserEntity user = new NeighborUserEntity();
        user.setAccount(account);
        user.setLogin(request.getLogin());
        user.setPinCode(passwordEncoder.encode(request.getPinCode()));
        user.setCreatedOn(new Date());
        user.setActivationStatus(ActivationStatus.REQUESTED);
        user.setUserPhone(request.getUserPhone());
        String urn = account.getAccountUrn() + ":" + login;
        user.setUserUrn(urn);
        userRepository.save(user);
        return user;
    }

    private NeighborActivationTokenEntity persistToken(NeighborUserEntity user) {
        NeighborActivationTokenEntity token = new NeighborActivationTokenEntity();
        token.setCreatedOn(new Date());
        token.setTokenStatus(TokenStatus.SENT);
        token.setToken(generateRandomPin());
        token.setUser(user);
        tokenRepository.save(token);
        return token;
    }

    private String generateRandomPin() {
        byte[] bytes = new byte[5];
        random.nextBytes(bytes);
        long value = 0;
        for (int i = 0; i < bytes.length; i++)
        {
            value += ((long) bytes[i] & 0xffL) << (8 * i);
        }
        return String.valueOf(value).substring(0, 5);
    }

    private NeighborUserStatusHistoryEntity persistHistory(NeighborUserEntity user) {
        NeighborUserStatusHistoryEntity history = new NeighborUserStatusHistoryEntity();
        history.setActivationStatus(user.getActivationStatus());
        history.setCreatedOn(new Date());
        history.setUser(user);
        userHistoryRepository.save(history);
        return history;
    }

    //todo cover by tests
    private GeneralResponse generateUserExistResponse(NeighborUserEntity user) {
        if (user.getActivationStatus() == null) {
            return ResponseGenerator.generateUserActiveError();
        }
        switch (user.getActivationStatus()) {
            case BLOCKED:
                return ResponseGenerator.generateUserBlockedError();
            case REQUESTED:
                return ResponseGenerator.generateUserRequestedRegistrationError();
            case ACTIVE:
            default:
                return ResponseGenerator.generateUserActiveError();
        }
    }

    @Override
    public GeneralResponse confirm(AuthConfirmRequest confirmRequest) {

        String login = confirmRequest.getLogin();
        Optional<NeighborActivationTokenEntity> foundToken = tokenRepository.findByToken(confirmRequest.getToken());
        if (!foundToken.isPresent())
            return ResponseGenerator.generateSecurityViolationError();
        NeighborActivationTokenEntity token = foundToken.get();

        if (TokenStatus.SENT != token.getTokenStatus()){
            return ResponseGenerator.generateSecurityViolationError();
        }

        Optional<NeighborUserEntity> userByLogin = userService.getUserByLogin(login);
        if (!userByLogin.isPresent())
            return ResponseGenerator.generateSecurityViolationError();

        NeighborUserEntity user = userByLogin.get();
        if (!user.getId().equals(token.getUserId())){
            return ResponseGenerator.generateSecurityViolationError();
        }

        //ok
        token.setTokenStatus(TokenStatus.CONFIRMED);
        NeighborUserStatusHistoryEntity history = new NeighborUserStatusHistoryEntity();
        history.setCreatedOn(new Date());
        history.setUser(user);
        history.setActivationStatus(ActivationStatus.ACTIVE);
        userHistoryRepository.save(history);

        user.setActivationStatus(ActivationStatus.ACTIVE);
        user.setUpdatedOn(new Date());
        userRepository.save(user);
        GeneralResponse ok = new GeneralResponse();
        ok.setHttpCode(201);
        return ok;
    }
}
