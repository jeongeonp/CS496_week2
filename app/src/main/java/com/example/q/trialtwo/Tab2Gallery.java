package com.example.q.trialtwo;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import static com.facebook.FacebookSdk.getApplicationContext;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import org.apache.commons.io.IOUtils;

public class Tab2Gallery extends Fragment {

    //같은 변수를 선언하기(activity에 있던 것들) 참고자료 https://www.youtube.com/watch?v=T_tEWiFGrsI
    ArrayList<File> list;
    GridView gridView;
/*
        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.manu_two, menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId() == R.id.action_two) {
                Toast.makeText(getActivity(), "Clicked on " + item.getTitle(), Toast.LENGTH_SHORT)
                        .show();
            }
            return true;
        }
        */

    public Tab2Gallery() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true); // 메뉴 만들려고 넣음

        View view = inflater.inflate(R.layout.tab2gallery, container, false); // view는 xml 자바 파일을 붙여넣어준 결과
        gridView = (GridView)view.findViewById(R.id.image_grid); //findViewById 이전에 정의한 class를 붙여줘야함 (in fragment), 자바 뷰는 이러한 컴포넌트들을 가짐


        list = imageReader(Environment.getExternalStorageDirectory());

        gridView.setAdapter(new gridAdapter()); //gridView에 어댑터 설정

        Button button = view.findViewById(R.id.button0); //여기 view를 쓰는게 맞나?
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    /* OnItemClickListener 인터페이스는 onItemClick이라는 메소드를 가짐.
                    AdapterView parent: 클릭된 항목의 부모 뷰
                    View view: 클릭한 항목
                    int Position: 클릭된 항목의 위치
                    long id: postion과 동일
                     */

                //이미지의 id를 FullImageActivity 클래스로 보내기
                //intent와 관련한 에러 https://stackoverflow.com/questions/20241857/android-intent-cannot-resolve-constructor
                // https://stackoverflow.com/questions/20768766/gallery-in-a-fragment
                Intent intent = new Intent(getActivity(), FullImageActivity.class);
                intent.putExtra("img", list.get(position).toString());
                Tab2Gallery.this.startActivity(intent);
            }
        });
        return view;

    }
    // fragment에서 기존에 activity에 존재하던 method 붙여넣기

    public class gridAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View convertView = null;

            if (convertView == null){

                convertView = getLayoutInflater().inflate(R.layout.row_layout, viewGroup, false);
                ImageView myImage = (ImageView) convertView.findViewById(R.id.my_image);
                myImage.setImageURI(Uri.parse(list.get(i).toString()));

            }

            return convertView;
        }
    }

    private ArrayList<File> imageReader(File externalStorageDirectory) { // 이미지를 list로 불러오는 함수

        ArrayList<File> b = new ArrayList<>();

        File[] files = externalStorageDirectory.listFiles();

        for (int i = 0; i<files.length;i++) {

            if (files[i].isDirectory()) {

                b.addAll(imageReader(files[i]));
            }else {

                if (files[i].getName().endsWith(".jpg")){

                    b.add(files[i]);
                }
            }
        }

        return b;
    }



}
