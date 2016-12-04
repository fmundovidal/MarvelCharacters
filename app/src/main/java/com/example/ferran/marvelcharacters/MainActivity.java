package com.example.ferran.marvelcharacters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView mTxtViewBuscaHeroe;
    EditText mEdtTextNombreBuscar;

    private String mHeroName;
    private String marvelStrURL;
    MarvelApiConfig mApiConfig = new MarvelApiConfig();


    List<Item> listItem = new ArrayList<>();
    List<Character> listCharacter = new ArrayList<>();

    Character selectedChar = new Character();

    RecyclerView recyclerView;

    Bitmap imageBitmap;
    List<Bitmap> imgBitmapList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTxtViewBuscaHeroe = (TextView)this.findViewById(R.id.txt_view_buscar);
        mEdtTextNombreBuscar = (EditText)this.findViewById(R.id.edt_text_buscar);
        Button mSearchBtn = (Button)this.findViewById(R.id.srch_btn);

        mSearchBtn.setOnClickListener(this);



        mEdtTextNombreBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_main);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager( //cambiando el elemento permite definir si es linear layout, un grid, un staggeredgridlayout, etc...
                this,
                LinearLayoutManager.VERTICAL,
                false)); //para que sea circular o no (false = no)

        MyListAdapterRecycler myListAdapterRecycler = new MyListAdapterRecycler(this,listItem);

        recyclerView.setAdapter(myListAdapterRecycler);

        recyclerView.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.srch_btn) {
            ConnectivityManager mConnectionManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectionManager.getActiveNetworkInfo();

            if (mNetworkInfo != null && mNetworkInfo.isConnected()) {
                if (view.getId() == R.id.srch_btn) {

                    this.mHeroName = mEdtTextNombreBuscar.getText().toString();
                    new MyAlternativeThread().execute(marvelStrURL = mApiConfig.ComputeRequestMarvelURL(this.mHeroName));

                }

            } else {
                Log.i("TAG", "Connection unavailable");
            }
        }
        else{
            //Toast.makeText(this,"Clicked ",Toast.LENGTH_SHORT).show();
        }
    }


    public class MyAlternativeThread extends AsyncTask<String, Void, String> {

        private String mHeroName="Spider-Man";

        private String marvelStrURL;

        MarvelApiConfig mApiConfig = new MarvelApiConfig();

        @Override
        protected String doInBackground(String... strings) {

            Log.i("TAG","1: The requested URL is: "+strings[0] );

                try {

                    URL myUrl = new URL(strings[0]);
                    HttpURLConnection myConnection = (HttpURLConnection)myUrl.openConnection();
                    myConnection.setRequestMethod("GET");
                    myConnection.setDoInput(true);

                    myConnection.connect();
                    int respCode = myConnection.getResponseCode();
                    Log.i("TAG","The response is: "+respCode );

                    if(respCode == HttpURLConnection.HTTP_OK){


                        InputStream myInStream = myConnection.getInputStream();
                        Reader reader = null;
                        reader = new InputStreamReader(myInStream,"UTF-8");
                        //myInStream.close();

                        //reader=null;
                        char[] buffer = new char[100000];
                        reader.read(buffer);


                        Log.i("TAG","The connection was: " + myConnection.getResponseMessage());
                        Log.i("TAG","Received: " + new String(buffer));


                        String jsonString = new String(buffer);

                        List<String> heroArray = new ArrayList<String>();

                        Character hero = new Character(jsonString);

                        hero.computeCharacterObject();

                        String des = hero.getDescription();

                        Log.i("TAG",des);

                        String name = hero.getName();
                        Log.i("TAG",name);

                        Log.i("TAG","The array of the characters is: "+ String.valueOf(hero.getCharacterArray()));

                        listCharacter.clear();
                        listItem.clear();
                        listCharacter.add(hero);
                        selectedChar=listCharacter.get(0);
                        imgBitmapList.clear();

                        int cnt = 0;

                        for(String counter : listCharacter.get(0).getImageArray()) {
                            myUrl = new URL(listCharacter.get(0).getImageArray().get(cnt));
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
                                imgBitmapList.add( BitmapFactory.decodeStream(myInStream));
                                myInStream.close();

                            } else
                                Log.i("TAG", "Connection not available");
                            cnt++;
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

            List<Item> newItemList = new ArrayList<>();

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
            recyclerView.setAdapter(myListAdapterRecycler);
        }


    }

}
