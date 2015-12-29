package com.jce.ant.quickpress;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends AppCompatActivity {
    private Button btnSave;
    static int level =1;
    static int complex=0;
    EditText lvl,cmpx;
    final int maxLevel = 10;
    final int maxComplex = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences prefs = getSharedPreferences("prefCW2", MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();

        lvl = (EditText) findViewById(R.id.levelNum);
        lvl.setText(prefs.getString("lvlRead", "3"));
        level = Integer.parseInt(lvl.getText().toString());

        cmpx = (EditText) findViewById(R.id.complexityNum);
        cmpx.setText(prefs.getString("cmpxRead", "3"));
        complex = Integer.parseInt(cmpx.getText().toString());

        btnSave = (Button) findViewById(R.id.saveBtn);
        btnSave.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {


                lvl = (EditText) findViewById(R.id.levelNum);
                String tempStr = lvl.getText().toString();
                lvl.setText(tempStr);
                level = Integer.parseInt(lvl.getText().toString());
                editor.putString("lvlRead", lvl.getText().toString());



                cmpx = (EditText) findViewById(R.id.complexityNum);
                String tempStr2 = cmpx.getText().toString();
                cmpx.setText(tempStr2);
                complex = Integer.parseInt(cmpx.getText().toString());
                editor.putString("cmpxRead", cmpx.getText().toString());
                editor.apply();

                if(level<1 || level >maxLevel)
                    Toast.makeText(getApplicationContext(), "Level should be 1-10", Toast
                            .LENGTH_SHORT).show();
                else if(complex<0 || complex>maxComplex)
                    Toast.makeText(getApplicationContext(), "Complexity should be 0-4", Toast
                            .LENGTH_SHORT).show();
                else {

                    startActivity(new Intent(Settings.this, MainActivity.class));
                }
            }
        });
    }


    public static Integer getLevel() {
        Integer l = level;
        return l;
    }

    public static Integer getComplex() {
        Integer c = complex;
        return c;
    }

    public static int getLvlCmpx(int level,int complex){

        return level*10 + complex;
    }

}
