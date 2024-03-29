package com.harmonycare.domain.auth.controller;

import com.harmonycare.domain.auth.dto.request.LoginRequest;
import com.harmonycare.domain.auth.dto.response.LoginResponse;
import com.harmonycare.domain.auth.service.AuthService;
import com.harmonycare.global.util.ApiUtil;
import com.harmonycare.global.util.ApiUtil.ApiSuccessResult;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "구글 로그인", description = "구글 OAuth2.0 로그인을 진행합니다.")
    @PostMapping("/login")
    public ResponseEntity<ApiSuccessResult<LoginResponse>> login(@RequestBody @Valid LoginRequest requestBody) {
        LoginResponse response = authService.googleLogin(requestBody);
        String authorization = response.token().grantType() + " " + response.token().accessToken();

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, authorization)
                .body(ApiUtil.success(HttpStatus.OK, response));
    }
}
