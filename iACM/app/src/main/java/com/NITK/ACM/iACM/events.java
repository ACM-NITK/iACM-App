package com.NITK.ACM.iACM;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class events extends AppCompatActivity {

    private List<EventsList> eList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EventAdaptor eAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    public static AlertDialog.Builder dialog;
    private Context context;
    Query query;
    Query query2;
    static int option=4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MainActivity.currentActivity="Events";
        context=getApplicationContext();
        setContentView(R.layout.activity_events);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        dialog = new AlertDialog.Builder(this);
        eAdapter = new EventAdaptor(eList);
        firebaseDatabase=FirebaseDatabase.getInstance();
        GetDataFireBase(option);
        recyclerView.setAdapter(eAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(context, 0));
       eAdapter.notifyDataSetChanged();

    }

    void GetDataFireBase(int option){
        databaseReference=firebaseDatabase.getReference("Events");
        Date date = Calendar.getInstance().getTime();


        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);

        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH); // 17
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        Integer value=year*10000+month*100+dayOfMonth;

        if(option==4)
        {
            query=databaseReference.orderByChild("datevalue");
        }


        if(option==1)
        {

            query=databaseReference.orderByChild("datevalue").startAt(value).endAt(value);
        }

        if(option==2)
        {
            Calendar temp = Calendar.getInstance();
            temp.setTime(today);
            temp.add(Calendar.DATE, -7);

            int wdayOfMonth = temp.get(Calendar.DAY_OF_MONTH); // 17
            int wmonth = temp.get(Calendar.MONTH); // 5
            int wyear = temp.get(Calendar.YEAR); // 2016

            Integer start=wyear*10000+wmonth*100+wdayOfMonth;

            Log.i("previouwee",Integer.toString(start));

            query=databaseReference.orderByChild("datevalue").startAt(start).endAt(value);
        }

        if(option==3)
        {
            Calendar temp = Calendar.getInstance();
            temp.setTime(today);
            temp.add(Calendar.DATE, -31);

            int mdayOfMonth = temp.get(Calendar.DAY_OF_MONTH);
            int mmonth = temp.get(Calendar.MONTH);
            int myear = temp.get(Calendar.YEAR);

            Integer start=myear*10000+mmonth*100+mdayOfMonth;

            query=databaseReference.orderByChild("datevalue").startAt(start).endAt(value);

        }


        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                EventsList data=new EventsList();
                data= dataSnapshot.getValue(EventsList.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.event_sort_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.today) {
            option=1;
            finish();
            startActivity(getIntent());
        }

        else if(id==R.id.lastweek)
        {
            option=2;
            finish();
            startActivity(getIntent());
        }

        else if(id==R.id.lastmonth)
        {
            option=3;
            finish();
            startActivity(getIntent());
        }

        else if(id==R.id.all)
        {
            option=4;
            finish();
            startActivity(getIntent());
        }

        return super.onOptionsItemSelected(item);
    }



}
