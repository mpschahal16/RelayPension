package com.codewarriors.hackathone.relaypension.adminside.tabactivitypack;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.codewarriors.hackathone.relaypension.R;
import com.codewarriors.hackathone.relaypension.StubNoReturn;
import com.codewarriors.hackathone.relaypension.customvariablesforparsing.FormPushPullCustomVAR;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by hp on 22-03-2018.
 */

public class FormDialogForCheck extends Dialog implements View.OnClickListener {

    Activity activity;
    String constituency,readyorqueue,aadharno;
    Button cancel,reject;


    EditText consituencyet, firstnameet, middlenameet, lastnameet, dobet, phonenoet, aadharnoet, hosenoet, streetet, postalet, cityet, stateet, familyincomeet, accountnoet,banknamert;
    Spinner agespinner;
    RadioGroup genderradiogroup;
    RadioButton maleradio, femaleradio, transgebderradio;

    DatabaseReference rootreference= FirebaseDatabase.getInstance().getReference();

    public FormDialogForCheck(Activity activity,String constituency,String readyorqueue,String aadharno) {
        super(activity);

        this.activity = activity;
        this.constituency=constituency;
        this.readyorqueue=readyorqueue;
        this.aadharno=aadharno;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.customformdialayout);
        cancel=findViewById(R.id.cancelbt);
        reject=findViewById(R.id.rejectbt);
        consituencyet=findViewById(R.id.consituency_etfillform);
        firstnameet=findViewById(R.id.first_nameetfillform);
        middlenameet=findViewById(R.id.middle_nameetfillform);
        lastnameet=findViewById(R.id.last_nameetfillform);


        cancel.setOnClickListener(this);
        reject.setOnClickListener(this);

        DatabaseReference exactref=rootreference.child("consituency/"+constituency+"/"+readyorqueue+"/");

        exactref.child(aadharno).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    FormPushPullCustomVAR formPushPullCustomVAR=dataSnapshot.getValue(FormPushPullCustomVAR.class);

                    consituencyet.setText(formPushPullCustomVAR.getConstituency());
                    firstnameet.setText(formPushPullCustomVAR.getFirstName());
                    middlenameet.setText(formPushPullCustomVAR.getMiddleName());
                    lastnameet.setText(formPushPullCustomVAR.getLastName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                activity.startActivity(new Intent(activity, StubNoReturn.class));
            }
        });

    }


    @Override
    public void onClick(View view) {
        int i=view.getId();
        switch (i)
        {
            case R.id.cancelbt:
            {
                dismiss();
                break;
            }


            case R.id.rejectbt:
            {
                Toast.makeText(activity,"clicked",Toast.LENGTH_LONG).show();
                break;
            }




        }
    }
}
