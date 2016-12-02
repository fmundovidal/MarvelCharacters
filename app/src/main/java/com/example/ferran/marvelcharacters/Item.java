package com.example.ferran.marvelcharacters;

import android.content.Context;

/**
 * Created by Ferran on 02/12/2016.
 */

public class Item {
    Context mContext;

    private String mTitle;
    private String mBody;
    private String mImage;

    Item(String imgRef, String aTitle, String aBody){
        this.mTitle = aTitle;
        this.mBody = aBody;
        this.mImage = imgRef;
    }
    public String getmTitle(){
        return this.mTitle;
    }
    public String getmBody(){
        return this.mBody;
    }
    public String getmImage(){
        return  this.mImage;
    }

}
