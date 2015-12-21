package com.jce.ant.quickpress;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Settings extends AppCompatActivity {
    private Button btnSave;
    private String level, complex;
    Bundle b = new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);




        btnSave = (Button) findViewById(R.id.saveBtn);

        btnSave.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                b.putString("lvl", ((EditText) findViewById(R.id.levelNum)).getText().toString());
                b.putString("cmpx", ((EditText) findViewById(R.id.complexityNum)).getText().toString());



                startActivity(new Intent(Settings.this, MainActivity.class));
            }
        });
    }
}
