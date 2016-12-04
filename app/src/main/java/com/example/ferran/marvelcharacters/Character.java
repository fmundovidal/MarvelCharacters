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

public class Character {

    //Declare object constants
    private static final String CHARACTER_DATA = "data";
    private static final String CHARACTER_RESULTS = "results";
    private static final String CHARACTER_NAME = "name";
    private static final String CHARACTER_TOTAL_NAMES = "count";
    private static final String CHARACTER_COMICS = "comics";
    private static final String CHARACTER_TOTAL_COMICS = "returned";
    private static final String CHARACTER_DESCRIPTION = "description";
    private static final String CHARACTER_IMAGE = "thumbnail";
    private static final String CHARACTER_ID = "id";
    private static final String IMAGE_EXTENSION = "extension";
    private static final String IMAGE_PATH = "path";
    private static final String URLS = "urls";
    private static final String URL_TYPE = "type";
    private static final String URL_LINK = "url";
    private static final String NAME_WIKI = "wiki";
    private static final String NAME_DETAIL = "detail";
    private static final String NAME_COMIC = "comiclink";

    //Declare object variables
    private int total_comics;
    private String jsonStringObject, name, description;

    private List<String> comicArray = new ArrayList<String>();
    private List<String> characterArray = new ArrayList<String>();
    private List<String> descriptionArray = new ArrayList<String>();
    private List<String> imageArray = new ArrayList<String>();
    private List<String> idArray = new ArrayList<String>();

    List<String> urlLinksList = new ArrayList<>();
    List<String> urlTypesList = new ArrayList<>();
    List<String> wikiList = new ArrayList<>();
    List<String> detailList = new ArrayList<>();
    List<String> comicList = new ArrayList<>();


    public Character(String jsonString) {
        this.jsonStringObject = jsonString;
    }

    public Character() {
    }

    //This function extracts JSON data and stores on object's fields
    public void computeCharacterObject() throws JSONException {
        JSONObject jsonReader = (JSONObject) new JSONObject(this.jsonStringObject);
        JSONObject jsonReaderData = jsonReader.getJSONObject(CHARACTER_DATA);
        JSONArray characterResults = jsonReaderData.getJSONArray(CHARACTER_RESULTS);
        Log.i("TAG", "Comics length: " + String.valueOf(total_comics));
        Log.i("TAG", "results: " + characterResults.toString());
        int characterCount = (int) jsonReaderData.get(CHARACTER_TOTAL_NAMES);
        Log.i("TAG", "count: " + String.valueOf(characterCount));
        JSONObject returnedData = characterResults.getJSONObject(0);

        returnedData = characterResults.getJSONObject(0);
        this.description = returnedData.getString(CHARACTER_DESCRIPTION);
        this.name = returnedData.getString(CHARACTER_NAME);
        this.total_comics = (int) returnedData.getJSONObject(CHARACTER_COMICS).get(CHARACTER_TOTAL_COMICS);

        //Each character individual
        for (int i = 0; i < characterResults.length(); i++) {
            returnedData = characterResults.getJSONObject(i);
            String charName = returnedData.getString(CHARACTER_NAME);
            this.characterArray.add(charName);
            String charComics = returnedData.getString(CHARACTER_COMICS);
            this.comicArray.add(charComics);
            String charDescription = returnedData.getString(CHARACTER_DESCRIPTION);
            this.descriptionArray.add(charDescription);
            this.imageArray.add(returnedData.getJSONObject(CHARACTER_IMAGE).getString(IMAGE_PATH)
                    + "/portrait_medium."
                    .concat(returnedData.getJSONObject(CHARACTER_IMAGE).getString(IMAGE_EXTENSION)));
            String charId = returnedData.getString(CHARACTER_ID);
            this.idArray.add(charId);

            urlTypesList.clear();
            urlLinksList.clear();
            JSONArray urlArray = returnedData.getJSONArray(URLS);

            for (int x = 0; x < urlArray.length(); x++) {
                String urlTypes = urlArray.getJSONObject(x).getString(URL_TYPE);
                urlTypesList.add(urlTypes);
                String urlLinks = urlArray.getJSONObject(x).getString(URL_LINK);
                urlLinksList.add(urlLinks);
            }

            if (urlLinksList.size() < 2) {
                urlLinksList.add("");
            }
            if (urlLinksList.size() < 3) {
                urlLinksList.add("");
            }
            int cnt = 0;
            boolean isWikiSet = false;
            boolean isDetailSet = false;
            boolean isComicSet = false;

            for (String p : urlTypesList) {
                switch (p) {
                    case NAME_WIKI:
                        wikiList.add(urlLinksList.get(cnt));
                        isWikiSet = true;
                        break;
                    case NAME_DETAIL:
                        detailList.add(urlLinksList.get(cnt));
                        isDetailSet = true;
                        break;
                    case NAME_COMIC:
                        comicList.add(urlLinksList.get(cnt));
                        isComicSet = true;
                        break;
                    default:

                        break;
                }
                cnt++;
            }

            if (!isWikiSet) {
                wikiList.add("Non available");
            }
            if (!isDetailSet) {
                detailList.add("Non available");
            }
            if (!isComicSet) {
                comicList.add("Non available");
            }
            Log.i("TAG", "URL TYPES: " + String.valueOf(urlTypesList));
            Log.i("TAG", "WIKI LIST SIZE: " + String.valueOf(wikiList.size()));
        }
    }


    //Getters
    public String getDescription() {
        return this.description;
    }

    public String getName() {
        return this.name;
    }

    public List<String> getCharacterArray() {
        return this.characterArray;
    }

    public List<String> getDescriptionArray() {
        return this.descriptionArray;
    }

    public List<String> getImageArray() {
        return this.imageArray;
    }

    public List<String> getIdArray() {
        return this.idArray;
    }

    public List<String> getWikiList() {
        return wikiList;
    }

    public List<String> getDetailList() {
        return detailList;
    }

    public List<String> getComicList() {
        return comicList;
    }


}








