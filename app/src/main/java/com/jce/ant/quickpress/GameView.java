/*
 * *
 *  * Created by assafbt on <Date>
 *
 */

package com.jce.ant.quickpress;

/**
 * Created by assafbt on 21/12/2015.
 */


import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;


public class GameView extends View {

    MainActivity mainMem = new MainActivity();
    int right, top, width, height;
    Paint paint;
    private Path path;
    int x, y,xr,yr;
    public int clickCounter;
    Random rand = new Random();
    int level, complex;

    private void init (AttributeSet attrs, int defStyle){
        paint = new Paint();
        xr=yr=x=y = 0;
        level = 3;
        complex = 3;


      //  clickCounter=Settings
       // paint.setColor(triAngColor);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        path = new Path();
        clickCounter = 0;
//        level = Integer.parseInt(((EditText) findViewById(R.id.levelNum)).getText().toString());
 //       complex = Integer.parseInt(((EditText) findViewById(R.id.complexityNum)).getText().toString());



        /*
        //Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs.R.styleable.MyView, defStyle, 0);
        String mExampleString = a.getString(R.styleable.MyView_exampleString);
        int mExampleColor = a.getColor(R.styleable.MyView_exampleColor, Color.RED);
           */
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

        for(int i =0; i<complex; i++) {
            x = rand.nextInt(findViewById(R.id.view1).getWidth()-30)+15;
            y = rand.nextInt(findViewById(R.id.view1).getHeight()-30)+15;
            // canvas.drawColor(Color.BLUE);
            canvas.drawCircle(x, y, 30, paint);
        }
        xr = rand.nextInt(findViewById(R.id.view1).getWidth()-30)+15;
        yr = rand.nextInt(findViewById(R.id.view1).getHeight()-30)+15;
        canvas.drawRect(xr, yr, xr+120, yr+60,paint);

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

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if  ((xr <= event.getX()) && (xr+120 >= event.getX())
                    && (yr <= event.getY()) && (yr+60 >= event.getY()))
                if((clickCounter<level-1)){

                    clickCounter++;
                    invalidate();
                }else{ // on last touch
                    ((MainActivity)getContext()).stopGame();
                    // save best value

                }


        }//if1


        return true;
    }//onTouchEvent


    public void resetCounter() {
        clickCounter=0;
    }
}//MyView