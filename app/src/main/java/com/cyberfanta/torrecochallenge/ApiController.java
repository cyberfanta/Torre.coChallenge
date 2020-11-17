package com.cyberfanta.torrecochallenge;

import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
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

    private static final String PAGE_URL_1 = "https://torre.bio/api/bios/";
//    private static final String PAGE_URL_2 = "https://torre.co/api/opportunities/";
//    private static final String PAGE_URL_3 = "https://search.torre.co/opportunities/_search/";
//    private static final String PAGE_URL_4 = "https://search.torre.co/people/_search/";


    /**
     * Get the Pet Data from Internet and add it to a Pet List.
     */
    static void getInfo_bios() {
        request = new Request.Builder()
                .url(PAGE_URL_1)
                .get()
                .build();

        try {
            response = CLIENT.newCall(request).execute();

            String responseJSON = Objects.requireNonNull(response.body()).string();
            Objects.requireNonNull(response.body()).close();

            responseJSON = responseJSON.substring(1, responseJSON.length() - 1);

            Log.i(null, responseJSON);
        } catch (IOException ignored) {}
    }


}