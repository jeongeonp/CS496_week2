package com.example.q.trialtwo;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.LinkedList;

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
                new LoadContactTask().execute();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //if (position==0) {
                    Intent myintent = new Intent(view.getContext(), Tab1Extra.class);
                    startActivity(myintent);
                //}
            }
        });

        return view;
    }

    private class LoadContactTask extends AsyncTask<Void,Void,LinkedList<Tuple<String, String, String>>> {
        //super(view);

        @Override
        protected LinkedList<Tuple<String, String, String>> doInBackground(Void... voids) {
            LinkedList<Tuple<String, String, String>> retval = new LinkedList<>();
            ContentResolver cr = getActivity().getApplicationContext().getContentResolver();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, "UPPER(" +ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + ") ASC");

            if ((cur != null ? cur.getCount() : 0) > 0) {
                while ( cur.moveToNext()) {
                    String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    //String phoneNo = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    //String email = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    //retval.add(new Tuple<>(name, phoneNo, email));
                    String email = "";

                    if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0 && cur.getCount() >0) {
                        Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, "UPPER(" +ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + ") ASC");

                        pCur.moveToNext();
                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.d("checking","phone: " + phoneNo);
                        Cursor cur1 = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, "UPPER(" +ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + ") ASC");
                        if (cur1.moveToNext()) {
                            email = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                            Log.d("checking","email: " + email);

                        }
                        retval.add(new Tuple<>(name, phoneNo, email));
                        cur1.close();

                        if (pCur != null) {
                            pCur.close();
                        }
                    }
                }
            }
            if (cur != null) {
                cur.close();
            }
            return retval;
        }

        @Override
        protected void onPostExecute(LinkedList<Tuple<String, String, String>> result)
        {

            ListView simpleList = (ListView) getView().findViewById(R.id.listView);
            Tab1Adapter arrayAdapter = new Tab1Adapter(getActivity(), R.layout.list_item, result);
            simpleList.setAdapter(arrayAdapter);
        }
    }


}

