package com.example.admin.wordtrainer20.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.admin.wordtrainer20.GeneralMenu;
import com.example.admin.wordtrainer20.R;

public class MainActivity extends GeneralMenu {

    private String[] data;
    private ListView listView;
    public Button openLibraryActivity;

    public void init(){
        data = new String[]{"value1", "value2", "value3", "value4", "value5", "value6", "value7", "value8", "value9", "value10", "value11", "value12", "value13"};


        openLibraryActivity = (Button) findViewById(R.id.openLibrariesButton);
        openLibraryActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openLibrariesActivity = new Intent(MainActivity.this, LibraryActivity.class);
                startActivity(openLibrariesActivity);
            }
        });


        listView = (ListView) findViewById(R.id.LibListView);
        listView.setAdapter(new ArrayAdapter<String>(
                this, R.layout.list_item,
                R.id.LibraryNameTextView, data
        ));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View v,
            int position, long id) {
                //
                //DO SOME STUFF ON ITEM CLICK
                //
                //Pass some data
                Intent openExerciseOneActivity = new Intent(MainActivity.this, SelectExerciseActivity.class);
                startActivity(openExerciseOneActivity);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }
}