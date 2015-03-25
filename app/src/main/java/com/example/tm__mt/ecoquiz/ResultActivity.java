package com.example.tm__mt.ecoquiz;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

public class ResultActivity extends ActionBarActivity {
    private static final String DEBUG_TAG = "ResultActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Log.d(DEBUG_TAG, "Creating ResultActivity...");
    }
}
