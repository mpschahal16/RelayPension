package com.codewarriors.hackathone.relaypension;



import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.codewarriors.hackathone.relaypension.adminside.AdminLogin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;
import java.util.concurrent.TimeUnit;



import static com.basgeekball.awesomevalidation.ValidationStyle.COLORATION;

public class SignIn extends AppCompatActivity implements View.OnClickListener {

    EditText phonenoet, otpet;
    TextView errorinsend, errorinverify;
    Button sendotp, verify;
    TextInputLayout textInputLayout;
    AwesomeValidation awesomeValidation;

    ProgressDialog progressDialog;
   // CatLoadingView mViewdddd;

    Timeraa timeraa;

    DatabaseReference rootRef;


    //1 means 1st try for otp rest retry
    int retry=1;




    private String phoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;

    private FirebaseAuth fbAuth;
    //added code
    FirebaseUser fbUser;

    //otp code
    SmsManager smsManager;
    PendingIntent sendPI,delPI;
    BroadcastReceiver sendBR,delBR;
    String data;
    int a,otp;
    //otp

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //otp_extra
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        phonenoet = findViewById(R.id.phoneloginet);//editText
        otpet = findViewById(R.id.otpaadet);//editText
        sendotp = findViewById(R.id.sendaddotpbt);
        verify = findViewById(R.id.verifybt);
        textInputLayout=findViewById(R.id.materialsignin2);//material design editt texview parent layout
        errorinsend = findViewById(R.id.sendotperrorret);//tv
        errorinverify = findViewById(R.id.vrifyotpeterror);//tv
        textInputLayout.setVisibility(View.INVISIBLE);
        otpet.setVisibility(View.INVISIBLE);
        verify.setVisibility(View.INVISIBLE);
        sendotp.setOnClickListener(this);
        verify.setOnClickListener(this);
        errorinsend.setVisibility(View.INVISIBLE);
        errorinverify.setVisibility(View.INVISIBLE);
        //otp code
   //   otponcreate();

        awesomeValidation = new AwesomeValidation(COLORATION);
        awesomeValidation.addValidation(this, R.id.phoneloginet, "^[+]?[0-9]{10,13}$", R.string.invalid_mobile_no);

