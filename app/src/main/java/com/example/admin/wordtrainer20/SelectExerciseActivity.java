package com.example.admin.wordtrainer20;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.admin.wordtrainer20.HelperClasses.DatabaseHelper;
import com.example.admin.wordtrainer20.HelperClasses.Exercise;
import com.example.admin.wordtrainer20.HelperClasses.Word;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SelectExerciseActivity extends GeneralMenu {
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private ImageButton exerciseTrue_or_False;
    private ImageButton exerciseWriting;
    private ImageButton exerciseChoiceRus_to_Eng;
    private ImageButton exerciseChoiceEng_to_Rus;
    private final int NUMBER_FOR_TRAINING = 10;
    private List<Word> listWord = new ArrayList<Word>();
    private Exercise learningObject;
    private int id_category;

    /*
    ПОКАЖИ ПРИМЕР, КАК ЭНЕЙБЛИТЬ КНОПКИ (ENABLE = false)
    ВО ВСЕХ УПРАЖНЕНИЯХ ДОЛЖНА БЫТЬ КНОПКА ДАЛЕЕ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */



    private void init(){

        exerciseTrue_or_False = (ImageButton) findViewById(R.id.ExerciseOneImageButton);
        exerciseWriting = (ImageButton) findViewById(R.id.ExerciseTwoImageButton);
        exerciseChoiceRus_to_Eng = (ImageButton) findViewById(R.id.ExerciseThreeImageButton);
        exerciseChoiceEng_to_Rus = (ImageButton) findViewById(R.id.ExerciseFourImageButton);

        connectionDatabase();


        if (listWord.isEmpty()){
            try {
                listWord = getWords();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        exerciseTrue_or_False.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                // DO SOME STUFF
                //
                Intent exerciseTrue_or_False = new Intent(SelectExerciseActivity.this, ExerciseTrueFalseActivity.class);
                exerciseTrue_or_False.putExtra("ListWord", (Serializable) listWord);
                startActivity(exerciseTrue_or_False);
                //
            }
        });

        exerciseWriting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                // DO SOME STUFF
                //
                Intent exerciseWriting = new Intent(SelectExerciseActivity.this, ExerciseWritingActivity.class);
                exerciseWriting.putExtra("ListWord", (Serializable) listWord);
                startActivity(exerciseWriting);
            }
        });

        exerciseChoiceRus_to_Eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                // DO SOME STUFF
                //
                Intent exerciseChoiceRus_to_Eng = new Intent(SelectExerciseActivity.this, ExerciseChoiceActivity.class);
                exerciseChoiceRus_to_Eng.putExtra("ListWord", (Serializable) listWord);
                startActivity(exerciseChoiceRus_to_Eng);
            }
        });

        /*


        exerciseChoiceEng_to_Rus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Word> listChoiceEng_to_Rus = new ArrayList<Word>();
                try {
                    listChoiceEng_to_Rus  = getWords("Choice");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //
                Intent openExerciseOneActivity = new Intent(SelectExerciseActivity.this, ExerciseChoiceActivity.class);
                openExerciseOneActivity.putExtra("ListWord", (Serializable) listChoiceEng_to_Rus);
                startActivity(openExerciseOneActivity);
            }
        });
        */

    }

    public void connectionDatabase() {
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
    }



    // Выбор 10 случайных слов
    public List<Word> getWords() throws IOException
    {
        List<Word> result = new ArrayList<>();
        String query = "SELECT * FROM words WHERE Category ='" + id_category + "'";
        Cursor cursor = mDb.rawQuery(query, null);

        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            do
            {
                Word word = new Word();
                word.setEnglishWord(cursor.getString(cursor.getColumnIndex("English")));
                word.setRussianWord(cursor.getString(cursor.getColumnIndex("Russian")));
                word.setId(cursor.getInt(cursor.getColumnIndex("_id")));
               // int category = cursor.getInt(cursor.getColumnIndex("Category"));
                Boolean t = getFieldById("Writing", word.getId());
                word.setProgress(t);
                Boolean isSelected = getIsSelected(id_category);
                Boolean isStudied = getIsStudied(word.getId());
                if (isSelected && !isStudied)
                {
                    result.add(word);
                }
            }
            while (cursor.moveToNext() && result.size()< NUMBER_FOR_TRAINING);
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

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            id_category = (int) extras.getInt("id");;
        }
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
