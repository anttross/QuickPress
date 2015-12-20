package com.jce.ant.quickpress;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentContainer = (FrameLayout) findViewById(R.id.fragmentContainer);
        GameFragment gameFragment = new GameFragment();

       // setContentView(R.layout.fragment_game);
        if (savedInstanceState != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(fragmentContainer.getId(), gameFragment, "TAG")
                    .addToBackStack(null)
                    .commit();
        }


    }
}
