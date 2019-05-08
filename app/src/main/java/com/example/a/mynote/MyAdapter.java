package com.example.a.mynote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MyAdapter extends SimpleAdapter {
    public MyAdapter(Context context,
                     List<? extends Map<String, ?>> data, int resource,
                     String[] from, int[] to) {
        super(context, data, resource, from, to);
    }
    public View getView(int position,View contentView,ViewGroup parent){

        View row = super.getView(position, contentView, parent);
        TextView payorsave = (TextView)row.findViewById(R.id.notename);
        payorsave.setTextColor(Color.parseColor("#ffff0000"));
        return row;
    }
}
