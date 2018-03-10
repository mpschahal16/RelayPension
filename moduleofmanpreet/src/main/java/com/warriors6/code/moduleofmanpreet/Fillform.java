package com.warriors6.code.moduleofmanpreet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class Fillform extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // Intent it=getIntent();
       // String adno=it.getExtras().getString("adharno",null);
        setContentView(R.layout.activity_fillform);
        String adno="499240755287";

        if(adno!=null)
        {
            fetchdetails();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Somthing wents wrong",Toast.LENGTH_LONG).show();
        }
    }

    private void fetchdetails() {
        Log.d("fetch","fetchdetails()");
    }
}
