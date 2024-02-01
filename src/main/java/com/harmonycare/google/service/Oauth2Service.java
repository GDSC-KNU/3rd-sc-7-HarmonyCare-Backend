package com.harmonycare.google.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.harmonycare.global.exception.GlobalException;
import com.harmonycare.google.dto.UserResourceDto;
import com.harmonycare.google.exception.Oauth2ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class Oauth2Service {
    @Value("${spring.oauth2.google.token-uri}")
    private String GOOGLE_TOKEN_URI;
    @Value("${spring.oauth2.google.resource-uri}")
    private String GOOGLE_RESOURCE_URI;
    @Value("${spring.oauth2.google.client-id}")
    private String GOOGLE_CLIENT_ID;
    @Value("${spring.oauth2.google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;
    @Value("${spring.oauth2.google.redirect-uri}")
    private String GOOGLE_REDIRECT_URL;

    private final RestTemplate restTemplate;

    public String getGoogleAccessToken(String authcode) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("code", authcode);
        params.add("client_id", GOOGLE_CLIENT_ID);
        params.add("client_secret", GOOGLE_CLIENT_SECRET);
        params.add("redirect_uri", GOOGLE_REDIRECT_URL);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        ResponseEntity<JsonNode> responseEntity = restTemplate.exchange(
                GOOGLE_TOKEN_URI,
                HttpMethod.POST,
                entity,
                JsonNode.class
        );
        JsonNode accessTokenNode = responseEntity.getBody();

        if (responseEntity.getStatusCode() != HttpStatus.OK || accessTokenNode == null){
            log.error(responseEntity.toString());
            throw new GlobalException(Oauth2ErrorCode.FAILED_GET_ACCESS_TOKEN);
        }

        return accessTokenNode.get("access_token").asText();
    }

    public UserResourceDto getUserResource(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(
                GOOGLE_RESOURCE_URI,
                HttpMethod.GET,
                entity,
                UserResourceDto.class
        ).getBody();
    }
}
