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

public class MyDictionaries extends GeneralMenu {

    private DatabaseHelper mDBHelper;  // Вспомогательный класс для подключения базы
    private SQLiteDatabase mDb;        // Соединение с базой
    private String[] dataUserTopic;    // Пользовательские словари
    private ListView listView;         // Список для отображения
    private ListViewAdapter listViewAdapter;

    public ImageButton openLibraryActivityButt;

    //  Мои словари
    public void init(){
        mDBHelper = new DatabaseHelper(this);

        checkConnectionDatabase();

        List<String> userTopics = getTopic();
        dataUserTopic = new String[userTopics.size()];
        dataUserTopic = userTopics.toArray(dataUserTopic);

        openLibraryActivityButt = (ImageButton) findViewById(R.id.openLibrariesButton);
        openLibraryActivityButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openLibrariesActivity = new Intent(MyDictionaries.this, LibraryActivity.class);
                startActivity(openLibrariesActivity);
            }
        });

        listViewAdapter = new ListViewAdapter(MyDictionaries.this, dataUserTopic);
        listView = (ListView) findViewById(R.id.LibListView);
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                //Pass some dataUserTopic
                Intent openListOfWordsActivity = new Intent(MyDictionaries.this, ListOfWordsActivity.class);
                openListOfWordsActivity.putExtra("id", getId(dataUserTopic[position]));
                startActivity(openListOfWordsActivity);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyDictionaries.this);
                builder.setTitle("Delete")
                        .setMessage("Do you want to delete " + dataUserTopic[position])
                        .setCancelable(true);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(), "You clicked YES", Toast.LENGTH_SHORT).show();

                        int id = getId(dataUserTopic[position]);
                        setVocabulary(id);

                        finish();
                        startActivity(getIntent());

                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {

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

    public void checkConnectionDatabase() {
        try
        {
            mDBHelper.updateDataBase();
        }
        catch (IOException mIOException)
        {
            throw new Error("UnableToUpdateDatabase");
        }

        try
        {
            mDb = mDBHelper.getWritableDatabase();
        }
        catch (SQLException mSQLException)
        {
            throw mSQLException;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dictionaries);

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
