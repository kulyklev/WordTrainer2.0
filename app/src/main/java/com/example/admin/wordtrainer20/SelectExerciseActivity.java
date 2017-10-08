package com.example.admin.wordtrainer20;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SelectExerciseActivity extends GeneralMenu {
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private ImageButton exerciseOne;
    private ImageButton exerciseTwo;
    private ImageButton exerciseThree;
    private ImageButton exerciseFour;
    private final int NUMBER_FOR_TRAINING = 10;


    private void init(){
        exerciseOne = (ImageButton) findViewById(R.id.ExerciseOneImageButton);
        exerciseTwo = (ImageButton) findViewById(R.id.ExerciseTwoImageButton);
        exerciseThree = (ImageButton) findViewById(R.id.ExerciseThreeImageButton);
        exerciseFour = (ImageButton) findViewById(R.id.ExerciseFourImageButton);

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


        exerciseOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Intent openExerciseTwoActivity = new Intent(SelectExerciseActivity.this, ExerciseOneActivity.class);
                startActivity(openExerciseTwoActivity);
                //
            }
        });

        exerciseTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                List<Word> listforExTwo = new ArrayList<Word>();
                try {
                    listforExTwo = getWords("Writing");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //
                Intent openExerciseTwoActivity = new Intent(SelectExerciseActivity.this, ExerciseTwoActivity.class);
                openExerciseTwoActivity.putExtra("ListWord", (Serializable) listforExTwo);
                startActivity(openExerciseTwoActivity);
            }
        });

        exerciseThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                //DO SOME STUFF
                //
                List<Word> listforExThree = new ArrayList<Word>();
                try {
                    listforExThree = getWords("Choice");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //
                Intent openExerciseOneActivity = new Intent(SelectExerciseActivity.this, ExerciseThreeActivity.class);
                openExerciseOneActivity.putExtra("ListWord", (Serializable) listforExThree);
                startActivity(openExerciseOneActivity);

            }
        });

        exerciseFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                //DO SOME STUFF
                //
            }
        });

    }

    public List<Word> getWords(String field) throws IOException
    {
        List<Word> result = new ArrayList<>();
        String query = "SELECT * FROM words";
        Cursor cursor = mDb.rawQuery(query, null);

        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            do {
                Word word = new Word();
                word.setEnglishWord(cursor.getString(cursor.getColumnIndex("English")));
                word.setRussianWord(cursor.getString(cursor.getColumnIndex("Russian")));
                //word.setCheck(false);
                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                int category = cursor.getInt(cursor.getColumnIndex("Category"));

                Boolean isSelected = getIsSelected(category);
                Boolean isStudied = getIsStudied(id);
                Boolean isTrainingOver = getFieldById(field,id);
                if (isSelected && !isStudied && !isTrainingOver)
                {
                    result.add(word);
                }
            } while (cursor.moveToNext() && result.size()< NUMBER_FOR_TRAINING);
        }
        cursor.close();
        return result;
    }

    public Boolean getIsSelected(int category){
        Cursor cursor = mDb.rawQuery("SELECT * FROM vocabulary WHERE _id='"+ category + "'", null);
        cursor.moveToFirst();
        Boolean i = cursor.getInt(cursor.getColumnIndex("isSelected")) == 1;
        cursor.close();
        return i;
    }

    public Boolean getIsStudied(int id){
        Cursor cursor = mDb.rawQuery("SELECT * FROM study WHERE _id='"+ id + "'", null);
        cursor.moveToFirst();
        Boolean i = cursor.getInt(cursor.getColumnIndex("isStudied")) == 1;
        cursor.close();
        return i;
    }

    public Boolean getFieldById(String field, int id){
        Cursor cursor = mDb.rawQuery("SELECT * FROM trainings WHERE _id='"+ id + "'", null);
        cursor.moveToFirst();
        Boolean t = cursor.getInt(cursor.getColumnIndex(field))==1;
        cursor.close();
        return t;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_exercise);

        init();
    }

    /*//
    //
    //CODE BELOW MUST BE REFACTERED
    //
    //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_setting:
                Toast.makeText(SelectExerciseActivity.this, "You have tapped on SETTING.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_about_us:
                Toast.makeText(SelectExerciseActivity.this, "You have tapped on ABOUT US.", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
