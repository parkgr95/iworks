package com.example.iworks.global.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class OpenViduUtil {
    @Value("${openvidu.host}")
    private String OPENVIDU_SERVER_URL;
    private final String SECRET_KEY;

    private final SecretKeyUtil secretKeyUtil;
    public OpenViduUtil(String secretKey, SecretKeyUtil secretKeyUtil) {
        this.secretKeyUtil = secretKeyUtil;
        SECRET_KEY = secretKeyUtil.getOpenViduSecretKey();
    }

    public int getSessionClientsNumber(String sessionId) throws JsonProcessingException {
        System.out.println(SECRET_KEY);
        URI uri = UriComponentsBuilder
                .fromUriString(OPENVIDU_SERVER_URL)
                .path("/openvidu/api/sessions/{sessionId}/connection")
                .encode()
                .buildAndExpand(sessionId)
                .toUri();
        System.out.println(uri);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",SECRET_KEY);

        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET,httpEntity,String.class);
        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        return jsonNode.get("numberOfElements").asInt();
    }
}
