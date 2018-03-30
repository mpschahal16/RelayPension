package com.codewarriors.hackathone.relaypension.adminside.tabactivitypack;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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


    MyReceiver myReceiver;



    //Test BUTTON
   // Button revert;

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
       // revert=view.findViewById(R.id.revertbt);

        Intent intent=getActivity().getIntent();
        constituency=intent.getExtras().getString("constituency",null);

        listtodisplay=new ArrayList<>();
        allformslistinready=new ArrayList<>();

        myReceiver=new MyReceiver();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("codewarriors");
        getActivity().registerReceiver(myReceiver, intentFilter);

      /*  revert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogForReadyForm dialogForReadyForm =new DialogForReadyForm(getActivity(),null);
                dialogForReadyForm.show();
            }
        });*/

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

                        Collections.sort(allformslistinready, new Comparator<FormPushPullCustomVAR>() {
                            @Override
                            public int compare(FormPushPullCustomVAR o1, FormPushPullCustomVAR o2) {
                                return o1.getFormno().compareToIgnoreCase(o2.getFormno());
                            }
                        });

                        readylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                DialogForReadyForm dialogForReadyForm =new DialogForReadyForm(getActivity(),allformslistinready.get(i));
                                dialogForReadyForm.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                       refresheverything();

                                    }
                                });
                                dialogForReadyForm.show();


                            }
                        });



                    }
                    else
                    {
                        Log.d("data","Ready is empty");
                    }

                    applicationFormListAdapter=new ApplicationFormListAdapter(getContext(),listtodisplay);
                    readylistview.setAdapter(applicationFormListAdapter);
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
           Toast.makeText(getContext(),"Error in Accepted",Toast.LENGTH_LONG).show();
          //  Toasty.error(getContext(),"Error in Accepted",Toast.LENGTH_LONG,true).show();
        }


        return view;
    }






    public void refresheverything()
    {
        DatabaseReference referencetoready=rootreference.child("consituency/"+constituency+"/");
        referencetoready.child("ready").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listtodisplay.clear();
                allformslistinready.clear();
                if(dataSnapshot.exists())
                {


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

                    Collections.sort(allformslistinready, new Comparator<FormPushPullCustomVAR>() {
                        @Override
                        public int compare(FormPushPullCustomVAR o1, FormPushPullCustomVAR o2) {
                            return o1.getFormno().compareToIgnoreCase(o2.getFormno());
                        }
                    });

                    applicationFormListAdapter.notifyDataSetChanged();

                }
                else
                {
                    Log.d("data","Ready is empty");
                }

                applicationFormListAdapter=new ApplicationFormListAdapter(getContext(),listtodisplay);
                readylistview.setAdapter(applicationFormListAdapter);
                applicationFormListAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.d("firebaseeeeee", databaseError.getMessage());


            }
        });


    }



    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub

            int datapassed = arg1.getIntExtra("data", 0);
            if(datapassed==1) {

              refresheverything();
            }

        }

    }













}
