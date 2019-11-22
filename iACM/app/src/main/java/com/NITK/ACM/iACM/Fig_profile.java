package com.NITK.ACM.iACM;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.PriorityQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fig_profile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    SessionManagment session;

    private FirebaseUser mUser;
      private FirebaseDatabase firebaseDatabase;
      private DatabaseReference databaseReference,mroot;

    String userp, passwordv;
    TextInputEditText firstname_q ;
    TextInputEditText lastname_q ;
    TextInputEditText email_q ;
    TextInputEditText username_q;
    TextInputEditText confirmpassword_q ;
    TextInputEditText password_q;

    TextInputLayout firstname_w ;
    TextInputLayout lastname_w ;
    TextInputLayout email_w ;
//    TextInputLayout username_w;
    TextInputLayout confirmpassword_w;
    TextInputLayout password_w;
    String emailv;
    Fig_details profile;
private  FigLeaderelements figLeaderelements ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_fig_profile);

 /////
        mAuth = FirebaseAuth.getInstance ();

        profile = new Fig_details ();

        figLeaderelements = new FigLeaderelements ();
        //
        figLeaderelements.setAdmin ("F");
        figLeaderelements.setPoints ("0");
        figLeaderelements.setStreak ("0");


        //


        Button nextButton = (Button) findViewById (R.id.next1);
        firstname_q = findViewById(R.id.firstname);
        lastname_q = findViewById(R.id.lastname);
        email_q =  findViewById(R.id.email);
//        username_q =  findViewById(R.id.username);
        confirmpassword_q =  findViewById(R.id.confirm_password);
        password_q =  findViewById(R.id.password);

        firstname_w = findViewById(R.id.firstname_up);
        lastname_w = findViewById(R.id.lastname_up);
        email_w =  findViewById(R.id.email_up);
//        username_w =  findViewById(R.id.username_up);
        confirmpassword_w =  findViewById(R.id.confirm_password_up);
        password_w =  findViewById(R.id.password_up);

        ////

        /// Button functionality

        nextButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

//                userp = username_q.getText ().toString ().trim ();
//                final String userv = "^[a-zA-Z0-9]+([_.a-zA-Z0-9])*$";
//
//                Pattern userx = Pattern.compile (userv);
//                Matcher matcher2 = userx.matcher (userp);

                passwordv = password_q.getText ().toString ().trim ();

                final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
                Pattern passwordp = Pattern.compile (PASSWORD_PATTERN);
                Matcher matcher = passwordp.matcher (passwordv);

                emailv = email_q.getText ().toString ().trim ();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                int k = 5;
                if (TextUtils.isEmpty (firstname_q.getText ())) {
                    k--;

                    firstname_w.setError ("First name is required!");
                } else
                    firstname_w.setErrorEnabled(false);


                if (TextUtils.isEmpty (lastname_q.getText ())) {

                    k--;
                    lastname_w.setError ("Last name is required!");

                } else
                    lastname_w.setErrorEnabled(false);




                if (!matcher.matches ()) {
                    k--;
                    password_w.setError ("Invalid Password");
                } else
                    password_w.setErrorEnabled(false);

                if (!confirmpassword_q.getText ().toString ().equals (password_q.getText ().toString ()) || confirmpassword_q.getText ().toString ().isEmpty ()) {
                    k--;
                    confirmpassword_w.setError ("Passwords are not same");
                } else
                    confirmpassword_w.setErrorEnabled(false);



                if (!(emailv.matches (emailPattern))) {
                    k--;
                    email_w.setError ("Invalid Email-Id");
                    // Toast.makeText(getApplicationContext(),"invalid email address",Toast.LENGTH_SHORT).show();
                } else
                    email_w.setErrorEnabled(false);


                if (k == 5) {
                  register();



                }

            }

        });

        ///
    }
    private void register()
    {

        profile.setEmail (email_q.getText ().toString ().trim ());
        profile.setFname (firstname_q.getText ().toString ().trim ());
        profile.setLname (lastname_q.getText ().toString ().trim ());
        figLeaderelements.setPerson_name (firstname_q.getText ().toString ().trim () + lastname_q.getText ().toString ().trim ());
        figLeaderelements.setEmail (email_q.getText ().toString ().trim ());


        mAuth.createUserWithEmailAndPassword (emailv,passwordv).addOnCompleteListener (new OnCompleteListener<AuthResult> () {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful ())
                {
                    Toast.makeText (Fig_profile.this,"Registration completed",Toast.LENGTH_SHORT).show ();
//                    session.createLoginSession ("Figuser",emailv);

                    mUser = mAuth.getCurrentUser ();
                    update ();
                    Intent myIntent = new Intent (Fig_profile.this,
                            Fig_homepage.class);
//                    myIntent.putExtra ("profile", profile);
                    startActivity (myIntent);
                    finish ();
                }
                if(!task.isSuccessful ())
                {
                    FirebaseAuthException e = (FirebaseAuthException)task.getException ();
                    Toast.makeText (Fig_profile.this," " + e.getMessage(),Toast.LENGTH_SHORT).show ();
                }
            }
        });
    }

    private void update()
    {
        databaseReference = FirebaseDatabase.getInstance ().getReference ();
        databaseReference.child ("FIG_User").child (mUser.getUid ()).setValue (profile);
        figLeaderelements.setuID (mUser.getUid ());
        databaseReference.child ("FigLeaderBoard").child (mUser.getUid ()).setValue (figLeaderelements);
    }


}
