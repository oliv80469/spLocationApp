package com.example.oliv.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(Constants.LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
        Intent backGroundService = new Intent(this.getApplicationContext(), BackGround.class);

        setContentView(R.layout.activity_main);
        startService(backGroundService);
        Log.d(Constants.LOG_TAG, "onCreate exit");
    }
}
