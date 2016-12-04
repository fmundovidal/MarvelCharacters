package com.example.ferran.marvelcharacters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class SecondActivity extends AppCompatActivity implements ComicsFragment.OnComicsFragmentInterface, View.OnClickListener {


    MarvelApiConfig mApiConfig = new MarvelApiConfig();

    FragmentManager fm = getSupportFragmentManager();
    ComicsFragment myComicsFragment = (ComicsFragment) fm.findFragmentById(R.id.frame_layout_comics_events);
    private boolean isFragmentOne;
    private Character mCharInfo;

    TextView mTitleText,mBodyText;
    ImageView mImageViewChar;
    String mCharName,mCharId,mCharDescription, mCharImageURL, mCharWikiLink, mCharDetailLink,mCharComicLink;
    Bitmap charBitmap;

    private boolean isImageURL;
    private boolean isImageDownloaded;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        mTitleText =(TextView)findViewById(R.id.heroe_title);
        mImageViewChar = (ImageView)findViewById(R.id.heroe_image);
        mBodyText = (TextView) findViewById(R.id.heroe_body);

        mCharId = getIntent().getStringExtra("CHAR_ID");
        mCharName = getIntent().getStringExtra("CHAR_NAME");
        mCharDescription = getIntent().getStringExtra("CHAR_DESCRIPTION");
        mCharImageURL = getIntent().getStringExtra("CHAR_IMAGE");
        mCharWikiLink = getIntent().getStringExtra("CHAR_WIKI_URL");
        mCharDetailLink = getIntent().getStringExtra("CHAR_DETAIL_URL");
        mCharComicLink = getIntent().getStringExtra("CHAR_COMIC_URL");

        Log.i("TAG","wiki link: "+ mCharWikiLink);
        Log.i("TAG","detail link: "+ mCharDetailLink);

        isImageURL = true;

        isImageDownloaded=false;
        new MyAlternativeThread().execute(mCharImageURL);

        while(!isImageDownloaded);

        mImageViewChar.setImageBitmap(charBitmap);


        mTitleText.setText(mCharName);
        mBodyText.setText(mCharDescription);
        mImageViewChar.setImageBitmap(charBitmap);

        FragmentManager fragManager = this.getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragManager.beginTransaction();
        fragTransaction.add(R.id.frame_layout_comics_events, new ComicsFragment(), "comics_fragment");
        isFragmentOne=true;
        fragTransaction.commit();

        Character heroInfo = new Character();
        isImageURL = false;
        new MyAlternativeThread().execute(mApiConfig.ComputeRequestMarvelComicURL(this.mCharId));

        final Button WikiBtn = (Button)findViewById(R.id.wiki_btn);
        WikiBtn.setOnClickListener(this);
        final Button DetailBtn = (Button)findViewById(R.id.detalle_btn);
        DetailBtn.setOnClickListener(this);
        final Button ComicBtn = (Button)findViewById(R.id.comics_btn);
        ComicBtn.setOnClickListener(this);

    }

    @Override
    public Character setComicList() {
        return this.mCharInfo;
    }

    @Override
    public void onClick(View view) {
        Intent browserIntent = new Intent();
        browserIntent.setAction(Intent.ACTION_VIEW);
        browserIntent.addCategory(Intent.CATEGORY_BROWSABLE);
        switch(view.getId()){
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

            Log.i("TAG","1: The requested URL is: "+strings[0] );

            try {

                URL myUrl = new URL(strings[0]);
                Log.i("TAG","URL: "+myUrl);

                HttpURLConnection myConnection = (HttpURLConnection)myUrl.openConnection();
                myConnection.setRequestMethod("GET");
                myConnection.setDoInput(true);

                myConnection.connect();
                int respCode = myConnection.getResponseCode();
                Log.i("TAG","The response is: "+respCode );

                if(respCode == HttpURLConnection.HTTP_OK){


                    InputStream myInStream = myConnection.getInputStream();

                    if(isImageURL) {

                        charBitmap= BitmapFactory.decodeStream(myInStream);
                        myInStream.close();
                        isImageDownloaded=true;
                    }
                    else {
                        Reader reader = null;
                        reader = new InputStreamReader(myInStream, "UTF-8");

                        char[] buffer = new char[100000];
                        reader.read(buffer);

                        Log.i("TAG", "The connection was: " + myConnection.getResponseMessage());
                        Log.i("TAG", "Received: " + new String(buffer));


                        String jsonString = new String(buffer);

                        List<String> heroArray = new ArrayList<String>();

                        Comics heroComics = new Comics(jsonString);

                        heroComics.computeCharacterComicEventInfo();



                    /*String des = hero.getDescription();

                    Log.i("TAG",des);

                    String name = hero.getName();
                    Log.i("TAG",name);

                    Log.i("TAG","The array of the comics is: "+ String.valueOf(hero.getCharacterArray()));

                    int cnt = 0;*/
                    }

                }



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }  catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

           /* List<Item> newItemList = new ArrayList<>();

            for (Character b:listCharacter){
                int i=0;
                for(String names : b.getCharacterArray()){

                    String heroDescription;
                    if(b.getDescriptionArray().get(i)!=null) {
                        heroDescription=b.getDescriptionArray().get(i);
                    }
                    else
                        heroDescription="";
                    Item hero = new Item(imgBitmapList.get(i), b.getCharacterArray().get(i),heroDescription );
                    i++;
                    newItemList.add(hero);
                }
            }
            MyListAdapterRecycler myListAdapterRecycler = new MyListAdapterRecycler(getApplicationContext(),newItemList,selectedChar);
            recyclerView.setAdapter(myListAdapterRecycler);*/
        }


    }
}
