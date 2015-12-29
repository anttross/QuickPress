/*
 * *
 *  * Created by assafbt on <Date>
 *
 */

package com.jce.ant.quickpress;

/**
 * Created by assafbt on 21/12/2015.
 */


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;


public class GameView extends View {

    Settings settings = new Settings();
    MainActivity mainMem = new MainActivity();
    DBHelper dbHelper;
    View gv = (GameView)findViewById(R.id.gameView);
    int right, top, width, height;
    Paint paint;

    private Path path;
    int x, y,xr,yr;
    public int clickCounter;
    Random rand = new Random();
    int size = 30;
    int level;
    int complex;

    private void init (AttributeSet attrs, int defStyle){
        paint = new Paint();
        xr=yr=x=y = 0;
        level = Settings.getLevel();
        complex = Settings.getComplex();


        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        path = new Path();
        clickCounter = 0;

    }//init

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w,h,oldw,oldh);


        right = getPaddingRight();
        top = getPaddingTop();
        width = w - (getPaddingLeft() + getPaddingRight());
        height = h - (getPaddingTop()+ getPaddingBottom());
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        paint.setColor(Color.parseColor("#992222"));


        //circles * complexity
        for(int i =0; i<complex; i++) {

            do {
                x = rand.nextInt(gv.getWidth());
            }while(x>gv.getWidth()-(size*2));
            do {
                y = rand.nextInt(gv.getHeight());
            }while(y>gv.getHeight()-(size*2));
           // Rect
            canvas.drawCircle(x+size, y+size, size, paint);

        }
       // one rectangle
        do {
            xr = rand.nextInt(gv.getWidth());
        }while(xr>(gv.getWidth()-(size*4)));
        do {
            yr = rand.nextInt(gv.getHeight());
        }while(yr>gv.getHeight()-(size * 2));
        canvas.drawRect(xr, yr, xr + (size * 4), yr + (size * 2), paint);
    }





    public GameView(Context context) {//1
        super(context);
        init(null, 0);
    }//1

    public GameView(Context context, AttributeSet attrs) {//2
        super(context, attrs);
        init(attrs, 0);
    }//2

    public GameView(Context context, AttributeSet attrs, int defStyle) {//3
        super(context, attrs, defStyle);
        init(attrs,defStyle);
    }//3





    @Override
    public boolean onTouchEvent( MotionEvent event){

            if((event.getAction() == MotionEvent.ACTION_DOWN)){
                    boolean isRunningNow = ((MainActivity) getContext()).isRunning;
                    Log.e("onTouchEvent","isRunningNow = "+ isRunningNow);
                    if ((xr <= event.getX()) && (xr + 120 >= event.getX())
                            && (yr <= event.getY()) && (yr + 60 >= event.getY())) {
                        if ((clickCounter < level - 1)&& isRunningNow) {
                            clickCounter++;
                            invalidate();

                        } else { // on last touch
                            int lvl_cmpx = Settings.getLvlCmpx(Settings.getLevel(), Settings.getComplex());

                            ((MainActivity) getContext()).stopGame(lvl_cmpx);

                            // save best value

                        }

                    }


        }//if1


        return true;
    }//onTouchEvent


    public void resetCounter() {
        clickCounter=0;
    }
}//MyView