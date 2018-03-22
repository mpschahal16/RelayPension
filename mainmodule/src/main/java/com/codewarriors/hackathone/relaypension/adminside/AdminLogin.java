package com.codewarriors.hackathone.relaypension.adminside;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codewarriors.hackathone.relaypension.R;
import com.codewarriors.hackathone.relaypension.adminside.listallconspack.ListAllConstituency;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminLogin extends AppCompatActivity implements View.OnClickListener{

    EditText t1, t2;
    Button b1,b2;
    TextView tv;
    FirebaseAuth auth;
    FirebaseUser user;
    ProgressDialog dialog;

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
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
       /* if (user != null) {
           // go_to_login();
            b2.setVisibility(View.VISIBLE);
            b1.setVisibility(View.INVISIBLE);
            tv.setText(user.getEmail());
        }*/

    }

    @Override
    public void onClick(View v) {
        String user = t1.getText().toString();
        String pass = t2.getText().toString();
        if(v== b1){
            dialog.setMessage("Registering User...please Wait");
            dialog.show();

            auth.createUserWithEmailAndPassword(user,pass).
                    addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                dialog.dismiss();
                                Toast.makeText(AdminLogin.this, "User Registered", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(AdminLogin.this, "ERROR: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(v==b2){
            dialog.setMessage("Logging In ...please Wait");
            dialog.show();
            auth.signInWithEmailAndPassword(user,pass).
                    addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                go_to_login();
                            }
                        }
                    }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.dismiss();
                    Toast.makeText(AdminLogin.this, "ERROR: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void go_to_login() {
        Intent intent = new Intent(this,ListAllConstituency.class);
        startActivity(intent);

    }

}