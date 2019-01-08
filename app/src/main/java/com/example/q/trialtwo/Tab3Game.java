package com.example.q.trialtwo;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Tab3Game extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab3game, container, false);

        Button newPage = (Button)view.findViewById(R.id.button1);
        newPage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FbLogin.class);
                startActivity(intent);
            }
        });
        return view;

    }
/*
  public void buttonClick(View v) {
        Intent intent = new Intent(getActivity(), FbLogin.class);
        startActivity(intent);
    }*/

}