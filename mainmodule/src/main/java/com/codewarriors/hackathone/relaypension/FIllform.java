package com.codewarriors.hackathone.relaypension;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.codewarriors.hackathone.relaypension.customvariablesforparsing.StubAadhaarCustomVAR;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FIllform extends AppCompatActivity {
    EditText firstnameet,middlenameet,lastnameet,dobet,phonenoet,aadharnoet,hosenoet,streetet,postalet,cityet;
    Spinner agespinner,statespinner;
    RadioGroup genderradiogroup;
    RadioButton maleradio,femaleradio,transgebderradio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fillform);
        seteverythingonlayout();
      /*  Intent it=getIntent();
        String adno=it.getExtras().getString("phoneno",null);*/
      String adno="499240755287";
        if(adno!=null)
        {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("stubofuid/");
            mDatabase.child(adno).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                    {
                     StubAadhaarCustomVAR stubAadhaarCustomVAR= dataSnapshot.getValue(StubAadhaarCustomVAR.class);
                     serAadharValues(stubAadhaarCustomVAR);
                    }
                    else
                    {

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(),"Cncelled",Toast.LENGTH_LONG).show();
                }
            });

        }
        else
        {
            Toast.makeText(getApplicationContext(),"Somthing wents wrong",Toast.LENGTH_LONG).show();
        }

    }

    private void serAadharValues(StubAadhaarCustomVAR stubAadhaarCustomVAR) {
        firstnameet.setText(stubAadhaarCustomVAR.getFirstName());
        middlenameet.setText(stubAadhaarCustomVAR.getMiddleName());
        lastnameet.setText(stubAadhaarCustomVAR.getLastName());
        dobet.setText(stubAadhaarCustomVAR.getDateofbirth());
        phonenoet.setText(stubAadhaarCustomVAR.getPhoneNo());
        aadharnoet.setText(stubAadhaarCustomVAR.getAadharNo());
        hosenoet.setText(stubAadhaarCustomVAR.getHoseno1());
        streetet.setText(stubAadhaarCustomVAR.getStreetorarea());
        postalet.setText(stubAadhaarCustomVAR.getPostalcode());
        cityet.setText(stubAadhaarCustomVAR.getCity());
        agespinner.setSelection(Integer.parseInt(stubAadhaarCustomVAR.getAge()));
        //statespinner.;
     /*   switch (stubAadhaarCustomVAR.getGender())
        {
            case "Male":
            {
                maleradio.setChecked(true);
            }
            case "Female":
            {
                femaleradio.setChecked(true);
            }
            case "Transgender":
            {
                transgebderradio.setChecked(true);
            }
        }*/

    }

    private void seteverythingonlayout() {
        firstnameet=findViewById(R.id.first_nameetfillform);
        middlenameet=findViewById(R.id.middle_nameetfillform);
        lastnameet=findViewById(R.id.last_nameetfillform);
        dobet=findViewById(R.id.dateofbirth_fillform);
        phonenoet=findViewById(R.id.phonenoetfillform);
        aadharnoet=findViewById(R.id.aadharnoetfillform);
        hosenoet=findViewById(R.id.houseno_etfillfrom);
        streetet=findViewById(R.id.street_etfillform);
        postalet=findViewById(R.id.postalcodeetfillform);
        cityet=findViewById(R.id.cityetfillform);
        agespinner=findViewById(R.id.agespinnerfillform);
        statespinner=findViewById(R.id.statespinnerfillform);
        genderradiogroup=findViewById(R.id.genderrgfillform);
        maleradio=findViewById(R.id.malerbfillform);
        femaleradio=findViewById(R.id.femalerbfillform);
        transgebderradio=findViewById(R.id.transgenderrbfillform);
        agespinner.setEnabled(false);
        statespinner.setEnabled(false);
        maleradio.setEnabled(false);
        femaleradio.setEnabled(false);
        transgebderradio.setEnabled(false);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
