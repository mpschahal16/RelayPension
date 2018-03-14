package com.codewarriors.hackathone.relaypension;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Aadharverify extends AppCompatActivity implements View.OnClickListener {
    EditText adharno,otpno;
    TextView errorsend,errorotp,otphead;
    Button sendotp,verify;

    String adharnotonextactivity;

    AwesomeValidation awesomeValidation;

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("stubofuid/");

    Timeraa timeraa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadharverify);

        adharno=findViewById(R.id.aadharnoetinverify);
        otpno=findViewById(R.id.otpaadet);
        errorsend=findViewById(R.id.aadharerrortv);
        errorotp=findViewById(R.id.verifotptverror);
        sendotp=findViewById(R.id.sendaddotpbt);
        verify=findViewById(R.id.verifyaadotpbt);

        otphead=findViewById(R.id.otpaad);

        errorsend.setVisibility(View.INVISIBLE);
        errorotp.setVisibility(View.INVISIBLE);
        verify.setVisibility(View.INVISIBLE);
        otphead.setVisibility(View.VISIBLE);
        otpno.setVisibility(View.INVISIBLE);
        otphead.setVisibility(View.INVISIBLE);

        sendotp.setOnClickListener(this);
        verify.setOnClickListener(this);


        awesomeValidation=new AwesomeValidation(ValidationStyle.COLORATION);
        awesomeValidation.addValidation(this,R.id.aadharnoetinverify,"\\d{12}",R.string.invalid_aadhaar);
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
                sendotptoaddharinno();
                break;
            }
            case R.id.verifyaadotpbt:
            {
                verifyaddinno();
                break;
            }
        }

    }

    private void verifyaddinno() {

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
    }

    private void sendotptoaddharinno() {

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
    }
}
