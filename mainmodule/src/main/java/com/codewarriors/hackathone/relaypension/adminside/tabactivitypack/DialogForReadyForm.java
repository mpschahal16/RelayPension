package com.codewarriors.hackathone.relaypension.adminside.tabactivitypack;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codewarriors.hackathone.relaypension.Aadharverify;
import com.codewarriors.hackathone.relaypension.R;
import com.codewarriors.hackathone.relaypension.customvariablesforparsing.FormPushPullCustomVAR;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;



import static android.content.ContentValues.TAG;

/**
 * Created by hp on 22-03-2018.
 */

public class DialogForReadyForm extends Dialog implements View.OnClickListener {

    private Activity activity;

    private ImageView picimageview,imageViewpassbook,imageViewpayslip,imageViewsignature;

    private FormPushPullCustomVAR formPushPullCustomVAR;


   ProgressDialog progressDialog;
   //CatLoadingView mViewdddd;
   SmsManager smsManager;
   String a;


    private ArrayList<FormPushPullCustomVAR> queueformlist;
    private DatabaseReference rootreference= FirebaseDatabase.getInstance().getReference();

    private StorageReference storageRef =
            FirebaseStorage.getInstance().getReference();
    private EditText consituencyet, firstnameet, middlenameet, lastnameet, dobet, phonenoet, aadharnoet, hosenoet, streetet, postalet, cityet, stateet, familyincomeet, accountnoet,banknamert;
    private Spinner agespinner;
    private RadioButton maleradio, femaleradio, transgebderradio;



    DialogForReadyForm(Activity activity, FormPushPullCustomVAR formPushPullCustomVAR) {
        super(activity);
        this.activity=activity;
        this.formPushPullCustomVAR=formPushPullCustomVAR;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.custom_dia_forreadyform);
        Button cancel = findViewById(R.id.acceptbt);
        Button reject = findViewById(R.id.rejectbt);
        // image view
        picimageview = findViewById(R.id.imageviewfillform);
        imageViewpassbook = findViewById(R.id.imageViewpassbook);
        imageViewpayslip = findViewById(R.id.imageviewincomslip);
        imageViewsignature = findViewById(R.id.imageViewsgnature);

        // all et(Edittext) searialwise from layout
        consituencyet = findViewById(R.id.consituency_etfillform);
        firstnameet = findViewById(R.id.first_nameetfillform);
        middlenameet = findViewById(R.id.middle_nameetfillform);
        lastnameet = findViewById(R.id.last_nameetfillform);
        dobet = findViewById(R.id.dateofbirth_fillform);
        phonenoet = findViewById(R.id.phonenoetfillform);
        aadharnoet = findViewById(R.id.aadharnoetfillform);
        hosenoet = findViewById(R.id.houseno_etfillfrom);
        streetet = findViewById(R.id.street_etfillform);
        postalet = findViewById(R.id.postalcodeetfillform);
        cityet = findViewById(R.id.cityetfillform);
        stateet = findViewById(R.id.stateetfillform);
        accountnoet = findViewById(R.id.aacountnofillformet);
        familyincomeet = findViewById(R.id.familyincomefillformet);
        banknamert=findViewById(R.id.banknamespinner);


        // radio group and radio button
        maleradio = findViewById(R.id.malerbfillform);
        femaleradio = findViewById(R.id.femalerbfillform);
        transgebderradio = findViewById(R.id.transgenderrbfillform);


        //spinner in form layout
        agespinner = findViewById(R.id.agespinnerfillform);
        agespinner.setEnabled(false);
        





        setEverything();
        queueformlist=new ArrayList<>();

        progressDialog=new ProgressDialog(getContext());
       // mViewdddd = new CatLoadingView();

