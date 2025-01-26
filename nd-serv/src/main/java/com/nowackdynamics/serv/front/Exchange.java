package com.nowackdynamics.serv.front;

import com.nowackdynamics.serv.framework.request.BaseRequest;
import com.nowackdynamics.serv.framework.response.BaseResponse;
import com.nowackdynamics.serv.framework.response.external.ErrorResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class Exchange {

    @Value("${server.security_token}")
    private String securityKey;

    @Value("${server.datastore_address}")
    private String datastoreAddress;

    public ResponseEntity<? extends BaseResponse> exchangeSync(String endpoint, BaseRequest request) throws RestClientException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<? extends BaseRequest> forwardingRequest;
        ResponseEntity<? extends BaseResponse> response;

        httpHeaders.set("Content-Type", "application/json");
        httpHeaders.set("Authorization", securityKey);

        forwardingRequest = new HttpEntity<>(request, httpHeaders);

        try {
            response = restTemplate.exchange(datastoreAddress + endpoint, HttpMethod.POST, forwardingRequest, BaseResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RestClientException(e.getLocalizedMessage());
        }
        return response;
    }

    @Async
    public void exchangeAsync(String endpoint, BaseRequest request) {
        exchangeSync(endpoint, request);
    }
}
