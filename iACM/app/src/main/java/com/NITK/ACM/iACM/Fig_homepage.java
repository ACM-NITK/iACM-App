package com.NITK.ACM.iACM;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Fig_homepage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static DatabaseReference databaseReference;
    private FirebaseAuth.AuthStateListener authListener;

    Button kep_button,event_button;
    FirebaseAuth firebaseAuth;
    Fig_details  fig_profile;

    public static FigLeaderelements user_data=new FigLeaderelements ();
    ImageView imageView ;
    ImageButton imageButton;
    TextView textView , emailfig ;
    ImageButton leaderboard;
    String username ,emailid;

    FirebaseDatabase firebaseDatabase;
    public static String currentActivity="Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        setContentView (R.layout.activity_fig_homepage);
        Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);
        setSupportActionBar (toolbar);
        //
    username = "FIG_USER";
    emailid = "FIG_MEMBER";
//
        firebaseAuth = FirebaseAuth.getInstance ();
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser ();
        databaseReference = FirebaseDatabase.getInstance ().getReference ();
        // funtion to set username and email id to nav_fig .
        databaseReference.child ("FIG_User").child (firebaseUser.getUid ()).addListenerForSingleValueEvent (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  fig_profile = new Fig_details ();
                fig_profile=dataSnapshot.getValue (Fig_details.class);
                if (fig_profile != null) {
                    username = fig_profile.getFname () + fig_profile.getLname ();

                    emailid = fig_profile.getEmail ();
                    NavigationView navigationView = (NavigationView) findViewById (R.id.nav_viewfig);
                    View headerview= navigationView.getHeaderView (0);

                    textView=(TextView) headerview.findViewById(R.id.profile_name);
                    textView.setText (username);
                    emailfig = (TextView) headerview.findViewById (R.id.email_id);
                    emailfig.setText (emailid);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentWhatsapp = new Intent(Intent.ACTION_VIEW);
                String url = "https://chat.whatsapp.com/FPEBKxcZfhu9fJdMJpvJPo";
                intentWhatsapp.setData(Uri.parse(url));
                intentWhatsapp.setPackage("com.whatsapp");
                startActivity(intentWhatsapp);
            }
        });
        //

        DrawerLayout drawer = (DrawerLayout) findViewById (R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle (
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener (toggle);
        toggle.syncState ();
//
        NavigationView navigationView = (NavigationView) findViewById (R.id.nav_viewfig);
        navigationView.setNavigationItemSelectedListener (this);
        //
        imageButton= (ImageButton) findViewById (R.id.events_button);
        imageButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (Fig_homepage.this, FigEvents.class));
            }
        });

        //


        //

        leaderboard = (ImageButton) findViewById (R.id.leaderboard_button);
        leaderboard.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (Fig_homepage.this,Figleaderboard.class));
            }
        });

        //
        if (firebaseAuth.getCurrentUser () == null) {
            startActivity (new Intent (Fig_homepage.this, Separatelogin.class)); // LoginActivity
            finish ();
        } else {


            Query query = databaseReference.child ("FigLeaderBoard").orderByChild ("email").equalTo (firebaseUser.getEmail ());
            Log.i ("user", query.toString ());

            if (user_data.getPerson_name () == null)
                startActivity (new Intent (Fig_homepage.this, LoadingActivity.class));

            query.addListenerForSingleValueEvent (new ValueEventListener () {

                public void onDataChange(DataSnapshot dataSnapshot) {
                    LoadingActivity.loadingActivity.finish ();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });


            query.addChildEventListener (new ChildEventListener () {

                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    user_data = dataSnapshot.getValue (FigLeaderelements.class);


//                    if (textView == null || user_data == null) {
//                        Log.i ("nullll", "nulll");
//
//                        Log.i ("load", "finished");
//                    }
//                    textView.setText (user_data.getPerson_name ());
//                    Log.v("name",user_data.getPerson_name ());

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            //


        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById (R.id.drawer_layout);
        if (drawer.isDrawerOpen (GravityCompat.START)) {
            drawer.closeDrawer (GravityCompat.START);
        } else {
            super.onBackPressed ();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater ().inflate (R.menu.fig_homepage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return super.onOptionsItemSelected (item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId ();

        if (id == R.id.nav_profile) {
            Toast.makeText (Fig_homepage.this,"Function Unable till further updates",Toast.LENGTH_LONG).show ();

//        } else if (id == R.id.nav_leader_board) {
//          //  startActivity (new Intent (Fig_homepage.this,Figleaderboard.class));


        } else if (id == R.id.nav_iConnect) {
            Toast.makeText (Fig_homepage.this,"Function Unable till further updates",Toast.LENGTH_LONG).show ();

        } else if (id == R.id.nav_iACM) {
            Toast.makeText (Fig_homepage.this,"Function Unable till further updates",Toast.LENGTH_LONG).show ();


        } else if (id == R.id.nav_logout) {
            firebaseAuth.signOut ();
            finish ();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById (R.id.drawer_layout);
        drawer.closeDrawer (GravityCompat.START);
        return true;
    }


    }


