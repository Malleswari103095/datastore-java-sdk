package com.datastore.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ObjUtil {

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static boolean isBlank(String value) {
        return (value == null || value.trim().length() <= 0);
    }

    /**
     * Gets map from given json string.
     *
     * @param json the string
     * @return the map
     */
    public static Map<String, Object> getMapFromJson(String json) {

        try {
            return mapper.readValue(json,
                    new TypeReference<HashMap<String, Object>>() {
                    });
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Gets json.
     *
     * @param object the object
     * @return the json
     */
    public static String getJson(Object object) {

        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
