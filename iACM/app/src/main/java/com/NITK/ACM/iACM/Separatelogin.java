package com.NITK.ACM.iACM;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Separatelogin extends AppCompatActivity {
    Button bt1,bt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_separatelogin);
        bt1 = (Button) findViewById (R.id.acmlogin);
        bt2= (Button) findViewById (R.id.figlogin);

        bt1.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (Separatelogin.this,LoginActivity.class));
            }
        });

        bt2.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (Separatelogin.this,Figlogin.class));
            }
        });

    }
}
