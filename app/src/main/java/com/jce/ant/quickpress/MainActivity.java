package com.jce.ant.quickpress;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private FrameLayout fragmentContainer;
    private Button settingsBtn, startBtn;
    GameFragment gameFragment = new GameFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentContainer = (FrameLayout) findViewById(R.id.fragmentContainer);

/*
                // our code
       // setContentView(R.layout.fragment_game);
        if (savedInstanceState != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(fragmentContainer.getId(), gameFragment, "TAG")
                    .addToBackStack(null)
                    .commit();
        }
*/
        startBtn = (Button) findViewById(R.id.startBtn);
/*
        startBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(fragmentContainer.getId(), gameFragment, "TAG")
                        .addToBackStack(null)
                        .commit();
            }

        });
    */
        settingsBtn = (Button) findViewById(R.id.settingsBtn);

        settingsBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Settings.class));
            }
        });


    }//onCreate


}//MainActivity
