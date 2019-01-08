package com.example.q.trialtwo;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Photo extends AppCompatActivity {

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */

    TextView tvData;
    Bitmap bitmap, bitmap2, decodedImage;
    ImageView iv;
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_main);

        setContentView(R.layout.activity_photo);




        tvData = (TextView)findViewById(R.id.textView);

        Button btn = (Button)findViewById(R.id.httpTest);
        Button btn2 = (Button)findViewById(R.id.httpTest2);
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        iv = (ImageView) findViewById(R.id.iv);




        //버튼이 클릭되면 여기 리스너로 옴

        btn.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                new JSONTask().execute("http://socrip4.kaist.ac.kr:2680/api/images");//AsyncTask 시작시킴

            }

        });
        btn2.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                //new JSONTask2().execute("http://socrip4.kaist.ac.kr:2680/api/images");//AsyncTask 시작시킴

                Intent intent2 = getIntent();

                String order = intent2.getStringExtra("fileOrder");
                Toast.makeText(getApplicationContext(),"this is " + order, Toast.LENGTH_SHORT).show();
                if(order.equals("0")) {iv.setImageResource(R.drawable.pic1);}
                else if (order.equals("1")) {iv.setImageResource(R.drawable.pic2);}
                else {iv.setImageResource(R.drawable.pic3);}

            }

        });

    }




    public class JSONTask extends AsyncTask<String, String, String> {

        @Override

        protected String doInBackground(String... urls) {
            Intent intent = getIntent();
            String path = intent.getStringExtra("uri");
            final Uri uri = Uri.parse(path);
            Log.d("ddddddd", uri.toString());

            try {
                Log.d("aaaaaa","aaaaaaa");
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                bitmap = BitmapFactory.decodeFile(path);
                bitmap2= Bitmap.createScaledBitmap(bitmap,200,200, true);
                Log.d("test",bitmap.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream stream0 = new ByteArrayOutputStream();
            bitmap2.compress(Bitmap.CompressFormat.JPEG,100,stream0);
            byte[] bytes = stream0.toByteArray();
            String s = Base64.encodeToString(bytes,Base64.DEFAULT);
            //Toast.makeText(getApplicationContext(), "Upload Successful", Toast.LENGTH_SHORT);

            try {

                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.

                org.json.JSONObject jsonObject = new org.json.JSONObject();
                jsonObject.put("Image", s);

                HttpURLConnection con = null;
                BufferedReader reader = null;


                try{
                    //URL url = new URL("http://192.168.25.16:3000/users");

                    URL url = new URL("http://socrip4.kaist.ac.kr:2680/api/images");

                    //연결을 함

                    con = (HttpURLConnection) url.openConnection();




                    con.setRequestMethod("POST");//POST방식으로 보냄

                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정

                    con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송

                    con.setRequestProperty("Accept", "application/json");//서버에 response 데이터를 html로 받음

                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미

                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미

                    con.connect();
                    //서버로 보내기위해서 스트림 만듬

                    OutputStream outStream = con.getOutputStream();

                    //버퍼를 생성하고 넣음

                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));

                    writer.write(jsonObject.toString());

                    writer.flush();

                    writer.close();//버퍼를 받아줌




                    //서버로 부터 데이터를 받음

                    InputStream stream = con.getInputStream();




                    reader = new BufferedReader(new InputStreamReader(stream));




                    StringBuffer buffer = new StringBuffer();




                    String line = "";

                    while((line = reader.readLine()) != null){

                        buffer.append(line);

                    }
                    stream.close();

                    Log.d("agaldkfalfkjadlkf",buffer.toString());


                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임




                } catch (MalformedURLException e){

                    e.printStackTrace();

                } catch (IOException e) {

                    e.printStackTrace();

                } finally {

                    if(con != null){

                        con.disconnect();

                    }

                    try {

                        if(reader != null){

                            reader.close();//버퍼를 닫아줌

                        }

                    } catch (IOException e) {

                        e.printStackTrace();

                    }

                }

            } catch (Exception e) {

                e.printStackTrace();

            }




            return null;

        }




        @Override

        protected void onPostExecute(String result) {

            //Log.d("11111",result.toString());
            super.onPostExecute(result);
            Toast.makeText(getApplicationContext(), "Upload Successful", Toast.LENGTH_SHORT).show();
            tvData.setText(result);//서버로 부터 받은 값을 출력해주는 부


        }

    }

    public class JSONTask2 extends AsyncTask<String, String, String> {




        @Override

        protected String doInBackground(String... urls) {
            Intent intent = getIntent();
            String path = intent.getStringExtra("uri");
            Uri uri = Uri.parse(path);

            try {
                //bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                bitmap = BitmapFactory.decodeFile(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream stream0 = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream0);
            byte[] bytes = stream0.toByteArray();
            String s = Base64.encodeToString(bytes,Base64.DEFAULT);

            try {

                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.

                org.json.JSONObject jsonObject = new org.json.JSONObject();
                jsonObject.put("Image", s);

                HttpURLConnection con = null;
                BufferedReader reader = null;


                try{
                    //URL url = new URL("http://192.168.25.16:3000/users");

                    URL url = new URL("http://13.125.24.15:9000/api/bookDownload");

                    //연결을 함

                    con = (HttpURLConnection) url.openConnection();




                    con.setRequestMethod("POST");//POST방식으로 보냄

                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정

                    con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송

                    con.setRequestProperty("Accept", "application/json");//서버에 response 데이터를 html로 받음

                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미

                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미

                    con.connect();
                    //서버로 보내기위해서 스트림 만듬

                    OutputStream outStream = con.getOutputStream();

                    //버퍼를 생성하고 넣음

                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));

                    writer.write(jsonObject.toString());

                    writer.flush();

                    writer.close();//버퍼를 받아줌

                    InputStream stream = con.getInputStream();




                    reader = new BufferedReader(new InputStreamReader(stream));




                    StringBuffer buffer = new StringBuffer();




                    String line = "";

                    while((line = reader.readLine()) != null){

                        buffer.append(line);

                    }



                    Log.d("aaaaaaaaaaaaaaaaaa", buffer.toString());
                    JSONObject object = new JSONObject(buffer.toString());
                    byte[] imageBytes = Base64.decode(object.getString("author"),Base64.DEFAULT);
                    Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);

                    stream.close();

                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임




                } catch (MalformedURLException e){

                    e.printStackTrace();

                } catch (IOException e) {

                    e.printStackTrace();

                } finally {

                    if(con != null){

                        con.disconnect();

                    }

                    try {

                        if(reader != null){

                            reader.close();//버퍼를 닫아줌

                        }

                    } catch (IOException e) {

                        e.printStackTrace();

                    }

                }

            } catch (Exception e) {

                e.printStackTrace();

            }
            return null;

        }




        @Override

        protected void onPostExecute(String result) {

            super.onPostExecute(result);
//            Log.d("aaaaaaa",result);
            Toast.makeText(getApplicationContext(), "Download Successful", Toast.LENGTH_SHORT).show();
            Intent intent = getIntent();
            String path = intent.getStringExtra("uri");
            String order = intent.getStringExtra("fileOrder");
            Toast.makeText(getApplicationContext(),"this is " + order, Toast.LENGTH_SHORT).show();
            //File imgFile = new File("R.drawable.pic" + order);
            Uri uri = Uri.parse(path);

            if(order.equals("0")) {iv.setImageResource(R.drawable.pic1);}
            else if (order.equals("1")) {iv.setImageResource(R.drawable.pic2);}
            else {iv.setImageResource(R.drawable.pic3);}

            //iv.setImageBitmap(decodedImage);

            tvData.setText(result);//서버로 부터 받은 값을 출력해주는 부

        }

    }


}


