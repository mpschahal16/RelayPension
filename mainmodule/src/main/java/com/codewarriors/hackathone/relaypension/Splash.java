package com.codewarriors.hackathone.relaypension;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        startActivity(new Intent(Splash.this,Insertdatatostub.class));

      /*  SharedPreferences prefs = getSharedPreferences("codewarriors", MODE_PRIVATE);
        String restoredText = prefs.getString("userid", null);
        if (restoredText != null) {

            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("UserState/");
            rootRef.child(restoredText).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String s=dataSnapshot.getValue().toString();
                    openactivity(s);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("warriors","Error in splash");

                }
            });


        }



        else {


            new Thread() {
                public void run() {
                    int waited = 0;
                    while (waited < 1500) {
                        try {
                            Thread.sleep(100);
                            waited += 100;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    finish();

                    startActivity(new Intent(Splash.this, SignIn.class));
                }
            }.start();
        }
        */
    }


    void openactivity(String s)
    {
        switch (s)
        {
            case "0":
            {
                startActivity(new Intent(Splash.this,FIllform.class));
                break;
            }
            case "1":
            {
                Toast.makeText(getApplicationContext(),"gottostatus",Toast.LENGTH_LONG).show();
                break;
            }
        }
    }
}