package com.example.admin.wordtrainer20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends GeneralMenu {

    private String[] data;
    private ListView listView;
    public Button openLibraryActivity;

    //  Мои словари
    public void init(){
        data = new String[]{"value1", "value2", "value3", "value4", "value5", "value6", "value7", "value8", "value9", "value10", "value11", "value12", "value13"};
        List<String> getListUserTopic = new ArrayList<>(); // Словари юзера




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
