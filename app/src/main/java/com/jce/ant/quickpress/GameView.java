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
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;


public class GameView extends View {
    int right, top, width, height;
    Paint paint;
    private Path path;
    int x, y;
    Random rand = new Random();


    private void init (AttributeSet attrs, int defStyle){
        paint = new Paint();
        x=y = 0;
       // paint.setColor(triAngColor);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        path = new Path();

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

        for(int i =0; i<5; i++) {
            x = rand.nextInt(findViewById(R.id.view1).getWidth()-30)+15;
            y = rand.nextInt(findViewById(R.id.view1).getHeight()-30)+15;
            // canvas.drawColor(Color.BLUE);
            canvas.drawCircle(x, y, 30, paint);
        }

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
        int bgColor;
        if(event.getAction() == MotionEvent.ACTION_DOWN){


         //   paint.setColor(triAngColor);
            invalidate();




        }//if

        return true;
    }//onTouchEvent

}//MyView