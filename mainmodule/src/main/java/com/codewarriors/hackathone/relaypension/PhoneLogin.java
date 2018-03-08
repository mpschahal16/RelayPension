package com.codewarriors.hackathone.relaypension;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PhoneLogin extends AppCompatActivity implements View.OnClickListener {
    EditText phonenoet,otpet;
    Button sendotp,verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonelogin);
        phonenoet=findViewById(R.id.phoneloginet);
        otpet=findViewById(R.id.otpet);
        sendotp=findViewById(R.id.sendotpbt);
        verify=findViewById(R.id.verifybt);
        otpet.setVisibility(View.INVISIBLE);
        verify.setVisibility(View.INVISIBLE);

        sendotp.setOnClickListener(this);
        verify.setOnClickListener(this);
    }

    public void sendotp()
    {
        otpet.setVisibility(View.VISIBLE);
        verify.setVisibility(View.VISIBLE);
    }


    public void verify()
    {

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id) {
            case R.id.sendotpbt: {
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
}
