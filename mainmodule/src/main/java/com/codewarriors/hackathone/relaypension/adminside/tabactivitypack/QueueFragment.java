package com.codewarriors.hackathone.relaypension.adminside.tabactivitypack;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codewarriors.hackathone.relaypension.R;
import com.google.android.gms.plus.PlusOneButton;


public class QueueFragment extends Fragment {




    public QueueFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_queue, container, false);



        return view;
    }



}
