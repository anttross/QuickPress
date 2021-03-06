package com.jce.ant.quickpress;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class     MainActivity extends AppCompatActivity {
        Settings settings = new Settings();

        boolean isRunning = false;
        private Button settingsBtn, startBtn;
        View view;
        int level=3, complex=2;
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

    int secs=0;
    int milliseconds=0;
    DAL dalObj = new DAL(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;


        view = findViewById(R.id.gameView);


        prefs = getSharedPreferences("best", MODE_PRIVATE);
        editor = prefs.edit();

        resentResultShowTime = (TextView) findViewById(R.id.recentResultShowTime);
        bestResultShowTime = (TextView) findViewById(R.id.bestResultShowTime);


        level = Settings.getLevel();
        complex = Settings.getComplex();

        // first init
        if (dalObj.isBDEmpty()) {
            dalObj.initRecords();
        }

        int placeBT = dalObj.getIndex(level, complex);
        int dRec = dalObj.getRecord(placeBT);
        bestResultShowTime.setText(dalObj.convertToTimeStringFormat(dRec));
        resentResultShowTime.setText("00:000");



        startBtn = (Button) findViewById(R.id.startBtn);
        settingsBtn = (Button) findViewById(R.id.settingsBtn);

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            // go to Settings View
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Settings.class));
            }
        });


        startBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                start();
            }
        });

        bestResultShowTime.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                int place1 = Settings.getLvlCmpx(Settings.getLevel(), Settings.getComplex());
                dalObj.resetRecord(place1);
                int dRec = dalObj.getRecord(place1);
                bestResultShowTime.setText(dalObj.convertToTimeStringFormat(dRec));
            }
        });
    }//onCreate

//
public static Context getContext(){
    return  mContext;
}

    // start the clock
    private void start(){
        if (!isRunning) {
            Log.e("Context", "let the game begin");
            isRunning = true;
            view.invalidate();
            startTime = SystemClock.uptimeMillis();
            customHandler.postDelayed(updateTimerThread, 0);
            ((GameView) view).resetCounter();

        }
        else {
            //do nothing;
        }


    }

    public void stopGame(int lvl_cmpx){
        stop(this.editor, lvl_cmpx);


    }

    // stop the clock
    public void stop(SharedPreferences.Editor editor, int lvl_cmpx){
        customHandler.removeCallbacks(updateTimerThread);
        if (isRunning) {//running
            isRunning = false;

            bestTime = dalObj.getRecord(lvl_cmpx);
            int bestTimeValue = (int)updatedTime;
            if ((bestTime == 0) || (bestTime > bestTimeValue)) { //check the best
                dalObj.updateRecord(lvl_cmpx, bestTimeValue);

                Toast.makeText(getApplicationContext(), "new record !", Toast.LENGTH_SHORT).show();
                bestResultShowTime.setText(dalObj.convertToTimeStringFormat(bestTimeValue));
            }
        }
    }//stop


    // time running
    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            secs = (int) (updatedTime / 1000);
            secs = secs % 60;
            milliseconds = (int) (updatedTime % 1000);
            resentResultShowTime.setText(String.format("%02d", secs) + ":" + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);

        }//run

    };//updateTimerThread


}//MainActivity
