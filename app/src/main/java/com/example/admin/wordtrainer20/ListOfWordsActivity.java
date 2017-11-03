package com.example.admin.wordtrainer20;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.os.*;
import android.view.*;
import android.widget.*;

import com.example.admin.wordtrainer20.AdapterFolder.*;
import com.example.admin.wordtrainer20.HelperClasses.*;

import java.io.*;
import java.util.*;

public class ListOfWordsActivity extends GeneralMenu {
    private Model[] modelItems;
    private ListView wordsLV;
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private int categoryId;


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

        List<Model> databaseWords = new ArrayList<>();
        try {
            databaseWords = getWords(categoryId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        modelItems = new Model[databaseWords.size()];
        modelItems = databaseWords.toArray(modelItems);


        wordsLV = (ListView) findViewById(R.id.listView1);
        final CustomAdapter adapter = new CustomAdapter(this, modelItems);
        wordsLV.setChoiceMode(wordsLV.CHOICE_MODE_MULTIPLE);
        wordsLV.setAdapter(adapter);

        wordsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                int study_id = getIdByEnglish(modelItems[position].getCategory());
                if (!adapter.getCheckBox(position)) {
                    setWordTrue(study_id);
                } else {
                    setWordFalse(study_id);
                }
                adapter.setCheckBox(position);
//                Toast.makeText(ListOfWordsActivity.this, modelItems[position].getCategory() /*words[position]*/ + " checked : " /*+ item.isChecked()*/, Toast.LENGTH_SHORT).show();
                //Intent intent = getIntent();
                //finish();
                //startActivity(intent);
            }
        });
    }

    public List<Model> getWords(int id) throws IOException {
        List<Model> list = new ArrayList<>();
        Cursor cursor = mDb.rawQuery("SELECT * FROM words WHERE Category='" + id + "'", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                String s = cursor.getString(cursor.getColumnIndex("English"));
                Integer i = cursor.getInt(cursor.getColumnIndex("_id"));
                Boolean b = getIsStudied(i);
                Model model = new Model(s, b);
                list.add(model);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public int getIdByEnglish(String english) {
        String copyEnglish = english.replaceAll("'", "''");

        Cursor cursor = mDb.rawQuery("SELECT * FROM words WHERE English='" + copyEnglish + "'", null);
        cursor.moveToFirst();
        int i = cursor.getInt(cursor.getColumnIndex("_id"));
        cursor.close();
        return i;
    }

    public Boolean getIsStudied(int id) {
        Cursor cursor = mDb.rawQuery("SELECT * FROM study WHERE _id='" + id + "'", null);
        cursor.moveToFirst();
        Boolean i = cursor.getInt(cursor.getColumnIndex("isStudied")) == 1;
        cursor.close();
        return i;
    }

    public void setWordTrue(long id) {
        Cursor cursor = mDb.rawQuery("UPDATE study" +
                " SET isStudied = 1 WHERE _id='" + id + "'", null);
        cursor.moveToFirst();
        cursor.close();
    }

    public void setWordFalse(long id) {
        Cursor cursor = mDb.rawQuery("UPDATE study" +
                " SET isStudied = 0 WHERE _id='" + id + "'", null);
        cursor.moveToFirst();
        cursor.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_words);
        Intent intent = getIntent();
        categoryId = intent.getIntExtra("id", 0);

        init();
    }
}
