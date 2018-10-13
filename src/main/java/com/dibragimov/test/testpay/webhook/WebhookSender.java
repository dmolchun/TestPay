package com.dibragimov.test.testpay.webhook;

import com.dibragimov.test.testpay.dto.WebhookJson;
import com.dibragimov.test.testpay.webhook.db.WebhookHolder;
import com.dibragimov.test.testpay.webhook.db.WebhookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class WebhookSender {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledWebhookSender.class);
    private static final int MAX_COUNT = 25;

    private WebhookRepository repository;

    public WebhookSender(WebhookRepository repository) {
        this.repository = repository;
    }

    @Async
    public void sendWebhookAsync(WebhookHolder holder) {
        this.sendWebhook(holder);
    }

    /**
     * Trying to send webhook.
     * If unable - save to repository
     */
    public boolean sendWebhook(WebhookHolder holder) {
        if (!doSend(holder)) {
            holder.incrementCounter();
            repository.save(holder);
            return false;
        }
        return true;
    }

    /**
     * Try to resend all webhooks from repository
     */
    public void resend() {
        repository.findAll().forEach(this::resend);
    }

    /**
     * Try to resend webhook
     * If counter is more than MAX_COUNT or send is ok - remove holder from repository
     */
    private void resend(WebhookHolder holder) {
        if (MAX_COUNT < holder.getRetryCount() || sendWebhook(holder)) {
            repository.delete(holder);
        }

    }

    /**
     * Trying to send webhook
     *
     * @return true if ok
     */
    private boolean doSend(WebhookHolder holder) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

            String jsonWebhook = new ObjectMapper().writeValueAsString(WebhookJson.from(holder));
            HttpEntity<String> entity = new HttpEntity<>(jsonWebhook, headers);

            ResponseEntity<String> responseEntity =
                    restTemplate.exchange(holder.getNotificationUrl(), HttpMethod.POST, entity, String.class);

            return responseEntity.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            logger.error("Error sending webhook {}", holder, e);
            return false;
        }
    }
}
