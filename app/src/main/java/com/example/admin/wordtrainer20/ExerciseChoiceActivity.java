package com.example.admin.wordtrainer20;

import android.content.Intent;
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
import java.io.Serializable;
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
    private MarkExercise TypeExercise;
    private boolean ans; // Верный / Не вырный ответ
    private List <Word> listRandom; // Рандом элементов выбора



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_choice);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            List<Word> ListWord = new ArrayList<>();    // Набор для изучения
            ListWord = (List<Word>) extras.getSerializable("ListWord");
            TypeExercise = (MarkExercise) extras.getSerializable("TitleExercise");
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


    private void init() throws IOException {

        connectionDatabase();

        textShow = (TextView) findViewById(R.id.wordTextView);

        if (learningObject.isTrainingOff(TypeExercise, mDb))
        { // Проверка на конец тренировки
            copy = learningObject.enableStudiedWords(TypeExercise, mDb);
            if (copy.size() > 0)
            {
                for (Word w: copy) {
                    learningObject.removeWordInList(w);
                }
            }
            nowStudy = learningObject.getWordForTextView(TypeExercise, mDb);
            if (TypeExercise == MarkExercise.RUS_TO_ENG)
                textShow.setText(nowStudy.getRussianWord());
            else
                textShow.setText(nowStudy.getEnglishWord());
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Все слова на этой треннировке пройдены", Toast.LENGTH_LONG).show();
            finish();
        }

        listRandom = new ArrayList<Word>();
        listRandom = learningObject.getListChoice(mDb, 4);
        listRandom.add(nowStudy);
        Collections.shuffle(listRandom);

        initializationButton(listRandom);
    }


    private void initializationButton(List<Word> listRandom) {


        selectBtn_1 = (Button) findViewById(R.id.variantButt_1);
        selectBtn_1.setOnClickListener(this);
        selectBtn_1.setText(wordForExercise(TypeExercise, 0, listRandom));

        selectBtn_2 = (Button) findViewById(R.id.variantButt_2);
        selectBtn_2.setOnClickListener(this);
        selectBtn_2.setText(wordForExercise(TypeExercise, 1, listRandom));

        selectBtn_3 = (Button) findViewById(R.id.variantButt_3);
        selectBtn_3.setOnClickListener(this);
        selectBtn_3.setText(wordForExercise(TypeExercise, 2, listRandom));

        selectBtn_4 = (Button) findViewById(R.id.variantButt_4);
        selectBtn_4.setOnClickListener(this);
        selectBtn_4.setText(wordForExercise(TypeExercise, 3, listRandom));

        selectBtn_5 = (Button) findViewById(R.id.variantButt_5);
        selectBtn_5.setOnClickListener(this);
        selectBtn_5.setText(wordForExercise(TypeExercise, 4, listRandom));

        skipBut = (Button) findViewById(R.id.NextButt);
        skipBut.setOnClickListener(this);
    }


    // В зависимости от тренировки смена параметров

    private String wordForExercise(MarkExercise mark, int index, List<Word> listRandom){
        if (mark == MarkExercise.RUS_TO_ENG)
            return listRandom.get(index).getEnglishWord();
        else
            return listRandom.get(index).getRussianWord();
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.variantButt_1:
                //
                if (nowStudy.checkWord(selectBtn_1.getText().toString(), TypeExercise))
                {
                    textShow.setText("Yes");
                    answerTrue();

                    if (learningObject.getWordList().size()== 0)
                    {
                        learningObject.setWordList(copy);
                        checkEndExercise();
                    }
                }
                else
                {
                    answerFalse();
                }

                break;

            case R.id.variantButt_2:

                if (nowStudy.checkWord(selectBtn_2.getText().toString(), TypeExercise))
                {
                    textShow.setText("Yes");
                    answerTrue();
                    if (learningObject.getWordList().size()== 0)
                    {
                        learningObject.setWordList(copy);
                        checkEndExercise();
                    }
                }
                else
                {
                    textShow.setText("No");
                    answerFalse();
                }

                break;

            case R.id.variantButt_3:

                if (nowStudy.checkWord(selectBtn_3.getText().toString(), TypeExercise))
                {
                    textShow.setText("Yes");
                    answerTrue();
                    if (learningObject.getWordList().size()== 0)
                    {
                        learningObject.setWordList(copy);
                        checkEndExercise();
                    }
                }
                else
                {
                    textShow.setText("No");
                    answerFalse();
                }

                break;

            case R.id.variantButt_4:

                if (nowStudy.checkWord(selectBtn_4.getText().toString(), TypeExercise))
                {
                    textShow.setText("Yes");
                    answerTrue();
                    if (learningObject.getWordList().size()== 0)
                    {
                        learningObject.setWordList(copy);
                        checkEndExercise();
                    }
                }
                else
                {
                    textShow.setText("No");
                    answerFalse();
                }

                break;

            case R.id.variantButt_5:

                if (nowStudy.checkWord(selectBtn_5.getText().toString(), TypeExercise))
                {
                    textShow.setText("Yes");
                    answerTrue();
                    if (learningObject.getWordList().size()== 0)
                    {
                        learningObject.setWordList(copy);
                        checkEndExercise();
                    }
                }
                else
                {
                    textShow.setText("No");
                    answerFalse();
                }
                break;

            case R.id.NextButt:

                textShow.setText("");

                Word tempSave = new Word();

                if (ans == false && learningObject.getWordList().size()>1)
                {
                    tempSave.setId(nowStudy.getId());
                    tempSave.setEnglishWord(nowStudy.getEnglishWord());
                    tempSave.setRussianWord(nowStudy.getRussianWord());
                    learningObject.removeWordInList(nowStudy);
                }

                nowStudy = learningObject.getWordForTextView(TypeExercise, mDb);
                if (TypeExercise == MarkExercise.RUS_TO_ENG)
                    textShow.setText(nowStudy.getRussianWord());
                else
                    textShow.setText(nowStudy.getEnglishWord());

                try {
                    listRandom = learningObject.getListChoice(mDb, 4);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                listRandom.add(nowStudy);
                Collections.shuffle(listRandom);

                initializationButton(listRandom);

                if (ans == false)
                    learningObject.insertWordInList(tempSave);

                ans = false;

                break;
            default:
                break;
        }
    }

    public void checkEndExercise() {
        finish();
        Intent selectExerciseActivity = new Intent(this, SelectExerciseActivity.class);
        selectExerciseActivity.putExtra("UniqForm","Tranning");
        selectExerciseActivity.putExtra("ListWord", (Serializable) learningObject.getWordList());
        startActivity(selectExerciseActivity);
    }

    public void answerFalse() {
        textShow.setText("No");
        ans = false;
        learningObject.setWord(nowStudy.getId(),0, mDb, MarkExercise.WRITING);
    }

    public void answerTrue() {
        ans = true;
        learningObject.setWord(nowStudy.getId(),1, mDb, TypeExercise);
        learningObject.removeWordInList(nowStudy);
        copy.add(nowStudy);
    }


}
