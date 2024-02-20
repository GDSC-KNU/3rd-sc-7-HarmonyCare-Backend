package com.harmonycare.domain.auth.service;

import com.harmonycare.domain.auth.dto.request.LoginRequest;
import com.harmonycare.domain.auth.dto.response.LoginResponse;
import com.harmonycare.domain.auth.dto.response.Token;
import com.harmonycare.domain.baby.service.BabyService;
import com.harmonycare.domain.member.service.MemberService;
import com.harmonycare.global.security.provider.JwtTokenProvider;
import com.harmonycare.global.util.OauthUtil;
import com.harmonycare.google.dto.UserResourceDto;
import com.harmonycare.google.service.Oauth2Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final Oauth2Service oauth2Service;
    private final BabyService babyService;
    private final MemberService memberService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public LoginResponse googleLogin(LoginRequest loginRequest) {
        String authcode = URLDecoder.decode(loginRequest.authcode(), StandardCharsets.UTF_8);
        System.out.println("authcode = " + authcode);

        String accessToken = oauth2Service.getGoogleAccessToken(authcode);

        UserResourceDto userResource = oauth2Service.getUserResource(accessToken);
        String email = userResource.email();
        String name = userResource.name();

        if (!memberService.existMemberByEmail(email))
            memberService.saveMember(email, name);

        boolean hasBaby = babyService.existBabyByMemberEmail(email);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userResource.email(), OauthUtil.oauthPasswordKey);

        Authentication authentication = authenticationManagerBuilder
                .getObject()
                .authenticate(authenticationToken);
        Token token = jwtTokenProvider.generateToken(authentication);

        return LoginResponse.builder()
                .hasBaby(hasBaby)
                .token(token)
                .build();
    }
}
