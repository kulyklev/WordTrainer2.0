package com.example.admin.wordtrainer20;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class ExerciseThreeActivity extends AppCompatActivity implements View.OnClickListener {
    //Create list if buttons
    private Button variantButt_1;
    private Button variantButt_2;
    private Button variantButt_3;
    private Button variantButt_4;
    private Button variantButt_5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_three);

        variantButt_1 = (Button) findViewById(R.id.variantButt_1);
        variantButt_1.setOnClickListener(this);
        variantButt_2 = (Button) findViewById(R.id.variantButt_2);
        variantButt_2.setOnClickListener(this);
        variantButt_3 = (Button) findViewById(R.id.variantButt_3);
        variantButt_3.setOnClickListener(this);
        variantButt_4 = (Button) findViewById(R.id.variantButt_4);
        variantButt_4.setOnClickListener(this);
        variantButt_5 = (Button) findViewById(R.id.variantButt_5);
        variantButt_5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.variantButt_1:
                //
                //Do stuff on button click
                //
                break;

            case R.id.variantButt_2:
                //
                //Do stuff on button click
                //
                break;

            case R.id.variantButt_3:
                //
                //Do stuff on button click
                //
                break;

            case R.id.variantButt_4:
                //
                //Do stuff on button click
                //
                break;

            case R.id.variantButt_5:
                //
                //Do stuff on button click
                //
                break;

            default:
                break;
        }
    }
}
