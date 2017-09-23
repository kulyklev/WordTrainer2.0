package com.example.admin.wordtrainer20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ExerciseOneActivity extends GeneralMenu {


    private Button buttonYes;
    private Button buttonNo;

    private void init(){
        buttonYes = (Button) findViewById(R.id.buttonYes);
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                //DO SOME STUFF ON BUTTON CLICK "I KNOW"
                //
            }
        });

        buttonNo = (Button) findViewById(R.id.buttonNo);
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                //DO SOME STUFF ON BUTTON CLICK "I DON`T KNOW"
                //
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_one);

        init();
    }
}
