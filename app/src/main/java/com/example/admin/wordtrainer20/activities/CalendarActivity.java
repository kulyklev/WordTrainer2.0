package com.example.admin.wordtrainer20.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.admin.wordtrainer20.GeneralMenu;
import com.example.admin.wordtrainer20.R;

import java.util.ArrayList;

public class CalendarActivity extends GeneralMenu {

    private CalendarView calendar;
    private ListView listOfWords;
    private ArrayList<String> words;
    private ArrayAdapter<String> adapter;

    private void init(){
        words = new ArrayList<>();

        listOfWords = (ListView) findViewById(R.id.ListOfWords);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, words);
        listOfWords.setAdapter(adapter);

        calendar = (CalendarView) findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //
                //WHEN DATE IS SELECTED, DELETE ALL ELEMENTS FROM words, THEN PUSH THERE NEW DATA.
                //DELETE METHOD addItems,
                //BUT MOVE HERE adapter.notifyDataSetChanged();
                //CHANGE LAYOUT FOR listItmes
                //
                Toast.makeText(getApplicationContext(), dayOfMonth + "." + month + "." + year, Toast.LENGTH_SHORT).show();

                addItems(listOfWords, new String( Integer.toString(dayOfMonth) + "." + Integer.toString(month) + "." + Integer.toString(year) ) );
            }
        });
    }

    private void addItems(View v, String data) {

        words.add("Clicked : " + data);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        init();
    }
}
