package com.example.admin.wordtrainer20;

import android.content.Intent;
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
    
    private DatabaseHelper mDBHelper;                   // Класс для работы с базой
    private SQLiteDatabase mDb;                         // Конект базы

    private EditText editTextAnswer;                    // Поле для ввода ответа
    private TextView textViewWord;                      // Поле слова для изучения
    private Button btnNext;                             // Кнопка далее

    private Exercise learningObject;                    // Реализация упражнения
    private Word nowStudy = new Word();                 // Слово изучаемое на данный момент
    private List<Word> copy = new ArrayList<>();        // Копия набора для изучения
    private boolean answer = false;                     // Ответ верный / не верный



    private void init(){
        
        connectionDatabase();   // Коннект


        editTextAnswer = (EditText) findViewById(R.id.textAnswer);
        textViewWord = (TextView) findViewById(R.id.textViewShow);
        btnNext = (Button) findViewById(R.id.button_next);

        // Проверка на слова, которые уже прошли данную тренировку

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
            textViewWord.setText(nowStudy.getRussianWord());
        }
        else {

            // Если все слова прошли тренировку, вывести сообщение и закрыть окно.
            Toast.makeText(getApplicationContext(), "Все слова на этой треннировке пройдены", Toast.LENGTH_LONG).show();
            finish();
        }



        editTextAnswer.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //
                //DO SOME STUFF
                //

                if(event.getAction() == KeyEvent.ACTION_DOWN && (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    // сохраняем текст, введенный до нажатия Enter в переменную
                    String userTranslate = editTextAnswer.getText().toString();

                    // Проверка слова
                    if (nowStudy.checkWord(userTranslate, MarkExercise.WRITING))
                    {
                        textViewWord.setText("Yes");

                        // Ставим флагу "верный ответ"
                        answer = true;

                        // Обновляем базу
                        learningObject.setWord(nowStudy.getId(),1, mDb, MarkExercise.WRITING);

                         /*
                           Удаляем слово из набора для тренировок.
                           Делается, для сокращения диапазона генерации.
                         */
                        learningObject.removeWordInList(nowStudy);

                        // Сохраняем слово в дополнительный массив (слово никуда не пропало)
                        copy.add(nowStudy);

                        // Проверяем, если слов нет -> все слова прошли тренировку
                        // Тогда все слова возвращаем из дополнительного списка и открываем вкладу "Выбор тренировки"
                        if (learningObject.getWordList().size()==0)
                        {
                            learningObject.setWordList(copy);
                            finish();
                            Intent openExerciseSelecetTraining = new Intent(ExerciseWritingActivity.this, ExerciseWritingActivity.class);
                            openExerciseSelecetTraining.putExtra("UniqForm","Tranning");
                            openExerciseSelecetTraining.putExtra("ListWord", (Serializable) learningObject.getWordList());
                            startActivity(openExerciseSelecetTraining);
                        }
                    }
                    else
                    {
                        // Если ошибка, слово выводим ошибку.
                        textViewWord.setText(textViewWord.getText() + "\n\nCorrect answer is " + nowStudy.getEnglishWord());
                        /*
                            ЛЕВ добавь поле для отображения правильного ответа
                         */

                        // learningObject.setWord(nowStudy.getId(),0, mDb, MarkExercise.WRITING);
                    }


                    return true;
                }
                else
                    return false;
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Очищаем поле
                textViewWord.setText("");
                editTextAnswer.setText("");
                /*
                    Если пользователь ответил не правильно, тогда:
                    1) Слово сохраняется в доп переменную.
                    2) Удаляется из нашего списка , если оно там не единственное,
                    делается , что бы слово не повторилось сразу же + у меньшение объема рандома
                 */
                Word tempSave = new Word();
                if (answer == false && learningObject.getWordList().size() > 1) {
                    tempSave.setId(nowStudy.getId());
                    tempSave.setEnglishWord(nowStudy.getEnglishWord());
                    tempSave.setRussianWord(nowStudy.getRussianWord());
                    learningObject.removeWordInList(nowStudy);
                }


                // Генерируем новое слово и выводим на экран
                nowStudy = learningObject.getWordForTextView(MarkExercise.WRITING, mDb);
                textViewWord.setText(nowStudy.getRussianWord());


                // Возвращаем слово, на которое был дан не правильный ответ.
                if (answer == false)
                    learningObject.insertWordInList(tempSave);

                // Флаг ставим в обратную позицию.
                answer = false;
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
        }
        init();
    }
}
