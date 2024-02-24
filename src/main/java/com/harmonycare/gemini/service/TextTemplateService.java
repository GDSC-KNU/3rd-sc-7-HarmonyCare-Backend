package com.harmonycare.gemini.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.harmonycare.gemini.dto.request.GeminiRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Transactional
public class TextTemplateService {

    private final RestTemplate restTemplate;

    @Value("${api.key}")
    private String API_KEY;
    @Value("${api.url}")
    private String API_URL;

    public String provideTip(GeminiRequestDTO geminiRequestDTO)
    {
        String GOOGLE_GEMINI_URL = API_URL + API_KEY;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonBody = "{ \"contents\": [{ \"parts\":[{ \"text\": \"" + geminiRequestDTO.prompt() + "\" }]}]}";
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

        ResponseEntity<JsonNode> response = restTemplate.exchange(
                GOOGLE_GEMINI_URL,
                HttpMethod.POST,
                requestEntity,
                JsonNode.class
        );

        String tip = response.getBody().get("candidates").get(0)
                                       .get("content")
                                       .get("parts").get(0)
                                       .get("text").asText();

        return tip;
    }
}
