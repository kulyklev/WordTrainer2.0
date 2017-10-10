package com.example.admin.wordtrainer20;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.example.admin.wordtrainer20.HelperClasses.DatabaseHelper;
import com.example.admin.wordtrainer20.HelperClasses.Exercise;
import com.example.admin.wordtrainer20.HelperClasses.MarkExercise;
import com.example.admin.wordtrainer20.HelperClasses.Word;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ExerciseWritingActivity extends GeneralMenu {
    
    private DatabaseHelper mDBHelper;  // Класс для работы с базой
    private SQLiteDatabase mDb;        // Конект базы
    private EditText answer;           // Поле для ввода ответа
    private TextView textShow;         // Поле слова для изучения
    private Button nextBtn;            // Кнопка далее
    private Exercise learningObject;   // Реализация упражнения
    private Word nowStudy = new Word();                 // Слово изучаемое на данный момент
    private List<Word> copy = new ArrayList<>();        // Копия набора для изучения
    private boolean ans = false;                        // Ответ верный / не верный


    /*
     КНОПКУ ДАЛЕЕ ОПУСТИ ВНИЗ ( НЕ С БОКУ, А ВНИЗ)
     ДОБАВЬ СТРОКУ ТЕКСТОВУЮ, куда будет выводиться правильный вариант ответа
     НЕПРАВИЛЬНЫЙ ВАРИАНТ ОТВЕТА СТРОКА - должна либо зачеркиваться, либо как то подсвечиваться!!!!!!!
    */





    private void init(){
        
        connectionDatabase();   // Коннект

        answer = (EditText) findViewById(R.id.textAnswer);
        textShow = (TextView) findViewById(R.id.textViewShow);
        nextBtn = (Button) findViewById(R.id.button_next);


        if (learningObject.isTrainingOff(MarkExercise.WRITING, mDb))
        { // Проверка на конец тренировки
            copy = learningObject.enableStudiedWords(MarkExercise.WRITING, mDb);
            if (copy.size() > 0)
            {
                for (Word w: copy) {
                    learningObject.removeWordInList(w);
                }
            }
            nowStudy = learningObject.getWordForTextView(MarkExercise.WRITING, mDb);
            textShow.setText(nowStudy.getRussianWord());
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Все слова на этой треннировке пройдены", Toast.LENGTH_LONG).show();
            finish();
        }



        answer.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //
                //DO SOME STUFF
                //

                if(event.getAction() == KeyEvent.ACTION_DOWN && (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    // сохраняем текст, введенный до нажатия Enter в переменную
                    String userTranslate = answer.getText().toString();

                    if (nowStudy.checkWord(userTranslate, MarkExercise.WRITING))
                    {
                        textShow.setText("Yes");

                        ans = true;

                        learningObject.setWord(nowStudy.getId(),1, mDb, MarkExercise.WRITING);
                        learningObject.removeWordInList(nowStudy);
                        copy.add(nowStudy);

                        if (learningObject.getWordList().size()==0)
                        {
                            learningObject.setWordList(copy);
                            finish();
                            Intent openExerciseSelecetTraining = new Intent(ExerciseWritingActivity.this, ExerciseWritingActivity.class);
                            openExerciseSelecetTraining.putExtra("ListWord", (Serializable) learningObject.getWordList());
                            startActivity(openExerciseSelecetTraining);
                        }
                    }
                    else
                    {
                        textShow.setText("No");
                        ans = false;
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
                textShow.setText("");

                Word tempSave = new Word();
                if (ans == false)
                {
                    tempSave.setId(nowStudy.getId());
                    tempSave.setEnglishWord(nowStudy.getEnglishWord());
                    tempSave.setRussianWord(nowStudy.getRussianWord());
                    learningObject.removeWordInList(nowStudy);
                }

                nowStudy = learningObject.getWordForTextView(MarkExercise.WRITING, mDb);
                textShow.setText(nowStudy.getRussianWord());
                answer.setText("");

                if (ans == false)
                    learningObject.insertWordInList(tempSave);

                ans = false;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_writing);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            List<Word> ListWord = new ArrayList<>();    // Набор для изучения
            ListWord = (List<Word>) extras.getSerializable("ListWord");
            learningObject = new Exercise(ListWord);
            // do something with the customer
        }
        init();
    }
}
