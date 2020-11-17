package com.cyberfanta.torrecochallenge;

import android.graphics.BitmapFactory;
import android.util.Log;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiController {
    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build();
    private static Request request;
    private static Response response;

//    private static final String PAGE_URL_1 = "https://bio.torre.co/api/bios/";
//    private static final String PAGE_URL_1 = "https://bio.torre.co/api/bios/$username";
    private static final String PAGE_URL_1 = "https://bio.torre.co/api/bios/julioleon2004";

    //    private static final String PAGE_URL_2 = "https://torre.co/api/opportunities/";
//    private static final String PAGE_URL_3 = "https://search.torre.co/opportunities/_search/";
//    private static final String PAGE_URL_4 = "https://search.torre.co/people/_search/";


    /**
     * Get the Pet Data from Internet and add it to a Pet List.
     */
    static int getInfo_bios(String name) {
        request = new Request.Builder()
                .url(PAGE_URL_1)
                .get()
                .build();

        try {
            response = CLIENT.newCall(request).execute();

            Log.i(null, response.toString());

            String responseJSON = Objects.requireNonNull(response.body()).string();
            Objects.requireNonNull(response.body()).close();

            responseJSON = responseJSON.substring(1, responseJSON.length() - 1);

            Log.i(null, responseJSON);

            if (responseJSON.contains("\"code\":\"020000\""))
                return MainActivity.NAME_EMPTY;

            if (responseJSON.contains("\"code\":\"011002\""))
                return MainActivity.PERSON_NOT_FOUND;

            JsonFactory factory = new JsonFactory();

            ObjectMapper mapper = new ObjectMapper(factory);
            JsonNode rootNode = mapper.readTree(responseJSON);

            Iterator<Map.Entry<String, JsonNode>> fieldsIterator = rootNode.fields();
            while (fieldsIterator.hasNext()) {

                Map.Entry<String,JsonNode> field = fieldsIterator.next();
                System.out.println("Key: " + field.getKey() + "\tValue:" + field.getValue());
            }
        } catch (IOException ignored) {}

        return MainActivity.PERSON_OK;
    }
}
