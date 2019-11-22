package com.NITK.ACM.iACM;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AttendeesListfig extends Fragment {
    private List<AttendeeRow> attendeeRowList = new ArrayList<> ();
    private RecyclerView recyclerView;
    private AttendeeAdaptorfig attendeeAdaptor;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    public String currTab;



    public void setCurrTab(String currTab)
    {
        this.currTab=currTab;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_attendeesfig, container, false);
        super.onCreate(savedInstanceState);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_viewfig);

        attendeeAdaptor = new AttendeeAdaptorfig(attendeeRowList);
        attendeeAdaptor.nowTab=currTab;

        firebaseDatabase=FirebaseDatabase.getInstance();
        recyclerView.setAdapter(attendeeAdaptor);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager (rootView.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        GetDataFireBase();

        recyclerView.setItemAnimator(new DefaultItemAnimator ());
        recyclerView.addItemDecoration(new DividerItemDecoration (rootView.getContext(), LinearLayoutManager.VERTICAL));

        attendeeAdaptor.notifyDataSetChanged();
        return rootView;

    }

    void GetDataFireBase(){
        if(currTab=="Tab1")
        {
            databaseReference=firebaseDatabase.getReference("FIG_Events").child(FigEventAdaptor.e.geteID()).child("Attendees");
            Log.i("...","Attendeesfig");
            Attendeesfig.whichTab=currTab;
        }

        else if(currTab=="Tab2")
        {    databaseReference=firebaseDatabase.getReference("FIG_Events").child(FigEventAdaptor.e.geteID()).child("Attended");
            Log.i("...","Attendedfig");
            Attendeesfig.whichTab=currTab;
        }

        databaseReference.addChildEventListener(new ChildEventListener () {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                AttendeeRow attendee_data=new AttendeeRow();
                attendee_data= dataSnapshot.getValue(AttendeeRow.class);

                //Log.i("data",data.title);
                assert attendee_data != null;
                Log.i ("name",attendee_data.getName ());
                attendeeAdaptor.addItem(attendee_data);
                attendeeAdaptor.notifyDataSetChanged();
                recyclerView.setAdapter(attendeeAdaptor);
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
