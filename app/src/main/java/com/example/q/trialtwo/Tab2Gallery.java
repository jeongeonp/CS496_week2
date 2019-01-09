package com.example.q.trialtwo;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;

import static com.facebook.FacebookSdk.getApplicationContext;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

public class Tab2Gallery extends Fragment {

    GridView gallery;

    public Tab2Gallery() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.tab2gallery, container, false);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                View view = getView();
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                gallery = (GridView) view.findViewById(R.id.gallery);;
                gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        GalleryList.GridViewAdapter gridViewAdapter = (GalleryList.GridViewAdapter)
                                adapterView.getAdapter();
                        Uri uri = (Uri) gridViewAdapter.getItem(i);
                        Intent intent = new Intent(getActivity(), Photo.class);
                        intent.putExtra("uri", uri.toString());
                        intent.putExtra("fileOrder",Integer.toString(i));
                        //Toast.makeText(getApplicationContext(),"this is " + i, Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                });

                Activity activity = getActivity();

                if (activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (activity.shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        Toast.makeText(getContext(), "Read gallery", Toast.LENGTH_SHORT).show();
                    }
                    requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                            Constants.PERM_READ_EXTERNAL_STORAGE);
                } else {
                    setGallery();
                }
            }
        });



        return view;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.PERM_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setGallery();
                } else {
                    Toast.makeText(getContext(), "No permissions to read contacts!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void setGallery() {
        int min = Math.min(gallery.getWidth(), gallery.getHeight());
        int numColumns = min/300;
        gallery.setNumColumns(numColumns);
        gallery.setAdapter(new GalleryList(getContext(), gallery.getWidth(), gallery.getHeight(), numColumns).getGallaryList());
    }
}