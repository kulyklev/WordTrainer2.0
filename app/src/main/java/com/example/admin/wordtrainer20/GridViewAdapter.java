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
    private int icons[];
    private String signatureText[];
    private Context context;
    private LayoutInflater inflater;

    public GridViewAdapter(Context context, int icons[], String signatureText[]) {
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
        View gridView = convertView;

        if (convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.grid_view_item, null);
        }

        ImageView icon = (ImageView) gridView.findViewById(R.id.icons);
        TextView text = (TextView) gridView.findViewById(R.id.signature);

        icon.setImageResource(icons[position]);
        text.setText(signatureText[position]);

        return gridView;
    }
}
