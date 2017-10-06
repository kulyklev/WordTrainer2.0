package com.example.admin.wordtrainer20;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class SelectExerciseActivity extends GeneralMenu {
    private ImageButton exerciseOne;
    private ImageButton exerciseTwo;
    private ImageButton exerciseThree;
    private ImageButton exerciseFour;

    private void init(){
        exerciseOne = (ImageButton) findViewById(R.id.ExerciseOneImageButton);
        exerciseTwo = (ImageButton) findViewById(R.id.ExerciseTwoImageButton);
        exerciseThree = (ImageButton) findViewById(R.id.ExerciseThreeImageButton);
        exerciseFour = (ImageButton) findViewById(R.id.ExerciseFourImageButton);

       /* List<Word> listWord = new ArrayList<>();
        DatabaseHelper mDBHelper = new DatabaseHelper(this);
        try {
            listWord = mDBHelper.getRandomWords();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        // Загрузить слова



        exerciseOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                //DO SOME STUFF
                //
                Intent openExerciseOneActivity = new Intent(SelectExerciseActivity.this, ExerciseOneActivity.class);
                startActivity(openExerciseOneActivity);
            }
        });

        exerciseTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                //DO SOME STUFF
                //
                Intent openExerciseTwoActivity = new Intent(SelectExerciseActivity.this, ExerciseTwoActivity.class);
                startActivity(openExerciseTwoActivity);
            }
        });

        exerciseThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                //DO SOME STUFF
                //
                Intent openExerciseThreeActivity = new Intent(SelectExerciseActivity.this, ExerciseThreeActivity.class);
                startActivity(openExerciseThreeActivity);

            }
        });

        exerciseFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                //DO SOME STUFF
                //
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_exercise);

        init();
    }

    /*//
    //
    //CODE BELOW MUST BE REFACTERED
    //
    //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_setting:
                Toast.makeText(SelectExerciseActivity.this, "You have tapped on SETTING.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_about_us:
                Toast.makeText(SelectExerciseActivity.this, "You have tapped on ABOUT US.", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
