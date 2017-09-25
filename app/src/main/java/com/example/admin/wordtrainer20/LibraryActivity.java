package com.example.admin.wordtrainer20;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class LibraryActivity extends GeneralMenu {

    private GridView gridView;
    private String signatureText[] = {"alarm", "android", "mobile", "profile_icon", "web", "wordpress", "7", "8"};
    private int icons[] = {
            R.drawable.sample_0,
            R.drawable.sample_1,
            R.drawable.sample_2,
            R.drawable.sample_3,
            R.drawable.sample_4,
            R.drawable.sample_5,
            R.drawable.sample_6,
            R.drawable.sample_7,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        gridView = (GridView) findViewById(R.id.gridView);

        GridViewAdapter adapter = new GridViewAdapter(LibraryActivity.this, icons, signatureText);

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //
                //ADD LIBRARY TO USER`S LIST
                //
                Toast.makeText(LibraryActivity.this, "You tapped: " + signatureText[position], Toast.LENGTH_SHORT).show();//DELETE THIS
            }
        });
    }
}
