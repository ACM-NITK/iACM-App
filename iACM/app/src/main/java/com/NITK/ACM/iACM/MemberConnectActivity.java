package com.NITK.ACM.iACM;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class MemberConnectActivity extends AppCompatActivity {

    private List<UserProfile> uList = new ArrayList<>();
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Context context;
    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_connect);

        context=getApplicationContext();
        MainActivity.currentActivity=getString(R.string.member_connect);

        recyclerView = (RecyclerView) findViewById(R.id.user_list);


        userAdapter = new UserAdapter(uList, context);
        firebaseDatabase=FirebaseDatabase.getInstance();
        GetDataFireBase();
        recyclerView.setAdapter(userAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(context, 0));
        userAdapter.notifyDataSetChanged();

    }

    private void GetDataFireBase() {

        databaseReference=firebaseDatabase.getReference(getString(R.string.users));

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                UserProfile userProfile=new UserProfile();
                userProfile= dataSnapshot.getValue(UserProfile.class);

                userAdapter.addItem(userProfile);
                userAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(userAdapter);
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



}

