package com.example.ferran.marvelcharacters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Ferran on 04/12/2016.
 */

public class InputStreamToString {

    String inputStreamToString(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = null;
        StringBuilder myStringBuilder = new StringBuilder();

        String streamLine;

        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        while ((streamLine = bufferedReader.readLine()) != null) {
            myStringBuilder.append(streamLine);
        }
        return myStringBuilder.toString();
    }
}



