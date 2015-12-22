package com.jce.ant.quickpress;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    boolean isRunning = false;
    private Button settingsBtn, startBtn;
    View view;
    int level, complex;
    private static Context mContext;
    private TextView resentResultShowTime, bestResultShowTime;

    private long startTime = 0L;
    private long bestTime = 0L;

    private Handler customHandler = new Handler();

    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    int secs, mins, milliseconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mContext = this;


        view = (GameView)findViewById(R.id.view1);


        prefs = getSharedPreferences("best", MODE_PRIVATE);
        editor = prefs.edit();


//        String stLvl = savedInstanceState.getString("lvl", "1");
//        String stCmpx = savedInstanceState.getString("cmpx", "0");
//        level = Integer.parseInt(stLvl);
  //      complex = Integer.parseInt(stCmpx);

        resentResultShowTime = (TextView) findViewById(R.id.resentResultShowTime);
        bestResultShowTime = (TextView) findViewById(R.id.bestResultShowTime);

        startBtn = (Button) findViewById(R.id.startBtn);

        settingsBtn = (Button) findViewById(R.id.settingsBtn);

        settingsBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Settings.class));
            }
        });


        startBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                start();
            }
        });

    }//onCreate

public static Context getContext(){
    return  mContext;
}
    // start method
    private void start(){
        if (!isRunning) {
            isRunning = true;
            ((GameView) view).invalidate();
            startTime = SystemClock.uptimeMillis();
            customHandler.postDelayed(updateTimerThread, 0);
            ((GameView) view).resetCounter();

        }
        else {
            //do nothing;
        }


    }

    public void stopGame(){
        //Toast.makeText(getApplicationContext(), "before", Toast.LENGTH_SHORT).show();
        stop(this.editor);
  //      Toast.makeText(getApplicationContext(), "after", Toast.LENGTH_SHORT).show();

    };

    // stop method
    public void stop(SharedPreferences.Editor editor){
        customHandler.removeCallbacks(updateTimerThread);
        if (isRunning) {//running
            isRunning = false;
            if ((bestTime > updatedTime) || (bestTime == 0)) { //check the best
                bestTime = updatedTime;

                // update best time for layout case
                editor.putLong("best", bestTime);
                editor.apply();

                secs = (int) (updatedTime / 1000);
                mins = secs / 60;
                secs = secs % 60;
                milliseconds = (int) (updatedTime % 1000);

                bestResultShowTime.setText(" " + mins + ":" + String.format("%02d", secs) + ":" + String.format("%03d", milliseconds));
            }
        }
    };


    // time running
    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            secs = (int) (updatedTime / 1000);
            mins = secs / 60;
            secs = secs % 60;
            milliseconds = (int) (updatedTime % 1000);
            resentResultShowTime.setText(" " + mins + ":" + String.format("%02d", secs) + ":" + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);

        }

    };
}//MainActivity
