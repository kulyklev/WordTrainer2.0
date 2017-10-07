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

    private static class ViewHolder {
        ImageView image;
        TextView text;
        Button openExersiceButt;
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
        ViewHolder viewHolder;

        if (convertView == null) {
            inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item, null);

            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.LibraryImageView);
            viewHolder.text = (TextView) convertView.findViewById(R.id.LibraryNameTextView);
            viewHolder.openExersiceButt = (Button) convertView.findViewById(R.id.openExerciseButt);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.openExersiceButt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //
                //Do some stuff, when opening list of Exercises
                //
                //Toast.makeText(context, "You clicked button to open " + libraryNames[position], Toast.LENGTH_SHORT).show();
                Intent openListOfWordsActivity = new Intent(context, SelectExerciseActivity.class);
                context.startActivity(openListOfWordsActivity);
            }
        });

        viewHolder.image.setImageResource(R.drawable.ic_book_black_24dp);
        viewHolder.text.setText(libraryNames[position]);

        return convertView;
    }
}