package org.neighbor.service;

import org.neighbor.api.GeneralResponse;
import org.neighbor.api.dtos.AuthCheckRequest;
import org.neighbor.api.dtos.AuthConfirmRequest;
import org.neighbor.api.dtos.AuthRegisterRequest;
import org.neighbor.entity.*;
import org.neighbor.repository.*;
import org.neighbor.utils.ResponseGenerator;
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
    private final static SecureRandom random  = new SecureRandom();;

    @Override
    public GeneralResponse check(AuthCheckRequest request) {
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
            return ResponseGenerator.generateOrgNotExistError();
        }

        NeighborOrg org = orgById.get();
        //todo cover by tests
        if (!Boolean.TRUE.equals(org.getActive())) {
            return ResponseGenerator.generateOrgBlockedError();
        }

        Optional<NeighborAccount> accountById = accountService.findOrgAndAccountNumber(org.getId(), request.getAccountNumber());
        if (!accountById.isPresent()) {
            return ResponseGenerator.generateAccountNotExistError();
        }

        NeighborAccount account = accountById.get();
        if (!Boolean.TRUE.equals(account.getActive())) {
            return ResponseGenerator.generateAccountBlockedError();
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
        user.setPinCode(passwordEncoder.encode(request.getPinCode()));
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
        Optional<NeighborActivationToken> foundToken = tokenRepository.findByToken(confirmRequest.getToken());
        if (!foundToken.isPresent())
            return ResponseGenerator.generateSecurityViolationError();
        NeighborActivationToken token = foundToken.get();

        if (TokenStatus.SENT != token.getTokenStatus()){
            return ResponseGenerator.generateSecurityViolationError();
        }

        Optional<NeighborUser> userByLogin = userService.getUserByLogin(login);
        if (!userByLogin.isPresent())
            return ResponseGenerator.generateSecurityViolationError();

        NeighborUser user = userByLogin.get();
        if (!user.getId().equals(token.getUserId())){
            return ResponseGenerator.generateSecurityViolationError();
        }

        //ok
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
        return ok;
    }
}
