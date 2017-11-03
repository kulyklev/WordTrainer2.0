package com.example.admin.wordtrainer20;

import android.content.*;
import android.support.v7.app.*;
import android.os.*;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Intent intent = new Intent(this, MyDictionaries.class);
        startActivity(intent);
        finish();
    }
}
