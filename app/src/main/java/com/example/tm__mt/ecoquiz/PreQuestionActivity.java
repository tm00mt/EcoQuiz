package com.example.tm__mt.ecoquiz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

public class PreQuestionActivity extends ActionBarActivity {
    private static final String DEBUG_TAG = "PreQuestionActivity";
    private static final int LOGOS_NUM = 6;

    private LogoDownloader[] logos = new LogoDownloader[LOGOS_NUM];

    private NextQuestion currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_question);

        Log.d(DEBUG_TAG, "Creating PreQuestionActivity...");

        if (isOnline()) {
            //get question data from previous Activity
            Intent intent = getIntent();
            currentQuestion = intent.getParcelableExtra("questionData");

            for (int i = 0; i < LOGOS_NUM; i++) {
                Log.d(DEBUG_TAG, "Link " + i + " passed to LogoDownloader: " + currentQuestion.getLogoLink(i));

                logos[i] = new LogoDownloader(i + 1, this);
                logos[i].execute(currentQuestion.getLogoLink(i));
            }
            new LogoStatusChecker().execute();
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Internet connection is unavailable.");
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

        @Override
        protected Void doInBackground(Void... params) {
            Log.d(DEBUG_TAG, "doInBackground2 ");

            while (true) {
                int n = 0;
                for (int i=0; i<LOGOS_NUM; i++) {
                    if (logos[i].isLogoSaved())
                        n++;
                }

                Log.d(DEBUG_TAG, "while loop..." + n);
                if (n >= 6)
                    break;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d(DEBUG_TAG, "onPostExecute2 ");
            for (int i = 0; i < LOGOS_NUM; i++)
                currentQuestion.setBitmapPath(logos[i].getLogoPath(), i);

            Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
            intent.putExtra("questionData", currentQuestion);
            startActivity(intent);
            finish();
        }
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
