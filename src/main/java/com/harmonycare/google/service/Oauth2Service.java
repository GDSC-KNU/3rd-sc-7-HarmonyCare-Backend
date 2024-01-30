package com.harmonycare.google.service;

import com.harmonycare.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class Oauth2Service {
    @Value("${spring.oauth2.google.token-uri}")
    private String GOOGLE_TOKEN_URL;
    @Value("${spring.oauth2.google.client-id}")
    private String GOOGLE_CLIENT_ID;
    @Value("${spring.oauth2.google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;
    @Value("${spring.oauth2.google.redirect-uri}")
    private String GOOGLE_REDIRECT_URL;

    private final RestTemplate restTemplate;

    public ResponseEntity<String> getGoogleAccessToken(String authcode) {
        Map<String, String> params = new HashMap<>();

        params.put("code", authcode);
        params.put("client_id", GOOGLE_CLIENT_ID);
        params.put("client_secret", GOOGLE_CLIENT_SECRET);
        params.put("redirect_uri", GOOGLE_REDIRECT_URL);
        params.put("grant_type", "authorization_code");

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(GOOGLE_TOKEN_URL, params, String.class);

        if(responseEntity.getStatusCode() == HttpStatus.OK){
            return null;
//            throw new GlobalException()
        }
        return responseEntity;
    }
}
