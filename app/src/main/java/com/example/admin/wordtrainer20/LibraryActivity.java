package com.example.admin.wordtrainer20;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LibraryActivity extends GeneralMenu {
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private GridView gridView;
    private String[] signatureText;// = {"alarm", "android", "mobile", "profile_icon", "web", "wordpress", "7", "8"};
    private int icons[] = {

            R.drawable.sample_0,
            R.drawable.sample_1,
            R.drawable.sample_2,
            R.drawable.sample_3,
            R.drawable.sample_4,
            R.drawable.sample_5,
            R.drawable.sample_6,
            R.drawable.sample_7,

            R.drawable.sample_0,
            R.drawable.sample_1,
            R.drawable.sample_2,
            R.drawable.sample_3,
            R.drawable.sample_4,
            R.drawable.sample_5,
            R.drawable.sample_6,
            R.drawable.sample_7,

            R.drawable.sample_0,
            R.drawable.sample_1,
            R.drawable.sample_2,
            R.drawable.sample_3,
            R.drawable.sample_4,
            R.drawable.sample_5,
            R.drawable.sample_6,
            R.drawable.sample_7,

            R.drawable.sample_0,
            R.drawable.sample_1,
            R.drawable.sample_2,
            R.drawable.sample_3,
            R.drawable.sample_4,
            R.drawable.sample_5,
            R.drawable.sample_6,
            R.drawable.sample_7,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        gridView = (GridView) findViewById(R.id.gridView);

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

        List<String> listTopicName = new ArrayList<>();
        listTopicName = getTopic();

        signatureText = new String[listTopicName.size()];
        signatureText = listTopicName.toArray(signatureText);


        GridViewAdapter adapter = new GridViewAdapter(LibraryActivity.this, icons, signatureText);

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //
                //ADD LIBRARY TO USERS LIST
                //
                Toast.makeText(LibraryActivity.this, "You tapped: " + signatureText[position], Toast.LENGTH_SHORT).show();//DELETE THIS
            }
        });
    }



    // Move to Helper
    public List<String> getTopic(){
        List<String> listTopic = new ArrayList<>();
        Cursor cursor = mDb.rawQuery("SELECT * FROM vocabulary", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            listTopic.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        return listTopic;
    }

}
