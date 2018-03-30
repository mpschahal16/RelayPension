package com.codewarriors.hackathone.relaypension;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.codewarriors.hackathone.relaypension.customvariablesforparsing.ConstituencyHelperClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.msg91.sendotp.library.SendOtpVerification;
import com.msg91.sendotp.library.Verification;
import com.msg91.sendotp.library.VerificationListener;

import java.util.ArrayList;


public class Aadharverify extends AppCompatActivity implements View.OnClickListener,VerificationListener{
   EditText aadharnoet,consituencyet,anualfamilyincomeet,otpaadharverifyet;

   ArrayList<String> listofconsituency;

   String adharno,constituency,salary;
   int familyanylin;


   TextInputLayout textInputLayout;

   AwesomeValidation awesomeValidation,otp;

   Button sendotptophonebt,verifyotpbt;
   DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("stubofuid/");
   DatabaseReference userstateconsref=FirebaseDatabase.getInstance().getReference("userstatecons/");


    ProgressDialog progressDialog;
   // CatLoadingView mViewdddd;
   //Timeraa timeraa;

    Verification msg69var;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadharverify);

        aadharnoet=findViewById(R.id.aadharnoetinverify);
        consituencyet=findViewById(R.id.constituebcyaadharveriet);
        anualfamilyincomeet=findViewById(R.id.anualincomeaadharveri);
        otpaadharverifyet=findViewById(R.id.otpaadverifyet);
        textInputLayout=findViewById(R.id.forthmaterial);

        sendotptophonebt=findViewById(R.id.sendaddotpbt);
        verifyotpbt=findViewById(R.id.verifyaadotpbt);

        ConstituencyHelperClass c=new ConstituencyHelperClass();
        listofconsituency=c.getConstituency();
        otpaadharverifyet.setVisibility(View.INVISIBLE);
        verifyotpbt.setVisibility(View.INVISIBLE);
        textInputLayout.setVisibility(View.INVISIBLE);
        sendotptophonebt.setOnClickListener(this);
        verifyotpbt.setOnClickListener(this);



         progressDialog=new ProgressDialog(this);

      //  mViewdddd = new CatLoadingView();




        awesomeValidation=new AwesomeValidation(ValidationStyle.COLORATION);
        otp=new AwesomeValidation(ValidationStyle.COLORATION);

        awesomeValidation.addValidation(this,R.id.aadharnoetinverify,"\\d{12}",R.string.invalid_aadhaar);
        awesomeValidation.addValidation(this,R.id.constituebcyaadharveriet, "[a-z A-Z][a-z A-z]*",R.string.invalid_name);
        awesomeValidation.addValidation(this,R.id.anualincomeaadharveri, "[0-9][0-9]*",R.string.invalid_name);
        awesomeValidation.addValidation(this,R.id.verifyaadotpbt,"\\d{4}",R.string.invalid_otp);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.sendaddotpbt:
            {
                if(awesomeValidation.validate())
                {
                    String constituency=consituencyet.getText().toString().toUpperCase();
                    salary=anualfamilyincomeet.getText().toString();
                    int familysalery=Integer.parseInt(salary);
                    if(listofconsituency.contains(constituency)&&familysalery<=100000)
                    {
                        checkinfoinstub();
                    }
                    else
                    {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                        alertDialogBuilder.setPositiveButton("Eligibilty Criteria", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(Aadharverify.this,EligibilityActivity.class));
                            }
                        })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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
                }
                break;
            }
            case R.id.verifyaadotpbt:
            {
                if(otp.validate()) {
                    verifyaddinno();
                }
                break;
            }
        }

    }


   private void verifyaddinno() {
       String s = otpaadharverifyet.getText().toString();

     /*  mViewdddd.setCanceledOnTouchOutside(false);
       mViewdddd.setText("Verifying...");
       mViewdddd.show(getSupportFragmentManager(),"");*/

       progressDialog.setMessage("Verifying...");
       progressDialog.setCancelable(false);
       progressDialog.show();
       msg69var.verify(s);
    }

    private void checkinfoinstub() {
        final String st=aadharnoet.getText().toString().trim();
        constituency= consituencyet.getText().toString();
        familyanylin=Integer.parseInt(anualfamilyincomeet.getText().toString());
        final DatabaseReference mr=mDatabase.child(st);

       /* mViewdddd.setCanceledOnTouchOutside(false);
        mViewdddd.setText("Checking Info");
        mViewdddd.show(getSupportFragmentManager(),"");*/

       progressDialog.setMessage("Checking User Info");
       progressDialog.setCancelable(false);
        progressDialog.show();


        userstateconsref.child(aadharnoet.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshotx) {

                if(dataSnapshotx.exists()) {

                   // mViewdddd.dismiss();
                    progressDialog.dismiss();
                   // Toasty.warning(getApplicationContext(),"Aadhar Already Used",Toast.LENGTH_LONG,true).show();
                    Toast.makeText(getApplicationContext(),"Aadhar Already Used",Toast.LENGTH_LONG).show();

                }
                else
                {

                    mr.child("phoneNo").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String userpno = dataSnapshot.getValue().toString();
                                adharno = st;
                                sendOTPtoPhoneNO(userpno);
                            } else {
                              //  mViewdddd.dismiss();
                                progressDialog.dismiss();
                               // Toasty.warning(getApplicationContext(), "Aadhar Does Not exsist in DB", Toast.LENGTH_LONG,true).show();
                                Toast.makeText(getApplicationContext(),"Aadhar Does Not exist in DataBase",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                           // Toasty.error(getApplicationContext(), "Check Network/Db error", Toast.LENGTH_LONG,true).show();

                            Toast.makeText(getApplicationContext(),"Error"+databaseError.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                   // mViewdddd.dismiss();
                   progressDialog.dismiss();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }



    //msg 91 code for otp verification of aadhar
    private void sendOTPtoPhoneNO(String userpno) {

        msg69var = SendOtpVerification.createSmsVerification
                (SendOtpVerification
                        .config("+91" + userpno)
                        .context(this)
                        .autoVerification(true)
                        .build(), this);
        msg69var.initiate();

    }

    @Override
    public void onInitiated(String response) {
        otpaadharverifyet.setVisibility(View.VISIBLE);
        verifyotpbt.setVisibility(View.VISIBLE);
        textInputLayout.setVisibility(View.VISIBLE);
       // mViewdddd.dismiss();
        progressDialog.dismiss();
       // Toasty.success(getApplication(),"Otp send to Aadhar linked Mobile",Toast.LENGTH_LONG,true).show();

        Toast.makeText(getApplication(),"OTP sent to Aadhar Linked Mobile",Toast.LENGTH_LONG).show();
        Log.d("msg69","onInitiated"+response);
    }

    @Override
    public void onInitiationFailed(Exception paramException) {
      //  Toasty.error(getApplication(),"Check ur Network",Toast.LENGTH_LONG,true).show();

        Toast.makeText(getApplication(),"Check Your Network Connection",Toast.LENGTH_LONG).show();
        Log.d("msg69","onInitiatedfailed");

    }

    @Override
    public void onVerified(String response) {
        Log.d("msg69","onverified");
       // mViewdddd.dismiss();
        progressDialog.dismiss();
        Intent intent = new Intent(Aadharverify.this, FIllform.class);
        intent.putExtra("aadharno", adharno);
        intent.putExtra("constituency",constituency);
        intent.putExtra("salary",salary);
        startActivity(intent);
    }

    @Override
    public void onVerificationFailed(Exception paramException) {
       // mViewdddd.dismiss();
        progressDialog.dismiss();
     //   Toasty.error(getApplicationContext(),"Verification Failed",Toast.LENGTH_LONG,true).show();
        Toast.makeText(getApplicationContext(),"Verification Failed",Toast.LENGTH_LONG).show();
        Log.d("msg69","onverifiedfailed");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.sign_out:
                SharedPreferences.Editor editor = getSharedPreferences("codewarriors", MODE_PRIVATE).edit();
                editor.putString("userid", null);
                editor.apply();
                Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                if (i != null) {
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                }
                finish();
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

   /* @SuppressLint("StaticFieldLeak")
    private class Timeraa extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            int i=60;
            while (i>0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i=i-1;
                publishProgress(i);
            }

            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            int x=values[0];
            errorsend.setText(String.valueOf("Retry in "+x+" sec"));
            if(x==1)
            {

                sendotp.setVisibility(View.VISIBLE);
                errorsend.setText(R.string.retry);
                sendotp.setText(R.string.send_again);

            }

        }
    }*/
}
