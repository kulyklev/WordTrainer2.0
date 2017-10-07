package com.example.admin.wordtrainer20;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExerciseThreeActivity extends AppCompatActivity implements View.OnClickListener {
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private Button variantButt_1;
    private Button variantButt_2;
    private Button variantButt_3;
    private Button variantButt_4;
    private Button variantButt_5;
    private final int RANDOM_NUMBER = 5;
    List<Word> ListWord = new ArrayList<>();
    private int correctNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_three);

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

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            ListWord = (List<Word>) extras.getSerializable("ListWord");
        }

        List<Word> listRandom = new ArrayList<>();
        try {
            listRandom = getRandomWords();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Random random = new Random();
        correctNumber = random.nextInt(5);
        listRandom.set(correctNumber,ListWord.get(0));

        /*
        После добавления перехода изменить ListWord.get(0)
        на нужный индекс
        */

        TextView myAwesomeTextView = (TextView)findViewById(R.id.wordTextView);
        myAwesomeTextView.setText(ListWord.get(0).getEnglishWord());

        variantButt_1 = (Button) findViewById(R.id.variantButt_1);
        variantButt_1.setOnClickListener(this);
        variantButt_1.setText(listRandom.get(0).getEnglishWord());

        variantButt_2 = (Button) findViewById(R.id.variantButt_2);
        variantButt_2.setOnClickListener(this);
        variantButt_2.setText(listRandom.get(1).getEnglishWord());

        variantButt_3 = (Button) findViewById(R.id.variantButt_3);
        variantButt_3.setOnClickListener(this);
        variantButt_3.setText(listRandom.get(2).getEnglishWord());

        variantButt_4 = (Button) findViewById(R.id.variantButt_4);
        variantButt_4.setOnClickListener(this);
        variantButt_4.setText(listRandom.get(3).getEnglishWord());

        variantButt_5 = (Button) findViewById(R.id.variantButt_5);
        variantButt_5.setOnClickListener(this);
        variantButt_5.setText(listRandom.get(4).getEnglishWord());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.variantButt_1:
                //
                if (correctNumber == 1)
                {   //После добавления перехода раскоментить и изменить нули на нужные id
                    //int id = getIdByEnglish(ListWord.get(0).getEnglishWord());
                    setWord(0+1,1);
                }
                else
                {   //После добавления перехода раскоментить и изменить нули на нужные id
                    //int id = getIdByEnglish(ListWord.get(0).getEnglishWord());
                    setWord(0+1,0);
                }
                //
                break;

            case R.id.variantButt_2:
                //
                if (correctNumber == 2)
                {   //После добавления перехода раскоментить и изменить нули на нужные id
                    //int id = getIdByEnglish(ListWord.get(0).getEnglishWord());
                    setWord(0+1,1);
                }
                else
                {   //После добавления перехода раскоментить и изменить нули на нужные id
                    //int id = getIdByEnglish(ListWord.get(0).getEnglishWord());
                    setWord(0+1,0);
                }
                //
                break;

            case R.id.variantButt_3:
                //
                if (correctNumber == 3)
                {   //После добавления перехода раскоментить и изменить нули на нужные id
                    //int id = getIdByEnglish(ListWord.get(0).getEnglishWord());
                    setWord(0+1,1);
                }
                else
                {   //После добавления перехода раскоментить и изменить нули на нужные id
                    //int id = getIdByEnglish(ListWord.get(0).getEnglishWord());
                    setWord(0+1,0);
                }
                //
                break;

            case R.id.variantButt_4:
                //
                if (correctNumber == 4)
                {   //После добавления перехода раскоментить и изменить нули на нужные id
                    //int id = getIdByEnglish(ListWord.get(0).getEnglishWord());
                    setWord(0+1,1);
                }
                else
                {   //После добавления перехода раскоментить и изменить нули на нужные id
                    //int id = getIdByEnglish(ListWord.get(0).getEnglishWord());
                    setWord(0+1,0);
                }
                //
                break;

            case R.id.variantButt_5:
                //
                if (correctNumber == 5)
                {   //После добавления перехода раскоментить и изменить нули на нужные id
                    //int id = getIdByEnglish(ListWord.get(0).getEnglishWord());
                    setWord(0+1,1);
                }
                else {//После добавления перехода раскоментить и изменить нули на нужные id
                    //int id = getIdByEnglish(ListWord.get(0).getEnglishWord());
                    setWord(0+1,0);
                }
                break;
            default:
                break;
        }
    }

    public int getIdByEnglish(String english){
        String copyEnglish = english.replaceAll("'", "''");

        Cursor cursor = mDb.rawQuery("SELECT * FROM words WHERE English='"+ copyEnglish + "'", null);
        cursor.moveToFirst();
        int i = cursor.getInt(cursor.getColumnIndex("_id"));
        cursor.close();
        return i;
    }

    public void setWord(long id, long val){
        Cursor cursor = mDb.rawQuery("UPDATE trainings" +
                " SET Choice='" + val +"'"+" WHERE _id='" + id + "'",null);
        cursor.moveToFirst();
        cursor.close();
    }

    public List<Word> getRandomWords() throws IOException
    {
        List<Word> result = new ArrayList<>();
        String query = "SELECT * FROM words";
        Cursor cursor = mDb.rawQuery(query, null);

        int random = (int)(Math.random()*30)+21;
        int i=0;

        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            do {
                if (i%random==0) {
                    Word word = new Word();
                    word.setEnglishWord(cursor.getString(cursor.getColumnIndex("English")));
                    word.setRussianWord(cursor.getString(cursor.getColumnIndex("Russian")));
                    //word.setCheck(false);
                    result.add(word);
                }
                i++;
            } while (cursor.moveToNext() && result.size()< RANDOM_NUMBER);
        }
        cursor.close();
        return result;
    }
}
