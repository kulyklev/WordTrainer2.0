package com.example.admin.wordtrainer20;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.wordtrainer20.HelperClasses.DatabaseHelper;
import com.example.admin.wordtrainer20.HelperClasses.Exercise;
import com.example.admin.wordtrainer20.HelperClasses.MarkExercise;
import com.example.admin.wordtrainer20.HelperClasses.Word;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ExerciseWritingActivity extends GeneralMenu {
    
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private EditText answer;
    private TextView textShow;
    private Button nextBtn;
    private Exercise learningObject;
    List<Word> ListWord = new ArrayList<>();
    Word nowStudy = new Word();
    List<Word> copy = new ArrayList<>();

    private void init(){
        
        connectionDatabase();

        answer = (EditText) findViewById(R.id.textAnswer);
        textShow = (TextView) findViewById(R.id.textViewShow);
        nextBtn = (Button) findViewById(R.id.button_next);


        nowStudy = learningObject.getWordForTextView(MarkExercise.WRITING, mDb);
        textShow.setText(nowStudy.getEnglishWord());

        answer.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //
                //DO SOME STUFF
                //
                if(event.getAction() == KeyEvent.ACTION_DOWN &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // сохраняем текст, введенный до нажатия Enter в переменную
                    String userTranslate = answer.getText().toString();
                    if (nowStudy.checkWord(userTranslate, MarkExercise.WRITING))
                    {
                        textShow.setText("Yes");
                        // Show button Next;
                        // Button click on Next -> repeat step1 and hide itself and show button Check
                        // Also clear textBox
                        //int id = getIdByEnglish(nowStudy.getEnglishWord());
                        learningObject.setWord(nowStudy.getId(),1, mDb, MarkExercise.WRITING);
                        learningObject.removeWordInList(nowStudy);
                        copy.add(nowStudy);
                    }
                    else
                    {
                        textShow.setText("No");
                        // Show right answer in label (setText (nowStudy.getEnglishWord()))
                        // Show button Next;
                        // Click on Next repeat step1 and hide itself and show button Check
                        // Also clear textBox

                        learningObject.setWord(nowStudy.getId(),0, mDb, MarkExercise.WRITING);
                    }
                    return true;
                }
                else
                    return false;
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                if (copy.size()<10)
                {
                    textShow.setText("");
                    nowStudy = learningObject.getWordForTextView(MarkExercise.WRITING, mDb);
                    textShow.setText(nowStudy.getEnglishWord());
                    answer.setText("");
                }
                else
                {
                   finish();
                }
                //Todo implement this button
                //
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

    public void setWord(long id, long val){
        Cursor cursor = mDb.rawQuery("UPDATE trainings" +
                " SET Writing='" + val +"'"+" WHERE _id='" + id + "'",null);
        cursor.moveToFirst();
        cursor.close();
    }

    public int getIdByEnglish(String english){
        String copyEnglish = english.replaceAll("'", "''");

        Cursor cursor = mDb.rawQuery("SELECT * FROM words WHERE English='"+ copyEnglish + "'", null);
        cursor.moveToFirst();
        int i = cursor.getInt(cursor.getColumnIndex("_id"));
        cursor.close();
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_writing);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            ListWord = (List<Word>) extras.getSerializable("ListWord");
            learningObject = new Exercise(ListWord);
            // do something with the customer
        }
        init();
    }
}
