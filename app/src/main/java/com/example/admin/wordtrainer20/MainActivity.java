package com.example.admin.wordtrainer20;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.admin.wordtrainer20.AdapterFolder.ListViewAdapter;
import com.example.admin.wordtrainer20.HelperClasses.DatabaseHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends GeneralMenu {

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private String[] data;
    private ListView listView;
    private ListViewAdapter listViewAdapter;
    public ImageButton openLibraryActivityButt;

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

        openLibraryActivityButt = (ImageButton) findViewById(R.id.openLibrariesButton);
        openLibraryActivityButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openLibrariesActivity = new Intent(MainActivity.this, LibraryActivity.class);
                startActivity(openLibrariesActivity);
            }
        });

        listViewAdapter = new ListViewAdapter(MainActivity.this, data);
        listView = (ListView) findViewById(R.id.LibListView);
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View v,
            int position, long id) {
                //
                //DO SOME STUFF ON ITEM CLICK
                //
                //Pass some data
                Intent openListOfWordsActivity = new Intent(MainActivity.this, ListOfWordsActivity.class);
                startActivity(openListOfWordsActivity);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete")
                        .setMessage("Do you want to delete " + data[position])
                        .setCancelable(true);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        //
                        //Do some stuff, when YES is clicked
                        //
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(), "You clicked YES", Toast.LENGTH_SHORT).show();

                        int id = getId(data[position]);
                        setVocabulary(id);

                        finish();
                        startActivity(getIntent());

                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        //
                        //Do some stuff, when NO is clicked
                        //
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(), "You clicked NO", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

                return true;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public int getId(String name){
        Cursor cursor = mDb.rawQuery("SELECT _id FROM vocabulary WHERE ShortName='"+ name + "'", null);
        cursor.moveToFirst();
        int i = cursor.getInt(cursor.getColumnIndex("_id"));
        cursor.close();
        return i;
    }

    public void setVocabulary(long id){
        Cursor cursor = mDb.rawQuery("UPDATE vocabulary" +
                " SET isSelected = 0 WHERE _id='" + id + "'",null);
        cursor.moveToFirst();
        cursor.close();
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
