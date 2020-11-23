package com.cyberfanta.torrecochallenge;

import android.graphics.BitmapFactory;
import android.util.Log;

import com.cyberfanta.torrecochallenge.models.Links;
import com.cyberfanta.torrecochallenge.models.Locations;
import com.cyberfanta.torrecochallenge.models.Persons;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.cyberfanta.torrecochallenge.SharedConstants.*;
import static com.cyberfanta.torrecochallenge.ProjectUtils.JSONtoClass;

public class ApiController {

    /**
     * Objects to read data from server
     */
    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build();
    private static Request request;
    private static Response response;

    /**
     * Links to server API
     */
    private static final String PAGE_URL_1 = "https://bio.torre.co/api/bios/";
//    private static final String PAGE_URL_2 = "https://torre.co/api/opportunities/";
//    private static final String PAGE_URL_3 = "https://search.torre.co/opportunities/_search/";
//    private static final String PAGE_URL_4 = "https://search.torre.co/people/_search/";

    /**
     * Object to store the data readed from server
     */
    private Persons persons;

    /**
     * Get the Data from Internet and add it to a Person object.
     * @param name Person.name
     * @return SharedConstants with reading statu
     */
    public SharedConstants getInfo_bios(String name) {
        persons = null;
        System.gc();

        String pageName = PAGE_URL_1.concat(name);
        request = new Request.Builder()
                .url(pageName)
                .get()
                .build();

        try {
            response = CLIENT.newCall(request).execute();

Log.i(null, response.toString());

            String responseJSON = Objects.requireNonNull(response.body()).string();
            Objects.requireNonNull(response.body()).close();

Log.i(null, responseJSON);

            if (responseJSON.contains("\"code\":\"020000\""))
                return NAME_EMPTY;

            if (responseJSON.contains("\"code\":\"011002\""))
                return PERSON_NOT_FOUND;

            //Json Root
            JsonFactory factory = new JsonFactory();
            ObjectMapper objectMapper = new ObjectMapper(factory);

            JsonNode jsonNode = objectMapper.readTree(responseJSON);
            Iterator<Map.Entry<String, JsonNode>> fieldsIterator = jsonNode.fields();
            Vector <Map.Entry<String,JsonNode>> fields = new Vector<>(0);

            while (fieldsIterator.hasNext()) {
                Map.Entry<String,JsonNode> field = fieldsIterator.next();
                fields.add(field); //Json Root Parsed
            }

            //Object person
            persons = JSONtoClass(fields.elementAt(0).getValue().toString(), Persons.class);
Log.i(null, "original json: " + fields.elementAt(0).getValue().toString());
Log.i(null, "person: " + persons.toString());

            jsonNode = objectMapper.readTree(fields.elementAt(0).getValue().toString());
            fieldsIterator = jsonNode.fields();
            Vector <Map.Entry<String,JsonNode>> persons_fields = new Vector<>(0);

            while (fieldsIterator.hasNext()) {
                Map.Entry<String,JsonNode> field = fieldsIterator.next();
Log.i(null, "Key: " + field.getKey() + "\tValue:" + field.getValue());
                if (field.getKey().equals("links"))
                    persons_fields.add(field); //Json person Parsed
                if (field.getKey().equals("location"))
                    persons_fields.add(field); //Json person Parsed
            }

            Locations locations = JSONtoClass(persons_fields.elementAt(1).getValue().toString(), Locations.class);
Log.i(null, "locations: " + locations.toString());
            persons.setLocations(locations);

Log.i(null, "persons_fields[0]: " + persons_fields.elementAt(0).getValue().toString().substring(1, persons_fields.elementAt(0).getValue().toString().length() - 1));

            String regex = "";
            regex = regex.concat("\\},\\{");

            String[] responseJSONList = persons_fields.elementAt(0).getValue().toString().substring(1, persons_fields.elementAt(0).getValue().toString().length() - 1).split(regex);
            responseJSONList[0] = responseJSONList[0].substring(1);
            responseJSONList[responseJSONList.length - 1] = responseJSONList[responseJSONList.length - 1].substring(0, responseJSONList[responseJSONList.length - 1].length() - 1);

            for (String field: responseJSONList) {
Log.i(null, "field: " + field);
                Links links = JSONtoClass("{" + field + "}", Links.class);
                persons.addLinks(links); //Json links parsing
Log.i(null, "links: " + links.toString());
            }
        } catch (IOException e) {
            return PERSON_LOAD_FAIL;
        }
        return PERSON_LOAD_OK;
    }

    /**
     * Read all images from server
     * @return SharedConstants with reading status
     */
    public SharedConstants readImages() {
        URL url;
        try {
            url = new URL(persons.getPictureThumbnail());
//            url.openConnection().setConnectTimeout(10);//10
//            url.openConnection().setReadTimeout(15);//15
            persons.setPictureThumbnailPhoto(BitmapFactory.decodeStream(url.openConnection().getInputStream()));

            url = new URL(persons.getPicture());
//            url.openConnection().setConnectTimeout(10);//10
//            url.openConnection().setReadTimeout(15);//15
            persons.setPicturePhoto(BitmapFactory.decodeStream(url.openConnection().getInputStream()));
        } catch (IOException e) {
            return IMAGES_FAILED;
        }
        return IMAGES_OK;
    }

    /**
     * Return the object with all data readed from server
     * @return Persons Objects
     */
    public Persons getPersons() {
        return persons;
    }
}
