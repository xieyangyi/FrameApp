package com.example.xieyangyi.framesdk.Json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.lang.reflect.Type;


public final class JsonUtil {

    private static Gson mGson;

    private static Gson getGson() {
        if (null == mGson) {
            mGson = new GsonBuilder().serializeNulls().disableHtmlEscaping().create();
        }
        return mGson;
    }

    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }

    public static String toJson(Object object, Type typeOfT) {
        return new Gson().toJson(object, typeOfT);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return getGson().fromJson(json, clazz);
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        return getGson().fromJson(json, typeOfT);
    }

    public static <T> T fromJson(JsonReader reader, Type typeOfT) {
        return getGson().fromJson(reader, typeOfT);
    }
}