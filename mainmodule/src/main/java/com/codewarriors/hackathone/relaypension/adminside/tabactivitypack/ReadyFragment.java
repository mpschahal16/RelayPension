package com.codewarriors.hackathone.relaypension.adminside.tabactivitypack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.codewarriors.hackathone.relaypension.R;
import com.codewarriors.hackathone.relaypension.customvariablesforparsing.FormPushPullCustomVAR;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class ReadyFragment extends Fragment {


    DatabaseReference rootreference= FirebaseDatabase.getInstance().getReference();
    private String constituency;

    ListView readylistview;

    FormPushPullCustomVAR formPushPullCustomVAR;
    ApplicationFormListAdapter applicationFormListAdapter;


    ArrayList<ApplicationFormListVAR> listtodisplay;
    ArrayList<FormPushPullCustomVAR> allformslistinready;

    public ReadyFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_ready, container, false);

        readylistview=view.findViewById(R.id.readyformlistv);

        Intent intent=getActivity().getIntent();
        constituency=intent.getExtras().getString("constituency",null);

        listtodisplay=new ArrayList<>();
        allformslistinready=new ArrayList<>();

        if(constituency!=null)
        {

            DatabaseReference referencetoready=rootreference.child("consituency/"+constituency+"/");
            referencetoready.child("ready").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                    {
                        listtodisplay.clear();
                        allformslistinready.clear();
                        for(DataSnapshot dataSnapshotchild:dataSnapshot.getChildren()) {
                            formPushPullCustomVAR=dataSnapshotchild.getValue(FormPushPullCustomVAR.class);
                            allformslistinready.add(formPushPullCustomVAR);
                            ApplicationFormListVAR applicationFormListVAR=new ApplicationFormListVAR(formPushPullCustomVAR.getFirstName()+" "+formPushPullCustomVAR.getMiddleName()
                            +" "+formPushPullCustomVAR.getLastName(),formPushPullCustomVAR.getAge(),
                                    formPushPullCustomVAR.getConstituency(),formPushPullCustomVAR.getFormno());


                           listtodisplay.add(applicationFormListVAR);
                        }
                            Collections.sort(listtodisplay, new Comparator<ApplicationFormListVAR>() {
                                @Override
                                public int compare(ApplicationFormListVAR applicationFormListVAR, ApplicationFormListVAR t1) {
                                    return applicationFormListVAR.getFno().compareToIgnoreCase(t1.getFno());
                                }
                            });
                        applicationFormListAdapter=new ApplicationFormListAdapter(getContext(),listtodisplay);
                        readylistview.setAdapter(applicationFormListAdapter);
                        applicationFormListAdapter.notifyDataSetChanged();
                        readylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                DialogForReadyForm dialogForReadyForm =new DialogForReadyForm(getActivity(),allformslistinready.get(i));
                                dialogForReadyForm.show();

                            }
                        });
                    }
                    else
                    {
                        Log.d("data","Ready is empty");
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                    Toast.makeText(getContext(),"Cancel",Toast.LENGTH_LONG).show();
                }
            });


        }

        else
        {
           Toast.makeText(getContext(),"Error",Toast.LENGTH_LONG).show();
        }













        return view;
    }


}
