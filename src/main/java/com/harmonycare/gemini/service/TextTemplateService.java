package com.harmonycare.gemini.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.harmonycare.gemini.dto.request.GeminiRequestDTO;
import lombok.RequiredArgsConstructor;
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

    private static final String API_URL = "https://us-central1-aiplatform.googleapis.com/v1/projects/sylvan-surf-411905/locations/us-central1/publishers/google/models/text-bison:predict";

    public String provideTip(GeminiRequestDTO geminiRequestDTO)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer ya29.a0AfB_byDszRHFER2Hc2CFSYHsqWfrj2nn1MmeQgQLgIfiAawuIrqPLo80CeJJGYf4Fdp8IlMC_8OXS-WZ3XWDKGS51qBucVfVdh7-VAcEzoQLn21igtG5frgIWnfIUusiZwm7ZvzgzMQBCIHUkDQFLa0ADd7aa1ZxmwWV49-BqAaCgYKARISARISFQHGX2MiTbF81jpEtAeLi0ArZ2h9SA0177");

        String jsonBody = "{ \"instances\": [ { \"prompt\": \"" + geminiRequestDTO.prompt() + "\" } ], \"parameters\": { \"temperature\": 1, \"maxOutputTokens\": 100, \"topP\": 0.9, \"topK\": 40 } }";
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

       ResponseEntity<JsonNode> response = restTemplate.exchange(
                API_URL,
                HttpMethod.POST,
                requestEntity,
                JsonNode.class
        );

        String tip = response.getBody().get("predictions").get(0).get("content").asText().replaceAll("\\*", "");

        return tip;
    }
}