        fbAuth = FirebaseAuth.getInstance();
        /*added code
        fbUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fbUser != null) {
            startActivity(new Intent(this,Splash.class));

        }*/
        progressDialog=new ProgressDialog(this);
        //mViewdddd = new CatLoadingView();
    }




    private void otponcreate() {
        //otp_extra
        sendPI = PendingIntent.getBroadcast(this,987,new Intent("SEND_SMS"),0);
        delPI=PendingIntent.getBroadcast(this,986, new Intent("DEL_SMS"),0);
        smsManager = SmsManager.getDefault();
        sendBR = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (this.getResultCode()){
                    case RESULT_OK :
                       // Toasty.success(context, "SMS SEND", Toast.LENGTH_SHORT,true).show();
                        Toast.makeText(context, "SMS SEND", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE :
                      //  Toasty.success(context, "NO SERVICE", Toast.LENGTH_SHORT,true).show();
                        Toast.makeText(context, "NO SERVICE", Toast.LENGTH_SHORT).show();
                        break;
                    default :
                       // Toasty.success(context, "SERVICE PROBLEM", Toast.LENGTH_SHORT,true).show();
                        Toast.makeText(context,  "SERVICE PROBLEM", Toast.LENGTH_SHORT).show();
                }
            }
        };
        delBR = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (this.getResultCode()){
                    case RESULT_OK :
                        //Toasty.success(context, "SMS DELIVERED", Toast.LENGTH_SHORT,true).show();
                        Toast.makeText(context, "SMS DELIVERED", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE :
                       // Toasty.error(context, "GENERIC FAILURE", Toast.LENGTH_SHORT,true).show();
                        Toast.makeText(context, "GENERIC FAILURE", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                      //  Toasty.error(context, "SERVICE PROBLEM", Toast.LENGTH_SHORT,true).show();
                        Toast.makeText(context, "SERVICE PROBLEM", Toast.LENGTH_SHORT).show();
                }
            }
        };
        this.registerReceiver(sendBR, new IntentFilter("SEND_SMS"));
        this.registerReceiver(delBR,new IntentFilter("DEL_SMS"));
    }


    @Override
    public void onClick(View v) {
        int id=v.getId();
       switch (id)
        {
            case R.id.sendaddotpbt:
            {
                senotpfun();
                break;

                //otp
            //mysenotpfun();

            }
            case R.id.verifybt:
            {
                verifyCode();
                break;
                //otp
               // myverifyCode();
            }
        }

    }

    private void mysenotpfun() {
//otp_extra
        Random r = new Random();
        a = r.nextInt(999999+100000);
        smsManager.sendTextMessage("+91"+phonenoet,null,""+a,sendPI,delPI);//change
    }

    private void senotpfun() {
        if(awesomeValidation.validate())
        {
            String phoneNumber ="+91"+phonenoet.getText().toString();
            setUpVerificatonCallbacks();
            switch (retry)
            {
                case 1:
                {

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            phoneNumber,        // Phone number to verify
                            60,                 // Timeout duration
                            TimeUnit.SECONDS,   // Unit of timeout
                            this,               // Activity (for callback binding)
                            verificationCallbacks);
                    retry=retry+1;

                   /* mViewdddd.setCanceledOnTouchOutside(false);
                    mViewdddd.setText("Sending OTP");
                    mViewdddd.show(getSupportFragmentManager(),"");*/


                    progressDialog.setMessage("Sending OTP To "+phoneNumber);
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    break;

                }
                case 2:
                {
                    progressDialog.setMessage("Sending OTP To "+phoneNumber);
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    resendCode(phoneNumber);
                   /* mViewdddd.setCanceledOnTouchOutside(false);
                    mViewdddd.setText("Sending OTP");
                    mViewdddd.show(getSupportFragmentManager(),"");*/
                    break;
                }
            }
        }
    }

    private void setUpVerificatonCallbacks() {

        verificationCallbacks =
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(
                            PhoneAuthCredential credential) {

                        Log.d("mpschahal","onverificationcomplete()");
                        signInWithPhoneAuthCredential(credential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {

                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            // Invalid request
                            Log.d("msg", "Invalid credential: "
                                    + e.getLocalizedMessage());
                           // mViewdddd.dismiss();
                            progressDialog.dismiss();
                        } else if (e instanceof FirebaseTooManyRequestsException) {
                            // SMS quota exceeded
                           // mViewdddd.dismiss();
                            progressDialog.dismiss();
                            Log.d("msg", "SMS Quota exceeded.");
                        }
                    }

                    @Override
                    public void onCodeSent(String verificationId,
                                           PhoneAuthProvider.ForceResendingToken token) {

                        phoneVerificationId = verificationId;
                        resendToken = token;
                        timeraa=new Timeraa();
                        timeraa.execute();
                      //  mViewdddd.dismiss();
                        progressDialog.dismiss();
                        sendotp.setVisibility(View.INVISIBLE);
                        otpet.setVisibility(View.VISIBLE);
                        verify.setVisibility(View.VISIBLE);
                        textInputLayout.setVisibility(View.VISIBLE);
                        retry=2;


                       // Toasty.success(getApplicationContext(),"OTP SEND",Toast.LENGTH_LONG,true).show();

                        Toast.makeText(getApplicationContext(), "OTP SEND", Toast.LENGTH_SHORT).show();



                    }
                };
    }



    public void verifyCode() {
        awesomeValidation.addValidation(this, R.id.otpaadet, "\\d{6}", R.string.invalid_otp);

        if(awesomeValidation.validate()) {
            String code=otpet.getText().toString();


           /* mViewdddd.setCanceledOnTouchOutside(false);
            mViewdddd.setText("Verifying...");
            mViewdddd.show(getSupportFragmentManager(),"");*/



           progressDialog.setMessage("Verifying...");
           progressDialog.setCancelable(false);
            progressDialog.show();
            PhoneAuthCredential credential =
                    PhoneAuthProvider.getCredential(phoneVerificationId, code);
            signInWithPhoneAuthCredential(credential);
        }

    }
    public void myverifyCode() {
        //otp_extra
        data = otpet.getText().toString();//change
        otp= Integer.parseInt(data);
        if(a==otp)
        {
           // Toasty.success(this, "welcome user", Toast.LENGTH_SHORT,true).show();
            Toast.makeText(this, "welcome user", Toast.LENGTH_SHORT).show();
        }
        else {
           // Toasty.error(this, "INVALID OTP", Toast.LENGTH_SHORT,true).show();
            Toast.makeText(this, "Welcome user", Toast.LENGTH_SHORT).show();
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        fbAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        rootRef = FirebaseDatabase.getInstance().getReference("UserState/");

                        if (task.isSuccessful()) {
                            String userid=task.getResult().getUser().getUid();
                            boolean isNew = task.getResult().getAdditionalUserInfo().isNewUser();
                            if(isNew) {

                                rootRef.child(userid).setValue(0).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                      //  mViewdddd.dismiss();
                                        progressDialog.dismiss();
                                        startActivity(new Intent(SignIn.this,Aadharverify.class));
                                    }
                                });

                            }

                            else {
                                rootRef.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String s=dataSnapshot.getValue().toString();
                                        if(s.equals("0"))
                                        {
                                            startActivity(new Intent(SignIn.this,Aadharverify.class));
                                        }
                                        else
                                        {
                                            startActivity(new Intent(SignIn.this,StatusActivity.class));
                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                       // Toasty.warning(getApplicationContext(),"Slow Internet",Toast.LENGTH_LONG,true).show();
                                        Toast.makeText(getApplicationContext(),"Slow Internet", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            SharedPreferences.Editor editor = getSharedPreferences("codewarriors", MODE_PRIVATE).edit();
                            editor.putString("userid", userid);
                            editor.apply();



                        } else {
                            if (task.getException() instanceof
                                    FirebaseAuthInvalidCredentialsException) {
                               errorinverify.setVisibility(View.VISIBLE);
                               errorinverify.setText("DID NOT MATCH");

                               //mViewdddd.dismiss();
                                progressDialog.dismiss();
                            }
                        }
                    }
                });
    }

    public void resendCode(String phoneNumber) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                verificationCallbacks,
                resendToken);
    }



    @SuppressLint("StaticFieldLeak")
    private class Timeraa extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            int i=60;
            while (i>0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i=i-1;
                publishProgress(i);
            }

            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            int x=values[0];
            errorinsend.setVisibility(View.VISIBLE);
            errorinsend.setText(String.valueOf("Retry in "+x+" sec"));
            if(x==1)
            {

                sendotp.setVisibility(View.VISIBLE);
                errorinsend.setText(R.string.retry);
                sendotp.setText(R.string.send_again);

            }

        }

         @Override
         protected void onPostExecute(String s) {
              super.onPostExecute(s);
             errorinsend.setText(R.string.retry);

         }
     }





     //menu button code written here



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menuforadminlogin, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.admin_login:
                startActivity(new Intent(SignIn.this, AdminLogin.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
