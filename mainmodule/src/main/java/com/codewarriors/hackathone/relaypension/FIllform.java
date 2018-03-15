package com.codewarriors.hackathone.relaypension;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.codewarriors.hackathone.relaypension.customvariablesforparsing.StubAadhaarCustomVAR;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class FIllform extends AppCompatActivity implements View.OnClickListener {
    EditText firstnameet,middlenameet,lastnameet,dobet,phonenoet,aadharnoet,hosenoet,streetet,postalet,cityet;
    Spinner agespinner,statespinner;
    RadioGroup genderradiogroup;
    RadioButton maleradio,femaleradio,transgebderradio;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fillform);
        seteverythingonlayout();
       Intent it=getIntent();
        String adno=it.getExtras().getString("phoneno",null);
        if(adno!=null)
        {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("stubofuid/");
            mDatabase.child(adno).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                    {
                     StubAadhaarCustomVAR stubAadhaarCustomVAR= dataSnapshot.getValue(StubAadhaarCustomVAR.class);
                     setAadharValues(stubAadhaarCustomVAR);
                    }
                    else
                    {
                        startActivity(new Intent(FIllform.this,StubNoReturn.class));

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

    private void setAadharValues(StubAadhaarCustomVAR stubAadhaarCustomVAR) {
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
        agespinner.setSelection(getAge(stubAadhaarCustomVAR.getDateofbirth()));

        switch (stubAadhaarCustomVAR.getGender())
        {
            case "Male":
            {
                maleradio.setChecked(true);
                break;
            }
            case "Female":
            {
                femaleradio.setChecked(true);
                break;
            }
            case "Transgender":
            {
                transgebderradio.setChecked(true);
                break;
            }
            default:
            {
                Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();
            }
        }

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




    public int getAge(String s) {

        int _day=Integer.parseInt(s.substring(0,s.indexOf("/")));
        int _month=Integer.parseInt(s.substring(s.indexOf("/")+1,s.lastIndexOf("/")));
        int _year=Integer.parseInt(s.substring(s.lastIndexOf("/")+1,s.length()));
        GregorianCalendar cal = new GregorianCalendar();
        int y, m, d, a;

        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DAY_OF_MONTH);
        cal.set(_year, _month, _day);
        a = y - cal.get(Calendar.YEAR);
        if ((m < cal.get(Calendar.MONTH))
                || ((m == cal.get(Calendar.MONTH)) && (d < cal
                .get(Calendar.DAY_OF_MONTH)))) {
            --a;
        }
        if(a < 0)
            throw new IllegalArgumentException("Age < 0");
        return a;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

        }
    }
}
