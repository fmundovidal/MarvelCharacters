package com.example.ferran.marvelcharacters;

import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ferran on 03/12/2016.
 */

public class Events {
    private static final String EVENT_DATA = "data";
    private static final String EVENT_RESULTS = "results";
    private static final String EVENT_TOTAL_NAMES = "count";
    private static final String EVENT_TOTAL_EVENTS = "total";
    private static final String EVENT_DESCRIPTION = "description";
    private static final String EVENT_IMAGE = "thumbnail";
    private static final String EVENT_ID = "id";
    private static final String EVENT_TITLE = "title";

    private static final String IMAGE_PATH = "path";
    private static final String IMAGE_EXTENSION = "extension";

    private int total_events;
    private String jsonStringObject, name, description;

    private List<String> eventArray = new ArrayList<String>();
    private List<String> descriptionArray = new ArrayList<String>();
    private List<String> imageUrlArray = new ArrayList<String>();


    private List<Bitmap> imageBitmapArray = new ArrayList<Bitmap>();
    private List<String> idArray = new ArrayList<String>();


    public Events(String jsonString) {
        this.jsonStringObject = jsonString;
    }

    public Events() {
    }

    public void computeCharacterEventInfo() throws JSONException {

        Log.i("TAG","Inside computeCharacterEventInfo, json string is: "+this.jsonStringObject.toString());
        JSONObject jsonReader = (JSONObject) new JSONObject(this.jsonStringObject);
        JSONObject jsonReaderData = jsonReader.getJSONObject(EVENT_DATA);
        JSONArray characterResults = jsonReaderData.getJSONArray(EVENT_RESULTS);
        int eventCount = (int) jsonReaderData.get(EVENT_TOTAL_NAMES);
        if (eventCount > 0) {
            JSONObject returnedData = characterResults.getJSONObject(0);

            for (int i = 0; i < (int)jsonReaderData.get(EVENT_TOTAL_NAMES)/*characterResults.length()*/; i++) {
                returnedData = characterResults.getJSONObject(i);
                String eventName = returnedData.getString(EVENT_TITLE);
                this.eventArray.add(eventName);
                String eventDescription = returnedData.getString(EVENT_DESCRIPTION);
                descriptionArray.add(eventDescription);

                this.imageUrlArray.add(returnedData.getJSONObject(EVENT_IMAGE).getString(IMAGE_PATH)
                        + "/portrait_small."
                        .concat(returnedData.getJSONObject(EVENT_IMAGE).getString(IMAGE_EXTENSION)));
            }
        }
    }

    public String getDescription() {
        return this.description;
    }

    public String getName() {
        return this.name;
    }

    public List<String> getEventArray() {
        return eventArray;
    }

    public List<String> getImageUrlArray() {
        return imageUrlArray;
    }

    public List<String> getDescriptionArray() {
        return descriptionArray;
    }

    public List<Bitmap> getImageBitmapArray() {
        return imageBitmapArray;
    }

    public void addImageBitmapArray(Bitmap imageBitmapArray) {
        this.imageBitmapArray.add(imageBitmapArray);
    }
}
