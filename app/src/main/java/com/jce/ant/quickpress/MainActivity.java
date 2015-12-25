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
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Settings settings = new Settings();

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



        //dalObj.addRecords(0,0); // delete after testing
        level = Settings.getLevel();
        complex = Settings.getComplex();


        dalObj.initRecords(0,0);

/*
        // first init
        if (dalObj.isBDEmpty()) {
            dalObj.initRecords(0,0);
        }
*/

        int placeBT = dalObj.getLvlCmpx(level,complex);
        secs = (int) (placeBT / 1000);
        // mins = secs / 60;
        secs = secs % 60;
        milliseconds = (int) (placeBT % 1000);

       // String dBT = Integer.toString(disBT);

        bestResultShowTime.setText(String.format("%02d", secs) + ":" + String.format("%03d", milliseconds));
        resentResultShowTime.setText("00:000");



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
            Toast.makeText(getApplicationContext(), "let the game begin", Toast.LENGTH_SHORT).show();
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

    // stop method
    public void stop(SharedPreferences.Editor editor, int lvl_cmpx){
        customHandler.removeCallbacks(updateTimerThread);
        if (isRunning) {//running
            isRunning = false;

            bestTime = dalObj.getRecord(lvl_cmpx);
            int bestTimeValue = dalObj.getRecord(lvl_cmpx);
            String dBT = Integer.toString(bestTimeValue);
            if ((bestTime == 0)) { //check the best
                dalObj.updateRecord(lvl_cmpx, dBT);
                Toast.makeText(getApplicationContext(), "new record !", Toast.LENGTH_SHORT).show();
                // update best time for layout case
                editor.putLong("best", bestTime);
                editor.apply();

                secs = (int) (updatedTime / 1000);
               // mins = secs / 60;
                secs = secs % 60;
                milliseconds = (int) (updatedTime % 1000);
//" " + mins + ":" +
                bestResultShowTime.setText(String.format("%02d", secs) + ":" + String.format("%03d", milliseconds));
            }
        }
    }


    // time running
    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            secs = (int) (updatedTime / 1000);
           // mins = secs / 60;
            secs = secs % 60;
            milliseconds = (int) (updatedTime % 1000);
            //" " + mins + ":" +
            resentResultShowTime.setText(String.format("%02d", secs) + ":" + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);

        }

    };

/*
    public static void updateRec(int lvl_cmpx, String val)
    {
        mContext = MainActivity.getContext();
        DAL dalObj =  DAL(mContext);

        int lc= lvl_cmpx;
        dalObj.updateRecord(lvl_cmpx, val);
    };
*/
}//MainActivity
