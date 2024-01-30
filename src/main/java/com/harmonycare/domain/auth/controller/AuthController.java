package com.harmonycare.domain.auth.controller;

import com.harmonycare.domain.auth.dto.request.LoginRequest;
import com.harmonycare.domain.auth.dto.response.Token;
import com.harmonycare.domain.auth.service.AuthService;
import com.harmonycare.global.util.ApiUtil;
import com.harmonycare.global.util.ApiUtil.ApiSuccessResult;
import com.harmonycare.google.service.Oauth2Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final Oauth2Service oauth2Service;

    @PostMapping("/login")
    public ResponseEntity<ApiSuccessResult<Token>> login(@RequestBody @Valid LoginRequest loginRequest) {
        Token token = authService.login(loginRequest);
        String authorization = token.grantType() + " " + token.accessToken();

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, authorization)
                .body(ApiUtil.success(HttpStatus.OK, token));
    }

    @PostMapping("/google")
    public ResponseEntity<?> googleLogin(@RequestParam("code") String authcode) {
        return oauth2Service.getGoogleAccessToken(authcode);
    }
}
