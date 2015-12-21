package com.jce.ant.quickpress;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private FrameLayout fragmentContainer;
    Button btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentContainer = (FrameLayout) findViewById(R.id.fragmentContainer);
        GameFragment gameFragment = new GameFragment();


                // our code
       // setContentView(R.layout.fragment_game);
        if (savedInstanceState != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(fragmentContainer.getId(), gameFragment, "TAG")
                    .addToBackStack(null)
                    .commit();
        }



        btnSettings = (Button) findViewById(R.id.settingsBtn);

        btnSettings.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view){
                startActivity(new Intent(MainActivity.this, Settings.class));
            }
        });


    }//onCreate


}//MainActivity
