package com.codewarriors.hackathone.relaypension;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
                startActivity(new Intent(Splash.this, PhoneLogin.class));
            }
        }.start();
    }
}