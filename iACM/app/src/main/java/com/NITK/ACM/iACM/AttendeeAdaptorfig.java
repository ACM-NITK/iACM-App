package com.NITK.ACM.iACM;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static com.NITK.ACM.iACM.R.layout.activity_attendee_row;

public class AttendeeAdaptorfig extends RecyclerView.Adapter<AttendeeAdaptorfig.MyViewHolder> {
private List<AttendeeRow> attendeeRowList;
        //Fig_homepage Fig_homepage;
        Button accept;
        Button reject;
        DatabaseReference databaseReference;
        TabLayout tabLayout;
public String nowTab;

public AttendeeAdaptorfig(List<AttendeeRow> attendeeRowList) {
        this.attendeeRowList = attendeeRowList;
        }



public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView attendee_title;


    public MyViewHolder(View view) {
        super(view);
        attendee_title = (TextView) view.findViewById(R.id.name);
        accept = (Button) view.findViewById(R.id.accept);
        reject = (Button) view.findViewById(R.id.reject);
        if(nowTab=="Tab2") {
            accept.setVisibility(View.GONE);
            reject.setText("Remove");
        }
    }
}

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(activity_attendee_row, parent, false);

        return new MyViewHolder (itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final AttendeeRow a=attendeeRowList.get(position);
        Log.i ("nameholder",a.getName ());
        holder.attendee_title.setText(a.getName());
        holder.attendee_title.setGravity(Gravity.CENTER_VERTICAL);

        reject.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(nowTab=="Tab1")
                {
                    holder.attendee_title.setTextColor(Color.RED);
                    //
                    databaseReference = FirebaseDatabase.getInstance ().getReference ();
                    databaseReference.child("FIG_Events").child(FigEventAdaptor.e.geteID()).child("Attendees").child(a.getaID()).removeValue();
                    Toast.makeText(v.getContext(), "Request rejected for "+a.getName(), Toast.LENGTH_SHORT).show();
                }

                else
                {
                    databaseReference = FirebaseDatabase.getInstance ().getReference ();
                    DatabaseReference removedperson =databaseReference.child("FigLeaderBoard").child(a.getaID());//
                  databaseReference.child("FIG_Events").child(EventAdaptor.e.geteID()).child("Attended").child(a.getaID()).removeValue();

                    removedperson.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                           FigLeaderelements remove=dataSnapshot.getValue(FigLeaderelements.class);
                           int t = FigEventAdaptor.e.getPoints(); //
                            remove.setPoints(String.valueOf (Integer.parseInt (remove.getPoints())- t));//

                            databaseReference.child("FigLeaderBoard").child(a.getaID()).setValue(remove);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    holder.attendee_title.setTextColor(Color.RED);
                    Toast.makeText(v.getContext(), "Removed "+a.getName(), Toast.LENGTH_SHORT).show();
                }



            }
        });

        accept.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.i("eTitle",FigEventAdaptor.e.geteID());
                holder.attendee_title.setTextColor(Color.GREEN);//
             //   DatabaseReference approvedperson =Fig_homepage.databaseReference.child("FigLeaderBoard").child(a.getaID());
                    DatabaseReference approvedperson = FirebaseDatabase.getInstance ().getReference ().child ("FigLeaderBoard").child (a.getaID ());
                approvedperson.addListenerForSingleValueEvent(new ValueEventListener () {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        FigLeaderelements remove=dataSnapshot.getValue(FigLeaderelements.class); //
                        assert remove != null;
                        remove.setPoints(remove.getPoints()+FigEventAdaptor.e.getPoints());//
                        databaseReference = FirebaseDatabase.getInstance ().getReference ();
                        databaseReference.child("FigLeaderBoard").child(a.getaID()).setValue(remove);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                //Fig_homepage.databaseReference.child("Leader Board").child(a.getaID()).child("points").setValue((Integer)(a.getPoints()+EventAdaptor.e.getPoints()));
                Toast.makeText(v.getContext(), "Attendance approved for "+a.getName(), Toast.LENGTH_SHORT).show();
                databaseReference = FirebaseDatabase.getInstance ().getReference ();
                databaseReference.child("FIG_Events").child(FigEventAdaptor.e.geteID()).child("Attended").child(a.getaID()).setValue(a);
                databaseReference.child("FIG_Events").child(FigEventAdaptor.e.geteID()).child("Attendees").child(a.getaID()).removeValue();

            }
        });
    }

    public void addItem(AttendeeRow attendeeRow)
    {
        this.attendeeRowList.add(attendeeRow);
    }
    @Override
    public int getItemCount() {

        Log.i("Item-Count",Integer.toString(attendeeRowList.size()));
        return attendeeRowList.size();
    }
}

