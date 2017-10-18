package com.example.admin.wordtrainer20;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.admin.wordtrainer20.HelperClasses.DatabaseHelper;
import com.example.admin.wordtrainer20.HelperClasses.Exercise;
import com.example.admin.wordtrainer20.HelperClasses.MarkExercise;
import com.example.admin.wordtrainer20.HelperClasses.Word;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExerciseTrueFalseActivity extends GeneralMenu {

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private Button btnTrue;
    private Button btnFalse;
    private Exercise learningObject;
    private TextView textShowEnglish;
    private TextView textShowRussian;
    private Word nowStudy;

    private void init(){

        textShowEnglish = (TextView) findViewById(R.id.textViewShowEng);
        textShowRussian= (TextView) findViewById(R.id.textViewShowRus);

        btnTrue = (Button) findViewById(R.id.buttonYes);

        connectionDatabase();

        nowStudy = learningObject.getWordForTextView(MarkExercise.TRUE_OR_FALSE, mDb);
        textShowEnglish.setText(nowStudy.getEnglishWord());

        String true_or_false = "";




        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                //DO SOME STUFF ON BUTTON CLICK "I KNOW"
                //
                Toast.makeText(getApplicationContext(), "You clicked button YES", Toast.LENGTH_SHORT).show();
            }
        });

        btnFalse = (Button) findViewById(R.id.buttonFalse);
        btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                //DO SOME STUFF ON BUTTON CLICK "I DON`T KNOW"
                //
                Toast.makeText(getApplicationContext(), "You clicked button NO", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_true_or_false);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            List<Word> ListWord = new ArrayList<>();    // Набор для изучения
            ListWord = (List<Word>) extras.getSerializable("ListWord");
            learningObject = new Exercise(ListWord);
        }
        init();
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
}
