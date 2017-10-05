package com.example.admin.wordtrainer20;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListOfWordsActivity extends AppCompatActivity {
    private String[] words;
    private ListView listOfWordsLV;

    private void init() {
        listOfWordsLV = (ListView) findViewById(R.id.listOfWords);
        listOfWordsLV.setAdapter(new ArrayAdapter<String>(
                this, R.layout.list_item,
                R.id.LibraryNameTextView, data
        ));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                //
                //DO SOME STUFF ON ITEM CLICK
                //
                //Pass some data
                Intent openExerciseOneActivity = new Intent(MainActivity.this, SelectExerciseActivity.class);
                startActivity(openExerciseOneActivity);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_words);
    }
}
