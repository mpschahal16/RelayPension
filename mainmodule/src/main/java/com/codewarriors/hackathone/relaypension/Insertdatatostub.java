package com.codewarriors.hackathone.relaypension;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.util.regex.Pattern;

public class Insertdatatostub extends AppCompatActivity {

    EditText firstnameet,middlenameet,lastnameet,dobet,phonenoet,aadharnoet,hodenoet,localityet,posstalcodeet,cityet;
    RadioGroup radioGroup;
    Boolean radioGroupflag=false;//for checking radio button is clicked or not will be set in on checkedchange listner.
    RadioButton male,female,transender;
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

        male=findViewById(R.id.radiobuttonmale);
        female=findViewById(R.id.radiobuttonfemale);
        transender=findViewById(R.id.radiotransgender);

        awesomeValidation=new AwesomeValidation(ValidationStyle.COLORATION);


        awesomeValidation.addValidation(this,R.id.first_nameet, "[a-z A-Z][a-z A-z]*",R.string.invalid_name);
        awesomeValidation.addValidation(this,R.id.middle_nameet,"[a-z A-Z]*",R.string.invalid_name);
        awesomeValidation.addValidation(this,R.id.last_nameet,"[a-z A-Z][a-z A-Z]*",R.string.invalid_name);
        awesomeValidation.addValidation(this,R.id.dateofbirthet, "([0-9]{2})/([0-9]{2})/([0-9]{4})",R.string.invalid_dob);
        awesomeValidation.addValidation(this,R.id.phonenoet,Patterns.PHONE,R.string.invalid_mobile_no);
        //12 digit aadhaar
        awesomeValidation.addValidation(this,R.id.aadharnoet,"\\d{12}",R.string.invalid_aadhaar);
        //here house no means house no or buildin For Ex 2/1304 or Sai complex
        awesomeValidation.addValidation(this,R.id.housenoet,"[a-z A-Z 0-9 / - , .]*",R.string.invalid_hoseno);
        //street for ex Buddhi Vihar
        awesomeValidation.addValidation(this,R.id.streetet,"[a-z A-Z , /]*",R.string.invalid_hoseno);
        //any 6 digit no
        awesomeValidation.addValidation(this,R.id.postalcodeet,"//d{6}",R.string.invalid_postal);
        awesomeValidation.addValidation(this,R.id.cityet,"[a-z A-Z]*",R.string.invalid_city);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                radioGroupflag=true;

            }
        });




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (awesomeValidation.validate()&&radioGroupflag)
                {
                    Toast.makeText(getApplicationContext(),"Sucess",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Invalid Entries",Toast.LENGTH_LONG).show();

                }

            }
        });
    }




}
