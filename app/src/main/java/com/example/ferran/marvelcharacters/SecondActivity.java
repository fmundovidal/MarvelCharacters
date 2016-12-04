package com.example.ferran.marvelcharacters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ferran on 02/12/2016.
 */

public class SecondActivity extends AppCompatActivity implements View.OnClickListener, ComicsFragment.OnComicsFragmentInterface, EventsFragment.OnEventsFragmentInterface {


    MarvelApiConfig mApiConfig = new MarvelApiConfig();

    FragmentManager fm = getSupportFragmentManager();
    ComicsFragment myComicsFragment = (ComicsFragment) fm.findFragmentById(R.id.frame_layout_comics_events);
    private boolean isFragmentOne;
    private Character mCharInfo;

    TextView mTitleText, mBodyText;
    ImageView mImageViewChar;
    String mCharName, mCharId, mCharDescription, mCharImageURL, mCharWikiLink, mCharDetailLink, mCharComicLink;
    Bitmap charBitmap;

    private int downloadContent;
    private static final int DOWNLOAD_CHAR_IMAGE = 0;
    private static final int DOWNLOAD_COMIC = 1;
    private static final int DOWNLOAD_EVENT = 2;

    private boolean isContentDownloaded;
    TabLayout tabs;
    TabLayout.Tab comicsTab;
    TabLayout.Tab eventsTab;

    Comics mComicsObject = new Comics();
    Events mEventObject = new Events();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        this.tabs = (TabLayout) findViewById(R.id.tabs);
        this.comicsTab = this.tabs.newTab().setText("Comics");
        this.eventsTab = this.tabs.newTab().setText("Events");
        this.tabs.addTab(comicsTab);
        this.tabs.addTab(eventsTab);

        mTitleText = (TextView) findViewById(R.id.heroe_title);
        mImageViewChar = (ImageView) findViewById(R.id.heroe_image);
        mBodyText = (TextView) findViewById(R.id.heroe_body);

        mCharId = getIntent().getStringExtra("CHAR_ID");
        mCharName = getIntent().getStringExtra("CHAR_NAME");
        mCharDescription = getIntent().getStringExtra("CHAR_DESCRIPTION");
        mCharImageURL = getIntent().getStringExtra("CHAR_IMAGE");
        mCharWikiLink = getIntent().getStringExtra("CHAR_WIKI_URL");
        mCharDetailLink = getIntent().getStringExtra("CHAR_DETAIL_URL");
        mCharComicLink = getIntent().getStringExtra("CHAR_COMIC_URL");

        Log.i("TAG", "wiki link: " + mCharWikiLink);
        Log.i("TAG", "detail link: " + mCharDetailLink);

        downloadContent = DOWNLOAD_CHAR_IMAGE;

        isContentDownloaded = false;
        new MyAlternativeThread().execute(mCharImageURL);

        while (!isContentDownloaded) ;

        int i = 0;
        for (String imageContent : mComicsObject.getComicArray()) {
            new MyAlternativeThread().execute(mComicsObject.getImageUrlArray().get(i));
            i++;
        }

        mImageViewChar.setImageBitmap(charBitmap);


        mTitleText.setText(mCharName);
        mBodyText.setText(mCharDescription);
        mImageViewChar.setImageBitmap(charBitmap);


