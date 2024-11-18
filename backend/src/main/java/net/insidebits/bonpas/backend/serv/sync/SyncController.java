package net.insidebits.bonpas.backend.serv2.sync;

import net.insidebits.bonpas.backend.serv2.crypto.MD5;
import net.insidebits.bonpas.backend.serv2.request.ServRequestHandler;
import net.insidebits.bonpas.backend.serv2.request.ServResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/sync")
@Validated
public class SyncController implements ServRequestHandler {

    @Value("${serv.db.backend}")
    private String forwardingAddress;

    @Value("${serv.db.backend_port}")
    private int forwardingPort;

    @Value("${serv.db.security_token}")
    private String securityToken;

    @PostMapping
    public ResponseEntity<ServResponse> sync(@Validated @RequestBody SyncRequest syncRequest) {
        String userId = syncRequest.getUserId();
        String receiptData = syncRequest.getData();
        String checksum = syncRequest.getChecksum();

        if(!MD5.verifyChecksum(receiptData, checksum)) {
            return ServResponse.INVALID_CHECKSUM;
        }

        // Any return value for a forward transmission is irrelevant,
        // as the client(app) works regardless of whether the backend database
        // server is available or not, at least at the time of a synchronization request that is.
        try {
            forwardTransmit(syncRequest);
        } catch (Exception e) {
            // Ignore any exceptions during transmission
        }
        return ServResponse.SYNC_ACCEPTED;
    }

    private void forwardTransmit(SyncRequest syncRequest) {
        RestTemplate rest = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        httpHeaders.set("Authorization", securityToken);

        HttpEntity<SyncRequest> forward = new HttpEntity<>(syncRequest, httpHeaders);

        ResponseEntity<Integer> response = rest.exchange(forwardingAddress, HttpMethod.POST, forward, Integer.class);

        if(response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Sync for '" + syncRequest.getUserId() + "' succeeded.");
        }

        if(response.getBody() == null) {
            return;
        }

        int code = response.getBody();
        System.err.println("Sync failed for '" + syncRequest.getUserId() + "', code: " + code);
    }

    @Override
    public String getEndpoint() {
        return "/sync";
    }
}
