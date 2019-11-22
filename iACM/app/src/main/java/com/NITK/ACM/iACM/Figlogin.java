package com.NITK.ACM.iACM;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class Figlogin extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private Button btnLogin,btnReset,btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_figlogin);
        auth = FirebaseAuth.getInstance();

        inputEmail = (EditText) findViewById(R.id.l_email);
        inputPassword = (EditText) findViewById(R.id.l_password);

        btnReset=(Button)findViewById(R.id.l_signup);

        btnLogin = (Button) findViewById(R.id.l_login);
        btnReset=(Button) findViewById(R.id.btn_reset_password);

        btnSignUp=(Button)findViewById(R.id.l_signup);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Figlogin.this, ResetPasswordActivity.class));
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DatabaseReference enable=MainActivity.databaseReference.child("SignUp").child("Enabled");
//                Log.i("enable",enable.toString());
//                enable.addListenerForSingleValueEvent(new ValueEventListener () {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        Log.i("dta",dataSnapshot.toString());
//                        String check = dataSnapshot.getValue(String.class);
//
//                        if(check==null)
//                            Log.i("check","null");
//
//                        else
//                            Log.i("check","not null");
//
//                        if(check.equals("T"))
//                            startActivity(new Intent (Figlogin.this, SignUpActivity.class));
//
//                        else
//                            Toast.makeText(Figlogin.this, "Sign Up is currently unvailable!", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
                startActivity (new Intent (Figlogin.this,Fig_profile.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }



                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Figlogin.this, new OnCompleteListener<AuthResult> () {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(Figlogin.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent intent = new Intent(Figlogin.this, Fig_homepage.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });
    }


    }

