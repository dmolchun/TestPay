package com.dibragimov.test.testpay.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AmountDeserializer extends StdDeserializer<Amount> {

    private static final String VALUE_REGEXP = "(^\\d{1,9}[.]\\d{1}$)|(^\\d{1,8}[.]\\d{2}$)|(^\\d{1,10}$)";
    private static final String CURRENCY_REGEXP = "^[A-Z]{3}$";

    public AmountDeserializer() {
        this(null);
    }

    private AmountDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Amount deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String value = getNodeValueOrException(jsonParser, node, "value");
        checkValue(jsonParser, value);
        String currency = getNodeValueOrException(jsonParser, node, "currency");
        checkCurrency(jsonParser, currency);
        return new Amount(value, currency);
    }

    private void checkValue(JsonParser jsonParser, String value) throws MismatchedInputException {
        if (!matches(VALUE_REGEXP, value)) {
            throw MismatchedInputException.from(jsonParser, Amount.class, "Error getting required field 'value'");
        }
    }

    private void checkCurrency(JsonParser jsonParser, String currency) throws MismatchedInputException {
        if (!matches(CURRENCY_REGEXP, currency)) {
            throw MismatchedInputException.from(jsonParser, Amount.class, "Error getting required field 'currency'");
        }
    }

    private boolean matches(String patternStr, String field) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(field);
        return matcher.matches();
    }

    private String getNodeValueOrException(JsonParser jsonParser, JsonNode node, String fieldName) throws IOException {
        JsonNode childNode = node.get(fieldName);
        if (childNode == null) {
            throw MismatchedInputException.from(jsonParser, Amount.class, "Error getting required field " + fieldName);
        }
        return childNode.textValue();
    }
}
