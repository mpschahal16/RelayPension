package com.codewarriors.hackathone.relaypension;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignIn extends AppCompatActivity implements View.OnClickListener{
    EditText phonenoet,otpet;
    TextView errorinsend,errorinverify;
    Button sendotp,verify;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        phonenoet=findViewById(R.id.phoneloginet);
        otpet=findViewById(R.id.otpaadet);
        sendotp=findViewById(R.id.sendaddotpbt);
        verify=findViewById(R.id.verifybt);
        errorinsend=findViewById(R.id.sendotperrorret);
        errorinverify=findViewById(R.id.vrifyotpeterror);
        otpet.setVisibility(View.INVISIBLE);
        verify.setVisibility(View.INVISIBLE);
        sendotp.setOnClickListener(this);
        verify.setOnClickListener(this);
        errorinsend.setVisibility(View.INVISIBLE);
        errorinverify.setVisibility(View.INVISIBLE);


    }

    public void sendotp()
    {
        String st=phonenoet.getText().toString().trim();
        if(TextUtils.isEmpty(st)||st.length()<10)
        {
            errorinsend.setVisibility(View.VISIBLE);
            errorinsend.setText(R.string.invalid_mobile_no);

        }
        else
        {
            sendotp.setVisibility(View.INVISIBLE);
            errorinsend.setVisibility(View.VISIBLE);
            timer=new Timer();
            timer.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"params");

            otpet.setVisibility(View.VISIBLE);
            verify.setVisibility(View.VISIBLE);
        }

    }

    public void verify()
    {
        String st=otpet.getText().toString().trim();
        if(TextUtils.isEmpty(st))
        {
            errorinverify.setVisibility(View.VISIBLE);
            errorinverify.setText(R.string.invalid_otp);


        }
        else
        {

            Intent it=new Intent(SignIn.this,Aadharverify.class);
            timer.cancel(true);
            startActivity(it);
        }

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id)
        {
            case R.id.sendaddotpbt:
            {
                sendotp();
                break;
            }
            case R.id.verifybt:
            {
                verify();
                break;
            }
        }

    }

    @SuppressLint("StaticFieldLeak")
    private class Timer extends AsyncTask<String, Integer, String> {
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
            errorinsend.setText(String.valueOf("Retry in "+x+" sec"));
            if(x==1)
            {
                sendotp.setVisibility(View.VISIBLE);
                sendotp.setText(R.string.send_again);

            }

        }
    }
}
