package com.example.admin.wordtrainer20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class ListOfWordsActivity extends GeneralMenu {
    private String[] words;
    private ListView wordsLV;

    private void init() {
        words = new String[] {"word1","word2","word3","word4","word5","word6","word7","word8","word9","word10"};


        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, words);

        wordsLV = (ListView) findViewById(R.id.ListOfWordsLV);
        wordsLV.setChoiceMode(wordsLV.CHOICE_MODE_MULTIPLE);
        wordsLV.setAdapter(itemsAdapter);
        wordsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                CheckedTextView item = (CheckedTextView) view;
                Toast.makeText(ListOfWordsActivity.this, words[position] + " checked : " + item.isChecked(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_words);

        init();
    }
}
