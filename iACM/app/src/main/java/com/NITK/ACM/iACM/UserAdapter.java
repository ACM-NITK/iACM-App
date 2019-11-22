package com.NITK.ACM.iACM;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;
import static com.NITK.ACM.iACM.R.layout.user_detail_row;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<UserProfile> userProfiles;
    private RecyclerViewClickListener mListener;
    private Context context;
    public static UserProfile userProfile;

    class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView userName;
        TextView batch;
        private RecyclerViewClickListener mListener;
        private LinearLayoutCompat linearLayoutCompat;

        UserViewHolder(View view, RecyclerViewClickListener recyclerViewClickListener)
        {
            super(view);
            userName = (TextView) view.findViewById(R.id.user_name);
            batch = (TextView) view.findViewById(R.id.batch);
            linearLayoutCompat = (LinearLayoutCompat) view.findViewById(R.id.user_linear_layout);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v, getAdapterPosition());
        }
    }


    public UserAdapter(List<UserProfile> userProfileList, Context context)
    {
        this.userProfiles = userProfileList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(user_detail_row, viewGroup, false);

        return new UserViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, final int i) {

        userViewHolder.userName.setText(userProfiles.get(i).getUserName());
        userViewHolder.batch.setText("Batch of "+userProfiles.get(i).getBatch().toString());

        userViewHolder.linearLayoutCompat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("DSDAS",userProfiles.get(i).getUserName());
                userProfile = userProfiles.get(i);
                context.startActivity(new Intent(context, UserDetailsActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return userProfiles.size();
    }

    public void addItem(UserProfile userProfile) {
        this.userProfiles.add(userProfile);
    }
}


