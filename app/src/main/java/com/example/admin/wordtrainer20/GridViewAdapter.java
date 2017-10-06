package com.example.admin.wordtrainer20;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 21.09.2017.
 */

public class GridViewAdapter extends BaseAdapter {
    private List<Bitmap> icons;
    private String signatureText[];
    private Context context;
    private LayoutInflater inflater;


    public GridViewAdapter(Context context, List<byte[]> icons, String signatureText[]) {
        this.context = context;
        this.signatureText = signatureText;

        this.icons = new ArrayList<>();
        for (byte[] img:
             icons) {
            this.icons.add( BitmapFactory.decodeByteArray(img, 0, img.length) );
        }
    }

    private static class ViewHolder {
        ImageView icon;
        TextView text;
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
        ViewHolder viewHolder;

        if (convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.grid_view_item, null);

            viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.icons);
            viewHolder.text = (TextView) convertView.findViewById(R.id.signature);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.text.setText(signatureText[position]);
        viewHolder.icon.setImageBitmap(icons.get(position));

        return convertView;
    }
}
