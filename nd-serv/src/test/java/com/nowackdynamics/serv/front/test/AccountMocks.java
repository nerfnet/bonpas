package com.nowackdynamics.serv.front.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountMocks {

    @Autowired
    private MockMvc mock;

    @Test
    void test_CreateAccount() throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("userId", UUID.randomUUID());
        data.put("email", "giovanni@nowackdynamics.com");
        data.put("pin", "012345");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(data);

        mock.perform(MockMvcRequestBuilders.post("/api/account/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void test_UpdatePin() throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("userId", UUID.fromString("1900f674-3273-4004-a1a0-3a6a04294f42"));
        data.put("currentPin", "01234567");
        data.put("newPin", "76543210");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(data);

        mock.perform(MockMvcRequestBuilders.post("/api/account/updatepin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void test_UpdateEmail() throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("userId", UUID.fromString("1900f674-3273-4004-a1a0-3a6a04294f42"));
        data.put("pin", "76543210");
        data.put("newEmail", "developer@nowackdynamics.com");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(data);

        mock.perform(MockMvcRequestBuilders.post("/api/account/updateemail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
