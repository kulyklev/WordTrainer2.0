package com.example.admin.wordtrainer20;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by admin on 21.09.2017.
 */

public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private int icons/*[]*/;
    private String signatureText[];
    private LayoutInflater inflater;

    public GridViewAdapter(Context context, int icons/*[]*/, String signatureText[]) {
        this.context = context;
        this.icons = icons;
        this.signatureText = signatureText;
    }

    @Override
    public int getCount() {
        return signatureText.length;
    }

    @Override
    public Object getItem(int position) {
        return signatureText[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         final String sigText = signatureText[position];

        if (convertView == null) {
            final LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.grid_view_item, null);
        }

        final ImageView imageView = (ImageView) convertView.findViewById(R.id.icons);
        final TextView textView = (TextView) convertView.findViewById(R.id.signature);

        imageView.setImageResource(R.drawable.book);
        textView.setText(signatureText[position]);


        return convertView;
    }
}
