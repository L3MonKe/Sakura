package dev.lemonclient.lemonchat.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class GsonUtils {
    public static GsonBuilder newBuilder() {
        return new GsonBuilder().setPrettyPrinting();
    }

    private static final Gson gson = (new GsonBuilder()).create();

    public GsonUtils() {
    }

    public static String beanToJson(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T jsonToBean(String jsonStr, Class<T> objClass) {
        return gson.fromJson(jsonStr, objClass);
    }

    public static String jsonFormatter(String uglyJsonStr) {
        Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(uglyJsonStr);
        return gson.toJson(je);
    }
}
