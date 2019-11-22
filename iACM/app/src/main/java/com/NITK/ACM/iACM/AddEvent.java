package com.NITK.ACM.iACM;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEvent extends Fragment implements View.OnClickListener {
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
    private CheckBox figCheck;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.add_event, container, false);

        btn_add_event=(Button) rootView.findViewById(R.id.btn_add_event);
        event_name=(EditText)  rootView.findViewById(R.id.event_name);
        datetext=(EditText) rootView.findViewById(R.id.Date);
        timetext=(EditText) rootView.findViewById(R.id.Time);
        sigtext=(EditText) rootView.findViewById(R.id.Sig);
        venue=(EditText) rootView.findViewById(R.id.Venue);
        details=(EditText)rootView.findViewById(R.id.Details);
        points=(EditText) rootView.findViewById(R.id.points);
        btn_add_event.setOnClickListener(this);
        figCheck = rootView.findViewById(R.id.FigcheckBox);


        timetext.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                showTimePickerDialog(view);
            }
        });


        datetext.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                showDatePickerDialog(view);
            }
        });


        sigtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu

                PopupMenu popup = new PopupMenu(getActivity().getBaseContext(), sigtext);


                popup.getMenuInflater().inflate(R.menu.sig_menu, popup.getMenu());
                Log.i("menu",popup.getMenuInflater().toString());
                popup.show();
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        sigtext.setText(item.getTitle());
                        return true;
                    }
                });

            }
        });

        return rootView;
    }


    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");

    }


    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        Log.i("datepicker","datepicker");
        newFragment.show(getFragmentManager(),"date");
    }


    @Override
    public void onClick(View v) {
        String name=event_name.getText().toString();
        //event_name=(EditText)  v.findViewById(R.id.event_name);

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getContext(),"Enter Event Name",Toast.LENGTH_SHORT).show();
            return;
        }

        else
        {
            databaseReference=FirebaseDatabase.getInstance().getReference();
            event.setTitle(name);

            event.setVenue(venue.getText().toString());
            event.setSig(sigtext.getText().toString());
            event.setDetails(details.getText().toString());

            String eventsId =databaseReference.child("Events").push().getKey();
            event.seteID(eventsId);

            event.setPoints(Integer.valueOf(points.getText().toString()));

            if(figCheck.isChecked())
                databaseReference.child("FIG_Events").child(eventsId).setValue(event);

            else
            {    databaseReference.child("Events").child(eventsId).setValue(event);
                 databaseReference.child("Event Count").child("name").setValue(event.getTitle());
            }

            Toast.makeText(getContext(),"Event Added",Toast.LENGTH_LONG).show();

            event_name.setText("");
            sigtext.setText("");
            venue.setText("");
            datetext.setText("");
            timetext.setText("");
            details.setText("");
            points.setText("");


        }
    }
}
