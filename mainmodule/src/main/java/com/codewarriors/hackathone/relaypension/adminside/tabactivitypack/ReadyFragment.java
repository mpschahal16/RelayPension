package com.codewarriors.hackathone.relaypension.adminside.tabactivitypack;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.codewarriors.hackathone.relaypension.R;
import com.google.android.gms.plus.PlusOneButton;
import com.google.firebase.database.DatabaseReference;


public class ReadyFragment extends Fragment {

    TextView test;


    Button readytestbt;

    private String constituency;


    public ReadyFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_ready, container, false);

        Intent intent=getActivity().getIntent();
        String contituency=intent.getExtras().getString("constituency");
        test=view.findViewById(R.id.readytv);


        readytestbt=view.findViewById(R.id.readytestbutton);






        readytestbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"ready bt",Toast.LENGTH_LONG).show();
            }
        });

        test.setText(contituency);



        return view;
    }


}
