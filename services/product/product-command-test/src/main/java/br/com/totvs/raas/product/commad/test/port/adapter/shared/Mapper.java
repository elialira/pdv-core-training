package br.com.totvs.raas.product.commad.test.port.adapter.shared;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

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

}
