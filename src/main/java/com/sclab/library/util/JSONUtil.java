package com.sclab.library.util;

import com.google.gson.Gson;

public class JSONUtil {

    public static String objectToJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

}