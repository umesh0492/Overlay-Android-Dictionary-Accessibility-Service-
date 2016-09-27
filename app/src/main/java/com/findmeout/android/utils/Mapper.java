package com.findmeout.android.utils;

/**
 * Created by umesh0492 on 27/09/16.
 */

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Singleton wrapper class which configures the Jackson JSON parser.
 */
public final class Mapper {
    private static ObjectMapper MAPPER;

    public static String string (Object data) {
        try {
            return get ().writeValueAsString (data);
        } catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }

    public static ObjectMapper get () {
        if (MAPPER == null) {
            MAPPER = new ObjectMapper ();

            MAPPER.setVisibility (PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

            // This is useful for me in case I add new object properties on the server side which are not yet available on the client.
            MAPPER.configure (DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }

        return MAPPER;
    }

    public static <T> T object (String data, TypeReference type) {
        try {
            return get ().readValue (data, type);
        } catch (IOException e) {
            //:// TODO: 27/09/16 log on which json data crash
            e.printStackTrace ();
            return null;
        }
    }

    public static <T> T object (String data, Class<T> type) {
        try {
            return objectOrThrow (data, type);
        } catch (Exception e) {
            //:// TODO: 27/09/16 log on which json data crash
            e.printStackTrace ();
            return null;
        }
    }

    public static <T> T objectOrThrow (String data, Class<T> type) throws IOException {
        return get ().readValue (data, type);
    }
}
