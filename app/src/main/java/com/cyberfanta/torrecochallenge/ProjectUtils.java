package com.cyberfanta.torrecochallenge;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class ProjectUtils {
    private static final Gson gson = new Gson();

    //    ---

    /**
     * Create a new object of a generic class and store JSON data on it.
     * @return <T> T - Generic Object Class
     */
    public static <T> T JSONtoClass(String ObjectJSON, Class<T> type) {
        return gson.fromJson(ObjectJSON, type);
    }

    /**
     * Create a new object of a generic class and store JSON data on it.
     * @return <T> T - Generic Object Class
     */
    public static <T> T JSONtoClass (JsonElement ObjectJSON, Class<T> type) {
        return gson.fromJson(ObjectJSON, type);
    }

    /**
     * Create a new object of a generic class and store JSON data on it.
     * @return <T> T - Generic Object Class
     */
    public static JsonElement JSONExtractJsonElement (String ObjectJSON, String type) {
        return JsonParser.parseString(ObjectJSON).getAsJsonObject().get(type);
    }
}