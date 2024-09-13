package com.dullfan.common.utils;

import com.google.gson.Gson;

public class JsonUtils {
    private static final Gson gson = new Gson();

    public static <T> T fromJson(String content, Class<T> clazz){
        return gson.fromJson(content,clazz);
    }

    public static String toJson(Object o){
        return gson.toJson(o);
    }

}
