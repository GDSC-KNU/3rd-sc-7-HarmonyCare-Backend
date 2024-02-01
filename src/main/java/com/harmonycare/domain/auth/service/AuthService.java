package com.harmonycare.domain.auth.service;

import com.harmonycare.domain.auth.dto.request.LoginRequest;
import com.harmonycare.domain.auth.dto.response.LoginResponse;
import com.harmonycare.domain.auth.dto.response.Token;
import com.harmonycare.domain.member.service.MemberService;
import com.harmonycare.global.security.provider.JwtTokenProvider;
import com.harmonycare.global.util.OauthUtil;
import com.harmonycare.google.dto.UserResourceDto;
import com.harmonycare.google.service.Oauth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final Oauth2Service oauth2Service;
    private final MemberService memberService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public LoginResponse googleLogin(LoginRequest loginRequest) {
        String authcode = URLDecoder.decode(loginRequest.authcode(), StandardCharsets.UTF_8);

        String accessToken = oauth2Service.getGoogleAccessToken(authcode);

        UserResourceDto userResource = oauth2Service.getUserResource(accessToken);
        String email = userResource.email();
        boolean isFirstLogin = false;

        if (!memberService.existMemberByEmail(email)) {
            memberService.saveMember(email);
            isFirstLogin = true;
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userResource.email(), OauthUtil.oauthPasswordKey);

        Authentication authentication = authenticationManagerBuilder
                .getObject()
                .authenticate(authenticationToken);
        Token token = jwtTokenProvider.generateToken(authentication);

        return LoginResponse.builder()
                .isFirstLogin(isFirstLogin)
                .token(token)
                .build();
    }
}
