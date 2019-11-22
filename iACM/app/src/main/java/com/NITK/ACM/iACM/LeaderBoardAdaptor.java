package com.NITK.ACM.iACM;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static com.NITK.ACM.iACM.R.layout.leader_board_row;

public class LeaderBoardAdaptor extends RecyclerView.Adapter<LeaderBoardAdaptor.MyViewHolder> {

    private List<LeaderBoardElement> leaderBoardElementList;

    public LeaderBoardAdaptor(List<LeaderBoardElement> leaderBoardElementListList) {
        this.leaderBoardElementList = leaderBoardElementListList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView per_name;
        public TextView points;
        public TextView position;

        public MyViewHolder(View view) {
            super(view);
            per_name = (TextView) view.findViewById(R.id.name);
            points = (TextView) view.findViewById(R.id.points);
            position=(TextView) view.findViewById(R.id.position);
        }
    }

    public LeaderBoardAdaptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(leader_board_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(LeaderBoardAdaptor.MyViewHolder holder, int position) {
        LeaderBoardElement e = leaderBoardElementList.get (getItemCount () - position - 1);
        holder.per_name.setText (e.getPerson_name ());
        holder.points.setText (e.getPoints ().toString ());
        holder.position.setText (Integer.toString (position + 1));
        try {
            if (e.getuID ().equals (MainActivity.user_data.getuID ())) {
                holder.per_name.setTextColor (Color.YELLOW);
                holder.points.setTextColor (Color.YELLOW);
                Log.i ("sdasd", e.getPerson_name ());
            }
        } catch (NullPointerException t)
        {}
    }

    public void addItem(LeaderBoardElement leaderBoardElementList)
    {
        this.leaderBoardElementList.add(leaderBoardElementList);
    }
    @Override
    public int getItemCount() {

        Log.i("Item-Count-2",Integer.toString(leaderBoardElementList.size()));
        return leaderBoardElementList.size();
    }
}
