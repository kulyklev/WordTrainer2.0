package com.example.admin.wordtrainer20;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by admin on 05.10.2017.
 */

public class ListViewAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private String[] libraryNames;

    ListViewAdapter(Context context, String[] libraryNames) {
        this.context = context;
        this.libraryNames = libraryNames;
    }

    @Override
    public int getCount() {
        return libraryNames.length;
    }

    @Override
    public Object getItem(int position) {
        return libraryNames[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View row = convertView;

        if (row == null) {
            inflater = LayoutInflater.from(context);
            row = inflater.inflate(R.layout.list_item, null);
        }

        final ImageView imageView = (ImageView) row.findViewById(R.id.LibraryImageView);
        final TextView textView = (TextView) row.findViewById(R.id.LibraryNameTextView);
        final Button openExerciseButt = (Button) row.findViewById(R.id.openExerciseButt);

        openExerciseButt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //
                //Do some stuff, when opening list of Exercises
                //
                Toast.makeText(context, "You clicked button to open " + libraryNames[position], Toast.LENGTH_SHORT).show();
                Intent openListOfWordsActivity = new Intent(context, SelectExerciseActivity.class);
                context.startActivity(openListOfWordsActivity);
            }
        });

        imageView.setImageResource(R.drawable.ic_book_black_24dp);
        textView.setText(libraryNames[position]);

        return row;
    }
}