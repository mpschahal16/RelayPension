package com.codewarriors.hackathone.relaypension;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

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
}