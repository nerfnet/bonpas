package com.nowackdynamics.serv.front.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
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
public class ReceiptMocks {

    @Autowired
    private MockMvc mock;

    @Test
    void test_CreateReceipt() throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("receiptId", UUID.randomUUID());
        data.put("receiptData", "RFJGRhtAGzMSG2EBHSERyc3Nzc3Nzc3Nzc3Nuwq6ICAgRVBTT04gICC6CrogICAdIQBUaGFuayB5b3UgHSERICAgugrIzc3Nzc3Nzc3Nzc28ChsyHSEAG0oETk9WRU1CRVIgMSwgMjAxMiAgMTA6MzAbZAMbYQBUTS1VeHh4ICAgICAgICAgICAgICAgICA2Ljc1ClRNLUh4eHggICAgICAgICAgICAgICAgNi4wMApQUy14eHggICAgICAgICAgICAgICAgMS43MAoKHSEBVE9UQUwgICAgICAgICAgICAgICAgMTQuNDUKHSEALS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tClBBSUQgICAgICAgICAgICAgICAgIDUwLjAwCkNIQU5HRSAgICAgICAgICAgICAgICAzNS41NQobcAACFB1rBCowMDAxNCoAHVZCAA==");
        data.put("analyticsInfo", Lists.newArrayList("Location: Home", "TerminalMod: A"));

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(data);

        mock.perform(MockMvcRequestBuilders.post("/api/receipt/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void test_ClaimReceipt() throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("userId", UUID.randomUUID());
        data.put("receiptId", UUID.fromString("2b05bfd5-c478-4689-8da2-a8b77d13b11a"));

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(data);

        mock.perform(MockMvcRequestBuilders.post("/api/receipt/claim")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
