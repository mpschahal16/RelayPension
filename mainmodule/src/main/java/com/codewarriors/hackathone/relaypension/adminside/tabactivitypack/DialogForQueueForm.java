package com.codewarriors.hackathone.relaypension.adminside.tabactivitypack;

import android.app.Activity;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codewarriors.hackathone.relaypension.R;
import com.codewarriors.hackathone.relaypension.StatusActivity;
import com.codewarriors.hackathone.relaypension.customvariablesforparsing.FormPushPullCustomVAR;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by hp on 22-03-2018.
 */

public class DialogForQueueForm extends Dialog {

    private Activity activity;

    private ImageView picimageview,imageViewpassbook,imageViewpayslip,imageViewsignature;

    private FormPushPullCustomVAR formPushPullCustomVAR;



    private StorageReference storageRef =
            FirebaseStorage.getInstance().getReference();
    private EditText consituencyet, firstnameet, middlenameet, lastnameet, dobet, phonenoet, aadharnoet, hosenoet, streetet, postalet, cityet, stateet, familyincomeet, accountnoet,banknamert;
    private Spinner agespinner;
    private RadioButton maleradio, femaleradio, transgebderradio;



    DialogForQueueForm(Activity activity, FormPushPullCustomVAR formPushPullCustomVAR) {
        super(activity);
        this.activity=activity;
        this.formPushPullCustomVAR=formPushPullCustomVAR;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.custom_dia_forqueueform);
        // image view
        picimageview = findViewById(R.id.imageviewfillform);
        imageViewpassbook = findViewById(R.id.imageViewpassbook);
        imageViewpayslip = findViewById(R.id.imageviewincomslip);
        imageViewsignature = findViewById(R.id.imageViewsgnature);

        // all et(Edittext) searialwise from layout
        consituencyet = findViewById(R.id.consituency_etfillform);
        firstnameet = findViewById(R.id.first_nameetfillform);
        middlenameet = findViewById(R.id.middle_nameetfillform);
        lastnameet = findViewById(R.id.last_nameetfillform);
        dobet = findViewById(R.id.dateofbirth_fillform);
        phonenoet = findViewById(R.id.phonenoetfillform);
        aadharnoet = findViewById(R.id.aadharnoetfillform);
        hosenoet = findViewById(R.id.houseno_etfillfrom);
        streetet = findViewById(R.id.street_etfillform);
        postalet = findViewById(R.id.postalcodeetfillform);
        cityet = findViewById(R.id.cityetfillform);
        stateet = findViewById(R.id.stateetfillform);
        accountnoet = findViewById(R.id.aacountnofillformet);
        familyincomeet = findViewById(R.id.familyincomefillformet);
        banknamert=findViewById(R.id.banknamespinner);


        // radio group and radio button
        maleradio = findViewById(R.id.malerbfillform);
        femaleradio = findViewById(R.id.femalerbfillform);
        transgebderradio = findViewById(R.id.transgenderrbfillform);


        //spinner in form layout
        agespinner = findViewById(R.id.agespinnerfillform);
        agespinner.setEnabled(false);




        setEverything();




    }

    private void setEverything() {

        consituencyet.setText(formPushPullCustomVAR.getConstituency());
        firstnameet.setText(formPushPullCustomVAR.getFirstName());
        middlenameet.setText(formPushPullCustomVAR.getMiddleName());
        lastnameet.setText(formPushPullCustomVAR.getLastName());
        dobet.setText(formPushPullCustomVAR.getDateofbirth());
        phonenoet.setText(formPushPullCustomVAR.getPhoneNo());
        aadharnoet.setText(formPushPullCustomVAR.getAadharNo());
        hosenoet.setText(formPushPullCustomVAR.getHoseno1());
        streetet.setText(formPushPullCustomVAR.getStreetorarea());
        postalet.setText(formPushPullCustomVAR.getPostalcode());
        cityet.setText(formPushPullCustomVAR.getCity());
        stateet.setText(formPushPullCustomVAR.getState());
        familyincomeet.setText(formPushPullCustomVAR.getFamilyincome());
        agespinner.setSelection(Integer.parseInt(formPushPullCustomVAR.getAge()));
        switch (formPushPullCustomVAR.getGender()) {
            case "Male": {
                maleradio.setChecked(true);
                break;
            }
            case "Female": {
                femaleradio.setChecked(true);
                break;
            }
            case "Transgender": {
                transgebderradio.setChecked(true);
                break;
            }
            default: {
               Log.d("rrrrrrrrrrrrr","error in gender");
            }
        }

        accountnoet.setText(formPushPullCustomVAR.getBankaccountno());
        banknamert.setText(formPushPullCustomVAR.getBankname());



        storageRef.child(formPushPullCustomVAR.getAadharNo()+"/userpic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(activity).load(uri).into(picimageview);

            }


        });

        storageRef.child(formPushPullCustomVAR.getAadharNo()+"/incomeslip").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(activity).load(uri).into(imageViewpayslip);

            }
        });

        storageRef.child(formPushPullCustomVAR.getAadharNo()+"/passbook").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(activity).load(uri).into(imageViewpassbook);

            }
        });
        storageRef.child(formPushPullCustomVAR.getAadharNo()+"/signature").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(activity).load(uri).into(imageViewsignature);

            }
        });
    }

}
