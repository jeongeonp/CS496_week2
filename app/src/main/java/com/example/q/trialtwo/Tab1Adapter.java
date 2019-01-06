package com.example.q.trialtwo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class Tab1Adapter extends BaseAdapter
{
    private LayoutInflater inflater;
    private LinkedList<Tuple<String, String, String>> data;
    private int layout;

    public Tab1Adapter(Context ctx, int layout_init, LinkedList<Tuple<String, String, String>> data_init) {
        if(data_init == null)
        {
            data = new LinkedList<>();
        }
        else
        {
            data = data_init;
        }
        layout = layout_init;
        inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = inflater.inflate(layout, viewGroup, false);
        }

        Tuple<String, String, String> d = data.get(i);

        TextView name = view.findViewById(R.id.name);
        name.setText(d.first);
        TextView contact = view.findViewById(R.id.mobile);
        contact.setText(d.second);
        TextView email = view.findViewById(R.id.email);
        email.setText(d.third);


        return view;
    }

}

class Tuple<X, Y, Z> {
    final X first;
    final Y second;
    final Z third;
    Tuple(X x, Y y, Z z)
    {
        first = x;
        second = y;
        third = z;
    }

}