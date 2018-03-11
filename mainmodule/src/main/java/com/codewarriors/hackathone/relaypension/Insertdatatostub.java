package com.codewarriors.hackathone.relaypension;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

public class Insertdatatostub extends AppCompatActivity {

    EditText firstnameet,middlenameet,lastnameet,dobet,phonenoet,aadharnoet,hodenoet,localityet,posstalcodeet,cityet;
    RadioGroup radioGroup;
    Spinner statespinner,agespinner;

    Button submit;

    AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertdatatostub);
        firstnameet=findViewById(R.id.first_nameet);
        middlenameet=findViewById(R.id.middle_nameet);
        lastnameet=findViewById(R.id.last_nameet);
        dobet=findViewById(R.id.dateofbirthet);
        phonenoet=findViewById(R.id.phonenoet);
        aadharnoet=findViewById(R.id.aadharnoet);
        hodenoet=findViewById(R.id.housenoet);
        localityet=findViewById(R.id.streetet);
        posstalcodeet=findViewById(R.id.postalcodeet);
        cityet=findViewById(R.id.cityet);
        radioGroup=findViewById(R.id.radiogroup);
        statespinner=findViewById(R.id.statespinner);
        agespinner=findViewById(R.id.spinnerage);
        submit=findViewById(R.id.submitbt);


        awesomeValidation=new AwesomeValidation(ValidationStyle.COLORATION);






        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
