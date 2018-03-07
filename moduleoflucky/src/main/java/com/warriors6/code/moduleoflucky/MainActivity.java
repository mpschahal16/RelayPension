package com.warriors6.code.moduleoflucky;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread splash = new Thread()
        {public void run()
        {try {
            Thread.sleep(3500);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            startActivity(new Intent(MainActivity.this,AppCompatActivity.class));
            finish();
        }
        }
        };
        splash.start();

    }
}