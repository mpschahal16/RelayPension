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

public class Aadharverify extends AppCompatActivity implements View.OnClickListener {
    EditText adharno,otpno;
    TextView errorsend,errorotp,otphead;
    Button sendotp,verify;

    String adharnotonextactivity;

    Timeraa timeraa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadharverify);

        adharno=findViewById(R.id.aadharnoet);
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
        if(TextUtils.isEmpty(st)||st.length()<12)
        {

            errorsend.setVisibility(View.VISIBLE);
            errorsend.setText(R.string.invalid_mobile_no);

        }
        else
        {
            sendotp.setVisibility(View.INVISIBLE);
            errorsend.setVisibility(View.VISIBLE);
            adharnotonextactivity=st;
            timeraa=new Timeraa();
            timeraa.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"params");

            verify.setVisibility(View.VISIBLE);
            otphead.setVisibility(View.VISIBLE);
            otpno.setVisibility(View.VISIBLE);
        }
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
