package com.NITK.ACM.iACM;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.support.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import static com.NITK.ACM.iACM.R.layout.event_list_row;

public class EventAdaptor extends RecyclerView.Adapter<EventAdaptor.MyViewHolder> {

    private List<EventsList> eventsList;
    public static EventsList e;
    //MainActivity mainActivity;
    static Button attd_button;
    static ImageButton remove;
    static ImageButton reminder;
    static TextView event;
    static boolean removeFlag=false;
    static EventsList edit;
   static private Context context;
    private static String purpose;



    public EventAdaptor(List<EventsList> eventsList) {
        this.eventsList = eventsList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            attd_button=(Button)view.findViewById(R.id.mark_attendance);
            remove=(ImageButton)view.findViewById(R.id.remove_event);
            event=(TextView)view.findViewById(R.id.title);
            reminder=(ImageButton)view.findViewById(R.id.reminder_event);
            context = itemView.getContext();
            Log.v ("string", String.valueOf (context));
            if(MainActivity.currentActivity=="ApproveAttendance")
            {
                attd_button.setText("");

            }

            else
            {
                remove.setVisibility(View.INVISIBLE);
                reminder.setVisibility(View.INVISIBLE);
            }

        }
    }


    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(event_list_row, parent, false);

        return new MyViewHolder(itemView);
    }


    public static class LoginDialog extends AppCompatDialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            final Context context=getContext();

            if(purpose.equals("Remove")) {
                builder.setTitle("Are you sure you wish to delete event!")
                        .setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MainActivity.databaseReference.child("Events").child(e.geteID()).removeValue();
                                Toast.makeText(context, "Event Removed", Toast.LENGTH_LONG);
                            }
                        })
                        .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                return builder.create();
            }

            else
            {
                builder.setTitle("Do you wish to send reminder for event!")
                        .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MainActivity.databaseReference.child("Reminder").child("name").setValue(e.getTitle()+" starting at "+e.getTime().getHours()+":"+e.getTime().getMinutes());
                            }
                        });
                return builder.create();

            }

        }
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        e = eventsList.get (getItemCount () - position - 1);
        if (e == null)
            Log.i ("Event null:", "yes");
        holder.title.setText (e.getTitle ());
        holder.title.setGravity (Gravity.CENTER_VERTICAL);

        try {

            switch (e.getSig ()) {
                case "Sanganitra": //holder.itemView.setBackgroundColor(Color.parseColor("#bc8f8f"));
                    holder.title.setBackgroundTintList (context.getResources ().getColorStateList (R.color.bg));

                    break;

                case "Media":
                    holder.title.setBackgroundTintList (context.getResources ().getColorStateList (R.color.rose));

                    break;

                case "Vidyut":
                    holder.title.setBackgroundTintList (context.getResources ().getColorStateList (R.color.green));
                    break;

                case "Kaaryavarta":
                    holder.title.setBackgroundTintList (context.getResources ().getColorStateList (R.color.grey));
                    break;

                case "Yantrika":
                    holder.title.setBackgroundTintList (context.getResources ().getColorStateList (R.color.pink));
                    break;

                case "Saahitya":
                    holder.title.setBackgroundTintList (context.getResources ().getColorStateList (R.color.purple));
                    break;

                default:
                    holder.title.setBackgroundTintList (context.getResources ().getColorStateList (R.color.khaki));

            }
            event.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    e = eventsList.get (getItemCount () - position - 1);
                    //Log.i("ji",e.getTitle());

                    if (events.dialog == null)
                        Log.i ("Dialog", " null");

                    // Log.i("curr",MainActivity.currentActivity);
                    if (!MainActivity.currentActivity.equals ("ApproveAttendance")) {

                        String date = "";

                        if ((e.date.getDate ()) < 10)
                            date = date + "0" + Integer.toString (e.date.getDate ()) + "/";

                        else
                            date = date + Integer.toString (e.date.getDate ()) + "/";

                        if ((e.date.getMonth ()) < 10)
                            date = date + "0" + Integer.toString (e.date.getMonth () + 1) + "/";

                        else
                            date = date + "0" + Integer.toString (e.date.getMonth () + 1) + "/";

                        date = date + Integer.toString (e.date.getYear () - 100 + 2000);

                        String time = "";

                        if (e.getTime ().getHours () < 10)
                            time = time + "0" + Integer.toString (e.getTime ().getHours ()) + ":";

                        else
                            time = time + Integer.toString (e.getTime ().getHours ()) + ":";


                        if (e.getTime ().getMinutes () < 10)
                            time = time + "0" + Integer.toString (e.getTime ().getMinutes ());

                        else
                            time = time + Integer.toString (e.getTime ().getMinutes ());

                        Log.i ("date time", date + " " + time);

                        events.dialog.setMessage ("SIG:      " + e.getSig () + "\nVenue:  " + e.getVenue () +
                                "\nDate:    " + date +
                                "\nTime:    " + time +
                                "\nPoints:  " + e.getPoints () +
                                "\nDetails: " + e.getDetails ()).setTitle (e.getTitle ());
                        AlertDialog alert = events.dialog.create ();
                        alert.show ();
                    }

                }
            });

            attd_button.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(final View v) {
                    e = eventsList.get (getItemCount () - position - 1);
                    final String name = MainActivity.user_data.getPerson_name ();
                    final String event_name = e.getTitle ();
                    if (e.getTitle () == null)
                        Log.i ("eIIDD", "null");

                    else
                        Log.i ("eeee", "not null");


                    if (MainActivity.currentActivity == "ApproveAttendance" || MainActivity.currentActivity == "Tab2" || MainActivity.currentActivity == "Tab1") {
                        Intent intent = new Intent (v.getContext (), Attendees.class);
                        v.getContext ().startActivity (intent);
                    } else {
                        if (e.getTitle () != null)
                            Log.i ("eid", e.geteID ());
                        MainActivity.databaseReference.child ("Events").child (e.geteID ()).child ("Attendees").child (MainActivity.user_data.getuID ()).addListenerForSingleValueEvent (new ValueEventListener () {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if (dataSnapshot.getValue () == null) {

                                    Log.i ("In if", "1");
                                    MainActivity.databaseReference.child ("Events").child (e.geteID ()).child ("Attended").child (MainActivity.user_data.getuID ()).addListenerForSingleValueEvent (new ValueEventListener () {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshots) {

                                            if (dataSnapshots.getValue () == null) {
                                                Log.i ("In if", "2");
                                                Toast.makeText (v.getContext (), "You have requested for attendance", Toast.LENGTH_LONG).show ();
                                                AttendeeRow attendeeRow = new AttendeeRow ();
                                                attendeeRow.setaID (MainActivity.user_data.getuID ());
                                                attendeeRow.setName (MainActivity.user_data.getPerson_name ());
                                                attendeeRow.setPoints (MainActivity.user_data.getPoints ());
                                                attendeeRow.setAttended ("Appeal");
                                                MainActivity.databaseReference.child ("Events").child (e.geteID ()).child ("Attendees").child (MainActivity.user_data.getuID ()).setValue (attendeeRow);
                                                holder.title.setTextColor (Color.GREEN);
                                            } else {
                                                Log.i ("in else", "2");
                                                Toast.makeText (v.getContext (), "Attendance has already been approved!", Toast.LENGTH_LONG).show ();
                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                } else {
                                    Log.i ("In else", "1");
                                    Toast.makeText (v.getContext (), "You have already appealed for attendance!", Toast.LENGTH_LONG).show ();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        Log.i ("got", event_name);

                    }
                }
            });

            remove.setOnClickListener (new View.OnClickListener () {

                @Override
                public void onClick(final View v) {
                    e = eventsList.get (getItemCount () - position - 1);
                    final Intent intent;
                    purpose = "Remove";
                    LoginDialog dialogFragment = new LoginDialog ();
                    dialogFragment.show (((AppCompatActivity) context).getSupportFragmentManager (), "Fragment");
                }
            });


            reminder.setOnClickListener (new View.OnClickListener () {

                @Override
                public void onClick(final View v) {
                    e = eventsList.get (getItemCount () - position - 1);
                    final Intent intent;
                    purpose = "Reminder";
                    LoginDialog dialogFragment = new LoginDialog ();
                    dialogFragment.show (((AppCompatActivity) context).getSupportFragmentManager (), "Fragment");

                }
            });
        }catch (NullPointerException e)
        {
        }
    }


    public void addItem(EventsList eventsList)
    {
        this.eventsList.add(eventsList);
    }
    @Override
    public int getItemCount() {

        Log.i("Item-Count",Integer.toString(eventsList.size()));
        return eventsList.size();
    }

}
