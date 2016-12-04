package com.example.ferran.marvelcharacters;

import android.graphics.Bitmap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ferran on 03/12/2016.
 *
 * This class is used to store a hero's comic, used on the second activity
 *
 */

public class Comics {
    private static final String COMIC_DATA = "data";
    private static final String COMIC_RESULTS = "results";
    private static final String COMIC_TOTAL_NAMES = "count";
    private static final String COMIC_TOTAL_COMICS = "returned";
    private static final String COMIC_DESCRIPTION = "description";
    private static final String COMIC_IMAGE = "thumbnail";
    private static final String COMIC_ID = "id";
    private static final String IMAGE_EXTENSION = "extension";
    private static final String IMAGE_PATH = "path";
    private static final String COMIC_TITLE = "title";


    private int total_comics;
    private String jsonStringObject, name, description;

    private List<String> comicArray = new ArrayList<String>();
    private List<String> descriptionArray = new ArrayList<String>();
    private List<String> imageUrlArray = new ArrayList<String>();


    private List<Bitmap> imageBitmapArray = new ArrayList<Bitmap>();
    private List<String> idArray = new ArrayList<String>();


    public Comics(String jsonString) {
        this.jsonStringObject = jsonString;
    }

    public Comics() {
    }

    //This function extracts JSON data and stores them into object fields.
    public void computeCharacterComicInfo() throws JSONException {

        JSONObject jsonReader = (JSONObject) new JSONObject(this.jsonStringObject);
        JSONObject jsonReaderData = jsonReader.getJSONObject(COMIC_DATA);
        JSONArray characterResults = jsonReaderData.getJSONArray(COMIC_RESULTS);
        int comicCount = (int) jsonReaderData.get(COMIC_TOTAL_NAMES);
        if (comicCount > 0) {
            JSONObject returnedData = characterResults.getJSONObject(0);

            for (int i = 0; i < characterResults.length(); i++) {
                returnedData = characterResults.getJSONObject(i);
                String comicName = returnedData.getString(COMIC_TITLE);
                this.comicArray.add(comicName);
                String comicDescription = returnedData.getString(COMIC_DESCRIPTION);
                descriptionArray.add(comicDescription);

                this.imageUrlArray.add(returnedData.getJSONObject(COMIC_IMAGE).getString(IMAGE_PATH)
                        + "/portrait_medium."
                        .concat(returnedData.getJSONObject(COMIC_IMAGE).getString(IMAGE_EXTENSION)));
            }
        }
    }

    public String getDescription() {
        return this.description;
    }

    public String getName() {
        return this.name;
    }

    public List<String> getComicArray() {
        return comicArray;
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
