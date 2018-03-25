package com.codewarriors.hackathone.relaypension;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.codewarriors.hackathone.relaypension.adminside.listallconspack.ListAllConstituency;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

      // startActivity(new Intent(Splash.this,Insertdatatostub.class));
       //finish();

       SharedPreferences prefs = getSharedPreferences("codewarriors", MODE_PRIVATE);
        String restoredText = prefs.getString("userid", null);
        String adminid=prefs.getString("adminid",null);
        if (restoredText != null) {

            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("UserState/");
            rootRef.child(restoredText).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {
                        String s = dataSnapshot.getValue().toString();
                        openactivity(s);
                    }
                    else
                    {
                        Toasty.error(Splash.this,"Unalbe to fetch user", Toast.LENGTH_LONG,true).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toasty.warning(Splash.this,"Check Your Internet", Toast.LENGTH_LONG,true).show();

                }
            });
        }
        else if(adminid!=null)
        {
            Intent intent = new Intent(this,ListAllConstituency.class);
            startActivity(intent);
        }
        else {
            new Thread() {
                public void run() {
                    int waited = 0;
                    while (waited < 1500) {
                        try {
                            Thread.sleep(100);
                            waited += 100;
                        }
                         catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    finish();

                    startActivity(new Intent(Splash.this, SignIn.class));
                }
            }.start();
        }
    }


    void openactivity(String s)
    {
        if(s.equals("0"))
        {
            startActivity(new Intent(Splash.this,Aadharverify.class));
        }
        else
        {
            startActivity(new Intent(Splash.this,StatusActivity.class));
        }
    }
}