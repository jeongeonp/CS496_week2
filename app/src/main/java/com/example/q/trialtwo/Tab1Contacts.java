package com.example.q.trialtwo;

import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.LinkedList;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.android.volley.AuthFailureError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Tab1Contacts extends Fragment {
    ListView listView;
    Button loadButton;
    static final String[] from = {"name", "mobile", "email", "id"};
    Tab1Adapter adapter;
    LinkedList<Tuple<String, String, String>> retval;

    // 서버 관련
    private static String TAG = "MainActivity";

    private static final String TAG_JSON = "webnautes";
    private static final String TAG_ID = "number";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADDRESS = "email";
    String mJsonString;
    ListView mlistView;
    LinkedList<Tuple<String, String, String>> data = new LinkedList<Tuple<String, String, String>>();
    JSONArray mPhoneData;
    String smPhoneData;
    JSONObject omPhoneData;
    LinkedList<Tuple<String, String, String>> phoneData;


    //서버2
    private static String IP_ADDRESS = "http://socrip4.kaist.ac.kr:2680/api/books";
    int idx;




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

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //if (position==0) {
                Intent myintent = new Intent(view.getContext(), Tab1Extra.class);
                startActivity(myintent);
                //}
            }
        });*/

        Button bring = (Button) view.findViewById(R.id.bring);

        bring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"click works222", Toast.LENGTH_SHORT).show();
                mlistView = (ListView) getView().findViewById(R.id.listView);

                GetData task = new GetData();
                task.execute("http://socrip4.kaist.ac.kr:2680/api/books");

            }
        });


        Button push = (Button) view.findViewById(R.id.push);


        phoneData = new LoadContactTask().doInBackground();
        //Toast.makeText(getContext(), phoneData.size(), Toast.LENGTH_SHORT).show();
        //mPhoneData = resultToJson(phoneData);
        //smPhoneData = mPhoneData.toString();

        //try printing




        push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (idx=0; idx<phoneData.size(); idx++) {

                    String url = "http://socrip4.kaist.ac.kr:2680/api/books";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                            //Intent view = new Intent(getActivity(), SearchFile.class);
//                        view.putExtra("name", );
//                        view.putExtra("fileText", );
                            //startActivity(view);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            //progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "error: " + volleyError.toString(), Toast.LENGTH_LONG).show();
                        }
                    }){ // adding parameter to send
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();

                            //parameters.put("name", text);

                        /*try {
                            parameters.put("number", omPhoneData.getString("number"));
                            //Toast.makeText(getApplicationContext(), omPhoneData.getString("number"), Toast.LENGTH_LONG).show();
                            parameters.put("name", omPhoneData.getString("name"));
                            parameters.put("email", omPhoneData.getString("email"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/
                            parameters.put("name", phoneData.get(idx).first);
                            parameters.put("number", phoneData.get(idx).second);
                            parameters.put("email", phoneData.get(idx).third);


                            //progressDialog.dismiss();
                            return parameters;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    requestQueue.add(stringRequest);
                }
                Toast.makeText(getActivity(), "click works", Toast.LENGTH_SHORT).show();


            }



        });

        return view;
    }

//Start of insertData (서버 2)

    private class SendDeviceDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String data = "";

            HttpURLConnection httpURLConnection = null;
            try {

                httpURLConnection = (HttpURLConnection) new URL(params[0]).openConnection();
                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoOutput(true);

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes("PostData=" + params[1]);
                wr.flush();
                wr.close();

                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);

                int inputStreamData = inputStreamReader.read();
                while (inputStreamData != -1) {
                    char current = (char) inputStreamData;
                    inputStreamData = inputStreamReader.read();
                    data += current;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("TAG", result); // this is expecting a response code to be sent from your server upon receiving the POST data
        }
    }




//Complicated LinkedList to better organized JSONArray
//    private static JSONArray resultToJson(LinkedList<Tuple<String, String, String>> result) {
//        Tuple<String, String, String> cur = new Tuple<String, String, String>("","","");
//        cur = result.getFirst();
//
//        Map<String, String> hashMap = new HashMap<String, String>();
//        JSONArray jsonArray = new JSONArray();
//        for (int i=0; i<result.size(); i++) {
//            try{
//                hashMap.put("name", cur.first);
//                hashMap.put("number", cur.second);
//                hashMap.put("email", cur.third);
//
//                jsonArray.getJSONObject(i).put("name", cur.first);
//                jsonArray.getJSONObject(i).put("number", cur.second);
//                jsonArray.getJSONObject(i).put("email", cur.third);
//                Log.d("each item", "each item has " + cur.first + " phone number: " + cur.second + " and email address: " + cur.third);
//
//                cur=result.get(i);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//        }
//        return jsonArray;


 //   }




    //로드 해오는법 (핸드폰에서)
    private class LoadContactTask extends AsyncTask<Void, Void, LinkedList<Tuple<String, String, String>>> {
        //super(view);

        @Override
        protected LinkedList<Tuple<String, String, String>> doInBackground(Void... voids) {
            //LinkedList<Tuple<String, String, String>> retval = new LinkedList<>();
            retval = new LinkedList<>();
            ContentResolver cr = getActivity().getApplicationContext().getContentResolver();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, "UPPER(" + ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + ") ASC");

            if ((cur != null ? cur.getCount() : 0) > 0) {
                while (cur.moveToNext()) {
                    String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    //String phoneNo = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    //String email = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    //retval.add(new Tuple<>(name, phoneNo, email));
                    String email = "";

                    if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0 && cur.getCount() > 0) {
                        Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, "UPPER(" + ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + ") ASC");

                        pCur.moveToNext();
                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.d("checking", "phone: " + phoneNo);
                        Cursor cur1 = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, "UPPER(" + ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + ") ASC");
                        if (cur1.moveToNext()) {
                            email = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                            Log.d("checking", "email: " + email);

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

        protected void onPostExecute(LinkedList<Tuple<String, String, String>> result) {

            ListView simpleList = (ListView) getView().findViewById(R.id.listView);
            Tab1Adapter arrayAdapter = new Tab1Adapter(getActivity(), R.layout.list_item, result);
            simpleList.setAdapter(arrayAdapter);
        }
    }


//데이타 가져오기 (서버1)
    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(getActivity(),
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

            Log.d(TAG, "response  - " + result);

            if (result == null) {
            } else {
                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();

            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }

    private void showResult() {
        try {
            JSONArray jsonArray = new JSONArray(mJsonString);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                String id = item.getString(TAG_ID);
                String name = item.getString(TAG_NAME);
                String address = item.getString(TAG_ADDRESS);
                Log.d("each item", "each item has " + name + " phone number: " + id + " and email address: " );

                Tuple<String, String, String> idd = new Tuple<>(name, id, address);

                data.add(idd);
            }

            Tab1Adapter dataAdapter = new Tab1Adapter(getActivity(), R.layout.list_item, data);
            mlistView.setAdapter(dataAdapter);

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }



}



