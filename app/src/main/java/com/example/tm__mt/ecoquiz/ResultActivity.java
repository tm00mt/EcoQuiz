package com.example.tm__mt.ecoquiz;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class ResultActivity extends ListActivity {
    private static final String DEBUG_TAG = "ResultActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Log.d(DEBUG_TAG, "Creating ResultActivity...");

        Intent intent = getIntent();
        int categoryId = intent.getIntExtra("categoryNumber", 0);
        int attemptNr = intent.getIntExtra("attemptNumber", 0);

        EcoQuizDBHelper DBHelper = new EcoQuizDBHelper(this);
        int cntr = DBHelper.prepareResultData(categoryId, attemptNr);
        ResultAdapter ra = new ResultAdapter(this);
        if (cntr > 0) {
            int i = 0;
            while (i < cntr) {
                if (DBHelper.getResultDataRR(i).givenAnswer == DBHelper.getResultDataCA(i))
                    ra.addOKItem(DBHelper.getResultDataRR(i));
                else
                    ra.addNOKItem(DBHelper.getResultDataRR(i));
                i++;
            }
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("No data to display.");
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

        ListView lv = getListView();
        View header = getLayoutInflater().inflate(R.layout.result_row_header, lv, false);

        lv.addHeaderView(header);
        setListAdapter(ra);

    }
}
