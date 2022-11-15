package com.zkz.dreamer.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

public class GsonUtils {
    private final static Gson gson;
    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        gson=gsonBuilder.create();
    }
    public static String toJson(Object obj){
        return gson.toJson(obj);
    }
    public static <T>T fromJson(String json,Class<T> clazz){
        return gson.fromJson(json,clazz);
    }
}
