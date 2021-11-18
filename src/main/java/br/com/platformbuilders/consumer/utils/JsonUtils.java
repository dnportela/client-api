package br.com.platformbuilders.consumer.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    public static Object toObject(String json, Class klass) throws JsonProcessingException {
        try {
            return new ObjectMapper().readValue(json, klass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static String toJson(Object object) throws JsonProcessingException {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
