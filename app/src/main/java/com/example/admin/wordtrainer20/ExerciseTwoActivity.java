package com.example.admin.wordtrainer20;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ExerciseTwoActivity extends GeneralMenu {
    private EditText answer;
    private TextView textShow;
    List<Word> ListWord = new ArrayList<Word>();
    Word nowStudy = new Word();

    private void init(){
        answer = (EditText) findViewById(R.id.textAnswer);
        textShow = (TextView) findViewById(R.id.textViewShow);


        ListWord.add(new Word("text1","text1"));
        ListWord.add(new Word("text2","text2"));
        ListWord.add(new Word("text3","text3"));
        ListWord.add(new Word("text4","text4"));
        ListWord.add(new Word("text5","text5"));
        ListWord.add(new Word("text6","text6"));
        ListWord.add(new Word("text7","text7"));
        ListWord.add(new Word("text8","text8"));



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
                if((event.getAction() == KeyEvent.ACTION_DOWN)&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // сохраняем текст, введенный до нажатия Enter в переменную
                    String userTranslate = answer.getText().toString();
                    if (nowStudy.checkWorld(userTranslate, MarkExercise.WRITING))
                    {
                        textShow.setText("Yes");
                        // Update nowStudy in table Trainings in dataBase (name training in enum)
                        // Show button Next;
                        // Button click on Next -> repeat step1 and hide itself and show button Check
                        // Also clear textBox

                    }
                    else
                    {
                        textShow.setText("No");
                        // Show right answer in label (setText (nowStudy.getEnglishWord()))
                        // Show button Next;
                        // Click on Next repeat step1 and hide itself and show button Check
                        // Also clear textBox
                    }
                    return true;
                }
                else
                    return false;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_two);
        init();
    }
}
