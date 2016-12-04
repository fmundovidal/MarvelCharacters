package com.example.ferran.marvelcharacters;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ferran on 03/12/2016.
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
    private String jsonStringObject,name,description;

    private List<String> comicArray = new ArrayList<String>();
    private List<String> descriptionArray = new ArrayList<String>();
    private List<String> imageArray = new ArrayList<String>();
    private List<String> idArray = new ArrayList<String>();


    public Comics(String jsonString){
        this.jsonStringObject = jsonString;
    }
    public Comics(){}


    public void computeCharacterComicEventInfo() throws JSONException {

        JSONObject jsonReader = (JSONObject) new JSONObject(this.jsonStringObject);
        JSONObject jsonReaderData = jsonReader.getJSONObject(COMIC_DATA);
        JSONArray characterResults = jsonReaderData.getJSONArray(COMIC_RESULTS);
        Log.i("TAG", "Comics length: " + String.valueOf(total_comics));
        Log.i("TAG", "results: " + characterResults.toString());
        int characterCount = (int) jsonReaderData.get(COMIC_TOTAL_NAMES);
        Log.i("TAG", "count: " + String.valueOf(characterCount));
        JSONObject returnedData = characterResults.getJSONObject(0);

        for(int i=0;i<characterResults.length();i++){
            returnedData = characterResults.getJSONObject(i);
            String comicName = returnedData.getString(COMIC_TITLE);
            this.comicArray.add(comicName);
            String comicDescription = returnedData.getString(COMIC_DESCRIPTION);
            descriptionArray.add(comicDescription);
        }

        Log.i("TAG","Titles: "+this.comicArray);
        Log.i("TAG","Descriptions: "+this.descriptionArray);


    }

    public String getDescription() {
        return this.description;
    }
    public String getName(){
        return this.name;
    }

    public List<String> getComicArray(){
        return comicArray;
    }
}
