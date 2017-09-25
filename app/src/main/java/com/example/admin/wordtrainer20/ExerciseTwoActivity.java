package com.example.admin.wordtrainer20;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ExerciseTwoActivity extends GeneralMenu {
    private EditText answer;

    private  void init(){
        answer = (EditText) findViewById(R.id.answer);
        answer.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //
                //DO SOME STUFF
                //
                Toast.makeText(getApplicationContext(), answer.getText().toString(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_two);
    }
}
