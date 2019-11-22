package com.NITK.ACM.iACM;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.MyViewHolder> {
    private List<Announcement> announcementList;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView Content;
        public MyViewHolder(View view) {
            super(view);
            Content = (TextView) view.findViewById(R.id.announcement);
        }
    }

    public AnnouncementAdapter(List<Announcement> announcementList) {
        this.announcementList = announcementList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.announcement_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Announcement announcement = announcementList.get(position);
        holder.Content.setText(announcement.getContent());
    }

    @Override
    public int getItemCount() {
        return announcementList.size();
    }

    public void addItem(Announcement announcement)
    {
        this.announcementList.add(announcement);
    }
}
