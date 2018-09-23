package com.dibragimov.test.testpay.controller;

import com.dibragimov.test.testpay.dto.Payment;
import com.dibragimov.test.testpay.dto.PaymentResponse;
import com.dibragimov.test.testpay.dto.State;
import com.dibragimov.test.testpay.dto.Transaction;
import com.dibragimov.test.testpay.webhook.WebhookSender;
import com.dibragimov.test.testpay.webhook.db.WebhookHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.UUID;

@RestController
public class PaymentController {
    private WebhookSender sender;

    public PaymentController(WebhookSender sender) {
        this.sender = sender;
    }

    private Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(value = "/payments/payment", consumes = {"application/json"})
    public PaymentResponse makePayment(@RequestBody Payment payment) {
        logger.info("Make payment request {}", payment);
        WebhookHolder holder = buildWebhook(payment);
        sender.sendWebhook(holder);
        return new PaymentResponse(holder.getId(), getFormattedNow(), State.CREATED);
    }

    /**
     * Generate webhook to send
     */
    private WebhookHolder buildWebhook(Payment payment) {
        Transaction transaction = payment.getTransaction();
        WebhookHolder holder = new WebhookHolder(
                UUID.randomUUID().toString(),
                transaction.getAmount().getCurrency(),
                transaction.getAmount().getValue(),
                transaction.getExternalId(),
                State.CREATED,
                payment.getNotificationUrl(),
                payment.getPayer().getEmail().toUpperCase()
        );
        addSha2(holder);
        return holder;
    }

    /**
     * Calculate and set sha-256 secret to holder
     */
    private void addSha2(WebhookHolder holder) {
        String secret = holder.asString();
        try {

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            secret = Arrays.toString(digest.digest(secret.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error generating sha-256 hash for {}", secret);
        }
        holder.setSha2sig(secret);
    }

    private String getFormattedNow() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return now.format(formatter);
    }

}
