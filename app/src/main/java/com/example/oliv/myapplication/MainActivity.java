package com.example.oliv.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent backGroundService = new Intent(this.getApplicationContext(), BackGround.class);
        Log.d(Constants.LOG_TAG, "starting main activity");
        setContentView(R.layout.activity_main);
        startService(backGroundService);
    }
}
