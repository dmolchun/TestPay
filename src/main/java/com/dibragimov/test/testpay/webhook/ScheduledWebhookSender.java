package com.dibragimov.test.testpay.webhook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledWebhookSender {

    private static final Logger log = LoggerFactory.getLogger(ScheduledWebhookSender.class);

    private WebhookSender sender;

    public ScheduledWebhookSender(WebhookSender sender) {
        this.sender = sender;
    }

    @Scheduled(cron = "0 0 0/3 * * *")
    public void sendScheduled() {
        log.info("Try to resend webhooks");
        sender.resend();
    }
}
