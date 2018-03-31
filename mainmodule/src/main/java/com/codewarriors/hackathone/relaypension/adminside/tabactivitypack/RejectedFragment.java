package com.codewarriors.hackathone.relaypension.adminside.tabactivitypack;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.codewarriors.hackathone.relaypension.R;
import com.codewarriors.hackathone.relaypension.customvariablesforparsing.FormPushPullCustomVAR;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;




public class RejectedFragment extends Fragment implements View.OnClickListener {

    DatabaseReference rootreference= FirebaseDatabase.getInstance().getReference();
    private String constituency;

    Button savealltocsv;

    ListView rejectedlistview;

    FormPushPullCustomVAR formPushPullCustomVAR;
    ApplicationFormListAdapter applicationFormListAdapter;


    ArrayList<ApplicationFormListVAR> listtodisplay;
    ArrayList<FormPushPullCustomVAR> allformslistinrejected;

    public RejectedFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rejected, container, false);

        rejectedlistview=view.findViewById(R.id.rejectformlv);

        savealltocsv=view.findViewById(R.id.savetvbtocs);

        Intent intent=getActivity().getIntent();
        constituency=intent.getExtras().getString("constituency");

        listtodisplay=new ArrayList<>();
        allformslistinrejected=new ArrayList<>();

        if(constituency!=null)
        {
            DatabaseReference referencetoready=rootreference.child("consituency/"+constituency+"/");
            referencetoready.child("rejected").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    listtodisplay.clear();
                    allformslistinrejected.clear();
                    if(dataSnapshot.exists())
                    {

                        for(DataSnapshot dataSnapshotchild:dataSnapshot.getChildren()) {
                            formPushPullCustomVAR=dataSnapshotchild.getValue(FormPushPullCustomVAR.class);
                            allformslistinrejected.add(formPushPullCustomVAR);
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

                        Collections.sort(allformslistinrejected, new Comparator<FormPushPullCustomVAR>() {
                            @Override
                            public int compare(FormPushPullCustomVAR o1, FormPushPullCustomVAR o2) {
                                return o1.getFormno().compareToIgnoreCase(o2.getFormno());
                            }
                        });


                        rejectedlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                DialogOfFormWithPdf dialogForQueueForm =new DialogOfFormWithPdf(getActivity(),allformslistinrejected.get(i),"2");
                                dialogForQueueForm.show();
                            }
                        });
                    }
                    else
                    {
                        Log.d("data","queue is empty");
                    }

                    applicationFormListAdapter=new ApplicationFormListAdapter(getContext(),listtodisplay);
                    rejectedlistview.setAdapter(applicationFormListAdapter);
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
            //Toasty.error(getContext(),"Error in Accepted",Toast.LENGTH_LONG,true).show();
        }

        savealltocsv.setOnClickListener(this);







        return view;
    }


    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id)
        {
            case R.id.savetvbtocs:
            {
                savealltocsv();
                break;
            }
        }
    }

    private void savealltocsv() {
        if(allformslistinrejected.isEmpty())
        {
            Toast.makeText(getContext(),"Rejected is empty",Toast.LENGTH_LONG).show();
        }
        else
        {
            ProgressDialog progressDialog=new ProgressDialog(getContext());
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Saving to .csv");
            String dir="/sdcard/Relay_Pension/RejectedCSV";
            File defaultFile = new File(dir);
            if (!defaultFile.exists())
                defaultFile.mkdirs();


            String filename = "/sdcard/Relay_Pension/RejectedCSV/rejectedcsv.csv";

            try {
                FileWriter fw = new FileWriter(filename);


                fw.append("Aadhar No");
                fw.append(",");

                fw.append("Form No");
                fw.append(",");

                fw.append("Constituency");
                fw.append(",");

                fw.append("First Name");
                fw.append(",");

                fw.append("Middle Name");
                fw.append(",");

                fw.append("Last Name");
                fw.append(",");

                fw.append("Age");
                fw.append(",");


                fw.append("Gender");
                fw.append(",");

                fw.append("D.O.B.");
                fw.append(",");

                fw.append("Phone No.");
                fw.append(",");

                fw.append("Address");
                fw.append(",");

                fw.append("City");
                fw.append(",");

                fw.append("State");
                fw.append(",");

                fw.append("Postal Code");
                fw.append(",");

                fw.append("Bank Account no.");
                fw.append(",");

                fw.append("Bank Name");
                fw.append(",");

                fw.append("Family Income");
                fw.append(",");


                fw.append("Status of Form");
                fw.append(",");



                fw.append('\n');




                for (FormPushPullCustomVAR formPushPullCustomVAR:allformslistinrejected)
                {

                    fw.append(formPushPullCustomVAR.getAadharNo());
                    fw.append(",");

                    fw.append(formPushPullCustomVAR.getFormno());
                    fw.append(",");

                    fw.append(formPushPullCustomVAR.getConstituency());
                    fw.append(",");

                    fw.append(formPushPullCustomVAR.getFirstName());
                    fw.append(",");

                    fw.append(formPushPullCustomVAR.getMiddleName());
                    fw.append(",");

                    fw.append(formPushPullCustomVAR.getLastName());
                    fw.append(",");

                    fw.append(formPushPullCustomVAR.getAge());
                    fw.append(",");


                    fw.append(formPushPullCustomVAR.getGender());
                    fw.append(",");

                    fw.append(formPushPullCustomVAR.getDateofbirth());
                    fw.append(",");

                    fw.append(formPushPullCustomVAR.getFormno());
                    fw.append(",");

                    fw.append(formPushPullCustomVAR.getHoseno1()+","+formPushPullCustomVAR.getStreetorarea());
                    fw.append(",");

                    fw.append(formPushPullCustomVAR.getCity());
                    fw.append(",");

                    fw.append(formPushPullCustomVAR.getState());
                    fw.append(",");

                    fw.append(formPushPullCustomVAR.getPostalcode());
                    fw.append(",");

                    fw.append(formPushPullCustomVAR.getBankaccountno());
                    fw.append(",");

                    fw.append(formPushPullCustomVAR.getBankname());
                    fw.append(",");

                    fw.append(formPushPullCustomVAR.getFamilyincome());
                    fw.append(",");


                    fw.append("Accepted");
                    fw.append(",");



                    fw.append('\n');





                }
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }




            Toast.makeText(getContext(),"sucess",Toast.LENGTH_LONG).show();

        }
    }
}
