package com.example.admin.wordtrainer20.AdapterFolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.admin.wordtrainer20.R;

/**
 * Created by 2andr on 07.10.2017.
 */

public class CustomAdapter extends ArrayAdapter<Model> {
    private static LayoutInflater inflater = null;
    Model[] modelItems = null;
    Context context;
    private View vi;
    private ViewHolder viewHolder;

    public CustomAdapter(Context context, Model[] resource) {
        super(context, R.layout.row, resource);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.modelItems = resource;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        vi = convertView;
        //Populate the Listview
        final int pos = position;
        Model items = modelItems[pos];
        if (convertView == null) {
            vi = inflater.inflate(R.layout.row, null);
            viewHolder = new ViewHolder();
            viewHolder.isStudied = (CheckBox) vi.findViewById(R.id.checkBox1);
            viewHolder.category = (TextView) vi.findViewById(R.id.textView1);
            vi.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.category.setText(items.getCategory());
        if (items.isStudied()) {
            viewHolder.isStudied.setChecked(true);
        } else {
            viewHolder.isStudied.setChecked(false);
        }
        return vi;
    }

    public void setCheckBox(int position) {
        //Update status of checkbox
        Model items = modelItems[position];
        items.setStudied(!items.isStudied());
        notifyDataSetChanged();
    }

    public Boolean getCheckBox(int position) {
        Model items = modelItems[position];
        return items.isStudied();
    }

    public Model[] getAllData() {
        return modelItems;
    }

    public class ViewHolder {
        TextView category;
        CheckBox isStudied;
    }
}