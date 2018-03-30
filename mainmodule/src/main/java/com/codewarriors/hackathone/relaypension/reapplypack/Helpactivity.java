package com.codewarriors.hackathone.relaypension.reapplypack;

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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codewarriors.hackathone.relaypension.R;
import com.codewarriors.hackathone.relaypension.StubNoReturn;
import com.codewarriors.hackathone.relaypension.customvariablesforparsing.StubAadhaarCustomVAR;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
        EditText username, fno, message;
        ImageView img;
        Button select, submit;
        CheckBox cb;
        ProgressDialog progressDialog;
        FirebaseStorage storage;
        StorageReference storageReference;
        String txt,adno;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_help_act);
            username = findViewById(R.id.etname);
            fno = findViewById(R.id.et_fn);
            message = findViewById(R.id.et_msg);
            img = findViewById(R.id.ivimg);
            select = findViewById(R.id.bt_img);
            submit = findViewById(R.id.submitbt);
            cb = findViewById(R.id.im_cb);

            submit.setOnClickListener(this);
            cb.setOnClickListener(this);


            /*Intent it = getIntent();
            adno=it.getExtras().getString("aadharno",null);


        if (adno != null ) {
            final DatabaseReference rootreference= FirebaseDatabase.getInstance().getReference();
            DatabaseReference stubreferecne = rootreference.child("stubofuid/");
            stubreferecne.child(adno).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        StubAadhaarCustomVAR stubAadhaarCustomVAR = dataSnapshot.getValue(StubAadhaarCustomVAR.class);
                        username.setText("" + stubAadhaarCustomVAR.getFirstName() + stubAadhaarCustomVAR.getMiddleName() + stubAadhaarCustomVAR.getLastName());
                        fno.setText(stubAadhaarCustomVAR.getAadharNo());
                        storageReference = storage.getReference().child(stubAadhaarCustomVAR.getAadharNo());
                    }
                    else
                    {
                        startActivity(new Intent(Helpactivity.this, StubNoReturn.class));
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    startActivity(new Intent(Helpactivity.this, StubNoReturn.class));
                }
            });

        } else {
            startActivity(new Intent(Helpactivity.this, StubNoReturn.class));
        }*/
            storage = FirebaseStorage.getInstance();
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.submitbt :
                    {
                   txt= message.getText().toString();

                }

                case R.id.bt_img :
                    {
                    try {
                        Intent photoPicker = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(photoPicker,1243 );

                    } catch (Exception exp) {
                        Log.i("Error", exp.toString());
                    }

                    break;

                }


            }
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            switch (requestCode) {
                case 1243: {
                    if (resultCode == RESULT_OK && null != data) {
                        final Uri selectedImage = data.getData();

                  /*  mViewdddd.setCanceledOnTouchOutside(false);
                    mViewdddd.setText("Uploading...");
                    mViewdddd.show(getSupportFragmentManager(),"");*/

                        progressDialog.setTitle("Uploading...");
                        progressDialog.show();
                        progressDialog.setCancelable(false);
                        StorageReference sre = storageReference.child("userpic");
                        sre.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                                //  mViewdddd.dismiss();
                                Glide.with(Helpactivity.this).load(selectedImage).into(img);
                                cb.setChecked(true);
                                Toast.makeText(Helpactivity.this, "Uploaded", Toast.LENGTH_LONG).show();
                                // Toasty.success(FIllform.this, "Uploaded", Toast.LENGTH_SHORT,true).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                //  mViewdddd.dismiss();
                                progressDialog.dismiss();
                                Log.d("eeeeeeee", "Error: " + e.getMessage());
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                        .getTotalByteCount());
                                //  mViewdddd.setText("Uploaded "+(int)progress+"%");
                                progressDialog.setMessage("Uploaded " + (int) progress + "%");
                            }
                        });


                    }

                }
            }
        }
}