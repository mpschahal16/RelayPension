package com.codewarriors.hackathone.relaypension;



import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
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

import java.util.concurrent.TimeUnit;

import static com.basgeekball.awesomevalidation.ValidationStyle.COLORATION;

public class SignIn extends AppCompatActivity implements View.OnClickListener {

    EditText phonenoet, otpet;
    TextView errorinsend, errorinverify;
    Button sendotp, verify;
    AwesomeValidation awesomeValidation;

    ProgressDialog progressDialog;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        phonenoet = findViewById(R.id.phoneloginet);//editText
        otpet = findViewById(R.id.otpaadet);//editText
        sendotp = findViewById(R.id.sendaddotpbt);
        verify = findViewById(R.id.verifybt);
        errorinsend = findViewById(R.id.sendotperrorret);//tv
        errorinverify = findViewById(R.id.vrifyotpeterror);//tv
        otpet.setVisibility(View.INVISIBLE);
        verify.setVisibility(View.INVISIBLE);
        sendotp.setOnClickListener(this);
        verify.setOnClickListener(this);
        errorinsend.setVisibility(View.INVISIBLE);
        errorinverify.setVisibility(View.INVISIBLE);



        awesomeValidation = new AwesomeValidation(COLORATION);
        awesomeValidation.addValidation(this, R.id.phoneloginet, "^[+]?[0-9]{10,13}$", R.string.invalid_mobile_no);

        fbAuth = FirebaseAuth.getInstance();
        //added code
        //fbUser = FirebaseAuth.getInstance().getCurrentUser();
       // if (fbUser != null) {
       //     startActivity(new Intent(this,Splash.class));

       // }
        progressDialog=new ProgressDialog(this);
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
            }
            case R.id.verifybt:
            {
                verifyCode();
                break;
            }
        }

    }

    private void senotpfun() {
        if(awesomeValidation.validate())
        {
            String phoneNumber = phonenoet.getText().toString();
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
                    progressDialog.setMessage("Sending OTP To"+phoneNumber);
                    progressDialog.show();
                    break;

                }
                case 2:
                {
                    resendCode(phoneNumber);
                    progressDialog.setMessage("Sending OTP To"+phoneNumber);
                    progressDialog.show();
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
                            progressDialog.dismiss();
                        } else if (e instanceof FirebaseTooManyRequestsException) {
                            // SMS quota exceeded
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
                        progressDialog.dismiss();
                        sendotp.setVisibility(View.INVISIBLE);
                        otpet.setVisibility(View.VISIBLE);
                        verify.setVisibility(View.VISIBLE);
                        retry=2;
                        Toast.makeText(getApplicationContext(),"OTP SEND",Toast.LENGTH_LONG).show();


                    }
                };
    }



    public void verifyCode() {
        String code = otpet.getText().toString();
        PhoneAuthCredential credential =
                    PhoneAuthProvider.getCredential(phoneVerificationId, code);
            signInWithPhoneAuthCredential(credential);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        fbAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        rootRef = FirebaseDatabase.getInstance().getReference("UserState/");
                        String userid=task.getResult().getUser().getUid();
                        if (task.isSuccessful()) {

                            boolean isNew = task.getResult().getAdditionalUserInfo().isNewUser();
                            if(isNew) {

                                rootRef.child(userid).setValue(0).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        startActivity(new Intent(SignIn.this,Aadharverify.class));
                                    }
                                });

                            }

                            else {
                                rootRef.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String s=dataSnapshot.getValue().toString();
                                        switch (s)
                                        {
                                            case "0":
                                            {
                                                startActivity(new Intent(SignIn.this,Aadharverify.class));
                                                break;
                                            }
                                            case "1":
                                            {
                                                Toast.makeText(getApplicationContext(),"gottostatus",Toast.LENGTH_LONG).show();
                                                break;
                                            }
                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Toast.makeText(getApplicationContext(),"cancel",Toast.LENGTH_LONG).show();
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

}