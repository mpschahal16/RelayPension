package com.codewarriors.hackathone.relaypension.adminside.tabactivitypack;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codewarriors.hackathone.relaypension.R;
import com.codewarriors.hackathone.relaypension.customvariablesforparsing.FormPushPullCustomVAR;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;



/**
 * Created by hp on 22-03-2018.
 */

public class DialogOfFormWithPdf extends Dialog {

    private Activity activity;

    String which;

    private ImageView picimageview,imageViewpassbook,imageViewpayslip,imageViewsignature;

    private FormPushPullCustomVAR formPushPullCustomVAR;

    Button savetopdf;

    ScrollView sv;





    private StorageReference storageRef =
            FirebaseStorage.getInstance().getReference();
    private EditText consituencyet, firstnameet, middlenameet, lastnameet, dobet, phonenoet, aadharnoet, hosenoet, streetet, postalet, cityet, stateet, familyincomeet, accountnoet,banknamert;
    private Spinner agespinner;
    private RadioButton maleradio, femaleradio, transgebderradio;



    DialogOfFormWithPdf(Activity activity, FormPushPullCustomVAR formPushPullCustomVAR,String which) {
        super(activity);
        this.activity=activity;
        this.formPushPullCustomVAR=formPushPullCustomVAR;
        this.which=which;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.custom_dia_for_accepted_rejected);
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
        sv=findViewById(R.id.scroolview);

       // savetopdf=findViewById(R.id.savetopdf);


        // radio group and radio button
        maleradio = findViewById(R.id.malerbfillform);
        femaleradio = findViewById(R.id.femalerbfillform);
        transgebderradio = findViewById(R.id.transgenderrbfillform);


        //spinner in form layout
        agespinner = findViewById(R.id.agespinnerfillform);
        agespinner.setEnabled(false);




      /*  savetopdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createpdfandsaveit();

                dismiss();



            }
        });*/









        setEverything();




    }

    private void createpdfandsaveit() {



        //First Check if the external storage is writable
        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            //Toasty.error(getContext(),"Storage Not Mountd",Toast.LENGTH_LONG,true).show();
            Toast.makeText(getContext(),"Storage Not Mountd",Toast.LENGTH_LONG).show();
            return;
        }


        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        {

            Bitmap bitmap = getBitmapFromView(sv,sv.getChildAt(0).getHeight(),sv.getChildAt(0).getWidth());
            write(bitmap);

            Log.e("callPhone: ", "permission" );
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            Toast.makeText(activity, "need permission", Toast.LENGTH_SHORT).show();
        }




    }

    private Bitmap getBitmapFromView(View view,int height,int width) {
        Bitmap bitmap = Bitmap.createBitmap(width, height,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }


    public Boolean write(Bitmap bitmap) {


        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();


        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#ffffff"));
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);


        String targetPdf;
        // write the document content
        if(which.equals("1"))
        {
            String dir="/sdcard/Relay_Pension/"+formPushPullCustomVAR.getConstituency()+"/Accepted";
            File defaultFile = new File(dir);
            if (!defaultFile.exists())
                defaultFile.mkdirs();
          //  targetPdf = "/sdcard/test.pdf";
            targetPdf =dir+"/Form_"+formPushPullCustomVAR.getFormno()+"_"+formPushPullCustomVAR.getAadharNo()+".pdf";



        }
        else
        {
            String dir="/sdcard/Relay_Pension/"+formPushPullCustomVAR.getConstituency()+"/Rejected";
            File defaultFile = new File(dir);
            if (!defaultFile.exists())
                defaultFile.mkdirs();
          //  targetPdf = "/sdcard/test2.pdf";
            targetPdf =dir+"/Form_"+formPushPullCustomVAR.getFormno()+"_"+formPushPullCustomVAR.getAadharNo()+".pdf";
        }

        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));

            // close the document
            document.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }



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
