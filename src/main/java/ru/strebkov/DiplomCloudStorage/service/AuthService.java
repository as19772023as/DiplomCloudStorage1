package ru.strebkov.DiplomCloudStorage.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.strebkov.DiplomCloudStorage.jwt.UtilJwt;
import ru.strebkov.DiplomCloudStorage.model.dto.request.LoginAuth;
import ru.strebkov.DiplomCloudStorage.model.dto.response.GetToken;
import ru.strebkov.DiplomCloudStorage.model.entity.User;
import ru.strebkov.DiplomCloudStorage.repository.AuthRepository;
import ru.strebkov.DiplomCloudStorage.repository.LoginRepository;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {
    private AuthRepository authRepository;
    private AuthenticationManager authManager;
    private UtilJwt utilJwt;
    private UserService userService;
    private final LoginRepository loginRepository;

    public GetToken login(LoginAuth loginAuth) {
        final String userName = loginAuth.getLogin();
        final String password = loginAuth.getPassword();

        authManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));

        final UserDetails userDetails = userService.loadUserByUsername(userName);
        final String token = utilJwt.generateToken(userDetails);

        authRepository.saveTokenAndUser(token, userName);
        log.info("User {} authenticate. JWT: {}", userName, token);
        return new GetToken(token);
    }


    public void logout(String token) {
        final String userToken = token.substring(7);
        final String userName = authRepository.getUsernameByToken(userToken);
        log.info("User {} logout, JWT is disabled", userName);
        authRepository.removeTokenAndUsername(token);
    }

    public User getUserFromToken(String token) {
        if (token.startsWith("Bearer ")) {
            String authTokenBearer = token.split(" ")[1];
            String userName = authRepository.getUsernameByToken(authTokenBearer);
            return loginRepository.findByUsername(userName);
        }
        return null;
    }
}
