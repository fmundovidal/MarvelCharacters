package com.example.ferran.marvelcharacters;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

public class SplashScreen extends AppCompatActivity {

    private static final long SPLASH_LOADING_TIME = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        //Crear un nuevo handler para iniciar la MainActivity
        Handler hndl = new Handler();
        hndl.postDelayed(new Runnable() {
            @Override
            public void run() {
                SplashScreen.this.startActivity(new Intent(SplashScreen.this,MainActivity.class));
                SplashScreen.this.finish();
            }
        },SPLASH_LOADING_TIME);

    }
}
