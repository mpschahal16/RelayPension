package com.codewarriors.hackathone.relaypension.adminside.tabactivitypack;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.codewarriors.hackathone.relaypension.R;
import com.codewarriors.hackathone.relaypension.customvariablesforparsing.FormPushPullCustomVAR;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static android.content.ContentValues.TAG;
import static android.support.v4.provider.FontsContractCompat.FontRequestCallback.RESULT_OK;

/*
 * Created by manpreet on 30/3/18.
 */

public class DialogForAdminReveify extends Dialog implements View.OnClickListener {

    Activity activity;

    private DatabaseReference rootreference= FirebaseDatabase.getInstance().getReference();
    Button dismiss,reject;
    RadioGroup radioGroup;
    RadioButton radioButton1,radioButton2,radioButton3;

    String rejectreason="0";
    FormPushPullCustomVAR formPushPullCustomVAR;

    DatabaseReference fromreadyref,rejectref;

    SmsManager smsManager;
    String a,b;

    public DialogForAdminReveify(@NonNull Activity activity, DatabaseReference fromreadyref, DatabaseReference rejectref, FormPushPullCustomVAR formPushPullCustomVAR) {
        super(activity);
        this.activity=activity;
        this.fromreadyref=fromreadyref;
        this.rejectref=rejectref;
        this.formPushPullCustomVAR=formPushPullCustomVAR;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.reject_dia);

        dismiss=findViewById(R.id.dismissdiarejbt);
        reject=findViewById(R.id.rejectdiabt);

        radioGroup=findViewById(R.id.radiogroupid);
        radioButton1=findViewById(R.id.reasonreject1);
        radioButton2=findViewById(R.id.reasonreject2);
        radioButton3=findViewById(R.id.reasonreject3);

        smsManager = SmsManager.getDefault();



        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {

                    case R.id.reasonreject1:
                    {
                        rejectreason="1";
                        a="reson 1";
                        break;
                    }
                    case R.id.reasonreject2:
                    {
                        rejectreason="2";
                        a="reason 2";
                        break;
                    }
                    case R.id.reasonreject3:
                    {

                        rejectreason="3";
                        a="reason 3";
                        break;
                    }
                }
            }
        });

        dismiss.setOnClickListener(this);
        reject.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id)
        {

            case R.id.rejectdiabt:
            {
                if(!rejectreason.equals("0"))
                {

                    readytorejected(fromreadyref,rejectref,formPushPullCustomVAR.getAadharNo(),rejectreason);

                }
                else
                {
                    Toast.makeText(getContext(),"Please select reason",Toast.LENGTH_LONG).show();
                }
                b = "Your application has been rejected because"+a;
                smsManager.sendTextMessage("+91"+formPushPullCustomVAR.getPhoneNo(),null,""+b,null,null);

                break;

            }
            case R.id.dismissdiarejbt:
            {
                dismiss();
            }
        }
    }

    private void readytorejected(final DatabaseReference fromPath, final DatabaseReference toPath, final String key,String reasonflag) {
        fromPath.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            // Now "DataSnapshot" holds the key and the value at the "fromPath".
            // Let's move it to the "toPath". This operation duplicates the
            // key/value pair at the "fromPath" to the "toPath".
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                toPath.child(dataSnapshot.getKey())
                        .setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if (databaseError == null) {
                                    Log.i(TAG, "onComplete: success");
                                    // In order to complete the move, we are going to erase
                                    // the original copy by assigning null as its value.
                                    fromPath.child(key).setValue(null);
                                    setstateofapplictionform(key,"0");
                                    seterrorinappliction(key);
                                    getQueuelist();
                                }
                                else {
                                    Log.e(TAG, "onComplete: failure:" + databaseError.getMessage() + ": "
                                            + databaseError.getDetails());
                                }
                            }
                        });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: " + databaseError.getMessage() + ": "
                        + databaseError.getDetails());
            }
        });
    }

    private void seterrorinappliction(final String aadhrnowheretochange)
    {
        DatabaseReference root= FirebaseDatabase.getInstance().getReference("userstatecons/"+aadhrnowheretochange+"/");

        root.child("errorinapplication").setValue(rejectreason).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(activity,"Sucess to change application state",Toast.LENGTH_LONG).show();
                //Toasty.success(activity,"Sucess to change application state",Toast.LENGTH_LONG,true).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("failure","to write state in statecons"+e.getMessage());
            }
        });

    }

    private void setstateofapplictionform(final String aadhrnowheretochange, String state) {
        DatabaseReference root= FirebaseDatabase.getInstance().getReference("userstatecons/"+aadhrnowheretochange+"/");

        root.child("applicationstate").setValue(state).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(activity,"Sucess to change application state",Toast.LENGTH_LONG).show();
                //Toasty.success(activity,"Sucess to change application state",Toast.LENGTH_LONG,true).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("failure","to write state in statecons"+e.getMessage());
            }
        });
    }


    private void getQueuelist()
    {

        DatabaseReference referencetoready=rootreference.child("consituency/"+formPushPullCustomVAR.getConstituency()+"/");
        referencetoready.child("queue").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {

                    ArrayList<FormPushPullCustomVAR> queueformlist=new ArrayList<>();
                    for(DataSnapshot dataSnapshotchild:dataSnapshot.getChildren()) {

                        FormPushPullCustomVAR formPushPullCustomVAR=dataSnapshotchild.getValue(FormPushPullCustomVAR.class);
                        queueformlist.add(formPushPullCustomVAR);

                    }
                    Collections.sort(queueformlist, new Comparator<FormPushPullCustomVAR>() {

                        @Override
                        public int compare(FormPushPullCustomVAR formPushPullCustomVAR, FormPushPullCustomVAR t1) {
                            return formPushPullCustomVAR.getFormno().compareToIgnoreCase(t1.getFormno());
                        }
                    });


                    if(!queueformlist.isEmpty())
                    {


                        DatabaseReference fromqueue=FirebaseDatabase.getInstance().getReference("consituency/"+formPushPullCustomVAR.getConstituency()+"/"+
                                "queue/");
                        DatabaseReference toready=FirebaseDatabase.getInstance().getReference("consituency/"+formPushPullCustomVAR.getConstituency()+"/"+
                                "ready/");

                        queuetoready(fromqueue,toready,queueformlist.get(0).getAadharNo());

                    }
                    else
                    {



                        //mViewdddd.dismiss();
                        dismiss();


                    }



                }
                else
                {


                    dismiss();
                    Log.d("error","erreor in dialog while fetching queue");

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.d("canceled","erreor in dialog while fetching queue");

            }
        });
    }


    private void queuetoready(final DatabaseReference fromPath, final DatabaseReference toPath, final String key) {
        fromPath.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            // Now "DataSnapshot" holds the key and the value at the "fromPath".
            // Let's move it to the "toPath". This operation duplicates the
            // key/value pair at the "fromPath" to the "toPath".
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                toPath.child(dataSnapshot.getKey())
                        .setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if (databaseError == null) {
                                    Log.d("errrrrrrrrrrrrr","12345");
                                    Log.i(TAG, "onComplete: success");
                                    // In order to complete the move, we are going to erase
                                    // the original copy by assigning null as its value.
                                    fromPath.child(key).setValue(null);
                                    setstateofapplictionform(key,"2");

                                    dismiss();

                                }
                                else {
                                    Log.e(TAG, "onComplete: failure:" + databaseError.getMessage() + ": "
                                            + databaseError.getDetails());
                                }
                            }
                        });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: " + databaseError.getMessage() + ": "
                        + databaseError.getDetails());
            }
        });
    }



}
