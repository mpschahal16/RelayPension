package com.warriors6.code.moduleofmanpreet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Fillform extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // Intent it=getIntent();
       // String adno=it.getExtras().getString("adharno",null);
        String adno=null;
        if(adno!=null)
        {
            Toast.makeText(getApplicationContext(),adno,Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Somthing wents wrong",Toast.LENGTH_LONG).show();
        }
        setContentView(R.layout.activity_fillform);
    }
}