        cancel.setOnClickListener(this);
        reject.setOnClickListener(this);



    }

    private void setEverything() {

        consituencyet.setText(formPushPullCustomVAR.getConstituency());
        firstnameet.setText(formPushPullCustomVAR.getFirstName());
        middlenameet.setText(formPushPullCustomVAR.getMiddleName());
        lastnameet.setText(formPushPullCustomVAR.getLastName());
        dobet.setText(formPushPullCustomVAR.getDateofbirth());
        phonenoet.setText(formPushPullCustomVAR.getPhoneNo());
        aadharnoet.setText(formPushPullCustomVAR.getAadharNo());
        hosenoet.setText(formPushPullCustomVAR.getHoseno1());
        streetet.setText(formPushPullCustomVAR.getStreetorarea());
        postalet.setText(formPushPullCustomVAR.getPostalcode());
        cityet.setText(formPushPullCustomVAR.getCity());
        stateet.setText(formPushPullCustomVAR.getState());
        familyincomeet.setText(formPushPullCustomVAR.getFamilyincome());
        agespinner.setSelection(Integer.parseInt(formPushPullCustomVAR.getAge()));
        switch (formPushPullCustomVAR.getGender()) {
            case "Male": {
                maleradio.setChecked(true);
                break;
            }
            case "Female": {
                femaleradio.setChecked(true);
                break;
            }
            case "Transgender": {
                transgebderradio.setChecked(true);
                break;
            }
            default: {
                Log.d("rrrrrrrrrrrrr","error in gender");
            }
        }

        accountnoet.setText(formPushPullCustomVAR.getBankaccountno());
        banknamert.setText(formPushPullCustomVAR.getBankname());



        storageRef.child(formPushPullCustomVAR.getAadharNo()+"/userpic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(activity).load(uri).into(picimageview);

            }


        });

        storageRef.child(formPushPullCustomVAR.getAadharNo()+"/incomeslip").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(activity).load(uri).into(imageViewpayslip);

            }
        });

        storageRef.child(formPushPullCustomVAR.getAadharNo()+"/passbook").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(activity).load(uri).into(imageViewpassbook);

            }
        });
        storageRef.child(formPushPullCustomVAR.getAadharNo()+"/signature").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(activity).load(uri).into(imageViewsignature);

            }
        });

    }


    @Override
    public void onClick(View view) {
        int i=view.getId();


        /*mViewdddd.setCanceledOnTouchOutside(false);
        mViewdddd.setText("Applying Changes");
        mViewdddd.show(((FragmentActivity) activity)
                .getSupportFragmentManager(),"");*/


        switch (i)
        {
            case R.id.acceptbt:
            {

                progressDialog.setMessage("Applying Changes");
                progressDialog.setCancelable(false);
                progressDialog.show();
                DatabaseReference fromreadyref=FirebaseDatabase.getInstance().getReference("consituency/"+formPushPullCustomVAR.getConstituency()+"/"+
                        "ready/");
                DatabaseReference acceptref=FirebaseDatabase.getInstance().getReference("consituency/"+formPushPullCustomVAR.getConstituency()+"/"+
                        "accepted/");
                readytoaccepted(fromreadyref,acceptref,formPushPullCustomVAR.getAadharNo());






                //TEST VALUES
            /*  DatabaseReference fromreadyref=FirebaseDatabase.getInstance().getReference("consituency/"+"A"+"/"+
                        "ready/");
                DatabaseReference acceptref=FirebaseDatabase.getInstance().getReference("consituency/"+"A"+"/"+
                        "accepted/");
                 // readytoaccepted(fromreadyref,acceptref,"499240755287");
                readytoaccepted(acceptref,fromreadyref,"499240755287");*/
                break;
            }


            case R.id.rejectbt:
            {

                 DatabaseReference fromreadyref=FirebaseDatabase.getInstance().getReference("consituency/"+formPushPullCustomVAR.getConstituency()+"/"+
                "ready/");
                DatabaseReference rejectref=FirebaseDatabase.getInstance().getReference("consituency/"+formPushPullCustomVAR.getConstituency()+"/"+
                        "rejected/");

                DialogForAdminReveify dialogForAdminReveify=new DialogForAdminReveify(activity,fromreadyref,rejectref,formPushPullCustomVAR);

                dialogForAdminReveify.show();
                dialogForAdminReveify.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                       dismiss();
                    }
                });

                /* DatabaseReference fromreadyref=FirebaseDatabase.getInstance().getReference("consituency/"+formPushPullCustomVAR.getConstituency()+"/"+
                "ready/");
                DatabaseReference rejectref=FirebaseDatabase.getInstance().getReference("consituency/"+formPushPullCustomVAR.getConstituency()+"/"+
                        "rejected/");
                readytorejected(fromreadyref,rejectref,formPushPullCustomVAR.getAadharNo());*/






                //TEST VALUES
              /* DatabaseReference fromreadyref=FirebaseDatabase.getInstance().getReference("consituency/"+"A"+"/"+
                        "ready/");
                DatabaseReference rejectref=FirebaseDatabase.getInstance().getReference("consituency/"+"A"+"/"+
                        "rejected/");

               // readytorejected(fromreadyref,rejectref,"499240755287");
                readytorejected(rejectref,fromreadyref,"499240755287");*/


                break;
            }




        }
    }



    private void readytorejected(final DatabaseReference fromPath, final DatabaseReference toPath, final String key) {
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


    private void readytoaccepted(final DatabaseReference fromPath, final DatabaseReference toPath, final String key) {
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
                                    setstateofapplictionform(key,"4");
                                    getQueuelist();

                                }
                                else {
                                    Log.e(TAG, "onComplete: failure:" + databaseError.getMessage() + ": "
                                            + databaseError.getDetails());
                                }
                            }
                        });

                a = "Your application has been accepted";
                smsManager.sendTextMessage("+91"+formPushPullCustomVAR.getPhoneNo(),null,""+a,null,null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: " + databaseError.getMessage() + ": "
                        + databaseError.getDetails());
            }
        });
    }

    private void setstateofapplictionform(final String aadhrnowheretochange, String state) {
        DatabaseReference root=FirebaseDatabase.getInstance().getReference("userstatecons/"+aadhrnowheretochange+"/");

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


                                    //progressDialog.dismiss();

                                    //mViewdddd.dismiss();
                                    queueformlist.remove(0);
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






    private void getQueuelist()
    {

        DatabaseReference referencetoready=rootreference.child("consituency/"+formPushPullCustomVAR.getConstituency()+"/");
        referencetoready.child("queue").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    queueformlist.clear();
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

                        progressDialog.dismiss();

                        //mViewdddd.dismiss();
                        dismiss();


                    }



                }
                else
                {

                    progressDialog.dismiss();

                    //mViewdddd.dismiss();

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
}
