package com.codewarriors.hackathone.relaypension.reapplypack;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codewarriors.hackathone.relaypension.FIllform;
import com.codewarriors.hackathone.relaypension.R;
import com.codewarriors.hackathone.relaypension.StubNoReturn;
import com.codewarriors.hackathone.relaypension.customvariablesforparsing.ReportCustomVAR;
import com.codewarriors.hackathone.relaypension.customvariablesforparsing.StubAadhaarCustomVAR;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Helpactivity extends AppCompatActivity implements View.OnClickListener {
    EditText aadharnoet, fnoet, messageet;
    ImageView imgvreport;
    Button select, submit;
    CheckBox cbforalreadyuser, checkBoforpic;

    final int RESULT_LOAD_INCOME_PIC = 461;

    ProgressDialog progressDialog;

    String aadharno, fno = "", messagestr;


    FirebaseStorage storage;
    StorageReference storageReference;

    LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_act);

        aadharnoet = findViewById(R.id.reportaadharno);
        fnoet = findViewById(R.id.et_fn);
        linearLayout = findViewById(R.id.reportlinearlayout);
        linearLayout.setVisibility(View.INVISIBLE);
        messageet = findViewById(R.id.reportmessage);
   /*     select = findViewById(R.id.reportpicselect);
        imgvreport=findViewById(R.id.reportimageview);*/

        submit = findViewById(R.id.submitbtreport);
        FirebaseStorage storage;

        //select.setOnClickListener(this);
        submit.setOnClickListener(this);

        progressDialog=new ProgressDialog(this);

        cbforalreadyuser = findViewById(R.id.checkBoxregister);
        cbforalreadyuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cbforalreadyuser.isChecked()) {
                    linearLayout.setVisibility(View.INVISIBLE);
                    //cbforalreadyuser);
                } else {
                    linearLayout.setVisibility(View.VISIBLE);
                    //cbforalreadyuser.setChecked(true);
                }
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

         /*   case R.id.reportpicselect: {
                if(aadharnoet.getText().toString().length()==12) {
                    Intent photoPicker = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(photoPicker, RESULT_LOAD_INCOME_PIC);
                    break;
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please enter aadhar no",Toast.LENGTH_LONG).show();
                }
            }*/

            case R.id.submitbtreport: {

                progressDialog.setMessage("Sending...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("report/");
                mDatabase.child(aadharnoet.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {

                            Toast.makeText(getApplicationContext(),"Your complain is already in progress",Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                        else
                        {

                            if(cbforalreadyuser.isChecked())
                            {
                                String aadhrno=aadharnoet.getText().toString();
                                String fno=fnoet.getText().toString();
                                String mess=messageet.getText().toString();
                                String checked="yes";
                                ReportCustomVAR reportCustomVAR=new ReportCustomVAR(aadhrno,fno,mess,checked);







                                mDatabase.child(aadharnoet.getText().toString()).setValue(reportCustomVAR).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            aadharnoet.setText("");
                                            fnoet.setText("");
                                            messageet.setText("");
                                            cbforalreadyuser.setChecked(false);
                                            linearLayout.setVisibility(View.INVISIBLE);

                                            progressDialog.dismiss();
                                            AlertDialog.Builder builder = new AlertDialog.Builder(Helpactivity.this);
                                            builder.setMessage("Cpmplain submitted sucessfully")
                                                    .setTitle("Sucess");

                                            AlertDialog alertDialog=builder.create();
                                            alertDialog.show();
                                        }
                                        else
                                        {
                                            Toast.makeText(getApplicationContext(), "Failed",Toast.LENGTH_LONG).show();
                                            //Toasty.error(getApplicationContext(), "Failed", Toast.LENGTH_LONG,true).show();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("errrrrr","ERROR "+e.getMessage());
                                    }
                                });


                            }
                            else
                            {

                                String aadhrno=aadharnoet.getText().toString();
                                String fno=fnoet.getText().toString();
                                String mess=messageet.getText().toString();
                                String checked="no";
                                ReportCustomVAR reportCustomVAR=new ReportCustomVAR(aadhrno,fno,mess,checked);







                                mDatabase.child(aadharnoet.getText().toString()).setValue(reportCustomVAR).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            aadharnoet.setText("");
                                            fnoet.setText("");
                                            messageet.setText("");
                                            cbforalreadyuser.setChecked(false);
                                            linearLayout.setVisibility(View.INVISIBLE);

                                            progressDialog.dismiss();
                                            AlertDialog.Builder builder = new AlertDialog.Builder(Helpactivity.this);
                                            builder.setMessage("Complain submitted sucessfully")
                                                    .setTitle("Sucess");

                                            AlertDialog alertDialog=builder.create();
                                            alertDialog.show();
                                        }
                                        else
                                        {
                                            Toast.makeText(getApplicationContext(), "Failed",Toast.LENGTH_LONG).show();
                                            //Toasty.error(getApplicationContext(), "Failed", Toast.LENGTH_LONG,true).show();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("errrrrr","ERROR "+e.getMessage());
                                    }
                                });



                            }



                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                break;
            }


        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && null != data) {
            final Uri selectedImage = data.getData();


                   /* mViewdddd.setCanceledOnTouchOutside(false);
                    mViewdddd.setText("Uploading...");
                    mViewdddd.show(getSupportFragmentManager(),"");*/

            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            progressDialog.setCancelable(false);
            storageReference = storage.getReference("reportimg").child(aadharnoet.getText().toString());

            StorageReference sre=storageReference.child("passbook");
            sre.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    // mViewdddd.dismiss();
                    Glide.with(Helpactivity.this).load(selectedImage).into(imgvreport);
                    checkBoforpic.setChecked(true);
                    //Toasty.success(FIllform.this, "Uploaded", Toast.LENGTH_SHORT,true).show();

                    Toast.makeText(Helpactivity.this,"Uploaded",Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    progressDialog.dismiss();
                    // mViewdddd.dismiss();
                    Log.d("eeeeeeee","Error: "+e.getMessage());
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                            .getTotalByteCount());
                    // mViewdddd.setText("Uploaded "+(int)progress+"%");
                    progressDialog.setMessage("Uploaded "+(int)progress+"%");
                }
            });
        }

    }

}