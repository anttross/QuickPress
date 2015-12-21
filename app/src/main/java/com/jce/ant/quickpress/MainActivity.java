package com.jce.ant.quickpress;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {


    private Button settingsBtn, startBtn;
    View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        view = (GameView)findViewById(R.id.view1);
        
        startBtn = (Button) findViewById(R.id.startBtn);

        settingsBtn = (Button) findViewById(R.id.settingsBtn);

        settingsBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Settings.class));
            }
        });


    }//onCreate


}//MainActivity
