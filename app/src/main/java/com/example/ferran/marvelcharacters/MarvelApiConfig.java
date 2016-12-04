package com.example.ferran.marvelcharacters;

/**
 * Created by Ferran on 02/12/2016.
 */

public class MarvelApiConfig {
    public static final String PUBLIC_APIKEY="525f93ccf6c344fddcaebd0d31e52a00";
    private static final String PRIVATE_APIKEY="5cc3a083f13e96ff2a695521c1e2a2093a9c1afd";
    public static final String MD5CODE = "a58fcbaed1090f407684847766406d8e";
    public static final String TOTAL_RESOURCES = "limit=10";
    public static final String TOTAL_EVENT_RESOURCES = "limit=5";

    public static final String MARVEL_CHAR_URL = "http://gateway.marvel.com/v1/public/characters?"+TOTAL_RESOURCES+"&nameStartsWith=";


    public static final String URL_TS_API = "&ts=1&apikey=";
    public static final String HASH_STR = "&hash=";

    public static final String MARVEL_PUBLIC_URL = "http://gateway.marvel.com/v1/public/characters/";

    public static final String MARVEL_COMIC_URL = "/comics?"+TOTAL_RESOURCES;
    public static final String MARVEL_EVENT_URL = "/events?"+TOTAL_EVENT_RESOURCES;


    public String ComputeRequestMarvelURL(String heroName) {

        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(MARVEL_CHAR_URL).append(heroName).
                append(URL_TS_API).
                append(PUBLIC_APIKEY).
                append(HASH_STR).
                append(MD5CODE);
        return strBuilder.toString();
    }

    public String ComputeRequestMarvelComicURL(String heroID){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(MARVEL_PUBLIC_URL).append(heroID).append(MARVEL_COMIC_URL).
                append(URL_TS_API).
                append(PUBLIC_APIKEY).
                append(HASH_STR).
                append(MD5CODE);
        return stringBuilder.toString();
    }

    public String ComputeRequestMarvelEventURL(String heroID){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(MARVEL_PUBLIC_URL).append(heroID).append(MARVEL_EVENT_URL).
                append(URL_TS_API).
                append(PUBLIC_APIKEY).
                append(HASH_STR).
                append(MD5CODE);
        return stringBuilder.toString();
    }

    public MarvelApiConfig (){}

}

