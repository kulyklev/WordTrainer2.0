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

import java.util.List;

/**
 * Created by admin on 05.10.2017.
 */

public class ListViewAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<String> libraryNames;

    ListViewAdapter(Context context, List<String> libraryNames) {
        this.context = context;
        this.libraryNames = libraryNames;
    }

    private static class ViewHolder {
        ImageView image;
        TextView text;
        Button openExerciseButt;
    }

    @Override
    public int getCount() {
        return libraryNames.size();
    }

    @Override
    public Object getItem(int position) {
        return libraryNames.get(position);
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
            viewHolder.openExerciseButt = (Button) convertView.findViewById(R.id.openExerciseButt);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.openExerciseButt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent openListOfWordsActivity = new Intent(context, SelectExerciseActivity.class);
                context.startActivity(openListOfWordsActivity);
            }
        });

        viewHolder.image.setImageResource(R.drawable.ic_book_black_24dp);
        viewHolder.text.setText(libraryNames.get(position));

        return convertView;
    }
}