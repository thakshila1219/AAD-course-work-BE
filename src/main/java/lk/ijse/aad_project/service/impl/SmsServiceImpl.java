package lk.ijse.aad_project.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lk.ijse.aad_project.service.SmsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class SmsServiceImpl implements SmsService {

    @Value("${txtmsg.api.url}")
    private String apiUrl;

    @Value("${txtmsg.api.token}")
    private String apiToken;

    @Value("${txtmsg.sender.id}")
    private String senderId;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void sendSms(String phoneNumber, String message) {

        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new RuntimeException("Customer phone number is not available.");
        }

        String recipient = phoneNumber.trim();

        // 0771234567 -> +94771234567
        if (recipient.startsWith("0")) {
            recipient = "+94" + recipient.substring(1);
        }

        Map<String, String> body = new HashMap<>();
        body.put("recipient", recipient);
        body.put("sender_id", senderId);
        body.put("type", "plain");
        body.put("message", message);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(java.util.List.of(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(apiToken);

        HttpEntity<Map<String, String>> request =
                new HttpEntity<>(body, headers);

        try {

            ResponseEntity<String> response =
                    restTemplate.exchange(
                            apiUrl,
                            HttpMethod.POST,
                            request,
                            String.class
                    );

            System.out.println("========== TXTMSG SMS ==========");
            System.out.println("RECIPIENT : " + recipient);
            System.out.println("STATUS    : " + response.getStatusCode());
            System.out.println("RESPONSE  : " + response.getBody());
            System.out.println("================================");

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException(
                        "SMS sending failed. HTTP status: "
                                + response.getStatusCode()
                );
            }

        } catch (Exception e) {

            System.out.println("========== SMS ERROR ==========");
            System.out.println(e.getMessage());
            System.out.println("===============================");

            throw new RuntimeException(
                    "Unable to send SMS: " + e.getMessage()
            );
        }
    }
}