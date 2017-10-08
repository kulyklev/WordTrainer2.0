package com.example.admin.wordtrainer20;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.wordtrainer20.HelperClasses.Exercise;
import com.example.admin.wordtrainer20.HelperClasses.MarkExercise;
import com.example.admin.wordtrainer20.HelperClasses.Word;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ExerciseTwoActivity extends GeneralMenu {
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private EditText answer;
    private TextView textShow;
    private Button skipButt;
    List<Word> ListWord = new ArrayList<>();
    Word nowStudy = new Word();

    private void init(){
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

        answer = (EditText) findViewById(R.id.textAnswer);
        textShow = (TextView) findViewById(R.id.textViewShow);
        skipButt = (Button) findViewById(R.id.button_next);

        Exercise obj = new Exercise(ListWord);

        // Step first - generation word on textView;
        nowStudy = obj.getWordForTextView();
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
                    if (nowStudy.checkWorld(userTranslate, MarkExercise.WRITING))
                    {
                        textShow.setText("Yes");
                        // Show button Next;
                        // Button click on Next -> repeat step1 and hide itself and show button Check
                        // Also clear textBox
                        int id = getIdByEnglish(nowStudy.getEnglishWord());
                        setWord(id,1);
                    }
                    else
                    {
                        textShow.setText("No");
                        // Show right answer in label (setText (nowStudy.getEnglishWord()))
                        // Show button Next;
                        // Click on Next repeat step1 and hide itself and show button Check
                        // Also clear textBox
                        int id = getIdByEnglish(nowStudy.getEnglishWord());
                        setWord(id,0);
                    }
                    return true;
                }
                else
                    return false;
            }
        });

        skipButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                //Todo implement this button
                //
            }
        });
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
        setContentView(R.layout.activity_exercise_two);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            ListWord = (List<Word>) extras.getSerializable("ListWord");
            System.out.println(ListWord.size());
            // do something with the customer
        }
        init();
    }
}
