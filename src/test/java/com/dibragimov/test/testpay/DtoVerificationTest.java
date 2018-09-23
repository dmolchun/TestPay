package com.dibragimov.test.testpay;

import com.dibragimov.test.testpay.dto.Intent;
import com.dibragimov.test.testpay.dto.Payment;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class DtoVerificationTest {
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testCorrectPaymentDeserialize() throws IOException {
        Payment payment = mapper.readValue(DtoVerificationTest.class.getResourceAsStream("/json/correctPayment.json"), Payment.class);
        assertEquals(Intent.ORDER, payment.getIntent());
        assertEquals("https://example.com/your_notification_url", payment.getNotificationUrl());
        assertEquals("test@example.com", payment.getPayer().getEmail());
        assertEquals("123456789", payment.getTransaction().getExternalId());
        assertEquals("7.47", payment.getTransaction().getAmount().getValue());
        assertEquals("USD", payment.getTransaction().getAmount().getCurrency());
        assertEquals("The payment transaction description", payment.getTransaction().getDescription());
    }

    @Test(expected = InvalidFormatException.class)
    public void testIncorrectIntentPaymentDeserialize() throws IOException {
         mapper.readValue(DtoVerificationTest.class.getResourceAsStream("/json/incorrectIntentPayment.json"), Payment.class);
    }

    @Test(expected = MismatchedInputException.class)
    public void noRequiredPropOnDeserialize() throws IOException {
         mapper.readValue(DtoVerificationTest.class.getResourceAsStream("/json/noRequiredProp.json"), Payment.class);
    }

    @Test(expected = MismatchedInputException.class)
    public void testIncorrectAmountValue() throws IOException {
         mapper.readValue(DtoVerificationTest.class.getResourceAsStream("/json/incorrectAmountValue.json"), Payment.class);
    }

    @Test(expected = MismatchedInputException.class)
    public void testIncorrectAmountCurrency() throws IOException {
         mapper.readValue(DtoVerificationTest.class.getResourceAsStream("/json/incorrectAmountCurrency.json"), Payment.class);
    }
}
