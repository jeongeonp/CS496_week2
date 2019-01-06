package com.example.q.trialtwo;

import java.util.ArrayList;
import java.util.HashMap;

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

public class Tab1Adapter extends CursorAdapter {
    private LayoutInflater cursorInflater;

    // Default constructor
    public Tab1Adapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
        cursorInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView mobile = (TextView) view.findViewById(R.id.mobile);
        TextView email = (TextView) view.findViewById(R.id.email);
        //String s_name = cursor.getString( cursor.getColumnIndex( MyTable.COLUMN_TITLE ) );
        //String s_mobile = cursor.getString( cursor.getColumnIndex( MyTable.COLUMN_TITLE ) );
        //String s_email = cursor.getString( cursor.getColumnIndex( MyTable.COLUMN_TITLE ) );
        //name.setText(s_name);
        //name.setText(s_mobile);
        //name.setText(s_email);
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // R.layout.list_row is your xml layout for each row
        return cursorInflater.inflate(R.layout.list_item, parent, false);
    }
}