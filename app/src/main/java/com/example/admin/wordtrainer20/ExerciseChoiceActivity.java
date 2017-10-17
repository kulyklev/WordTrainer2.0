package com.example.admin.wordtrainer20;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
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
import java.util.Collections;
import java.util.List;

public class ExerciseChoiceActivity extends AppCompatActivity implements View.OnClickListener {
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private Button selectBtn_1;
    private Button selectBtn_2;
    private Button selectBtn_3;
    private Button selectBtn_4;
    private Button selectBtn_5;
    private Button skipBut;
    private final int RANDOM_NUMBER = 5;
    private List<Word> copy = new ArrayList<>();
    private Exercise learningObject;
    private Word nowStudy = new Word();
    private TextView textShow;


    /*
    ДОБАВЬ КНОПКУ ДАЛЕЕ (НОРМАЛЬНУЮ КНОПКУ)
    СДЕЛАЙ КНОПКИ ВЫБОРА НЕ ПО ШИРИНЕ ЭКРАНА , А ЧУТЬ МЕНЬШЕ И СИМПОТИЧНЕЙ
    */





    private void init() throws IOException {

        connectionDatabase();
        textShow = (TextView) findViewById(R.id.wordTextView);

        if (learningObject.isTrainingOff(MarkExercise.RUS_TO_ENG, mDb))
        { // Проверка на конец тренировки
            copy = learningObject.enableStudiedWords(MarkExercise.RUS_TO_ENG, mDb);
            if (copy.size() > 0)
            {
                for (Word w: copy) {
                    learningObject.removeWordInList(w);
                }
            }
            nowStudy = learningObject.getWordForTextView(MarkExercise.RUS_TO_ENG, mDb);
            textShow.setText(nowStudy.getRussianWord());
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Все слова на этой треннировке пройдены", Toast.LENGTH_LONG).show();
            finish();
        }

        List <Word> listRandom = new ArrayList<Word>();
        listRandom = learningObject.getListChoice(mDb);
        listRandom.add(nowStudy);
        Collections.shuffle(listRandom);

        selectBtn_1 = (Button) findViewById(R.id.variantButt_1);
        selectBtn_1.setOnClickListener(this);
        selectBtn_1.setText(listRandom.get(0).getEnglishWord());

        selectBtn_2 = (Button) findViewById(R.id.variantButt_2);
        selectBtn_2.setOnClickListener(this);
        selectBtn_2.setText(listRandom.get(1).getEnglishWord());

        selectBtn_3 = (Button) findViewById(R.id.variantButt_3);
        selectBtn_3.setOnClickListener(this);
        selectBtn_3.setText(listRandom.get(2).getEnglishWord());

        selectBtn_4 = (Button) findViewById(R.id.variantButt_4);
        selectBtn_4.setOnClickListener(this);
        selectBtn_4.setText(listRandom.get(3).getEnglishWord());

        selectBtn_5 = (Button) findViewById(R.id.variantButt_5);
        selectBtn_5.setOnClickListener(this);
        selectBtn_5.setText(listRandom.get(4).getEnglishWord());

        skipBut = (Button) findViewById(R.id.NextButt);
        skipBut.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_choice);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            List<Word> ListWord = new ArrayList<>();    // Набор для изучения
            ListWord = (List<Word>) extras.getSerializable("ListWord");
            learningObject = new Exercise(ListWord);
            // do something with the customer
        }
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.variantButt_1:
                //
                if (nowStudy.checkWord(selectBtn_1.getText().toString(), MarkExercise.RUS_TO_ENG))
                {
                    textShow.setText("Yes");
                    //learningObject.setWord(nowStudy.getId(),  1, mDb, MarkExercise.RUS_TO_ENG);
                }
                else
                {
                    textShow.setText("No");
                    //learningObject.setWord(nowStudy.getId(), 0, mDb, MarkExercise.RUS_TO_ENG);
                }

                break;

            case R.id.variantButt_2:

                if (nowStudy.checkWord(selectBtn_2.getText().toString(), MarkExercise.RUS_TO_ENG))
                {
                    textShow.setText("Yes");
                    //learningObject.setWord(nowStudy.getId(), 1, mDb, MarkExercise.RUS_TO_ENG);
                }
                else
                {
                    textShow.setText("No");
                    //learningObject.setWord(nowStudy.getId(), 0, mDb, MarkExercise.RUS_TO_ENG);
                }

                break;

            case R.id.variantButt_3:

                if (nowStudy.checkWord(selectBtn_3.getText().toString(), MarkExercise.RUS_TO_ENG))
                {
                    textShow.setText("Yes");
                    //learningObject.setWord(nowStudy.getId(), 1, mDb, MarkExercise.RUS_TO_ENG);
                }
                else
                {
                    textShow.setText("No");
                    //learningObject.setWord(nowStudy.getId(), 0, mDb, MarkExercise.RUS_TO_ENG);
                }

                break;

            case R.id.variantButt_4:

                if (nowStudy.checkWord(selectBtn_4.getText().toString(), MarkExercise.RUS_TO_ENG))
                {
                    textShow.setText("Yes");
                    //learningObject.setWord(nowStudy.getId(), 1, mDb, MarkExercise.RUS_TO_ENG);
                }
                else
                {
                    textShow.setText("No");
                    //learningObject.setWord(nowStudy.getId(), 0, mDb, MarkExercise.RUS_TO_ENG);
                }

                break;

            case R.id.variantButt_5:

                if (nowStudy.checkWord(selectBtn_5.getText().toString(), MarkExercise.RUS_TO_ENG))
                {
                    textShow.setText("Yes");
                    //learningObject.setWord(nowStudy.getId(), 1, mDb, MarkExercise.RUS_TO_ENG);
                }
                else
                {
                    textShow.setText("No");
                    //learningObject.setWord(nowStudy.getId(), 0, mDb, MarkExercise.RUS_TO_ENG);
                }
                break;

            case R.id.NextButt:
                //
                //
                //SKIP Button
                //
                //
                break;
            default:
                break;
        }
    }


}
