package com.example.admin.wordtrainer20;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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
import java.util.Random;

public class ExerciseTrueFalseActivity extends GeneralMenu {

    private DatabaseHelper mDBHelper;                   // Класс для работы с базой
    private SQLiteDatabase mDb;                         // Конект базы

    // Кнопки
    private Button btnTrue;
    private Button btnFalse;
    private Button btnSkip;

    private Exercise learningObject;                    // Реализация упражнения
    private TextView textViewEnglishWord;               // Поле для вывод английского слова
    private TextView textViewRussianWord;               // Поле для вывод русского слова

    private Word nowStudy;                              // Слово изучаемо на данный момент
    private List<Word> listRandom;                      // Список вариантов ответов
    private List<Word> copy;                            // Дополнительный список
    private boolean answer = false;                        // Ответ верный / не верный

    private void init() throws IOException {

        textViewEnglishWord = (TextView) findViewById(R.id.textViewShowEng);
        textViewRussianWord = (TextView) findViewById(R.id.textViewShowRus);

        connectionDatabase();           // Коннект

        copy = new ArrayList<>();       // Инициализация доп массива
        // Проверка на слова, которые уже прошли данную тренировку

        if (learningObject.isTrainingOff(MarkExercise.TRUE_OR_FALSE, mDb)) {
            // Проверка на конец тренировки
            copy = learningObject.enableStudiedWords(MarkExercise.TRUE_OR_FALSE, mDb);
            if (copy.size() > 0) {
                for (Word w : copy) {
                    learningObject.removeWordInList(w);
                }
            }
            nowStudy = learningObject.getWordForTextView(MarkExercise.TRUE_OR_FALSE, mDb);
            textViewEnglishWord.setText(nowStudy.getEnglishWord());
        } else {
            // Если все слова прошли тренировку, вывести сообщение и закрыть окно.
            Toast.makeText(getApplicationContext(), "Все слова на этой треннировке пройдены", Toast.LENGTH_LONG).show();
            finish();
        }

        // Рандом слов для проверки правильный перевод или нет (всего 3 слова, одно из них 100% правильное)
        listRandom = new ArrayList<>();
        listRandom = learningObject.getListChoice(mDb, 2);
        listRandom.add(nowStudy);
        Collections.shuffle(listRandom);

        // Выводим один из вариантов на экран
        textViewRussianWord.setText(listRandom.get(randomWord(0, listRandom.size())).getRussianWord());

        btnTrue = (Button) findViewById(R.id.buttonYes);
        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nowStudy.checkWord(textViewRussianWord.getText().toString(), MarkExercise.TRUE_OR_FALSE)) {
                    // Процес аналогичен для всех тренировок.
                    answer = true;
                    changeButtonBackground(btnTrue, answer);

                    learningObject.setWord(nowStudy.getId(), 1, mDb, MarkExercise.TRUE_OR_FALSE);
                    learningObject.removeWordInList(nowStudy);
                    copy.add(nowStudy);

                    if (learningObject.getWordList().size() == 0) {
                        learningObject.setWordList(copy);
                        checkEndExercise();
                    }
                } else {
                    textViewRussianWord.setText("Correct answer: " + nowStudy.getRussianWord());
                    changeButtonBackground(btnTrue, answer);
                }

            }
        });

        btnFalse = (Button) findViewById(R.id.buttonFalse);
        btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Если перевод слова неверный и пользователь нажал "No", тогда пользователь прав и слово обновляется, как изучено
                if (!nowStudy.checkWord(textViewRussianWord.getText().toString(), MarkExercise.TRUE_OR_FALSE)) {
                    // Процес аналогичен для всех тренировок.

                    answer = true;
                    changeButtonBackground(btnFalse, answer);

                    learningObject.setWord(nowStudy.getId(), 1, mDb, MarkExercise.TRUE_OR_FALSE);
                    learningObject.removeWordInList(nowStudy);
                    copy.add(nowStudy);

                    if (learningObject.getWordList().size() == 0) {
                        learningObject.setWordList(copy);
                        checkEndExercise();
                    }
                } else {
                    textViewRussianWord.setText("Correct answer: " + nowStudy.getRussianWord());
                    changeButtonBackground(btnFalse, answer);
                }
            }
        });

        btnSkip = (Button) findViewById(R.id.NextButton);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                defaultButtonBackground();
                // Процес аналогичен для всех тренировок.

                textViewEnglishWord.setText("");
                textViewRussianWord.setText("");

                Word tempSave = new Word();

                if (answer == false && learningObject.getWordList().size() > 1) {
                    tempSave.setId(nowStudy.getId());
                    tempSave.setEnglishWord(nowStudy.getEnglishWord());
                    tempSave.setRussianWord(nowStudy.getRussianWord());
                    learningObject.removeWordInList(nowStudy);
                }

                nowStudy = learningObject.getWordForTextView(MarkExercise.TRUE_OR_FALSE, mDb);
                textViewEnglishWord.setText(nowStudy.getEnglishWord());

                try {
                    listRandom = learningObject.getListChoice(mDb, 2);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                listRandom.add(nowStudy);
                Collections.shuffle(listRandom);

                textViewRussianWord.setText(listRandom.get(randomWord(0, listRandom.size() - 1)).getRussianWord());

                if (answer == false)
                    learningObject.insertWordInList(tempSave);

                answer = false;

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_true_or_false);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            List<Word> ListWord = new ArrayList<>();    // Набор для изучения
            ListWord = (List<Word>) extras.getSerializable("ListWord");
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
            throw mSQLException;
        }
    }

    public int randomWord(int min, int max) {
        max--;
        Random random = new Random();
        return random.nextInt(max + 1 - min) + min;
    }

    public void checkEndExercise() {
        finish();
        Intent selectExerciseActivity = new Intent(this, SelectExerciseActivity.class);
        selectExerciseActivity.putExtra("UniqForm", "Tranning");
        selectExerciseActivity.putExtra("ListWord", (Serializable) learningObject.getWordList());
        startActivity(selectExerciseActivity);
    }

    private void changeButtonBackground(final Button btn, boolean color) {
        //if color = true => green
        //if color = false => red

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
                hsv[0] = from[0] + (to[0] - from[0]) * animation.getAnimatedFraction();
                hsv[1] = from[1] + (to[1] - from[1]) * animation.getAnimatedFraction();
                hsv[2] = from[2] + (to[2] - from[2]) * animation.getAnimatedFraction();

                btn.setBackgroundColor(Color.HSVToColor(hsv));
            }
        });

        anim.start();
    }

    private void defaultButtonBackground() {
        btnFalse.setBackgroundResource(android.R.drawable.btn_default);
        btnTrue.setBackgroundResource(android.R.drawable.btn_default);
    }
}
