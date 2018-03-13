package com.codewarriors.hackathone.relaypension;

import android.graphics.Color;
import android.support.annotation.NonNull;
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
import com.codewarriors.hackathone.relaypension.customvariablesforparsing.StubAadhaarCustomVAR;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Insertdatatostub extends AppCompatActivity {

    EditText firstnameet,middlenameet,lastnameet,dobet,phonenoet,aadharnoet,hodenoet,localityet,posstalcodeet,cityet;
    RadioGroup radioGroup;
    Boolean radioGroupflag=false;//for checking radio button is clicked or not will be set in on checkedchange listner.
    RadioButton male,female,transender;
    Spinner statespinner,agespinner;

    Button submit;


    String gender;


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
        aadharnoet=findViewById(R.id.aadhaarnoet);
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
        awesomeValidation.addValidation(this,R.id.aadhaarnoet,"\\d{12}",R.string.invalid_aadhaar);
        //here house no means house no or buildin For Ex 2/1304 or Sai complex
        awesomeValidation.addValidation(this,R.id.housenoet,"[a-z A-Z 0-9][a-z A-Z 0-9 /  , .]*[-]*[a-z A-Z 0-9 /  , .]*",R.string.invalid_hoseno);
        //street for ex Buddhi Vihar
        awesomeValidation.addValidation(this,R.id.streetet,"[a-z A-Z][a-z A-Z , /]*",R.string.invalid_hoseno);
        //any 6 digit no
        awesomeValidation.addValidation(this,R.id.postalcodeet,"([0-9]{6})",R.string.invalid_postal);
        awesomeValidation.addValidation(this,R.id.cityet,"[a-z A-Z][a-z A-Z]*",R.string.invalid_city);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                radioGroupflag=true;

                switch (radioGroup.getCheckedRadioButtonId())
                {
                    case R.id.radiobuttonmale:
                    {
                        gender="Male";
                        break;
                    }
                    case R.id.radiobuttonfemale:
                    {
                        gender="Female";
                        break;
                    }
                    case R.id.radiotransgender:
                    {
                        gender="Transgender";
                        break;
                    }
                }

            }
        });




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //radiogroupflag vaue is to check weather user touched it or not(touched then ragiogroupflag=true else flase)
                //second && case STATE IS SELECTED OR NOT
                //3RD AGE IS SELECTED OR NOT
            if (awesomeValidation.validate()&&radioGroupflag&&
                      !statespinner.getSelectedItem().toString().equals("")&&
                      !agespinner.getSelectedItem().toString().equals(""))
              {

                  String first_name=firstnameet.getText().toString();
                  String middle_name=middlenameet.getText().toString();
                  String last_name=lastnameet.getText().toString();
                  String age=agespinner.getSelectedItem().toString();
                  String gen=gender;
                  String dateofbirth=dobet.getText().toString();
                  String phone_no=phonenoet.getText().toString();
                  String aadhar_no=aadharnoet.getText().toString();
                  String hoseno=hodenoet.getText().toString();
                  String street=localityet.getText().toString();
                  String postal=posstalcodeet.getText().toString();
                  String city=cityet.getText().toString();
                  String state=statespinner.getSelectedItem().toString();


                StubAadhaarCustomVAR stubAadhaarCustomVAR=new StubAadhaarCustomVAR(first_name,
                          middle_name,last_name,age,gen,dateofbirth,phone_no,aadhar_no,hoseno,street,
                          postal,city,state);

                  DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("stubofuid/");




                  mDatabase.child(aadhar_no).setValue(stubAadhaarCustomVAR).addOnCompleteListener(new OnCompleteListener<Void>() {
                      @Override
                      public void onComplete(@NonNull Task<Void> task) {
                          if (task.isSuccessful()) {
                              firstnameet.getText().clear();
                              middlenameet.getText().clear();
                              lastnameet.getText().clear();
                              dobet.getText().clear();
                              phonenoet.getText().clear();
                              aadharnoet.getText().clear();
                              hodenoet.getText().clear();
                              localityet.getText().clear();
                              posstalcodeet.getText().clear();
                              cityet.getText().clear();
                              radioGroup.clearCheck();
                              statespinner.setSelection(0);
                              agespinner.setSelection(0);



                              Toast.makeText(getApplicationContext(), "Sucess", Toast.LENGTH_LONG).show();
                          }
                          else
                          {
                              Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                          }
                      }
                  });




              }
                else
                {
                    if(!radioGroupflag)
                    {
                        Toast.makeText(getApplicationContext(),"Select Gender",Toast.LENGTH_LONG).show();
                    }

                    if(statespinner.getSelectedItem().toString().equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"Select State",Toast.LENGTH_LONG).show();
                    }

                    if(agespinner.getSelectedItem().toString().equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"Select Age",Toast.LENGTH_LONG).show();
                    }

                }

            }
        });
    }




}
