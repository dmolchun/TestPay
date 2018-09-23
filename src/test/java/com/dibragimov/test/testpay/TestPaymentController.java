package com.dibragimov.test.testpay;

import com.dibragimov.test.testpay.controller.PaymentController;
import com.dibragimov.test.testpay.dto.*;
import com.dibragimov.test.testpay.webhook.db.WebhookHolder;
import com.dibragimov.test.testpay.webhook.db.WebhookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPaymentController {

    @Autowired
    PaymentController controller;
    @Autowired
    WebhookRepository repository;

    @Test
    public void testPayment() {
        Payment payment = new Payment(
                Intent.ORDER,
                "someUrl",
                new Payer("email"),
                new Transaction(
                        new Amount("value", "currency")
                )
        );
        PaymentResponse response = controller.makePayment(payment);
        assertNotNull(response);
        List<WebhookHolder> holders = repository.findAll();
        assertEquals(1, holders.size());
        assertEquals(response.getId(), holders.get(0).getId());
        assertEquals(response.getState(), holders.get(0).getStatus());
        assertEquals(payment.getNotificationUrl(), holders.get(0).getNotificationUrl());
    }

}
