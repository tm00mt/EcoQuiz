package com.example.tm__mt.ecoquiz;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class ResultActivity extends ListActivity {
    private static final String DEBUG_TAG = "ResultActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Log.d(DEBUG_TAG, "Creating ResultActivity...");

        Intent intent = getIntent();
        final int categoryId = intent.getIntExtra("categoryNumber", 0);
        final int attemptNr = intent.getIntExtra("attemptNumber", 0);

        EcoQuizDBHelper DBHelper = new EcoQuizDBHelper(this);
        ResultAdapter ra = new ResultAdapter(this);
        int cntr = DBHelper.prepareResultData(categoryId, attemptNr);
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

        Button bSave = (Button) findViewById(R.id.bSave);
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RankingActivity.class);
                intent.putExtra("categoryId", categoryId);
                intent.putExtra("attemptNr", attemptNr);
                startActivity(intent);
                finish();
            }
        });

    }
}
