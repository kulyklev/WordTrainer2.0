package com.example.admin.wordtrainer20;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.io.IOException;
import java.util.*;

public class MainActivity extends GeneralMenu {
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private String[] data;
    private ListView listView;
    public Button openLibraryActivity;

    //  Мои словари
    public void init(){
        mDBHelper = new DatabaseHelper(this);
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }
        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        List<String> userTopics = getTopic();
        data = new String[userTopics.size()];
        data = userTopics.toArray(data);

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

    public List<String> getTopic(){
        List<String> listTopic = new ArrayList<>();
        Cursor cursor = mDb.rawQuery("SELECT * FROM vocabulary WHERE isSelected=1;", null);
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            do {
                listTopic.add(cursor.getString(cursor.getColumnIndex("ShortName")));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return listTopic;
    }
}
