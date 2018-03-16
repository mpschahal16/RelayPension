package com.codewarriors.hackathone.relaypension;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class FIllform extends AppCompatActivity implements View.OnClickListener {
    EditText firstnameet,middlenameet,lastnameet,dobet,phonenoet,aadharnoet,hosenoet,streetet,postalet,cityet,stateet,familyincomeet,accountnoet;
    Spinner agespinner;
    RadioGroup genderradiogroup;
    RadioButton maleradio,femaleradio,transgebderradio;


    Button submit;


    String adno,consituency,familyincome;

    AwesomeValidation awesomeValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fillform);
        seteverythingonlayout();
        Intent it=getIntent();
       /* adno="499240755287";
        consituency="A";
        familyincome="80000";*/

        adno=it.getExtras().getString("aadharno",null);
        consituency=it.getExtras().getString("constituency",null);
        familyincome=it.getExtras().getString("salary",null);
        if(adno!=null&&consituency!=null&&familyincome!=null)
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
                    startActivity(new Intent(FIllform.this,StubNoReturn.class));
                }
            });

        }
        else
        {
            startActivity(new Intent(FIllform.this,StubNoReturn.class));
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
        stateet.setText(stubAadhaarCustomVAR.getState());
        familyincomeet.setText(familyincome);
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
        genderradiogroup=findViewById(R.id.genderrgfillform);
        maleradio=findViewById(R.id.malerbfillform);
        femaleradio=findViewById(R.id.femalerbfillform);
        transgebderradio=findViewById(R.id.transgenderrbfillform);
        stateet=findViewById(R.id.stateetfillform);
        accountnoet=findViewById(R.id.aacountnofillformet);
        familyincomeet=findViewById(R.id.familyincomefillformet);
        submit=findViewById(R.id.submitfillform);
        agespinner.setEnabled(false);

        awesomeValidation=new AwesomeValidation(ValidationStyle.COLORATION);


        awesomeValidation.addValidation(this,R.id.first_nameetfillform, "[a-z A-Z][a-z A-z]*",R.string.invalid_name);
        awesomeValidation.addValidation(this,R.id.middle_nameetfillform,"[a-z A-Z]*",R.string.invalid_name);
        awesomeValidation.addValidation(this,R.id.last_nameetfillform,"[a-z A-Z][a-z A-Z]*",R.string.invalid_name);
        awesomeValidation.addValidation(this,R.id.dateofbirth_fillform, "([0-9]{2})/([0-9]{2})/([0-9]{4})",R.string.invalid_dob);
        awesomeValidation.addValidation(this,R.id.phonenoetfillform, Patterns.PHONE,R.string.invalid_mobile_no);
        //12 digit aadhaar
        awesomeValidation.addValidation(this,R.id.aadharnoetfillform,"\\d{12}",R.string.invalid_aadhaar);
        //here house no means house no or buildin For Ex 2/1304 or Sai complex
        awesomeValidation.addValidation(this,R.id.houseno_etfillfrom,"[a-z A-Z 0-9][a-z A-Z 0-9 /  , .]*[-]*[a-z A-Z 0-9 /  , .]*",R.string.invalid_hoseno);
        //street for ex Buddhi Vihar
        awesomeValidation.addValidation(this,R.id.street_etfillform,"[a-z A-Z][a-z A-Z , /]*",R.string.invalid_hoseno);
        //any 6 digit no
        awesomeValidation.addValidation(this,R.id.postalcodeetfillform,"([0-9]{6})",R.string.invalid_postal);
        awesomeValidation.addValidation(this,R.id.cityetfillform,"[a-z A-Z][a-z A-Z]*",R.string.invalid_city);
        awesomeValidation.addValidation(this,R.id.stateetfillform,"[a-z A-Z][a-z A-Z]*",R.string.invalid_state);
        awesomeValidation.addValidation(this,R.id.aacountnofillformet,"[0-9][0-9]*",R.string.invalid_accountnp);
        awesomeValidation.addValidation(this,R.id.familyincomefillformet,"[0-9][0-9]*",R.string.invalid);

        submit.setOnClickListener(this);
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
            case R.id.submitfillform:
            {
                if(awesomeValidation.validate())
                {
                    Toast.makeText(getApplicationContext(),"Sucess",Toast.LENGTH_LONG).show();
                }
                break;
            }

        }
    }
}
