package com.NITK.ACM.iACM;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth.AuthStateListener authListener;
    public static FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    public static LeaderBoardElement user_data=new LeaderBoardElement();
    public static String currentActivity="Main";
    public TextView textView;
    private List<Announcement> announcementList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AnnouncementAdapter aAdapter;
    private ImageButton event_button,leaderboard_button,member_connect_button,projects_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseMessaging.getInstance().subscribeToTopic("all");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        event_button=(ImageButton)findViewById(R.id.events_button);
        leaderboard_button=(ImageButton)findViewById(R.id.leaderboard_button);
        member_connect_button=(ImageButton)findViewById(R.id.member_connect_button);
        projects_button=(ImageButton)findViewById(R.id.projects_button);
        recyclerView=(RecyclerView)findViewById(R.id.announcement_recycler);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        textView=(TextView) navigationView.getHeaderView(0).findViewById(R.id.profile_name);//(TextView)findViewById(R.id.profile_name);

        databaseReference = FirebaseDatabase.getInstance().getReference();




        //get firebase auth instance
        auth = FirebaseAuth.getInstance();


        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //startActivity(new Intent(MainActivity.this, HomePage.class));
        //finish();

        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, Separatelogin.class)); // LoginActivity
            finish();
        } else {


            Query query = databaseReference.child("Leader Board").orderByChild("email").equalTo(user.getEmail());
            Log.i("user",query.toString());

            if(user_data.getPerson_name()==null)
            startActivity(new Intent(MainActivity.this, LoadingActivity.class));

            query.addListenerForSingleValueEvent(new ValueEventListener() {

                public void onDataChange(DataSnapshot dataSnapshot) {
                    LoadingActivity.loadingActivity.finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });


            query.addChildEventListener(new ChildEventListener() {

                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    user_data = dataSnapshot.getValue(LeaderBoardElement.class);


                    if (textView==null || user_data==null)
                    {
                        Log.i("nullll","nulll");

                        Log.i("load","finished");
                    }
                    textView.setText(user_data.getPerson_name());

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



            FirebaseMessaging.getInstance().subscribeToTopic("iACM")
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            String msg = getString(R.string.app_name);
                            if (!task.isSuccessful()) {
                                msg = getString(R.string.app_name);
                            }
                            Log.d("Subscribe", msg);
                            //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });




            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentWhatsapp = new Intent(Intent.ACTION_VIEW);
                    String url = "https://chat.whatsapp.com/6thhFBjXqyqC0pIZsGKEuu";
                    intentWhatsapp.setData(Uri.parse(url));
                    intentWhatsapp.setPackage("com.whatsapp");
                    startActivity(intentWhatsapp);
                }
            });



            event_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent myIntent = new Intent(view.getContext(), events.class);
                    MainActivity.this.startActivity(myIntent);
                }
            });

            leaderboard_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent myIntent = new Intent(view.getContext(), LeaderBoard.class);
                    MainActivity.this.startActivity(myIntent);
                }
            });

            member_connect_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), MemberConnectActivity.class);
                    MainActivity.this.startActivity(intent);
                    //Toast.makeText(MainActivity.this, "No contacts found!", Toast.LENGTH_SHORT).show();
                }
            });

            projects_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, "Projects yet to be updated!", Toast.LENGTH_SHORT).show();
                }
            });

            GetDataFireBase();

            aAdapter = new AnnouncementAdapter(announcementList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(aAdapter);
            GridLayoutManager mGrid = new GridLayoutManager(this, 1);
            recyclerView.setLayoutManager(mGrid);
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);

        }


    }

    void GetDataFireBase() {
        DatabaseReference databaseReference2;
        databaseReference2 = databaseReference.child("Announcements");
        databaseReference2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Announcement data=new Announcement();
                data= dataSnapshot.getValue(Announcement.class);

                //Log.i("data",data.title);
                aAdapter.addItem(data);
                aAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(aAdapter);
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

    }

    public void go(View view)
    {
        startActivity(new Intent(this , profile.class));
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem item=menu.findItem(R.id.admin_portal);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {

            auth = FirebaseAuth.getInstance();
            auth.sendPasswordResetEmail(user_data.getEmail())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
        }

        else if(id== R.id.sign_out)
        {

            auth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();

        }

        else if(id== R.id.admin_portal)
        {
            String eff="F";
            if(user_data.getAdmin()==null);


            else if(user_data.getAdmin().equals(eff)) {
                Toast.makeText(getApplicationContext(), "Sorry you don't have administrator privileges", Toast.LENGTH_LONG).show();
            } else {
                startActivity(new Intent(MainActivity.this, AdminPortal.class));
                //finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_events) {
            // Handle the camera action
            startActivity(new Intent(this , events.class));
        } else if (id == R.id.nav_images) {
            Toast.makeText(MainActivity.this, "No images found!", Toast.LENGTH_SHORT).show();
           // startActivity(new Intent(this , Images.class));

        } else if (id == R.id.nav_projects) {
            Toast.makeText(MainActivity.this, "Projects yet to be updated!", Toast.LENGTH_SHORT).show();
           // startActivity(new Intent(this , Projects.class));

        } else if (id == R.id.nav_contacts) {
            Toast.makeText(MainActivity.this, "No contacts found!", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(this , Contacts.class));

        } else if (id == R.id.nav_leader_board) {
            startActivity(new Intent(this , LeaderBoard.class));
        }
        else if (id == R.id.nav_about) {
            startActivity(new Intent(this , About.class));
        }
        else if (id == R.id.nav_profile)
        {
            startActivity (new Intent (MainActivity.this,profile.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
