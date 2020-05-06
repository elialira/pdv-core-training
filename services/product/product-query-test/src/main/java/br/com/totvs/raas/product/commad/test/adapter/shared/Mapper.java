package br.com.totvs.raas.product.commad.test.adapter.shared;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

import static java.util.Objects.nonNull;

public class Mapper {

    public static Object to(byte[] value) {
        try {
            return new ObjectMapper().readValue(value, Object.class);
        } catch (IOException cause) {
            throw new IllegalArgumentException("Error converting byte to map", cause);
        }
    }

    public static Map<String, Object> toMap(byte[] value) {
        try {
            return new ObjectMapper().readValue(value, Map.class);
        } catch (IOException cause) {
            throw new IllegalArgumentException("Error converting byte to map", cause);
        }
    }

    public static String toJson(Map<String, Object> value) {
        try {
            return new ObjectMapper().writeValueAsString(value);
        } catch (IOException cause) {
            throw new IllegalArgumentException("Error converting map to json", cause);
        }
    }

    public static byte[] toBytes(Map<String, Object> value) {
        String convertedValue = toJson(value);
        if (nonNull(convertedValue)) {
            return convertedValue.getBytes();
        }
        return null;
    }

}
