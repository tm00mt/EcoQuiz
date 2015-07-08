package com.example.tm__mt.ecoquiz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

/**
 * Created by tm__mt
 *
 * Helper class. Is used to prepare images to the first question.
 *
 * Checks if there is network connection and then tries to download logos to the first question.
 */

public class PreQuestionActivity extends ActionBarActivity {
    private static final String DEBUG_TAG = "PreQuestionActivity";
    private static final int LOGOS_NUM = 6;

    private LogoDownloader[] logos = new LogoDownloader[LOGOS_NUM];
    private SingleQuestionData firstQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_question);

        Log.d(DEBUG_TAG, "Creating PreQuestionActivity...");

        if (isNetworkAvailable()) {
            //get question data from previous Activity
            Intent intent = getIntent();
            firstQuestion = intent.getParcelableExtra("questionData");

            for (int i = 0; i < LOGOS_NUM; i++) {
                logos[i] = new LogoDownloader(i + 1, this);
                Log.d(DEBUG_TAG, "Link na serwer: " + firstQuestion.getLogoLink(i));
                logos[i].execute(firstQuestion.getLogoLink(i));
            }
            new LogoStatusChecker().execute();
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(R.string.no_internet_connection);
            alertDialogBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            finish();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    private class LogoStatusChecker extends AsyncTask<Void, Void, Void> {

        boolean logosAreDownloaded = false;

        @Override
        protected Void doInBackground(Void... params) {
            Log.d(DEBUG_TAG, "LogoStatusChecker.doInBackground");
            long startTime = SystemClock.elapsedRealtime();
            Log.d(DEBUG_TAG, "startTime: " + startTime);
            long currentTime;
            long displayDownloadStatusEveryMilis = 500;
            long stopDownloadAfterMilis = 10000;
            int checkCounter = 1;
            boolean displayStatus;

            while (true) {
                currentTime = SystemClock.elapsedRealtime();
                if ((currentTime - startTime ) / displayDownloadStatusEveryMilis > checkCounter)  {
                    Log.d(DEBUG_TAG, checkCounter + ". check of downloaded logos status");
                    checkCounter++;
                    displayStatus = true;
                } else {
                    displayStatus = false;
                }

                int n = 0;
                for (int i=0; i<LOGOS_NUM; i++) {
                    if (logos[i].isLogoSaved(displayStatus))
                        n++;
                }

                if (n >= 6) {
                    logosAreDownloaded = true;
                    break;
                }

                if (currentTime - startTime > stopDownloadAfterMilis) {
                    for (int i = 0; i < LOGOS_NUM; i++) {
                        logos[i].cancel(true);
                    }
                    break;
                }
            }
            Log.d(DEBUG_TAG, "endTime: " + SystemClock.elapsedRealtime());

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d(DEBUG_TAG, "LogoStatusChecker.onPostExecute");

            if (logosAreDownloaded) {
                for (int i = 0; i < LOGOS_NUM; i++)
                    firstQuestion.setBitmapPath(logos[i].getLogoPath(), i);

                Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                intent.putExtra("questionData", firstQuestion);
                startActivity(intent);
                finish();
            } else {
                displayDialogBox(2);
            }
        }
    }

    private boolean isNetworkAvailable() {
        Log.d(DEBUG_TAG, "isNetworkAvailable");
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void displayDialogBox(int msgNumber) {
        final int msgNoInternet = 1;
        final int msgLogoDownloadFailed = 2;

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        switch (msgNumber) {
            case msgNoInternet:
                alertDialogBuilder.setMessage(R.string.no_internet_connection);
                break;
            case msgLogoDownloadFailed:
                alertDialogBuilder.setMessage(R.string.downloading_to_long);
                break;
        }
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
