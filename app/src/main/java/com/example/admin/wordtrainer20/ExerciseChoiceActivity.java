package com.example.admin.wordtrainer20;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    private Button selectButtons[] = new Button[5];
    private List<Word> copy = new ArrayList<>();
    private Exercise learningObject;
    private Word nowStudy = new Word();
    private TextView textShow;
    private MarkExercise TypeExercise;
    private boolean ans; // Верный / неверный ответ
    private List<Word> listRandom; // Рандом элементов выбора

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_choice);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            List<Word> ListWord = new ArrayList<>();    // Набор для изучения
            ListWord = (List<Word>) extras.getSerializable("ListWord");
            TypeExercise = (MarkExercise) extras.getSerializable("TitleExercise");
            learningObject = new Exercise(ListWord);
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
            throw new RuntimeException();
        }
    }


    private void init() throws IOException {
        connectionDatabase();
        textShow = (TextView) findViewById(R.id.wordTextView);

        if (learningObject.isTrainingOff(TypeExercise, mDb)) { // Проверка на конец тренировки
            copy = learningObject.enableStudiedWords(TypeExercise, mDb);
            if (copy.size() > 0) {
                for (Word w : copy) {
                    learningObject.removeWordInList(w);
                }
            }
            nowStudy = learningObject.getWordForTextView(TypeExercise, mDb);
            if (TypeExercise == MarkExercise.RUS_TO_ENG)
                textShow.setText(nowStudy.getRussianWord());
            else
                textShow.setText(nowStudy.getEnglishWord());
        } else {
            Toast.makeText(getApplicationContext(), "Все слова на этой треннировке пройдены", Toast.LENGTH_LONG).show();
            finish();
        }

        listRandom = new ArrayList<>();
        listRandom = learningObject.getListChoice(mDb, 4);
        listRandom.add(nowStudy);
        Collections.shuffle(listRandom);

        initializationButton(listRandom);
    }


    private void initializationButton(List<Word> listRandom) {
        int[] buttons = new int[]{R.id.variantButt_1, R.id.variantButt_2, R.id.variantButt_3, R.id.variantButt_4, R.id.variantButt_5};

        for (int i = 0; i < buttons.length; i++) {
            selectButtons[i] = (Button) findViewById(buttons[i]);
            selectButtons[i].setOnClickListener(this);
            selectButtons[i].setText(wordForExercise(TypeExercise, i, listRandom));
            selectButtons[i].setEnabled(true);
        }

        Button skipBut = (Button) findViewById(R.id.NextButt);
        skipBut.setOnClickListener(this);

    }

    // В зависимости от тренировки смена параметров

    private String wordForExercise(MarkExercise mark, int index, List<Word> listRandom) {
        if (mark == MarkExercise.RUS_TO_ENG)
            return listRandom.get(index).getEnglishWord();
        else
            return listRandom.get(index).getRussianWord();
    }

    public void checkAnswer(int num) {
        if (nowStudy.checkWord(selectButtons[num].getText().toString(), TypeExercise)) {
            answerTrue(selectButtons[num]);
            if (learningObject.getWordList().size() == 0) {
                learningObject.setWordList(copy);
                checkEndExercise();
            }
        } else {
            answerFalse(selectButtons[num]);
        }
    }

    @Override
    public void onClick(View v) {
        for (Button button : selectButtons) {
            button.setEnabled(false);
        }
        switch (v.getId()) {
            case R.id.variantButt_1:
                checkAnswer(0);
                break;

            case R.id.variantButt_2:
                checkAnswer(1);
                break;

            case R.id.variantButt_3:
                checkAnswer(2);
                break;

            case R.id.variantButt_4:
                checkAnswer(3);
                break;

            case R.id.variantButt_5:
                checkAnswer(4);
                break;

            case R.id.NextButt:

                textShow.setText("");
                for (Button button : selectButtons) {
                    button.setBackgroundResource(android.R.drawable.btn_default);
                }

                Word tempSave = new Word();

                if (!ans && learningObject.getWordList().size() > 1) {
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

                if (!ans)
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
        selectExerciseActivity.putExtra("UniqForm", "Tranning");
        selectExerciseActivity.putExtra("ListWord", (Serializable) learningObject.getWordList());
        startActivity(selectExerciseActivity);
    }

    public void answerFalse(Button btn) {
        changeButtonBackground(btn, false);
        //textShow.setText("No");
        ans = false;
        learningObject.setWord(nowStudy.getId(), 0, mDb, MarkExercise.WRITING);
    }

    public void answerTrue(final Button btn) {
        changeButtonBackground(btn, true);
        ans = true;
        learningObject.setWord(nowStudy.getId(), 1, mDb, TypeExercise);
        learningObject.removeWordInList(nowStudy);
        copy.add(nowStudy);
    }

    //if color = true => green, if color = false => red
    private void changeButtonBackground(final Button btn, boolean color) {
        String clr = color ? "#FF00FF00" : "#FFFF0000";
        final float[] from = new float[3],
                to = new float[3];

        Color.colorToHSV(Color.parseColor("#FFFFFFFF"), from);   // from white
        Color.colorToHSV(Color.parseColor(clr), to);     // to red

        ValueAnimator anim = ValueAnimator.ofFloat(0, 1);   // animate from 0 to 1
        anim.setDuration(300);                              // for 300 ms

        final float[] hsv = new float[3];                  // transition color
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // Transition along each axis of HSV (hue, saturation, value)
                for (int i = 0; i < 3; i++) {
                    hsv[i] = from[i] + (to[i] - from[i]) * animation.getAnimatedFraction();
                }
                btn.setBackgroundColor(Color.HSVToColor(hsv));
            }
        });

        anim.start();
    }
}
