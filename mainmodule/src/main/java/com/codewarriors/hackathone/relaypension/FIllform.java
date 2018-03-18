package com.codewarriors.hackathone.relaypension;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.bumptech.glide.Glide;
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

import java.util.Calendar;
import java.util.GregorianCalendar;

public class FIllform extends AppCompatActivity implements View.OnClickListener {
    EditText consituencyet, firstnameet, middlenameet, lastnameet, dobet, phonenoet, aadharnoet, hosenoet, streetet, postalet, cityet, stateet, familyincomeet, accountnoet;
    Spinner agespinner, bankspinner;
    RadioGroup genderradiogroup;
    RadioButton maleradio, femaleradio, transgebderradio;
    CheckBox selectpiccb, passbookcb, payslipcb, signaturecb;


    ImageView picimageview, imageViewpassbook, imageViewpayslip, imageViewsignature;


    Button submitbt, selectphotobt, changeaddressbt, selectpassbookpicbt, selectincomeslipbt, selectsignaturebt;


    String adno, consituency, familyincome;

    AwesomeValidation awesomeValidation;


    FirebaseStorage storage;
    StorageReference storageReference;


    final int RESULT_LOAD_PIC = 459;
    final int RESULT_LOAD_PASSBOOK_PIC = 460;
    final int RESULT_LOAD_INCOME_PIC = 461;
    final int RESULT_LOAD_SIGNATURE = 462;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fillform);
        // getting permission if not on devices > marshmello
        reqPermission();

        //linking ui with code and listner
        seteverythingonlayout();


        // Test values that we will get from intent in normal work
        adno = "499240755287";
        consituency = "A";
        familyincome = "80000";

        //getting values from calling intent
        Intent it = getIntent();


      /*  adno=it.getExtras().getString("aadharno",null);
        consituency=it.getExtras().getString("constituency",null);
        familyincome=it.getExtras().getString("salary",null);*/
        if (adno != null && consituency != null && familyincome != null) {
            final DatabaseReference rootreference=FirebaseDatabase.getInstance().getReference();
            DatabaseReference stubreferecne = rootreference.child("stubofuid/");
            stubreferecne.child(adno).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        StubAadhaarCustomVAR stubAadhaarCustomVAR = dataSnapshot.getValue(StubAadhaarCustomVAR.class);
                        setAadharValues(stubAadhaarCustomVAR);

                    } else {
                        startActivity(new Intent(FIllform.this, StubNoReturn.class));
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    startActivity(new Intent(FIllform.this, StubNoReturn.class));
                }
            });

            rootreference.child("globalcounter").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String formno=dataSnapshot.getValue().toString();
                    int temp=Integer.parseInt(formno)+1;
                    rootreference.child("globalcounter").setValue(temp);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        } else {
            startActivity(new Intent(FIllform.this, StubNoReturn.class));
        }


    }


    private void seteverythingonlayout() {


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


        // radio group and radio button
        genderradiogroup = findViewById(R.id.genderrgfillform);
        maleradio = findViewById(R.id.malerbfillform);
        femaleradio = findViewById(R.id.femalerbfillform);
        transgebderradio = findViewById(R.id.transgenderrbfillform);


        //spinner in form layout
        agespinner = findViewById(R.id.agespinnerfillform);
        agespinner.setEnabled(false);
        bankspinner = findViewById(R.id.banknamespinner);


        //all bt(buttons) in form layout

        submitbt = findViewById(R.id.submitfillformbt);
        selectphotobt = findViewById(R.id.selectimgfillformbt);
        changeaddressbt = findViewById(R.id.changeaddressfilformbt);
        selectpassbookpicbt = findViewById(R.id.selectpassbook_fillformbt);
        selectincomeslipbt = findViewById(R.id.selectIncomslipbt_fillformbt);
        selectsignaturebt = findViewById(R.id.selectsignature_fillformbt);

        //all checkbox in from layout
        selectpiccb = findViewById(R.id.selcetpic_fillformcb);
        passbookcb = findViewById(R.id.selectpassbookcb);
        payslipcb = findViewById(R.id.selectincomecb);
        signaturecb = findViewById(R.id.selectsignaturecb);


        //awsomeValidation of all edittext

        awesomeValidation = new AwesomeValidation(ValidationStyle.COLORATION);
        awesomeValidation.addValidation(this, R.id.consituency_etfillform, "[a-z A-Z][a-z A-Z]*", R.string.invalid);
        awesomeValidation.addValidation(this, R.id.first_nameetfillform, "[a-z A-Z][a-z A-z]*", R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.middle_nameetfillform, "[a-z A-Z]*", R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.last_nameetfillform, "[a-z A-Z][a-z A-Z]*", R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.dateofbirth_fillform, "([0-9]{2})/([0-9]{2})/([0-9]{4})", R.string.invalid_dob);
        awesomeValidation.addValidation(this, R.id.phonenoetfillform, Patterns.PHONE, R.string.invalid_mobile_no);
        //12 digit aadhaar
        awesomeValidation.addValidation(this, R.id.aadharnoetfillform, "\\d{12}", R.string.invalid_aadhaar);
        //here house no means house no or buildin For Ex 2/1304 or Sai complex
        awesomeValidation.addValidation(this, R.id.houseno_etfillfrom, "[a-z A-Z 0-9][a-z A-Z 0-9 /  , .]*[-]*[a-z A-Z 0-9 /  , .]*", R.string.invalid_hoseno);
        //street for ex Buddhi Vihar
        awesomeValidation.addValidation(this, R.id.street_etfillform, "[a-z A-Z][a-z A-Z , /]*", R.string.invalid_hoseno);
        //any 6 digit no
        awesomeValidation.addValidation(this, R.id.postalcodeetfillform, "([0-9]{6})", R.string.invalid_postal);
        awesomeValidation.addValidation(this, R.id.cityetfillform, "[a-z A-Z][a-z A-Z]*", R.string.invalid_city);
        awesomeValidation.addValidation(this, R.id.stateetfillform, "[a-z A-Z][a-z A-Z]*", R.string.invalid_state);
        awesomeValidation.addValidation(this, R.id.aacountnofillformet, "[0-9][0-9]*", R.string.invalid_accountnp);
        awesomeValidation.addValidation(this, R.id.familyincomefillformet, "[0-9][0-9]*", R.string.invalid);

        submitbt.setOnClickListener(this);
        selectphotobt.setOnClickListener(this);
        changeaddressbt.setOnClickListener(this);
        selectpassbookpicbt.setOnClickListener(this);
        selectincomeslipbt.setOnClickListener(this);
        selectsignaturebt.setOnClickListener(this);



        //we r getting storage root reference here

        storage = FirebaseStorage.getInstance();

    }

    private void setAadharValues(StubAadhaarCustomVAR stubAadhaarCustomVAR) {
        consituencyet.setText(consituency);
        firstnameet.setText(stubAadhaarCustomVAR.getFirstName());
        middlenameet.setText(stubAadhaarCustomVAR.getMiddleName());
        lastnameet.setText(stubAadhaarCustomVAR.getLastName());
        dobet.setText(stubAadhaarCustomVAR.getDateofbirth());
        phonenoet.setText(stubAadhaarCustomVAR.getPhoneNo());
        aadharnoet.setText(stubAadhaarCustomVAR.getAadharNo());
        hosenoet.setText(stubAadhaarCustomVAR.getHoseno1());
        streetet.setText(stubAadhaarCustomVAR.getStreetorarea());
        postalet.setText(stubAadhaarCustomVAR.getPostalcode());
        cityet.setText(stubAadhaarCustomVAR.getCity());
        stateet.setText(stubAadhaarCustomVAR.getState());
        familyincomeet.setText(familyincome);
       // agespinner.setSelection(55);
        agespinner.setSelection(getAge(stubAadhaarCustomVAR.getDateofbirth()));

        switch (stubAadhaarCustomVAR.getGender()) {
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
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
            }
        }

        storageReference = storage.getReference().child(stubAadhaarCustomVAR.getAadharNo());

    }


    public int getAge(String s) {
        int _day = Integer.parseInt(s.substring(0, s.indexOf("/")));
        int _month = Integer.parseInt(s.substring(s.indexOf("/") + 1, s.lastIndexOf("/")));
        int _year = Integer.parseInt(s.substring(s.lastIndexOf("/") + 1, s.length()));
        GregorianCalendar cal = new GregorianCalendar();
        int y, m, d, a;
        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DAY_OF_MONTH);
        cal.set(_year, _month, _day);
        a = y - cal.get(Calendar.YEAR);
        if ((m < cal.get(Calendar.MONTH))
                || ((m == cal.get(Calendar.MONTH)) && (d < cal
                .get(Calendar.DAY_OF_MONTH)))) {
            --a;
        }
        if (a < 0)
            throw new IllegalArgumentException("Age < 0");
        return a;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.submitfillformbt: {
                if (awesomeValidation.validate() && !bankspinner.getSelectedItem().toString().equals("") && selectpiccb.isChecked() && passbookcb.isChecked() && payslipcb.isChecked() && signaturecb.isChecked()) {
                    if (Integer.parseInt(agespinner.getSelectedItem().toString()) >= 60 && Integer.parseInt(familyincomeet.getText().toString()) <= 100000) {
                       AlerDialogCeckBeforesubmission();
                    } else {
                        if (!(Integer.parseInt(agespinner.getSelectedItem().toString()) >= 60)) {
                            AlerDialogCriteria();
                        }
                        if (!(Integer.parseInt(familyincomeet.getText().toString()) <= 100000)) {
                            AlerDialogCriteria();
                        }
                    }

                    break;
                } else {
                    if (bankspinner.getSelectedItem().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Select Bank", Toast.LENGTH_LONG).show();
                        break;
                    } else if (!selectpiccb.isChecked()) {
                        Toast.makeText(getApplicationContext(), "Upload your Passport Size pic", Toast.LENGTH_LONG).show();
                        break;
                    } else if (!passbookcb.isChecked()) {
                        Toast.makeText(getApplicationContext(), "Attach your Passbook", Toast.LENGTH_LONG).show();
                        break;
                    } else if (!payslipcb.isChecked()) {
                        Toast.makeText(getApplicationContext(), "Attach your Income Slip", Toast.LENGTH_LONG).show();
                        break;
                    } else if (!signaturecb.isChecked()) {
                        Toast.makeText(getApplicationContext(), "Attach your Signature", Toast.LENGTH_LONG).show();
                        break;
                    } else {
                        Log.d("else", "else part reached");
                        break;
                    }
                }

            }
            case R.id.selectimgfillformbt: {
                try {
                    Intent photoPicker = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(photoPicker, RESULT_LOAD_PIC);

                } catch (Exception exp) {
                    Log.i("Error", exp.toString());
                }

                break;
            }


            case R.id.changeaddressfilformbt: {
                hosenoet.setInputType(InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS);
                streetet.setInputType(InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS);
                postalet.setInputType(InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS);
                cityet.setInputType(InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS);
                stateet.setInputType(InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS);
                break;

            }


            case R.id.selectpassbook_fillformbt: {
                try {
                    Intent photoPicker = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(photoPicker, RESULT_LOAD_PASSBOOK_PIC);

                } catch (Exception exp) {
                    Log.i("Error", exp.toString());
                }
                break;
            }


            case R.id.selectIncomslipbt_fillformbt: {
                try {
                    Intent photoPicker = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(photoPicker, RESULT_LOAD_INCOME_PIC);

                } catch (Exception exp) {
                    Log.i("Error", exp.toString());
                }
                break;
            }


            case R.id.selectsignature_fillformbt: {
                try {
                    Intent photoPicker = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(photoPicker, RESULT_LOAD_SIGNATURE);

                } catch (Exception exp) {
                    Log.i("Error", exp.toString());
                }
                break;
            }


            default: {
                Log.d("default bolck", "default in switch case");
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_LOAD_PIC: {
                if (resultCode == RESULT_OK && null != data) {
                    final Uri selectedImage = data.getData();

                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();

                    StorageReference sre=storageReference.child("userpic");
                    sre.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Glide.with(FIllform.this).load(selectedImage).into(picimageview);
                            selectpiccb.setChecked(true);
                            Toast.makeText(FIllform.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            progressDialog.dismiss();
                            Toast.makeText(FIllform.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });


                }
                break;
            }


            case RESULT_LOAD_PASSBOOK_PIC: {
                if (resultCode == RESULT_OK && null != data) {
                    final Uri selectedImage = data.getData();


                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();

                    StorageReference sre=storageReference.child("passbook");
                    sre.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Glide.with(FIllform.this).load(selectedImage).into(imageViewpassbook);
                            passbookcb.setChecked(true);
                            Toast.makeText(FIllform.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            progressDialog.dismiss();
                            Toast.makeText(FIllform.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
                }
                break;
            }

            case RESULT_LOAD_INCOME_PIC: {
                if (resultCode == RESULT_OK && null != data) {
                    final Uri selectedImage = data.getData();

                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();

                    StorageReference sre=storageReference.child("incomeslip");
                    sre.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Glide.with(FIllform.this).load(selectedImage).into(imageViewpayslip);
                            payslipcb.setChecked(true);
                            Toast.makeText(FIllform.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            progressDialog.dismiss();
                            Toast.makeText(FIllform.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });

                }
                break;
            }

            case RESULT_LOAD_SIGNATURE: {
                if (resultCode == RESULT_OK && null != data) {
                    final Uri selectedImage = data.getData();


                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();

                    StorageReference sre=storageReference.child("incomeslip");
                    sre.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Glide.with(FIllform.this).load(selectedImage).into(imageViewsignature);
                            signaturecb.setChecked(true);
                            Toast.makeText(FIllform.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            progressDialog.dismiss();
                            Toast.makeText(FIllform.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
                }
                break;
            }
            default: {
                Log.d("deafault", "reache default case");
            }


        }


    }


    // permission requesting code
    private void reqPermission() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 499);
        }
    }

    //AlertDialog FOR RECHECK BEFORE SUBMISSION


    private void AlerDialogCeckBeforesubmission()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               Toast.makeText(getApplicationContext(),"Suceess",Toast.LENGTH_LONG).show();
            }
        })
                .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                })
                .setCancelable(true)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setMessage("Please ReCheck EveryThing");
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }







    //ALerDialog For Showing Elligeble Criteria

    private void AlerDialogCriteria()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setPositiveButton("Eligibilty Criteria", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(FIllform.this,EligibilityActivity.class));
            }
        })
                .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                })
                .setCancelable(true)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setMessage("Please Read Eligibilty Criteria");
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }




    //End
}
