package com.NITK.ACM.iACM;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class UserDetailsActivity extends AppCompatActivity{

    TextView userName, Batch, About, AreasOfInterest, Achievements;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_user_details);

            userName = findViewById(R.id.user_detail_name);
            Batch = findViewById(R.id.batch);
            About = findViewById(R.id.About);
            AreasOfInterest = findViewById(R.id.AreasOfInterest);
            Achievements = findViewById(R.id.Achievements);

            userName.setText(UserAdapter.userProfile.getUserName());
            Batch.setText("Batch of "+UserAdapter.userProfile.getBatch().toString());
            About.setText(UserAdapter.userProfile.getBio());
            AreasOfInterest.setText(UserAdapter.userProfile.getAreasOfInterest());
            Achievements.setText(UserAdapter.userProfile.getAchievements());
        }

}
