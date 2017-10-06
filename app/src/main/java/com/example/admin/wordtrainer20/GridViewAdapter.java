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

import java.util.List;

/**
 * Created by admin on 21.09.2017.
 */

public class GridViewAdapter extends BaseAdapter {
    private List<byte[]> icons;
    private String signatureText[];
    private Context context;
    private LayoutInflater inflater;

    public GridViewAdapter(Context context, List<byte[]> icons, String signatureText[]) {
        this.context = context;
        this.icons = icons;
        this.signatureText = signatureText;
    }

    static class ViewHolder {
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

        Bitmap bmp= BitmapFactory.decodeByteArray( icons.get(position), 0, icons.get(position).length );
        viewHolder.text.setText(signatureText[position]);
        viewHolder.icon.setImageBitmap(bmp);

        return convertView;
    }
}
