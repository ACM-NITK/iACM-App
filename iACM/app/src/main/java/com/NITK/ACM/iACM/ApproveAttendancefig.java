package com.NITK.ACM.iACM;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class ApproveAttendancefig extends Fragment {
    private List<EventsList> eList = new ArrayList<> ();
    private RecyclerView recyclerView;
    private FigEventAdaptor eAdapter; // changed
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    static ViewPager viewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.approve_attendancefig, container, false);

        super.onCreate(savedInstanceState);
        Fig_homepage.currentActivity="ApproveAttendance";
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        viewPager=(ViewPager) getActivity().findViewById(R.id.pager);

        eAdapter = new FigEventAdaptor(eList);//changed
        firebaseDatabase=FirebaseDatabase.getInstance();
        GetDataFireBase();
        recyclerView.setAdapter(eAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager (rootView.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator ());
        recyclerView.addItemDecoration(new DividerItemDecoration (rootView.getContext(), 0));
        eAdapter.notifyDataSetChanged();
        return rootView;

    }

    void GetDataFireBase(){
        databaseReference=firebaseDatabase.getReference("FIG_Events");
        databaseReference.addChildEventListener(new ChildEventListener () {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                EventsList data=new EventsList();
                data= dataSnapshot.getValue(EventsList.class);

                //Log.i("data",data.title);
                eAdapter.addItem(data);
                eAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(eAdapter);
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

