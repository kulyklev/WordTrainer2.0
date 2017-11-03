package com.example.admin.wordtrainer20;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.admin.wordtrainer20.HelperClasses.DatabaseHelper;
import com.example.admin.wordtrainer20.HelperClasses.MarkExercise;
import com.example.admin.wordtrainer20.HelperClasses.Word;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SelectExerciseActivity extends GeneralMenu {
    private final int NUMBER_FOR_TRAINING = 10;
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private ImageButton exerciseTrue_or_False;
    private ImageButton exerciseWriting;
    private ImageButton exerciseChoiceRus_to_Eng;
    private ImageButton exerciseChoiceEng_to_Rus;
    private List<Word> listWord = new ArrayList<Word>();
    private int id_category;


    private void init() {

        exerciseTrue_or_False = (ImageButton) findViewById(R.id.ExerciseOneImageButton);
        exerciseWriting = (ImageButton) findViewById(R.id.ExerciseTwoImageButton);
        exerciseChoiceRus_to_Eng = (ImageButton) findViewById(R.id.ExerciseThreeImageButton);
        exerciseChoiceEng_to_Rus = (ImageButton) findViewById(R.id.ExerciseFourImageButton);

        connectionDatabase();

        // Проверить на изученность списка

        if (listWord.isEmpty()) {
            try {
                listWord = getWords();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (isTranningOff()) {
                Toast.makeText(getApplicationContext(), "Слова потренированы!", Toast.LENGTH_LONG).show();
                finish();
                Intent myDictionaries = new Intent(SelectExerciseActivity.this, MyDictionaries.class);
                startActivity(myDictionaries);
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
                exerciseChoiceRus_to_Eng.putExtra("TitleExercise", MarkExercise.RUS_TO_ENG);
                startActivity(exerciseChoiceRus_to_Eng);
            }
        });


        exerciseChoiceEng_to_Rus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent exerciseChoiceEng_to_Rus = new Intent(SelectExerciseActivity.this, ExerciseChoiceActivity.class);
                exerciseChoiceEng_to_Rus.putExtra("ListWord", (Serializable) listWord);
                exerciseChoiceEng_to_Rus.putExtra("TitleExercise", MarkExercise.ENG_TO_RUS);
                startActivity(exerciseChoiceEng_to_Rus);
            }
        });

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
    public List<Word> getWords() throws IOException {
        List<Word> result = new ArrayList<>();
        String query = "SELECT * FROM words WHERE Category ='" + id_category + "'";
        Cursor cursor = mDb.rawQuery(query, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Word word = new Word();
                word.setEnglishWord(cursor.getString(cursor.getColumnIndex("English")));
                word.setRussianWord(cursor.getString(cursor.getColumnIndex("Russian")));
                word.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                Boolean isSelected = getIsSelected(id_category);
                Boolean isStudied = getIsStudied(word.getId());
                if (isSelected && !isStudied) {
                    result.add(word);
                }
            }
            while (cursor.moveToNext() && result.size() < NUMBER_FOR_TRAINING);
        }
        cursor.close();
        return result;
    }

    public Boolean getIsSelected(int category) {
        Cursor cursor = mDb.rawQuery("SELECT * FROM vocabulary WHERE _id='" + category + "'", null);
        cursor.moveToFirst();
        Boolean i = cursor.getInt(cursor.getColumnIndex("isSelected")) == 1;
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

    public boolean isTranningOff() {
        boolean isOff = true;
        for (Word w : listWord) {
            if (!w.checkComplete(mDb)) {
                isOff = false;
                break;
            }
        }

        return isOff;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_exercise);

        Bundle extrasFirst = getIntent().getExtras();
        if (extrasFirst != null) {
            String activity = extrasFirst.getString("UniqForm");
            if ((activity.equals("MyVocabulary")))
                id_category = (int) extrasFirst.getInt("id");
            else if ((activity.equals("Tranning")))
                listWord = (List<Word>) extrasFirst.getSerializable("ListWord");
        }

        init();
    }

}
