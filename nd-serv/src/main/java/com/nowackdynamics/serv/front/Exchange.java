package com.nowackdynamics.serv.front;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nowackdynamics.serv.framework.request.BaseRequest;
import com.nowackdynamics.serv.framework.response.BaseResponse;
import com.nowackdynamics.serv.framework.response.ErrorCodes;
import com.nowackdynamics.serv.framework.response.ResponseType;
import com.nowackdynamics.serv.framework.response.external.ErrorResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class Exchange {

    private static final RestTemplate restTemplate = new RestTemplate();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${server.security_token}")
    private String securityKey;

    @Value("${server.datastore_address}")
    private String datastoreAddress;

    public ResponseEntity<? extends BaseResponse> exchangeSync(String endpoint, BaseRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        httpHeaders.set("Authorization", securityKey);

        HttpEntity<? extends BaseRequest> forwardingRequest = new HttpEntity<>(request, httpHeaders);

        ResponseEntity<String> jsonResponse;
        try {
            jsonResponse = restTemplate.exchange(datastoreAddress + endpoint, HttpMethod.POST, forwardingRequest, String.class);
        } catch (HttpClientErrorException e) {
            BaseResponse response = transformResponseJson(e.getResponseBodyAsString());
            return ResponseEntity.status(e.getStatusCode()).body(response);
        }

        try {
            BaseResponse response = transformResponseJson(jsonResponse.getBody());
            return ResponseEntity.status(jsonResponse.getStatusCode()).body(response);
        } catch (Exception e) {
            return ErrorResponse.create("Exchange transform error", ErrorCodes.UNKNOWN_GENERIC);
        }
    }

    @Async
    public void exchangeAsync(String endpoint, BaseRequest request) {
        exchangeSync(endpoint, request);
    }

    private BaseResponse transformResponseJson(String json) {
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode responseTypeNode = rootNode.get("responseType");

            if (responseTypeNode == null || responseTypeNode.isNull()) {
                throw new RuntimeException("Invalid response; responseType missing");
            }

            String responseTypeString = responseTypeNode.asText();
            ResponseType responseType;
            try {
                responseType = ResponseType.valueOf(responseTypeString);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Unknown responseType: " + responseTypeString);
            }

            Class<? extends BaseResponse> responseClass = responseType.getResponseClass();
            return objectMapper.readValue(json, responseClass);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing JSON response", e);
        }
    }
}

