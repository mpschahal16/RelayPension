package com.codewarriors.hackathone.relaypension;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class FIllform extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent it=getIntent();
        String adno=it.getExtras().getString("adharno",null);
        if(adno!=null)
        {
           seteverythingonlayout();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Somthing wents wrong",Toast.LENGTH_LONG).show();
        }
        setContentView(R.layout.activity_fillform);
    }

    private void seteverythingonlayout() {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
