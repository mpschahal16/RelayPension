package com.codewarriors.hackathone.relaypension.adminside;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.codewarriors.hackathone.relaypension.R;
import com.codewarriors.hackathone.relaypension.adminside.listallconspack.ListAllConstituency;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.roger.catloadinglibrary.CatLoadingView;

import es.dmoral.toasty.Toasty;

public class AdminLogin extends AppCompatActivity implements View.OnClickListener{

    EditText t1, t2;
    Button b1,b2;
    TextView tv;
    FirebaseAuth auth;
    FirebaseUser user;


    ProgressDialog dialog;
    CatLoadingView mViewdddd;

    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_admin_login);
        t1 = this.findViewById(R.id.admemail);
        t2 = this.findViewById(R.id.pass);
        b1 = this.findViewById(R.id.signup);
        b2 = this.findViewById(R.id.signin);
        tv = this.findViewById(R.id.login);
      //  b1.setOnClickListener(this);
        b1.setVisibility(View.INVISIBLE);
       // b2.setVisibility(View.INVISIBLE);
        b2.setOnClickListener(this);
        tv.setOnClickListener(this);
        dialog = new ProgressDialog(this);

        mViewdddd = new CatLoadingView();
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
       /* if (user != null) {
           // go_to_login();
            b2.setVisibility(View.VISIBLE);
            b1.setVisibility(View.INVISIBLE);
            tv.setText(user.getEmail());
        }*/

        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        awesomeValidation = new AwesomeValidation(ValidationStyle.COLORATION);
        awesomeValidation.addValidation(this, R.id.admemail, Patterns.EMAIL_ADDRESS, R.string.invalid_mail);
       // awesomeValidation.addValidation(this, R.id.pass, regexPassword, R.string.invalid_password);

    }

    @Override
    public void onClick(View v) {
        final String user = t1.getText().toString();
        String pass = t2.getText().toString();
        if(awesomeValidation.validate()&&pass.length()>8) {
            if (v == b1) {
              /*  dialog.setMessage("Registering User...please Wait");
                dialog.show();

                auth.createUserWithEmailAndPassword(user, pass).
                        addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    dialog.dismiss();
                                    Toasty.success(AdminLogin.this, "User Registered", Toast.LENGTH_SHORT,true).show();
                                    t1.setText("");
                                    t2.setText("");
                                    t1.requestFocus();




                                    go_to_login();
                                }
                            }
                        }).addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toasty.error(AdminLogin.this, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT,true).show();
                    }
                });*/
            }
            if (v == b2) {
                mViewdddd.setCanceledOnTouchOutside(false);
                mViewdddd.setText("Logging In ...please Wait");
                mViewdddd.show(getSupportFragmentManager(),"");
               /* dialog.setMessage("Logging In ...please Wait");
                dialog.show();*/
                auth.signInWithEmailAndPassword(user, pass).
                        addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //saving admin id
                                    SharedPreferences.Editor editor = getSharedPreferences("codewarriors", MODE_PRIVATE).edit();
                                    editor.putString("adminid", user);
                                    editor.apply();

                                    mViewdddd.dismiss();

                                    go_to_login();
                                }
                            }
                        }).addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mViewdddd.dismiss();
                       // dialog.dismiss();
                        Toasty.error(AdminLogin.this, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT,true).show();
                    }
                });
            }
        }
    }

    private void go_to_login() {

        Intent intent = new Intent(this,ListAllConstituency.class);
        startActivity(intent);

    }

}