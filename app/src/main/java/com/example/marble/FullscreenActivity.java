package com.example.marble;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FullscreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);



        Handler delay=new Handler();
        delay.postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(FullscreenActivity.this,Category.class));

            }
        },6000);
    }
}
