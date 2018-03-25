package com.codewarriors.hackathone.relaypension.adminside.tabactivitypack;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import es.dmoral.toasty.Toasty;


public class QueueFragment extends Fragment {

    DatabaseReference rootreference= FirebaseDatabase.getInstance().getReference();
    private String constituency;

    ListView queuelistview;


    FormPushPullCustomVAR formPushPullCustomVAR;
    ApplicationFormListAdapter applicationFormListAdapter;


    ArrayList<ApplicationFormListVAR> listtodisplay;
    ArrayList<FormPushPullCustomVAR> allformslistinqueue;

    public QueueFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_queue, container, false);
        queuelistview=view.findViewById(R.id.queuefromlv);

        Intent intent=getActivity().getIntent();
        constituency=intent.getExtras().getString("constituency");

        listtodisplay=new ArrayList<>();
        allformslistinqueue=new ArrayList<>();

        if(constituency!=null)
        {
            DatabaseReference referencetoready=rootreference.child("consituency/"+constituency+"/");
            referencetoready.child("queue").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    listtodisplay.clear();
                    allformslistinqueue.clear();
                    if(dataSnapshot.exists())
                    {

                        for(DataSnapshot dataSnapshotchild:dataSnapshot.getChildren()) {
                            formPushPullCustomVAR=dataSnapshotchild.getValue(FormPushPullCustomVAR.class);
                            allformslistinqueue.add(formPushPullCustomVAR);
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
                        Collections.sort(allformslistinqueue, new Comparator<FormPushPullCustomVAR>() {
                            @Override
                            public int compare(FormPushPullCustomVAR o1, FormPushPullCustomVAR o2) {
                                return o1.getFormno().compareToIgnoreCase(o2.getFormno());
                            }
                        });


                            queuelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    DialogForQueueForm dialogForQueueForm =new DialogForQueueForm(getActivity(),allformslistinqueue.get(i));
                                    dialogForQueueForm.show();
                                }
                            });
                    }
                    else
                    {
                        Log.d("data","queue is empty");
                    }
                    applicationFormListAdapter=new ApplicationFormListAdapter(getContext(),listtodisplay);
                    queuelistview.setAdapter(applicationFormListAdapter);
                    applicationFormListAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                    Log.d("firebaseeeeee", databaseError.getMessage());
                }
            });


        }

        else
        {
            Toasty.error(getContext(),"Error in Queue",Toast.LENGTH_LONG,true).show();
        }


        return view;
    }



}
