package com.example.admin.wordtrainer20;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class ListOfWordsActivity extends GeneralMenu {
    private String[] words;
    private ListView wordsLV;
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private int id;

    private void init() {
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

        List<String> databaseWords = new ArrayList<>();
        try {
            databaseWords = getWords(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        words = new String[databaseWords.size()];
        words = databaseWords.toArray(words);


        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_checked, words);

        wordsLV = (ListView) findViewById(R.id.ListOfWordsLV);
        wordsLV.setChoiceMode(wordsLV.CHOICE_MODE_MULTIPLE);
        wordsLV.setAdapter(itemsAdapter);
        wordsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                CheckedTextView item = (CheckedTextView) view;
                Toast.makeText(ListOfWordsActivity.this, words[position] + " checked : " + item.isChecked(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public List<String> getWords(int id) throws IOException
    {
        List<String> list = new ArrayList<>();
        Cursor cursor = mDb.rawQuery("SELECT * FROM words WHERE Category='"+ id + "'", null);
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            do {
                list.add(cursor.getString(cursor.getColumnIndex("English")));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_words);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);

        init();
    }
}
