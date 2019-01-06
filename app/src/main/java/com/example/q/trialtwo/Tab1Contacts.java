package com.example.q.trialtwo;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Tab1Contacts extends Fragment {

    ListView listView;
    Button loadButton;
    static final String[] from = {"name", "mobile", "email", "id"};
    Tab1Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1contacts, container, false);

        listView = (ListView) view.findViewById(R.id.listView);

        loadButton = (Button) view.findViewById(R.id.loadButton);

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, "UPPER(" +ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + ") ASC");



                getActivity().startManagingCursor(cursor);

                String[] from = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Email.DATA, ContactsContract.CommonDataKinds.Phone._ID};

                int[] to = {android.R.id.text1, android.R.id.text2};

                //adapter = new Tab1Adapter(getActivity(),from);
                //listView.setAdapter(adapter);

                SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), android.R.layout.simple_expandable_list_item_2, cursor, from, to);
                listView.setAdapter(simpleCursorAdapter);
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            }
        });

        return view;
    }




}
