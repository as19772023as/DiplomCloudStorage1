package ru.strebkov.DiplomCloudStorage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.strebkov.DiplomCloudStorage.model.dto.request.LoginAuth;
import ru.strebkov.DiplomCloudStorage.model.dto.response.GetToken;
import ru.strebkov.DiplomCloudStorage.service.AuthService;


@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/login")
    public GetToken login(@RequestBody LoginAuth login) {
        return authService.login(login);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("auth-token") String authToken) {
        authService.logout(authToken);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
