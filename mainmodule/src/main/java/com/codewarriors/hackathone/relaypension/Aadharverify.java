package com.codewarriors.hackathone.relaypension;

import android.content.DialogInterface;
import android.hardware.ConsumerIrManager;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.codewarriors.hackathone.relaypension.customvariablesforparsing.ConstituencyHelperClass;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Aadharverify extends AppCompatActivity implements View.OnClickListener,DialogInterface.OnClickListener {
   EditText aadharnoet,consituencyet,anualfamilyincomeet,otpaadharverifyet;

   ArrayList<String> listofconsituency;
   ArrayList<Integer> listofmaxlimitinconsituency;


   TextInputLayout textInputLayout;

   AwesomeValidation awesomeValidation,otp;

   Button sendotptophonebt,verifyotpbt;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("stubofuid/");

    //Timeraa timeraa;

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
        listofmaxlimitinconsituency=c.getConstituencylimit();



        otpaadharverifyet.setVisibility(View.INVISIBLE);
        verifyotpbt.setVisibility(View.INVISIBLE);
        textInputLayout.setVisibility(View.INVISIBLE);
        sendotptophonebt.setOnClickListener(this);
        verifyotpbt.setOnClickListener(this);

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
                    int familysalery=Integer.parseInt(anualfamilyincomeet.getText().toString());
                    if(listofconsituency.contains(constituency)&&familysalery<=100000)
                    {

                        Toast.makeText(getApplicationContext(),"Sucess",Toast.LENGTH_LONG).show();

                    }
                    else
                    {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

                        alertDialogBuilder.setPositiveButton("xxxxxxxxxxx",this)
                                .setNegativeButton("Cancle", this)
                                .setCancelable(true)
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .setMessage("Message");

                        AlertDialog alertDialog = alertDialogBuilder.create();

                        alertDialog.show();


                    }
                }
               // sendotptoaddharinno();
                break;
            }
            case R.id.verifyaadotpbt:
            {
              //  verifyaddinno();
                break;
            }
        }

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }

   /* private void verifyaddinno() {

        String st=otpno.getText().toString().trim();
        if(TextUtils.isEmpty(st))
        {
            errorotp.setVisibility(View.VISIBLE);
            errorotp.setText(R.string.invalid_otp);


        }
        else
        {

            Intent it=new Intent(Aadharverify.this,FIllform.class);
            it.putExtra("adharno",adharnotonextactivity);
            timeraa.cancel(true);
            startActivity(it);
        }
    }*/

  /*  private void sendotptoaddharinno() {

        String st=adharno.getText().toString().trim();
        if(awesomeValidation.validate())
        {
            adharnotonextactivity=st;
            DatabaseReference mr=mDatabase.child(adharnotonextactivity);

            //PRESENTLY WE ARE EXTRACTING AADHAR NO FROM AADHAR NO AND SENDING IT TO FORM FILL ACTIVITY
            //BUT WE NEED TO FETCH PHONE NO ATTACHED TO THAT AADHAAR AND VERIFY THAT PHONE NO WITH OTP AND THEN SEND THAT AADHAR NO TO NEXT ACTIVITY
            mr.child("aadharNo").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {
                        String userpno = dataSnapshot.getValue().toString();
                        sendOTPtoPhoneNO(userpno);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Aadhar Does Not exsist in DB",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(),"Check Network/Db error",Toast.LENGTH_LONG).show();

                }
            });
        }
    }

    private void sendOTPtoPhoneNO(String userpno) {
        Intent intent=new Intent(Aadharverify.this,FIllform.class);
        intent.putExtra("phoneno",userpno);
        startActivity(intent);
    }

    @SuppressLint("StaticFieldLeak")
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