        TabLayout.OnTabSelectedListener listener = new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals("Events"))
                    make_transaction(false);
                else
                    make_transaction(true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
        tabs.addOnTabSelectedListener(listener);


        /***********************Downloading the content********************/
        Character heroInfo = new Character();
        downloadContent = DOWNLOAD_COMIC;
        isContentDownloaded = false;
        new MyAlternativeThread().execute(mApiConfig.ComputeRequestMarvelComicURL(this.mCharId));
        while (!isContentDownloaded) ;

        downloadContent= DOWNLOAD_EVENT;
        isContentDownloaded = false;
        new MyAlternativeThread().execute(mApiConfig.ComputeRequestMarvelEventURL(this.mCharId));
        while(!isContentDownloaded);



        /**********************End of downloading content********************/
        final Button WikiBtn = (Button) findViewById(R.id.wiki_btn);
        WikiBtn.setOnClickListener(this);
        final Button DetailBtn = (Button) findViewById(R.id.detalle_btn);
        DetailBtn.setOnClickListener(this);
        final Button ComicBtn = (Button) findViewById(R.id.comics_btn);
        ComicBtn.setOnClickListener(this);

        FragmentManager fragManager = this.getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragManager.beginTransaction();
        make_transaction(true);

    }

    public void make_transaction(boolean comicSelected) {

        FragmentManager fragManager = this.getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragManager.beginTransaction();
        if (comicSelected) {
            fragTransaction.replace(R.id.frame_layout_comics_events, new ComicsFragment(), "fragment_comics");
        } else {
            fragTransaction.replace(R.id.frame_layout_comics_events, new EventsFragment(), "fragment_eventos");
        }
        fragTransaction.commit();
    }


    @Override
    public Comics setComicList() {
        return this.mComicsObject;
    }

    @Override
    public Events setEventList() {
        return this.mEventObject;
    }
    @Override
    public void onClick(View view) {
        Intent browserIntent = new Intent();
        browserIntent.setAction(Intent.ACTION_VIEW);
        browserIntent.addCategory(Intent.CATEGORY_BROWSABLE);
        switch (view.getId()) {
            case R.id.wiki_btn:
                browserIntent.setData(Uri.parse(mCharWikiLink));
                startActivity(browserIntent);
                break;
            case R.id.detalle_btn:
                browserIntent.setData(Uri.parse(mCharDetailLink));
                startActivity(browserIntent);
                break;
            case R.id.comics_btn:
                browserIntent.setData(Uri.parse(mCharComicLink));
                startActivity(browserIntent);
                break;
        }

    }



    public class MyAlternativeThread extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            Log.i("TAG", "1: The requested URL is: " + strings[0]);

            try {

                URL myUrl = new URL(strings[0]);
                Log.i("TAG", "URL: " + myUrl);

                HttpURLConnection myConnection = (HttpURLConnection) myUrl.openConnection();
                myConnection.setRequestMethod("GET");
                myConnection.setDoInput(true);

                myConnection.connect();
                int respCode = myConnection.getResponseCode();
                Log.i("TAG", "The response is: " + respCode);

                if (respCode == HttpURLConnection.HTTP_OK) {


                    InputStream myInStream = myConnection.getInputStream();

                    if (downloadContent == DOWNLOAD_CHAR_IMAGE) {

                        charBitmap = BitmapFactory.decodeStream(myInStream);
                        myInStream.close();
                        isContentDownloaded = true;
                    } else if (downloadContent == DOWNLOAD_COMIC) {
                        Reader reader = null;
                        reader = new InputStreamReader(myInStream, "UTF-8");

                        char[] buffer = new char[100000];
                        reader.read(buffer);

                        Log.i("TAG", "The connection was: " + myConnection.getResponseMessage());
                        Log.i("TAG", "Received: " + new String(buffer));

                        String jsonString = new String(buffer);
                        List<String> heroArray = new ArrayList<String>();
                        Comics heroComics = new Comics(jsonString);
                        heroComics.computeCharacterComicInfo();
                        mComicsObject = heroComics;

                        int cnt = 0;
                        for (String counter : mComicsObject.getImageUrlArray()) {
                            myUrl = new URL(mComicsObject.getImageUrlArray().get(cnt));
                            Log.i("TAG", "Image URL: " + myUrl);
                            myConnection = (HttpURLConnection) myUrl.openConnection();
                            myConnection.setRequestMethod("GET");
                            myConnection.setDoInput(true);

                            myConnection.connect();
                            respCode = myConnection.getResponseCode();
                            Log.i("TAG", "The response is: " + respCode);

                            if (respCode == HttpURLConnection.HTTP_OK) {
                                myInStream = myConnection.getInputStream();
                                //imageBitmap = BitmapFactory.decodeStream(myInStream);
                                mComicsObject.addImageBitmapArray(BitmapFactory.decodeStream(myInStream));
                                myInStream.close();

                            } else
                                Log.i("TAG", "Connection not available");
                            cnt++;
                        }
                        isContentDownloaded = true;
                    } else if (downloadContent == DOWNLOAD_EVENT) {
                        Reader reader = null;
                        reader = new InputStreamReader(myInStream, "UTF-8");

                        char[] buffer = new char[100000];
                        reader.read(buffer);

                        Log.i("TAG", "The connection was: " + myConnection.getResponseMessage());
                        Log.i("TAG", "Received: " + new String(buffer));

                        String jsonString = new String(buffer);
                        List<String> heroArray = new ArrayList<String>();
                        Events heroComics = new Events(jsonString);
                        heroComics.computeCharacterEventInfo();
                        mEventObject = heroComics;

                        int cnt = 0;
                        for (String counter : mEventObject.getImageUrlArray()) {
                            myUrl = new URL(mEventObject.getImageUrlArray().get(cnt));
                            Log.i("TAG", "Image URL: " + myUrl);
                            myConnection = (HttpURLConnection) myUrl.openConnection();
                            myConnection.setRequestMethod("GET");
                            myConnection.setDoInput(true);

                            myConnection.connect();
                            respCode = myConnection.getResponseCode();
                            Log.i("TAG", "The response is: " + respCode);

                            if (respCode == HttpURLConnection.HTTP_OK) {
                                myInStream = myConnection.getInputStream();
                                mEventObject.addImageBitmapArray(BitmapFactory.decodeStream(myInStream));
                                myInStream.close();

                            } else
                                Log.i("TAG", "Connection not available");
                            cnt++;
                        }
                        isContentDownloaded = true;

                    }else {


                        isContentDownloaded = true;
                    }

                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


        }


    }
}
