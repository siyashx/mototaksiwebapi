package com.codesupreme.mototaksiwebapi.api.webhook;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    @Value("${wasender.webhook.secret}")
    private String webhookSecret; // .env / properties-dən gəlir

    private boolean verifySignature(String signature) {
        // Wasender sənə "x-webhook-signature" başlığı göndərir.
        // Docs-da sadə bərabərlik göstərilib. (HMAC yoxdursa:)
        return signature != null && signature.equals(webhookSecret);
    }

    @PostMapping("/wasender")
    public ResponseEntity<Map<String, Object>> handleWebhook(
            @RequestHeader(value = "x-webhook-signature", required = false) String signature,
            @RequestBody Map<String, Object> payload
    ) {
        if (!verifySignature(signature)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid signature"));
        }

        String event = (String) payload.get("event");
        Map<String, Object> data = (Map<String, Object>) payload.get("data");

        // Sadə yönləndirmə
        if ("messages.upsert".equals(event)) {
            // Yeni gələn mesaj
            System.out.println("Yeni mesaj gəldi: " + data);
            // TODO: burada DB-yə yaz, queue-ya at və s.
        } else if ("session.status".equals(event)) {
            System.out.println("Session status: " + data);
        } else if ("message.sent".equals(event)) {
            System.out.println("Mesaj göndərildi: " + data);
        } else {
            System.out.println("Digər event: " + event + " -> " + data);
        }

        // Webhook cavabını tez qaytar
        return ResponseEntity.ok(Map.of("received", true));
    }
}
