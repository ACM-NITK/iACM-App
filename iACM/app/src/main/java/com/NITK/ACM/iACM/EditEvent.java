package com.NITK.ACM.iACM;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditEvent extends AppCompatActivity  {
    private Button btn_add_event;
    private EditText event_name;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    public static EditText datetext;
    public static EditText timetext;
    public static EditText sigtext;
    private EditText venue;
    static EventsList event=new EventsList();
    private EditText details;
    private EditText points;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

       // View rootView = inflater.inflate(R.layout.add_event, container, false);
        setContentView(R.layout.activity_edit_event);

        btn_add_event=(Button) findViewById(R.id.btn_add_event);
        event_name=(EditText)  findViewById(R.id.event_name);
        datetext=(EditText) findViewById(R.id.Date);
        timetext=(EditText) findViewById(R.id.Time);
        sigtext=(EditText) findViewById(R.id.Sig);
        venue=(EditText) findViewById(R.id.Venue);
        details=(EditText)findViewById(R.id.Details);
        points=(EditText) findViewById(R.id.points);


        event_name.setText(EventAdaptor.e.getTitle());
        datetext.setText(EventAdaptor.e.getDate().toString());
        timetext.setText(EventAdaptor.e.getTime().toString());
        venue.setText(EventAdaptor.e.getVenue());
        details.setText(EventAdaptor.e.getDetails());
        points.setText(EventAdaptor.e.getPoints());




    }
}
